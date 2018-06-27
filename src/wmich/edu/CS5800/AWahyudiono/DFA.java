package wmich.edu.CS5800.AWahyudiono;
/**
 * DFA Simulator and Minimizer
 * by Agung Wahyudiono
 * 
 * This class will create the DFA object
 */

import java.util.ArrayList;
import java.util.HashMap;

public class DFA {
	
	private String initialState;
	private ArrayList<Character> alphabet;
	private ArrayList<String> finalState;
	private ArrayList<String> states;
	private HashMap<String,Transition> transition;
	
	private String finalComputationState;
	
	public DFA() {
		this.alphabet = new ArrayList<>();
		this.states = new ArrayList<>();
		this.finalState = new ArrayList<>();
		this.transition = new HashMap<>();
	}
	
	/*
	public Transition getTransition(int index) {
		Transition myTrans = transition.get(index);
		return myTrans;
	}*/
	
	public void addAlphabet(char a) {
		
		this.alphabet.add(a);
		
	}
	
	public void addFinalState(String f) {
		
		this.finalState.add(f);
		
	}
	
	public void addState(String state) {
		
		this.states.add(state);
		
	}
	
	public void setInitialState(String initState) {
		
		this.initialState = initState;
		
	}
	
	public ArrayList<String> getFinalState() {
		
		return this.finalState;
		
	}
	
	public ArrayList<String> getState() {
		
		return this.states;
		
	}
	
	public Transition getStateTransition(String state) {
		
		Transition myTrans = transition.get(state);
		return myTrans;
		
	}
	
	public ArrayList<Character> getAlphabet() {
		
		return this.alphabet;
		
	}
	
	public void addTransition(int transIndex, String strTrans) {
		
		Transition myTrans = new Transition(strTrans, alphabet);
		
		transition.put(this.states.get(transIndex), myTrans);
		
	}
	
	public void ComputeString(String str) {
		
		System.out.printf("{%s|%s} <=> \n",this.initialState,str);
		
		doCompute(str,this.initialState);
		
		if(this.finalState.contains(this.finalComputationState)) {
			System.out.println("ACCEPTED");
		} else {
			System.out.println("NOT ACCEPTED");
		}
		
	}
	
	private void doCompute(String str, String state) {
		
		
		char target = str.charAt(0);
		str = str.substring(1);
		
		Transition currentTrans = this.transition.get(state);
		this.finalComputationState = currentTrans.getValue(target);
		System.out.printf("{%s|%s} <=> \n",this.finalComputationState,str);
		
		if(str.length() > 0) {
			doCompute(str,this.finalComputationState);
		} 
	}

}
