package aiss.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import aiss.api.resources.TrainerResource;
import aiss.api.resources.PokemonResource;
import aiss.api.resources.BattleResource;
import aiss.api.resources.GymResource;


public class GameFreakApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	// Loads all resources that are implemented in the application
	// so that they can be found by RESTEasy.
	public GameFreakApplication() {

		singletons.add(TrainerResource.getInstance());
		singletons.add(PokemonResource.getInstance());
		singletons.add(BattleResource.getInstance());
        singletons.add(GymResource.getInstance());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}