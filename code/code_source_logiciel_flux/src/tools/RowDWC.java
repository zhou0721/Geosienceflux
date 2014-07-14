package tools;

public class RowDWC {
	private int year, flux_corrige, flux_limite_inf, flux_limite_sup;
	private float biais, e10, e90;

	public RowDWC(int year, int flux_corrige, int flux_litemite_inf, int flux_limite_sup, float biais, float e10, float e90) {
		super();
		this.year = year;
		this.flux_corrige = flux_corrige;
		this.setFlux_limite_inf(flux_litemite_inf);
		this.setFlux_limite_sup(flux_limite_sup);
		this.setBiais(biais);
		this.setE10(e10);
		this.setE90(e90);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public float getBiais() {
		return biais;
	}

	public void setBiais(float biais) {
		this.biais = biais;
	}

	public int getFlux_corrige() {
		return flux_corrige;
	}

	public void setFlux_corrige(int flux) {
		this.flux_corrige = flux;
	}

	public float getE10() {
		return e10;
	}

	public void setE10(float e10) {
		this.e10 = e10;
	}

	public float getE90() {
		return e90;
	}

	public void setE90(float e90) {
		this.e90 = e90;
	}

	public int getFlux_limite_inf() {
		return flux_limite_inf;
	}

	public void setFlux_limite_inf(int flux_limite_inf) {
		this.flux_limite_inf = flux_limite_inf;
	}

	public int getFlux_limite_sup() {
		return flux_limite_sup;
	}

	public void setFlux_limite_sup(int flux_limite_sup) {
		this.flux_limite_sup = flux_limite_sup;
	}
	
}