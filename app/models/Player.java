package models;

import play.*;
import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

import javax.persistence.*;
import java.util.*;

@Entity
public class Player extends Model {
	
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
    
	@ManyToOne
	@JoinColumn()
	public Ally ally;
	
	@OneToMany(mappedBy="player")
	public List<Town> towns;
	
	@OneToMany(mappedBy="winner")
	public List<Conquer> wins;
	
	@OneToMany(mappedBy="loser")
	public List<Conquer> losses;

	public Player(Date date) {
		this.date = date;
	}
	
}
