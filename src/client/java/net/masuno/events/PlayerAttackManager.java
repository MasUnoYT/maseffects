package net.masuno.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MathUtility;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Environment(EnvType.CLIENT)
public class PlayerAttackManager {

    public static void clientAttack(PlayerEntity player, Entity victim, World world){
        if (world.isClient){
            if (!MasConfig.INSTANCE.ShieldEffect) return;

            //If it doesn't have mace, do nothing
            if (!player.getMainHandStack().isOf(Items.MACE)) return;

            boolean is_shielding = false;
            if (victim instanceof PlayerEntity victim_player){
                if (MathUtility.isShielding(player,victim_player) && player.fallDistance >= 1.5F){
                    //if enemy is using shield, spawn shield hit
                    is_shielding = true;
                    ShieldShockwave(victim_player);
                }
            }
            if (!is_shielding && victim instanceof LivingEntity livingEntity){
                if (hasEnchantment(player.getMainHandStack(), Enchantments.BREACH)){
                    //If player is using breach, apply armor particles
                    if (MasConfig.INSTANCE.ArmorParticles) ArmorParticles(livingEntity);
                }
            }
        }
    }

    public static void ShieldShockwave(PlayerEntity shielder){
        Vec3d direction = shielder.getRotationVector();
        Vec3d height = new Vec3d(0F,shielder.getDimensions(shielder.getPose()).height() * 0.6F,0F);
        Vec3d pos = shielder.getPos().add(height).add(direction.multiply(0.5F));
        shielder.getWorld().addParticle(ModParticles.SHIELD_WAVE,pos.getX(),pos.getY(),pos.getZ(),0F,0F,0F);
    }

    public static void SlamEffect(double x, double y, double z) {
        //Gets the current world (overworld/nether/end) depending on where the client is
        ClientWorld world = MinecraftClient.getInstance().world;

        //If there´s no world, stop here
        if (world == null) return;

        //set a random vector between -0.6 and 0.6 values
        Random rand = new Random();
        Vec3d r = new Vec3d(rand.nextFloat(-0.6f,0.6f),rand.nextFloat(-1.2f,1.2f),rand.nextFloat(-0.6f,0.6f));

        if (MasConfig.INSTANCE.MaceShockwave){
            //Spawn two shock-waves (small and big)
            world.addParticle(ModParticles.SMASH,x,y,z,0.8d,0.8d,1.5 * MasConfig.INSTANCE.MaceShockwaveSize);
            world.addParticle(ModParticles.SMASH,x,y,z,0.4d,1d,0.35 * MasConfig.INSTANCE.MaceShockwaveSize);
        }

        if (MasConfig.INSTANCE.MaceSpark){
            //Spawn the mace hit sparks
            world.addParticle(ModParticles.FLICK,
                    x + r.x,
                    y + r.y + 0.9F,
                    z + r.z,
                    0.5F,0,0);

            world.addParticle(ModParticles.FLICK,
                    x + r.x,
                    y + r.y + 0.9F,
                    z + r.z,
                    0.2F,0,0);
        }

        if(MasConfig.INSTANCE.MaceFlash){
            //Spawn the mace hit flash
            world.addParticle(ModParticles.FLASH,x,y,z,0,0,0);
        }
    }

    public static void SpawnParticlesOnHitbox(World world,Vec3d pos,Vec3d size , ParticleEffect particle, int count) {
        //Spawns the particle randomly inside the bounding box "size"
        for (int i = 0; i <= count; i++) {
            Vec3d r = new Vec3d(
                    new Random().nextDouble(-size.x / 2F, size.x / 2F),
                    new Random().nextDouble(0, size.y),
                    new Random().nextDouble(-size.x / 2F, size.x / 2F));

            world.addParticle(particle,
                    pos.getX() + r.getX(),
                    pos.getY() + r.getY(),
                    pos.getZ() + r.getZ(),

                    r.getX(), r.getY(), r.getZ());
        }
    }

    public static boolean hasEnchantment(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        return stack.getEnchantments().getEnchantments().toString().
                contains(enchantment.getValue().toString());
    }

    public static void ArmorParticles(LivingEntity le){
        //Checks for every armor item the victim has
        List<ItemStack> armor_items = new ArrayList<>();
        le.getArmorItems().forEach(armor_items::add);
        for(ItemStack item : armor_items){
            Vec3d size = new Vec3d(
                    le.getDimensions(le.getPose()).width(),
                    le.getDimensions(le.getPose()).height() / 4F,
                    le.getDimensions(le.getPose()).width());

            if (item.isIn(ItemTags.HEAD_ARMOR)){
                //If the item being checked is a helmet, check the material and spawn the corresponding particle in the head
                int count = new Random().nextInt(0,3);
                if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.DIAMOND){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY() * 3F,0F)),size,ModParticles.DIAMOND_SCRAP,count);
                    continue;
                }else if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.NETHERITE){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY() * 3F,0F)),size,ModParticles.NETHERITE_SCRAP,count);
                    continue;
                }
            }

            if (item.isIn(ItemTags.CHEST_ARMOR)){
                //If the item being checked is a chestplate, check the material and spawn the corresponding particle in the chest
                int count = new Random().nextInt(0,3);
                if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.DIAMOND){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY() * 2F,0F)),size,ModParticles.DIAMOND_SCRAP,count);
                    continue;
                }else if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.NETHERITE){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY() * 2F,0F)),size,ModParticles.NETHERITE_SCRAP,count);
                    continue;
                }
            }
            if (item.isIn(ItemTags.LEG_ARMOR)){
                //If the item being checked is leggings, check the material and spawn the corresponding particle in the legs
                int count = new Random().nextInt(0,3);
                if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.DIAMOND){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY(),0F)),size,ModParticles.DIAMOND_SCRAP,count);
                    continue;
                }else if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.NETHERITE){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY(),0F)),size,ModParticles.NETHERITE_SCRAP,count);
                    continue;
                }
            }
            if (item.isIn(ItemTags.FOOT_ARMOR)){
                //If the item being checked is boots, check the material and spawn the corresponding particle in the feet
                int count = new Random().nextInt(0,3);
                if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.DIAMOND){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos(),size,ModParticles.DIAMOND_SCRAP,count);
                }else if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.NETHERITE){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos(),size,ModParticles.NETHERITE_SCRAP,count);
                }
            }
        }
    }
}
