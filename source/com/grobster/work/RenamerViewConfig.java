package com.grobster.work;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
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
import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;

/*
 * @version 11.1.8c - widened the width of the application
 * @author Jeffery Quarles
 */

public class RenamerViewConfig implements FileRenamerObserver {
	private JFrame frame;
	private JButton dummyButton;
	private JPanel dummyPanel;
	private JTextArea area;
	private JPanel westPanel;
	private JScrollPane scroller;
	private String[] companies; // check this out
	private JComboBox<String> companyCombo;  // check this out
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
	private ArrayList<String> selectionsArrayList;
	private HashMap<String, String> hm;
	
	public RenamerViewConfig(FileRenamerInterface renamer) {
		this.renamer = renamer;
		hm = new HashMap<>();
		selectionsArrayList = new ArrayList<>();
		renamer.setAutoFolderName();
		renamer.registerObserver((FileRenamerObserver) this);
		createProgramFolder();
		buildCompanyCombo();
		companies = selectionsArrayList.toArray(new String[selectionsArrayList.size()]);
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
		setButton.addActionListener(new SetButtonListener());
		folderField = new JTextField(10);
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
		modeCombo.addActionListener(new ModeComboListener());
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
		dummyButton.addActionListener(new RenameButtonListener());
		frame = new JFrame("Photo Renamer v11.1.8");
		dummyPanel = new JPanel();
		dummyPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		dummyPanel.add(dummyButton);
		frame.getContentPane().add(BorderLayout.SOUTH, dummyPanel);
		frame.getContentPane().add(BorderLayout.WEST, westPanel);
		
		westPanel.add(scroller);
		westPanel.add(numberFilesRenamedPanel);
		
		companyCombo = new JComboBox<String>(companies);
		companyCombo.addActionListener(new CompanyComboListener());
		//companyCombo.setSelectedIndex(1);
		companyPanel.add(companyLabel);
		companyPanel.add(companyCombo);
		mainPanel.add(companyPanel);
		mainPanel.add(modePanel);
		
		updatePathLabel();
		
		mainPanel.add(inputPanel);
		frame.getContentPane().add(BorderLayout.NORTH, pathPanel);
		frame.getContentPane().add(BorderLayout.EAST, mainPanel);
		frame.getRootPane().setDefaultButton(dummyButton);
		frame.setSize(700, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		//test
		
		pathLabel.setText("Add Entry to Configuration File");
		updatePathLabelFromConfigFile();
		
		
		//test
	}
	
	public void updatePathLabelFromConfigFile() {
		String companyString = companyCombo.getSelectedItem().toString();
		String selectedPath = hm.get(companyString);
		renamer.setBaseFolderName(selectedPath);
		if (modeCombo.getSelectedItem().toString().equals("Auto")) {
			renamer.setAutoFolderName();
			updatePathLabel();
		}
	}
	
	public void disableComponents() {
		setButton.setEnabled(false);
		folderField.setEnabled(false);
	}
	
	public void update() {
		String oldName = renamer.getOldFileName();
		String newName = renamer.getNewFileName();
		StringBuilder oldAndNew = new StringBuilder();
		oldAndNew.append(newName + fileNameSeperator + oldName);
		area.append(oldAndNew.toString() + "\n");
	}
	
	private void buildCompanyCombo() {
		Path path = Paths.get(System.getProperty("user.home") + System.getProperty("file.separator") + "FileRenamer" + System.getProperty("file.separator") + "renamer_config.txt");
		if (Files.exists(path)) {
			try {
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.contains("#")) {
						String[] tokens = line.split("#");
						selectionsArrayList.add(tokens[0].trim());
						hm.put(tokens[0].trim(), tokens[1].trim());
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				Path file = Files.createFile(path);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void createProgramFolder() { //method must run before buildCompanyCombo method in constructor
		String userHomeString = System.getProperty("user.home") + System.getProperty("file.separator") + "FileRenamer";
		Path path = Paths.get(userHomeString);
		Path logPath = Paths.get(userHomeString + System.getProperty("file.separator") + "logs"); // test
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
				Files.createDirectory(logPath);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

	}
	
	public void updatePathLabel() {
		pathLabel.setText(renamer.getFullPathName());
	}
	
	class CompanyComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updatePathLabelFromConfigFile();
		}
	}
	
	class ModeComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
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
		}
	}
	
	class SetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			renamer.setManualFolderName(folderField.getText().trim());
			updatePathLabel();
		}
	}
	
	class RenameButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
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
				updatePathLabel();
				renamer.renameFiles();
				numberFilesRenamedLabel.setText(numberFilesString + Integer.toString(renamer.getNumberOfFilesRenamed()));
			}
		}
	}
}
