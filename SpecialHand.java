//Assumes all objects created are proper notation.
public class SpecialHand {

	String name;
	int numberOfCombos;
	double equity;

	public SpecialHand(String name) {
		this.name = name;
		calcNumberOfCombinations(name);
	}

	public SpecialHand(String name, double equity) {
		this.name = name;
		calcNumberOfCombinations(name);
		this.equity = equity;
	}

	public void calcNumberOfCombinations(String name) {
		if (name.length() == 2) {
			numberOfCombos = 6;
		}
		else {
			String offsuitOrNot = new String(name.substring(2));
			if (offsuitOrNot.toLowerCase().equals("o")) {
				numberOfCombos = 12;
			}
			else {
				numberOfCombos = 4;
			}
		}

	}

	public int compareTo(SpecialHand otherHand) {
		double differenceInEquity = getEquity() - otherHand.getEquity();
		if (differenceInEquity > 0) {
			return 1;
		}
		else if (differenceInEquity < 0) {
			return -1;
		}
		else {
			return 0;
		}
	}

	public int getNumberOfCombinations() {
		return this.numberOfCombos;
	}

	public String getName() {
		return this.name;
	}

	public double getEquity() {
		return this.equity;
	}
	
}