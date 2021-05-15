package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

import org.json.JSONObject;

/**
 * 
 * @author Zhe Wang
 * Student ID: 1064919
 * Username: zwwang4
 * The University of Melbourne
 *
 */


public class DictionaryClient {
	// IP and port
	private static String ip = "localhost";
	private static int port = 2112;
	
	//instance variables
	private static DataInputStream input;
	private static DataOutputStream output;
	private Socket socket;
	
	
	
	public static void main(String[] args) throws UnknownHostException, IOException 
	{
			//open GUI interface
			ClientWindow window = new ClientWindow();
			window.getFrame().setVisible(true);
			
			window.getTextArea().append("Author: Zhe Wang\nStudent id:1064919 \n");
			
			//read from arguments
	    	if(args.length <2) {
	    		window.getTextArea().append("Argument missing, set to default port 2112 \n");
	    	}
	    	else {
	    		try {
	    		ip = args[0];
	    		int setPort = Integer.parseInt(args[1]);
	    		if (setPort > 1023 && setPort < 65354) {
	    			port = setPort;
	    		}else {
	    			window.getTextArea().append("bad port number, number should be in range (1023,65354), set to default port 2112 \n");
	    		}
	    	}
	    		
	    	catch(NumberFormatException e){
	    		window.getTextArea().append("bad port number, port should be an int, set to default port 2112 \n");
	    	}
	    	
	    	window.getTextArea().append("Client trying to connect to ---- ip: "+ip+", port: "+ port + "\n");}
	}
	
	
	
	/**
	 * Constructor
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public DictionaryClient(JTextArea textArea) throws UnknownHostException, ConnectException, IOException{
        try {
        	socket = new Socket(ip, port);
    		input = new DataInputStream(socket.getInputStream());
    	    output = new DataOutputStream(socket.getOutputStream());
//    	    textArea.append("Successfully Connected!!\n");
//    	    textArea.append("========= Welcome to the dictionary! =========== \n");
        } catch (ConnectException e) {
            // Output expected ConnectException.
        	e.printStackTrace();
        	String msg = "Failed to connect to the server, please check the address.";
        	textArea.append(msg);
        	System.out.println(msg);
        } catch (IOException e) {
        	e.printStackTrace();
        	
        }

		
		
	}
	
	
	
	
	/**
	 * Getters
	 */
	public DataOutputStream getOut() {
		return output;
	}
	public DataInputStream getIn() {
		return input;
	}
	
	
	
	/**
	 * create JSON format command for message passing
	 */
	public JSONObject jsonCreate(String action, String word, String meanings) {
		JSONObject command = new JSONObject();
	    command.put("action", action);
	    command.put("word", word);
	    command.put("meanings", meanings);
	    return command;
	}
	
	
	
	/**
	 * Helper methods for input and output
	 */
	public String inputHelper() throws IOException {
    	String message = input.readUTF();
	    return message;
	}
	
	
	
	public void outputHelper(JSONObject command) throws IOException {
    	output.writeUTF(command.toString());
    	System.out.println("Data sent to Server--> " + command.toString());
    	output.flush();
	}
	



}
