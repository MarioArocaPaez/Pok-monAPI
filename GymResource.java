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
	public Collection<Gym> getAll( @QueryParam("order") String order,
		 @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit)
	{
	
		List<Gym> result = new ArrayList<Gym>();
		
		// Filter
		for (Gym btl: repository.getAllGyms()) {
					result.add(btl);
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
        
        // Update type
        if (gym.getType()!=null)
            oldgym.setType(gym.getType());
        
        // Update helpers
        if (gym.getHelpers()!=null)
            oldgym.setHelpers(gym.getHelpers());
        
        // Update leader
        if (gym.getLeader() != null) {
            oldgym.setLeader(gym.getLeader());
        }
        
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
			@PathParam("trainerId") String helperId)
	{				
		
		Gym g = repository.getGym(gymId);
		Trainer tr = repository.getTrainer(helperId);
		
		if (tr==null)
			throw new NotFoundException("The Trainer with id=" + helperId + " was not found");
		
		if (g == null)
			throw new NotFoundException("The Gym with id=" + gymId + " was not found");
			
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