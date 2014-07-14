package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import tools.Reader;

public class ConfigurationFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton confirm = new JButton("Enregistrer"),
			setExport = new JButton("Changer"), setHydro = new JButton(
					"Changer"), setRnb = new JButton("Changer");
	private JFileChooser chooser;
	private JLabel hydro = new JLabel("Hydro"), rnb = new JLabel("RNB"),
			export = new JLabel("Export");

	private File directory_hydro = null, directory_rnb = null,
			directory_export = null;

	private JTextArea path_hydro = new JTextArea("Dossier non défini"),
			path_rnb = new JTextArea("Dossier non défini"),
			path_export = new JTextArea("Dossier non défini");

	public ConfigurationFrame(final Reader read) {
		super();

		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		this.setSize(500, 350);
		this.setLocationRelativeTo(getParent());
		this.setLayout(null);
		setAlwaysOnTop(true);
		this.setTitle("Configuration");
		setResizable(false);

		ConfigPanel pan = new ConfigPanel();

		path_hydro.setText(" Dossier non défini");
		path_export.setText(" Dossier non défini");
		path_rnb.setText(" Dossier non défini");

		pan.setLayout(null);
		pan.add(path_hydro);
		pan.add(path_rnb);
		pan.add(path_export);
		pan.add(confirm);
		pan.add(setRnb);
		pan.add(setHydro);
		pan.add(setExport);
		pan.add(hydro);
		pan.add(rnb);
		pan.add(export);

		path_hydro.setBounds(15, 40, 350, 25);
		path_hydro.setFocusable(false);
		hydro.setBounds(230, 5, 100, 30);
		setHydro.setBounds(375, 40, 100, 25);

		setHydro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Choix du dossier hydro");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				setAlwaysOnTop(false);
				if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
					directory_hydro = chooser.getSelectedFile();
					read.setDirectory_hydro(directory_hydro);
					path_hydro.setText(" " + chooser.getSelectedFile());
				}
				setAlwaysOnTop(true);
			}
		});

		path_rnb.setBounds(15, 120, 350, 25);
		path_rnb.setFocusable(false);
		rnb.setBounds(235, 85, 100, 30);
		setRnb.setBounds(375, 120, 100, 25);
		setRnb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(read.getDirectory_hydro().getParentFile());
				chooser.setDialogTitle("Choix du dossier RNB");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				setAlwaysOnTop(false);
				if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
					directory_rnb = chooser.getSelectedFile();
					path_rnb.setText(" " + chooser.getSelectedFile());
				}
				setAlwaysOnTop(true);
			}
		});

		path_export.setBounds(15, 200, 350, 25);
		path_export.setFocusable(false);
		export.setBounds(230, 165, 100, 30);
		setExport.setBounds(375, 200, 100, 25);
		setExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(read.getDirectory_hydro().getParentFile());
				chooser.setDialogTitle("Choix du dossier d'export");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				setAlwaysOnTop(false);
				if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
					directory_export = chooser.getSelectedFile();
					path_export.setText(" " + chooser.getSelectedFile());
				}
				setAlwaysOnTop(true);
			}
		});

		confirm.setBounds(150, 260, 200, 25);

		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setAlwaysOnTop(false);
				if (directory_export == null || directory_rnb == null
						|| directory_hydro == null) {
					JOptionPane
							.showMessageDialog(
									null,
									"Veuillez entrer les répertoires contenant les données hydro, RNB, et le répertoire d'export.",
									"Champ vide", JOptionPane.WARNING_MESSAGE);
				} else {
					MainFrame.getCombo_hydro().removeAllItems();
					MainFrame.getCombo_rnb().removeAllItems();

					for (File myFile : directory_hydro.listFiles())
						MainFrame.getCombo_hydro().addItem(
								myFile.toString()
										.substring(
												directory_hydro.toString()
														.length() + 1,
												myFile.toString().length()));
					for (File myFile : directory_rnb.listFiles())
						MainFrame.getCombo_rnb().addItem(
								myFile.toString().substring(
										directory_rnb.toString().length() + 1,
										myFile.toString().length()));

					MainFrame.getCombo_hydro().setSelectedItem(null);
					MainFrame.getCombo_rnb().setSelectedItem(null);

					read.setDirectory_export(directory_export);
					read.setDirectory_rnb(directory_rnb);
					read.setDirectory_hydro(directory_hydro);
					
					setVisible(false);
				}
			}
		});

		this.setContentPane(pan);
	}

}
