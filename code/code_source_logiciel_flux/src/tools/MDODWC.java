package tools;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class MDODWC extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final List<RowDWC> rowDwc = new ArrayList<RowDWC>();

	private String[] entete = {"Année", "Flux corrigé (T/an)", "Flux limite inférieure (T/an)", "Flux limite supérieure (T/an)", "Biais", "e10", "e90"};
	
	public String[] getEntetes() {
		return entete;
	}

	public MDODWC() {
		super();
	}

	public int getRowCount() {
		return rowDwc.size();
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
			return rowDwc.get(rowIndex).getYear();
		case 1:
			return rowDwc.get(rowIndex).getFlux_corrige();
		case 2:
			return rowDwc.get(rowIndex).getFlux_limite_sup();
		case 3 :
			return rowDwc.get(rowIndex).getFlux_limite_sup();
		case 4:
			return rowDwc.get(rowIndex).getBiais();
		case 5:
			return rowDwc.get(rowIndex).getE10();
		case 6:
			return rowDwc.get(rowIndex).getE90();
		default:
			return null;
		}
	}

	public void addRow(RowDWC row) {
		rowDwc.add(row);

		fireTableRowsInserted(rowDwc.size()-1, rowDwc.size()-1);
	}

	public void removeRow(int rowIndex) {
		rowDwc.remove(rowIndex);

		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void clean() {
		while(rowDwc.isEmpty() == false) {
			removeRow(rowDwc.size()-1);
		}
	}

}