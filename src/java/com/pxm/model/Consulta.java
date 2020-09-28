package com.pxm.model;

/**
 *
 * @author cmaemo
 */

public class Consulta {

    private Integer idConsulta;
    private String codigoConsulta;
    private String categoriaConsulta;
    private String tituloConsulta;
    private String descricaoConsulta;
    private double valorConsulta;

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    public void setCodigoConsulta(String codigoConsulta) {
        this.codigoConsulta = codigoConsulta;
    }

    public String getCategoriaConsulta() {
        return categoriaConsulta;
    }

    public void setCategoriaConsulta(String categoriaConsulta) {
        this.categoriaConsulta = categoriaConsulta;
    }

    public String getTituloConsulta() {
        return tituloConsulta;
    }

    public void setTituloConsulta(String tituloConsulta) {
        this.tituloConsulta = tituloConsulta;
    }

    public String getDescricaoConsulta() {
        return descricaoConsulta;
    }

    public void setDescricaoConsulta(String descricaoConsulta) {
        this.descricaoConsulta = descricaoConsulta;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idConsulta;
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
        final Consulta other = (Consulta) obj;
        if (this.idConsulta != other.idConsulta) {
            return false;
        }
        return true;
    }
    
}
