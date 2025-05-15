package net.masuno.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import me.shedaniel.math.Color;

import java.util.ArrayList;
import java.util.List;

@Config(name = "mas_config")
public class MasConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static MasConfig INSTANCE;

    public static void init()
    {
        AutoConfig.register(MasConfig.class, JanksonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(MasConfig.class).getConfig();
    }

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "mace")
    @Comment("If enabled, creates a shockwave effect when slamming with the mace")
    public boolean MaceShockwave = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "mace")
    @Comment("Increases the size of the mace shockwave")
    public float MaceShockwaveSize = 1F;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "mace")
    @Comment("Sets the opacity of the mace shockwave")
    public float MaceShockwaveOpacity = 1F;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "mace")
    @Comment("Creates spark particles when slamming with the mace")
    public boolean MaceSpark = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "mace")
    @Comment("Creates flash upon slamming with the mace")
    public boolean MaceFlash = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "mace")
    @Comment("Creates a small shockwave on a player´s shield when blocking mace attacks")
    public boolean ShieldEffect = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "mace")
    @Comment("If enabled spawns armor breaking particles when using the breach enchantment")
    public boolean ArmorParticles = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "default")
    @Comment("If enabled changes the totem particle effect completely")
    public boolean CustomTotemEffect = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "default")
    @Comment("Changes the opacity of the totem effect")
    public float TotemEffectOpacity = 1.0F;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "default")
    @Comment("Spawns custom death particles when a player dies")
    public boolean PlayerDeathEffect = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "default")
    @Comment("Makes ender pearls spawn a trail of particles")
    public boolean PearlTrailParticles = true;


    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "default")
    @Comment("Changes the opacity of the pearl trail")
    public float PearlTrailOpacity = 0.5F;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Only players and ender pearls will show their hitboxes, player hitboxes will fade from afar and pearls can have different colors!")
    public boolean CustomHitbox = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Change the ender pearl hitbox color depending on who threw it")
    public boolean PearlHitboxColors = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Pearls thrown by these players will be colored green instead of red")
    public List<String> PearlWhiteList = new ArrayList<>();

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Color of your own pearl hitbox")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int SelfPearlColor = 0xB3ffff00;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Color of your allies pearl hitboxes")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int AllyPearlColor = 0xB30000ff;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Color of other player's pearl hitboxes")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int OtherPearlColor = 0xB3ff0000;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Opacity of other mob's hitboxes")
    public float MobPearlHitboxOpacity = 0.3F;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Opacity of player's hitboxes")
    public float PlayerPearlHitboxOpacity = 0.8F;


    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Distance in blocks where the hitbox starts to fade for mobs and players")
    public float HitboxFadeDistance = 15F;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category(value = "hitbox")
    @Comment("Distance in blocks where the hitbox starts to appear for pearls and wind charges")
    public float HitboxProjectileFadeDistance = 5F;
}