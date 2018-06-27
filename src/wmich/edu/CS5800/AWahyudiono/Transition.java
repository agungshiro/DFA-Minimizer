package wmich.edu.CS5800.AWahyudiono;

/**
 * DFA Simulator and Minimizer
 * by Agung Wahyudiono
 * 
 * This class will produce Transition instance in an hashmap
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Transition {
	
	private String strTrans;
	private HashMap map = new HashMap();
	private ArrayList<Character> alphabet = new ArrayList<>();
	
	public Transition(String strTrans, ArrayList<Character> alphabet) {
		// TODO Auto-generated constructor stub
		this.strTrans = strTrans;
		this.alphabet = alphabet;
		
		this.mapping();
	}
	
	public String getValue(Character key) {
		String val = (String) map.get(key);
		return val;
	}
	
	private void mapping() {
		String[] strArr = strTrans.split(",");
		
		for(int i=0;i<alphabet.size();i++) {
			
			map.put(alphabet.get(i), strArr[i]);
			
		}
	}

}