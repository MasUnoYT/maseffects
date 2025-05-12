package net.masuno.mixin;

import net.masuno.events.PlayerAttackManager;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleUtil.class)
public abstract class MaceMixin {
    @Inject(method = "spawnSmashAttackParticles",at = @At(value = "HEAD"))
    private static void MaceAttack(WorldAccess world, BlockPos pos, int count, CallbackInfo ci){
        Vec3d vec = pos.toCenterPos().add(0.0, 0.5, 0.0);
        PlayerAttackManager.SlamEffect(vec.getX(),vec.getY(),vec.getZ());
    }
}
