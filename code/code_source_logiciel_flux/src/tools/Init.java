package tools;

import java.util.ArrayList;

import graphics.MainFrame;

public class Init {

	static private ArrayList<Element> hydro = null, rnb = null;
	static private ArrayList<Data> data = null;
	static private Info info = new Info();
	
	static public ArrayList<Element> getHydro() {
		return hydro;
	}

	static public void setHydro(ArrayList<Element> hydro) {
		Init.hydro = hydro;
	}

	static public ArrayList<Element> getRnb() {
		return rnb;
	}

	static public void setRnb(ArrayList<Element> rnb) {
		Init.rnb = rnb;
	}
	
	static public ArrayList<Data> getData() {
		return data;
	}

	static public void setData(ArrayList<Data> data) {
		Init.data = data;
	}
	
	static public Info getInfo() {
		return info;
	}

	static public void setInfo(Info info) {
		Init.info = info;
	}
	
	public static void main(String[] args) {
		Reader read = new Reader();
		@SuppressWarnings("unused")
		MainFrame frame = new MainFrame(read);
	}

}
