package com.grimm.baunifiedhelper.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;

public class BaWidgetDebugTracker
{
	private static final int MAX_DEPTH = 12;

	private final Client client;

	public BaWidgetDebugTracker(Client client)
	{
		this.client = client;
	}

	public List<BaWidgetDebugEntry> findMatchingWidgets(String filterText, int maxResults)
	{
		List<BaWidgetDebugEntry> results = new ArrayList<>();

		if (maxResults <= 0)
		{
			return results;
		}

		List<String> filters = parseFilters(filterText);

		Widget[] roots = client.getWidgetRoots();

		if (roots == null)
		{
			return results;
		}

		for (Widget root : roots)
		{
			visitWidget(root, filters, results, maxResults, 0);

			if (results.size() >= maxResults)
			{
				break;
			}
		}

		return results;
	}

	private void visitWidget(Widget widget, List<String> filters, List<BaWidgetDebugEntry> results, int maxResults, int depth)
	{
		if (widget == null || results.size() >= maxResults || depth > MAX_DEPTH)
		{
			return;
		}

		if (!widget.isHidden())
		{
			checkWidgetValue(widget, "text", widget.getText(), filters, results, maxResults);
			checkWidgetValue(widget, "name", widget.getName(), filters, results, maxResults);
		}

		visitChildren(widget.getChildren(), filters, results, maxResults, depth + 1);
		visitChildren(widget.getDynamicChildren(), filters, results, maxResults, depth + 1);
		visitChildren(widget.getStaticChildren(), filters, results, maxResults, depth + 1);
		visitChildren(widget.getNestedChildren(), filters, results, maxResults, depth + 1);
	}

	private void visitChildren(Widget[] children, List<String> filters, List<BaWidgetDebugEntry> results, int maxResults, int depth)
	{
		if (children == null || results.size() >= maxResults)
		{
			return;
		}

		for (Widget child : children)
		{
			visitWidget(child, filters, results, maxResults, depth);

			if (results.size() >= maxResults)
			{
				break;
			}
		}
	}

	private void checkWidgetValue(
		Widget widget,
		String source,
		String value,
		List<String> filters,
		List<BaWidgetDebugEntry> results,
		int maxResults
	)
	{
		if (value == null || value.trim().isEmpty() || results.size() >= maxResults)
		{
			return;
		}

		String cleaned = cleanWidgetText(value);

		if (cleaned.isEmpty())
		{
			return;
		}

		if (!matchesAnyFilter(cleaned, filters))
		{
			return;
		}

		results.add(new BaWidgetDebugEntry(widget.getId(), source, cleaned));
	}

	private List<String> parseFilters(String filterText)
	{
		List<String> filters = new ArrayList<>();

		if (filterText == null || filterText.trim().isEmpty())
		{
			return filters;
		}

		String[] parts = filterText.split(",");

		for (String part : parts)
		{
			String trimmed = part.trim().toLowerCase(Locale.ROOT);

			if (!trimmed.isEmpty())
			{
				filters.add(trimmed);
			}
		}

		return filters;
	}

	private boolean matchesAnyFilter(String value, List<String> filters)
	{
		if (filters.isEmpty())
		{
			return true;
		}

		String lowerValue = value.toLowerCase(Locale.ROOT);

		for (String filter : filters)
		{
			if (lowerValue.contains(filter))
			{
				return true;
			}
		}

		return false;
	}

	private String cleanWidgetText(String value)
	{
		return value
			.replaceAll("<[^>]*>", "")
			.replace('\u00A0', ' ')
			.replaceAll("\\s+", " ")
			.trim();
	}
}