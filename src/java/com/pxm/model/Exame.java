package com.pxm.model;

/**
 *
 * @author cmaemo
 */
public class Exame {

    private Integer idExame;
    private String codigoExame;
    private String categoriaExame;
    private String valorExame;
    private String tituloExame;

    private String descricaoExame;

    public Integer getIdExame() {
        return idExame;
    }

    public void setIdExame(Integer idExame) {
        this.idExame = idExame;
    }

    public String getCodigoExame() {
        return codigoExame;
    }

    public void setCodigoExame(String codigoExame) {
        this.codigoExame = codigoExame;
    }

    public String getCategoriaExame() {
        return categoriaExame;
    }

    public void setCategoriaExame(String categoriaExame) {
        this.categoriaExame = categoriaExame;
    }

    public String getTituloExame() {
        return tituloExame;
    }

    public void setTituloExame(String tituloExame) {
        this.tituloExame = tituloExame;
    }

    public String getDescricaoExame() {
        return descricaoExame;
    }

    public void setDescricaoExame(String descricaoExame) {
        this.descricaoExame = descricaoExame;
    }

    public String getValorExame() {
        return valorExame;
    }

    public void setValorExame(String valorExame) {
        this.valorExame = valorExame;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idExame;
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
        final Exame other = (Exame) obj;
        if (this.idExame != other.idExame) {
            return false;
        }
        return true;
    }
}
