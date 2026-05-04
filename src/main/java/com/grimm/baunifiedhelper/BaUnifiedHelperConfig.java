package com.grimm.baunifiedhelper;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("baunifiedhelper")
public interface BaUnifiedHelperConfig extends Config
{
	@ConfigSection(
		name = "General",
		description = "General BA Unified Helper settings.",
		position = 0
	)
	String generalSection = "general";

	@ConfigSection(
		name = "Detection",
		description = "Barbarian Assault area and state detection settings.",
		position = 1
	)
	String detectionSection = "detection";

	@ConfigSection(
		name = "NPCs",
		description = "NPC count and highlight settings.",
		position = 2
	)
	String npcSection = "npcs";

	@ConfigSection(
		name = "NPC Colors",
		description = "Colors used for Barbarian Assault NPC highlights.",
		position = 3
	)
	String npcColorsSection = "npcColors";

	@ConfigSection(
		name = "Testing",
		description = "Temporary testing tools for checking overlays outside Barbarian Assault.",
		position = 4
	)
	String testingSection = "testing";

	@ConfigItem(
		keyName = "showHud",
		name = "Show HUD",
		description = "Shows the BA Unified Helper overlay.",
		section = generalSection,
		position = 0
	)
	default boolean showHud()
	{
		return true;
	}

	@ConfigItem(
		keyName = "customBaRegionIds",
		name = "Custom BA Region IDs",
		description = "Comma-separated region IDs treated as Barbarian Assault areas. Useful while testing live BA detection.",
		section = detectionSection,
		position = 0
	)
	default String customBaRegionIds()
	{
		return "";
	}

	@ConfigItem(
		keyName = "showEnemyCounts",
		name = "Show Enemy Counts",
		description = "Shows Penance Fighter, Ranger, Runner, and Healer counts on the HUD.",
		section = npcSection,
		position = 0
	)
	default boolean showEnemyCounts()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightRoleNpcs",
		name = "Highlight Role NPCs",
		description = "Highlights NPCs relevant to your current or manually selected role.",
		section = npcSection,
		position = 1
	)
	default boolean highlightRoleNpcs()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showNpcLabels",
		name = "Show NPC Labels",
		description = "Shows small labels above highlighted Barbarian Assault NPCs.",
		section = npcSection,
		position = 2
	)
	default boolean showNpcLabels()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightAttackerNpcs",
		name = "Highlight Attacker NPCs",
		description = "Highlights Penance Fighters and Penance Rangers for the Attacker role.",
		section = npcSection,
		position = 3
	)
	default boolean highlightAttackerNpcs()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightDefenderNpcs",
		name = "Highlight Defender NPCs",
		description = "Highlights Penance Runners for the Defender role.",
		section = npcSection,
		position = 4
	)
	default boolean highlightDefenderNpcs()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightHealerNpcs",
		name = "Highlight Healer NPCs",
		description = "Highlights Penance Healers for the Healer role.",
		section = npcSection,
		position = 5
	)
	default boolean highlightHealerNpcs()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightAllBaNpcsWhenRoleUnknown",
		name = "Highlight All BA NPCs When Role Unknown",
		description = "Highlights all tracked Barbarian Assault NPCs when the role is unknown or set to Auto.",
		section = npcSection,
		position = 6
	)
	default boolean highlightAllBaNpcsWhenRoleUnknown()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "attackerNpcColor",
		name = "Attacker NPC Color",
		description = "Color used for Attacker role NPC highlights.",
		section = npcColorsSection,
		position = 0
	)
	default Color attackerNpcColor()
	{
		return new Color(255, 80, 80, 180);
	}

	@Alpha
	@ConfigItem(
		keyName = "defenderNpcColor",
		name = "Defender NPC Color",
		description = "Color used for Defender role NPC highlights.",
		section = npcColorsSection,
		position = 1
	)
	default Color defenderNpcColor()
	{
		return new Color(255, 190, 70, 180);
	}

	@Alpha
	@ConfigItem(
		keyName = "healerNpcColor",
		name = "Healer NPC Color",
		description = "Color used for Healer role NPC highlights.",
		section = npcColorsSection,
		position = 2
	)
	default Color healerNpcColor()
	{
		return new Color(80, 255, 120, 180);
	}

	@Alpha
	@ConfigItem(
		keyName = "unknownNpcColor",
		name = "Unknown Role NPC Color",
		description = "Fallback color used when the role is unknown.",
		section = npcColorsSection,
		position = 3
	)
	default Color unknownNpcColor()
	{
		return new Color(255, 255, 255, 140);
	}

	@Alpha
	@ConfigItem(
		keyName = "npcLabelColor",
		name = "NPC Label Color",
		description = "Color used for NPC labels.",
		section = npcColorsSection,
		position = 4
	)
	default Color npcLabelColor()
	{
		return Color.WHITE;
	}

	@ConfigItem(
		keyName = "debugMode",
		name = "Debug/Test Mode",
		description = "Shows test BA data so the overlay can be checked outside Barbarian Assault.",
		section = testingSection,
		position = 0
	)
	default boolean debugMode()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showDebugVisualSamples",
		name = "Show Debug Visual Samples",
		description = "Shows sample highlight boxes for testing NPC highlight colors outside Barbarian Assault.",
		section = testingSection,
		position = 1
	)
	default boolean showDebugVisualSamples()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showDebugDiagnostics",
		name = "Show Debug Diagnostics",
		description = "Shows region, location, and BA detection diagnostics while Debug/Test Mode is enabled.",
		section = testingSection,
		position = 2
	)
	default boolean showDebugDiagnostics()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showWidgetDiagnostics",
		name = "Show Widget Diagnostics",
		description = "Shows matching visible widget text/name entries while Debug/Test Mode is enabled.",
		section = testingSection,
		position = 3
	)
	default boolean showWidgetDiagnostics()
	{
		return false;
	}

	@ConfigItem(
		keyName = "widgetDiagnosticFilter",
		name = "Widget Diagnostic Filter",
		description = "Comma-separated text filters for visible widget diagnostics.",
		section = testingSection,
		position = 4
	)
	default String widgetDiagnosticFilter()
	{
		return "wave,call,horn,attack,defend,collect,heal,egg,tofu,crackers,worms,accurate,aggressive,controlled,defensive";
	}

	@Range(
		min = 1,
		max = 20
	)
	@ConfigItem(
		keyName = "maxWidgetDiagnosticLines",
		name = "Max Widget Diagnostic Lines",
		description = "Maximum number of widget diagnostic lines shown on the HUD.",
		section = testingSection,
		position = 5
	)
	default int maxWidgetDiagnosticLines()
	{
		return 8;
	}

	@ConfigItem(
		keyName = "manualRole",
		name = "Manual Role",
		description = "Overrides the displayed role while testing. Auto will use detected role later.",
		section = testingSection,
		position = 6
	)
	default BaManualRole manualRole()
	{
		return BaManualRole.AUTO;
	}
}