package net.masuno.particles;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ShieldWave extends SpriteBillboardParticle {
    public ShieldWave(ClientWorld clientWorld, double x, double y, double z,
                         SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed ) {
        super(clientWorld, x, y, z,xSpeed,ySpeed,zSpeed);
        this.velocityMultiplier = 0F;
        this.maxAge = 30;
        this.scale = 0.3F;
        this.setSprite(spriteProvider);
    }

    private float scaler = 1F;

    @Override
    public void tick(){
        super.tick();
        this.scale += scaler * 0.2F;
        scaler -= 1F / this.maxAge;

        this.alpha -= 1F / this.maxAge;

        scaler = Math.clamp(scaler,0,1);
        this.alpha = Math.clamp(alpha,0,1);;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ShieldWave(world,x,y,z,this.spriteProvider,velocityX,velocityY,velocityZ);
        }
    }
}