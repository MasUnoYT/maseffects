package net.masuno.events;

import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.util.math.Vec3d;

public class EnderPearlTickEvent {
    public static void tick(EnderPearlEntity entity){
        if (MasConfig.INSTANCE.PearlTrailParticles){
            Vec3d motion = entity.getMovement().normalize().multiply(0.05F);
            for (int i = 0; i < 3; i ++){
                entity.getWorld().addParticle(ModParticles.ENDER_PEARL_TRAIL,entity.getX(),entity.getY(),entity.getZ(),
                        motion.getX(),
                        motion.getY(),
                        motion.getZ());
            }
        }
    }
}
