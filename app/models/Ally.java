package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class Ally extends Model {
	
    
	@Required
	public long igId;
	
	@Required
	public String name;
	
	@Required
	public long score;
	
    public long rank;
        
	public long scoreAll;
               
	public long scoreAtt;
             
	public long scoreDef;
        
    public boolean isTracked;

	@OneToMany(mappedBy="ally")
	public List<Player> players;
	
	@OneToMany(mappedBy="ally")
	public List<Player> towns;
	
	@OneToMany(mappedBy="winnerAlly")
	public List<Conquer> wins;
	
	@OneToMany(mappedBy="loserAlly")
	public List<Conquer> losses;
	
    @OneToMany(mappedBy="ally")
	public List<AllyScore> history;
}