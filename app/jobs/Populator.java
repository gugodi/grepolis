package jobs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import play.jobs.*;
import play.db.jpa.*;


import play.Logger;

import models.*;

@OnApplicationStart
public class Populator extends Job {
	
	
	public void doJob() {
		
		Logger.info("Clearing Database");
		Player.deleteAll();
		Town.deleteAll();
		Ally.deleteAll();
		Conquer.deleteAll();
		JPA.em().flush();
		JPA.em().clear();
		
		Logger.info("Starting Populator Job");
		URL allyUrl;
		URL playerUrl;
		URL townUrl;
		URL conquerUrl;
		URL allyUrlAll;
		URL allyUrlAtt;
		URL allyUrlDef;
		URL playerUrlAll;
		URL playerUrlAtt;
		URL playerUrlDef;
		try {
			allyUrl = new URL("http://fr16.grepolis.com/data/alliances.txt.gz");
			allyUrlAll = new URL("http://fr16.grepolis.com/data/alliance_kills_all.txt.gz");
			allyUrlAtt = new URL("http://fr16.grepolis.com/data/alliance_kills_att.txt.gz");
			allyUrlDef = new URL("http://fr16.grepolis.com/data/alliance_kills_def.txt.gz");
			playerUrl = new URL("http://fr16.grepolis.com/data/players.txt.gz");
			playerUrlAll = new URL("http://fr16.grepolis.com/data/player_kills_all.txt.gz");
			playerUrlAtt = new URL("http://fr16.grepolis.com/data/player_kills_att.txt.gz");
			playerUrlDef = new URL("http://fr16.grepolis.com/data/player_kills_def.txt.gz");
			townUrl = new URL("http://fr16.grepolis.com/data/towns.txt.gz");
			conquerUrl = new URL("http://fr16.grepolis.com/data/conquers.txt.gz"); 
		} 
		catch (MalformedURLException e) {
			
			throw new RuntimeException(e);
		}
		GZIPInputStream allyIn = null;
		GZIPInputStream playerIn = null;
		GZIPInputStream townIn = null;
		GZIPInputStream conquerIn = null;
		GZIPInputStream allyInAll = null;
		GZIPInputStream allyInAtt = null;
		GZIPInputStream allyInDef = null;
		GZIPInputStream playerInAll = null;
		GZIPInputStream playerInAtt = null;
		GZIPInputStream playerInDef = null;
		try {
			allyIn = new GZIPInputStream((InputStream) allyUrl.getContent());
			playerIn = new GZIPInputStream((InputStream) playerUrl.getContent());
			townIn = new GZIPInputStream((InputStream) townUrl.getContent());
			conquerIn = new GZIPInputStream((InputStream) conquerUrl.getContent());
			allyInAll = new GZIPInputStream((InputStream) allyUrlAll.getContent());
			allyInAtt = new GZIPInputStream((InputStream) allyUrlAtt.getContent());
			allyInDef = new GZIPInputStream((InputStream) allyUrlDef.getContent());
			playerInAll = new GZIPInputStream((InputStream) playerUrlAll.getContent());
			playerInAtt = new GZIPInputStream((InputStream) playerUrlAtt.getContent());
			playerInDef = new GZIPInputStream((InputStream) playerUrlDef.getContent());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		int i = 0;
		
		Scanner scanner = new Scanner(allyIn).useDelimiter("\n");
		Logger.info("-----Alliances:-----");
		
		
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Ally ally = new Ally(new Date());
			
			
			ally.igId = ligne.nextLong();
			try {
				ally.name = URLDecoder.decode(ligne.next(), "UTF-8");
			} 
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			
			ally.score = ligne.nextLong();
			
			ally.save();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----Joueurs:-----");
		scanner = new Scanner(playerIn).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Player player = new Player(new Date());
			
			player.igId = ligne.nextLong();
			
			try {
				player.name = URLDecoder.decode(ligne.next(), "UTF-8");
			} 
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			
			String allyId = ligne.next();
			
			if (!allyId.equals("")) {
				player.ally =  Ally.find("byIgId", Long.valueOf(allyId)).first();
			}
			player.score = ligne.nextLong();
			
			player.save();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----Villages:-----");
		scanner = new Scanner(townIn).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Town town = new Town(new Date());
			
			town.igId = ligne.nextLong();
			
			String playerId = ligne.next();
			if (!playerId.equals("")) {
				town.player =  Player.find("byIgId", Long.valueOf(playerId)).first();
				if(town.player.ally != null){
					town.ally = town.player.ally;
				}
			}
			
			try {
				town.name = URLDecoder.decode(ligne.next(), "UTF-8");
			} 
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			town.x = ligne.nextInt();
			town.y = ligne.nextInt();
			town.position = ligne.nextLong();
			town.score = ligne.nextLong();
			
			town.save();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----Conquer:-----");
		scanner = new Scanner(conquerIn).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Conquer conquer = new Conquer();
			
			conquer.town = Town.find("byIgId", ligne.nextLong()).first();
			conquer.date = new Date(ligne.nextLong());
			conquer.winner = Player.find("byIgId", ligne.nextLong()).first();
						
			String loserId = ligne.next();
			if (!loserId.equals("")) {
				conquer.loser =  Player.find("byIgId", Long.valueOf(loserId)).first();
			}
			
			String winnerAllyId = ligne.next();
			if (!winnerAllyId.equals("")) {
				conquer.winnerAlly =  Ally.find("byIgId", Long.valueOf(winnerAllyId)).first();
			}
			
			String loserAllyId = ligne.next();
			if (!loserAllyId.equals("")) {
				conquer.loserAlly =  Ally.find("byIgId", Long.valueOf(loserAllyId)).first();
			}
			
			Logger.info(String.valueOf(conquer.town.igId)) ;
			conquer.score = ligne.nextLong();
						
			conquer.save();
			
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----PlayerAll:-----");
		scanner = new Scanner(playerInAll).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			ligne.next();
			Player player = Player.find("byIgId", ligne.nextLong()).first();
			player.scoreAll = ligne.nextLong();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----PlayerAtt:-----");
		scanner = new Scanner(playerInAtt).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			ligne.next();
			Player player = Player.find("byIgId", ligne.nextLong()).first();
			player.scoreAtt = ligne.nextLong();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----PlayerDef:-----");
		scanner = new Scanner(playerInDef).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Player player = Player.find("byIgId", ligne.nextLong()).first();
			player.scoreDef = ligne.nextLong();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----AllyAll:-----");
		scanner = new Scanner(allyInAll).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Ally ally = Ally.find("byIgId", ligne.nextLong()).first();
			ally.scoreAll = ligne.nextLong();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----AllyAtt:-----");
		scanner = new Scanner(allyInAtt).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Ally ally = Ally.find("byIgId", ligne.nextLong()).first();
			ally.scoreAtt = ligne.nextLong();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----AllyDef:-----");
		scanner = new Scanner(allyInDef).useDelimiter("\n");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Ally ally = Ally.find("byIgId", ligne.nextLong()).first();
			ally.scoreDef = ligne.nextLong();
			i++;
			if (i % 200 == 0) {
				JPA.em().flush();
				JPA.em().clear();
				Logger.info(".");
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("Flushing and Clearing");
		JPA.em().flush();
		JPA.em().clear();
		Logger.info("end");
	}
}
	
	
	

