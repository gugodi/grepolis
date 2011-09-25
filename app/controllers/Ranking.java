/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.annotations.*;

import flexjson.*;

import java.util.*;

import models.*;
import play.*;
import play.mvc.*;
import play.db.jpa.Model.*;
/**
 *
 * @author memo
 */
public class Ranking extends Controller {	
	
	public static void Players(String name, String col ) {
		Player player = Player.find("byName", name).first();
		if (col == null) {
			col = "rank";
		}
		else {
		
			if (col.toLowerCase().equals("overall")) col = "rankAll";
			if (col.toLowerCase().equals("defense")) col = "rankDef";
			if (col.toLowerCase().equals("offence")) col = "rankAtt";
			
			
		}
		render(player,col);
	}
	
	public static void playerTable(int iDisplayStart, 
						            int iColumns, 
						            String sSearch, 
						            boolean bRegex, 
						            int sEcho,
						            int iSortingCols) {

				Long colId = Long.valueOf(params.get("iSortCol_0"));
				String col = params.get("mDataProp_"+colId);
				col = col.replace("rank", "score");
				String sort = params.get("sSortDir_0");
				String query = "order by "+col+" "+sort;
				List<Player> players = Player.find(query).from(iDisplayStart).fetch(25);
				DataTable<Player> response = new DataTable(Player.count(), Player.count() , sEcho, players);
				JSONSerializer serializer = new JSONSerializer();
				String test = serializer.include("aaData","aaData.towns").serialize(response);
				renderText(test);
				
			}
	
	public static void Allies(String name, String col ) {
		Ally ally = Ally.find("byName", name).first();
		if (col == null) {
			col = "rank";
		}
		else {
		
			if (col.toLowerCase().equals("overall")) col = "rankAll";
			if (col.toLowerCase().equals("defense")) col = "rankDef";
			if (col.toLowerCase().equals("offence")) col = "rankAtt";
			
			
		}
		render(ally,col);
	}
	
	public static void allyTable(int iDisplayStart, 
						            int iColumns, 
						            String sSearch, 
						            boolean bRegex, 
						            int sEcho,
						            int iSortingCols) {

				Long colId = Long.valueOf(params.get("iSortCol_0"));
				String col = params.get("mDataProp_"+colId);
				col = col.replace("rank", "score");
				String sort = params.get("sSortDir_0");
				String query = "order by "+col+" "+sort;
				List<Ally> allies = Ally.find(query).from(iDisplayStart).fetch(25);
				DataTable<Player> response = new DataTable(Player.count(), Player.count() , sEcho, allies);
				JSONSerializer serializer = new JSONSerializer();
				String test = serializer.include("aaData","aaData.townCount").serialize(response);
				
				
				
				
				
				Logger.info(col);
				
				
				renderText(test);
				
			}
		
	  
}
