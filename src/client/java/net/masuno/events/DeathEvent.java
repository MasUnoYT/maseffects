package net.masuno.events;

import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class DeathEvent {
    public static void runDeath(AbstractClientPlayerEntity player){
        if (MasConfig.INSTANCE.PlayerDeathEffect){
            Vec3d pos = player.getPos();
            player.clientWorld.addParticle(ModParticles.DEATH_SKULL,pos.getX(),pos.getY(),pos.getZ(),0,0,0);

            for (int i = 0; i < 30; i ++){
                player.clientWorld.addParticle(ModParticles.DEATH_SPARK,pos.getX(),pos.getY(),pos.getZ(),0,0,0);
            }
        }
    }
}
