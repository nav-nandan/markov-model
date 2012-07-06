/*
 * Author : Naveen Nandan
 */

package markov.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/*
 * 		Author 		: Naveen Nandan
 * 		Programme 	: Master of Science (Bioinformatics)
 * 		Date		: March 2012
 * 		School		: School of Computer Engineering
 * 		University	: Nanyang Technological University
 * 		Course		: Computational Biology
 * 		Description	: This project builds a machine learning classifier (based on Markov model)
 * 				  that trains on a set of plus (exon) and minus (non-exon) DNA sequences and
 * 				  predicts the following test sequences as either exon or non-exon based on
 * 				  the classifier model.
 */

public class ExonPredictor {
	
	public static void main (String args []) {
		try {
			
			MarkovModelClassifier classifier = ExonPredictor.loadData();
			classifier.trainClassfier();
			classifier.evaluate();
			ExonPredictor.getClassifierStatistics(classifier);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static MarkovModelClassifier loadData () throws Exception {
		
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/markov/model/config.properties"));
		
		File dir = new File (properties.getProperty("directoryPath"));
		
		File [] files = dir.listFiles();
		
		Instance trainPlusFeatures;
		Instance trainMinusFeatures;
		Instance testDataPlus;
		Instance testDataMinus;
		
		MarkovModelClassifier classifier = new MarkovModelClassifier();

		for (File file : files) {
			if (!file.getName().endsWith(".txt")) {
				continue;
			}
			else {
				if (file.getName().contains("train_plus")) {
					trainPlusFeatures = new Instance();
					trainPlusFeatures.loadInstances(file, properties
							.getProperty("train_plus"));
					classifier.setTrainingDataPlus(trainPlusFeatures);
				}
				else if (file.getName().contains("train_minus")) {
					trainMinusFeatures = new Instance();
					trainMinusFeatures.loadInstances(file, properties
							.getProperty("train_minus"));
					classifier.setTrainingDataMinus(trainMinusFeatures);
				}
				else if (file.getName().contains("test_plus")) {
					testDataPlus = new Instance();
					testDataPlus.loadInstances(file, properties
							.getProperty("test_plus"));
					classifier.setTestingDataPlus(testDataPlus);
				}
				else if (file.getName().contains("test_minus")) {
					testDataMinus = new Instance();
					testDataMinus.loadInstances(file, properties
							.getProperty("test_minus"));
					classifier.setTestingDataMinus(testDataMinus);
				}
			}
		}
		
		return classifier;
	}
	
	private static void getClassifierStatistics (MarkovModelClassifier classifier)
			throws Exception {
		
		int count = 0;
		
		// Prediction based on calculated scores
		for(int i : classifier.getTestDataPlusEvaluation().keySet())
		{
			if(classifier.getTestDataPlusEvaluation().get(i) >= 0)
				count++;
		}
			
		int tp = count;
		int fn = classifier.getTestingDataPlus().getInstances().size() - count;
				
		count = 0;
			
		// Prediction based on calculated scores
		for(int i : classifier.getTestDataMinusEvaluation().keySet())
		{
			if(classifier.getTestDataMinusEvaluation().get(i) >= 0)
				count++;
		}
				
		int fp = count;
		int tn = classifier.getTestingDataMinus().getInstances().size() - count;
					
					
		/*
		 * 		Classifier performance - Model measures
		*/
		double accuracy = (double) (tp + tn) / (tp + fn + fp + tn);
		double sensitivity = (double) tp / (tp + fn);
		double specficity = (double) tn / (tn + fp);
		double precision = (double) tp / (tp + fp);
		double recall = (double) tp / (tp + fn);
		double Fscore = (double) (2 * precision * recall) / (precision + recall);
		
		System.out.println();
		System.out.println("\t\t***Given Dataset***");
		System.out.println("\t\tTrain plus : " + classifier.getTrainingDataPlus()
				.getInstances().size());
		System.out.println("\t\tTrain minus : " + classifier.getTrainingDataMinus()
				.getInstances().size());
		System.out.println("\t\tTest plus : " + classifier.getTestingDataPlus()
				.getInstances().size());
		System.out.println("\t\tTest minus : " + classifier.getTestingDataMinus()
				.getInstances().size());
		
		System.out.println();
		System.out.println("\t\t***Prediction Results***");
		System.out.println("\t\tTrue positive : " + tp);
		System.out.println("\t\tFalse negative : " + fn);	
		System.out.println("\t\tFalse positive : " + fp);
		System.out.println("\t\tTrue negative : " + tn);
		
		System.out.println();
		System.out.println("\t\t***Model Performance Measures***");
		System.out.println("\t\tAccuracy : " + accuracy);
		System.out.println("\t\tSensitivity : " + sensitivity);
		System.out.println("\t\tSpecificity : " + specficity);
		System.out.println("\t\tPrecision : " + precision);
		System.out.println("\t\tRecall : " + recall);
		System.out.println("\t\tF-measure : " + Fscore);
	}
}
