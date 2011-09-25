package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

import java.util.*;

@Entity
public class AllyScore extends Model {
	
	@Required
	public Date date;
	
	@Required
	public long score;
	
	public long rank;
	
	public long scoreAll;
	
	public long rankAll;
               
	public long scoreAtt;
	
	public long rankAtt;
             
	public long scoreDef;
	
	public long rankDef;

    @ManyToOne
    @JoinColumn()
    public Ally ally;
    
    @OneToMany(mappedBy="allyScore")
    public List<PlayerScore> playerScores;
}
