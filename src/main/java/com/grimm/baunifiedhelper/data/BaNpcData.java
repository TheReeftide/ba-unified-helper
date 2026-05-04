package com.grimm.baunifiedhelper.data;

import net.runelite.api.NPC;

public final class BaNpcData
{
	private BaNpcData()
	{
	}

	public static BaNpcType getNpcType(NPC npc)
	{
		if (npc == null || npc.getName() == null)
		{
			return BaNpcType.OTHER;
		}

		String name = npc.getName();

		switch (name)
		{
			case "Penance Fighter":
				return BaNpcType.FIGHTER;
			case "Penance Ranger":
				return BaNpcType.RANGER;
			case "Penance Runner":
				return BaNpcType.RUNNER;
			case "Penance Healer":
				return BaNpcType.HEALER;
			default:
				return BaNpcType.OTHER;
		}
	}

	public static boolean isBaNpc(NPC npc)
	{
		return getNpcType(npc) != BaNpcType.OTHER;
	}
}