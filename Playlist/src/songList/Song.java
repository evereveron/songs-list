package songList;
//Jasmine Feng and Risham Chokshi
//cs213 Assignment 1

public class Song {

	
	 String SongsName ;
	String SongsArtist ;
	String SongsYear ;
	String SongsAlbum ;
	
	public Song(String SongsName,String SongsArtist,String SongsYear,String SongsAlbum){
		this.SongsName= SongsName; 
		this.SongsArtist= SongsArtist;
		this.SongsYear = SongsYear ;
		this.SongsAlbum= SongsAlbum;
		
	}
	

	public String getSongsName() {
		return SongsName;
	}

	public void setSongsName(String songsName) {
		SongsName = songsName;
	}

	public String getSongsArtist() {
		return SongsArtist;
	}

	public void setSongsArtist(String songsArtist) {
		SongsArtist = songsArtist;
	}

	public String getSongsYear() {
		return SongsYear;
	}

	public void setSongsYear(String songsYear) {
		SongsYear = songsYear;
	}

	public String getSongsAlbum() {
		return SongsAlbum;
	}

	public void setSongsAlbum(String songsAlbum) {
		SongsAlbum = songsAlbum;
	}
	@Override
	public boolean equals(Object song2) {
		if(song2!=null && !(song2 instanceof Song)){
			Song Song2 = (Song) song2;
        if(this.SongsName.equalsIgnoreCase(Song2.getSongsName()))
        	return this.SongsArtist.equalsIgnoreCase(Song2.getSongsArtist());
		}
     return false;
  }

	
}