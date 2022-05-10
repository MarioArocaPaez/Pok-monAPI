package aiss.model;

public class Pokemon {

	private String name;
	private String type1;
	private String type2;
	private Integer hp;
	private Integer attack;
	private Integer defense;
	private Integer generation;
	private Boolean legend;

	public Pokemon() {
	}

	public Pokemon(String name, String type1, String type2, Integer hp, Integer attack, Integer defense,
			Integer generation, Boolean legend) {
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.generation = generation;
		this.legend = legend;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getAttack() {
		return attack;
	}

	public void setAttack(Integer attack) {
		this.attack = attack;
	}

	public Integer getDefense() {
		return defense;
	}

	public void setDefense(Integer defense) {
		this.defense = defense;
	}

	public Integer getGeneration() {
		return generation;
	}

	public void setGeneration(Integer generation) {
		this.generation = generation;
	}

	public Boolean getLegend() {
		return legend;
	}

	public void setLegend(Boolean legend) {
		this.legend = legend;
	}


}
