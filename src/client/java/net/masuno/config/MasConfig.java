package net.masuno.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.masuno.events.PlayerAttackManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

public class MasConfig {

    private static KeyBinding ConfigKey;
    public static boolean MaceShockwave = true;
    public static float MaceShockwaveSize = 1F;
    public static boolean MaceSpark = true;
    public static boolean MaceFlash = true;
    public static boolean ShieldEffect = true;
    public static boolean ArmorParticles = true;
    public static boolean CustomTotemEffect = true;

    public static void Register(){
        //Register keybind to open the config menu
        ConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.maseffects.config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.maseffects"
        ));

        AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            PlayerAttackManager.clientAttack(playerEntity,entity,world);
            return ActionResult.PASS;
        });

        //Check for every tick if the key was pressed
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ConfigKey.wasPressed()) {
                //Open the config by setting the screen to ConfigScreen
                Screen configScreen = getConfigScreen();
                if (MinecraftClient.getInstance().currentScreen != configScreen) MinecraftClient.getInstance().setScreen(configScreen);
            }
        });

    }

    //Generate the config screen
    public static Screen getConfigScreen(){
        ConfigBuilder result = ConfigBuilder.create()
            .setParentScreen(MinecraftClient.getInstance().currentScreen)
            .setTitle(Text.translatable("title.maseffects.config"));

        ConfigCategory mace = result.getOrCreateCategory(Text.translatable("category.maseffects.mace"));

        ConfigEntryBuilder entryBuilder = result.entryBuilder();
        mace.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.maseffects.shockwave"),MaceShockwave)
                .setDefaultValue(true).setTooltip(Text.translatable("option.maseffects.shockwave_tooltip"))
                .setSaveConsumer(newValue -> MaceShockwave = newValue).build());

        mace.addEntry(entryBuilder.startFloatField(Text.translatable("option.maseffects.shockwave_size"),MaceShockwaveSize)
                .setDefaultValue(1F).setTooltip(Text.translatable("option.maseffects.shockwave_size_tooltip"))
                .setSaveConsumer(newValue -> MaceShockwaveSize = newValue).build());

        mace.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.maseffects.spark"),MaceSpark)
                .setDefaultValue(true).setTooltip(Text.translatable("option.maseffects.spark_tooltip"))
                .setSaveConsumer(newValue -> MaceSpark = newValue).build());

        mace.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.maseffects.flash"),MaceFlash)
                .setDefaultValue(true).setTooltip(Text.translatable("option.maseffects.flash_tooltip"))
                .setSaveConsumer(newValue -> MaceFlash = newValue).build());

        mace.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.maseffects.mace_shield"), ShieldEffect)
                .setDefaultValue(true).setTooltip(Text.translatable("option.maseffects.mace_shield_tooltip"))
                .setSaveConsumer(newValue -> ShieldEffect = newValue).build());

        mace.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.maseffects.armor"), ArmorParticles)
                .setDefaultValue(true).setTooltip(Text.translatable("option.maseffects.armor_tooltip"))
                .setSaveConsumer(newValue -> ArmorParticles = newValue).build());


        ConfigCategory generic = result.getOrCreateCategory(Text.translatable("category.maseffects.generic"));

        generic.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.maseffects.totem"), CustomTotemEffect)
                .setDefaultValue(true).setTooltip(Text.translatable("option.maseffects.totem_tooltip"))
                .setSaveConsumer(newValue -> CustomTotemEffect = newValue).build());


        return result.build();
    }

}
