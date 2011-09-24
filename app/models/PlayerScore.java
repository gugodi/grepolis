package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

import java.util.*;

@Entity
public class PlayerScore extends Model {
	
	@Required
	public Date date;
	
	@Required
	public long score;
	
	public long scoreAll;
	
	public long scoreAtt;

	public long scoreDef;

    @ManyToOne
    @JoinColumn()
    public Player player;
    
    @OneToMany(mappedBy="playerScore")
    public List<TownScore> townScores;
    
    @ManyToOne
	@JoinColumn()
	public AllyScore allyScore;
}
