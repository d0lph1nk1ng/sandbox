package com.example.dk.sandbox.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.dk.sandbox.AnimationFragment;
import com.example.dk.sandbox.CanvasFragment;
import com.example.dk.sandbox.DragDropFragment;
import com.example.dk.sandbox.R;
import com.example.dk.sandbox.ScrollviewFragment;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ExampleContent {

	/**
	 * An array of example items.
	 */
	public static List<ExampleItem> ITEMS = new ArrayList<ExampleItem>();

	/**
	 * A map of example items, by ID.
	 */
	public static Map<String, ExampleItem> ITEM_MAP = new HashMap<String, ExampleItem>();

	static {
		// Add sample items.
		addItem(new ExampleItem("1", "Scrollview", R.layout.fragment_scrollview, ScrollviewFragment.class.getSimpleName()));
		addItem(new ExampleItem("2", "Drag & Drop", R.layout.fragment_drag_drop, DragDropFragment.class.getSimpleName()));
		addItem(new ExampleItem("3", "Animation", R.layout.fragment_animation, AnimationFragment.class.getSimpleName()));
		addItem(new ExampleItem("4", "Canvas", R.layout.fragment_canvas, CanvasFragment.class.getSimpleName()));
	}

	private static void addItem(ExampleItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class ExampleItem {
		public String id;
		public String content;
		public int layoutId;
		public String className;

		public ExampleItem(String id, String content, int layoutId, String className) {
			this.id = id;
			this.content = content;
			this.layoutId = layoutId;
			this.className = className;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
