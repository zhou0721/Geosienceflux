package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tools.Data;
import tools.Element;
import tools.MDOVisua;
import tools.RowVisua;

public class VisuaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel annee_debut = new JLabel("     Année début : "), annee_fin = new JLabel("Année fin : "), nombre_annees = new JLabel("Nombre d'années : "),
			releves_hydro = new JLabel("     Relevés hydro : "), releves_rnb = new JLabel("Relevés RNB : "), releves_concordants = new JLabel("Concordants : ");
	private JTable tableau;
	private MDOVisua modele = new MDOVisua();
	
	public JTable getTableau() {
		return tableau;
	}

	public void setTableau(JTable tableau) {
		this.tableau = tableau;
	}
	
	public VisuaFrame() {
		super();

		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		this.setTitle("Visualisation des données");
		this.setSize(600, 350);
		this.setMinimumSize(getSize());
		this.setLocationRelativeTo(getParent());

		JPanel resume = new JPanel();
		resume.setLayout(new GridLayout(2, 3));
		resume.add(annee_debut);
		resume.add(annee_fin);
		resume.add(nombre_annees);
		resume.add(releves_hydro);
		resume.add(releves_rnb);
		resume.add(releves_concordants);

		tableau = new JTable(modele);

		this.getContentPane().add(resume, BorderLayout.NORTH);
		this.getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);

		pack();
	}

	public void createResume(ArrayList<Data> mainData, ArrayList<Element> mainHydro, ArrayList<Element> mainRnb, String srnb) {
		annee_debut.setText("     Année début = " + mainData.get(0).getYear());
		annee_fin.setText("Année fin = " + mainData.get(mainData.size()-1).getYear());
		nombre_annees.setText("Nombre d'années = " + (Integer.valueOf(mainData.get(mainData.size()-1).getYear()) - Integer.valueOf(mainData.get(0).getYear())+1));
		releves_hydro.setText("     Mesures débit = " + mainHydro.size());
		releves_rnb.setText("Mesures " + srnb + " = " + mainRnb.size());
		releves_concordants.setText("Concordants = " + mainData.size());
		
		modele.clean();

		ArrayList<Data> data = mainData;
		ArrayList<Element> hydro = mainHydro;
		ArrayList<Element> rnb = mainRnb;

		int year, previousYear, numberHydro = 0, numberRnb = 0, numberAll = 0, indexData = 0, indexRnb = 0, indexHydro = 0;

		if(hydro.get(0).getYear() < rnb.get(0).getYear()) {
			year = previousYear = hydro.get(0).getYear();
			numberHydro--;
			
			while(indexHydro < hydro.size()) {
				
				year = hydro.get(indexHydro).getYear();
				numberHydro++;
				
				if(year != previousYear || indexHydro == hydro.size()-1) {
					if(indexHydro == hydro.size()-1) {
						previousYear = year;
						numberHydro++;
					}
					
					while(indexData < data.size()) {
						if(data.get(indexData).getYear() == previousYear)
							numberAll++;
						else if(data.get(indexData).getYear() > previousYear)
							break;
						indexData++;
					}
					
					while(indexRnb < rnb.size()) {
						if(rnb.get(indexRnb).getYear() == previousYear)
							numberRnb++;
						else if(rnb.get(indexRnb).getYear() > previousYear)
							break;
						indexRnb++;
					}
					
					modele.addRow(new RowVisua(previousYear, numberHydro, numberRnb, numberAll));
					numberAll = numberHydro = numberRnb = 0;
					previousYear = year;
				}
				
				indexHydro++;
			}
		}

		else {
			year = previousYear = rnb.get(0).getYear();
			numberRnb--;
			
			while(indexRnb < rnb.size()) {
				
				year = rnb.get(indexRnb).getYear();
				numberRnb++;
				
				if(year != previousYear || indexRnb == rnb.size()-1) {
					if(indexRnb == rnb.size()-1) {
						previousYear = year;
						numberRnb++;
					}
					
					while(indexData < data.size()) {
						if(data.get(indexData).getYear() == previousYear)
							numberAll++;
						else if(data.get(indexData).getYear() > previousYear)
							break;
						indexData++;
					}
					
					while(indexHydro < hydro.size()) {
						if(hydro.get(indexHydro).getYear() == previousYear)
							numberHydro++;
						else if(hydro.get(indexHydro).getYear() > previousYear)
							break;
						indexHydro++;
					}
					
					modele.addRow(new RowVisua(previousYear, numberHydro, numberRnb, numberAll));
					numberAll = numberHydro = numberRnb = 0;
					previousYear = year;
				}
				
				indexRnb++;
			}
		}

	}

}
