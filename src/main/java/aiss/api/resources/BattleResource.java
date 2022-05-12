package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import aiss.model.Battle;
import aiss.model.repository.MapTrainerRepository;




@Path("/battles")
public class BattleResource {
	
	/*Singleton*/
	private static BattleResource _instance=null;
	TrainerRepository repository;
	
	private BattleResource() {
		repository=MapTrainerRepository.getInstance();

	}
	public static BattleResource getInstance()
	{
		if(_instance==null)
			_instance=new BattleResource();
		return _instance; 
	}
	@GET
	@Produces("application/json")
	public Collection<Battle> getAll( @QueryParam("order") String order,
		 @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit)
	{
	
		List<Battle> result = new ArrayList<Battle>();
		
		// Filter
		for (Battle btl: repository.getAllBattles()) {
			
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
	public Battle get(@PathParam("id") String id)
	{
		Battle b = repository.getBattle(id);
		
		if (b == null) {
			throw new NotFoundException("The Battle with id="+ id +" was not found");			
		}
		
		return b;
	}
	@POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addBattle(@Context UriInfo uriInfo, Battle battle) {
        if (battle.getId() == null || "".equals(battle.getId()))
            throw new BadRequestException("The id of the battle must not be null");
       
        repository.addBattle(battle);

        // Builds the response. Returns the Trainer the has just been added.
        UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
        URI uri = ub.build(battle.getId());
        ResponseBuilder resp = Response.created(uri);
        resp.entity(battle);            
        return resp.build();
    }
}
