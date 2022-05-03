package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

	private String id;
	private String name;
	private String description;
	private List<Song> songs;
	
	public Playlist() {}
	
	public Playlist(String name) {
		this.name = name;
	}
	
	protected void setSongs(List<Song> s) {
		songs = s;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Song> getSongs() {
		return songs;
	}
	
	public Song getSong(String id) {
		if (songs==null)
			return null;
		
		Song song =null;
		for(Song s: songs)
			if (s.getId().equals(id))
			{
				song=s;
				break;
			}
		
		return song;
	}
	
	public void addSong(Song s) {
		if (songs==null)
			songs = new ArrayList<Song>();
		songs.add(s);
	}
	
	public void deleteSong(Song s) {
		songs.remove(s);
	}
	
	public void deleteSong(String id) {
		Song s = getSong(id);
		if (s!=null)
			songs.remove(s);
	}

}
