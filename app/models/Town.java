package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class Town extends Model {
    
		
	@Required
	public long igId;
	
	@Required
	public String name;
	
	@Required
	public long score;

	public long position;
	
	public int x;
	
	public int y;
	
	public boolean isTracked;
	
	@ManyToOne
	@JoinColumn()
 	public Ally ally;

	@ManyToOne
	@JoinColumn()
	public Player player;
	
	@OneToMany(mappedBy= "town")
	public List<TownScore> history;
        
	@OneToMany(mappedBy="town")
	public List<Conquer> conquers;
	
}
