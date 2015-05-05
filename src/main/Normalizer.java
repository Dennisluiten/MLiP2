package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Normalizer {

	final boolean TRAIN = true;

	
	final int NRTRAINDATA = 61878, NRTESTDATA = 144368, NRFEAT = 94;
	int sizeData;
	File oldFile;
	FileWriter fw;
	BufferedReader reader;
	int [][] oldData;
	double [][] newData;
	String [] classData;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Starting Normalization process.");
		Normalizer n = new Normalizer();
		n.readData();
		n.normalize();
		n.writeData;
	}
	
	public Normalizer() throws IOException{
		if (TRAIN){
			sizeData = NRTRAINDATA;
			oldData = new int [sizeData][NRFEAT];
			newData = new double [sizeData][NRFEAT];
			oldFile = new File("resources\\train.csv");
			fw = new FileWriter("resources\\train-normalized.csv");
			classData = new String [sizeData];
			
		}else{
			sizeData = NRTESTDATA;
			oldData = new int [sizeData][NRFEAT];
			newData = new double [sizeData][NRFEAT];
			oldFile = new File("resources\\test.csv");
			fw = new FileWriter("resources\\test-normalized.csv");
		}
		
		try {
			reader = new BufferedReader(new FileReader(oldFile));
			fw.write(reader.readLine());
			
		} catch (FileNotFoundException e) {
			System.out.println("Er is geen file te vinden.");
			e.printStackTrace();
		}
	}
	
	private void readData() throws IOException{
		for(int d = 0; d<sizeData; d++ ){
			String[] ss = reader.readLine().split(",");
			for(int f = 0; f < NRFEAT; f++){
				oldData[d][f] = Integer.parseInt(ss[f]);
			}
			if(TRAIN){
				classData[d] = ss[NRFEAT];
			}
		}
	}
	
	/**
	 * Normalizing to the range: 0-1
	 */
	private void normalize(){
		for(int f = 1; f < NRFEAT; f++){
			int max = 0;
			for(int d = 0; d < sizeData; d++){
				if (max < oldData[d][f]){
					max = oldData[d][f];
				}
			}
			for(int d = 0; d < sizeData; d++){
				newData[d][f] = oldData[d][f]/(max*1.0);
			}
		}
	}
	
	private void writeData(){
		
	}
	
	

}
