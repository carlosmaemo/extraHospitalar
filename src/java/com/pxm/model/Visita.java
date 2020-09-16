/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.model;

/**
 *
 * @author cmaemo
 */
public class Visita {

    private Integer idVisita;
    private String codigoVisita;   
    private String tipoVisita;
    private String tempoVisita;
    private String valorVisita;

    public Integer getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(Integer idVisita) {
        this.idVisita = idVisita;
    }

    public String getCodigoVisita() {
        return codigoVisita;
    }

    public void setCodigoVisita(String codigoVisita) {
        this.codigoVisita = codigoVisita;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getTempoVisita() {
        return tempoVisita;
    }

    public void setTempoVisita(String tempoVisita) {
        this.tempoVisita = tempoVisita;
    }

    public String getValorVisita() {
        return valorVisita;
    }

    public void setValorVisita(String valorVisita) {
        this.valorVisita = valorVisita;
    }
 
    @Override

    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idVisita;
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
        final Visita other = (Visita) obj;
        if (this.idVisita != other.idVisita) {
            return false;
        }
        return true;
    }
}
