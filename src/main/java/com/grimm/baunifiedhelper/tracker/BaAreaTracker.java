package com.grimm.baunifiedhelper.tracker;

import com.grimm.baunifiedhelper.data.BaNpcData;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;

public class BaAreaTracker
{
	private final Client client;

	public BaAreaTracker(Client client)
	{
		this.client = client;
	}

	public boolean hasLoadedBaNpcs()
	{
		for (NPC npc : client.getNpcs())
		{
			if (BaNpcData.isBaNpc(npc))
			{
				return true;
			}
		}

		return false;
	}

	public int getCurrentRegionId()
	{
		WorldPoint worldPoint = getLocalPlayerWorldPoint();

		if (worldPoint == null)
		{
			return -1;
		}

		return worldPoint.getRegionID();
	}

	public int getWorldX()
	{
		WorldPoint worldPoint = getLocalPlayerWorldPoint();

		if (worldPoint == null)
		{
			return -1;
		}

		return worldPoint.getX();
	}

	public int getWorldY()
	{
		WorldPoint worldPoint = getLocalPlayerWorldPoint();

		if (worldPoint == null)
		{
			return -1;
		}

		return worldPoint.getY();
	}

	public int getPlane()
	{
		WorldPoint worldPoint = getLocalPlayerWorldPoint();

		if (worldPoint == null)
		{
			return -1;
		}

		return worldPoint.getPlane();
	}

	public String getDetectionReason(boolean foundBaNpcs)
	{
		if (foundBaNpcs)
		{
			return "BA NPCs loaded";
		}

		return "Not detected";
	}

	private WorldPoint getLocalPlayerWorldPoint()
	{
		Player localPlayer = client.getLocalPlayer();

		if (localPlayer == null)
		{
			return null;
		}

		return localPlayer.getWorldLocation();
	}
}