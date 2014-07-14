package tools;

import tools.Element;

public class Data extends Element {

	private float flow;
	
	public float getFlow() {
		return flow;
	}

	public void setFlow(float flow) {
		this.flow = flow;
	}

	public Data(int day, int month, int year, float rnb, float flow) {
		super(day, month, year, rnb);
		this.setFlow(flow);
	}
	
	public Data(float flow, Element elem) {
		this.day = elem.getDay();
		this.month = elem.getMonth();
		this.year = elem.getYear();
		this.value = elem.getValue();
		this.setFlow(flow);
	}
	
	public Data() {
		super();
		this.setFlow(0);
	}


	@Override
	public String toString() {
		return "[" + flow + " " + day + "-" + month + "-" + year + " " + value + "]";
	}
	
}
