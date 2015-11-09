package hmw1.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is responsible for reading in words to play with
 * @author guuurris
 *
 */
public class Filehandler {

	
	private File file;
	private FileInputStream in;
	private BufferedReader br;
	private Random r;
	private ConcurrentHashMap<Integer, String> words; 
	
	/**
	 * 
	 * @param path - path to file containing all the words
	 */
	public Filehandler(String path) {
		this.file = new File(path);
		//the size of file should be that big
		words = new ConcurrentHashMap<Integer, String>(25143);
		//open -> read them all -> close file
		openFile();
		fillMap();
		close();
		
	}
	
	/**
	 * Opens the file in which we shall collect all the containing words from
	 * 
	 * @return - tells if file could be opened 
	 */
	private boolean openFile(){
		try {
			in = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in));
		} catch (FileNotFoundException e) {
			
			System.out.println(file.getAbsolutePath());
			return false;//file couldnt be opened
		}
		return true;
	}
	
	
	/**
	 * Stores all words found in file in a threadsafe map structure
	 */
	private void fillMap(){
		
		String word = null;
		try {
			
			//Reader stopp buffer when line is reached
			if(br.ready()){
				int i = 0;
				while( (word = br.readLine()) != null) {
					words.put(i++, word);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * A thread safe way to return a random word 
	 * @return a word that shall be presented to the player
	 */
	public synchronized String getRandomWord() {
		Random r = new Random();
		return words.get(r.nextInt(words.size()));
	}
	
	/**
	 * Closes the pipes working with the file
	 * 
	 * 
	 */
	private void close() {
		try {
			br.close();
			in.close();
			
		} catch (IOException e) {
			//ignores warning
		}
	}
	
	
}
