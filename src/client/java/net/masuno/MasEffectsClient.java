package net.masuno;

import net.fabricmc.api.ClientModInitializer;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;

import java.util.ArrayList;
import java.util.List;

public class MasEffectsClient implements ClientModInitializer {
	public static final String MOD_ID = "maseffects";
	public static List<String> MACE_SOUNDS = new ArrayList<>();

	@Override
	public void onInitializeClient() {
		MACE_SOUNDS.add("minecraft:item.mace.smash_air");
		MACE_SOUNDS.add("minecraft:item.mace.smash_ground");
		MACE_SOUNDS.add("minecraft:item.mace.smash_ground_heavy");
		MasConfig.Register();
		ModParticles.Register();
	}

}