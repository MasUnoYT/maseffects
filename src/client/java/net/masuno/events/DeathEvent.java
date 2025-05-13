package net.masuno.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.text.Text;

public class DeathEvent {
    public static void runDeath(AbstractClientPlayerEntity player){
        MinecraftClient.getInstance().player.sendMessage(Text.of(player.getNameForScoreboard() + " sa matao"));
    }

}
