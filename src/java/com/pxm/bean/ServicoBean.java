package com.pxm.bean;

import com.pxm.util.Conecxao;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Carlos Maemo
 */

@ManagedBean
@SessionScoped
public class ServicoBean implements Serializable {

    public Conecxao conecxao = new Conecxao();
    
    private String db;

    public String getDb() {
        db = conecxao.db;
        return db;
    }

    public void setDb(String db) {
        this.db = conecxao.db;
    }
    
    

}
