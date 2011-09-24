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
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import play.jobs.*;
import play.db.jpa.*;


import play.Logger;

import models.*;

@OnApplicationStart
@Every("1h")
public class Populator extends Job {
	
	
	public void doJob() {
		
		Date now = new Date();
		
		AllyPop(now);
        PlayerPop(now);
        TownPop(now);
        ConquerPop();
        
	}
	
	private void AllyPop(Date now) {
		Logger.info(">>>>>Ally<<<<<");
		
		Logger.info("Fetch files");
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
		catch (MalformedURLException e) {throw new RuntimeException(e);}
		
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
		
		catch (IOException e) {throw new RuntimeException(e);}
		
		Logger.info("->done");
		
		int i = 0;
		
		Logger.info("alliances.txt");
		Scanner scanner = new Scanner(allyIn).useDelimiter(",");
		

		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			Long igId = ligne.nextLong();
			String name = ligne.next();
			Long score = ligne.nextLong();
			ligne.nextLong();
            ligne.nextLong();
            ligne.nextLong();
			
            ligne.close();
            
            Ally ally = Ally.find("byIgId", igId).first();
            
            if(ally == null){
            	ally = new Ally();
            	ally.igId = igId;
            }
            
			try {ally.name = URLDecoder.decode(name, "UTF-8");} 
			catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
			
			ally.score = score;
            ally.save();
            
            AllyScore allyScore = new AllyScore();
            allyScore.score = ally.score;
            allyScore.date = now;
            allyScore.ally = ally;
            allyScore.save();
                                    
			i++;

			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			
		}
		scanner.close();
		Logger.info("->done");
		
		Logger.info("alliance_kills_all.txt");		
		scanner = new Scanner(allyInAll).useDelimiter(",");
		
		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			ligne.nextLong();
			long allyId = ligne.nextLong();
			long score = ligne.nextLong();	
			
			Ally ally = Ally.find("byIgId", allyId).first();
			ally.scoreAll = score;
            ally.save();
            
            AllyScore allyScore = AllyScore.find("date = ? and ally = ?", now, ally).first();
            allyScore.scoreAll = score;
            allyScore.save();
            
				
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			ligne.close();
		}
		scanner.close();
		Logger.info("->done");
		
		Logger.info("alliance_kills_att.txt");
		scanner = new Scanner(allyInAtt).useDelimiter(",");
		
		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			ligne.nextLong();
			long allyId = ligne.nextLong();
			long score = ligne.nextLong();	
			
			Ally ally = Ally.find("byIgId", allyId).first();
			ally.scoreAtt = score;
            ally.save();
            
            AllyScore allyScore = AllyScore.find("date = ? and ally = ?", now, ally).first();
            allyScore.scoreAtt = score;
            allyScore.save();
				
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			ligne.close();
		}
		
		scanner.close();
		Logger.info("->done");
		
		Logger.info("alliance_kills_def.txt");
		scanner = new Scanner(allyInDef).useDelimiter(",");
		
		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			ligne.nextLong();
			long allyId = ligne.nextLong();
			long score = ligne.nextLong();	
			
			Ally ally = Ally.find("byIgId", allyId).first();
			ally.scoreDef = score;
            ally.save();

            AllyScore allyScore = AllyScore.find("date = ? and ally = ?", now, ally).first();
            allyScore.scoreDef = score;
            allyScore.save();
				
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			ligne.close();
		}
		scanner.close();
		Logger.info("->done");
		
		Logger.info(">>>>>Done<<<<<");
		
	}
	
	private void PlayerPop(Date now) {
		Logger.info(">>>>>Player<<<<<");
		
		Logger.info("Fetch files");
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
		catch (MalformedURLException e) {throw new RuntimeException(e);}
		
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
		
		catch (IOException e) {throw new RuntimeException(e);}
		
		Logger.info("->done");
		
		int i = 0;
		
		Logger.info("players.txt");
		Scanner scanner = new Scanner(playerIn).useDelimiter(",");
		
		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			
			Long igId = ligne.nextLong();
			String name = ligne.next();
			String allyId = ligne.next();
			Long score = ligne.nextLong();
			
			ligne.close();
            
            Player player = Player.find("byIgId", igId).first();
            
            
            if(player == null){
            	player = new Player();
            	player.igId = igId;
            	player
            }
            
			try {player.name = URLDecoder.decode(name, "UTF-8");} 
			catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
			
			if (!allyId.equals("")) {
				Ally ally =  Ally.find("byIgId", Long.valueOf(allyId)).first();
				player.ally = ally;
			}
                     
			player.score = score;
            player.save();
			
            PlayerScore playerScore = new PlayerScore();
            playerScore.score = player.score;
            playerScore.date = now;
            playerScore.player = player;
            if(player.ally != null){
            	playerScore.allyScore = player.ally.history.get(player.ally.history.size() -1);
			}
           	playerScore.save();
            
			
                                    
			i++;

			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			
		}
		scanner.close();
		Logger.info("->done");
		
		Logger.info("player_kills_all.txt");		
		scanner = new Scanner(playerInAll).useDelimiter(",");
		
		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			ligne.nextLong();
			long playerId = ligne.nextLong();
			long score = ligne.nextLong();	
			
			Player player = Player.find("byIgId", playerId).first();
			player.scoreAll = score;
            player.save();

            PlayerScore playerScore = PlayerScore.find("date = ? and player = ?",now,player).first();
            playerScore.scoreAll = score;
			playerScore.save();
				
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			ligne.close();
		}
		scanner.close();
		Logger.info("->done");
		
		Logger.info("player_kills_att.txt");
		scanner = new Scanner(playerInAtt).useDelimiter(",");
		
		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			ligne.nextLong();
			long playerId = ligne.nextLong();
			long score = ligne.nextLong();	
			
			Player player = Player.find("byIgId", playerId).first();
			player.scoreAtt = score;
            player.save();
            
            PlayerScore playerScore = PlayerScore.find("date = ? and player = ?",now,player).first();
            playerScore.scoreAtt = score;
			playerScore.save();
			
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			ligne.close();
		}
		
		
		scanner.close();
		Logger.info("->done");
		
		Logger.info("player_kills_def.txt");
		scanner = new Scanner(playerInDef).useDelimiter(",");
		
		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			ligne.nextLong();
			long playerId = ligne.nextLong();
			long score = ligne.nextLong();	
			
			Player player = Player.find("byIgId", playerId).first();
			player.scoreDef = score;
            player.save();

            PlayerScore playerScore = PlayerScore.find("date = ? and player = ?",now,player).first();
            playerScore.scoreDef = score;
			playerScore.save();
			
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			ligne.close();
		}
		scanner.close();
		Logger.info("->done");
		
		Logger.info(">>>>>Done<<<<<");
		
	}
	
	private void TownPop(Date now) {
		Logger.info(">>>>>Town<<<<<");
		
		Logger.info("Fetch files");
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
		
		Logger.info("->done");
		
		int i = 0;
		
		Logger.info("towns.txt");
		Scanner scanner = new Scanner(townIn).useDelimiter(",");
		
		while(scanner.hasNext()){
			
			Scanner ligne = new Scanner(scanner.nextLine()).useDelimiter(",");
			
			long igId = ligne.nextLong();
			String playerId = ligne.next();
			String name = ligne.next();
			int x = ligne.nextInt();
			int y = ligne.nextInt();
			long position = ligne.nextLong();
			long score = ligne.nextLong();
			
			ligne.close();
			
			
            Town town = Town.find("byIgId", igId).first();
            if(town == null) {
            	town = new Town();
            	town.igId = igId; 
            }
            
			if (!playerId.equals("")) {
				Player player =  Player.find("byIgId", Long.valueOf(playerId)).first();
				town.player = player;
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
			
			TownScore townScore = new TownScore();
			townScore.date = now;
			townScore.town = town;
			if (!playerId.equals("")){
			townScore.playerScore = town.player.history.get(town.player.history.size() -1);
			}
			townScore.save();
			
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
		}
		scanner.close();
		Logger.info("->done");
		Logger.info(">>>>>Done<<<<<");
		
	}
	
	private boolean ConquerPop() {
		Logger.info(">>>>>Conquer<<<<<");
		
		Logger.info("Fetch files");
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
		Logger.info("->done");
		int i = 0;
		
		Logger.info("conquers.txt");
		
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
			
			ligne.close();
			
			Date date = new Date(dateLong*1000);
			
			if(date.after(lastDate)){
				Conquer conquer = new Conquer();
				conquer.town = Town.find("byIgId", townId).first();
				conquer.date = date;
				conquer.winner = Player.find("byIgId", winnerId).first();
				if (!loserId.equals("")) {
					conquer.loser =  Player.find("byIgId", Long.valueOf(loserId)).first();
				}
				if (!winnerAllyId.equals("")) {
					conquer.winnerAlly =  Ally.find("byIgId", Long.valueOf(winnerAllyId)).first();
				}
				if (!loserAllyId.equals("")) {
					conquer.loserAlly = Ally.find("byIgId", Long.valueOf(loserAllyId)).first();
				}
				conquer.score = score;
				conquer.save();
			}
			i++;
			if (i % 20 == 0) {
				JPA.em().flush();
				JPA.em().clear();
			}
			
			
		}
		scanner.close();
		Logger.info("done");
		return true;
	}
	
	public void CleanScore(Date now) {
		
		Date week = new Date(now.getTime() - 3600000*24*7);
		
		Collection<PlayerScore> playerScores= PlayerScore.find("date > ? ", week).fetch();
		
		for(Iterator iter = playerScores.iterator(); iter.hasNext();) {
			
			PlayerScore playerScore = (PlayerScore) iter.next();
			Calendar instance = Calendar.getInstance();
			instance.setTime(playerScore.date);
						
			if(instance.HOUR_OF_DAY != 0 ){
				
				playerScore.delete();
			}
			
		}
		
	}

}
	
	
	

