package controllers;

import play.*;
import play.mvc.*;
import play.db.jpa.Model.*;
import javax.persistence.Query;
import java.util.*;
import com.google.gson.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	render();
    }
    
    public static void ranking(int iDisplayStart, 
                               int iColumns,
                               int iDisplayLength, 
                               String sSearch, 
                               boolean bRegex, 
                               int sEcho,
                               int iSortingCols) {
        
        Map<Long,String> cols = new HashMap<Long, String>();
        cols.put(Long.valueOf(0), "name");
        cols.put(Long.valueOf(1), "score");
        String from = " FROM Player p";
        String orderBy = " ORDER BY ";
        String select = "SELECT p";
        String where = " WHERE p.* like %"+sSearch+"% ";
        
        for(int i = 0;i < iSortingCols;i++) {
            Long col = Long.valueOf(params.get("iSortCol_"+i));
            String colName = cols.get(col);
            orderBy += "p."+colName+" "+params.get("sSortDir_"+i);
        }
        
        Logger.info(select+from+orderBy);
        String query = select+from+where+orderBy;
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Collection<Player> results = Player.find(query).from(iDisplayStart).fetch(iDisplayLength);
        long l = Player.count();
        DataTable response = new DataTable(Player.count(), l , sEcho);
        String json = gson.toJson(response);
        
        Player test = Player.findById(l);
        String jsonData = gson.toJson(results);
        json = json.replace("}", ", \"aaData\":"+jsonData+"}");
        
        renderText(json);
      
    }
}