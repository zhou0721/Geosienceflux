package tools;

import java.util.ArrayList;
import java.util.Collections;

import org.jfree.data.xy.XYSeries;

public class Indicateurs {
	public static float b50(ArrayList<Data> data) {
		XYSeries tmp = new XYSeries("");
		ArrayList<Data> working = new ArrayList<Data>();
		for(Data a : data)
			if(a.getFlow() > 0 && a.getValue() > 0) {
				working.add(a);
			}
		for(Data a : working)
			tmp.add(Math.log10(a.getFlow()), Math.log10(a.getValue()));

		double sumx = 0, sumx2 = 0, sumy = 0, sumxy = 0;

		int n;
		double meanX;
		double meanY;
		double slope;
		
		sumx = sumx2 = sumy = sumxy = 0;
		for(int i = 0; i < tmp.getItemCount(); i++) {
				sumx += tmp.getDataItem(i).getXValue();
				sumx2 += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getXValue();
				sumy += tmp.getDataItem(i).getYValue();
				sumxy += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getYValue();
		}
		
		n = tmp.getItemCount();
		meanX = sumx / n;
		meanY = sumy / n;

		slope = (sumxy - sumx * meanY) / (sumx2 - sumx * meanX);
		return (float) slope;
	}
	
	public static float b50inf(ArrayList<Data> data) {
		XYSeries dots_one = new XYSeries("1");
		XYSeries tmp = new XYSeries("");
		ArrayList<Data> working = new ArrayList<Data>();
		for(Data a : data)
			if(a.getFlow() > 0 && a.getValue() > 0) {
				working.add(a);
			}
		int mediane = working.size() / 2;
		for(Data a : working)
			tmp.add(Math.log10(a.getFlow()), Math.log10(a.getValue()));
		for(int i = 0; i < tmp.getItemCount(); i++) {
			if(i < mediane)
				dots_one.add(tmp.getDataItem(i).getXValue(), tmp.getDataItem(i).getYValue());

		}

		double sumx = 0, sumx2 = 0, sumy = 0, sumxy = 0;
		for(int i = 0; i < tmp.getItemCount(); i++) {
			if(i < mediane) {
				sumx += tmp.getDataItem(i).getXValue();
				sumx2 += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getXValue();
				sumy += tmp.getDataItem(i).getYValue();
				sumxy += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getYValue();
			}
		}

		int n = dots_one.getItemCount();
		double meanX = sumx / n;
		double meanY = sumy / n;
		double slope = (sumxy - sumx * meanY) / (sumx2 - sumx * meanX);

		return (float) slope;
	}

	public static float b50sup(ArrayList<Data> data) {
		XYSeries dots_two = new XYSeries("2");
		XYSeries tmp = new XYSeries("");
		ArrayList<Data> working = new ArrayList<Data>();
		for(Data a : data)
			if(a.getFlow() > 0 && a.getValue() > 0) {
				working.add(a);
			}
		int mediane = working.size() / 2;
		for(Data a : working)
			tmp.add(Math.log10(a.getFlow()), Math.log10(a.getValue()));
		for(int i = 0; i < tmp.getItemCount(); i++) {
			if(i >= mediane)
				dots_two.add(tmp.getDataItem(i).getXValue(), tmp.getDataItem(i).getYValue());

		}

		double sumx = 0, sumx2 = 0, sumy = 0, sumxy = 0;

		int n;
		double meanX;
		double meanY;
		double slope;

		for(int i = 0; i < tmp.getItemCount(); i++) {
			if(!(i < mediane)) {
				sumx += tmp.getDataItem(i).getXValue();
				sumx2 += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getXValue();
				sumy += tmp.getDataItem(i).getYValue();
				sumxy += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getYValue();
			}
		}
		
		n = dots_two.getItemCount();
		meanX = sumx / n;
		meanY = sumy / n;

		slope = (sumxy - sumx * meanY) / (sumx2 - sumx * meanX);
		return (float) slope;
	}
	
	public static float m2(ArrayList<Element> tmp, float b50sup) {
		float m2 = (float) (27.65 * b50sup + w2(tmp));
		if(m2 < 2)
			m2 = 2;
		return m2;
	}
	
	public static float w2(ArrayList<Element> tmp) {
		float w2 = 0, w2t = 0;
		Collections.sort(tmp);

		float index = tmp.size() * 0.02f;
		for(int i = 0; i < index; i++) {
			w2t += tmp.get(i).getValue();
		}
		w2t += tmp.get((int) Math.ceil(index)).getValue() * (index - Math.ceil(index));
		for(Element e : tmp) {
			w2 += e.getValue();
		}
		return (w2t * 100 / w2);
	}
}
