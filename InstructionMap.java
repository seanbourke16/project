package project;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class InstructionMap {
	public static Set<String> sourceCodes = new TreeSet<>();
	public static Map<String, Integer> opcode = new TreeMap<>();
	public static Map<Integer, String> mnemonics = new TreeMap<>();
	public static Set<String> noArgument = new TreeSet<>();
	public static Set<String> indirectOK= new TreeSet<>();

	static {

		sourceCodes.add("NOP");
		sourceCodes.add("LOD");
		sourceCodes.add("LODI");
		sourceCodes.add("STO");
		sourceCodes.add("ADD");
		sourceCodes.add("ADDI");
		sourceCodes.add("SUB");
		sourceCodes.add("SUBI");
		sourceCodes.add("MUL");
		sourceCodes.add("MULI");
		sourceCodes.add("DIV");
		sourceCodes.add("DIVI");
		sourceCodes.add("AND");
		sourceCodes.add("ANDI");
		sourceCodes.add("NOT");
		sourceCodes.add("CMPL");
		sourceCodes.add("CMPZ");
		sourceCodes.add("JUMP");
		sourceCodes.add("JUMPI");
		sourceCodes.add("JUMPA");
		sourceCodes.add("JMPZ");
		sourceCodes.add("JMPZI");
		sourceCodes.add("JMPZA");
		sourceCodes.add("HALT");

		
		indirectOK.add("LOD");
		indirectOK.add("ADD");
		indirectOK.add("SUB");
		indirectOK.add("MUL");
		indirectOK.add("DIV");
		indirectOK.add("STO");
		indirectOK.add("AND");
		indirectOK.add("CMPL");
		indirectOK.add("CMPZ");
		indirectOK.add("JUMP");
		indirectOK.add("JMPZ");
		
		noArgument.add("NOP");
		noArgument.add("NOT");
		noArgument.add("HALT");

		opcode.put("NOP", 0x0);
		opcode.put("LOD", 0x1);
		opcode.put("LODI", 0x1);
		opcode.put("STO", 0x2);
		opcode.put("ADD", 0x3);
		opcode.put("ADDI", 0x3);
		opcode.put("SUB", 0x4);
		opcode.put("SUBI", 0x4);
		opcode.put("MUL", 0x5);
		opcode.put("MULI", 0x5);
		opcode.put("DIV", 0x6);
		opcode.put("DIVI", 0x6);
		opcode.put("AND", 0x7);
		opcode.put("ANDI", 0x7);
		opcode.put("NOT", 0x8);
		opcode.put("CMPL", 0x9);
		opcode.put("CMPZ", 0x10);
		opcode.put("JUMP", 0x11);
		opcode.put("JUMPI", 0x11);
		opcode.put("JUMPA", 0x11);
		opcode.put("JMPZ", 0x12);
		opcode.put("JMPZI", 0x12);
		opcode.put("JMPZA", 0x12);
		opcode.put("HALT", 0x15);

		mnemonics.put(0x0, "NOP");
		mnemonics.put(0x1, "LOD");
		mnemonics.put(0x2, "STO");
		mnemonics.put(0x3, "ADD");
		mnemonics.put(0x4, "SUB");
		mnemonics.put(0x5, "MUL");
		mnemonics.put(0x6, "DIV");
		mnemonics.put(0x7, "AND");
		mnemonics.put(0x8, "NOT");
		mnemonics.put(0x9, "CMPL");
		mnemonics.put(0x10, "CMPZ");
		mnemonics.put(0x11, "JUMP");
		mnemonics.put(0x12, "JMPZ");
		mnemonics.put(0x15, "HALT");
	}
}