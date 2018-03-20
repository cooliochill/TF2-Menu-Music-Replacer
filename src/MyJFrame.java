import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

@SuppressWarnings("serial")
public class MyJFrame extends JFrame implements ActionListener{
	int width, height, offset;
	String title;
	Dimension dim;
	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3");
	Button chooseFiles = new Button("Choose Files");
	Button rename = new Button("Rename");
	JLabel selected = new JLabel ("Select between 1 - 999 files");
	JLabel finish = new JLabel("");
	ArrayList<File> oldFiles = new ArrayList<File>();
	static JFrame frame = new MyJFrame("Select", 240, 180);
	static ArrayList<File> newFiles = new ArrayList<File>();
	
	public MyJFrame(String title, int width, int height) {
		super(title);
		this.width = width;
		this.height = height;
		this.title = title;
		this.offset = width / 14;
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		//Center frame on the screen
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		//Add and center components
		setComponentProperties();
		add(chooseFiles);
		add(selected);
		add(rename);
		add(finish);
		
		chooseFiles.addActionListener(this);
		rename.addActionListener(this);
	}
	
	public static void main(String[] args) {
		createFiles();
		frame.setVisible(true);
	}
	
	private static void createFiles() {
		for (int i = 0; i < 26; i++) {
			//File newFile = new File("C:/Steam Games/steamapps/common/Team Fortress 2/tf/custom/custom menu sounds/sound/ui/gamestartup" + (i + 1) + ".mp3");
			File path = new File("C:/Program Files (x86)/Steam/steamapps/common/Team Fortress 2/");
			File newFile = new File(System.getProperty("user.home") + "\\Desktop\\custom menu music\\sound\\ui\\gamestartup" + (i + 1) + ".mp3");
			if (path.exists()) {
				newFile = new File("C:/Program Files (x86)/Steam/steamapps/common/Team Fortress 2/tf/custom/custom menu music/sound/ui/"
						+ "gamestartup" + (i + 1) + ".mp3");
			}
			newFiles.add(newFile);
		}
	}
	public void setComponentProperties() {
		chooseFiles.setBounds((width - 100 - offset) / 2, 10, 100, 20);
		selected.setBounds((width - selected.getPreferredSize().width - offset) / 2, 35,
				selected.getPreferredSize().width, selected.getPreferredSize().height);
		rename.setBounds((width - 100 - offset) / 2, height * 10 / 21, 100, 20);
		finish.setBounds((width - finish.getPreferredSize().width - offset) / 2, height * 100 / 165, 
				finish.getPreferredSize().width, finish.getPreferredSize().height);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(chooseFiles)) {
			oldFiles.clear();
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(true);
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				Collections.addAll(oldFiles, chooser.getSelectedFiles()); 
				selected.setText(oldFiles.size() + " files have been chosen");
				setComponentProperties();
			}
			finish.setText("");
		}
		else if (arg0.getSource().equals(rename)) {
			finish.setText("Working...");
			setComponentProperties();
			try {
				FileUtils.cleanDirectory(new File("C:/Steam Games/steamapps/common/Team Fortress 2/tf/custom/custom menu sounds/sound/ui/"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
			if (oldFiles.size() > 0 && oldFiles.size() <= 26 && oldFiles.size() <= 999) {
				for (int i = 0; i < 26; i++) {
					for(File file : oldFiles) {
						if (i < 26) {
							System.out.println(i);
							try {
								FileUtils.copyFile(file, newFiles.get(i));
								if (oldFiles.size() != 1)
									i++;
							} catch (Exception e) {
								e.printStackTrace();
								finish.setText("Error!");
								setComponentProperties();
								return;
							}
						}
					}
				}
				finish.setText("Done!!!");
				setComponentProperties();
			}
			else if(oldFiles.size() > 0 && oldFiles.size() <= 999) {
				newFiles.clear();
				for (int i = 0; i < oldFiles.size(); i++) {
					for(File file : oldFiles) {
						try {
							File newFile = new File("C:/Steam Games/steamapps/common/Team Fortress 2/tf/custom/custom menu sounds/sound/ui/gamestartup" + (i + 1) + ".mp3");
							newFiles.add(newFile);
							FileUtils.copyFile(file, newFiles.get(i));
							if (oldFiles.size() != 1)
								i++;
						} catch (Exception e) {
							e.printStackTrace();
							finish.setText("Error!");
							setComponentProperties();
							return;
						}
					}
				}
				finish.setText("Done!!!");
				setComponentProperties();
			}
			else {
				finish.setText("Error! Please select between 1 and 999 files.");
				setComponentProperties();
			}
		}
	}
}
