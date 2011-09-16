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
		
		AllyPop();
		PlayerPop();
		TownPop();
		ConquerPop();
		
	}
	
	private boolean AllyPop() {
		
		URL allyUrl;
		URL allyUrlAll;
		URL allyUrlAtt;
		URL allyUrlDef;
		try {
			allyUrl = new URL("http://fr16.grepolis.com/data/alliances.txt.gz");
			allyUrlAll = new URL("http://fr16.grepolis.com/data/alliance_kills_all.txt.gz");
			allyUrlAtt = new URL("http://fr16.grepolis.com/data/alliance_kills_att.txt.gz");
			allyUrlDef = new URL("http://fr16.grepolis.com/data/alliance_kills_def.txt.gz");
		} 
		catch (MalformedURLException e) {
			
			throw new RuntimeException(e);
		}
		GZIPInputStream allyIn = null;
		GZIPInputStream allyInAll = null;
		GZIPInputStream allyInAtt = null;
		GZIPInputStream allyInDef = null;

		try {
			allyIn = new GZIPInputStream((InputStream) allyUrl.getContent());
			allyInAll = new GZIPInputStream((InputStream) allyUrlAll.getContent());
			allyInAtt = new GZIPInputStream((InputStream) allyUrlAtt.getContent());
			allyInDef = new GZIPInputStream((InputStream) allyUrlDef.getContent());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		int i = 0;
		
		Scanner scanner = new Scanner(allyIn).useDelimiter(",");
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
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----AllyAll:-----");
		scanner = new Scanner(allyInAll).useDelimiter(",");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			ligne.next();
			long allyId = ligne.nextLong();
			long score = ligne.nextLong();			
			Ally ally = Ally.find("byIgId", allyId).first();
			ally.scoreAll = score;
			ally.save();
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----AllyAtt:-----");
		scanner = new Scanner(allyInAtt).useDelimiter(",");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			ligne.next();
			long allyId = ligne.nextLong();
			long score = ligne.nextLong();			
			Ally ally = Ally.find("byIgId", allyId).first();
			ally.scoreAtt = score;
			ally.save();
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----AllyDef:-----");
		scanner = new Scanner(allyInDef).useDelimiter(",");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			ligne.next();
			long allyId = ligne.nextLong();
			long score = ligne.nextLong();			
			Ally ally = Ally.find("byIgId", allyId).first();
			ally.scoreDef = score;
			ally.save();
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		return true;
	}
	
	public boolean PlayerPop() {

		URL playerUrl;
		URL playerUrlAll;
		URL playerUrlAtt;
		URL playerUrlDef;
		try {
			playerUrl = new URL("http://fr16.grepolis.com/data/players.txt.gz");
			playerUrlAll = new URL("http://fr16.grepolis.com/data/player_kills_all.txt.gz");
			playerUrlAtt = new URL("http://fr16.grepolis.com/data/player_kills_att.txt.gz");
			playerUrlDef = new URL("http://fr16.grepolis.com/data/player_kills_def.txt.gz");
			} 
		catch (MalformedURLException e) {
			
			throw new RuntimeException(e);
		}
		GZIPInputStream playerIn = null;
		GZIPInputStream playerInAll = null;
		GZIPInputStream playerInAtt = null;
		GZIPInputStream playerInDef = null;
		try {
			playerIn = new GZIPInputStream((InputStream) playerUrl.getContent());
			playerInAll = new GZIPInputStream((InputStream) playerUrlAll.getContent());
			playerInAtt = new GZIPInputStream((InputStream) playerUrlAtt.getContent());
			playerInDef = new GZIPInputStream((InputStream) playerUrlDef.getContent());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		int i = 0;
		
		Logger.info("-----Joueurs:-----");
		Scanner scanner = new Scanner(playerIn).useDelimiter(",");
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
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----PlayerAll:-----");
		scanner = new Scanner(playerInAll).useDelimiter(",");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
		    ligne.next();
			long playerId = ligne.nextLong();
			long score = ligne.nextLong();
			Player player = Player.find("byIgId", playerId).first();
			player.scoreAtt = score;
			player.save();
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----PlayerAtt:-----");
		scanner = new Scanner(playerInAtt).useDelimiter(",");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			ligne.next();
			long playerId = ligne.nextLong();
			long score = ligne.nextLong();
			Player player = Player.find("byIgId", playerId).first();
			player.scoreAtt = score;
			player.save();
			i++;
			if (i % 1000 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		Logger.info("-----PlayerDef:-----");
		scanner = new Scanner(playerInDef).useDelimiter(",");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			ligne.next();
			long playerId = ligne.nextLong();
			long score = ligne.nextLong();
			Player player = Player.find("byIgId", playerId).first();
			player.scoreDef = score;
			player.save();
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		return true;
	}
	
	public boolean TownPop() {
		
		URL townUrl;
		
		try {
			townUrl = new URL("http://fr16.grepolis.com/data/towns.txt.gz");
		} 
		catch (MalformedURLException e) {
			
			throw new RuntimeException(e);
		}
		GZIPInputStream townIn = null;
		try {
			townIn = new GZIPInputStream((InputStream) townUrl.getContent());
			
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		int i = 0;
		Logger.info("-----Villages:-----");
		Scanner scanner = new Scanner(townIn).useDelimiter(",");
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			Town town = new Town(new Date());
			long igId = ligne.nextLong();
			String playerId = ligne.next();
			String name = ligne.next();
			int x = ligne.nextInt();
			int y = ligne.nextInt();
			long position = ligne.nextLong();
			long score = ligne.nextLong();
			town.igId = igId;
			
			
			if (!playerId.equals("")) {
				town.player =  Player.find("byIgId", Long.valueOf(playerId)).first();
				/*if(town.player.ally != null){
					town.ally = town.player.ally;
				}*/
			}
			
			try {
				town.name = URLDecoder.decode(name, "UTF-8");
			} 
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			town.x = x;
			town.y = y;
			town.position = position;
			town.score = score;
			
			town.save();
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
		}
		scanner.close();
		Logger.info("done");
		return true;
	}
	
	public boolean ConquerPop() {
		
		Date now = new Date();
		URL conquerUrl;
		
		try {
			conquerUrl = new URL("http://fr16.grepolis.com/data/conquers.txt.gz");
		} 
		catch (MalformedURLException e) {
			
			throw new RuntimeException(e);
		}
		GZIPInputStream conquerIn = null;
		try {
			conquerIn = new GZIPInputStream((InputStream) conquerUrl.getContent());
			
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		int i = 0;
		Logger.info("-----Conquer:-----");
		Scanner scanner = new Scanner(conquerIn).useDelimiter(",");
		Conquer lastConquer = Conquer.find("SELECT c FROM Conquer c ORDER BY c.date DESC").first();
		Date lastDate = lastConquer == null ? new Date(0): lastConquer.date;
		while(scanner.hasNext()){
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			long townId = ligne.nextLong();
			long dateLong = ligne.nextInt();
			long winnerId = ligne.nextLong();
			String winnerAllyId = ligne.next();
			String loserId = ligne.next();
			String loserAllyId = ligne.next();
			long score = ligne.nextLong();
			
			Date date = new Date(dateLong*1000);
			Logger.info("ig:"+dateLong+", last:"+lastDate.getTime()+", trans:"+date.getTime()+" today:"+now.getTime());
			if(date.after(lastDate)){
				Conquer conquer = new Conquer();
				conquer.town = Town.find("byIgId", townId).first();
				conquer.date = date;
				conquer.winner = Player.find("byIgId", winnerId).first();
				Logger.info("ben si !!!!");
			
				if (!loserId.equals("")) {
					conquer.loser =  Player.find("byIgId", Long.valueOf(loserId)).first();
				}
				
				
				if (!winnerAllyId.equals("")) {
					conquer.winnerAlly =  Ally.find("byIgId", Long.valueOf(winnerAllyId)).first();
				}
				
				
				if (!loserAllyId.equals("")) {
					conquer.loserAlly =  Ally.find("byIgId", Long.valueOf(loserAllyId)).first();
				}
				
				
				conquer.score = score;
							
				conquer.save();
			}
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			ligne.close();
			
		}
		scanner.close();
		Logger.info("done");
		return true;
	}

}
	
	
	

