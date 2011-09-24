package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.*;
import java.util.*;

@Entity
public class Player extends Model {
	
       
	@Required
	public long igId;
	       
	@Required
	public String name;
	
	@Required
	public long score;
	
	public long scoreAll;
       
	public long scoreAtt;
      
	public long scoreDef;
        
    public boolean tracked;
    
    @ManyToOne
	@JoinColumn()
	public Ally ally;
	
	@OneToMany(mappedBy="player")
	public List<Town> towns;
	
	@OneToMany(mappedBy="winner")
	public List<Conquer> wins;
	
	@OneToMany(mappedBy="loser")
	public List<Conquer> losses;
        
    @OneToMany(mappedBy="player")
	public List<PlayerScore> history;

}