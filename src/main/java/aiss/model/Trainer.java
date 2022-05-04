package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class Trainer {

    private String id;
    private String name;
    private Integer age;
    private String gender;
    private List<Pokemon> pokemons;
    
	public Trainer() {}
    
	public Trainer(String name) {
		this.name = name;
	}
    
    public Trainer(String name, Integer age, String gender, List<Pokemon> pokemons) {
    	checkRightAmountPokemon(new Trainer(name,age,gender,pokemons));
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.pokemons = pokemons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
    this.pokemons = pokemons;
    }
    
	public Pokemon getPokemon(String id) {
		if (pokemons==null)
			return null;
		
		Pokemon poke =null;
		for(Pokemon p: pokemons)
			if (p.getId().equals(id))
			{
				poke=p;
				break;
			}
		
		return poke;
	}
	
	public void addPokemon(Pokemon p) {
		if (pokemons==null)
			pokemons = new ArrayList<Pokemon>();
		pokemons.add(p);
	}
	
	public void deletePokemon(Pokemon p) {
		pokemons.remove(p);
	}
	
	public void deletePokemon(String id) {
		Pokemon p = getPokemon(id);
		if (p!=null)
			pokemons.remove(p);
	}
	
	public static void checkRightAmountPokemon(Trainer t){
		if(!(t.pokemons.size() < 6)) {
			throw new IllegalArgumentException("A trainer team must have less than 6 Pokémon only");
		}
	}
	
}