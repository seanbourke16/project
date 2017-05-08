package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Loader {
	public static String load(MachineModel model, File file, int codeOffset, int memoryOffset){
		int codeSize = 0;
		if (model == null || file == null){
			return null;
		}
		try (Scanner input = new Scanner(file)){
			boolean incode = true;
			while (input.hasNextLine()){
			    String z=input.nextLine();
			    Scanner parser = new Scanner(z);
			    //System.out.println(z);
			    int first=parser.nextInt(16);
			    if(incode){
				if(first==-1){
				    incode=false;
				}
				else{
				    model.setCode(codeOffset+codeSize,first,parser.nextInt(16),parser.nextInt(16));
				    codeSize++;
				}
			    }
			    else{
				int val=parser.nextInt(16);
				//System.out.println(first);
				//System.out.println(val);
				model.setData(memoryOffset+first,val);
				parser.close();
			    }
			}
			return ""+codeSize;
		}
		catch(ArrayIndexOutOfBoundsException e){
		    return "Array Index  "+e.getMessage();
		}
		catch(NoSuchElementException e){
		    return "From Scanner: NoSuchElementException";
		}
		catch(FileNotFoundException e){
		    return "File "+file.getName()+" Not Found";
		}
	}
	public static void main(String[] args) {
		MachineModel model = new MachineModel();
		String s = Loader.load(model, new File("out.pexe"),16,32);
		for(int i = 16; i < 16+Integer.parseInt(s); i++) {
		    //System.out.println(i);
			System.out.println(model.getCode().getText(i));			
		}
		System.out.println("--");
		System.out.println("4FF " + 
			Integer.toHexString(model.getData(0x20+0x4FF)).toUpperCase());
		System.out.println("0 " + 
			Integer.toHexString(model.getData(0x20)).toUpperCase());
		System.out.println("10 -" + 
			Integer.toHexString(-model.getData(0x20+0x10)).toUpperCase());
	}
}
