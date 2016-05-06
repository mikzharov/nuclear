package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HighScores {
	
	//2 dimensional arrays
	public static List<List<String>> namesAndScores = new ArrayList<List<String>>();
	public static List<List<String>> namesAndScoresSorted = new ArrayList<List<String>>();
	
	File f = new File("highscores/highscores.txt");
	
	//file i/o
	/**
	 * Reads the High score file
	 * @throws IOException Throws if it is not found / etc.
	 */
	public void read() throws IOException {
		if (f.exists() && !f.isDirectory()) {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			String[] parts;
			
			while((line = br.readLine()) != null) {
				parts = line.split("-");
				if(parts.length>1)
				namesAndScores.add(Arrays.asList(parts[0], parts[1]));
			}
		fr.close();
		br.close();
		}
	}
	/**
	 * Writes the highscore to the file
	 * @param name The file name
	 * @param score The string score
	 * @throws IOException File not found / access denied / etc.
	 */
	public void write(String name, String score) throws IOException {
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		ArrayList<Integer> sortedList = new ArrayList<Integer>();
		
		namesAndScores.add(Arrays.asList(name, score));
		
		//grab all the scores and store them in another arraylist as int values
		for(int i=0; i<namesAndScores.size(); i++) {
			sortedList.add(Integer.parseInt(namesAndScores.get(i).get(1)));
		}
		
		//sort from highest to lowest
		Collections.sort(sortedList, Collections.reverseOrder());
		
		for (int i=0; i<sortedList.size(); i++) {
			for (int j=0; j<namesAndScores.size(); j++) {
				if (String.valueOf(sortedList.get(i)).equals(namesAndScores.get(j).get(1))) {
					bw.write(namesAndScores.get(j).get(0));
					bw.write("-");
					bw.write(namesAndScores.get(j).get(1));bw.newLine();
					namesAndScoresSorted.add(Arrays.asList(namesAndScores.get(j).get(0), namesAndScores.get(j).get(1)));
				}
			}
		}
		bw.close();
		fw.close();
	}
}
