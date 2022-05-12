package aiss.model.repository;

import java.util.Collection;

import aiss.model.Pokemon;
import aiss.model.Trainer;
import aiss.model.Battle;

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
    public void addPokemon(String TrainerId, String PokemonId);
    public void removePokemon(String TrainerId, String PokemonId);
    
    //Battles
    public void addBattle(Battle b);
   // public void calculateBattle(Battle b);
    public Collection<Battle> getAllBattles();
    public Battle getBattle(String BattleId);

}