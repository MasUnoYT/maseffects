package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class DeathSpark extends SpriteBillboardParticle {

    public DeathSpark(ClientWorld clientWorld, double x, double y, double z,
                      SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed ) {
        super(clientWorld, x, y, z,xSpeed,ySpeed,zSpeed);
        this.gravityStrength = -0.1F;
        this.collidesWithWorld = false;
        this.velocityMultiplier = 1F;
        this.velocityY = 0;
        this.scale = this.random.nextBetween(5,10) * 0.06F;
        this.velocityX = this.random.nextBetween(-10,10) * 0.02F;
        this.velocityZ = this.random.nextBetween(-10,10) * 0.02F;
        this.maxAge = 20;
        this.alpha = 1F;
        this.spriteProv = spriteProvider;
        this.setSprite(spriteProv.getSprite(age,maxAge));
    }
    private final SpriteProvider spriteProv;
    @Override
    public void tick(){
        super.tick();
        this.setSprite(spriteProv.getSprite(age,maxAge));
        this.alpha = (1F - ((float) this.age / this.maxAge)) * 0.45F;
        this.velocityX *= 0.9F;
        this.velocityZ *= 0.9F;
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
            return new DeathSpark(world,x,y,z,this.spriteProvider,velocityX,velocityY,velocityZ);
        }
    }
}