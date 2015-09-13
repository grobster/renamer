package com.grobster.work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/*
 * @version 10.1.0
 * @author Jeffery Quarles
 */

public class RenamerView implements ActionListener, FileRenamerObserver {
	JLabel programLabel;
	JLabel enterDateLabel;
	JTextField dateField;
	JButton renameButton;
	JPanel mainPanel;
	JPanel enterTextPanel;
	JButton dateButton;
	JFrame frame;
	JPanel renameButtonPanel;
	FileRenamerInterface renamer;
	JLabel numberOfFilesRenamedLabel;
	JPanel numberPanel;
	JLabel numbersLabel;
	JComboBox<String> modeCombo;
	String[] selections = {"Auto", "Manual"};
	String[] companies = {"LMS", "PLM"};
	JPanel comboPanel;
	JLabel modeLabel;
	JTextArea area;
	JScrollPane scroller;
	JPanel passwordPanel;
	private final String fileNameSeperator = "  <----  ";
	JPanel scrollerPanel;
	JComboBox combo;
	JPanel companyPanel;
	JLabel companyLabel;
	
	public RenamerView(FileRenamerInterface renamer) {
		this.renamer = renamer;
		renamer.setAutoFolderName();
		renamer.registerObserver((FileRenamerObserver) this);
	}
	
	public void createView() {
		frame = new JFrame("M4.01a Photo Renamer v10.1.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 400);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		
		enterTextPanel = new JPanel();
		companyPanel = new JPanel();
		mainPanel.add(companyPanel);
		mainPanel.add(enterTextPanel);
		enterDateLabel = new JLabel("Folder Name:");
		enterDateLabel.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		enterTextPanel.add(enterDateLabel);
		dateField = new JTextField(5);
		dateField.setEditable(false);
		enterTextPanel.add(dateField);
		dateButton = new JButton("Set");
		dateButton.addActionListener(this);
		dateButton.setEnabled(false);
		dateButton.setForeground(Color.blue);
		enterTextPanel.add(dateButton);
		renameButtonPanel = new JPanel();
		mainPanel.add(renameButtonPanel);
		renameButton = new JButton("Rename");
		renameButton.setForeground(Color.blue);
		renameButton.addActionListener(this);
		renameButtonPanel.add(renameButton);
		
		modeCombo = new JComboBox<String>(selections);
		modeCombo.setForeground(Color.blue);
		modeCombo.addActionListener(this);
		modeLabel = new JLabel("Mode:");
		modeLabel.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		comboPanel = new JPanel();
		mainPanel.add(comboPanel);
		comboPanel.add(modeLabel);
		comboPanel.add(modeCombo);
		
		
		numberPanel = new JPanel();
		mainPanel.add(numberPanel);
		numberOfFilesRenamedLabel = new JLabel("Number of files renamed:");
		numberOfFilesRenamedLabel.setForeground (Color.red);
		numberOfFilesRenamedLabel.setFont(new Font("Sans-Serif", Font.BOLD, 13));
		numberPanel.add(numberOfFilesRenamedLabel);
		numbersLabel = new JLabel("0");
		numbersLabel.setFont(new Font("Sans-Serif", Font.BOLD, 13));
		numbersLabel.setForeground (Color.red);
		numberPanel.add(numbersLabel);
		
		passwordPanel = new JPanel();
		
		area = new JTextArea(10, 30);
		scroller = new JScrollPane(area);
		scrollerPanel = new JPanel();
		scrollerPanel.add(scroller);
		frame.getContentPane().add(BorderLayout.SOUTH, scrollerPanel);
		
		companyLabel = new JLabel("Company:");
		companyLabel.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		companyPanel.add(companyLabel);
		combo = new JComboBox<String>(companies);
		combo.addActionListener(this);
		combo.setForeground(Color.blue);
		combo.setSelectedIndex(1); //
		companyPanel.add(combo);
		
		frame.getRootPane().setDefaultButton(renameButton);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dateButton) {
				renamer.setManualFolderName(dateField.getText().trim());
		} else if (e.getSource() == renameButton) {
			area.setText("");
			if (modeCombo.getSelectedItem().toString().equals("Auto")) {
				renamer.setAutoFolderName();
				renamer.renameFiles();
				numbersLabel.setText(Integer.toString(renamer.getNumberOfFilesRenamed()));
			} else if (modeCombo.getSelectedItem().toString().equals("Manual")) {
				System.out.println(modeCombo.getSelectedItem().toString().equals("Manual")); //take out
				renamer.renameFiles();
				numbersLabel.setText(Integer.toString(renamer.getNumberOfFilesRenamed()));
			}
		} else if (e.getSource() == modeCombo) {
			if (modeCombo.getSelectedItem().toString().equals("Auto")) {
				dateField.setText("");
				dateField.setEditable(false);
				dateButton.setEnabled(false);
				renamer.setAutoFolderName();
			} else {
				dateField.setEditable(true);
				dateField.requestFocus();
				dateButton.setEnabled(true);
			}
		} else if (e.getSource() == combo) {
			if (combo.getSelectedItem().toString().equals("LMS")) {
				renamer.setBaseFolderName("\\\\mycommunity.ugs.com@SSL\\DavWWWRoot\\IT\\GSM\\SS\\DC\\cvgops\\PKI\\LMS Photos\\");
			} else {
				renamer.setBaseFolderName("\\\\mycommunity.ugs.com@SSL\\DavWWWRoot\\IT\\GSM\\SS\\DC\\cvgops\\PKI\\Tentech\\");
			}
		}
	}
	
	public void update() {
		String oldName = renamer.getOldFileName();
		String newName = renamer.getNewFileName();
		StringBuilder oldAndNew = new StringBuilder();
		oldAndNew.append(newName + fileNameSeperator + oldName);
		area.append(oldAndNew.toString() + "\n");
	}

}