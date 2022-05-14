package aiss.model.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aiss.model.Pokemon;
import aiss.model.Trainer;
import aiss.model.Battle;
import aiss.model.Gym;


public class MapTrainerRepository implements TrainerRepository{

	Map<String, Trainer> trainerMap;
	Map<String, Pokemon> pokemonMap;
	Map<String, Battle> battleMap;
	Map<String, Gym> gymMap;
	private static MapTrainerRepository instance=null;
	private int indexT=0;			
	private int indexB=0;
	private int indexG=0;
	
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
		battleMap = new HashMap<String,Battle>();
		gymMap = new HashMap<String, Gym>();
		
		// Create pokemons
		Pokemon pikachu=new Pokemon();
		pikachu.setName("Pikachu");
		pikachu.setType1("Electric");
		pikachu.setHp(50);
		pikachu.setAttack(60);
		pikachu.setDefense(40);
		pikachu.setGeneration(1);
		pikachu.setLegend(false);
		addPokemon(pikachu);
		
		Pokemon mew=new Pokemon();
		mew.setName("Mew");
		mew.setType1("Psychic");
		mew.setType2(null);
		mew.setHp(75);
		mew.setAttack(75);
		mew.setDefense(75);
		mew.setGeneration(1);
		mew.setLegend(true);
		addPokemon(mew);
		
		Pokemon charmander =new Pokemon();
		charmander.setName("Charmander");
		charmander.setType1("Fire");
		charmander.setType2(null);
		charmander.setHp(30);
		charmander.setAttack(40);
		charmander.setDefense(20);
		charmander.setGeneration(1);
		charmander.setLegend(false);
		addPokemon(charmander);
		
		Pokemon beedrill =new Pokemon();
		beedrill.setName("Beedrill");
		beedrill.setType1("Bug");
		beedrill.setType2("Poison");
		beedrill.setHp(40);
		beedrill.setAttack(60);
		beedrill.setDefense(20);
		beedrill.setGeneration(1);
		beedrill.setLegend(false);
		addPokemon(beedrill);
		
		Pokemon wingull =new Pokemon();
		wingull.setName("Wingull");
		wingull.setType1("Water");
		wingull.setType2("Flying");
		wingull.setHp(20);
		wingull.setAttack(40);
		wingull.setDefense(30);
		wingull.setGeneration(3);
		wingull.setLegend(false);
		addPokemon(wingull);
		
		Pokemon pelipper =new Pokemon();
		pelipper.setName("Pelipper");
		pelipper.setType1("Water");
		pelipper.setType2("Flying");
		pelipper.setHp(40);
		pelipper.setAttack(40);
		pelipper.setDefense(30);
		pelipper.setGeneration(3);
		pelipper.setLegend(false);
		addPokemon(pelipper);
		
		Pokemon magikarp =new Pokemon();
		magikarp.setName("Magikarp");
		magikarp.setType1("Water");
		magikarp.setType2(null);
		magikarp.setHp(10);
		magikarp.setAttack(0);
		magikarp.setDefense(10);
		magikarp.setGeneration(1);
		magikarp.setLegend(false);
		addPokemon(magikarp);
		
		Pokemon sharpedo =new Pokemon();
		sharpedo.setName("Sharpedo");
		sharpedo.setType1("Water");
		sharpedo.setType2("Dark");
		sharpedo.setHp(30);
		sharpedo.setAttack(70);
		sharpedo.setDefense(25);
		sharpedo.setGeneration(3);
		sharpedo.setLegend(false);
		addPokemon(sharpedo);
		
		Pokemon magmar =new Pokemon();
		magmar.setName("Magmar");
		magmar.setType1("Fire");
		magmar.setType2(null);
		magmar.setHp(60);
		magmar.setAttack(60);
		magmar.setDefense(50);
		magmar.setGeneration(1);
		magmar.setLegend(false);
		addPokemon(magmar);
		
		Pokemon groudon =new Pokemon();
		groudon.setName("Groudon");
		groudon.setType1("Ground");
		groudon.setType2(null);
		groudon.setHp(80);
		groudon.setAttack(69);
		groudon.setDefense(80);
		groudon.setGeneration(3);
		groudon.setLegend(true);
		addPokemon(groudon);
		
		Pokemon charizard =new Pokemon();
		charizard.setName("Charizard");
		charizard.setType1("Fire");
		charizard.setType2("Flying");
		charizard.setHp(50);
		charizard.setAttack(70);
		charizard.setDefense(40);
		charizard.setGeneration(1);
		charizard.setLegend(false);
		addPokemon(charizard);
		
		Pokemon zubat =new Pokemon();
		zubat.setName("Zubat");
		zubat.setType1("Poison");
		zubat.setType2("Flying");
		zubat.setHp(20);
		zubat.setAttack(30);
		zubat.setDefense(10);
		zubat.setGeneration(1);
		zubat.setLegend(false);
		addPokemon(zubat);
		
		Pokemon crobat =new Pokemon();
		crobat.setName("Crobat");
		crobat.setType1("Poison");
		crobat.setType2("Flying");
		crobat.setHp(40);
		crobat.setAttack(60);
		crobat.setDefense(25);
		crobat.setGeneration(2);
		crobat.setLegend(false);
		addPokemon(crobat);
		
		Pokemon venusaur =new Pokemon();
		venusaur.setName("Venusaur");
		venusaur.setType1("Grass");
		venusaur.setType2("Poison");
		venusaur.setHp(80);
		venusaur.setAttack(40);
		venusaur.setDefense(70);
		venusaur.setGeneration(1);
		venusaur.setLegend(false);
		addPokemon(venusaur);
		
		Pokemon bastiodon =new Pokemon();
		bastiodon.setName("Bastiodon");
		bastiodon.setType1("Rock");
		bastiodon.setType2("Steel");
		bastiodon.setHp(90);
		bastiodon.setAttack(30);
		bastiodon.setDefense(95);
		bastiodon.setGeneration(4);
		bastiodon.setLegend(false);
		addPokemon(bastiodon);
		
		Pokemon umbreon =new Pokemon();
		umbreon.setName("Espeon");
		umbreon.setType1("Dark");
		umbreon.setType2(null);
		umbreon.setHp(40);
		umbreon.setAttack(60);
		umbreon.setDefense(40);
		umbreon.setGeneration(2);
		umbreon.setLegend(false);
		addPokemon(umbreon);
		
		Pokemon banette =new Pokemon();
		banette.setName("Banette");
		banette.setType1("Ghost");
		banette.setType2(null);
		banette.setHp(40);
		banette.setAttack(65);
		banette.setDefense(30);
		banette.setGeneration(3);
		banette.setLegend(false);
		addPokemon(banette);
		
		Pokemon porygon =new Pokemon();
		porygon.setName("Porygon");
		porygon.setType1("Normal");
		porygon.setType2(null);
		porygon.setHp(60);
		porygon.setAttack(20);
		porygon.setDefense(60);
		porygon.setGeneration(1);
		porygon.setLegend(false);
		addPokemon(porygon);
		
		Pokemon magnezone =new Pokemon();
		magnezone.setName("Magnezone");
		magnezone.setType1("Electric");
		magnezone.setType2("Steel");
		magnezone.setHp(70);
		magnezone.setAttack(55);
		magnezone.setDefense(90);
		magnezone.setGeneration(4);
		magnezone.setLegend(false);
		addPokemon(magnezone);
		
		Pokemon registeel =new Pokemon();
		registeel.setName("Registeel");
		registeel.setType1("Steel");
		registeel.setType2(null);
		registeel.setHp(50);
		registeel.setAttack(30);
		registeel.setDefense(80);
		registeel.setGeneration(3);
		registeel.setLegend(true);
		addPokemon(registeel);
		
		Pokemon jirachi =new Pokemon();
		jirachi.setName("Jirachi");
		jirachi.setType1("Steel");
		jirachi.setType2("Psychic");
		jirachi.setHp(80);
		jirachi.setAttack(30);
		jirachi.setDefense(60);
		jirachi.setGeneration(3);
		jirachi.setLegend(true);
		addPokemon(jirachi);
		
		Pokemon cosmog =new Pokemon();
		cosmog.setName("Cosmog");
		cosmog.setType1("Steel");
		cosmog.setType2(null);
		cosmog.setHp(50);
		cosmog.setAttack(20);
		cosmog.setDefense(30);
		cosmog.setGeneration(7);
		cosmog.setLegend(true);
		addPokemon(cosmog);
		
		Pokemon yamper =new Pokemon();
		yamper.setName("Yamper");
		yamper.setType1("Electric");
		yamper.setType2(null);
		yamper.setHp(50);
		yamper.setAttack(30);
		yamper.setDefense(20);
		yamper.setGeneration(8);
		yamper.setLegend(false);
		addPokemon(yamper);
		
		Pokemon victini =new Pokemon();
		victini.setName("Victini");
		victini.setType1("Fire");
		victini.setType2(null);
		victini.setHp(50);
		victini.setAttack(50);
		victini.setDefense(40);
		victini.setGeneration(5);
		victini.setLegend(true);
		addPokemon(victini);
		
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
		
		Trainer JJ=new Trainer();
		JJ.setName("JJ");
		JJ.setAge(45);
		JJ.setGender("Male");
		addTrainer(JJ);
		
		Trainer mariano=new Trainer();
		mariano.setName("Mariano R.");
		mariano.setAge(69);
		mariano.setGender("Male");
		addTrainer(mariano);
		
		Trainer manue=new Trainer();
		manue.setName("Prof. Manuel");
		manue.setAge(52);
		manue.setGender("Male");
		addTrainer(manue);

		Trainer edgelord=new Trainer();
		edgelord.setName("Edge Lord");
		edgelord.setAge(14);
		edgelord.setGender("Male");
		addTrainer(edgelord);

		Trainer joselin=new Trainer();
		joselin.setName("Joselin");
		joselin.setAge(6);
		joselin.setGender("Male");
		addTrainer(joselin);

		Trainer josefina=new Trainer();
		josefina.setName("Josefina");
		josefina.setAge(98);
		josefina.setGender("Female");
		addTrainer(josefina);
		
		Trainer leaf = new Trainer();
		leaf.setName("Leaf");
		leaf.setAge(18);
		leaf.setGender("Female");
		addTrainer(leaf);
		
		Trainer brendan = new Trainer();
		brendan.setName("Brendan");
		brendan.setAge(17);
		brendan.setGender("Male");
		addTrainer(brendan);
		
		// Add pokemons to trainers
		addPokemon(red.getId(), pikachu.getName());
		addPokemon(red.getId(), mew.getName());
		addPokemon(blue.getId(), pikachu.getName());
		addPokemon(mariano.getId(), wingull.getName());
		addPokemon(mariano.getId(), pelipper.getName());
		addPokemon(mariano.getId(), wingull.getName());
		addPokemon(manue.getId(), porygon.getName());
		addPokemon(edgelord.getId(), banette.getName());
		addPokemon(edgelord.getId(), umbreon.getName());
		addPokemon(edgelord.getId(), sharpedo.getName());
		addPokemon(JJ.getId(), magnezone.getName());
		addPokemon(JJ.getId(), registeel.getName());
		addPokemon(joselin.getId(), venusaur.getName());
		addPokemon(joselin.getId(), bastiodon.getName());
		addPokemon(joselin.getId(), groudon.getName());
		addPokemon(josefina.getId(), beedrill.getName());
		addPokemon(josefina.getId(), charizard.getName());
		addPokemon(leaf.getId(), venusaur.getName());
		addPokemon(leaf.getId(), pelipper.getName());
		addPokemon(leaf.getId(), registeel.getName());
		addPokemon(leaf.getId(), sharpedo.getName());
		addPokemon(leaf.getId(), charizard.getName());
		addPokemon(leaf.getId(), charizard.getName());
		
		// Create battle
		Battle test = new Battle();
		test.setTr1(joselin);
		test.setTr2(josefina);
		test.setName("A battle for the ages");
		test.setWinner(josefina);
		addBattle(test);
		
		Battle b2 = new Battle();
		b2.setTr1(red);
		b2.setTr2(blue);
		b2.setName("Red VS Blue");
		b2.setWinner(red);
		addBattle(b2);
		
		Battle b3 = new Battle();
		b3.setTr1(mariano);
		b3.setTr2(JJ);
		b3.setName("A battle without winner");
		addBattle(b3);
		
		//create gym
		Gym g1 = new Gym();
		g1.setHelpers(List.of(joselin, josefina, edgelord));
		g1.setType("Plant");
		g1.setLeader(leaf);
		addGym(g1);
		
		Gym g2 = new Gym();
		g2.setHelpers(List.of(mariano, JJ));
		g2.setType("Fire");
		g2.setLeader(manue);
		addGym(g2);
	}
	
	// Trainer related operations
	
	@Override
	public void addTrainer(Trainer t) {
        String id = "t" + indexT++;
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
		pokemonMap.put(p.getName(), p);
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
		
		Pokemon pkmn = pokemonMap.get(p.getName());
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
			
	// Battle related operations
			
	@Override
	public void addBattle(Battle b) {
		 String id = "b" + indexB++;
	        b.setId(id);
	        battleMap.put(id,b);
	}
	@Override
    public Collection<Battle> getAllBattles(){
		return battleMap.values();
	}
	@Override
    public Battle getBattle(String BattleId) {
		return battleMap.get(BattleId);
	}
    @Override
    public void removeBattle(String BattleId) {
		battleMap.remove(BattleId);
	}
    @Override
    public void updateBattle(Battle b) {
		battleMap.put(b.getId(),b);
	}
		    
	// Gym related operations
	          
    public void addGym(Gym g) {
        String id = "g" + indexG++;
        g.setId(id);
        gymMap.put(id, g);
    }
    
    public Collection<Gym> getAllGyms(){
        return gymMap.values();
    }
    
    public Gym getGym(String gymId) {
        return gymMap.get(gymId);
    }
    
	public void deleteGym(String gymId) {
		gymMap.remove(gymId);
	}
	
	@Override
	public void addHelper(String gymId, String helperId) {
		Gym gym = getGym(gymId);
		gym.addHelper(trainerMap.get(helperId));
	}

	@Override
	public Collection<Trainer> getAllHelpers(String gymId) {
		return getGym(gymId).getHelpers();
	}

	@Override
	public void removeHelper(String gymId, String helperId) {
		getGym(gymId).deleteHelper(getTrainer(helperId));
	}
}