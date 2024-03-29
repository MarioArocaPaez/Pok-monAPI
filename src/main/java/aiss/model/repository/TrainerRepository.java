package aiss.model.repository;

import java.util.Collection;

import aiss.model.Pokemon;
import aiss.model.Trainer;
import aiss.model.Battle;
import aiss.model.Gym;

public interface TrainerRepository {
    
    
    // Pokemons
    public void addPokemon(Pokemon p);
    public Collection<Pokemon> getAllPokemons();
    public Pokemon getPokemon(String PokemonId);
    public void updatePokemon(Pokemon p);
    public void deletePokemon(String PokemonId);
    
    // Trainers
    public void addTrainer(Trainer p);
    public Collection<Trainer> getAllTrainers();
    public Trainer getTrainer(String TrainerId);
    public void updateTrainer(Trainer p);
    public void deleteTrainer(String TrainerId);
    public Collection<Pokemon> getAll(String TrainerId);
	public void addPokemonToTrainer(String trainerId, String pokemonName);
    public void removePokemon(String TrainerId, String PokemonId);
    
    //Battles
    public void addBattle(Battle b);
    public Collection<Battle> getAllBattles();
    public Battle getBattle(String BattleId);
    public void removeBattle(String BattleId);
    public void updateBattle(Battle b);
    
    //Gyms
    public void addGym(Gym g);
    public Collection<Gym> getAllGyms();
    public Gym getGym(String GymId);
	public void deleteGym(String gymId);
	void addHelper(String gymId, String helperId);
	Collection<Trainer> getAllHelpers(String gymId);
	void removeHelper(String gymId, String helperId);

}