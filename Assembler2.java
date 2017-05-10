package project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler2 {
    public static void assemble(File input, File output, ArrayList<String> errors){
	ArrayList<String> code = new ArrayList<>();
	ArrayList<String> data = new ArrayList<>();
	ArrayList<String> inText=new ArrayList<String>();
	boolean first = false;
	boolean blank = false;
	int blank1 = 0;
	try (Scanner fileInput = new Scanner(input)) {
		while (fileInput.hasNextLine()) {
			String line = fileInput.nextLine();
			inText.add(line);
		}
		for (int x = 0; x < inText.size(); x++) {
			String line = inText.get(x);
			if (line.trim().length() == 0 && !blank) {
					blank = true;
					blank1 = x + 1;
				}
			if (line.trim().length() != 0 && blank){
				errors.add("Error on line " + blank1 + ": illegal blank line in the source file");
				blank = false;
			}
			if (line.trim().length() != 0) {
				if (line.charAt(0) == ' ' || line.charAt(0) == '\t') {
					errors.add("Error on line " + (x + 1) + ": line starts with illegal white space");
				}
			}
			if (line.trim().toUpperCase().startsWith("--") && first == false) {
				String dataSep = line.trim().replace("-", "");
				if (dataSep.length() != 0) {
					errors.add("Error on line " + (x + 1) + ": dashed line has non-dashes");
					first = true;
				}
			}
			else if (line.trim().toUpperCase().startsWith("--") && first == true) {
				String dataSep = line.trim().replace("-", "");
				if (dataSep.length() != 0) {
					errors.add("Error on line " + (x + 1) + ": has a duplicate data separator");
				}
			}
		}
		Scanner fileInput2 = new Scanner(input);
		while (fileInput2.hasNextLine()) {
			String line = fileInput2.nextLine();
			if (!line.trim().startsWith("--")) {
				code.add(line.trim());
			}
			else {
				break;
			}
		}
		while (fileInput2.hasNextLine()) {
			String line = fileInput2.nextLine();
			data.add(line.trim());
		}
		fileInput2.close();
	}
	catch (FileNotFoundException e) {
		errors.add("Input file does not exist");
		return;
	}
	ArrayList<String> outText = new ArrayList<String>();
	for (int lineNum = 0; lineNum < code.size(); lineNum++) {
		String[] parts = code.get(lineNum).trim().split("\\s+");
		int opcode = 0;
		try {
			if (code.get(lineNum).trim().length() == 0) {
				continue;
			}
			opcode = InstructionMap.opcode.get(parts[0]);
		}
		catch (NullPointerException e) {
			if (InstructionMap.opcode.containsKey(parts[0].toUpperCase()) && !InstructionMap.opcode.containsKey(parts[0])) {
				errors.add("Error on line " + (lineNum + 1) + ": mnemonic must be upper case: " + parts[0]);
			}
			else {
				errors.add("Error on line " + (lineNum + 1) + ": illegal mnemonic");
			}
			continue;
		}
		if (InstructionMap.sourceCodes.contains(parts[0].toUpperCase())
				&& !InstructionMap.sourceCodes.contains(parts[0])) {
			errors.add("Error on line " + (lineNum + 1) + ": mnemonic must be upper case");
		}
		if (InstructionMap.noArgument.contains(parts[0]) && parts.length != 1) {
			errors.add("Error on line " + (lineNum + 1) + ": this mnemonic cannot take arguments");
		}
		if (!InstructionMap.noArgument.contains(parts[0])) {
			if (parts.length == 1) {
				errors.add("Error on line " + (lineNum + 1) + ": this mnemonic is missing an argument");
			}
			if (parts.length >= 3) {
				errors.add("Error on line " + (lineNum + 1) + ": this mnemonic has too many arguments");
			}
			if (parts.length == 2) {
				int indirLvl = 1;
				if (parts[1].startsWith("[")) {
					if (!InstructionMap.indirectOK.contains(parts[0])) {
						errors.add("Error on line " + (lineNum + 1) + ": does not contain a legal operation");
					}
					if (InstructionMap.indirectOK.contains(parts[0])) {
						if (!parts[1].endsWith("]")) {
							errors.add("Error on line " + (lineNum + 1) + ": this argument is missing closing \"]\"");
						}
					}
					parts[1] = parts[1].substring(1, parts[1].length() - 1);
					indirLvl = 2;
				}
				if (parts[0].endsWith("I")) {
					indirLvl = 0;
				}
				else if (parts[0].endsWith("A")) {
					indirLvl = 3;
				}
				outText.add(Integer.toHexString(opcode).toUpperCase() + " " + indirLvl + " " + parts[1]);
				int arg = 0;
				try {
					arg = Integer.parseInt(parts[1], 16);
				}
				catch (NumberFormatException e) {
					errors.add("Error on line " + (lineNum + 1) + ": argument is not a hex number");
				}
			}
		}
		else {
			outText.add(Integer.toHexString(opcode).toUpperCase() + " 0 0");
		}
	}
	for (int lineNum = 0; lineNum < data.size();  lineNum++) {
		String[] parts = data.get(lineNum).trim().split("\\s+");
		if (data.get(lineNum).trim().length() != 0) {
			if (parts.length == 1) {
				errors.add("Error on line " + (lineNum + 2 + code.size()) + ": data format does not consist of two numbers");
			}
			if (parts.length >= 3) {
				errors.add("Error on line " + (lineNum + 2 + code.size()) + ": has more than one argument");
			}
			if (parts.length == 2) {
				int arg1 = 0;
				try {
					arg1 = Integer.parseInt(parts[0], 16);
				}
				catch (NumberFormatException e) {
					errors.add("Error on line " + (lineNum + 2 + code.size()) + ": data address is not a hex number");
				}
				int arg2 = 0;
				try {
					arg2 = Integer.parseInt(parts[1], 16);
				}
				catch (NumberFormatException e) {
					errors.add("Error on line " + (lineNum + 2 + code.size()) + ": data value is not a hex number");
				}
				catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}	
	}
	outText.add("-1");
	outText.addAll(data);
	if (errors.size() == 0) {
		try (PrintWriter out = new PrintWriter(output)) {
			for (String s : outText) {
				out.println(s);
			}
		}
		catch (FileNotFoundException e) {
			errors.add("Cannot create output file");
		}
	}
}

}
