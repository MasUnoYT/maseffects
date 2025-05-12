package net.masuno.mixin;

import net.masuno.events.TotemEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class TotemMixin extends ClientCommonNetworkHandler implements ClientPlayPacketListener, TickablePacketListener {

    protected TotemMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Shadow
    private ClientWorld world;


    //Calls the totem effects whenever a player uses a totem
    @Inject(method = "onEntityStatus",at = @At(value = "HEAD"))
    private void onEntityTotem(EntityStatusS2CPacket packet, CallbackInfo ci){
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = packet.getEntity(this.world);
        if (entity != null) {
            switch (packet.getStatus()) {
                case 35:
                    TotemEvent.run(entity);
            }
        }
    }

}