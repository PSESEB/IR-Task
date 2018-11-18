package IR;

import java.util.ArrayList;

/**
 * Main Method with Params
 * 
 * @author weenzeal
 *
 */
public class Main {

	public static void main(String[] args) {
		
		if(args.length != 4) {
			System.out.println("You need to parse exactly 4 Parameters! Try again");
			System.exit(0);
		}
		
		String pathDocs = args[0];
		String pathIndex = args[1];
		boolean modeVS;
		if(args[2].toUpperCase().equals("VS")) {
			modeVS = true;
			
		}else if(args[2].toUpperCase().equals("OK")){
			modeVS = false;
		}else {
			System.out.println("Mode needs to be either 'OK' or 'VS' ... Try again");
			System.exit(0);
		}
		String query = args[3];
		
		ArrayList<Doc> documents = new ArrayList<Doc>();
	    for(String file : FileFactory.traverseFolderStructure(pathDocs)) {
	    	documents.add(FileFactory.getInstance(file));
	    }

	}

}
