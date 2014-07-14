package tools;

public class RowVisua {
	private int year, number_hydro, number_rnb, number_all;

	public RowVisua(int year, int number_hydro, int number_rnb, int number_all) {
		super();
		this.year = year;
		this.number_hydro = number_hydro;
		this.number_rnb = number_rnb;
		this.number_all = number_all;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getNumber_hydro() {
		return number_hydro;
	}

	public void setNumber_hydro(int number_hydro) {
		this.number_hydro = number_hydro;
	}

	public int getNumber_rnb() {
		return number_rnb;
	}

	public void setNumber_rnb(int number_rnb) {
		this.number_rnb = number_rnb;
	}

	public int getNumber_all() {
		return number_all;
	}

	public void setNumber_all(int number_all) {
		this.number_all = number_all;
	}
	
	
}
