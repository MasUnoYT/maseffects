package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ReviveSparkParticle extends SpriteBillboardParticle {
    //The sparks particles the totem throws all around
    public ReviveSparkParticle (ClientWorld clientWorld, double x, double y, double z,
                          SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed ) {
        super(clientWorld, x, y, z,xSpeed,ySpeed,zSpeed);

        //Sets the length of the particle to be random
        this.maxAge = this.random.nextBetween(30,48);
        this.alpha = MasConfig.INSTANCE.TotemEffectOpacity;
        this.scale = 0.1F;

        //Sets the velocity of the particle to be random
        this.velocityX = this.random.nextBetween(-10,10) / 25F;
        this.velocityY = this.random.nextBetween(-10,10) / 25F;
        this.velocityZ = this.random.nextBetween(-10,10) / 25F;
        this.velocityMultiplier = 1F;
        this.collidesWithWorld = true;
        this.setSprite(spriteProvider);

        //Sets the entity that used the totem (similar to ReviveParticle)
        Entity ent = clientWorld.getEntityById((int)xSpeed);
        if (ent != null){
            if (ent instanceof LivingEntity){
                this.target = (LivingEntity) ent;
            }
        }


        //Sets the color to either green or yellow (randomly).
        if (this.random.nextBoolean()){
            this.setColor(0F,1F,0F);
        }else {
            this.setColor(1F,1F,0F);
        }
    }

    private LivingEntity target = null;
    private Vec3d targetDir ;
    @Override
    public void tick(){
        super.tick();

        //if the entity was set successfully, set a target position and direction vector
        if(target != null){
            targetDir = new Vec3d(target.getX() - x,target.getY() + target.getDimensions(target.getPose()).height() / 2F - y,target.getZ() - z);
            targetDir = targetDir.normalize();
        }

        //if the particle has been up for long enough, then stop it
        if (age >= maxAge / 5F && age < maxAge / 4F){
            this.velocityMultiplier = 0;
        }
        if(target != null){
            //after it stops, if the target is not null, move the particle towards the entity using the target direction variable
            if (age >= maxAge / 4F && target.getPos().distanceTo(new Vec3d(x,y,z)) < 20){

                alpha = Math.clamp(1F - ((age - (maxAge / 1.5F)) / 20F),0,1) * MasConfig.INSTANCE.TotemEffectOpacity;

                this.velocityMultiplier = 1F;
                velocityX = targetDir.x * ((age - 15) * 0.05F);
                velocityY = targetDir.y * ((age - 15) * 0.05F);
                velocityZ = targetDir.z * ((age - 15) * 0.05F);

                //if the particle has reached the player, hide it and kill it until it disappears
                if (target.getPos().distanceTo(new Vec3d(x,y,z)) < 2F){
                    alpha = 0F;
                    dead = true;
                }
            }
        }
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
            return new ReviveSparkParticle(world,x,y,z,this.spriteProvider,velocityX,velocityY,velocityZ);
        }
    }
}