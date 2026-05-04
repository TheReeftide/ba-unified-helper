package com.grimm.baunifiedhelper;

import com.google.inject.Provides;
import com.grimm.baunifiedhelper.overlay.BaDebugVisualOverlay;
import com.grimm.baunifiedhelper.overlay.BaHudOverlay;
import com.grimm.baunifiedhelper.overlay.BaNpcOverlay;
import com.grimm.baunifiedhelper.tracker.BaAreaTracker;
import com.grimm.baunifiedhelper.tracker.BaNpcTracker;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "BA Unified Helper",
	description = "A unified Barbarian Assault helper plugin with calls, timers, labels, and role-based highlights.",
	tags = {"barbarian assault", "ba", "minigame", "overlay", "helper"}
)
public class BaUnifiedHelperPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BaUnifiedHelperConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private BaHudOverlay hudOverlay;

	@Inject
	private BaNpcOverlay npcOverlay;

	@Inject
	private BaDebugVisualOverlay debugVisualOverlay;

	private final BaWaveState waveState = new BaWaveState();

	private BaNpcTracker npcTracker;
	private BaAreaTracker areaTracker;
	private int debugTicks;

	@Override
	protected void startUp()
	{
		npcTracker = new BaNpcTracker(client);
		areaTracker = new BaAreaTracker(client);

		overlayManager.add(hudOverlay);
		overlayManager.add(npcOverlay);
		overlayManager.add(debugVisualOverlay);

		log.info("BA Unified Helper started");
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(hudOverlay);
		overlayManager.remove(npcOverlay);
		overlayManager.remove(debugVisualOverlay);

		waveState.reset();
		debugTicks = 0;
		npcTracker = null;
		areaTracker = null;

		log.info("BA Unified Helper stopped");
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		updateLocationSnapshot();

		if (config.debugMode())
		{
			debugTicks++;
			waveState.setDebugValues(getActiveRole(), debugTicks);
			updateLocationSnapshot();
			return;
		}

		debugTicks = 0;

		if (npcTracker == null || areaTracker == null)
		{
			waveState.reset();
			return;
		}

		boolean foundBaNpcs = npcTracker.updateNpcCounts(waveState);
		boolean inConfiguredRegion = areaTracker.isInConfiguredBaRegion(config.customBaRegionIds());
		boolean inBa = foundBaNpcs || inConfiguredRegion;
		String detectionReason = areaTracker.getDetectionReason(foundBaNpcs, inConfiguredRegion);

		waveState.updateLiveState(inBa, getActiveRole(), detectionReason);
		updateLocationSnapshot();

		// TODO: Add known BA regions once confirmed during live testing.
		// TODO: Replace/add widget-based role and wave detection.
		// TODO: v0.2 call tracking and call-change timer.
		// TODO: v0.3 ground item labels/highlights.
		// TODO: v0.4 inventory/spellbook/widget highlights.
		// TODO: v0.5 cannon ammo overlay.
	}

	private void updateLocationSnapshot()
	{
		if (areaTracker == null)
		{
			return;
		}

		waveState.setLocationSnapshot(
			areaTracker.getCurrentRegionId(),
			areaTracker.getWorldX(),
			areaTracker.getWorldY(),
			areaTracker.getPlane()
		);
	}

	public BaRole getActiveRole()
	{
		BaManualRole manualRole = config.manualRole();

		if (manualRole == null || manualRole == BaManualRole.AUTO)
		{
			return BaRole.UNKNOWN;
		}

		return manualRole.toBaRole();
	}

	public BaWaveState getWaveState()
	{
		return waveState;
	}

	@Provides
	BaUnifiedHelperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BaUnifiedHelperConfig.class);
	}
}