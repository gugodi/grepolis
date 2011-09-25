package controllers;
import play.*;
import play.mvc.*;
import play.db.jpa.Model.*;
import java.util.*;
import models.*;
import flexjson.*;


public class PlayerController extends Controller {

	public static void view(Long id) {
		
		Player player = Player.findById(id);
		Logger.info(player.name+String.valueOf(player.id));
		render(player);
	}
	
	public static void panel(Long id) {
		
		Player player = Player.findById(id);
		Logger.info(player.name+String.valueOf(player.id));
		render(player);
	}
}
