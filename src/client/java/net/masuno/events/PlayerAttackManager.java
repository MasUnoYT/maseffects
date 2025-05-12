package net.masuno.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Environment(EnvType.CLIENT)
public class PlayerAttackManager {

    public static void SlamEffect(double x, double y, double z) {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world == null) return;

        List<LivingEntity> entities = new ArrayList<>();
        for (Entity ent : world.getEntities()){
            if (ent instanceof LivingEntity e) entities.add(e);
        }
        LivingEntity victim = world.getClosestEntity(entities, TargetPredicate.DEFAULT,null,x,y,z);
        if (victim != null && MasConfig.ArmorParticles) ArmorParticles(victim);

        Random rand = new Random();
        Vec3d r = new Vec3d(rand.nextFloat(-0.4f,0.4f),rand.nextFloat(-0.8f,0.8f),rand.nextFloat(-0.4f,0.4f));
        r = r.multiply(1.5F);

        if (MasConfig.MaceShockwave){
            world.addParticle(ModParticles.SMASH,x,y,z,0.8d,0.8d,2);
            world.addParticle(ModParticles.SMASH,x,y,z,0.4d,1d,0.5);
        }

        if (MasConfig.MaceSpark){
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

        if(MasConfig.MaceFlash){
            world.addParticle(ModParticles.FLASH,x,y,z,0,0,0);
        }
    }

    public static void SpawnParticlesOnHitbox(World world,Vec3d pos,Vec3d size , ParticleEffect particle, int count) {

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

    public static void ArmorParticles(LivingEntity le){
        List<ItemStack> armor_items = new ArrayList<>();
        le.getArmorItems().forEach(armor_items::add);
        for(ItemStack item : armor_items){
            Vec3d size = new Vec3d(
                    le.getDimensions(le.getPose()).width(),
                    le.getDimensions(le.getPose()).height() / 4F,
                    le.getDimensions(le.getPose()).width());

            if (item.isIn(ItemTags.HEAD_ARMOR)){
                int count = new Random().nextInt(0,3);
                if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.DIAMOND){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY() * 3F,0F)),size,ModParticles.DIAMOND_SCRAP,count);
                    continue;
                }else {
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY() * 3F,0F)),size,ModParticles.NETHERITE_SCRAP,count);
                    continue;
                }
            }

            if (item.isIn(ItemTags.CHEST_ARMOR)){
                int count = new Random().nextInt(0,3);
                if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.DIAMOND){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY() * 2F,0F)),size,ModParticles.DIAMOND_SCRAP,count);
                    continue;
                }else {
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY() * 2F,0F)),size,ModParticles.NETHERITE_SCRAP,count);
                    continue;
                }
            }
            if (item.isIn(ItemTags.LEG_ARMOR)){
                int count = new Random().nextInt(0,3);
                if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.DIAMOND){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY(),0F)),size,ModParticles.DIAMOND_SCRAP,count);
                    continue;
                }else {
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos().add(new Vec3d(0F,size.getY(),0F)),size,ModParticles.NETHERITE_SCRAP,count);
                    continue;
                }
            }
            if (item.isIn(ItemTags.FOOT_ARMOR)){
                int count = new Random().nextInt(0,3);
                if (((ArmorItem)item.getItem()).getMaterial() == ArmorMaterials.DIAMOND){
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos(),size,ModParticles.DIAMOND_SCRAP,count);
                }else {
                    SpawnParticlesOnHitbox(le.getWorld(), le.getPos(),size,ModParticles.NETHERITE_SCRAP,count);
                }
            }
        }
    }
}
