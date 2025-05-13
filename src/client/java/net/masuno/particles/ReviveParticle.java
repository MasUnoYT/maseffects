package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MathUtility;
import net.masuno.config.MasConfig;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ReviveParticle extends SpriteBillboardParticle {
    //The orbit-like particle that spawns when a totem is used
    public ReviveParticle(ClientWorld clientWorld, double x, double y, double z,
                         SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed ) {
        super(clientWorld, x, y, z,xSpeed,ySpeed,zSpeed);
        this.maxAge = 20;
        this.alpha = 0f;
        this.scale = 0.2F;

        //Uses the xSpeed argument to pass the scale because I'm lazy.
        this.scaler = xSpeed;

        //Makes sure the particle doesn't move
        this.velocityMultiplier = 0;

        //Sets the rotation values to random numbers
        this.rotX = this.random.nextBetween(-180,180);
        this.rotY = this.random.nextBetween(-180,180);
        this.rotZ = this.random.nextBetween(-180,180);

        //Uses the ySpeed argument to pass the id of the entity in the client.
        Entity ent = clientWorld.getEntityById((int)ySpeed);

        //Checks that the entity is valid.
        if (ent != null){
            if (ent instanceof LivingEntity){

                //Sets the target to the entity.
                this.target = (LivingEntity) ent;
            }
        }


        //Sets the color to either green or yellow (randomly).
        if (this.random.nextBoolean()){
            this.setColor(1,1F,0F);
        }else {
            this.setColor(0F,1F,0F);
        }
        this.spriteProv = spriteProvider;
        this.setSprite(spriteProv.getSprite(age,maxAge));

    }
    private final SpriteProvider spriteProv;

    private final double scaler; //Controls the math behind the scale

    //Rotation values
    private final double rotX;
    private final double rotZ;
    private double rotY;

    private LivingEntity target = null; //The entity that used the totem

    private Quaternionf QUATERNION = new Quaternionf(0F, -0.7F, 0.7F, 0F); //Rotates the particle based on this value
    @Override
    public void buildGeometry(VertexConsumer buffer, Camera camera, float ticks){

        //Displays the entity in the "QUATERNION" rotation instead of using the billboard

        Vec3d vec3 = camera.getPos();
        float x = (float) (MathHelper.lerp(ticks, this.prevPosX, this.x) - vec3.getX());
        float y = (float) (MathHelper.lerp(ticks, this.prevPosY, this.y) - vec3.getY());
        float z = (float) (MathHelper.lerp(ticks, this.prevPosZ, this.z) - vec3.getZ());

        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        Vector3f[] vector3fsBottom = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F)};

        float f4 = this.getSize(ticks);

        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = vector3fs[i];
            vector3f.rotate(QUATERNION);
            vector3f.mul(f4);
            vector3f.add(x, y, z);
            Vector3f vector3fBottom = vector3fsBottom[i];
            vector3fBottom.rotate(QUATERNION);
            vector3fBottom.mul(f4);
            vector3fBottom.add(x, y - 0.1F, z);
        }

        float f7 = this.getMinU();
        float f8 = this.getMaxU();
        float f5 = this.getMinV();
        float f6 = this.getMaxV();
        int light = this.getBrightness(ticks);

        buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light);

        buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
    }
    @Override
    public void tick(){
        super.tick();

        this.setSprite(spriteProv.getSprite(age,maxAge));
        //Moves to the entity that used the totem (If it moved)
        if(target != null){
            this.setPos(target.getX(),target.getY() + target.getDimensions(target.getPose()).height() / 2F,target.getZ());
        }
        if (MasConfig.INSTANCE.TotemEffectOpacity <= 0) return;


        //Increases the rotY value (used for the orbit rotation effect)
        this.rotY += 20F;

        //Sets the particle rotation to just the orbit rotation
        QUATERNION = MathUtility.euler(0F,0,(float) rotY);

        //Offsets the rotation so its random while keeping the orbit rotation effect
        QUATERNION = MathUtility.euler((float) rotZ, (float) rotX, (float) -rotZ).mul(QUATERNION);

        //Scales the particle up
        this.scale = (this.alpha / MasConfig.INSTANCE.TotemEffectOpacity) * (float) this.scaler;

        //Sets the particles opacity
        this.alpha = Math.clamp((float)Math.sqrt(Math.sin (((double) age / maxAge) * Math.PI))/ 1.2F,0,1F) * MasConfig.INSTANCE.TotemEffectOpacity;

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
            return new ReviveParticle(world,x,y,z,this.spriteProvider,velocityX,velocityY,velocityZ);
        }
    }

}