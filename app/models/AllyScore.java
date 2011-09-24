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
	
	public long scoreAll;

	public long scoreAtt;
	
	public long scoreDef;

    @ManyToOne
    @JoinColumn()
    public Ally ally;
    
    @OneToMany(mappedBy="allyScore")
    public List<PlayerScore> playerScores;
}
