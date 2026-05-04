package com.grimm.baunifiedhelper;

public enum BaRole
{
	UNKNOWN("Unknown"),
	ATTACKER("Attacker"),
	COLLECTOR("Collector"),
	DEFENDER("Defender"),
	HEALER("Healer");

	private final String displayName;

	BaRole(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return displayName;
	}
}