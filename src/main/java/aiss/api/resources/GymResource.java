package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public Response addGym(@Context UriInfo uriInfo, Gym Gym) {
        if (Gym.getId() == null || "".equals(Gym.getId()))
            throw new BadRequestException("The id of the Gym must not be null");
       
        repository.addGym(Gym);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
        URI uri = ub.build(Gym.getId());
        ResponseBuilder resp = Response.created(uri);
        resp.entity(Gym);            
        return resp.build();
    }

}