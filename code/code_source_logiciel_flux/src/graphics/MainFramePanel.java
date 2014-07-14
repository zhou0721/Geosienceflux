package graphics;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

public class MainFramePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public MainFramePanel() {
		super();
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.gray);
		g.drawRoundRect(5, 30, 343, 140, 5, 5);
		g.drawRoundRect(5, 320, 343, 140, 5, 5);
	}
}
