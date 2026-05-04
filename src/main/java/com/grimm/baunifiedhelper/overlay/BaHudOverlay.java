package com.grimm.baunifiedhelper.overlay;

import com.grimm.baunifiedhelper.BaUnifiedHelperConfig;
import com.grimm.baunifiedhelper.BaUnifiedHelperPlugin;
import com.grimm.baunifiedhelper.BaWaveState;
import com.grimm.baunifiedhelper.tracker.BaWidgetDebugEntry;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class BaHudOverlay extends OverlayPanel
{
	private final BaUnifiedHelperPlugin plugin;
	private final BaUnifiedHelperConfig config;

	@Inject
	public BaHudOverlay(BaUnifiedHelperPlugin plugin, BaUnifiedHelperConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showHud())
		{
			return null;
		}

		BaWaveState state = plugin.getWaveState();

		if (!state.isInBa() && !config.debugMode())
		{
			return null;
		}

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("BA Unified Helper")
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Mode")
			.right(state.isDebugMode() ? "Debug/Test" : "Live")
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Role")
			.right(state.getRole().getDisplayName())
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Wave")
			.right(state.getWaveNumber() <= 0 ? "-" : Integer.toString(state.getWaveNumber()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Timer")
			.right(formatTicks(state.getWaveTicks()))
			.build());

		if (config.showEnemyCounts())
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Fighters")
				.right(Integer.toString(state.getFightersAlive()))
				.build());

			panelComponent.getChildren().add(LineComponent.builder()
				.left("Rangers")
				.right(Integer.toString(state.getRangersAlive()))
				.build());

			panelComponent.getChildren().add(LineComponent.builder()
				.left("Runners")
				.right(Integer.toString(state.getRunnersAlive()))
				.build());

			panelComponent.getChildren().add(LineComponent.builder()
				.left("Healers")
				.right(Integer.toString(state.getHealersAlive()))
				.build());
		}

		if (config.debugMode() && config.showDebugDiagnostics())
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Detection")
				.right(state.getDetectionReason())
				.build());

			panelComponent.getChildren().add(LineComponent.builder()
				.left("Region")
				.right(Integer.toString(state.getRegionId()))
				.build());

			panelComponent.getChildren().add(LineComponent.builder()
				.left("World")
				.right(state.getWorldX() + ", " + state.getWorldY() + ", " + state.getPlane())
				.build());
		}

		if (config.debugMode() && config.showWidgetDiagnostics())
		{
			panelComponent.getChildren().add(TitleComponent.builder()
				.text("Widget Diagnostics")
				.build());

			if (plugin.getWidgetDebugEntries().isEmpty())
			{
				panelComponent.getChildren().add(LineComponent.builder()
					.left("Matches")
					.right("None")
					.build());
			}
			else
			{
				for (BaWidgetDebugEntry entry : plugin.getWidgetDebugEntries())
				{
					panelComponent.getChildren().add(LineComponent.builder()
						.left(Integer.toString(entry.getWidgetId()))
						.right(entry.getSource() + ": " + shorten(entry.getValue(), 28))
						.build());
				}
			}
		}

		return super.render(graphics);
	}

	private String formatTicks(int ticks)
	{
		int totalSeconds = (int) Math.floor(ticks * 0.6);
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}

	private String shorten(String value, int maxLength)
	{
		if (value == null)
		{
			return "";
		}

		if (value.length() <= maxLength)
		{
			return value;
		}

		return value.substring(0, Math.max(0, maxLength - 3)) + "...";
	}
}