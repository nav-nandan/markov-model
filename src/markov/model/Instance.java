/*
 * Author : Naveen Nandan
 */

package markov.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Instance {
	
	private List<String> instances;
	private Map<List<String>,Integer> features;
	
	public Instance () {
		this.instances = new LinkedList<String>();
		this.features = new HashMap<List<String>, Integer>();
	}
	
	public List<String> getInstances() {
		return instances;
	}

	public void setInstances(List<String> instances) {
		this.instances = instances;
	}

	public Map<List<String>, Integer> getFeatures() {
		return features;
	}

	public void setFeatures(Map<List<String>, Integer> features) {
		this.features = features;
	}

	public void loadInstances (File file, String category) throws Exception {
		if (category.equals("plus")) {
			loadPositiveInstances(file);
		} else if (category.equals("minus")) {
			loadNegativeInstances(file);
		} else {
			throw new Exception("feature category incorrect");
		}
		
		extractFeatures();
	}
	
	private void loadPositiveInstances (File file) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader(file));
			
		String line = null;
		
		while((line = br.readLine()) != null)
		{
			String replace_sequence = "";
			
			for(int i=3;i<line.length()-2;i++)
			{
				if(i%3 == 0)
				{
					if(StateTransitions.getStateTransitions()
						.containsKey(line.substring(i,i+3)))
					{
						// Removal of stop codons
						if(StateTransitions.getStateTransitions()
							.get(line.substring(i,i+3)).equals("o"))
							break;
						
						replace_sequence += StateTransitions.getStateTransitions()
							.get(line.substring(i,i+3));
					}
				}
			}
		
			// Model for data_plus
			instances.add("X123"+replace_sequence+"X");
		}
		
		br.close();
	}
	
	private void loadNegativeInstances (File file) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader(file));
			
		String line = null;
			
		while((line = br.readLine()) != null)
		{
			String replace_sequence = "";
			
			for(int i=0;i<line.length()-2;i++)
			{
				if(i%3 == 0)
				{
					if(StateTransitions.getStateTransitions()
						.containsKey(line.substring(i,i+3)))
					{
						// Removal of stop codons
						if(StateTransitions.getStateTransitions()
							.get(line.substring(i,i+3)).equals("o"))
							break;
									
							replace_sequence += StateTransitions.getStateTransitions()
								.get(line.substring(i,i+3));
					}
				}
			}
			
			// Model for data_minus
			instances.add("X"+replace_sequence+"X");
		}
		
		br.close();
	}
	
	private Map<List<String>, Integer> extractFeatures () throws Exception {
		
		LinkedList<String> stateTransitions;
		
		int count = 0;
		
		// Populating the counts of each state in data
		for(String feature : instances)
		{	
			for(int i=0;i<feature.length()-1;i++)
			{
				stateTransitions = new LinkedList<String>();
				
				stateTransitions.add(feature.substring(i,i+1));
				stateTransitions.add(feature.substring(i+1,i+2));
				
				if(features.containsKey(stateTransitions))
				{
					count = features.get(stateTransitions) + 1;
					features.put(stateTransitions, count);
				}
				else
				{
					features.put(stateTransitions, 1);
				}
			}
		}
		
		return features;
	}
}
