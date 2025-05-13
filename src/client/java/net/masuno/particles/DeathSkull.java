package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class DeathSkull extends SpriteBillboardParticle {

    public DeathSkull(ClientWorld clientWorld, double x, double y, double z,
                      SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed ) {
        super(clientWorld, x, y, z,xSpeed,ySpeed,zSpeed);
        this.gravityStrength = 0F;
        this.collidesWithWorld = false;
        this.velocityY = 0.2;
        this.scale = 1F;
        this.velocityX = 0;
        this.velocityZ = 0;
        this.maxAge = 60;
        this.alpha = 1F;
        this.spriteProv = spriteProvider;
        this.setSprite(spriteProv.getSprite(age,maxAge));
    }
    private final SpriteProvider spriteProv;
    @Override
    public void tick(){
        super.tick();
        this.setSprite(spriteProv.getSprite(Math.clamp(age,0,10),10));
        if (age > 30){
            alpha -= 1F / 30;
        }
        this.velocityY *= 0.95F;
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
            return new DeathSkull(world,x,y,z,this.spriteProvider,velocityX,velocityY,velocityZ);
        }
    }
}