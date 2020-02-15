import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.*;

public class KeywordColumnarTransposition implements ActionListener {
	JFrame mainFrame;
	JPanel selectionPanel, firstTextPanel, buttonPanel, plainTextPanel, cipherTextPanel, keyPanel;
	JComboBox<String> selectionBox;
	JLabel plaintextLabel, ciphertextLabel, keyLabel;
	JTextArea plainTextField, cipherTextField; 
	JTextField keyTextField;
	JButton encryptionButton, decryptionButton, clearButton;
	String eString = "Encrypt";
	String dString = "Decrypt";
	public KeywordColumnarTransposition(){
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
		selectionBox = new JComboBox<String>();
		selectionBox.addItem(eString);
		selectionBox.addItem(dString);
		selectionPanel = new JPanel();
		selectionPanel.add(selectionBox);
		plaintextLabel = new JLabel("Plaintext: ");
		ciphertextLabel = new JLabel("Ciphertext: ");
		keyLabel = new JLabel("Key: ");
		plainTextField = new JTextArea(5, 20);
		plainTextField.setEditable(false);
		cipherTextField = new JTextArea(5, 20);
		cipherTextField.setEditable(false);
		keyTextField = new JTextField(10);
		keyTextField.setEditable(false);
		firstTextPanel = new JPanel();
		firstTextPanel.setLayout(new BorderLayout());
		plainTextPanel = new JPanel();
		plainTextPanel.setLayout(new FlowLayout());
		cipherTextPanel = new JPanel();
		cipherTextPanel.setLayout(new FlowLayout());
		keyPanel = new JPanel();
		keyPanel.setLayout(new FlowLayout());
		
		plainTextPanel.add(plaintextLabel);
		plainTextPanel.add(plainTextField);
		firstTextPanel.add(plainTextPanel, BorderLayout.NORTH);
		
		cipherTextPanel.add(ciphertextLabel);
		cipherTextPanel.add(cipherTextField);
		firstTextPanel.add(cipherTextPanel, BorderLayout.CENTER);
		
		
		keyPanel.add(keyLabel);
		keyPanel.add(keyTextField);
		firstTextPanel.add(keyPanel, BorderLayout.SOUTH);
		
		buttonPanel = new JPanel();
		encryptionButton = new JButton("Encrypt");
		decryptionButton = new JButton("Decrypt");
		clearButton = new JButton("Clear");
		
		selectionBox.addActionListener(this);
		encryptionButton.addActionListener(this);
		decryptionButton.addActionListener(this);
		clearButton.addActionListener(this);
		
		mainFrame.getContentPane().add(selectionPanel, BorderLayout.NORTH);
		mainFrame.getContentPane().add(firstTextPanel, BorderLayout.CENTER);
		mainFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		mainFrame.setVisible(true);
		mainFrame.pack();
	}
	char resultingArray[][], encryptionArray[][], decryptionArray[][], mainKey[], temporaryKey[];
	public void createEncryptionMatrix(String s, String key, int row, int column) {
		resultingArray = new char[row][column];
		int k = 0;
		mainKey = key.toCharArray();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (k < s.length()) {
					resultingArray[i][j] = s.charAt(k);
					k++;
				} else {
					resultingArray[i][j] = ' ';
				}
			}
		}
	}
	public void createKey(String key, int column) {
		temporaryKey = key.toCharArray();
		for (int i = 0; i < column - 1; i++) {
			for (int j = i + 1; j < column; j++) {
				if (temporaryKey[i] > temporaryKey[j]) {
					char temp = temporaryKey[i];
					temporaryKey[i] = temporaryKey[j];
					temporaryKey[j] = temp;
				}
			}
		}
	}
	public void createDecryptionMatrix(String s, String key, int row, int column) {
		resultingArray = new char[row][column];
		int k = 0;
		mainKey = key.toCharArray();
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row; j++) {
				if (k < s.length()) {
					resultingArray[j][i] = s.charAt(k);
					k++;
				} else {
					resultingArray[j][i] = ' ';
				}
			}
		}
	}
	public void encryptionFunction(int row, int column) {
		encryptionArray = new char[row][column];
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < column; j++) {
				if (mainKey[i] == temporaryKey[j]) {
					for (int k = 0; k < row; k++) {
						encryptionArray[k][j] = resultingArray[k][i];
					}
					temporaryKey[j] = '?';
					break;
				}
			}
		}
	}
	public void decryptionFunction(int row, int column) {
		decryptionArray = new char[row][column];
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < column; j++) {
				if (mainKey[j] == temporaryKey[i]) {
					for (int k = 0; k < row; k++) {
						decryptionArray[k][j] = resultingArray[k][i];
					}
					mainKey[j] = '?';
					break;
				}
			}
		}
	}
	public void encryptionResult(int row, int column, char resultingArray[][]) {
		LinkedList<Character> resultingStringArray = new LinkedList<Character>();
		char x;
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row; j++) {
				x = resultingArray[j][i];
				resultingStringArray.add(x);
			}
		}
		StringBuilder cipherTextStringBuilder = new StringBuilder();
		for (Character pointer : resultingStringArray) {
		    cipherTextStringBuilder.append(pointer);
		}
		String outputCipherText = cipherTextStringBuilder.toString();
		cipherTextField.setText(outputCipherText);
	}
	public void decryptionResult(int row, int column, char resultingArray[][]) {
		LinkedList<Character> resultingStringArray = new LinkedList<Character>();
		char x;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				x = resultingArray[i][j];
				resultingStringArray.add(x);
			}
		}
		StringBuilder plainTextStringBuilder = new StringBuilder();
		for(Character pointer : resultingStringArray){
			plainTextStringBuilder.append(pointer);
		}
		String outputPlaintext = plainTextStringBuilder.toString();
		plainTextField.setText(outputPlaintext);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(selectionBox.getSelectedItem().equals(eString)){
			cipherTextField.setEditable(false);
			plainTextField.setEditable(true);
			keyTextField.setEditable(true);
			buttonPanel.remove(decryptionButton);
			buttonPanel.add(encryptionButton);
			buttonPanel.add(clearButton);
			mainFrame.pack();
			if(e.getSource().equals(encryptionButton)){
				String plainText = plainTextField.getText();
				String keyText = keyTextField.getText();
				int lengthOfPlainText = plainText.length();
				int lengthOfKeyText = keyText.length();
				int numberOfRows = lengthOfPlainText / lengthOfKeyText;
				if(lengthOfPlainText % lengthOfKeyText != 0)
					numberOfRows++;
				int numberOfColumns = keyText.length();
				createEncryptionMatrix(plainText, keyText,
										numberOfRows, numberOfColumns);
				createKey(keyText, numberOfColumns);
				encryptionFunction(numberOfRows, numberOfColumns);
				encryptionResult(numberOfRows, numberOfColumns, encryptionArray);
				
			}
		}
		if(selectionBox.getSelectedItem().equals(dString)){
			plainTextField.setEditable(false);
			cipherTextField.setEditable(true);
			keyTextField.setEditable(true);
			buttonPanel.remove(encryptionButton);
			buttonPanel.add(decryptionButton);
			buttonPanel.add(clearButton);
			mainFrame.pack();
			if(e.getSource().equals(decryptionButton)){
				String cipherText = cipherTextField.getText();
				String keyText = keyTextField.getText();
				int lengthOfCipherText = cipherText.length();
				int lengthOfKeyText = keyText.length();
				int numberOfRows = lengthOfCipherText / lengthOfKeyText;
				if(lengthOfCipherText % lengthOfKeyText != 0)
					numberOfRows++;
				int numberOfColumns = keyText.length();
				createDecryptionMatrix(cipherText, keyText, numberOfRows, numberOfColumns);
				createKey(keyText, numberOfColumns);
				decryptionFunction(numberOfRows, numberOfColumns);
				decryptionResult(numberOfRows, numberOfColumns, decryptionArray);
			}
		}
		if(e.getSource().equals(clearButton)){
			plainTextField.setText("");
			cipherTextField.setText("");
			keyTextField.setText("");
		}
	}
	
	public static void main(String[] args){
		new KeywordColumnarTransposition();
	}
}
