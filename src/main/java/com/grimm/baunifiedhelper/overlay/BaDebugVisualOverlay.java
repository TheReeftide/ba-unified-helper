package com.grimm.baunifiedhelper.overlay;

import com.grimm.baunifiedhelper.BaUnifiedHelperConfig;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class BaDebugVisualOverlay extends Overlay
{
	private final BaUnifiedHelperConfig config;

	@Inject
	public BaDebugVisualOverlay(BaUnifiedHelperConfig config)
	{
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.debugMode() || !config.showDebugVisualSamples())
		{
			return null;
		}

		int x = 350;
		int y = 120;
		int width = 150;
		int height = 34;
		int gap = 12;

		drawSample(graphics, x, y, width, height, "Attacker: Fighter", config.attackerNpcColor());
		y += height + gap;

		drawSample(graphics, x, y, width, height, "Attacker: Ranger", config.attackerNpcColor());
		y += height + gap;

		drawSample(graphics, x, y, width, height, "Defender: Runner", config.defenderNpcColor());
		y += height + gap;

		drawSample(graphics, x, y, width, height, "Healer: Healer", config.healerNpcColor());
		y += height + gap;

		drawSample(graphics, x, y, width, height, "Unknown: BA NPC", config.unknownNpcColor());

		return null;
	}

	private void drawSample(Graphics2D graphics, int x, int y, int width, int height, String label, Color color)
	{
		Rectangle rectangle = new Rectangle(x, y, width, height);

		Color fill = new Color(
			color.getRed(),
			color.getGreen(),
			color.getBlue(),
			Math.min(color.getAlpha(), 90)
		);

		graphics.setColor(fill);
		graphics.fill(rectangle);

		graphics.setStroke(new BasicStroke(2));
		graphics.setColor(color);
		graphics.draw(rectangle);

		if (config.showNpcLabels())
		{
			FontMetrics metrics = graphics.getFontMetrics();
			int textX = x + 8;
			int textY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();

			graphics.setColor(Color.BLACK);
			graphics.drawString(label, textX + 1, textY + 1);

			graphics.setColor(config.npcLabelColor());
			graphics.drawString(label, textX, textY);
		}
	}
}