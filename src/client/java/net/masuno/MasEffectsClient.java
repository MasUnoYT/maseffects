package net.masuno;

import net.fabricmc.api.ClientModInitializer;
import net.masuno.config.MasConfig;
import net.masuno.events.EventManager;
import net.masuno.particles.ModParticles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MasEffectsClient implements ClientModInitializer {
	public static final String MOD_ID = "maseffects";

	public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		logger.debug("Mas Effects is loaded!");

		MasConfig.Register();
		ModParticles.Register();
		EventManager.registerEvents();
	}

}