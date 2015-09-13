package com.grobster.work;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/*
 * @version 11.0.6
 * @author Jeffery Quarles
 */

public class RenamerView11 implements ActionListener, FileRenamerObserver {
	private JFrame frame;
	private JButton dummyButton;
	private JPanel dummyPanel;
	private JTextArea area;
	private JPanel westPanel;
	private JScrollPane scroller;
	private String[] companies = {"LMS", "PLM"};
	private JComboBox<String> companyCombo;
	private JPanel mainPanel;
	private JPanel companyPanel;
	private JLabel companyLabel;
	private JPanel modePanel;
	private String[] modes = {"Auto", "Manual", "Other"};
	private JComboBox<String> modeCombo;
	private JLabel modeLabel;
	private JPanel inputPanel;
	private JLabel folderNameLabel;
	private JTextField folderField;
	private JButton setButton;
	private JPanel numberFilesRenamedPanel;
	private JLabel numberFilesRenamedLabel;
	private FileRenamerInterface renamer;
	private final String numberFilesString = "Number of Files Renamed: ";
	private final String fileNameSeperator = "  <----  ";
	private JPanel pathPanel;
	private JLabel pathLabel;
	
	public RenamerView11(FileRenamerInterface renamer) {
		this.renamer = renamer;
		renamer.setAutoFolderName();
		renamer.registerObserver((FileRenamerObserver) this);
		//updatePathLabel();
	}
	
	public void createView() {
		numberFilesRenamedLabel = new JLabel(numberFilesString + "0");
		pathPanel = new JPanel();
		pathLabel = new JLabel();
		
		pathPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		pathPanel.add(pathLabel);
		
		
		pathPanel.add(pathLabel);
		numberFilesRenamedLabel.setForeground (Color.blue);
		numberFilesRenamedLabel.setFont(new Font("Sans-Serif", Font.BOLD, 13));
		
		numberFilesRenamedPanel = new JPanel();
		numberFilesRenamedPanel.add(numberFilesRenamedLabel);
		setButton = new JButton("Set");
		setButton.setEnabled(false);
		setButton.addActionListener(this);
		folderField = new JTextField(5);
		folderField.setEditable(false);
		folderNameLabel = new JLabel("Folder Name:");
		folderNameLabel.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		inputPanel = new JPanel();
		inputPanel.add(folderNameLabel);
		inputPanel.add(folderField);
		inputPanel.add(setButton);
		modeLabel = new JLabel("Mode:");
		modeLabel.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		modeCombo = new JComboBox<String>(modes);
		modeCombo.addActionListener(this);
		modePanel = new JPanel();
		modePanel.add(modeLabel);
		modePanel.add(modeCombo);
		companyLabel = new JLabel("Company:");
		companyLabel.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		companyPanel = new JPanel();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		area = new JTextArea(10, 30);
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		scroller = new JScrollPane(area);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		dummyButton = new JButton("Rename");
		dummyButton.addActionListener(this);
		frame = new JFrame("Photo Renamer v11.0.6");
		dummyPanel = new JPanel();
		dummyPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		dummyPanel.add(dummyButton);
		frame.getContentPane().add(BorderLayout.SOUTH, dummyPanel);
		frame.getContentPane().add(BorderLayout.WEST, westPanel);
		
		westPanel.add(scroller);
		westPanel.add(numberFilesRenamedPanel);
		
		companyCombo = new JComboBox<String>(companies);
		companyCombo.addActionListener(this);
		companyCombo.setSelectedIndex(1);
		companyPanel.add(companyLabel);
		companyPanel.add(companyCombo);
		mainPanel.add(companyPanel);
		mainPanel.add(modePanel);
		
		mainPanel.add(inputPanel);
		frame.getContentPane().add(BorderLayout.NORTH, pathPanel);
		frame.getContentPane().add(BorderLayout.EAST, mainPanel);
		frame.getRootPane().setDefaultButton(dummyButton);
		frame.setSize(600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public void disableComponents() {
		setButton.setEnabled(false);
		folderField.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == setButton) {
				renamer.setManualFolderName(folderField.getText().trim());
				updatePathLabel();
		} else if (e.getSource() == dummyButton) {
			area.setText("");
			if (modeCombo.getSelectedItem().toString().equals("Other")) {
				renamer.renameFiles();
				numberFilesRenamedLabel.setText(numberFilesString + Integer.toString(renamer.getNumberOfFilesRenamed()));
			} else if (modeCombo.getSelectedItem().toString().equals("Auto")) {
				renamer.setAutoFolderName();
				updatePathLabel();
				renamer.renameFiles();
				numberFilesRenamedLabel.setText(numberFilesString + Integer.toString(renamer.getNumberOfFilesRenamed()));
			} else if (modeCombo.getSelectedItem().toString().equals("Manual")) {
				System.out.println(modeCombo.getSelectedItem().toString().equals("Manual")); //take out
				updatePathLabel();
				renamer.renameFiles();
				numberFilesRenamedLabel.setText(numberFilesString + Integer.toString(renamer.getNumberOfFilesRenamed()));
			}
		} else if (e.getSource() == modeCombo) {
			if (modeCombo.getSelectedItem().toString().equals("Auto")) {
				companyCombo.setEnabled(true);
				folderField.setText("");
				folderField.setEditable(false);
				setButton.setEnabled(false);
				renamer.setAutoFolderName();
				updatePathLabel();
			} else if (modeCombo.getSelectedItem().toString().equals("Other")) {
				companyCombo.setEnabled(false);
				folderField.setText("");
				folderField.setEditable(false);
				setButton.setEnabled(false);
				try {
					String otherPath = JOptionPane.showInputDialog(null, "Enter Path");
					renamer.setFullDirectoryPath(otherPath);
					updatePathLabel();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				
			} else {
				folderField.setEditable(true);
				companyCombo.setEnabled(true);
				folderField.requestFocus();
				setButton.setEnabled(true);
			}
		} else if (e.getSource() == companyCombo) {
			if (companyCombo.getSelectedItem().toString().equals("LMS")) {
				renamer.setBaseFolderName("\\\\mycommunity.ugs.com@SSL\\DavWWWRoot\\IT\\GSM\\SS\\DC\\cvgops\\PKI\\LMS Photos\\");
				if (modeCombo.getSelectedItem().toString().equals("Auto")) {
					renamer.setAutoFolderName();
					updatePathLabel();
				}

			} else {
				renamer.setBaseFolderName("\\\\mycommunity.ugs.com@SSL\\DavWWWRoot\\IT\\GSM\\SS\\DC\\cvgops\\PKI\\Tentech\\");
				if (modeCombo.getSelectedItem().toString().equals("Auto")) {
					renamer.setAutoFolderName();
					updatePathLabel();
				}
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
	
	public void updatePathLabel() {
		pathLabel.setText(renamer.getFullPathName());
	}
}
