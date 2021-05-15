package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @author Zhe Wang
 * Student ID: 1064919
 * Username: zwwang4
 * The University of Melbourne
 *
 */

/**
 * This class is to generate the functionalities of Dictionary
 */

public class Dictionary {
	
	//instance variables
	private Map<String, List<String>> dictData;
	
	
	/**
	 * Constructor
	 */
	public Dictionary() {
		dictData = new HashMap<String, List<String>>();
	}
	
	
	/**
	 * Getter
	 */
	public Map<String, List<String>> getDict(){
		return dictData;
	}
	
	
	
	/**
	 * Functionalities
	 * 5 types of actions: add, update, remove, query, load
	 */
	
	//Query the meaning(s) of a given word
	public synchronized String query(String word){
		word = word.toLowerCase();
		if(!word.matches("[a-zA-Z]+")) {
			return "Sorry, the word \"" + word+ "\""+" must only contain letters, please put a new word.";
		}
		//check existing
		else if(!dictData.containsKey(word)) {
			return "Sorry, the word \"" + word+ "\" "+"doesn't exist, please put a new word.";
		}
		//remove word
		else {
			List<String> list = dictData.get(word);
			String meanings = String.join(", ", list);
			return "The word \"" + word+ "\" "+"has meanings: " + meanings;
		}
	}
	
	
	//Add a new word
	public synchronized String add(String word, String[] meanings) {
		//all to lower case
		word = word.toLowerCase();
		String str= String.join(",", meanings);
		String meanings1[]=str.toLowerCase().split(",");
		//if already exist
		if(dictData.containsKey(word)) {
			return "Sorry, the word \"" + word+ "\" "+"already exists, please put a new word.";
		}
		//check word only letters
		else if(!word.matches("[a-zA-Z]+")) {
			return "Sorry, the word \"" + word+ "\""+" must only contain letters, please put a new word.";
		}
		//check duplicate meaning
		else if(checkDuplicate(meanings1)) {
			return "Sorry, the meanings has duplicates, please check.";
		}
		//add the word & meanings
		else {
			List<String> values = new ArrayList<String>();
			for(int i=0; i<meanings1.length; i++) {
				values.add(meanings1[i]);
			}
			dictData.put(word, values);
			return "The word \"" + word + "\" "+"is successfully added";
		}
		
	}
	
	
	//Remove an existing word
	public synchronized String remove(String word) {
		word = word.toLowerCase();
		//word only letters
		if(!word.matches("[a-zA-Z]+")) {
			return "Sorry, the word \"" + word+ "\""+" must only contain letters, please put a new word.";
		}
		//check existing
		else if(!dictData.containsKey(word)) {
			return "Sorry, the word \"" + word+ "\" "+"doesn't exist, please put a new word.";
		}
		//remove word
		else {
			dictData.remove(word);
			return "The word \"" + word + "\" "+"is successfully removed";
		}
		
	}
	
	
	//Update meaning of an existing word
	public synchronized String update(String word, String[] meanings) {
		//all to lower case
		word = word.toLowerCase();
		String str= String.join(",", meanings);
		String meanings1[]=str.toLowerCase().split(",");
		//check word only letters
		if(!word.matches("[a-zA-Z]+")) {
			return "Sorry, the word \"" + word+ "\""+" must only contain letters, please put a new word.";
		}
		
		//if word not exist
		else if(!dictData.containsKey(word)) {
			return "Sorry, the word \"" + word+ "\" "+"doesn't exist, please put a new word to update or add this word instead.";
		}
		
		//check duplicate meaning
		else if(checkDuplicate(meanings1)) {
			return "Sorry, the meanings has duplicates, please check.";
		}
		
		//update
		else {
			List<String> values = new ArrayList<String>();
			for(int i=0; i<meanings1.length; i++) {
				values.add(meanings1[i]);
			}
			dictData.replace(word, values);
			return "The word \"" + word + "\" "+"is successfully updated";
		}
	}
	

	
	
	
	/**
	 * Helper method for Load dictionary data from CSV file
	 * @return negative number means missing
	 */
	public int loadDict(String[] input) {
		String word = input[0];
		//the word is missing
		if(word.isEmpty()) {
			return -1;
		}
        List<String> list = new ArrayList<String>(Arrays.asList(input));
        list.removeAll(Arrays.asList("", null));
        list.remove(0);
        String[] meanings = list.toArray(new String[0]);
        //word missing meanings
        if(meanings.length == 0) {
        	return -2;
        }
        
        if (!dictData.containsKey(word)) {
        	add(word,meanings);
        	return 1;
        }
        else {
        	update(word,meanings);
        	return 2;
        }
        
        
        
        
	}
	

	
	
	/**
	 * Helper methods, check only letters and duplicates
	 * 
	 */
	public static boolean checkLetter(String[] meanings) {
		for(int i = 0; i < meanings.length; i++) {
			if(meanings[i].matches("[a-zA-Z]+")==false) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkDuplicate(String[] meanings) {
		for (int j=0;j<meanings.length;j++) {
		    for (int k=j+1;k<meanings.length;k++) {
		        if (meanings[k].equals(meanings[j])){ // or use .equals()
		            return true;
		        }
		    }
		}
		return false;
	}
	
	
	

	
	

}
