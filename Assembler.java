package project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler {
	public static void assemble(File input, File output, ArrayList<String> errors){
		ArrayList<String> code = new ArrayList<>();
		
		ArrayList<String> data = new ArrayList<>();
		try{
			boolean dashes=false;
			Scanner sc = new Scanner(input);
			//int counter=0;
			System.out.println();
			while(sc.hasNextLine()){
				if(dashes){
					data.add(sc.next());
					//System.out.println(counter);
				}
				else{
				    String z=sc.nextLine();
					if(z.trim().startsWith("--")&&!dashes){
						dashes=true;
					}
					else{
					    code.add(z);
					}
				}
				//counter++;
			}
			//System.out.println(counter);
			sc.close();
		} catch (FileNotFoundException e){
			System.out.println("Input file does not exist");
		}
		ArrayList<String> outText=new ArrayList<>();
		for(String x:code){
			String[] parts=x.trim().split("\\s+");
			String indirLvl="1";
			if(parts.length==2){
				if(parts[1].startsWith("[")){
					parts[1]=parts[1].substring(1,parts[1].length()-1);
					indirLvl="2";
				}
			}
			if(parts[0].endsWith("I")){
				indirLvl="0";
			}
			if(parts[0].endsWith("A")){
				indirLvl="3";
			}
			int opcode=InstructionMap.opcode.get(parts[0]);
			if(parts.length==1){
				outText.add(Integer.toHexString(opcode).toUpperCase()+" 0 0");
			}
			else{
				outText.add(Integer.toHexString(opcode).toUpperCase()+" "+indirLvl+" "+parts[1]);
			}
		}
		outText.add("-1");
		outText.addAll(data);
		//System.out.println(outText);
		try (PrintWriter out = new PrintWriter(output)){
			for(String s : outText) out.println(s);
		} catch (FileNotFoundException e) {
			errors.add("Cannot create output file");
		}
	}
	
	public static void main(String[] args){
		ArrayList<String> errors = new ArrayList<>();
		assemble(new File("in.pasm"), new File("out.pexe"), errors);		
	}
}
