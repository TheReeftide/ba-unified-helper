package com.grimm.baunifiedhelper.tracker;

import com.grimm.baunifiedhelper.BaWaveState;
import com.grimm.baunifiedhelper.data.BaNpcData;
import com.grimm.baunifiedhelper.data.BaNpcType;
import net.runelite.api.Client;
import net.runelite.api.NPC;

public class BaNpcTracker
{
	private final Client client;

	public BaNpcTracker(Client client)
	{
		this.client = client;
	}

	public boolean updateNpcCounts(BaWaveState waveState)
	{
		int fighters = 0;
		int rangers = 0;
		int runners = 0;
		int healers = 0;
		boolean foundAnyBaNpc = false;

		for (NPC npc : client.getNpcs())
		{
			if (npc == null || npc.getName() == null || npc.isDead())
			{
				continue;
			}

			BaNpcType type = BaNpcData.getNpcType(npc);

			switch (type)
			{
				case FIGHTER:
					fighters++;
					foundAnyBaNpc = true;
					break;
				case RANGER:
					rangers++;
					foundAnyBaNpc = true;
					break;
				case RUNNER:
					runners++;
					foundAnyBaNpc = true;
					break;
				case HEALER:
					healers++;
					foundAnyBaNpc = true;
					break;
				case OTHER:
				default:
					break;
			}
		}

		waveState.setNpcCounts(fighters, rangers, runners, healers);
		return foundAnyBaNpc;
	}
}