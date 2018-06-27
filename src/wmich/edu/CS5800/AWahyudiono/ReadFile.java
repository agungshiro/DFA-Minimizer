package wmich.edu.CS5800.AWahyudiono;

/**
 * DFA Simulator and Minimizer
 * by Agung Wahyudiono
 * 
 * This class is for reading a file and produce the DFA instance based on input
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile {
	
	private String filePath;
	private boolean modeState = false;
	private boolean modeCharacter = false;
	private boolean modeInitial = false;
	private boolean modeFinal = false;
	private boolean modeTransition = false;
	
	public ReadFile(String path) {
		
		filePath = path;
		
	}
	
	private void resetFlag() {
		
		modeState = false;
		modeCharacter = false;
		modeInitial = false;
		modeFinal = false;
		modeTransition = false;
	
	}
	
	public DFA DoRead() throws IOException {
		
		// Open the file 
		FileInputStream fstream = new FileInputStream(filePath);
		InputStreamReader istream = new InputStreamReader(fstream);
		BufferedReader breader = new BufferedReader(istream);
		
		DFA myDFA = new DFA();
		
		String strLine;
		
		int transIndex = 0;
		
		while((strLine = breader.readLine()) != null) {
			
			//System.out.println(strline);
			if(strLine.contains("states(Q):")) {
				resetFlag();
				modeState = true;
				continue;
			}
			
			if(strLine.contains("character:")) {
				resetFlag();
				modeCharacter = true;
				continue;
			}
			
			if(strLine.contains("initial state:")) {
				resetFlag();
				modeInitial = true;
				continue;
			}
			
			if(strLine.contains("final state:")) {
				resetFlag();
				modeFinal = true;
				continue;
			}
			
			if(strLine.contains("transition:")) {
				resetFlag();
				modeTransition = true;
				continue;
			}
			
			if(modeState) {
				String[] myState = strLine.split(",");
				
				for(int k = 0 ; k < myState.length; k++) {
					myDFA.addState(myState[k]);
				}
				
				System.out.printf("States : %s \n", strLine);
			}
			
			if(modeCharacter) {
				String[] myChar = strLine.split(",");
				
				for(int j = 0 ; j < myChar.length; j++) {
					myDFA.addAlphabet(myChar[j].charAt(0));
				}
				
				System.out.printf("Characters : %s \n", strLine);
			}

			if(modeInitial) {
				
				myDFA.setInitialState(strLine);
				System.out.printf("Initial state : %s \n", strLine);
			
			}
			
			if(modeFinal) {
				
				String[] myFState = strLine.split(",");
				
				for(int j = 0 ; j < myFState.length; j++) {
					myDFA.addFinalState(myFState[j]);
				}
				
				System.out.printf("Finale state : %s \n", strLine);
			}
			
			if(modeTransition) {
				
				System.out.printf("Transition -> %s \n", strLine);
				myDFA.addTransition(transIndex,strLine);
				transIndex++;
				
			}
			
		}
		
		return myDFA;
	}

}
