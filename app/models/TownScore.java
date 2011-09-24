package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class TownScore extends Model {
	
	@Required
	public Date date;
	
	@Required
	public long ownerId;
	
	@Required
	public long score;
	
	@ManyToOne
    @JoinColumn()
	public Town town;
	
	@ManyToOne
	@JoinColumn()
	public PlayerScore playerScore;
}
