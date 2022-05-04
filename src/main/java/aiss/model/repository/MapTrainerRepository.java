package aiss.model.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import aiss.model.Pokemon;
import aiss.model.Trainer;


public class MapTrainerRepository implements TrainerRepository{

	Map<String, Trainer> trainerMap;
	Map<String, Pokemon> pokemonMap;
	private static MapTrainerRepository instance=null;
	private int index=0;			
	
	
	public static MapTrainerRepository getInstance() {
		
		if (instance==null) {
			instance = new MapTrainerRepository();
			instance.init();
		}
		
		return instance;
	}
	
	public void init() {
		
		trainerMap = new HashMap<String,Trainer>();
		pokemonMap = new HashMap<String,Pokemon>();
		
		// Create pokemons
		Pokemon pikachu=new Pokemon();
		pikachu.setName("Pikachu");
		pikachu.setType1("Electric");
		pikachu.setType2(null);
		pikachu.setHp(50);
		pikachu.setAttack(60);
		pikachu.setDefense(40);
		pikachu.setGeneration(1);
		pikachu.setLegend(false);
		addPokemon(pikachu);
		
		Pokemon mew=new Pokemon();
		mew.setName("Pikachu");
		mew.setType1("Psychic");
		mew.setType2(null);
		mew.setHp(75);
		mew.setAttack(75);
		mew.setDefense(75);
		mew.setGeneration(1);
		mew.setLegend(true);
		addPokemon(mew);
		
		// Create trainers
		Trainer red=new Trainer();
		red.setName("Red");
		red.setAge(10);
		red.setGender("Male");
		addTrainer(red);
		
		Trainer blue=new Trainer();
		blue.setName("Blue");
		blue.setAge(11);
		blue.setGender("Male");
		addTrainer(blue);
		
		// Add pokemons to trainers
		addPokemon(red.getId(), pikachu.getId());
		addPokemon(red.getId(), mew.getId());
		addPokemon(blue.getId(), pikachu.getId());

	}
	
	// Trainer related operations
			@Override
			public void addTrainer(Trainer t) {
		        String id = "t" + index++;
		        t.setId(id);
		        trainerMap.put(id,t);
		    }
			
			@Override
			public Collection<Trainer> getAllTrainers() {
					return trainerMap.values();
			}

			@Override
			public Trainer getTrainer(String id) {
				return trainerMap.get(id);
			}
			
			@Override
			public void updateTrainer(Trainer t) {
				trainerMap.put(t.getId(),t);
			}

			@Override
			public void deleteTrainer(String id) {	
				trainerMap.remove(id);
			}
			

			@Override
			public void addPokemon(String trainerId, String pokemonId) {
				Trainer trainer = getTrainer(trainerId);
				trainer.addPokemon(pokemonMap.get(pokemonId));
			}

			@Override
			public Collection<Pokemon> getAll(String trainerId) {
				return getTrainer(trainerId).getPokemons();
			}

			@Override
			public void removePokemon(String trainerId, String pokemonId) {
				getTrainer(trainerId).deletePokemon(pokemonId);
			}

			
	// Pokemon related operations
			
			@Override
			public void addPokemon(Pokemon p) {
				String id = "s" + index++;
				p.setId(id);
				pokemonMap.put(id, p);
			}
			
			@Override
			public Collection<Pokemon> getAllPokemons() {
					return pokemonMap.values();
			}

			@Override
			public Pokemon getPokemon(String pokemonId) {
				return pokemonMap.get(pokemonId);
			}

			@Override
			public void updatePokemon(Pokemon p) {
				
				Pokemon pkmn = pokemonMap.get(p.getId());
				pkmn.setName(p.getName());
				pkmn.setType1(p.getType1());
				pkmn.setType2(p.getType2());
				pkmn.setHp(p.getHp());
				pkmn.setAttack(p.getAttack());
				pkmn.setDefense(p.getDefense());
				pkmn.setGeneration(p.getGeneration());
				pkmn.setLegend(p.getLegend());
			}

			@Override
			public void deletePokemon(String pokemonId) {
				pokemonMap.remove(pokemonId);
			}
}
