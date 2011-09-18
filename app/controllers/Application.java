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
        String from = " FROM Player p";
        String orderBy = " ORDER BY ";
        String select = "SELECT p";
        String where =  " WHERE p.name like ? OR p.ally.name like ? ";
        
        for(int i = 0;i < iSortingCols;i++) {
            Long col = Long.valueOf(params.get("iSortCol_"+i));
            String colName = params.get("mDataProp_"+col);
            orderBy += "p."+colName+" "+params.get("sSortDir_"+i);
        }
        
        sSearch = "%"+sSearch+"%";
        String query = select+from+where+orderBy;
        Logger.info(query);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Collection<Player> results = Player.find(query,sSearch,sSearch).from(iDisplayStart).fetch(iDisplayLength);
        long l = Player.count();
        DataTable response = new DataTable(Player.count(), l , sEcho);
        String json = gson.toJson(response);
        
        Player test = Player.findById(l);
        String jsonData = gson.toJson(results);
        json = json.replace("}", ", \"aaData\":"+jsonData+"}");
        
        renderText(json);
      
    }
}