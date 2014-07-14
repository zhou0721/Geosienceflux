package tools;

public class Info {

	private float minRnb, maxRnb, minHydro,
	maxHydro, minDataRnb, minDataHydro,
	maxDataRnb, maxDataHydro;
	private String s_rnb, s_hydro, polluant;

	public Info() {
		minRnb = Float.MAX_VALUE;
		maxRnb = Float.MIN_VALUE;
		minHydro = Float.MAX_VALUE;
		maxHydro = Float.MIN_VALUE;
		minDataRnb = Float.MAX_VALUE;
		maxDataRnb = Float.MIN_VALUE;
		minDataHydro = Float.MAX_VALUE;
		maxDataHydro = Float.MIN_VALUE;
	}
	
	public float getMinRnb() {
		return minRnb;
	}

	public void setMinRnb(float minRnb) {
		this.minRnb = minRnb;
	}

	public float getMaxRnb() {
		return maxRnb;
	}

	public void setMaxRnb(float maxRnb) {
		this.maxRnb = maxRnb;
	}

	public float getMinHydro() {
		return minHydro;
	}

	public void setMinHydro(float minHydro) {
		this.minHydro = minHydro;
	}

	public float getMaxHydro() {
		return maxHydro;
	}

	public void setMaxHydro(float maxHydro) {
		this.maxHydro = maxHydro;
	}

	public float getMinDataRnb() {
		return minDataRnb;
	}

	public void setMinDataRnb(float minDataRnb) {
		this.minDataRnb = minDataRnb;
	}

	public float getMinDataHydro() {
		return minDataHydro;
	}

	public void setMinDataHydro(float minDataHydro) {
		this.minDataHydro = minDataHydro;
	}

	public float getMaxDataRnb() {
		return maxDataRnb;
	}

	public void setMaxDataRnb(float maxDataRnb) {
		this.maxDataRnb = maxDataRnb;
	}

	public float getMaxDataHydro() {
		return maxDataHydro;
	}

	public void setMaxDataHydro(float maxDataHydro) {
		this.maxDataHydro = maxDataHydro;
	}

	public String getS_rnb() {
		return s_rnb;
	}

	public void setS_rnb(String s_rnb) {
		this.s_rnb = s_rnb.replaceAll(".txt", "");
	}

	public String getS_hydro() {
		return s_hydro;
	}

	public void setS_hydro(String s_hydro) {
		this.s_hydro = s_hydro.replaceAll(".txt", "");
	}

	public String getPolluant() {
		return polluant;
	}

	public void setPolluant(String polluant) {
		this.polluant = polluant;
	}
}
