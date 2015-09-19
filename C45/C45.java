import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

/**
 * C4.5 Implementation using Java.
 * 
 * @author Harshit Shah
 */
public class C45 {

	public static void main(String[] args) throws IOException {
		// .csv data sets
		String file = "ind-aus-minimumdata.csv";
		Scanner scan;

		scan = new Scanner(new File(file));
		String headerLine = scan.nextLine();
		String headers[] = headerLine.split(",");

		// Class variable is assumed as last column
		int classIndex = headers.length - 1;
		int numAttributes = headers.length - 1;

		// Store all attributes
		Attribute attributes[] = new Attribute[numAttributes];
		for (int x = 0; x < numAttributes; x++) {
			attributes[x] = new Attribute(headers[x]);
		}

		List<String> classes = new ArrayList<String>();
		List<Integer> classesCount = new ArrayList<Integer>();

		// Store the values into respected attributes
		// along with respected classes
		while (scan.hasNextLine()) {
			Record data = null;
			String line = scan.nextLine();
			String lineData[] = line.split(",");

			// insert class into classes List
			if (classes.isEmpty()) {
				classes.add(lineData[classIndex]);
				classesCount.add(classes.indexOf(lineData[classIndex]), 1);
			} else {
				if (!classes.contains(lineData[classIndex])) {
					classes.add(lineData[classIndex]);
					classesCount.add(classes.indexOf(lineData[classIndex]), 1);
				} else {
					classesCount.set(classes.indexOf(lineData[classIndex]),
							classesCount.get(classes
									.indexOf(lineData[classIndex])) + 1);
				}
			}

			// insert data into attributes
			for (int x = 0; x < numAttributes; x++) {
				data = new Record(lineData[x], lineData[classIndex]);
				attributes[x].insertRecord(data);
			}
		}

		System.out.println("==============================");
		// Displaying our attributes classified by C4.5
		for (Attribute a : attributes)
			System.out.println(a.toString());
		System.out.println("==============================");

		// TESTING DATA
		Attribute age = new Attribute("age");

		Record inV = new Record("30", "yes");
		age.insertRecord(inV);
		inV = new Record("30", "yes");
		age.insertRecord(inV);
		inV = new Record("30", "no");
		age.insertRecord(inV);
		inV = new Record("30", "no");
		age.insertRecord(inV);
		inV = new Record("30", "no");
		age.insertRecord(inV);
		inV = new Record("35", "yes");
		age.insertRecord(inV);
		inV = new Record("35", "yes");
		age.insertRecord(inV);
		inV = new Record("35", "yes");
		age.insertRecord(inV);
		inV = new Record("35", "yes");
		age.insertRecord(inV);
		inV = new Record("40", "yes");
		age.insertRecord(inV);
		inV = new Record("40", "yes");
		age.insertRecord(inV);
		inV = new Record("40", "yes");
		age.insertRecord(inV);
		inV = new Record("40", "no");
		age.insertRecord(inV);
		inV = new Record("40", "no");
		age.insertRecord(inV);

		System.out.println("\n===========Test Data Output===================");
		System.out.println(age.toString());

		List<Integer> testCount = new ArrayList<Integer>();
		testCount.add(9);
		testCount.add(5);

		double testIofD = calcIofD(testCount);
		age.setGain(testIofD, 14);

		System.out.println("Information Entropy (I of D) : " + testIofD);
		System.out.println("Gain of age attribute: " + age.gain);
		System.out.println("==============================");

	}

	// Calculates Information Entropy
	public static double calcIofD(List<Integer> classesCount) {
		double IofD = 0.0;
		double temp = 0.0;

		int totalNumClasses = 0;
		for (int i : classesCount) {
			totalNumClasses += i;
		}

		for (double d : classesCount) {
			temp = (-1 * (d / totalNumClasses))
					* (Math.log((d / totalNumClasses)) / Math.log(2));
			IofD += temp;
		}
		return IofD;
	}
}

class Record {
	public String valueName = "";
	public String className = "";

	public Record(String name, String inClass) {
		this.valueName = new String(name);
		this.className = new String(inClass);
	}

	public boolean isNameEqual(Record inV) {
		if (this.valueName.equals(inV.valueName))
			return true;
		return false;
	}

}

class Attribute {
	public String name = new String();
	public List<AllPossibleValues> values = new ArrayList<AllPossibleValues>();
	public double gain = 0.0;

	public Attribute(String name) {
		this.name = name;
	}

	public void setGain(double IofD, int totalNumClasses) {
		int totalValClasses = 0;
		for (AllPossibleValues v : values) {
			v.setEntropy();
			for (int i : v.classesCount) {
				totalValClasses += i;
			}
			gain += (totalValClasses / (double) totalNumClasses) * v.gain;
			totalValClasses = 0;
		}
		this.gain = IofD - this.gain;
	}

	public void insertRecord(Record inVal) {
		if (this.values.isEmpty()) {
			values.add(new AllPossibleValues(inVal.valueName, inVal.className));
		} else {
			for (AllPossibleValues v : values) {
				if (v.valueName.equals(inVal.valueName)) {
					v.update(inVal);
					return;
				}
			}
			values.add(new AllPossibleValues(inVal.valueName, inVal.className));
		}
	}

	public String toString() {
		String out = new String("attribute: " + this.name + "\n");
		for (AllPossibleValues v : values) {
			out += "\tvalue: " + v.valueName + ", ";
			out += "\n\t\tclasses: ";
			for (String c : v.classes) {
				out += c + ", ";
			}
			out += "\n\t\tcounts: ";
			for (Integer i : v.classesCount) {
				out += i + ", ";
			}
			out += "\n";
		}

		return out;
	}

	class AllPossibleValues {
		public String valueName = new String();
		public List<String> classes = new ArrayList<String>();
		public List<Integer> classesCount = new ArrayList<Integer>();
		public double gain = 0.0;

		public AllPossibleValues(String valName, String newClass) {
			this.valueName = valName;
			this.classes.add(newClass);
			this.classesCount.add(1);
		}

		public void setEntropy() {
			double temp = 0.0;

			int totalNumClasses = 0;
			for (int i : this.classesCount) {
				totalNumClasses += i;
			}

			for (double d : classesCount) {
				temp = (-1 * (d / totalNumClasses))
						* (Math.log((d / totalNumClasses)) / Math.log(2));
				this.gain += temp;
			}
		}

		public void update(Record inVal) {
			if (this.classes.contains(inVal.className)) {
				this.classesCount.set(this.classes.indexOf(inVal.className),
						this.classesCount.get(this.classes
								.indexOf(inVal.className)) + 1);
			} else {
				this.classes.add(inVal.className);
				this.classesCount.add(this.classes.indexOf(inVal.className), 1);
			}
		}
	}
}
