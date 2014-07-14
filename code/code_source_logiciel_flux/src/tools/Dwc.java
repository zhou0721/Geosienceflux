package tools;

import graphics.MainFrame;

import java.util.ArrayList; 

public class Dwc {

	public static float Flux(ArrayList<Data> data, ArrayList<Element> hydro) {
		float up, down, mean;
		up = down = mean = 0;
		for(Data a : data) {
			up += a.getFlow() * a.getValue();
			down += a.getFlow();
		}
		for(Element e : hydro)
			mean += e.getValue();
		mean = mean / hydro.size();
		return (float) (Math.round(31.536 * (up / down) * mean * 100.0) / 100.0);
	}
	
	public static float Biais(int periode, int recurrence, float m2, float b50sup) {
		return (float) (Math.round(MainFrame.getRead().biais(periode, recurrence, m2, b50sup) * 10.0) / 10.0);
	}
	
	public static float impe10(int periode, int recurrence, float m2) {
		return (float) (Math.round(MainFrame.getRead().impe10(periode, recurrence, m2) * 10.0) / 10.0);
	}
	
	public static float impe90(int periode, int recurrence, float m2) {
		return (float) (Math.round(MainFrame.getRead().impe90(periode, recurrence, m2) * 10.0) / 10.0);
	}
	
}
