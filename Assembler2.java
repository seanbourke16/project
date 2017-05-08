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
	try{
	    boolean dashes=false;
	    Scanner sc = new Scanner(input);
	    //int counter=0;
	    while(sc.hasNextLine()){
		/*if(dashes){
		  data.add(sc.nextLine());
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
		  }*/
		//counter++;
		inText.add(sc.nextLine());
		//}
		//System.out.println(counter);
		sc.close();
	    }
	}catch (FileNotFoundException e){
	    System.out.println("Input file does not exist");
	}
	boolean dashes=false;
	for(int x=0;x<inText.size();x++){
	    if(inText.get(x).trim().length()==0){
		if(x<inText.size()-1&&inText.get(x+1).trim().length()!=0){
		    errors.add("Error: line "+x+" is a blank line");
		}
	    }
	    if(inText.get(x).charAt(0)==' '||inText.get(x).charAt(0)=='\t'){
		errors.add("Error: line"+x+" starts with a white space");
	    }
	    if(inText.get(x).trim().toUpperCase().startsWith("--")){
		if(inText.get(x).trim().replace("-","").length()!=0){
		    errors.add("Error: line "+x+" has a badly formatted data separator");
		}
		dashes=true;
	    }
	    if(dashes){
		data.add(inText.get(x));
	    }
	    else{
		code.add(inText.get(x));
	    }
	}
	for(int x=0;x<code.size();x++){
	    String[] parts=code.get(x).trim().split("\\s+");
	    if(InstructionMap.sourceCodes.contains(parts[0].toUpperCase())&&!InstructionMap.sourceCodes.contains(parts[0])){
		errors.add("Error: line "+x+" does not have the instruction mnemonic in upper case");
	    }
	    if(!InstructionMap.noArgument.contains(parts[0])&&parts.length==1){
		errors.add("Error: line "+x+" is missing an argument");
	    }
	    if(parts.length>2){
		errors.add("Error: line "+x+" has more than one argument");
	    }
	    if(parts.length==2){
	    }
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
}
