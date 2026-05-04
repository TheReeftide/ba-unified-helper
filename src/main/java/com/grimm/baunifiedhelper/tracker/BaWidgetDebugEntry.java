package com.grimm.baunifiedhelper.tracker;

public class BaWidgetDebugEntry
{
	private final int widgetId;
	private final String source;
	private final String value;

	public BaWidgetDebugEntry(int widgetId, String source, String value)
	{
		this.widgetId = widgetId;
		this.source = source;
		this.value = value;
	}

	public int getWidgetId()
	{
		return widgetId;
	}

	public String getSource()
	{
		return source;
	}

	public String getValue()
	{
		return value;
	}

	public String toDisplayLine()
	{
		return widgetId + " " + source + ": " + value;
	}
}