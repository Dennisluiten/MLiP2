package main;

import java.io.FileWriter;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Main {
	private MyFileReader fr;
	public  FastVector wekaAttributes = new FastVector(94);
	private Instances trainSet, testSet;
	private boolean train = false, test = true;
	private Classifier classifier;
	
	public static void main(String[] args) throws Exception {
		new Main();
		System.out.println("Processes finished.");
	}
	
	public Main () throws Exception{
		declareFeatureVector();
		trainSet = new Instances ("Rel", wekaAttributes, 61878);
		trainSet.setClassIndex(0);
		if(train){
			fr = new MyFileReader("train", this);	

			for(int i = 0; i < 61878; i++){
				//System.out.println("INstance nr: " + i);
				trainSet.add(fr.readInstanceTrain());
			}

			classifier = new BayesNet();
			classifier.buildClassifier(trainSet);
			SerializationHelper.write("resources\\classifier.model", classifier);
		}else{
			classifier = (Classifier)SerializationHelper.read("resources\\classifier.model");
		}
		if(test){
			fr = new MyFileReader("test", this);
			int [] classifications = new int [144368];
			testSet = new Instances("Rel", wekaAttributes, 144368);
			for(int i = 0; i < 144368; i++){
			Instance instance = fr.readInstanceTest();
			instance.setDataset(trainSet);
			classifications[i] = (int) classifier.classifyInstance(instance)+1;
			System.out.println("Classification int:  "+ classifications[i]);
		
			}
			createSubmissionFile(classifications);
		}
		
	}
	
	private void createSubmissionFile(int[] results) throws IOException{
		System.out.println("Submission file");
		FileWriter writer = new FileWriter("resources\\submission.csv");
		createCSVHeaders(writer);
		int counter = 1;
		for (int result : results)
		{ //Todo, id, 1 en 
			writer.append(counter + ",");
			for (int j = 1; j < 10; j++)
			{
				if (j == result)
					writer.append("1");
				else
					writer.append("0");	
				if (j < 9)
					writer.append(",");
			}
			writer.append("\n");
			counter++;
		}
		writer.flush();
		writer.close();
		System.out.println("Done");
		
	}
	
	private void createCSVHeaders(FileWriter fw) throws IOException
	{
		fw.append("id,");
		for (int i = 1; i < 10; i++)
		{
			fw.append("Class_" + i);
			if(i < 9)
				fw.append(",");
		}
		fw.append("\n");
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
