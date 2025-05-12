package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class FlickParticle extends SpriteBillboardParticle {
    //The flick particle that spawns when you slam with the mace
    public FlickParticle(ClientWorld clientWorld, double x, double y, double z,
                         SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed ) {
        super(clientWorld, x, y, z,xSpeed,ySpeed,zSpeed);
        this.alpha = 1F;
        this.scaler = (float) xSpeed;
        this.maxAge = 20;
        this.time = 0F;
        this.velocityMultiplier = 0;
        this.setSprite(spriteProvider);

    }


    public float time; //Controls the math behind the particles opacity (alpha)
    public float scaler; //Controls the math behind the particle scale

    @Override
    public void tick(){
        super.tick();
        this.alpha = Math.clamp((1F - this.time) * 1.5F,0.2F,1) - 0.2F;
        this.time += 1F / this.maxAge;
        this.scale = this.alpha * this.scaler;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
    public static class Factory implements  ParticleFactory<SimpleParticleType>{
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FlickParticle(world,x,y,z,this.spriteProvider,velocityX,velocityY,velocityZ);
        }
    }
}