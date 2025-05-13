package net.masuno.particles;

import com.sun.source.tree.LambdaExpressionTree;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.masuno.MasEffectsClient;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModParticles {
    //Registers mod particles


    //Mace hit shockwave
    public static final SimpleParticleType SMASH = FabricParticleTypes.simple();

    //Mace hit spark
    public static final SimpleParticleType FLICK = FabricParticleTypes.simple();

    //Mace hit flash
    public static final SimpleParticleType FLASH = FabricParticleTypes.simple();

    //Mace hit armor effect for diamond pieces
    public static final SimpleParticleType DIAMOND_SCRAP = FabricParticleTypes.simple();

    //Mace hit armor effect for netherite pieces
    public static final SimpleParticleType NETHERITE_SCRAP = FabricParticleTypes.simple();

    //Totem ring particle
    public static final SimpleParticleType REVIVE = FabricParticleTypes.simple();

    //Totem spark particle
    public static final SimpleParticleType REVIVE_SPARK = FabricParticleTypes.simple();

    //Totem spark particle
    public static final SimpleParticleType SHIELD_WAVE = FabricParticleTypes.simple();

    public static final SimpleParticleType DEATH_SPARK = FabricParticleTypes.simple();
    public static final SimpleParticleType DEATH_SKULL = FabricParticleTypes.simple();


    //Register each particle
    public static void Register(){

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "smash"), SMASH);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "flick"), FLICK);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "flash"), FLASH);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "diamond_scrap"), DIAMOND_SCRAP);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "netherite_scrap"), NETHERITE_SCRAP);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "revive"), REVIVE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "revive_spark"), REVIVE_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "shield_wave"), SHIELD_WAVE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "death_spark"), DEATH_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MasEffectsClient.MOD_ID, "death_skull"), DEATH_SKULL);

        ParticleFactoryRegistry.getInstance().register(SMASH, SmashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(FLICK, FlickParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(FLASH, FireworksSparkParticle.FlashFactory::new);
        ParticleFactoryRegistry.getInstance().register(DIAMOND_SCRAP, ScrapParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(NETHERITE_SCRAP, ScrapParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(REVIVE, ReviveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(REVIVE_SPARK, ReviveSparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SHIELD_WAVE, ShieldWave.Factory::new);
        ParticleFactoryRegistry.getInstance().register(DEATH_SPARK, DeathSpark.Factory::new);
        ParticleFactoryRegistry.getInstance().register(DEATH_SKULL, DeathSkull.Factory::new);
    }
}
