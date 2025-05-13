package net.masuno.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

import java.util.List;

public class EventManager {
    public static void registerEvents(){

        AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            PlayerAttackManager.clientAttack(playerEntity,entity,world);
            return ActionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {

            if (minecraftClient.world == null) return;
            if (minecraftClient.world.getPlayers() == null) return;

            List<AbstractClientPlayerEntity> players = minecraftClient.world.getPlayers();

            for(AbstractClientPlayerEntity player : players){
                if (player.deathTime == 1){
                    DeathEvent.runDeath(player);
                }
            }
        });
    }
}
