package graphics;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

public class ConfigPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ConfigPanel() {
		super();
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.gray);
		g.drawRoundRect(5, 110, 475, 45, 5, 5);
		g.drawRoundRect(5, 30, 475, 45, 5, 5);
		g.drawRoundRect(5, 190, 475, 45, 5, 5);
	}
}
