package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;


@Entity
public class Conquer extends Model {
	
	@Required
	public Date date;
	
	public long score;
	
	@ManyToOne
	@JoinColumn()
	public Player winner;
	
	@ManyToOne
	@JoinColumn()
	public Ally winnerAlly;
	
	@ManyToOne
	@JoinColumn()
	public Player loser;
	
       
	@ManyToOne
	@JoinColumn()
	public Ally loserAlly;

	@Required
	@ManyToOne
	@JoinColumn()
	public Town town;
	
	
}
