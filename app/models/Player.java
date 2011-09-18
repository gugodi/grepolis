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
	
        @Expose
	public long scoreAll;
        
        @Expose
	public long scoreAtt;
        
        @Expose
	public long scoreDef;
        
        @Expose
	public long rankAll;
        
        @Expose
	public long rankAtt;
        
        @Expose
	public long rankDef;
        
        @Expose
        public long rank;
        
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
        
        @Expose
        @Transient
        public String allyName;
        
        @Expose
        @Transient
        public int townCount;
        
        
        public String getAllyName() {
            return ally.name;
        }

        public int getTownCount() {
            return towns.size();
        }
        
}