package aiss.api.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import aiss.model.Playlist;
import aiss.model.Pokemon;
import aiss.model.Song;
import aiss.model.repository.MapPlaylistRepository;
import aiss.model.repository.PlaylistRepository;

import java.net.URI;
import java.util.Collection;



@Path("/pokemons")
public class PokemonResource {

	public static PokemonResource _instance=null;
	PlaylistRepository repository;
	
	private PokemonResource(){
		repository=MapPlaylistRepository.getInstance();
	}
	
	public static PokemonResource getInstance()
	{
		if(_instance==null)
			_instance=new PokemonResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Pokemon> getAll()
	{
		return repository.getAllPokemons();
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Pokemon get(@PathParam("id") String pokemonId)
	{
		Pokemon poke = repository.getPokemon(pokemonId);
		
		if (poke == null) {
			throw new NotFoundException("The Pokémon with id="+ pokemonId +" was not found");			
		}
		
		return poke;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addPokemon(@Context UriInfo uriInfo, Pokemon poke) {
		if (poke.getName() == null || "".equals(poke.getName()))
			throw new BadRequestException("The name of the song must not be null");

		repository.addPokemon(poke);

		// Builds the response. Returns the song the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(poke.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(poke);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateSong(Song song) {
		Song oldsong = repository.getSong(song.getId());
		if (oldsong == null) {
			throw new NotFoundException("The song with id="+ song.getId() +" was not found");			
		}

		// Update name
		if (song.getTitle()!=null)
			oldsong.setTitle(song.getTitle());
		
		// Update description
		if (song.getArtist()!=null)
			oldsong.setArtist(song.getArtist());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSong(@PathParam("id") String songId) {
		Song toberemoved=repository.getSong(songId);
		if (toberemoved == null)
			throw new NotFoundException("The song with id="+ songId +" was not found");
		else
			repository.deleteSong(songId);
		
		return Response.noContent().build();
	}
	
}
