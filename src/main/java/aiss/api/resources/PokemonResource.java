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

import aiss.model.Pokemon;
import aiss.model.Trainer;
import aiss.model.repository.MapTrainerRepository;
import aiss.model.repository.TrainerRepository;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;



@Path("/pokemons")
public class PokemonResource {

	public static PokemonResource _instance=null;
	TrainerRepository repository;
	
	private PokemonResource(){
		repository=MapTrainerRepository.getInstance();
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
		Collection<Pokemon> res = repository.getAllPokemons().stream()
				.sorted(Comparator.comparing(Pokemon::getName)).collect(Collectors.toList());
		return res;
	}
	
	
	@GET
	@Path("/{name}")
	@Produces("application/json")
	public Pokemon get(@PathParam("name") String pokemonName)
	{
		Pokemon poke = repository.getPokemon(pokemonName);
		
		if (poke == null) {
			throw new NotFoundException("The Pokémon " + pokemonName +" was not found");			
		}
		
		return poke;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addPokemon(@Context UriInfo uriInfo, Pokemon poke) {
		if (poke.getName() == null || "".equals(poke.getName()))
			throw new BadRequestException("The name of the Pokémon must not be null");
		if (poke.getType1() == null || "".equals(poke.getType1()))
			throw new BadRequestException("The first type of the Pokémon must not be null");
		if (poke.getHp() == null)
			throw new BadRequestException("The Health Points of the Pokémon must not be null");
		if (poke.getAttack() == null)
			throw new BadRequestException("The Attack of the Pokémon must not be null");
		if (poke.getDefense() == null)
			throw new BadRequestException("The Defense of the Pokémon must not be null");

		repository.addPokemon(poke);

		// Builds the response. Returns the song the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(poke.getName());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(poke);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updatePokemon(Pokemon poke) {
		Pokemon oldpoke = repository.getPokemon(poke.getName());
		if (oldpoke == null) {
			throw new NotFoundException("The Pokémon " + poke.getName() +" was not found");			
		}

		// Update name
		if (poke.getName()!=null)
			oldpoke.setName(poke.getName());
		
		// Update description
		if (poke.getType1()!=null)
			oldpoke.setType1(poke.getType1());
		if (poke.getType2()!=null)
			oldpoke.setType2(poke.getType2());
		if (poke.getGeneration()!=null)
			oldpoke.setGeneration(poke.getGeneration());
		if (poke.getLegend()!=null)
			oldpoke.setLegend(poke.getLegend());
		if (poke.getHp()!=null)
			oldpoke.setHp(poke.getHp());
		if (poke.getAttack()!=null)
			oldpoke.setAttack(poke.getAttack());
		if (poke.getDefense()!=null)
			oldpoke.setDefense(poke.getDefense());

		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{name}")
	public Response removePokemon(@PathParam("name") String pokemonName) {
		Pokemon toberemoved=repository.getPokemon(pokemonName);
		if (toberemoved == null)
			throw new NotFoundException("The Pokémon " + pokemonName +" was not found");
		else
			repository.deletePokemon(pokemonName);
		
			for(Trainer a: repository.getAllTrainers()) {
				repository.removePokemon(a.getId(), pokemonName);
			}
		return Response.noContent().build();
	}
	
}