package client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import org.json.JSONObject;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

/**
 * 
 * @author Zhe Wang
 * Student ID: 1064919
 * Username: zwwang4
 * The University of Melbourne
 *
 */


public class ClientWindow {
	
	//instance variables
	private JFrame frame;
	private JTextField addWord;
	private JTextField addMeanings;
	private JTextField updateWord;
	private JButton updateButton;
	private JTextField updateMeanings;
	private JTextField removeWord;
	private JButton removeButton;
	private JTextField queryWord;
	private JButton queryButton;
	private JTextArea textArea;
	private JTextField filePath;
	private JButton loadButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the application.- COnstructor
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public ClientWindow() throws UnknownHostException, IOException {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	private void initialize() throws UnknownHostException, IOException {
		//DictionaryClient client = new DictionaryClient();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		
		//TextArea used to show messages from server
		textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 12;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 5;
		frame.getContentPane().add(textArea, gbc_textArea);
		
		
		
		//Load file field and button
		filePath = new JTextField();
		GridBagConstraints gbc_filePath = new GridBagConstraints();
		gbc_filePath.gridwidth = 10;
		gbc_filePath.insets = new Insets(0, 0, 5, 5);
		gbc_filePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_filePath.gridx = 0;
		gbc_filePath.gridy = 4;
		frame.getContentPane().add(filePath, gbc_filePath);
		filePath.setColumns(10);
		filePath.setUI(new JTextFieldHint(" Enter the path of CSV file", Color.gray));  
		
		loadButton = new JButton("Load file");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createActionNoMeaning(filePath, "load");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_loadButton = new GridBagConstraints();
		gbc_loadButton.insets = new Insets(0, 0, 5, 0);
		gbc_loadButton.gridx = 11;
		gbc_loadButton.gridy = 4;
		frame.getContentPane().add(loadButton, gbc_loadButton);
		
		

		
		//Add word field and button
		addWord = new JTextField();
		GridBagConstraints gbc_addWord = new GridBagConstraints();
		gbc_addWord.gridwidth = 1;
		gbc_addWord.insets = new Insets(0, 0, 5, 5);
		gbc_addWord.fill = GridBagConstraints.HORIZONTAL;
		gbc_addWord.gridx = 0;
		gbc_addWord.gridy = 0;
		frame.getContentPane().add(addWord, gbc_addWord);
		addWord.setColumns(10);
		addWord.setUI(new JTextFieldHint(" Enter a word to add", Color.gray));  
		
		addMeanings = new JTextField();
		GridBagConstraints gbc_addMeanings = new GridBagConstraints();
		gbc_addMeanings.gridwidth = 8;
		gbc_addMeanings.insets = new Insets(0, 0, 5, 5);
		gbc_addMeanings.fill = GridBagConstraints.HORIZONTAL;
		gbc_addMeanings.gridx = 2;
		gbc_addMeanings.gridy = 0;
		frame.getContentPane().add(addMeanings, gbc_addMeanings);
		addMeanings.setColumns(10);
		addMeanings.setUI(new JTextFieldHint(" Enter the meanings, seperate by comma", Color.gray));  

		JButton addButton = new JButton("Add word");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					createAction(addWord, addMeanings, "add");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}}
			
		);
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.insets = new Insets(0, 0, 5, 0);
		gbc_addButton.gridx = 11;
		gbc_addButton.gridy = 0;
		frame.getContentPane().add(addButton, gbc_addButton);
		
		
		
		//Update word field and button
		updateWord = new JTextField();
		GridBagConstraints gbc_updateWord = new GridBagConstraints();
		gbc_updateWord.insets = new Insets(0, 0, 5, 5);
		gbc_updateWord.fill = GridBagConstraints.HORIZONTAL;
		gbc_updateWord.gridx = 0;
		gbc_updateWord.gridy = 1;
		frame.getContentPane().add(updateWord, gbc_updateWord);
		updateWord.setColumns(10);
		updateWord.setUI(new JTextFieldHint(" Enter a word to update", Color.gray)); 
		
		updateMeanings = new JTextField();
		GridBagConstraints gbc_updateMeanings = new GridBagConstraints();
		gbc_updateMeanings.gridwidth = 8;
		gbc_updateMeanings.insets = new Insets(0, 0, 5, 5);
		gbc_updateMeanings.fill = GridBagConstraints.HORIZONTAL;
		gbc_updateMeanings.gridx = 2;
		gbc_updateMeanings.gridy = 1;
		frame.getContentPane().add(updateMeanings, gbc_updateMeanings);
		updateMeanings.setColumns(10);
		updateMeanings.setUI(new JTextFieldHint(" Enter the meanings, seperate by comma", Color.gray)); 
		
		updateButton = new JButton("Update word");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createAction(updateWord, updateMeanings, "update");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		GridBagConstraints gbc_updateButton = new GridBagConstraints();
		gbc_updateButton.insets = new Insets(0, 0, 5, 0);
		gbc_updateButton.gridx = 11;
		gbc_updateButton.gridy = 1;
		frame.getContentPane().add(updateButton, gbc_updateButton);
		
		
		
		//Remove word field and button
		removeWord = new JTextField();
		GridBagConstraints gbc_removeWord = new GridBagConstraints();
		gbc_removeWord.insets = new Insets(0, 0, 5, 5);
		gbc_removeWord.fill = GridBagConstraints.HORIZONTAL;
		gbc_removeWord.gridx = 0;
		gbc_removeWord.gridy = 2;
		frame.getContentPane().add(removeWord, gbc_removeWord);
		removeWord.setColumns(10);
		removeWord.setUI(new JTextFieldHint(" Enter a word to remove", Color.gray)); 
		
		removeButton = new JButton("Remove word");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createActionNoMeaning(removeWord, "remove");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_removeButton = new GridBagConstraints();
		gbc_removeButton.insets = new Insets(0, 0, 5, 0);
		gbc_removeButton.gridx = 11;
		gbc_removeButton.gridy = 2;
		frame.getContentPane().add(removeButton, gbc_removeButton);
		
		
		
		//Query word field and button
		queryWord = new JTextField();
		GridBagConstraints gbc_queryWord = new GridBagConstraints();
		gbc_queryWord.insets = new Insets(0, 0, 5, 5);
		gbc_queryWord.fill = GridBagConstraints.HORIZONTAL;
		gbc_queryWord.gridx = 0;
		gbc_queryWord.gridy = 3;
		frame.getContentPane().add(queryWord, gbc_queryWord);
		queryWord.setColumns(10);
		queryWord.setUI(new JTextFieldHint(" Enter a word to query", Color.gray)); 
		
		queryButton = new JButton("Query word");
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createActionNoMeaning(queryWord, "query");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_queryButton = new GridBagConstraints();
		gbc_queryButton.insets = new Insets(0, 0, 5, 0);
		gbc_queryButton.gridx = 11;
		gbc_queryButton.gridy = 3;
		frame.getContentPane().add(queryButton, gbc_queryButton);
	}
	
	
	
	
	
	/**
	 * These two methods 1. create JSON command and use outputHelper to send; 
	 * 				     2. Use inputHelper to get server input, shown in message area
	 * @param wordField
	 * @param meaningField
	 * @param action
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void createAction(JTextField wordField, JTextField meaningField,  String action) throws ConnectException, UnknownHostException, IOException {

			DictionaryClient client = new DictionaryClient(textArea);
			String word = wordField.getText();
			String meanings = meaningField.getText();
			
			JSONObject command = client.jsonCreate(action, word, meanings);	
			client.outputHelper(command);

			String message = client.inputHelper();
			textArea.append(message);
			System.out.println(message);

			
	}
	
	public void createActionNoMeaning(JTextField wordField, String action) throws UnknownHostException, IOException {
		DictionaryClient client = new DictionaryClient(textArea);
		String word = wordField.getText();
		String meanings = "";
		JSONObject command = client.jsonCreate(action, word, meanings);

		
			try {
				client.outputHelper(command);
				
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				String message = client.inputHelper();
				textArea.append(message);
				System.out.println(message);
				}
			 catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	
	
	
	/**
	 * Getters
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}

}
