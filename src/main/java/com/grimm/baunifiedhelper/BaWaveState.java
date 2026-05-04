package com.grimm.baunifiedhelper;

public class BaWaveState
{
	private boolean inBa;
	private boolean debugMode;
	private int waveNumber;
	private int waveTicks;
	private BaRole role = BaRole.UNKNOWN;

	private int fightersAlive;
	private int rangersAlive;
	private int runnersAlive;
	private int healersAlive;

	public void reset()
	{
		inBa = false;
		debugMode = false;
		waveNumber = 0;
		waveTicks = 0;
		role = BaRole.UNKNOWN;
		fightersAlive = 0;
		rangersAlive = 0;
		runnersAlive = 0;
		healersAlive = 0;
	}

	public void setDebugValues(BaRole role, int waveTicks)
	{
		this.inBa = true;
		this.debugMode = true;
		this.waveNumber = 1;
		this.waveTicks = waveTicks;
		this.role = role == null ? BaRole.UNKNOWN : role;

		setNpcCounts(4, 3, 5, 4);
	}

	public void updateLiveState(boolean inBa, BaRole role)
	{
		this.inBa = inBa;
		this.debugMode = false;

		if (inBa)
		{
			this.waveTicks++;
			this.waveNumber = this.waveNumber <= 0 ? 1 : this.waveNumber;
			this.role = role == null ? BaRole.UNKNOWN : role;
		}
		else
		{
			reset();
		}
	}

	public void setNpcCounts(int fightersAlive, int rangersAlive, int runnersAlive, int healersAlive)
	{
		this.fightersAlive = fightersAlive;
		this.rangersAlive = rangersAlive;
		this.runnersAlive = runnersAlive;
		this.healersAlive = healersAlive;
	}

	public boolean isInBa()
	{
		return inBa;
	}

	public boolean isDebugMode()
	{
		return debugMode;
	}

	public int getWaveNumber()
	{
		return waveNumber;
	}

	public int getWaveTicks()
	{
		return waveTicks;
	}

	public BaRole getRole()
	{
		return role;
	}

	public int getFightersAlive()
	{
		return fightersAlive;
	}

	public int getRangersAlive()
	{
		return rangersAlive;
	}

	public int getRunnersAlive()
	{
		return runnersAlive;
	}

	public int getHealersAlive()
	{
		return healersAlive;
	}
}