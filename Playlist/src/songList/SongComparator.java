package songList;
//Jasmine Feng and Risham Chokshi
//cs213 Assignment 1
import java.util.Comparator;

public class SongComparator implements Comparator<Song> {


	@Override
	public int compare(Song song1, Song song2) {
		// TODO Auto-generated method stub
		if(song1.getSongsName().compareToIgnoreCase(song2.getSongsName())!=0)
			return song1.getSongsName().compareToIgnoreCase(song2.getSongsName());
		else
			return song1.getSongsArtist().compareToIgnoreCase(song2.getSongsArtist());
	}
}
