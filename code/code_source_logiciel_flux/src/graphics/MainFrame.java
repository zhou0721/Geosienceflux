package graphics;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



import org.jfree.ui.RefineryUtilities;

import tools.Init;
import tools.Reader;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -1L;
	private static JComboBox<String> combo_hydro = new JComboBox<String>(), combo_rnb = new JComboBox<String>(), combo_col_value_rnb = new JComboBox<String>();
	private JTextField choose_na_value = new JTextField(), choose_year_hydro = new JTextField(), choose_month_hydro = new JTextField(),
			choose_day_hydro = new JTextField(), choose_year_rnb = new JTextField(), choose_month_rnb = new JTextField(),
			choose_day_rnb = new JTextField(), choose_value_hydro = new JTextField();
	private JLabel label_na_value = new JLabel("Valeur manquante : "), label_year_hydro = new JLabel("Année : "), label_month_hydro = new JLabel("Mois : "),
			label_day_hydro = new JLabel("Jour : "), label_year_rnb = new JLabel("Année : "), label_month_rnb = new JLabel("Mois : "),
			label_day_rnb = new JLabel("Jour : "), label_value_hydro = new JLabel("Valeur : "),
			colonnes1 = new JLabel("<html><u>Colonnes</u> : </html>"), colonnes2 = new JLabel("<html><u>Colonnes</u> : </html>"), hydro = new JLabel("Hydro"),
			rnb = new JLabel("RNB"), station_hydro = new JLabel("Station : "), station_rnb = new JLabel("Station : "),
			polluant = new JLabel("Polluant : ");
	private JRadioButton m3 = new JRadioButton("Mètres cube par seconde"), l = new JRadioButton("Litres par seconde");
	private static Boolean parsing_rnb = false, parsing_hydro = false;
	private JButton confirm_rnb = new JButton("Mettre à jour RNB"), confirm_hydro = new JButton("Mettre à jour Hydro");
	public static Reader read;

	private FrameIndicateurs ind;
	@SuppressWarnings("unused")
	private SelectFrame selectFrame;
	private ConfigurationFrame configurationFrame;

	final VisuaFrame visuaFrame = new VisuaFrame();

	public static JComboBox<String> getCombo_hydro() {
		return combo_hydro;
	}

	public static void setCombo_hydro(JComboBox<String> combo_hydro) {
		MainFrame.combo_hydro = combo_hydro;
	}

	public static JComboBox<String> getCombo_rnb() {
		return combo_rnb;
	}

	public static void setCombo_rnb(JComboBox<String> combo_rnb) {
		MainFrame.combo_rnb = combo_rnb;
	}

	public static JComboBox<String> getCombo_col_value_rnb() {
		return combo_col_value_rnb;
	}

	public static void setCombo_col_value_rnb(JComboBox<String> combo_col_value_rnb) {
		MainFrame.combo_col_value_rnb = combo_col_value_rnb;
	}

	public static Boolean getParsing_rnb() {
		return parsing_rnb;
	}

	public static void setParsing_rnb(Boolean parsing_rnb) {
		MainFrame.parsing_rnb = parsing_rnb;
	}

	public static Boolean getParsing_hydro() {
		return parsing_hydro;
	}

	public static void setParsing_hydro(Boolean parsing_hydro) {
		MainFrame.parsing_hydro = parsing_hydro;
	}

	public static Reader getRead() {
		return read;
	}

	public static void setRead(Reader new_read) {
		MainFrame.read = new_read;
	}

	public MainFrame(final Reader read) {
		super();
		MainFrame.read = read;
		this.setTitle("Flux Polluants");
		this.setSize(360, (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.9));
		setResizable(false);
		setMinimumSize(getSize());
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());

		MainFramePanel pan = new MainFramePanel();
		pan.setLayout(null);
		configurationFrame = new ConfigurationFrame(read);

		m3.setSelected(true);

		pan.add(hydro);
		pan.add(colonnes1);
		pan.add(label_year_hydro);
		pan.add(choose_year_hydro);
		pan.add(label_month_hydro);
		pan.add(choose_month_hydro);
		pan.add(label_day_hydro);
		pan.add(choose_day_hydro);
		pan.add(label_value_hydro);
		pan.add(choose_value_hydro);
		pan.add(m3);
		pan.add(l);
		pan.add(combo_hydro);
		pan.add(station_hydro);

		pan.add(choose_na_value);
		pan.add(choose_day_rnb);
		pan.add(choose_month_rnb);
		pan.add(choose_year_rnb);
		pan.add(label_day_rnb);
		pan.add(label_month_rnb);
		pan.add(label_year_rnb);
		pan.add(label_na_value);
		pan.add(colonnes2);
		pan.add(rnb);
		pan.add(combo_rnb);
		pan.add(station_rnb);
		pan.add(combo_col_value_rnb);
		pan.add(polluant);

		hydro.setBounds(160, 5, 100, 30);
		colonnes1.setBounds(10, 65, 120, 30);
		label_year_hydro.setBounds(10, 95, 50, 25);
		choose_year_hydro.setBounds(60, 95, 25, 25);
		label_month_hydro.setBounds(100, 95, 50, 25);
		choose_month_hydro.setBounds(140, 95, 25, 25);
		label_day_hydro.setBounds(180, 95, 50, 25);
		choose_day_hydro.setBounds(220, 95, 25, 25);
		label_value_hydro.setBounds(265, 95, 50, 25);
		choose_value_hydro.setBounds(315, 95, 25, 25);
		m3.setBounds(10, 120, 200, 25);
		l.setBounds(10, 140, 200, 25);
		station_hydro.setBounds(10, 40, 70, 25);
		combo_hydro.setBounds(70, 40, 200, 25);

		rnb.setBounds(165, 295, 100, 30);
		colonnes2.setBounds(10, 355, 120, 30);
		label_year_rnb.setBounds(10, 385, 50, 25);
		choose_year_rnb.setBounds(60, 385, 25, 25);
		label_month_rnb.setBounds(100, 385, 50, 25);
		choose_month_rnb.setBounds(140, 385, 25, 25);
		label_day_rnb.setBounds(180, 385, 50, 25);
		choose_day_rnb.setBounds(220, 385, 25, 25);
		label_na_value.setBounds(10, 420, 130, 25);
		choose_na_value.setBounds(130, 420, 40, 25);
		station_rnb.setBounds(10, 330, 70, 25);
		combo_rnb.setBounds(70, 330, 200, 25);
		polluant.setBounds(10, 520, 70, 25);
		combo_col_value_rnb.setBounds(70, 520, 150, 25);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("Fichiers"), menu2 = new JMenu("Graphiques"), menu3 = new JMenu("Indicateurs"),
				menu4 = new JMenu("Méthodes");
		final JMenuItem config = new JMenuItem("Configuration"), quitter = new JMenuItem("Quitter"), serie = new JMenuItem("Série chronologique"),
				distributionQ = new JMenuItem("Distribution Q"), distributionC = new JMenuItem("Distribution C"), log = new JMenuItem("Log Q/C"),
				indicateur = new JMenuItem("Calcul d'indicateurs"), visua = new JMenuItem("Visualisation"), dwc = new JMenuItem("DWC");

		configurationFrame.setVisible(true);

		ButtonGroup groupe = new ButtonGroup();
		groupe.add(m3);
		groupe.add(l);

		pan.add(confirm_rnb);
		confirm_rnb.setBounds(80, 480, 200, 25);
		confirm_rnb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setAlwaysOnTop(false);
				if(!(numbers(choose_day_rnb.getText()) && numbers(choose_month_rnb.getText()) && numbers(choose_year_rnb.getText()))) {
					JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
				}
				else if(combo_rnb.getSelectedItem() == null)
					JOptionPane.showMessageDialog(null, "Veuillez choisir une station", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
				else {
					read.setCol_year_rnb(Integer.valueOf(choose_year_rnb.getText())-1);
					read.setCol_month_rnb(Integer.valueOf(choose_month_rnb.getText())-1);
					read.setCol_day_rnb(Integer.valueOf(choose_day_rnb.getText())-1);
					if(choose_na_value.getText().equals(""))
						read.setNa_value("999");
					else
						read.setNa_value(choose_na_value.getText());
					read.fillHashRnb();
				}
			}
		});

		pan.add(confirm_hydro);
		confirm_hydro.setBounds(80, 190, 200, 25);

		confirm_hydro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setAlwaysOnTop(false);
				if(!(MainFrame.numbers(choose_year_hydro.getText()) && MainFrame.numbers(choose_value_hydro.getText()) && MainFrame.numbers(choose_day_hydro.getText()) && MainFrame.numbers(choose_month_hydro.getText()))) {
					JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
				}
				else if(combo_hydro.getSelectedItem() == null)
					JOptionPane.showMessageDialog(null, "Veuillez choisir une station", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
				else {
					read.setCol_year_hydro(Integer.valueOf(choose_year_hydro.getText())-1);
					read.setCol_month_hydro(Integer.valueOf(choose_month_hydro.getText())-1);;
					read.setCol_day_hydro(Integer.valueOf(choose_day_hydro.getText())-1);
					read.setCol_value_hydro(Integer.valueOf(choose_value_hydro.getText())-1);
					read.setIs_m3(m3.isSelected());
					read.parse_hydro();
					if(parsing_rnb && parsing_hydro) {
						read.match();
						visua.setEnabled(true);
						distributionQ.setEnabled(true);
						distributionC.setEnabled(true);
						serie.setEnabled(true);
						log.setEnabled(true);
						indicateur.setEnabled(true);
						dwc.setEnabled(true);
					}
				}
			}
		});

		visua.setEnabled(false);
		distributionQ.setEnabled(false);
		distributionC.setEnabled(false);
		serie.setEnabled(false);
		log.setEnabled(false);
		indicateur.setEnabled(false);
		dwc.setEnabled(false);

		visua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(combo_col_value_rnb.getSelectedItem() != null) {
					visuaFrame.createResume(Init.getData(), Init.getHydro(), Init.getRnb(), combo_col_value_rnb.getSelectedItem().toString());
					visuaFrame.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un polluant", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
			}
		});

		combo_col_value_rnb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(combo_col_value_rnb.getSelectedItem() != null) {
					visuaFrame.getTableau().getColumnModel().getColumn(2).setHeaderValue("Mesures " + combo_col_value_rnb.getSelectedItem().toString());
					Init.getInfo().setPolluant(combo_col_value_rnb.getSelectedItem().toString());
					read.setCol_value_rnb(combo_col_value_rnb.getSelectedIndex());
					read.parse_rnb();
					if(parsing_rnb && parsing_hydro) {
						read.match();
						visua.setEnabled(true);
						distributionQ.setEnabled(true);
						distributionC.setEnabled(true);
						serie.setEnabled(true);
						log.setEnabled(true);
						indicateur.setEnabled(true);
						dwc.setEnabled(true);
					}
				}
			}
		});

		config.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				configurationFrame.setVisible(true);
			}
		});

		quitter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		serie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GraphChronologique timeseriesdemo8 = new GraphChronologique("Série chronologique");
				timeseriesdemo8.pack();
				RefineryUtilities.centerFrameOnScreen(timeseriesdemo8);
				timeseriesdemo8.setVisible(true);
			}
		});

		distributionC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GraphDistribution histogramdemo1 = new GraphDistribution("Distribution concentration", 2);
				histogramdemo1.pack();
				RefineryUtilities.centerFrameOnScreen(histogramdemo1);
				histogramdemo1.setVisible(true);
				histogramdemo1.setDefaultCloseOperation(HIDE_ON_CLOSE);
			}
		});

		distributionQ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GraphDistribution histogramdemo2 = new GraphDistribution("Distribution débit", 1);
				histogramdemo2.pack();
				RefineryUtilities.centerFrameOnScreen(histogramdemo2);
				histogramdemo2.setVisible(true);
				histogramdemo2.setDefaultCloseOperation(HIDE_ON_CLOSE);
			}
		});

		log.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GraphLog linechartdemo6 = new GraphLog("Relation contentration débit");
				linechartdemo6.pack();
				RefineryUtilities.centerFrameOnScreen(linechartdemo6);
				linechartdemo6.setVisible(true);
				linechartdemo6.setDefaultCloseOperation(HIDE_ON_CLOSE);
			}
		});

		indicateur.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ind = new FrameIndicateurs(Init.getData(), Init.getHydro(), read);
				ind.setVisible(true);
			}
		});

		dwc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				maj();
				selectFrame = new SelectFrame(Init.getData(), Init.getHydro(), 0);
			}
		});

		Separator sep1 = new Separator();

		menu1.add(config);
		menu1.add(visua);
		menu1.add(sep1);
		menu1.add(quitter);

		menu2.add(serie);
		menu2.add(distributionQ);
		menu2.add(distributionC);
		menu2.add(log);

		menu3.add(indicateur);

		menu4.add(dwc);

		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		menuBar.add(menu4);

		this.setJMenuBar(menuBar);
		this.setContentPane(pan);

		this.setVisible(true);

	}

	public void maj() {
		confirm_hydro.doClick();
	}

	public static boolean numbers(String str) {

		if (str == null || str.length() == 0)
			return false;

		for (int i = 0; i < str.length(); i++) {

			if (!Character.isDigit(str.charAt(i)))
				return false;
		}

		return true;
	}

}
