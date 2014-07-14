package tools;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class MDOVisua extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final List<RowVisua> rowVisuas = new ArrayList<RowVisua>();

	private String[] entete = {"Année", "Mesures Q", "Mesures qualité", "Concordants"};
	
	public String[] getEntetes() {
		return entete;
	}

	public void setEntetes(String rnb) {
		this.entete[2] = "Mesures " + rnb;
	}

	public MDOVisua() {
		super();
	}

	public int getRowCount() {
		return rowVisuas.size();
	}

	public int getColumnCount() {
		return entete.length;
	}

	public String getColumnName(int columnIndex) {
		return entete[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return rowVisuas.get(rowIndex).getYear();
		case 1:
			return rowVisuas.get(rowIndex).getNumber_hydro();
		case 2:
			return rowVisuas.get(rowIndex).getNumber_rnb();
		case 3:
			return rowVisuas.get(rowIndex).getNumber_all();
		default:
			return null;
		}
	}

	public void addRow(RowVisua rowVisua) {
		rowVisuas.add(rowVisua);

		fireTableRowsInserted(rowVisuas.size()-1, rowVisuas.size()-1);
	}

	public void removeRow(int rowIndex) {
		rowVisuas.remove(rowIndex);

		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void clean() {
		while(rowVisuas.isEmpty() == false) {
			removeRow(rowVisuas.size()-1);
		}
	}

}
