package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.model.Gym;
import aiss.model.Pokemon;
import aiss.model.Trainer;
import aiss.model.repository.MapTrainerRepository;
import aiss.model.repository.TrainerRepository;

@Path("/gyms")
public class GymResource {
	
	/*Singleton*/
	private static GymResource _instance=null;
	TrainerRepository repository;
	
	private GymResource() {
		repository=MapTrainerRepository.getInstance();

	}
	public static GymResource getInstance()
	{
		if(_instance==null)
			_instance=new GymResource();
		return _instance; 
	}
	
    @GET
    @Produces("application/json")
    public Collection<Gym> getAll(@QueryParam("type") String type, @QueryParam("order") String order,
         @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit)
    {
    
        List<Gym> result = new ArrayList<Gym>();
        
        // Filter
        for (Gym gym: repository.getAllGyms()) {
            if(type == null || gym.getType().equals(type)) {
                result.add(gym);
            }         
        }
		
		Collections.sort(result, (p1,p2) -> p1.getId().compareTo(p2.getId()));
		
		// Order
		if (order != null) {
			if (order.equals("-id")) {
				Collections.sort(result, (p1,p2) -> p2.getId().compareTo(p1.getId()));
			} else if (order.equals("type")) {
				Collections.sort(result, (p1,p2) -> p1.getType().compareTo(p2.getType()));
			} else if (order.equals("-type")) {
				Collections.sort(result, (p1,p2) -> p2.getType().compareTo(p1.getType()));
			} else if (order.equals("leader")) {
				Collections.sort(result, (p1,p2) -> p1.getLeader().getName().compareTo(p2.getLeader().getName()));
			} else if (order.equals("-leader")) {
				Collections.sort(result, (p1,p2) -> p2.getLeader().getName().compareTo(p1.getLeader().getName()));
			} else {
				throw new BadRequestException("The order parameter must be '-id', 'type', '-type', 'leader', '-leader'.");
			}
		}	
			
		// Offset
		if (offset != null) {
			if (offset <= result.size()) {
				result.subList(0, offset).clear();
			} else {
				Integer value = result.size()+1;
				throw new BadRequestException("Offset value has to be less than "+value+".");
			}
		}
		
		//Limit
		if (limit != null) {
			if (limit <= result.size()) {
				result = result.subList(0, limit);
			} else {
				Integer value = result.size()+1;
				throw new BadRequestException("Limit value has to be less than "+value+".");
			}
		}
		
		return result;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Gym get(@PathParam("id") String id)
	{
		Gym b = repository.getGym(id);
		
		if (b == null) {
			throw new NotFoundException("The Gym with id="+ id +" was not found");			
		}
		
		return b;
	}
	@POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addGym(@Context UriInfo uriInfo, Gym gym) {
        if (gym.getLeader() == null)
            throw new BadRequestException("The leader of the Gym must not be null");
       
        //check only the id of the trainers and add the object
        List<String> everyHelperId = repository.getAllTrainers().stream().map(x->x.getId())
        		.collect(Collectors.toList());
        List<Trainer> listhelper = new ArrayList<>();
        
        for (Trainer h : gym.getHelpers()) {
            if (everyHelperId.contains(h.getId())) {
                h = repository.getTrainer(h.getId());
                listhelper.add(h);
            }
        }
        if(everyHelperId.contains(gym.getLeader().getId())) {
        	gym.setLeader(repository.getTrainer(gym.getLeader().getId()));
        } else {
            throw new BadRequestException("The id of the leader of the Gym doesn't exist");
        }
        gym.setHelpers(listhelper);
        
        //check that one of the helpers isn't already the leader
        if (listhelper.contains(gym.getLeader())) {
            throw new BadRequestException(gym.getLeader().getName() + " cannot be leader and helper at the same time");
        }
        
        //for each helper, at least one of their pokémon must be of the gym's type
        for(Trainer h: listhelper) {
            Boolean cond = false;
        	for(Pokemon p: h.getPokemons()) {
        		if(gym.getType().equals(p.getType1()) || gym.getType().equals(p.getType2())) {
        			cond = true;
        		}
        	}
        	if(!cond) {
        		throw new BadRequestException(h.getName() + " (one of the gym's helpers) doesn't have any Pokémon of the gym's type");
        	}
        }
        
        //the leader's pokemon must be all of the gym's type
    	for(Pokemon p: gym.getLeader().getPokemons()) {
    		if(!(gym.getType().equals(p.getType1()) || gym.getType().equals(p.getType2()))) {
    			throw new BadRequestException("The leader must have all of their Pokémon of the gym's type");
    		}	
    	}

        repository.addGym(gym);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
        URI uri = ub.build(gym.getId());
        ResponseBuilder resp = Response.created(uri);
        resp.entity(gym);            
        return resp.build();
    }

	
    @PUT
    @Consumes("application/json")
    public Response updateGym(Gym gym) {
    	Gym oldgym = repository.getGym(gym.getId());
        if (oldgym == null) {
            throw new NotFoundException("The Gym with id="+ gym.getId() +" was not found");            
        }
        
        //check only the id of the trainers and add the object
        List<String> everyHelperId = repository.getAllTrainers().stream().map(x->x.getId())
        		.collect(Collectors.toList());
        List<Trainer> listhelper = new ArrayList<>();
        
        if (gym.getHelpers()!=null) {
	        for (Trainer h : gym.getHelpers()) {
	            if (everyHelperId.contains(h.getId())) {
	                h = repository.getTrainer(h.getId());
	                listhelper.add(h);
	            }
	        }
	        gym.setHelpers(listhelper);
        }
        
        
        if (gym.getLeader() != null) {
	        if(everyHelperId.contains(gym.getLeader().getId())) {
	        	gym.setLeader(repository.getTrainer(gym.getLeader().getId()));
	        } else {
	            throw new BadRequestException("The id of the leader of the Gym doesn't exist");
	        }
        }
        
        // Update type
        if (gym.getType()!=null)
            oldgym.setType(gym.getType());
        
        //check that one of the helpers isn't already the leader
        if (gym.getHelpers()!=null) {
	        if (listhelper.contains(gym.getLeader())) {
	            throw new BadRequestException(gym.getLeader().getName() + " cannot be leader and helper at the same time");
	        }
        }
        
        //for each helper, at least one of their pokémon must be of the gym's type
        if (gym.getHelpers()!=null) {
	        for(Trainer h: listhelper) {
	            Boolean cond = false;
	        	for(Pokemon p: h.getPokemons()) {
	        		if(oldgym.getType().equals(p.getType1()) || oldgym.getType().equals(p.getType2())) {
	        			cond = true;
	        		}
	        	}
	        	if(!cond) {
	        		throw new BadRequestException(h.getName() + " (one of the gym's helpers) doesn't have any Pokémon of the gym's type");
	        	}
	        }
        }
        
        //the leader's pokemon must be all of the gym's type
        if (gym.getLeader() != null) {
	    	for(Pokemon p: gym.getLeader().getPokemons()) {
	    		if(!(oldgym.getType().equals(p.getType1()) || oldgym.getType().equals(p.getType2()))) {
	    			throw new BadRequestException("The leader must have all of their Pokémon of the gym's type");
	    		}	
	    	}
        }
        
    	oldgym.setLeader(gym.getLeader());
        oldgym.setHelpers(gym.getHelpers());
        
        return Response.noContent().build();
    }
    
	@DELETE
	@Path("/{id}")
	public Response removeGym(@PathParam("id") String id) {
		Gym toberemoved = repository.getGym(id);
		if (toberemoved == null)
			throw new NotFoundException("The Gym with id="+ id +" was not found");
		else
			repository.deleteGym(id);
		
		return Response.noContent().build();
	}
	
	@POST	
	@Path("/{gymId}/{trainerId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addHelper(@Context UriInfo uriInfo,@PathParam("gymId") String gymId, 
			@PathParam("trainerId") String helperId) {				
		
		Gym g = repository.getGym(gymId);
		Trainer tr = repository.getTrainer(helperId);
		
		if (tr==null)
			throw new NotFoundException("The Trainer with id=" + helperId + " was not found");
		
		if (g == null)
			throw new NotFoundException("The Gym with id=" + gymId + " was not found");
		
        //check that one of the helpers isn't already the leader
        if (g.getLeader().equals(tr)) {
            throw new BadRequestException(tr.getName() + " cannot be leader and helper at the same time");
        }
        
        //for each helper, at least one of their pokémon must be of the gym's type
            Boolean cond = false;
        	for(Pokemon p: tr.getPokemons()) {
        		if(g.getType().equals(p.getType1()) || g.getType().equals(p.getType2())) {
        			cond = true;
        		}
        	}
        	if(!cond) {
        		throw new BadRequestException(tr.getName() + " (one of the gym's helpers) doesn't have any Pokémon of the gym's type");
        	}
			
		repository.addHelper(gymId, helperId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(gymId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(g);			
		return resp.build();
	}
	
	@DELETE
	@Path("/{gymId}/{trainerId}")
	public Response removeHelper(@PathParam("gymId") String gymId, @PathParam("trainerId") String helperId) {
		Gym g = repository.getGym(gymId);
		Trainer tr = repository.getTrainer(helperId);
		
		if (tr==null)
			throw new NotFoundException("The Trainer with id=" + helperId + " was not found");
		
		if (g == null)
			throw new NotFoundException("The Gym with id=" + gymId + " was not found");
        
		repository.removeHelper(gymId, helperId);		
		
		return Response.noContent().build();
	}
    
}
