package models;

import play.*;
import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Ally extends Model {
	
	@Required
	public Date date;
    
	@Required
	public long igId;
	
	@Required
	public String name;
	
	@Required
	public long score;
	
	public long scoreAll;
	
	public long scoreAtt;
	
	public long scoreDef;
    
		
	@OneToMany(mappedBy="ally")
	public List<Player> players;
	
	@OneToMany(mappedBy="winnerAlly")
	public List<Conquer> wins;
	
	@OneToMany(mappedBy="loserAlly")
	public List<Conquer> losses;
	
	public Ally(Date date) {
		this.date = date;
	}
}
