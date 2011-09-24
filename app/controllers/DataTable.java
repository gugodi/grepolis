package controllers;

import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.Expose;

public class DataTable<T> {

	@Expose
    private long iTotalRecords;
    
    @Expose
    private long iTotalDisplayRecords;
    
    @Expose
    private int sEcho;
    
    private Collection<T> aaData;
  
    
    public DataTable(long iTotalRecords, long iTotalDisplayRecords, int sEcho, Collection<T> aaData) {
        
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.sEcho = sEcho;
        this.aaData = aaData;
        
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

    public Collection<T> getAaData() {
        return aaData;
    }

    public void setAaData(Collection<T> aaData) {
        this.aaData = aaData;
    }
}
