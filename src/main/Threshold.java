package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Threshold {

	final double THRESHOLD = 0.97;

	final int NRTESTDATA = 144368;
	private File oldFile = new File("resources\\submission-KStar-dist.csv");
	private String newFile = "resources\\submission-KStar-threshold"+ THRESHOLD + ".csv";
	private BufferedReader reader;
	
	FileWriter writer;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Starting Threshold adjustment.");
		new Threshold();
		System.out.println("Processes finished.");
	}
	
	public Threshold() throws IOException{
		writer = new FileWriter(newFile);
		try {
			reader = new BufferedReader(new FileReader(oldFile));
			System.out.println(reader.readLine());
		} catch (FileNotFoundException e) {
			System.out.println("Er is geen file te vinden.");
			e.printStackTrace();
		}
		createCSVHeaders();
		
		for(int id = 1; id <= NRTESTDATA; id++){
			String [] ss = reader.readLine().split(",");
			double [] ds = new double [9];
			for(int c = 1; c <=9; c++){
				ds[c-1] = Double.parseDouble(ss[c]);
			}
			writeData(ds, id);
		}
		writer.flush();
		writer.close();
	}
	
	private void writeData(double [] results, int i) throws IOException{
		writer.append(i+ ",");
		boolean aboveThreshold = false;
		for (int j = 0; j < 9; j++){
			if (results[j] >= THRESHOLD)
				aboveThreshold = true; 
		}
		if (aboveThreshold){
			for(int l = 0; l < 9; l++){
				if(results[l] < THRESHOLD)
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
