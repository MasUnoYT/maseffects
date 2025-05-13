package net.masuno.particles;

import com.ibm.icu.impl.number.MultiplierFormatHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class EnderPearlTrail extends SpriteBillboardParticle {
    public EnderPearlTrail(ClientWorld clientWorld, double x, double y, double z,
                           SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed ) {
        super(clientWorld, x, y, z,xSpeed,ySpeed,zSpeed);
        this.gravityStrength = 0F;
        this.velocityX = xSpeed + (this.random.nextBetween(-10,10) * 0.005F);
        this.velocityY = ySpeed + (this.random.nextBetween(-10,10) * 0.005F);
        this.velocityZ = zSpeed + (this.random.nextBetween(-10,10) * 0.005F);
        this.maxAge = 10;
        this.spriteProv = spriteProvider;
        this.setSprite(spriteProv.getSprite(age,maxAge));

        assert MinecraftClient.getInstance().player != null;
        if (MinecraftClient.getInstance().player.getPos().distanceTo(new Vec3d(x,y,z)) < 6){
            this.alpha_ctrl -= 0.8F;
        }
        this.alpha = alpha_ctrl * MasConfig.INSTANCE.PearlTrailOpacity;
    }
    private final SpriteProvider spriteProv;
    private float alpha_ctrl = 1F;
    @Override
    public void tick(){
        super.tick();
        this.setSprite(spriteProv.getSprite(age,maxAge));
        this.scale = (1F - ((float) this.age / this.maxAge)) * 0.15F;
        this.alpha_ctrl -= 0.1F;
        assert MinecraftClient.getInstance().player != null;
        if (MinecraftClient.getInstance().player.getPos().distanceTo(new Vec3d(x,y,z)) < 6){
            this.alpha_ctrl -= 0.8F;
        }
        this.alpha = alpha_ctrl * MasConfig.INSTANCE.PearlTrailOpacity;
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
            return new EnderPearlTrail(world,x,y,z,this.spriteProvider,velocityX,velocityY,velocityZ);
        }
    }
}