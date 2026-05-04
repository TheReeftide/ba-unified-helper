package com.grimm.baunifiedhelper;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BaUnifiedHelperPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BaUnifiedHelperPlugin.class);
		RuneLite.main(args);
	}
}