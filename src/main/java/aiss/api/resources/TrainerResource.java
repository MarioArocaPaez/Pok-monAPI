package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.model.repository.TrainerRepository;
import aiss.model.Trainer;
import aiss.model.Pokemon;
import aiss.model.repository.MapTrainerRepository;






@Path("/trainers")
public class TrainerResource {
	
	/* Singleton */
	private static TrainerResource _instance=null;
	TrainerRepository repository;
	
	private TrainerResource() {
		repository=MapTrainerRepository.getInstance();

	}
	
	public static TrainerResource getInstance()
	{
		if(_instance==null)
				_instance=new TrainerResource();
		return _instance;
	}
	

	@GET
	@Produces("application/json")
	public Collection<Trainer> getAll(@QueryParam("name") String name, @QueryParam("isEmpty") Boolean isEmpty)
	{
		List<Trainer> retrurnedTrainer = new ArrayList<>();
		
		for(Trainer t: repository.getAllTrainers()) {
			if(name == null || t.getName().equals(name)) {
				if(isEmpty == null ||
						(isEmpty && t.getPokemons() == null || t.getPokemons().size() == 0) ||
						isEmpty && t.getPokemons() != null && t.getPokemons().size() == 0) {
					retrurnedTrainer.add(t);
				}
			}
		}
		return repository.getAllTrainers();
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Trainer get(@PathParam("id") String id)
	{
		Trainer t = repository.getTrainer(id);
		
		if (t == null) {
			throw new NotFoundException("The Trainer with id="+ id +" was not found");			
		}
		
		return t;
	}
	
	@POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addTrainer(@Context UriInfo uriInfo, Trainer trainer) {
        if (trainer.getName() == null || "".equals(trainer.getName()))
            throw new BadRequestException("The name of the Trainer must not be null");
        
        List<String> everyPokemonsId = repository.getAllPokemons().stream().map(x->x.getName()).collect(Collectors.toList());
        List<Pokemon> listpoke = new ArrayList<>();
        
        for (Pokemon pkmn : trainer.getPokemons()) {
            if (everyPokemonsId.contains(pkmn.getName())) {
                pkmn = repository.getPokemon(pkmn.getName());
                listpoke.add(pkmn);
            }
        }
        trainer.setPokemons(listpoke);
        repository.addTrainer(trainer);

        // Builds the response. Returns the Trainer the has just been added.
        UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
        URI uri = ub.build(trainer.getId());
        ResponseBuilder resp = Response.created(uri);
        resp.entity(trainer);            
        return resp.build();
    }

	
    @PUT
    @Consumes("application/json")
    public Response updateTrainer(Trainer trainer) {
        Trainer oldtrainer = repository.getTrainer(trainer.getId());
        if (oldtrainer == null) {
            throw new NotFoundException("The Trainer with id="+ trainer.getId() +" was not found");            
        }
        
        List<String> everyPokemonsId = repository.getAllPokemons().stream().map(x->x.getName()).collect(Collectors.toList());
        List<Pokemon> listpoke = new ArrayList<>();
        
        for (Pokemon pkmn : trainer.getPokemons()) {
            if (everyPokemonsId.contains(pkmn.getName())) {
                pkmn = repository.getPokemon(pkmn.getName());
                listpoke.add(pkmn);
            }
        }
        trainer.setPokemons(listpoke);
        
        // Update name
        if (trainer.getName()!=null)
            oldtrainer.setName(trainer.getName());
        
        // Update Age
        if (trainer.getAge()!=null)
            oldtrainer.setAge(trainer.getAge());
        
        // Update Gender
        if (trainer.getGender() != null) {
            oldtrainer.setGender(trainer.getGender());
        }
        
        // Update Pokemons
        if (trainer.getPokemons() != null) {
            oldtrainer.setPokemons(trainer.getPokemons());
        }
        
        return Response.noContent().build();
    }
	
	@DELETE
	@Path("/{id}")
	public Response removeTrainer(@PathParam("id") String id) {
		Trainer toberemoved = repository.getTrainer(id);
		if (toberemoved == null)
			throw new NotFoundException("The Trainer with id="+ id +" was not found");
		else
			repository.deleteTrainer(id);
		
		return Response.noContent().build();
	}
	
	
	@POST	
	@Path("/{trainerId}/{pokemonName}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addPokemon(@Context UriInfo uriInfo,@PathParam("trainerId") String trainerId, @PathParam("pokemonName") String pokemonName)
	{				
		
		Trainer tr = repository.getTrainer(trainerId);
		Pokemon pkmn = repository.getPokemon(pokemonName);
		
		if (tr==null)
			throw new NotFoundException("The Trainer with id=" + trainerId + " was not found");
		
		if (pkmn == null)
			throw new NotFoundException("The Pokemon with name=" + pokemonName + " was not found");
			
		repository.addPokemon(trainerId, pokemonName);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(trainerId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(tr);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{trainerId}/{pokemonName}")
	public Response removePokemon(@PathParam("trainerId") String trainerId, @PathParam("pokemonName") String pokemonName) {
		Trainer trainer = repository.getTrainer(trainerId);
		Pokemon pokemon = repository.getPokemon(pokemonName);
		
		if (trainer==null)
			throw new NotFoundException("The Trainer with id=" + trainerId + " was not found");
		
		if (pokemon == null)
			throw new NotFoundException("The Pokemon with name=" + pokemonName + " was not found");
		
		
		repository.removePokemon(trainerId, pokemonName);		
		
		return Response.noContent().build();
	}
}