/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.annotations.*;
/**
 *
 * @author memo
 */
public class DataTable {
    
    @Expose
    private long iTotalRecords;
    
    @Expose
    private long iTotalDisplayRecords;
    
    @Expose
    private int sEcho;
  
    
    public DataTable(long iTotalRecords, long iTotalDisplayRecords, int sEcho) {
        
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.sEcho = sEcho;
        
    }

    public long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }
    
    
}
