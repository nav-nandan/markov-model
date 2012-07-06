/*
 * Author : Naveen Nandan
 */

package markov.model;

import java.util.HashMap;
import java.util.Map;

public class StateTransitions {
	
	/* Data structure to represent the state transitions */
	private final static Map<String,String> stateTransitions = new HashMap<String, String>();
	
	/* Initialization of the codon sequences or states; used as a lookup table */
	static
	{
		stateTransitions.put("ATT", "I");
		stateTransitions.put("ATC", "I");
		stateTransitions.put("ATA", "I");
		stateTransitions.put("CTT", "L");
		stateTransitions.put("CTC", "L");
		stateTransitions.put("CTA", "L");
		stateTransitions.put("CTG", "L");
		stateTransitions.put("TTA", "L");
		stateTransitions.put("TTG", "L");
		stateTransitions.put("GTT", "V");
		stateTransitions.put("GTC", "V");
		stateTransitions.put("GTA", "V");
		stateTransitions.put("GTG", "V");
		stateTransitions.put("TTT", "F");
		stateTransitions.put("TTC", "F");
		stateTransitions.put("ATG", "M");
		stateTransitions.put("TGT", "C");
		stateTransitions.put("TGC", "C");
		stateTransitions.put("GCT", "A");
		stateTransitions.put("GCC", "A");
		stateTransitions.put("GCA", "A");
		stateTransitions.put("GCG", "A");
		stateTransitions.put("GGT", "G");
		stateTransitions.put("GGC", "G");
		stateTransitions.put("GGA", "G");
		stateTransitions.put("GGG", "G");
		stateTransitions.put("CCT", "P");
		stateTransitions.put("CCC", "P");
		stateTransitions.put("CCA", "P");
		stateTransitions.put("CCG", "P");
		stateTransitions.put("ACT", "T");
		stateTransitions.put("ACC", "T");
		stateTransitions.put("ACA", "T");
		stateTransitions.put("ACG", "T");
		stateTransitions.put("TCT", "S");
		stateTransitions.put("TCC", "S");
		stateTransitions.put("TCA", "S");
		stateTransitions.put("TCG", "S");
		stateTransitions.put("AGT", "S");
		stateTransitions.put("AGC", "S");
		stateTransitions.put("TAT", "Y");
		stateTransitions.put("TAC", "Y");
		stateTransitions.put("TGG", "W");
		stateTransitions.put("CAA", "Q");
		stateTransitions.put("CAG", "Q");
		stateTransitions.put("AAT", "N");
		stateTransitions.put("AAC", "N");
		stateTransitions.put("CAT", "H");
		stateTransitions.put("CAC", "H");
		stateTransitions.put("GAA", "E");
		stateTransitions.put("GAG", "E");
		stateTransitions.put("GAT", "D");
		stateTransitions.put("GAC", "D");
		stateTransitions.put("AAA", "K");
		stateTransitions.put("AAG", "K");
		stateTransitions.put("CGT", "R");
		stateTransitions.put("CGC", "R");
		stateTransitions.put("CGA", "R");
		stateTransitions.put("CGG", "R");
		stateTransitions.put("AGA", "R");
		stateTransitions.put("AGG", "R");
		stateTransitions.put("TAA", "o");
		stateTransitions.put("TAG", "o");
		stateTransitions.put("TGA", "o");
	}

	public static Map<String, String> getStateTransitions() {
		return stateTransitions;
	}
}
