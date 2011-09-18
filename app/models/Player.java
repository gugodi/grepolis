package models;

import com.google.gson.annotations.*;
import play.*;
import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

import javax.persistence.*;
import java.util.*;

@Entity
public class Player extends Model {
	
        @Expose
	@Required
	public long igId;
	
        @Expose
	@Required
	public String name;
	
        @Expose
	@Required
	public long score;
	
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