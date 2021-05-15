package server;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import javax.net.ServerSocketFactory;

import org.json.JSONObject;



/**
 * 
 * @author Zhe Wang
 * Student ID: 1064919
 * Username: zwwang4
 * The University of Melbourne
 *
 */


public class DictionaryServer {
	// Declare the default port number and initial file path
	private static int port = 2112;
	private static String ip ;
	private static String fileName = "/Users/Zhe/Documents/COMP90015DS/ass1/initial.csv";
	
	// Identifies the request numbers
	private static int counter = 0;
	private static Dictionary dict = new Dictionary();
	private static ServerWindow window;
	
	
	
	
	
	
    public static void main(String[] args){ 	
    	//load GUI interface
		window = new ServerWindow();
		window.getFrame().setVisible(true);
		
		
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//read from arguments
    	if(args.length <2) {
    		window.getMessage().setText("argument missing, set to default port 2112, no initial file uploaded \n");
    	}
    	else {
    		try {
    		fileName = args[1];
    		int setPort = Integer.parseInt(args[0]);
    		if (setPort > 1023 && setPort < 65354) {
    			port = setPort;
    			//window.getMessage().setText("bad port number, number should be in range (1023,65354), set to default port 2112 \n");
    		}else {
    			window.getMessage().setText("bad port number, number should be in range (1023,65354), set to default port 2112 \n");
    		}
    		}
    		catch(NumberFormatException e) {
    			window.getMessage().setText("bad port number, port should be an int, set to default port 2112 \n");
    		}
    		
    		
    		
    	}
    	
    	
    	//load initial data set
    	if(readCSVData(fileName)==1) {
    		window.getMessage().append("File loaded successfully!\n");
    	}
    	else {
    		window.getMessage().append("File not found. \n");
    	}
        

        
		ServerSocketFactory factory = ServerSocketFactory.getDefault(); 
		try(ServerSocket server = factory.createServerSocket(port))
		{
			
			System.out.println("Waiting for client connection-");
			window.getMessage().append("Server listening on ip: "+ ip + ", port:"+ port + "\nWaiting for client connection--\n");
			// Wait for connections.
			while(true)
			{ 
				Socket client = server.accept(); 
				counter++;
				System.out.println("Request "+counter+": Applying for connection! ");
				window.getMessage().setText("Request "+counter+": Applying for connection! \n");
				Thread t = new Thread(() -> serveClient(client, window));
				t.start(); 
			}
		} 
		catch (BindException e) {
			window.getMessage().setText("Address already in use, please check and start again");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	

}
    
    
    
    
    
	private static void serveClient(Socket client, ServerWindow window) 
	{
		try(Socket clientSocket = client)
		{
			DataInputStream input = new DataInputStream(clientSocket.getInputStream());
		    DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

		    
		    //read input
		    String readString = input.readUTF();
		    window.getMessage().append("command: " + readString+"\n");
		    System.out.println("command: " + readString);
		    
		    JSONObject readCommand = new JSONObject(readString.toString());
		    
		    String word = readCommand.getString("word");
		    String meaningString = readCommand.getString("meanings");
	    	String[] meanings = meaningString.split(",");
	    	
		    
		    
	    	// 5 types of actions: add, update, remove, query, load
	    	// output: message send to client
	    	String message = null;                               
		    if (readCommand.get("action").equals("add")) {
		    	message = dict.add(word, meanings);
		    }
		    else if (readCommand.get("action").equals("update")) {
		    	message = dict.update(word, meanings);
		    }
		    else if (readCommand.get("action").equals("remove")) {
		    	message = dict.remove(word);
		    }
		    else if (readCommand.get("action").equals("query")) {
		    	message = dict.query(word);
		    }
		    else if (readCommand.get("action").equals("load")) {
		    	fileName = word;
		    	if(readCSVData(fileName)==1) {
		    		message = "File loaded successfully!";
		    	}
		    	else {
		    		message = "File not found ";
		    	}
		    	
		    }
		    window.getMessage().append(message+"\n");
		    output.writeUTF(message+"\n");
		    System.out.println(message+"\n");
		    }
		    
		catch (EOFException e) {
			e.printStackTrace();
			}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		//System.out.println(Arrays.asList(dict.getDict()));
		
		
		//dictionary window
		String dictionary = "word: meanings \n";
		for (Map.Entry<String, List<String>> entry : dict.getDict().entrySet()) {
		    dictionary += entry.getKey()+" : "+entry.getValue()+"\n";
		}
		window.getDict().setText(dictionary);
		
		
	}
	
	
	/**
	 * Load dictionary data from CSV file
	 * @param path
	 */
	private static int readCSVData(String path) {
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            List<String[]> inputList = reader.readAll();
            inputList.remove(0);
            for(String[] input : inputList) {
            	int flag = dict.loadDict(input);
            	if(flag == -1) {
            		System.out.println("Empty word exists, please check the file.");
            	}
            	else if(flag == -2) {
            		System.out.println("Empty meanings exists, please check the file.");
            	}
            }
            System.out.println("Loading finished!");
            return 1;
        }
        catch(CsvException e) {
        	System.out.println("file not found.");
        	return -1;
        }
        catch(IOException e) {
        	System.out.println("file not found.");
        	return -1;
        }
	}
	
}
