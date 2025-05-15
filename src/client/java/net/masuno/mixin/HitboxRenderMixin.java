package net.masuno.mixin;

import net.masuno.config.MasConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(EntityRenderDispatcher.class)
public class HitboxRenderMixin {
    @Inject(method = "renderHitbox",at = @At("HEAD"),cancellable = true)
    private static void renderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, float red, float green, float blue, CallbackInfo ci){
        boolean isMob = true;
        if (!MasConfig.INSTANCE.CustomHitbox) return;

        Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());

        //Hitbox is from player
        if(MinecraftClient.getInstance().player == null) return;
        if (entity.isPlayer()){
            isMob = false;
            float distance = (float)Math.clamp((MinecraftClient.getInstance().player.getPos().distanceTo(entity.getPos()) - MasConfig.INSTANCE.HitboxFadeDistance) / 20F,0D,1D);
            WorldRenderer.drawBox(matrices,vertices,box,1F,1F,1F,(1F - distance) *MasConfig.INSTANCE.PlayerPearlHitboxOpacity);
        }

        //Hitbox is from pearl
        if (entity instanceof EnderPearlEntity pe){
            isMob = false;
            float distance = (float)Math.clamp((MinecraftClient.getInstance().player.getPos().distanceTo(entity.getPos()) - MasConfig.INSTANCE.HitboxProjectileFadeDistance) / 20F,0D,1D);
            if (pe.getOwner() != null && !MasConfig.INSTANCE.PearlHitboxColors){
                if (pe.getOwner().getUuid() == MinecraftClient.getInstance().player.getUuid()){
                    //Pearl is mine
                    Color c = Color.decode(Integer.toHexString(MasConfig.INSTANCE.SelfPearlColor));
                    WorldRenderer.drawBox(matrices,vertices,box,c.getRed() / 255F,c.getGreen()/ 255F,c.getBlue() / 255F,distance);

                }else if(MasConfig.INSTANCE.PearlWhiteList.contains(pe.getOwner().getNameForScoreboard())){
                    //Pearl is from whitelisted player
                    Color c = Color.decode(Integer.toHexString(MasConfig.INSTANCE.AllyPearlColor));
                    WorldRenderer.drawBox(matrices,vertices,box,c.getRed() / 255F,c.getGreen()/ 255F,c.getBlue() / 255F,distance);
                }
                else {
                    //Pearl is from non-whitelisted player
                    Color c = Color.decode(Integer.toHexString(MasConfig.INSTANCE.OtherPearlColor));
                    WorldRenderer.drawBox(matrices,vertices,box,c.getRed() / 255F,c.getGreen()/ 255F,c.getBlue() / 255F,distance);
                }
            }else {
                WorldRenderer.drawBox(matrices,vertices,box,1F,1F,0,distance);
            }
        }

        //Hitbox is from wind charge
        if (entity instanceof WindChargeEntity){
            isMob = false;
            float distance = (float)Math.clamp((MinecraftClient.getInstance().player.getPos().distanceTo(entity.getPos()) - MasConfig.INSTANCE.HitboxProjectileFadeDistance) / 20F,0D,1D);
            WorldRenderer.drawBox(matrices,vertices,box,0.9F,0.9F,1F,distance);
        }

        if (entity instanceof LivingEntity && isMob){
            float distance = (float)Math.clamp((MinecraftClient.getInstance().player.getPos().distanceTo(entity.getPos()) - MasConfig.INSTANCE.HitboxFadeDistance) / 20F,0D,1D);
            WorldRenderer.drawBox(matrices,vertices,box,1F,1F,1F,(1F - distance) * MasConfig.INSTANCE.MobPearlHitboxOpacity);
        }

        ci.cancel();
    }
}
