package aiss.model.repository;

import java.util.Collection;

import aiss.model.Playlist;
import aiss.model.Song;

public interface PlaylistRepository {
	
	
	// Songs
	public void addSong(Song s);
	public Collection<Song> getAllSongs();
	public Song getSong(String songId);
	public void updateSong(Song s);
	public void deleteSong(String songId);
	
	// Playlists
	public void addPlaylist(Playlist p);
	public Collection<Playlist> getAllPlaylists();
	public Playlist getPlaylist(String playlistId);
	public void updatePlaylist(Playlist p);
	public void deletePlaylist(String playlistId);
	public Collection<Song> getAll(String playlistId);
	public void addSong(String playlistId, String songId);
	public void removeSong(String playlistId, String songId); 

	
	
	
	

}
