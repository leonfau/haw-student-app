package de.minimum.hawapp.app.blackboard.gui;


public class Item implements Comparable<Item> {
	private final String name;
	private final String data;
	private final String date;
	private final String path;
	private final String image;

	public Item(final String n, final String d, final String dt,
			final String p, final String img) {
		name = n;
		data = d;
		date = dt;
		path = p;
		image = img;
	}

	public String getName() {
		return name;
	}

	public String getData() {
		return data;
	}

	public String getDate() {
		return date;
	}

	public String getPath() {
		return path;
	}

	public String getImage() {
		return image;
	}

	@Override
	public int compareTo(final Item o) {
		if (name != null) {
			return name.toLowerCase().compareTo(o.getName().toLowerCase());
		} else {
			throw new IllegalArgumentException();
		}
	}
}
