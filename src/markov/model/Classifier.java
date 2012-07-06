/*
 * Author : Naveen Nandan
 */

package markov.model;

abstract public class Classifier {
	
	private Instance trainingDataPlus;
	private Instance trainingDataMinus;
	private Instance testingDataPlus;
	private Instance testingDataMinus;
				
	
	public Classifier () {
		this.trainingDataPlus = new Instance();
		this.trainingDataMinus = new Instance();
		this.testingDataPlus = new Instance();
		this.testingDataMinus = new Instance();
	}

	public Instance getTrainingDataPlus() {
		return trainingDataPlus;
	}

	public void setTrainingDataPlus(Instance trainingDataPlus) {
		this.trainingDataPlus = trainingDataPlus;
	}

	public Instance getTrainingDataMinus() {
		return trainingDataMinus;
	}

	public void setTrainingDataMinus(Instance trainingDataMinus) {
		this.trainingDataMinus = trainingDataMinus;
	}

	public Instance getTestingDataPlus() {
		return testingDataPlus;
	}

	public void setTestingDataPlus(Instance testingDataPlus) {
		this.testingDataPlus = testingDataPlus;
	}

	public Instance getTestingDataMinus() {
		return testingDataMinus;
	}

	public void setTestingDataMinus(Instance testingDataMinus) {
		this.testingDataMinus = testingDataMinus;
	}

	public void trainClassfier() throws Exception {
		
	}

	public void evaluate() throws Exception {
		
	}
}
