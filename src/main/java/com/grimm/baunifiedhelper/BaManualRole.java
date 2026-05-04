package com.grimm.baunifiedhelper;

public enum BaManualRole
{
	AUTO("Auto"),
	ATTACKER("Attacker"),
	COLLECTOR("Collector"),
	DEFENDER("Defender"),
	HEALER("Healer");

	private final String displayName;

	BaManualRole(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public BaRole toBaRole()
	{
		switch (this)
		{
			case ATTACKER:
				return BaRole.ATTACKER;
			case COLLECTOR:
				return BaRole.COLLECTOR;
			case DEFENDER:
				return BaRole.DEFENDER;
			case HEALER:
				return BaRole.HEALER;
			case AUTO:
			default:
				return BaRole.UNKNOWN;
		}
	}

	@Override
	public String toString()
	{
		return displayName;
	}
}