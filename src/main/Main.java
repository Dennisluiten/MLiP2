package main;

import java.io.FileWriter;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.classifiers.trees.REPTree;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Main {
	private MyFileReader fr;
	public  FastVector wekaAttributes = new FastVector(94);
	private Instances trainSet;
	private boolean train = true, test = true;
	private Classifier classifier;
	FileWriter writer = new FileWriter("resources\\submission.csv");

	
	public static void main(String[] args) throws Exception {
		new Main();
		System.out.println("Processes finished.");
	}
	
	public Main () throws Exception{
		declareFeatureVector();
		trainSet = new Instances ("Rel", wekaAttributes, 61878);
		trainSet.setClassIndex(0);
		if(train){
			System.out.println("Start training.");
			fr = new MyFileReader("train", this);	

			for(int i = 0; i < 61878; i++){
				//System.out.println("INstance nr: " + i);
				trainSet.add(fr.readInstanceTrain());
			}

			
			classifier = new REPTree();
			classifier.buildClassifier(trainSet);
			System.out.println("Saving model");
			SerializationHelper.write("resources\\classifier.model", classifier);
			System.out.println("Model saved");
		}else{
			classifier = (Classifier)SerializationHelper.read("resources\\classifier.model");
		}
		if(test){
			System.out.println("Start Testing.");
			fr = new MyFileReader("test", this);
			double [] classification = new double [9];
			//			testSet = new Instances("Rel", wekaAttributes, 144368);
			createCSVHeaders();
			for(int i = 1; i < 144369; i++){
				Instance instance = fr.readInstanceTest();
				instance.setDataset(trainSet);
				classification = classifier.distributionForInstance(instance);
				createSubmissionFile(classification, i);
				if(i%1000 == 0)
					System.out.println("Classifying Nr: " + i);

			}	
		}
		writer.flush();
		writer.close();
		System.out.println("Done");
	}

	private void createSubmissionFile(double [] results, int i) throws IOException{
		writer.append(i+ ",");
		for (int j = 0; j < 9; j++)
		{
//			System.out.println("Current value: "+ results [j]);
			writer.append(results[j]+ "");	
			if (j < 8){
				writer.append(",");
//				System.out.println("Appending ','");
			}
		}	
		writer.append("\n");

	}
	
	private void createCSVHeaders() throws IOException
	{
		writer.append("id,");
		for (int i = 1; i < 10; i++)
		{
			writer.append("Class_" + i);
			if(i < 9)
				writer.append(",");
		}
		writer.append("\n");
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
