package com.pxm.model;

/**
 *
 * @author cmaemo
 */
public class Consumivel {
    
    private Integer idConsumivel;
    private String codigoConsumivel;
    private String categoriaConsumivel;
    private String tituloConsumivel;
    private String valorConsumivel;
    private String composicaoConsumivel;
    private String posologiaConsumivel;

    public Integer getIdConsumivel() {
        return idConsumivel;
    }

    public void setIdConsumivel(Integer idConsumivel) {
        this.idConsumivel = idConsumivel;
    }

    public String getCodigoConsumivel() {
        return codigoConsumivel;
    }

    public void setCodigoConsumivel(String codigoConsumivel) {
        this.codigoConsumivel = codigoConsumivel;
    }

    public String getCategoriaConsumivel() {
        return categoriaConsumivel;
    }

    public void setCategoriaConsumivel(String categoriaConsumivel) {
        this.categoriaConsumivel = categoriaConsumivel;
    }

    public String getTituloConsumivel() {
        return tituloConsumivel;
    }

    public void setTituloConsumivel(String tituloConsumivel) {
        this.tituloConsumivel = tituloConsumivel;
    }

    public String getValorConsumivel() {
        return valorConsumivel;
    }

    public void setValorConsumivel(String valorConsumivel) {
        this.valorConsumivel = valorConsumivel;
    }

    public String getComposicaoConsumivel() {
        return composicaoConsumivel;
    }

    public void setComposicaoConsumivel(String composicaoConsumivel) {
        this.composicaoConsumivel = composicaoConsumivel;
    }

    public String getPosologiaConsumivel() {
        return posologiaConsumivel;
    }

    public void setPosologiaConsumivel(String posologiaConsumivel) {
        this.posologiaConsumivel = posologiaConsumivel;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idConsumivel;
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
        final Consumivel other = (Consumivel) obj;
        if (this.idConsumivel != other.idConsumivel) {
            return false;
        }
        return true;
    }
}
