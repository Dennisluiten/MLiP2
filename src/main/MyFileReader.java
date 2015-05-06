package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.Instance;

public class MyFileReader {
	private File file;
	private BufferedReader reader;
	private Main main;
	
	public MyFileReader (String testOrTrain, Main main) throws IOException{
		this.main = main;
		file = new File("resources\\"+ testOrTrain + "-normalized.csv");
		try {
			reader = new BufferedReader(new FileReader(file));
			System.out.println(reader.readLine());
		} catch (FileNotFoundException e) {
			System.out.println("Er is geen file te vinden.");
			e.printStackTrace();
		}
	}
	
	public Instance readInstanceTrain() throws IOException{
		Instance instance = new Instance(94);
		String nextLine = reader.readLine();
//		System.out.println(nextLine);
		if(nextLine != null){
			String [] ss = nextLine.split(",");
			for(int i= 1; i< 94; i++){
				//System.out.println(i + ": " + ss[i]);
				instance.setValue((Attribute) main.wekaAttributes.elementAt(i), Double.parseDouble(ss[i]));

			}
			instance.setValue((Attribute) main.wekaAttributes.elementAt(0), ss[94]);

		}
		else{System.out.println("End of File reached.");}
		return instance;
	}
	
	public Instance readInstanceTest() throws IOException{
		Instance instance = new Instance(94);
		String nextLine = reader.readLine();
//		System.out.println(nextLine);
//		if(nextLine != null){
			String [] ss = nextLine.split(",");
			for(int i= 1; i< 94; i++){
				//System.out.println(i + ": " + ss[i]);
				instance.setValue((Attribute) main.wekaAttributes.elementAt(i), Double.parseDouble(ss[i]));

			}
//		}
//		else{System.out.println("End of File reached.");}
		return instance;
	}

}