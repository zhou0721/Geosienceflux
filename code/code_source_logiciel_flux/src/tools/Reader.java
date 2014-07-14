package tools;

import graphics.MainFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Reader {
	private File directory_hydro, directory_rnb, directory_export;
	private Boolean is_m3 = false;
	private int col_day_rnb, col_month_rnb, col_year_rnb, col_value_rnb, col_day_hydro, col_month_hydro, col_year_hydro, col_value_hydro;
	private String na_value;

	public File getDirectory_hydro() {
		return directory_hydro;
	}
	public void setDirectory_hydro(File directory_hydro) {
		this.directory_hydro = directory_hydro;
	}
	public File getDirectory_rnb() {
		return directory_rnb;
	}
	public void setDirectory_rnb(File directory_rnb) {
		this.directory_rnb = directory_rnb;
	}
	public File getDirectory_export() {
		return directory_export;
	}
	public void setDirectory_export(File directory_export) {
		this.directory_export = directory_export;
	}
	public Boolean getIs_m3() {
		return is_m3;
	}
	public void setIs_m3(Boolean is_m3) {
		this.is_m3 = is_m3;
	}
	public int getCol_day_rnb() {
		return col_day_rnb;
	}
	public void setCol_day_rnb(int col_day_rnb) {
		this.col_day_rnb = col_day_rnb;
	}
	public int getCol_month_rnb() {
		return col_month_rnb;
	}
	public void setCol_month_rnb(int col_month_rnb) {
		this.col_month_rnb = col_month_rnb;
	}
	public int getCol_year_rnb() {
		return col_year_rnb;
	}
	public void setCol_year_rnb(int col_year_rnb) {
		this.col_year_rnb = col_year_rnb;
	}
	public int getCol_value_rnb() {
		return col_value_rnb;
	}
	public void setCol_value_rnb(Integer col_value_rnb) {
		this.col_value_rnb = col_value_rnb;
	}
	public int getCol_day_hydro() {
		return col_day_hydro;
	}
	public void setCol_day_hydro(int col_day_hydro) {
		this.col_day_hydro = col_day_hydro;
	}
	public int getCol_month_hydro() {
		return col_month_hydro;
	}
	public void setCol_month_hydro(int col_month_hydro) {
		this.col_month_hydro = col_month_hydro;
	}
	public int getCol_year_hydro() {
		return col_year_hydro;
	}
	public void setCol_year_hydro(int col_year_hydro) {
		this.col_year_hydro = col_year_hydro;
	}
	public int getCol_value_hydro() {
		return col_value_hydro;
	}
	public void setCol_value_hydro(int col_value_hydro) {
		this.col_value_hydro = col_value_hydro;
	}
	public String getNa_value() {
		return na_value;
	}
	public void setNa_value(String na_value) {
		this.na_value = na_value;
	}

	public void  parse_hydro() {
		ArrayList<Element> elem = new ArrayList<Element>();
		BufferedReader buffer = null;
		FileReader filereader = null;
		try {
			Init.getInfo().setS_hydro(MainFrame.getCombo_hydro().getSelectedItem().toString());
			for(File file : directory_hydro.listFiles()) {
				if(file.toString().contains(MainFrame.getCombo_hydro().getSelectedItem().toString())) {
					filereader = new FileReader(file);
					break;
				}
			}
			buffer = new BufferedReader(filereader);
			String line;
			String[] curline;
			Init.getInfo().setMaxHydro(Float.MIN_VALUE);
			try {
				line = buffer.readLine();
				while((line = buffer.readLine()) != null) {
					curline = line.split("\t");
					float debit = Float.valueOf(curline[col_value_hydro]);
					if(!this.is_m3)
						debit = debit / 1000;
					if(debit > 0)
						elem.add(new Element(Integer.valueOf(curline[col_day_hydro]), Integer.valueOf(curline[col_month_hydro]), Integer.valueOf(curline[col_year_hydro]), debit));
					if(Init.getInfo().getMaxHydro() < debit)
						Init.getInfo().setMaxHydro(debit);
					if(Init.getInfo().getMinHydro() > debit)
						Init.getInfo().setMinHydro(debit);
				}
				filereader.close();
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Init.setHydro(elem);
		MainFrame.setParsing_hydro(true);
	}

	public void  parse_rnb() {
		ArrayList<Element> elem = new ArrayList<Element>();
		BufferedReader buffer = null;
		FileReader filereader = null;
		try {
			Init.getInfo().setS_rnb(MainFrame.getCombo_rnb().getSelectedItem().toString());
			for(File file : directory_rnb.listFiles()) {
				if(file.toString().contains(MainFrame.getCombo_rnb().getSelectedItem().toString())) {
					filereader = new FileReader(file);
					break;
				}
			}
			buffer = new BufferedReader(filereader);
			String line;
			String[] curline;
			Init.getInfo().setMaxRnb(Float.MIN_VALUE);
			try {
				line = buffer.readLine();
				while((line = buffer.readLine()) != null) {
					curline = line.split("\t");
					if(!Float.valueOf(curline[col_value_rnb]).equals(Float.valueOf(na_value))) {
						elem.add(new Element(Integer.valueOf(curline[col_day_rnb]), Integer.valueOf(curline[col_month_rnb]), Integer.valueOf(curline[col_year_rnb]), Float.valueOf(curline[col_value_rnb])));
						if(Init.getInfo().getMaxRnb() < Float.valueOf(curline[col_value_rnb]))
							Init.getInfo().setMaxRnb(Float.valueOf(curline[col_value_rnb]));
						if(Init.getInfo().getMinRnb() > Float.valueOf(curline[col_value_rnb]))
							Init.getInfo().setMinRnb(Float.valueOf(curline[col_value_rnb]));
					}
				}
				filereader.close();
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Init.setRnb(elem);
		MainFrame.setParsing_rnb(true);
	}

	public void fillHashRnb() {
		BufferedReader buffer = null;
		FileReader filereader = null;
		try {
			for(File file : directory_rnb.listFiles()) {
				if(file.toString().contains(MainFrame.getCombo_rnb().getSelectedItem().toString())) {
					filereader = new FileReader(file);
					break;
				}
			}
			buffer = new BufferedReader(filereader);
			String line;
			String[] curline;
			line = buffer.readLine();
			curline = line.split("\t");
			int cpt = 4;
			MainFrame.getCombo_col_value_rnb().removeAllItems();
			for(String s : curline) {
				if(cpt > 3) {
					MainFrame.getCombo_col_value_rnb().addItem(s);
				}
				cpt++;
			}
			filereader.close();
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void match() {
		ArrayList<Data> data = new ArrayList<Data>();
		ArrayList<Element> rnb = Init.getRnb(), hydro = Init.getHydro();
		int cpt_rnb = 0, cpt_hydro = 0;

		while(cpt_rnb < rnb.size() && cpt_hydro < hydro.size()) {
			if(rnb.get(cpt_rnb).compare(hydro.get(cpt_hydro)) == 0) {
				data.add(new Data(rnb.get(cpt_rnb).month, rnb.get(cpt_rnb).month, rnb.get(cpt_rnb).year, rnb.get(cpt_rnb).value, hydro.get(cpt_hydro).value));
				cpt_hydro++;
				cpt_rnb++;
			}
			else if(rnb.get(cpt_rnb).compare(hydro.get(cpt_hydro)) == -1)
				cpt_rnb++;
			else
				cpt_hydro++;
		}

		Init.setData(data);
	}

	public float biais(int periode, int recurrence, float m2, float b50sup) {
		BufferedReader read = null;
		String file, first, current;
		float u = 0, v = 0;
		ArrayList<Integer> array = new ArrayList<Integer>();
		if(b50sup < 0)
			file = "./config/biais_b50sup_negatif.txt";
		else
			file = "./config/biais_b50sup_positif.txt";
		try {
			read = new BufferedReader(new FileReader(file));
			first = read.readLine();
			while ((current = read.readLine()) != null)
				array.add(Integer.valueOf(current.split("\t")[0]));
			array.add(recurrence);
			Collections.sort(array);

			int rec = 0;
			while(array.get(rec) != recurrence)
				rec++;
			if(rec == 0)
				rec = array.get(rec + 1);
			else if(rec == array.size() - 1)
				rec = array.get(rec - 1);
			else {
				if(Math.abs(array.get(rec + 1) - array.get(rec)) < Math.abs(array.get(rec - 1) + array.get(rec)))
					rec = array.get(rec + 1);
				else
					rec = array.get(rec - 1);
			}

			int per = 1;
			array.clear();
			while(per < first.split("\t").length) {
				array.add(Integer.valueOf(first.split("\t")[per].split("_")[1]));
				per += 2;
			}
			array.add(periode);
			Collections.sort(array);

			per = 0;
			while(array.get(per) != periode)
				per++;
			if(per == 0)
				per = array.get(per + 1);
			else if(per == array.size() - 1)
				per = array.get(per - 1);
			else {
				if(Math.abs(array.get(per + 1) - array.get(per)) < Math.abs(array.get(per - 1) + array.get(per)))
					per = array.get(per + 1);
				else
					per = array.get(per - 1);
			}

			int cpt = 1;
			while(Integer.valueOf(first.split("\t")[cpt].split("_")[1]) != per)
				cpt++;

			read.close();
			read = new BufferedReader(new FileReader(file));
			first = read.readLine();
			do {
				first = read.readLine();
			} while(Integer.valueOf(first.split("\t")[0]) != rec);
			u = Float.valueOf(first.split("\t")[cpt]);
			v = Float.valueOf(first.split("\t")[cpt + 1]);

			read.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return m2 * m2 * u + m2 * v;
	}

	public float impe10(int periode, int recurrence, float m2) {
		BufferedReader read = null;
		String file, first, current;
		float u = 0, v = 0;
		ArrayList<Integer> array = new ArrayList<Integer>();
		file = "./config/imprecision_e10.txt";

		try {
			read = new BufferedReader(new FileReader(file));
			first = read.readLine();
			while ((current = read.readLine()) != null)
				array.add(Integer.valueOf(current.split("\t")[0]));
			array.add(recurrence);
			Collections.sort(array);

			int rec = 0;
			while(array.get(rec) != recurrence)
				rec++;
			if(rec == 0)
				rec = array.get(rec + 1);
			else if(rec == array.size() - 1)
				rec = array.get(rec - 1);
			else {
				if(Math.abs(array.get(rec + 1) - array.get(rec)) < Math.abs(array.get(rec - 1) + array.get(rec)))
					rec = array.get(rec + 1);
				else
					rec = array.get(rec - 1);
			}

			int per = 1;
			array.clear();
			while(per < first.split("\t").length) {
				array.add(Integer.valueOf(first.split("\t")[per].split("_")[1]));
				per += 2;
			}
			array.add(periode);
			Collections.sort(array);

			per = 0;
			while(array.get(per) != periode)
				per++;
			if(per == 0)
				per = array.get(per + 1);
			else if(per == array.size() - 1)
				per = array.get(per - 1);
			else {
				if(Math.abs(array.get(per + 1) - array.get(per)) < Math.abs(array.get(per - 1) + array.get(per)))
					per = array.get(per + 1);
				else
					per = array.get(per - 1);
			}

			int cpt = 1;
			while(Integer.valueOf(first.split("\t")[cpt].split("_")[1]) != per)
				cpt++;

			read.close();
			read = new BufferedReader(new FileReader(file));
			first = read.readLine();
			do {
				first = read.readLine();
			} while(Integer.valueOf(first.split("\t")[0]) != rec);
			u = Float.valueOf(first.split("\t")[cpt]);
			v = Float.valueOf(first.split("\t")[cpt + 1]);

			read.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return m2 * m2 * u + m2 * v;
	}

	public float impe90(int periode, int recurrence, float m2) {
		BufferedReader read = null;
		String file, first, current;
		float u = 0, v = 0;
		ArrayList<Integer> array = new ArrayList<Integer>();
		file = "./config/imprecision_e90.txt";

		try {
			read = new BufferedReader(new FileReader(file));
			first = read.readLine();
			while ((current = read.readLine()) != null)
				array.add(Integer.valueOf(current.split("\t")[0]));
			array.add(recurrence);
			Collections.sort(array);

			int rec = 0;
			while(array.get(rec) != recurrence)
				rec++;
			if(rec == 0)
				rec = array.get(rec + 1);
			else if(rec == array.size() - 1)
				rec = array.get(rec - 1);
			else {
				if(Math.abs(array.get(rec + 1) - array.get(rec)) < Math.abs(array.get(rec - 1) + array.get(rec)))
					rec = array.get(rec + 1);
				else
					rec = array.get(rec - 1);
			}

			int per = 1;
			array.clear();
			while(per < first.split("\t").length) {
				array.add(Integer.valueOf(first.split("\t")[per].split("_")[1]));
				per += 2;
			}
			array.add(periode);
			Collections.sort(array);

			per = 0;
			while(array.get(per) != periode)
				per++;
			if(per == 0)
				per = array.get(per + 1);
			else if(per == array.size() - 1)
				per = array.get(per - 1);
			else {
				if(Math.abs(array.get(per + 1) - array.get(per)) < Math.abs(array.get(per - 1) + array.get(per)))
					per = array.get(per + 1);
				else
					per = array.get(per - 1);
			}

			int cpt = 1;
			while(Integer.valueOf(first.split("\t")[cpt].split("_")[1]) != per)
				cpt++;

			read.close();
			read = new BufferedReader(new FileReader(file));
			first = read.readLine();
			do {
				first = read.readLine();
			} while(Integer.valueOf(first.split("\t")[0]) != rec);
			u = Float.valueOf(first.split("\t")[cpt]);
			v = Float.valueOf(first.split("\t")[cpt + 1]);

			read.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return m2 * m2 * u + m2 * v;
	}

}
