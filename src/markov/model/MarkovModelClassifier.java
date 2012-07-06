/*
 * Author : Naveen Nandan
 */

package markov.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MarkovModelClassifier extends Classifier {

	// Data structure to hold the Maximum Likelihood Estimates and Log-Likelihood Ratios
	private Map<List<String>, Double> trainPlusMLEstimates;
	private Map<List<String>, Double> trainMinusMLEstimates;
	private Map<List<String>, Double> logLikelihoodRatios;
	
	// Data structure to hold the classification scores of test data
	private Map<Integer, Double> testDataPlusEvaluation;
	private Map<Integer, Double> testDataMinusEvaluation;
	
	public MarkovModelClassifier () {
		this.trainPlusMLEstimates = new HashMap<List<String>, Double>();
		this.trainPlusMLEstimates = new HashMap<List<String>, Double>();
		this.logLikelihoodRatios = new HashMap<List<String>, Double>();
		this.testDataPlusEvaluation = new HashMap<Integer, Double>();
		this.testDataMinusEvaluation = new HashMap<Integer, Double>();
	}
	
	public Map<List<String>, Double> getTrainPlusMLEstimates() {
		return trainPlusMLEstimates;
	}

	public void setTrainPlusMLEstimates(
			Map<List<String>, Double> trainPlusMLEstimates) {
		this.trainPlusMLEstimates = trainPlusMLEstimates;
	}

	public Map<List<String>, Double> getTrainMinusMLEstimates() {
		return trainMinusMLEstimates;
	}

	public void setTrainMinusMLEstimates(
			Map<List<String>, Double> trainMinusMLEstimates) {
		this.trainMinusMLEstimates = trainMinusMLEstimates;
	}

	public Map<List<String>, Double> getLogLikelihoodRatios() {
		return logLikelihoodRatios;
	}

	public void setLogLikelihoodRatios(Map<List<String>, Double> logLikelihoodRatios) {
		this.logLikelihoodRatios = logLikelihoodRatios;
	}

	public Map<Integer, Double> getTestDataPlusEvaluation() {
		return testDataPlusEvaluation;
	}

	public void setTestDataPlusEvaluation(
			Map<Integer, Double> testDataPlusEvaluation) {
		this.testDataPlusEvaluation = testDataPlusEvaluation;
	}

	public Map<Integer, Double> getTestDataMinusEvaluation() {
		return testDataMinusEvaluation;
	}

	public void setTestDataMinusEvaluation(
			Map<Integer, Double> testDataMinusEvaluation) {
		this.testDataMinusEvaluation = testDataMinusEvaluation;
	}

	/*
	 * 		Training the Model - Building the classifier
	 */
	@Override
	public void trainClassfier () throws Exception {
		this.trainPlusMLEstimates = calculateMLEstimates(this.getTrainingDataPlus());
		this.trainMinusMLEstimates = calculateMLEstimates(this.getTrainingDataMinus());
		calculateLogLikelihoodRatios(); 
	}
	
	
	/*
	 * 		Testing the Model - Evaluating the classifier
	 */
	@Override
	public void evaluate () throws Exception {
		this.testDataPlusEvaluation = calculateTestScores(this.getTestingDataPlus());
		this.testDataMinusEvaluation = calculateTestScores(this.getTestingDataMinus());
	}
	
	private Map<List<String>, Double> calculateMLEstimates (Instance features)
			throws Exception {
		
		Map<List<String>, Double> estimates = new HashMap<List<String>, Double>();
		int state_transition_count = 0;
		int all_transition_count = 0;
		
		// Calculation of ML estimates using training data
		for(List<String> state_transition : features.getFeatures().keySet())
		{
			all_transition_count = 0;
				
			state_transition_count = features.getFeatures().get(state_transition);
				
			for(List<String> all_transition : features.getFeatures().keySet())
			{
				if(state_transition.get(0).equals(all_transition.get(0)))
				{
					all_transition_count += features.getFeatures().get(all_transition);
				}
			}
			
			estimates.put(state_transition,
				((double) state_transition_count / (double) all_transition_count));
		}
		
		return estimates;
	}
	
	private void calculateLogLikelihoodRatios () throws Exception {
		
		// Calculation of log-likelihood ratios
		for(List<String> train_plus_MLE : trainPlusMLEstimates.keySet())
		{
			// Handling the zero denominator for states that are unique to train_plus model
			if(train_plus_MLE.get(0).equals("X")||train_plus_MLE.get(0).equals("1")
					||train_plus_MLE.get(0).equals("2"))
			{
				logLikelihoodRatios.put(train_plus_MLE, 1.0);
			}
			else
			{
				if(!trainMinusMLEstimates.containsKey(train_plus_MLE))
				{
					logLikelihoodRatios.put(train_plus_MLE, 1.0);
				}
				else
				{
					logLikelihoodRatios.put(train_plus_MLE, 
					Math.log(trainPlusMLEstimates.get(train_plus_MLE) 
						/ trainMinusMLEstimates.get(train_plus_MLE)));
				}
			}
		}
				
		for(List<String> train_minus_MLE : trainMinusMLEstimates.keySet())
		{
			if(!trainPlusMLEstimates.containsKey(train_minus_MLE))
			{
				// Handling the zero denominator for states that are unique to train_minus model
				logLikelihoodRatios.put(train_minus_MLE, -1.0);
			}
		}
	}
	
	private Map<Integer, Double> calculateTestScores (Instance testData) throws Exception {
		
		Map<Integer, Double> testDataEvaluation = new HashMap<Integer, Double>();
		
		double score = 0.0;
		
		int count = 1;
		
		List<String> state_transitions;
		
		// For each instance of test data, compute the classification scores
		for(String instance : testData.getInstances())
		{	
			score = 0.0;
			
			for(int i=0;i<instance.length()-1;i++)
			{
				state_transitions = new LinkedList<String>();
				
				state_transitions.add(instance.substring(i,i+1));
				state_transitions.add(instance.substring(i+1,i+2));
				
				if(logLikelihoodRatios.containsKey(state_transitions))
				{
					score += logLikelihoodRatios.get(state_transitions);
				}
			}
			
			// Length normalization
			testDataEvaluation.put(count, (double) score / testData.getInstances()
					.get(count-1).length());
			count++;
		}
		
		return testDataEvaluation;
	}
}
