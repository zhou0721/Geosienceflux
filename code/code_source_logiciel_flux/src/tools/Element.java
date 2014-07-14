package tools;

public class Element implements Comparable<Element> {
	protected int day, month, year;
	protected float value;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Element(int day, int month, int year, float value) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
		this.value = value;
	}

	public Element() {
		super();
		this.day = 0;
		this.month = 0;
		this.year = 0;
		this.value = 0;
	}

	public String toString() {
		return "[" + day + "-" + month + "-" + year + "   " + value + "]";
	}

	public int compare(Element elem) {
		if(this.year < elem.year)
			return -1;
		else if(this.year > elem.year)
			return 1;
		else if(this.month < elem.month)
			return -1;
		else if(this.month > elem.month)
			return 1;
		else if(this.day < elem.day)
			return -1;
		else if(this.day > elem.day)
			return 1;
		else return 0;
	}

	public int compareTo(Element elem) {
		if(this.value > elem.value)
			return -1;
		else if(this.value < elem.value)
			return 1;
		else
			return 0;
	}
}
