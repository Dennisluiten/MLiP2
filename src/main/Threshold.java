package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Threshold {

	private File file;
	private BufferedReader reader;
	
	FileWriter writer = new FileWriter("resources\\submissionthreshold.csv");
	
	public static void main(String[] args) throws Exception {
		new Threshold();
		System.out.println("Processes finished.");
	}
	
	public Threshold() throws IOException{
		file = new File("resources\\submission-KStar-dist.csv");
		try {
			reader = new BufferedReader(new FileReader(file));
			System.out.println(reader.readLine());
		} catch (FileNotFoundException e) {
			System.out.println("Er is geen file te vinden.");
			e.printStackTrace();
		}
		createCSVHeaders();
		
		createSubmissionFile( iets ,i);
	}
	
	private void createSubmissionFile(double [] results, int i) throws IOException{
		writer.append(i+ ",");
		boolean threshold = false;
		for (int j = 0; j < 9; j++){
			if (results[j] >= 0.97)
				threshold = true; 
		}
		if (threshold){
			for(int l = 0; l < 9; l++){
				if(results[l] < 0.97)
					writer.append("0");
				else
					writer.append("1");
				if (l < 8)
					writer.append(",");
			}
		}
		else{
			for(int k = 0; k < 9; k++){
				writer.append(results[k] + "");
				if (k < 8)
					writer.append(",");
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
	
}
