package com.grimm.baunifiedhelper.overlay;

import com.grimm.baunifiedhelper.BaRole;
import com.grimm.baunifiedhelper.BaUnifiedHelperConfig;
import com.grimm.baunifiedhelper.BaUnifiedHelperPlugin;
import com.grimm.baunifiedhelper.data.BaNpcData;
import com.grimm.baunifiedhelper.data.BaNpcType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class BaNpcOverlay extends Overlay
{
	private final Client client;
	private final BaUnifiedHelperPlugin plugin;
	private final BaUnifiedHelperConfig config;

	@Inject
	public BaNpcOverlay(Client client, BaUnifiedHelperPlugin plugin, BaUnifiedHelperConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;

		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.highlightRoleNpcs())
		{
			return null;
		}

		BaRole role = plugin.getActiveRole();

		if (!plugin.getWaveState().isInBa() && !config.debugMode())
		{
			return null;
		}

		for (NPC npc : client.getNpcs())
		{
			if (npc == null || npc.getName() == null || npc.isDead())
			{
				continue;
			}

			BaNpcType type = BaNpcData.getNpcType(npc);

			if (type == BaNpcType.OTHER)
			{
				continue;
			}

			if (!shouldHighlight(role, type))
			{
				continue;
			}

			Color highlightColor = getHighlightColor(role, type);
			String label = getLabel(role, type);

			renderNpcHighlight(graphics, npc, highlightColor);

			if (config.showNpcLabels())
			{
				renderNpcLabel(graphics, npc, label);
			}
		}

		return null;
	}

	private boolean shouldHighlight(BaRole role, BaNpcType type)
	{
		switch (role)
		{
			case ATTACKER:
				return config.highlightAttackerNpcs()
					&& (type == BaNpcType.FIGHTER || type == BaNpcType.RANGER);

			case DEFENDER:
				return config.highlightDefenderNpcs()
					&& type == BaNpcType.RUNNER;

			case HEALER:
				return config.highlightHealerNpcs()
					&& type == BaNpcType.HEALER;

			case COLLECTOR:
				return false;

			case UNKNOWN:
			default:
				return config.highlightAllBaNpcsWhenRoleUnknown();
		}
	}

	private Color getHighlightColor(BaRole role, BaNpcType type)
	{
		switch (role)
		{
			case ATTACKER:
				return config.attackerNpcColor();

			case DEFENDER:
				return config.defenderNpcColor();

			case HEALER:
				return config.healerNpcColor();

			case COLLECTOR:
				return config.unknownNpcColor();

			case UNKNOWN:
			default:
				switch (type)
				{
					case FIGHTER:
					case RANGER:
						return config.attackerNpcColor();

					case RUNNER:
						return config.defenderNpcColor();

					case HEALER:
						return config.healerNpcColor();

					case OTHER:
					default:
						return config.unknownNpcColor();
				}
		}
	}

	private String getLabel(BaRole role, BaNpcType type)
	{
		if (role == BaRole.ATTACKER)
		{
			switch (type)
			{
				case FIGHTER:
					return "Fighter";
				case RANGER:
					return "Ranger";
				default:
					return "Target";
			}
		}

		if (role == BaRole.DEFENDER && type == BaNpcType.RUNNER)
		{
			return "Runner";
		}

		if (role == BaRole.HEALER && type == BaNpcType.HEALER)
		{
			return "Healer";
		}

		switch (type)
		{
			case FIGHTER:
				return "Fighter";
			case RANGER:
				return "Ranger";
			case RUNNER:
				return "Runner";
			case HEALER:
				return "Healer";
			case OTHER:
			default:
				return "BA NPC";
		}
	}

	private void renderNpcHighlight(Graphics2D graphics, NPC npc, Color color)
	{
		Shape hull = npc.getConvexHull();

		if (hull != null)
		{
			OverlayUtil.renderPolygon(graphics, hull, color);
		}
	}

	private void renderNpcLabel(Graphics2D graphics, NPC npc, String label)
	{
		Point textLocation = Perspective.getCanvasTextLocation(
			client,
			graphics,
			npc.getLocalLocation(),
			label,
			npc.getLogicalHeight() + 40
		);

		if (textLocation != null)
		{
			OverlayUtil.renderTextLocation(graphics, textLocation, label, config.npcLabelColor());
		}
	}
}