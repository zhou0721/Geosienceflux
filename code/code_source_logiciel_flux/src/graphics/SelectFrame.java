package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jfree.ui.RefineryUtilities;

import tools.Data;
import tools.Element;
import tools.Init;

public class SelectFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel labelDebut = new JLabel("Année début :"), labelFin = new JLabel("Année fin :"), labelNbSaisons = new JLabel("Nombre saisons"),
			labelMoisDepart = new JLabel("Mois de départ");
	private JTextField anneeDebut = new JTextField(), anneeFin = new JTextField();
	private JRadioButton interannuel = new JRadioButton("Flux interannuel"), annee = new JRadioButton("Flux année par année"), saison = new JRadioButton("Flux par saison"),
			civile = new JRadioButton("Civile"), hydro = new JRadioButton("Hydro");
	private JComboBox<Integer> nbSaisons = new JComboBox<Integer>();
	private JComboBox<String> moisDepart = new JComboBox<String>(), moisDepart2 = new JComboBox<String>();
	private JButton confirm = new JButton("Valider");

	public SelectFrame(final ArrayList<Data> mainData, final ArrayList<Element> mainHydro, int i) {
		super();
		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		setTitle("Sélection période");
		setSize(265, 370);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		this.setLocationRelativeTo(getParent());

		nbSaisons.addItem(2);
		nbSaisons.addItem(3);
		nbSaisons.addItem(4);
		nbSaisons.addItem(6);
		nbSaisons.addItem(12);

		moisDepart.addItem("Janvier");
		moisDepart.addItem("Février");
		moisDepart.addItem("Mars");
		moisDepart.addItem("Avril");
		moisDepart.addItem("Mai");
		moisDepart.addItem("Juin");
		moisDepart.addItem("Juillet");
		moisDepart.addItem("Août");
		moisDepart.addItem("Septembre");
		moisDepart.addItem("Octobre");
		moisDepart.addItem("Novembre");
		moisDepart.addItem("Décembre");

		moisDepart2.addItem("Janvier");
		moisDepart2.addItem("Février");
		moisDepart2.addItem("Mars");
		moisDepart2.addItem("Avril");
		moisDepart2.addItem("Mai");
		moisDepart2.addItem("Juin");
		moisDepart2.addItem("Juillet");
		moisDepart2.addItem("Août");
		moisDepart2.addItem("Septembre");
		moisDepart2.addItem("Octobre");
		moisDepart2.addItem("Novembre");
		moisDepart2.addItem("Décembre");

		JPanel panel = new JPanel();
		panel.setLayout(null);

		panel.add(moisDepart2);
		panel.add(labelDebut);
		panel.add(labelFin);
		panel.add(anneeDebut);
		panel.add(anneeFin);
		panel.add(nbSaisons);
		panel.add(moisDepart);
		panel.add(interannuel);
		panel.add(annee);
		panel.add(saison);
		panel.add(civile);
		panel.add(hydro);
		panel.add(labelNbSaisons);
		panel.add(labelMoisDepart);
		panel.add(confirm);

		labelDebut.setBounds(10, 5, 100, 25);
		labelFin.setBounds(145, 5, 100, 25);
		anneeDebut.setBounds(95, 5, 40, 25);
		anneeFin.setBounds(210, 5, 40, 25);
		interannuel.setBounds(5, 50, 200, 25);
		annee.setBounds(5, 100, 200, 25);
		civile.setBounds(50, 120, 60, 25);
		hydro.setBounds(50, 140, 60, 25);
		moisDepart.setBounds(120, 144, 100, 20);
		saison.setBounds(5, 180, 130, 25);
		labelNbSaisons.setBounds(50, 200, 100, 25);
		labelMoisDepart.setBounds(50, 230, 100, 25);
		nbSaisons.setBounds(150, 204, 50, 20);
		moisDepart2.setBounds(150, 234, 100, 20);
		confirm.setBounds(30, 300, 195, 25);

		panel.setSize(99999, 99999);
		this.setContentPane(panel);

		annee.setSelected(true);
		civile.setSelected(true);

		ButtonGroup groupe = new ButtonGroup();
		groupe.add(interannuel);
		groupe.add(annee);
		groupe.add(saison);
		ButtonGroup groupe2 = new ButtonGroup();
		groupe2.add(civile);
		groupe2.add(hydro);

		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(anneeDebut.getText());
				if(!MainFrame.numbers(anneeDebut.getText()) && !anneeDebut.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Veuillez entrer une année de début correcte", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
				else if(!MainFrame.numbers(anneeFin.getText()) && !anneeFin.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Veuillez entrer une année de fin correcte", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
				else {
					dispose();
					ArrayList<Data> array_data = new ArrayList<Data>();
					ArrayList<Element> array_hydro = new ArrayList<Element>();
					int debut, fin;
					if(anneeDebut.getText().equals(""))
						debut = mainData.get(0).getYear();
					else
						debut = Integer.valueOf(anneeDebut.getText());
					if(anneeFin.getText().equals(""))
						fin = mainData.get(mainData.size() - 1).getYear();
					else
						fin = Integer.valueOf(anneeFin.getText());
					for(Data a : mainData)
						if(a.getYear() >= debut && a.getYear() <= fin)
							array_data.add(a);
					for(Element e : mainHydro)
						if(e.getYear() >= debut && e.getYear() <= fin)
							array_hydro.add(e);
					if(interannuel.isSelected() == true) {
						@SuppressWarnings("unused")
						DwcFrame frame = new DwcFrame(array_data, array_hydro);
					}
					else if(annee.isSelected() == true) {
						int mois = 0;
						String s = "default";
						if(civile.isSelected() == true) {
							mois = 0;
							s = "Flux par année civile : " + Init.getInfo().getPolluant();
						}
						else if(hydro.isSelected() == true) {
							mois = moisDepart.getSelectedIndex();
							s = "Flux par année hydrologique : " + Init.getInfo().getPolluant() + "(" + moisDepart.getSelectedItem() + ")";
						}
						else
							System.out.println("ERREUR, pas de radio button selectionné");
						GraphFlux timeseriesdemo1 = new GraphFlux(0, s, mois, array_data, array_hydro);
						timeseriesdemo1.pack();
						RefineryUtilities.centerFrameOnScreen(timeseriesdemo1);
						timeseriesdemo1.setVisible(true);
					}
					else if(saison.isSelected() == true) {
						int mois = moisDepart2.getSelectedIndex(), saisons = Integer.valueOf(nbSaisons.getSelectedItem().toString());
						String s = "Flux par saison : " + Init.getInfo().getPolluant() + "(" + moisDepart.getSelectedItem() + ")";
						GraphFlux timeseriesdemo1 = new GraphFlux(saisons, s, mois, array_data, array_hydro);
						timeseriesdemo1.pack();
						RefineryUtilities.centerFrameOnScreen(timeseriesdemo1);
						timeseriesdemo1.setVisible(true);
					}
					else
						System.out.println("wrong choice");
				}
			}
		});

	}

}
