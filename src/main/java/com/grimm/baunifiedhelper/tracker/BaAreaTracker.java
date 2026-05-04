package com.grimm.baunifiedhelper.tracker;

import com.grimm.baunifiedhelper.data.BaNpcData;
import java.util.HashSet;
import java.util.Set;
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

	public boolean isInConfiguredBaRegion(String configuredRegionIds)
	{
		int currentRegionId = getCurrentRegionId();

		if (currentRegionId <= 0)
		{
			return false;
		}

		Set<Integer> regionIds = parseRegionIds(configuredRegionIds);
		return regionIds.contains(currentRegionId);
	}

	public String getDetectionReason(boolean foundBaNpcs, boolean inConfiguredRegion)
	{
		if (inConfiguredRegion && foundBaNpcs)
		{
			return "Configured BA region + BA NPCs";
		}

		if (inConfiguredRegion)
		{
			return "Configured BA region";
		}

		if (foundBaNpcs)
		{
			return "BA NPCs loaded";
		}

		return "Not detected";
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

	private Set<Integer> parseRegionIds(String configuredRegionIds)
	{
		Set<Integer> regionIds = new HashSet<>();

		if (configuredRegionIds == null || configuredRegionIds.trim().isEmpty())
		{
			return regionIds;
		}

		String[] parts = configuredRegionIds.split(",");

		for (String part : parts)
		{
			String trimmed = part.trim();

			if (trimmed.isEmpty())
			{
				continue;
			}

			try
			{
				regionIds.add(Integer.parseInt(trimmed));
			}
			catch (NumberFormatException ignored)
			{
				// Ignore malformed region entries so one bad value does not break detection.
			}
		}

		return regionIds;
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