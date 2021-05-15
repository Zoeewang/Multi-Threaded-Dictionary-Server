package server;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

/**
 * 
 * @author Zhe Wang
 * Student ID: 1064919
 * Username: zwwang4
 * The University of Melbourne
 *
 */

/**
 * 
 * This class
 *
 */

public class ServerWindow {

	//instance variables
	private JFrame frame;
	private JTextArea messageArea;
	private JTextArea dictArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		//Message Area
		JLabel messages = new JLabel("Messages");
		GridBagConstraints gbc_messages = new GridBagConstraints();
		gbc_messages.gridwidth = 2;
		gbc_messages.insets = new Insets(0, 0, 5, 5);
		gbc_messages.gridx = 0;
		gbc_messages.gridy = 0;
		frame.getContentPane().add(messages, gbc_messages);
		
		messageArea = new JTextArea();
		GridBagConstraints gbc_messageArea = new GridBagConstraints();
		gbc_messageArea.insets = new Insets(0, 0, 5, 0);
		gbc_messageArea.fill = GridBagConstraints.BOTH;
		gbc_messageArea.gridx = 2;
		gbc_messageArea.gridy = 0;
		frame.getContentPane().add(messageArea, gbc_messageArea);
		
		
		//Dictionary Area
		JLabel dict = new JLabel("Dictionary");
		GridBagConstraints gbc_dict = new GridBagConstraints();
		gbc_dict.gridwidth = 2;
		gbc_dict.insets = new Insets(0, 0, 5, 5);
		gbc_dict.gridx = 0;
		gbc_dict.gridy = 1;
		frame.getContentPane().add(dict, gbc_dict);
		
		dictArea = new JTextArea();
		GridBagConstraints gbc_dictArea = new GridBagConstraints();
		gbc_dictArea.gridheight = 5;
		gbc_dictArea.insets = new Insets(0, 0, 5, 0);
		gbc_dictArea.fill = GridBagConstraints.BOTH;
		gbc_dictArea.gridx = 2;
		gbc_dictArea.gridy = 1;
		frame.getContentPane().add(dictArea, gbc_dictArea);
	}
	
	
	/**
	 * Getters
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	public JTextArea getMessage() {
		return messageArea;
	}
	
	public JTextArea getDict() {
		return dictArea;
	}
	
	
	
}
