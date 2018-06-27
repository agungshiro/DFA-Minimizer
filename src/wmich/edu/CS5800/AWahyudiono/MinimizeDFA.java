package wmich.edu.CS5800.AWahyudiono;
/**
 * DFA Simulator and Minimizer
 * by Agung Wahyudiono
 * 
 * This is the main class
 */
import java.io.IOException;

public class MinimizeDFA {

	public MinimizeDFA() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws IOException {
		ReadFile myreader = new ReadFile("input.txt");
		
		DFA myDFA = myreader.DoRead();
		
		/*
		Transition trans0 = myDFA.getTransition(0);
		System.out.println(trans0.getValue('b'));
		*/
		
		// Test String
		myDFA.ComputeString("aabaabaab");
		
		System.out.print("\n\n");
		
		// Do Minimizing
		Minimizer minimizer = new Minimizer(myDFA);
	}

}
