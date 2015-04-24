package main;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Main {
	private MyFileReader fr;
	public  FastVector wekaAttributes = new FastVector(94);
	private Instances trainSet;
	private boolean train = true, test = false;
	private Classifier classifier;
	
	public static void main(String[] args) throws Exception {
		new Main();
		System.out.println("Processes finished.");
	}
	
	public Main () throws Exception{
		declareFeatureVector();
		if(train){
			fr = new MyFileReader("train", this);	
			trainSet = new Instances ("Rel", wekaAttributes, 144368);
			for(int i = 0; i < 61878; i++){
				//System.out.println("INstance nr: " + i);
				trainSet.add(fr.readInstanceTrain());
			}
			trainSet.setClassIndex(0);
			classifier = new BayesNet();
			classifier.buildClassifier(trainSet);
			SerializationHelper.write("resources\\classifier.model", classifier);
		}else{
			classifier = (Classifier)SerializationHelper.read("resources\\classifier.model");
		}
		if(test){
			
		}
	}
	
	private void createSubmissionFile(){
		//TODO
	}
	
	private void declareFeatureVector(){
		
		FastVector classVector = new FastVector(9);
		for(int i = 1; i <= 9; i++){
			classVector.addElement("Class_" + i);
		}
		Attribute classAttribute = new Attribute ( "Class", classVector);
		wekaAttributes.addElement(classAttribute);
		for(int i = 1; i <= 93; i++){
			wekaAttributes.addElement(new Attribute("Feature" + i));
			
		}
		
	}

}
