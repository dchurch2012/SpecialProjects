import java.util.*;
import java.io.*;
	
public class VectorSolver {
	public static void main(String[] args) {
		
		if(!checkArgs(args)) {
			System.exit(-1);
		}
		
		VectorContainer vectorContainer = new VectorContainer(); 
	
		if(args.length == 2)
		{
			String fileName  = new String(args[1]);
			
			File f = new File(fileName);
			
			if(!f.exists()){
				System.out.println(fileName + " not found");
				System.exit(-2);
			}
			else {
				f = null;
			}
			
			try {
				vectorContainer.readVectorsFromFile(fileName);
			}
			catch(Exception except) {
				System.out.println("Error reading data from datafile");
			}
		}
		else {
			vectorContainer.demoSampleProblem();
			//vectorContainer.readVectorsFromConsole(); 
		}
	
	}
	
	public static void printArgs(String[] args) {
		for(String argument : args) {
			System.out.println(argument);
		}
	}
	
	public static void displayUsage() {
		System.out.println("usage : VectorSolver [optional] -f input_filename");
	}
	
	public static boolean checkArgs(String[] args) {
		if(args.length == 0) {
			return true;
		}
		else
		if(args.length == 2) {
			if(args[0].equals("-f")) {
				return true;
			}
		}
			
		return false;
	}
 }
