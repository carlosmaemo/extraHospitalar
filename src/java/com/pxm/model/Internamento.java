package com.pxm.model;

/**
 *
 * @author cmaemo
 */
public class Internamento {

    private Integer idInternamento;
    private String codigoInternamento;    
    private String tipoInternamento;
    private String tempoInternamento;
    private double valorInternamento;

    public Integer getIdInternamento() {
        return idInternamento;
    }

    public void setIdInternamento(Integer idInternamento) {
        this.idInternamento = idInternamento;
    }

    public String getCodigoInternamento() {
        return codigoInternamento;
    }

    public void setCodigoInternamento(String codigoInternamento) {
        this.codigoInternamento = codigoInternamento;
    }

    public String getTipoInternamento() {
        return tipoInternamento;
    }

    public void setTipoInternamento(String tipoInternamento) {
        this.tipoInternamento = tipoInternamento;
    }

    public String getTempoInternamento() {
        return tempoInternamento;
    }

    public void setTempoInternamento(String tempoInternamento) {
        this.tempoInternamento = tempoInternamento;
    }

    public double getValorInternamento() {
        return valorInternamento;
    }

    public void setValorInternamento(double valorInternamento) {
        this.valorInternamento = valorInternamento;
    }
    
    @Override

    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idInternamento;
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
        final Internamento other = (Internamento) obj;
        if (this.idInternamento != other.idInternamento) {
            return false;
        }
        return true;
    }
}
