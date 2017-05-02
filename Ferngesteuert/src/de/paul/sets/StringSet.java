package de.paul.sets;

import java.util.ArrayList;
import java.util.List;

import de.paul.utils.FileIO;

public class StringSet {
	
	private String name;
	private List<String> list = new ArrayList<>();
	
	public StringSet(String path, String name) {
		this.name = name;
		if (path != null)
			load(path);
	}
	
	private void load(String path) {
		String[] lines = FileIO.loadText(path, new String[]{});
		addAll(lines);
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getList() {
		return list;
	}
	
	public String get(int i) {
		return list.get(i);
	}
	
	public void add(String item) {
		list.add(item);
	}
	
	public void addAll(String[] list) {
		for (int i = 0; i < list.length; i++) {
			add(list[i]);
		}
	}
	
	public int getSize() {
		return list.size();
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
