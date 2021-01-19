package com.pxm.model;

import com.pxm.dao.FacturaDAO;
import com.pxm.exception.ErroSistema;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cmaemo
 */
public class VisitaTemp {

    private int ordem;

    private Integer idVisita, idFactura;

    private String visita, visitaData, visitaHora, visitaTempo, visitaCodigo, visitaTipo;
    private double visitaValor;
    private boolean canEdit;
    
    private FacturaDAO facturaDao = new FacturaDAO();

    public VisitaTemp(String visita, String visitaData, String visitaHora, String visitaTempo, double visitaValor, String visitaCodigo, String visitaTipo) {
        this.visita = visita;
        this.visitaData = visitaData;
        this.visitaHora = visitaHora;
        this.visitaTempo = visitaTempo;
        this.visitaValor = visitaValor;
        this.visitaCodigo = visitaCodigo;
        this.visitaTipo = visitaTipo;
        canEdit = false;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Integer getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(Integer idVisita) {
        this.idVisita = idVisita;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getVisita() {
        
        String[] parte = visita.split("_/");

        return parte[0];
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }

    public String getVisitaData() {
        return visitaData + " / " + visitaHora;
    }

    public void setVisitaData(String visitaData) {
        this.visitaData = visitaData;
    }

    public String getVisitaHora() {
        return visitaHora;
    }

    public void setVisitaHora(String visitaHora) {
        this.visitaHora = visitaHora;
    }

    public String getVisitaTempo() throws ErroSistema {
        
        String[] parte = visita.split("_/");
        visitaTempo = facturaDao.verificarDadosVisita(parte[1], "tempoVisita");
        
        return visitaTempo;
    }

    public String getVisitaCodigo() throws ErroSistema {
        String[] parte = visita.split("_/");
        visitaCodigo = facturaDao.verificarDadosVisita(parte[1], "codigoVisita");
        return visitaCodigo;
    }

    public void setVisitaCodigo(String visitaCodigo) {
        this.visitaCodigo = visitaCodigo;
    }

    public String getVisitaTipo() throws ErroSistema {
        String[] parte = visita.split("_/");
        visitaTipo = facturaDao.verificarDadosVisita(parte[1], "tipoVisita");
        return visitaTipo;
    }

    public void setVisitaTipo(String visitaTipo) {
        this.visitaTipo = visitaTipo;
    }
    
    public void setVisitaTempo(String visitaTempo) {
        this.visitaTempo = visitaTempo;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public double getVisitaValor() throws ErroSistema {

        String[] parte = visita.split("_/");
        visitaValor = facturaDao.verificarDadosValorVisita(parte[1]);
        
        return visitaValor;
    }

    public void setVisitaValor(double visitaValor) {
        this.visitaValor = visitaValor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.ordem;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VisitaTemp other = (VisitaTemp) obj;
        if (this.ordem != other.ordem) {
            return false;
        }
        return true;
    }

}
