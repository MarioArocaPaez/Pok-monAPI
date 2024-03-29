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
	public Collection<Battle> getAll(@QueryParam("winner") String winner, @QueryParam("order") String order,
		 @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit)
	{
		
		List<Battle> result = new ArrayList<Battle>();
		
		// Filter
		for (Battle btl: repository.getAllBattles()) {
			if (winner == null
					|| (winner.equals("tr1") && btl.getWinner() == btl.getTr1())
					|| (winner.equals("tr2") && btl.getWinner() == btl.getTr2())
					|| (winner.equals("noWinner") && btl.getWinner() == null)) {
						result.add(btl);
			}
			if (winner != null && !winner.equals("tr1") && !winner.equals("tr2") && !winner.equals("noWinner")){
				throw new BadRequestException("The winner parameter must be'tr1', 'tr2', 'noWinner'.");
			}
		}
		
		Collections.sort(result, (p1,p2) -> p1.getId().compareTo(p2.getId()));
		
		// Order
		if (order != null) {
			if (order.equals("-id")) {
				Collections.sort(result, (p1,p2) -> p2.getId().compareTo(p1.getId()));
			} else if (order.equals("name")) {
				List<Battle> nullList = result.stream().filter(x->x.getName()==null).collect(Collectors.toList());
				List<Battle> noNullList = result.stream().filter(x->x.getName()!=null).collect(Collectors.toList());
				Collections.sort(noNullList, (p1,p2) -> p1.getName().compareTo(p2.getName()));
				for (Battle btl: nullList) {
					noNullList.add(btl);
				}
				result=noNullList;
			} else if (order.equals("-name")) {
				List<Battle> nullList = result.stream().filter(x->x.getName()==null).collect(Collectors.toList());
				List<Battle> noNullList = result.stream().filter(x->x.getName()!=null).collect(Collectors.toList());
				Collections.sort(noNullList, (p1,p2) -> p2.getName().compareTo(p1.getName()));
				for (Battle btl: nullList) {
					noNullList.add(btl);
				}
				result=noNullList;
			} else if (order.equals("tr1")) {
				Collections.sort(result, (p1,p2) -> p1.getTr1().getName().compareTo(p2.getTr1().getName()));
			} else if (order.equals("-tr1")) {
				Collections.sort(result, (p1,p2) -> p2.getTr1().getName().compareTo(p1.getTr1().getName()));
			} else if (order.equals("tr2")) {
				Collections.sort(result, (p1,p2) -> p1.getTr2().getName().compareTo(p2.getTr2().getName()));
			} else if (order.equals("-tr2")) {
				Collections.sort(result, (p1,p2) -> p2.getTr2().getName().compareTo(p1.getTr2().getName()));
			} else if (order.equals("winner")) {
				List<Battle> nullList = result.stream().filter(x->x.getWinner()==null).collect(Collectors.toList());
				List<Battle> noNullList = result.stream().filter(x->x.getWinner()!=null).collect(Collectors.toList());
				Collections.sort(noNullList, (p1,p2) -> p1.getWinner().getName().compareTo(p2.getWinner().getName()));
				for (Battle btl: nullList) {
					noNullList.add(btl);
				}
				result=noNullList;
			} else if (order.equals("-winner")) {
				List<Battle> nullList = result.stream().filter(x->x.getWinner()==null).collect(Collectors.toList());
				List<Battle> noNullList = result.stream().filter(x->x.getWinner()!=null).collect(Collectors.toList());
				Collections.sort(noNullList, (p1,p2) -> p2.getWinner().getName().compareTo(p1.getWinner().getName()));
				for (Battle btl: nullList) {
					noNullList.add(btl);
				}
				result=noNullList;
			} else {
				throw new BadRequestException("The order parameter must be '-id', 'name', '-name', 'tr1', '-tr1', 'tr2', '-tr2', 'winner', '-winner'.");
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
		
		if(battle.getTr1()== battle.getTr2()) {
			throw new BadRequestException("A battle cannot be of a trainer facing against themselves");
		}
		
        List<String> everyTrainerId = repository.getAllTrainers().stream().map(x->x.getId())
        		.collect(Collectors.toList());
        if(everyTrainerId.contains(battle.getTr1().getId())) {
        	battle.setTr1(repository.getTrainer(battle.getTr1().getId()));
        } else {
			throw new BadRequestException("The first trainer doesn't exist");
        }
        if(everyTrainerId.contains(battle.getTr2().getId())) {
        	battle.setTr2(repository.getTrainer(battle.getTr2().getId()));
        } else {
			throw new BadRequestException("The second trainer doesn't exist");
        }
        
        if (battle.getName() == null || "".equals(battle.getName())) {
        	battle.setName(battle.getTr1().getName() + " VS " + battle.getTr2().getName());
        }
        
        if (battle.getWinner() == null) {
    		List<Integer> statstr1 = new ArrayList<>();
    		List<Integer> statstr2 = new ArrayList<>();
        	for(Pokemon p:battle.getTr1().getPokemons()) {
        		statstr1.add(p.getAttack());
        		statstr1.add(p.getDefense());
        		statstr1.add(p.getHp());
        	}
        	for(Pokemon p:battle.getTr2().getPokemons()) {
        		statstr2.add(p.getAttack());
        		statstr2.add(p.getDefense());
        		statstr2.add(p.getHp());
        	}
        	Integer sumtr1 = statstr1.stream().mapToInt(Integer::intValue).sum() 
        			/ battle.getTr1().getPokemons().size();
        	Integer sumtr2 = statstr2.stream().mapToInt(Integer::intValue).sum() 
        			/ battle.getTr2().getPokemons().size();
        	if(sumtr1>sumtr2) {
        		battle.setWinner(battle.getTr1());
        	} else if(sumtr2>sumtr1) {
        		battle.setWinner(battle.getTr2());
        	} else {
        		battle.setWinner(null);
        	}

        }
        repository.addBattle(battle);

        // Builds the response. Returns the Trainer the has just been added.
        UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
        URI uri = ub.build(battle.getId());
        ResponseBuilder resp = Response.created(uri);
        resp.entity(battle);            
        return resp.build();
    }
	 @PUT
	    @Consumes("application/json")
	    public Response updateBattle(Battle battle) {
	        Battle oldbattle = repository.getBattle(battle.getId());
	        if (oldbattle == null) {
	            throw new NotFoundException("The Battle with id="+ battle.getId() +" was not found");            
	        }

	        if (battle.getName() != null) {
	        	oldbattle.setName(battle.getName());
	        }
			if (battle.getWinner() != null) {
		     	oldbattle.setWinner(battle.getWinner());
		    }
	        return Response.noContent().build();
	    }

	@DELETE
	@Path("/{battleId}")
	public Response removeBattle(@PathParam("battleId") String battleId) {
		Battle battle = repository.getBattle(battleId);

		if (battle==null)
			throw new NotFoundException("The Battle with id=" + battleId + " was not found");

		repository.removeBattle(battleId);		

		return Response.noContent().build();
	}
} 

