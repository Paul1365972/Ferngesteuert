package de.paul.components;

import javax.swing.DefaultListSelectionModel;

public class MyListSelectionModel extends DefaultListSelectionModel {
	private static final long serialVersionUID = 1L;

	@Override
	public void setSelectionInterval(int index0, int index1) {
		if (super.isSelectedIndex(index0)) {
			super.removeSelectionInterval(index0, index1);
		} else {
			super.addSelectionInterval(index0, index1);
		}
	}
}