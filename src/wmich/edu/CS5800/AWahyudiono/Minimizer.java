package wmich.edu.CS5800.AWahyudiono;
/**
 * DFA Simulator and Minimizer
 * by Agung Wahyudiono
 * 
 * This class will doing some minimizing procedure and will show each step of minimizing
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Minimizer {
	
	private DFA dfa;
	private ArrayList<String> states;
	private int stateNum;
	private String matrice[][];
	private boolean flags[][];
	private ArrayList<String> finalState;
	private ArrayList<Character> alphabet;
	private ArrayList<String> newState;
	private HashMap<String,Transition> newTransitionTable;

	public Minimizer(DFA dfa) {
		// TODO Auto-generated constructor stub
		this.dfa = dfa;
		this.states = dfa.getState();
		this.stateNum = states.size();
		this.matrice = new String[stateNum][stateNum];
		this.flags = new boolean[stateNum][stateNum];
		this.finalState = dfa.getFinalState();
		this.alphabet = dfa.getAlphabet();
		this.newState = new ArrayList<>();
		this.newTransitionTable = new HashMap<>();
		
		pairing(0,this.stateNum);
		//printTable(this.stateNum-2,this.stateNum-1,0,true);
		checkDistinguishable(0,0,1);
		printTable(this.stateNum-2,this.stateNum-1,0,true);
		System.out.print("\n\n\n");
		checkDistinguishable(0,0,2);
		printTable(this.stateNum-2,this.stateNum-1,0,true);
		System.out.print("\n\n\n");
		checkDistinguishable(0,0,3);
		printTable(this.stateNum-2,this.stateNum-1,0,true);
		buildNewState();
		printNewState();
		
	}
	
	private void buildNewState() {
		newState = states;
		
		for(int i=0;i<this.stateNum;i++) {
			for(int j=0;j<this.stateNum;j++) {
				if(flags[i][j]==true && matrice[i][j] != null) {
					String[] s = matrice[i][j].split(",");
					
					newState.remove(new String(s[0]));
					newState.remove(new String(s[1]));
					newState.add(matrice[i][j]);
				}
			}
		}
		
		//this.simplifyState(0, 1);
	}
	
	private void simplifyState(int i, int j) {
		if(i<newState.size() && j > i) {
			
			if(j<newState.size()) {
				String[] s = newState.get(j).split(",");
				
				if(newState.get(i).contains(s[0]) && newState.get(i).contains(s[1])) {
					newState.remove(j);
				}else if(newState.get(i).contains(s[0])) {
					newState.set(i, newState.get(i)+","+s[1]);
					newState.remove(j);
					
 				}else if(newState.get(i).contains(s[1])) {
 					newState.set(i, newState.get(i)+","+s[0]);
 					newState.remove(j);
 					
 				}
				
				this.simplifyState(i, j+1);
			} else {
				this.simplifyState(i+1, i+2);
			}
		}
	}
	
	private void buildNewTransitionTable() {
		
	}

	
	private void printNewState() {
		System.out.print("\n\nNew States:\n");
		for(String st:newState) {
			System.out.printf("%s \t|\n",st);
		}
	}
	
	private void pairing(int i, int sNum) {
		if(i<sNum) {
			for(int x = i+1 ; x < sNum; x++) {
				//System.out.printf("{%s,%s} \n",this.states.get(i),this.states.get(x));
				
				this.matrice[i][x] = this.states.get(i)+","+this.states.get(x);
				this.flags[i][x] = true;
				
			}	
			i++;
			pairing(i,sNum);
		}
	}
	
	private void checkDistinguishable(int rows, int cols, int mode) {
		if(rows < this.stateNum) {
			if(cols < this.stateNum) {
				
				if(mode == 1) {
					stepOne(rows,cols);
				}
				
				if(mode == 2) {
					stepTwo(rows,cols);
				}
				
				if(mode == 3) {
					stepThree(rows,cols);
				}
				
				checkDistinguishable(rows,cols+1,mode);
				
			} else {
				checkDistinguishable(rows+1,0,mode);
			}
		}
	}
	
	private void stepOne(int row, int col) {
		if(matrice[row][col] != null && flags[row][col] == true) {
			String arrState[] = matrice[row][col].split(",");
			
			if(this.finalState.contains(arrState[0])^this.finalState.contains(arrState[1])) {
				matrice[row][col] = " *1  ";
				flags[row][col] = false;
			}
			
		}
	}
	
	private void stepTwo(int row, int col) {
		if(matrice[row][col] != null && flags[row][col] == true) {
			// Split pairing state by ","
			String arrState[] = matrice[row][col].split(",");
			Transition[] arrStateTrans = new Transition[arrState.length];
			
			for(int i = 0; i<arrState.length;i++) {
				arrStateTrans[i] = dfa.getStateTransition(arrState[i]);
			}
			
			for(char alph:alphabet) {
				if(finalState.contains(arrStateTrans[0].getValue(alph)) ^ finalState.contains(arrStateTrans[1].getValue(alph))) {
					matrice[row][col] = " *2  ";
					flags[row][col] = false;
				}
			}
			
		}
	}
	
	private void stepThree(int row, int col) {
		if(matrice[row][col] != null && flags[row][col] == true) {
			String arrState[] = matrice[row][col].split(",");
			
			Transition t00 = dfa.getStateTransition(arrState[0]);
			Transition t10 = dfa.getStateTransition(arrState[1]);
			
			for(char alph:alphabet ) {
				
				Transition t01 = dfa.getStateTransition(t00.getValue(alph));
				Transition t11 = dfa.getStateTransition(t10.getValue(alph));
				
				for(char alph1:alphabet) {
					if(finalState.contains(t01.getValue(alph1))^finalState.contains(t11.getValue(alph1))) {
						matrice[row][col] = " *3  ";
						flags[row][col] = false;
					}
				}
			}
			
		}
	}
	
	private void printTable(int rows, int cols , int segment, boolean label) {
		if(rows >= 0) {
			
			if(cols > 0 ) {
				if (segment == 0) {
					System.out.print("--------");
				}
				if (segment == 1) {
					if(label) {
						System.out.print("   "+this.states.get(rows)+"   |");
					} else {
						if(this.matrice[rows][cols] == null) {
							System.out.print("       |");
						} else {
							System.out.print("{"+this.matrice[rows][cols]+"}|");
						}
					}
				}
				
				if(label) {
					printTable(rows,cols,segment,false);
				} else {
					printTable(rows,cols-1,segment,false);
				}
				
			} else {
				System.out.print("\n");
				if(segment == 1) {
					printTable(rows-1,this.stateNum-1,0,true);
				} else {
					printTable(rows,this.stateNum-1,segment+1,true);
				}
				
			}
			
		} else {
			
			for (int x=0;x<2;x++) {
				System.out.print("\n");
				for(int y=this.stateNum;y>0;y--) {
					if(x==0) {
						System.out.print("--------");
					} else {
						if(y==this.stateNum) {
							System.out.print("        |");
						} else {
							System.out.print("   "+this.states.get(y)+"  |");
						}
					}
				}
			}
			
		}
	}

}
