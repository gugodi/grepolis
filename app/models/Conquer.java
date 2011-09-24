package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

import org.codehaus.jackson.annotate.*;


@Entity
public class Conquer extends Model {
	
	@Required
	public Date date;
	
	public long score;
	
        @JsonBackReference("player-winner")
	@ManyToOne
	@JoinColumn()
	public Player winner;
	
        @JsonBackReference("ally-winner") 
	@ManyToOne
	@JoinColumn()
	public Ally winnerAlly;
	
        @JsonBackReference("player-loser")
	@ManyToOne
	@JoinColumn()
	public Player loser;
	
        @JsonBackReference("ally-loser")
	@ManyToOne
	@JoinColumn()
	public Ally loserAlly;
        
	@JsonBackReference("town-conquer")
	@Required
	@ManyToOne
	@JoinColumn()
	public Town town;
	
	
}
