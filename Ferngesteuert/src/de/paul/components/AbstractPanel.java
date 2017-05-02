package de.paul.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class AbstractPanel extends JPanel {
	protected static final Font classic = UIManager.getFont("JPanel.font");
	protected static final Font medium = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
	protected static final Font big = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	private static final long serialVersionUID = 1L;

	private void init() {
		setPreferredSize(new Dimension(400, 300));
	}

	public AbstractPanel() {
		super();
		init();
	}

	public AbstractPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		init();
	}

	public AbstractPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		init();
	}

	public AbstractPanel(LayoutManager layout) {
		super(layout);
		init();
	}

}
