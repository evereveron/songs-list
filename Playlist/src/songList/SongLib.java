package songList;

//Jasmine Feng and Risham Chokshi
//cs213 Assignment 1

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class SongLib extends JFrame implements ActionListener {
	
	//puts in all the information based on the index
	ArrayList <Song> Songs = new ArrayList<Song>();
	
	//to get the list from the Array into the Radio Button
	JList List;
	DefaultListModel songlist = new DefaultListModel();
	JScrollPane listScroller;
	
	//Display for Viewing Song
	JLabel Display = new JLabel("");
	JLabel Error = new JLabel("");
	//creating all the buttons
	JButton Edit = new JButton("Edit");
	JButton Add = new JButton("Add");
	JButton Delete = new JButton("Delete");
	
	//After clicking edit, these buttons should be there
	JButton Cancel = new JButton("Cancel");
	JButton Submit = new JButton("Submit");
	
	//After clicking Add, these buttons should be there
	JButton Add_Cancel = new JButton("Cancel");
	//JButton Add_Submit = new JButton("Submit");
	
	//Creating Panel
	JPanel Song_List;
	JPanel Edit_List;
	JPanel Inside_Edit;
	JPanel AddPanel;
	JPanel Add_Inside;
	JPanel DeletePanel;
	String filename = "songs.txt";
	
	//TextBox
	JTextArea song_name = new JTextArea("Song",1,10);
	JTextArea artist_name = new JTextArea("Artist Name",1,10);
	JTextArea year = new JTextArea("Year",1,10);
	JTextArea album = new JTextArea("Album",1,10);
	//for add_inside panel
	JTextArea add_song_name = new JTextArea("Song",1, 10);
	JTextArea add_artist_name = new JTextArea("Artist Name",1,10);
	JTextArea add_year = new JTextArea("Year",1,10);
	JTextArea add_album = new JTextArea("Album",1,10);
	
	public SongLib(String title) throws IOException{
		super(title);
		
		// use FlowLayout
		setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		
		//do the list
		Song_List = new JPanel(new GridLayout(0, 1));
		//Song_List.setBackground(Color.red);
		Song_List.setPreferredSize(new Dimension(250,200));
	
		readFile(filename);
		
		
	
		//adding each component to the panel
		//adding to the list
		
		for(int i =0; i<Songs.size(); i++){
			String temp = Songs.get(i).getSongsName();
			if(temp!=null && Songs.get(i).getSongsArtist()!=null){
				temp = temp + ", " + Songs.get(i).getSongsArtist();
				songlist.addElement(temp);
			}
			}
	
		List = new JList(songlist);
		List.setVisibleRowCount(-1);
		//change the Display
		
		
		/* **** 
		 * why is the display auto set to 0?
		 */
		if(Songs.size()>=1){
			List.setSelectedIndex(0);
			this.Display.setText(Songs.get(0).getSongsName()+", "+Songs.get(0).getSongsArtist()+", "+ Songs.get(0).getSongsYear()+", "+ Songs.get(0).getSongsAlbum());
		}
		List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listScroller = new JScrollPane(List);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		JLabel label = new JLabel("Song_Name,Song_Artist");
		label.setLabelFor(List);
		Song_List.add(label);
		Song_List.add(listScroller);
		Song_List.add(Display);
		//add the display for the first label
		
		//do the edit panel
		Edit_List = new JPanel(new FlowLayout(FlowLayout.RIGHT,1,2));
		Edit_List.setPreferredSize(new Dimension(150,250));
		
		
		JPanel Inside_Edit = new JPanel(new GridLayout(0,1));
		Inside_Edit.setPreferredSize(new Dimension(100,180));
		Inside_Edit.add(new JLabel("Song:"));
		Inside_Edit.add(song_name);
		Inside_Edit.add(new JLabel("Artist:"));
		Inside_Edit.add(artist_name);
		Inside_Edit.add(new JLabel("Year:"));
		Inside_Edit.add(year);
		Inside_Edit.add(new JLabel("Album:"));
		Inside_Edit.add(album);
		Inside_Edit.add(Cancel);
		Inside_Edit.add(Submit);
		
		
		Edit_List.add(Edit);
		
		//added this!! 
		
		//Edit_List.add(Inside_Edit);
		
		//do the add_delete panel
		Add_Inside = new JPanel(new GridLayout(0,1));
		Add_Inside.setPreferredSize(new Dimension(100,180));
		Add_Inside.add(new JLabel("Song:"));
		Add_Inside.add(add_song_name);
		Add_Inside.add(new JLabel("Artist:"));
		Add_Inside.add(add_artist_name);
		Add_Inside.add(new JLabel("Year:"));
		Add_Inside.add(add_year);
		Add_Inside.add(new JLabel("Album:"));
		Add_Inside.add(add_album);
		Add_Inside.add(Add);
		Add_Inside.add(Add_Cancel);		
		AddPanel = new JPanel(new FlowLayout());
		
		
		
		//added this!
		
		AddPanel.add(Delete);
		AddPanel.add(Add_Inside);
		//add all the panels to frame
		
		//Error.setText("Error: cannot be added");
		Error.setForeground(Color.red);
		add(Song_List);
		add(Edit_List);
		add(AddPanel);
		add(Error);
		
		Add.addActionListener(this);
		Delete.addActionListener(this);
		Edit.addActionListener(this);
		
		//for testing purposes:
		
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	
            	try {
					updateFile();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
                System.exit(0);
            }
        });
		
		
		
	}
	
	
	
	public void SortList(){
		Collections.sort(Songs, new SongComparator());
		
	}
	
	public boolean FindList(Song comp){
		return Songs.contains(comp);
		
	}	
	
	public void readFile(String fileName) throws IOException{
		
		File file = new File (filename);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		
		while((line = br.readLine()) != null) {
			
			if(line.length()>1){
			Song song = tokenize(line);
			Songs.add(song);
		}
		}
		
		SortList();
		
	}
	
	private Song tokenize(String line) {
		
	
		String[] tokens = line.split("\\|");
		
		Song song = new Song(tokens[0], tokens[1], tokens[2], tokens[3]);
		
		return song;
	}
	
	private void print(){
		for(int i=0; i<Songs.size(); i++) {
			System.out.println(Songs.get(i).SongsName + " " +Songs.get(i).SongsArtist + " " + Songs.get(i).SongsYear + " " +Songs.get(i).SongsAlbum  );
		}
	}
	
	private void updateFile() throws IOException {
		File file = new File(filename);
		String line = "";
		
		//if file doesnt exist, create it
		if(!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(int i=0; i<Songs.size(); i++) {
			line += Songs.get(i).SongsName + "|" + Songs.get(i).SongsArtist + "|" + Songs.get(i).SongsYear + "|" + Songs.get(i).SongsAlbum +"\n";
			//System.out.println(line);
			bw.write(line);
			line = "";
		}
		
		bw.close();
		
		
	}
	
	public void EditList(String name, String Artist, String Year, String album, int choice){
		
		if(choice==0){
			if(name!=null&&Artist!=null&&Year!=null&&album!=null){
				Song temp = new Song(name,Artist,Year,album);
				if(FindList(temp)!=true){
					Songs.add(temp);
					
					String temp2 = Songs.get(Songs.size()-1).getSongsName() + ", " + Songs.get(Songs.size()-1).getSongsArtist();
					songlist.addElement(temp2);
					
					SortList();
					}
				else{ //the name already exists
					Display.setText("Error: This song already Exist");
				}
			}
		
		}
		else if(choice==1){
			boolean t = false;
			if(name!=null&&Artist!=null){
				for(int i =0; i<Songs.size();i++){
					if(name.equalsIgnoreCase(Songs.get(i).getSongsName())&&Artist.equalsIgnoreCase(Songs.get(i).getSongsArtist())){
						 Songs.remove(i); t=true;
						 break;
					}
				}
				if(t!=false){//it was not found
					Display.setText("Error:Cannot be deleted");
				}
			}
			
		}
					
	}
	
	//method is for 
	//this substring may have to be fixed -_- (if songs have ", " in them.)
	private String[] separateSelection(String selection) {
		String[] ans;
		
		ans = selection.split(", ");
		
		//System.out.println("" + ans[0] + ans[1]);
		
		return ans;
	}
	
	@Override
	public void actionPerformed(ActionEvent event){
		
		if(event.getSource() == Add) {
			System.out.println("Add button clicked");
			System.out.println("You Selected : " + List.getSelectedValue());
			
			String songName = add_song_name.getText(); 
			String artistName = add_artist_name.getText();
			String yearValue = add_year.getText();
			String albumName = add_album.getText();
			
			EditList(songName, artistName, yearValue, albumName, 0);
			
		}
		else if(event.getSource() == Delete) {
			System.out.println("Delete button clicked");
			String[] selection = separateSelection(List.getSelectedValue().toString());
			EditList(selection[0], selection[1], null, null, 1);
			songlist.remove(List.getSelectedIndex());
		}
		else if(event.getSource() == Edit) {
			System.out.println("Edit button clicked");
		}
		else {
			System.out.println("error in action performed");
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JFrame Play_List = new SongLib("Play Your Music!");
		Play_List.setSize(450,500);
		Play_List.setResizable(false);
		Play_List.setLocationRelativeTo(null);
		Play_List.setVisible(true);
	}
	
	

}
