package aiss.model;

import java.util.List;

public class Gym {
	
	private String id;
	private String type;
	private List<Trainer> helpers;
	private Trainer leader;
	
	public Gym() {
		
	}
	
	public Gym(String id, String type, List<Trainer> helpers, Trainer leader) {
		this.id = id;
		this.type = type;
		this.helpers = helpers;
		this.leader = leader;
	}
	public Gym(String type, List<Trainer> helpers, Trainer leader) {
		this.type = type;
		this.helpers = helpers;
		this.leader = leader;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Trainer> getHelpers() {
		return helpers;
	}

	public void setHelpers(List<Trainer> helpers) {
		this.helpers = helpers;
	}

	public Trainer getLeader() {
		return leader;
	}

	public void setLeader(Trainer leader) {
		this.leader = leader;
	}
	


}