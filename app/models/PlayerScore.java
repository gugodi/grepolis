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
public class PlayerScore extends Model {
	
        @Expose
	@Required
	public Date date;
	
        @Expose
	@Required
	public long ownerId;
	
        @Expose
	@Required
	public long score;
	
        @Expose
	public long scoreAll;
	
        @Expose
	public long scoreAtt;
	
        @Expose
	public long scoreDef;
        
        @ManyToOne
        @JoinColumn()
        public Player player;
}
