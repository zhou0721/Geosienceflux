package graphics;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import tools.Data;
import tools.Dwc;
import tools.Element;
import tools.Indicateurs;
import tools.Init;

public class DwcFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	final private JLabel flux = new JLabel(), biais = new JLabel(), imprecisione10 = new JLabel(),
			imprecisione90 = new JLabel();

	public DwcFrame(ArrayList<Data> data, ArrayList<Element> element) {

		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		this.setSize(300, 300);
		this.setLocationRelativeTo(getParent());
		this.setLayout(null);
		this.setTitle("Résultats méthode DWC : " + Init.getInfo().getPolluant());
		this.setResizable(false);
		this.setVisible(true);

		this.getContentPane().add(flux);
		this.getContentPane().add(biais);
		this.getContentPane().add(imprecisione10);
		this.getContentPane().add(imprecisione90);

		flux.setBounds(25, 10, 200, 25);
		biais.setBounds(25, 50, 200, 25);
		imprecisione10.setBounds(25, 90, 200, 25);
		imprecisione90.setBounds(25, 115, 200, 25);
		
		flux.setText("Flux = " + (int) Dwc.Flux(data, element) + "   t / an");

		int periode = 1 + data.get(data.size() - 1).getYear() - data.get(0).getYear();
		int recurrence = Math.round(365 / (data.size() / (1 + data.get(data.size() - 1).getYear() - data.get(0).getYear())));
		
		float m2 = Indicateurs.m2(element, Indicateurs.b50sup(data));
		biais.setText("Biais = " + Dwc.Biais(periode, recurrence, m2, Indicateurs.b50sup(data)) + " %");
		imprecisione10.setText("Imprécision e10 = " + Dwc.impe10(periode, recurrence, m2) + " %");
		imprecisione90.setText("Imprécision e90 = " + Dwc.impe90(periode, recurrence, m2) + " %");
	}

}
