package aiss.model;

public class Battle {
	
	private String id;
	private String name;
	private Trainer tr1;
	private Trainer tr2;
	private Trainer winner;
	
	public Battle() {
	}
	
	public Battle(String name, Trainer tr1, Trainer tr2, Trainer winner) {
		this.name = name;
		this.tr1 = tr1;
		this.tr2 = tr2;
		this.winner = winner;
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
    
    public Trainer getTr1() {
        return tr1;
    }

    public void setTr1(Trainer  tr1) {
        this.tr1 = tr1;
    }
    public Trainer  getTr2() {
        return tr2;
    }

    public void setTr2(Trainer  tr2) {
        this.tr2 = tr2;
    }
    public Trainer getWinner() {
        return winner;
    }

    public void setWinner(Trainer winner) {
    	this.winner = winner;
    }
}
