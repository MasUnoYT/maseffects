package net.masuno.events;

import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.entity.Entity;

public class TotemEvent {
    //Runs the on totem code
    public static void run(Entity popper) {
        if (!MasConfig.CustomTotemEffect){
            return;
        }

        for(int i = 1; i < 18; i++){
            double scaler = 5F;
            popper.getWorld().addParticle(ModParticles.REVIVE,
                    popper.getX(),popper.getY() + popper.getDimensions(popper.getPose()).height() / 2F,popper.getZ(),
                    scaler,popper.getId(),0);
        }
        for(int i = 1; i < 100; i++){
            popper.getWorld().addParticle(ModParticles.REVIVE_SPARK,
                    popper.getX(),popper.getY() + popper.getDimensions(popper.getPose()).height() / 2F,popper.getZ(),
                    popper.getId(),0,0);
        }
    }

}
