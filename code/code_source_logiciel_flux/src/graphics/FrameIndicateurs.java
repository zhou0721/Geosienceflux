package graphics;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import tools.Data;
import tools.Element;
import tools.Init;
import tools.Reader;

public class FrameIndicateurs extends JFrame {

	private static final long serialVersionUID = 1L;
	final private JLabel nb = new JLabel(), indw2 = new JLabel(), indb50 = new JLabel(),
			indb50inf = new JLabel() , indb50sup = new JLabel(), methodes = new JLabel(),
			indm2 = new JLabel();

	public FrameIndicateurs(ArrayList<Data> mainData, ArrayList<Element> mainHydro, Reader read) {
		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		this.setSize(350, 300);
		this.setLocationRelativeTo(getParent());
		this.setLayout(null);
		this.setTitle("Indicateurs pour " + Init.getInfo().getPolluant());
		this.setResizable(false);
		this.setVisible(true);

		this.getContentPane().add(nb);
		this.getContentPane().add(indw2);
		this.getContentPane().add(indb50);
		this.getContentPane().add(indb50inf);
		this.getContentPane().add(indb50sup);
		this.getContentPane().add(methodes);
		this.getContentPane().add(indm2);

		nb.setBounds(25, 10, 1000, 25);
		indw2.setBounds(25, 50, 100, 25);
		indm2.setBounds(25, 75, 100, 25);
		indb50.setBounds(25, 100, 100, 25);
		indb50inf.setBounds(25, 125, 100, 25);
		indb50sup.setBounds(25, 150, 100, 25);
		methodes.setBounds(25, 200, 300, 25);
		
		update(mainData, mainHydro);
		read.parse_hydro();
	}

	public void update(ArrayList<Data> mainData, ArrayList<Element> mainHydro) {
		nb.setText("Nombre de couples de mesures C-Q : " + mainData.size());
		indw2.setText("W2% = " + Math.round(tools.Indicateurs.w2(mainHydro) * 100.0) / 100.0);
		indb50.setText("b = " + Math.round(tools.Indicateurs.b50(mainData) * 100.0) / 100.0);
		indb50inf.setText("b50inf = " + Math.round(tools.Indicateurs.b50inf(mainData) * 100.0) / 100.0);
		float b50sup = tools.Indicateurs.b50sup(mainData);
		indb50sup.setText("b50sup = " + Math.round(b50sup * 100.0) / 100.0);
		methodes.setText("Méthodes conseillées : " + this.choose(mainData, mainHydro));
		indm2.setText("M2% = " + Math.round(tools.Indicateurs.m2(mainHydro, b50sup) * 100.0) / 100.0);
	}

	private String choose(ArrayList<Data> mainData, ArrayList<Element> mainHydro) {
		float w2 = tools.Indicateurs.w2(mainHydro), b50sup = tools.Indicateurs.b50sup(mainData);
		if(w2 <= 10) {
			if(b50sup <= -0.2) {
				return "DWC";
			}
			else if(b50sup > -0.2 && b50sup <= 0.2) {
				return "DWC";
			}
			else if(b50sup > 0.2 && b50sup <= 0.8) {
				return "SRC50*, HSM";
			}
			else if(b50sup > 0.8 && b50sup <= 1.4) {
				return "SRC50*, HSM";
			}
			else {
				return "SRC50*, HSM";
			}
		}
		else if(w2 > 10 && w2 <= 15) {
			if(b50sup <= -0.2) {
				return "DWC";
			}
			else if(b50sup > -0.2 && b50sup <= 0.2) {
				return "DWC";
			}
			else if(b50sup > 0.2 && b50sup <= 0.8) {
				return "SRC50*, HSM";
			}
			else if(b50sup > 0.8 && b50sup <= 1.4) {
				return "SRC50*, HSM";
			}
			else {
				return "HSM";
			}
		}
		else {
			if(b50sup <= -0.2) {
				return "DWC";
			}
			else if(b50sup > -0.2 && b50sup <= 0.2) {
				return "SRC50*, HSM";
			}
			else if(b50sup > 0.2 && b50sup <= 0.8) {
				return "SRC50*, HSM";
			}
			else if(b50sup > 0.8 && b50sup <= 1.4) {
				return "HSM";
			}
			else {
				return "HSM";
			}
		}
	}

}
