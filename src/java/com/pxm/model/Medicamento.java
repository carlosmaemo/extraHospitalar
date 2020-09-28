package com.pxm.model;

/**
 *
 * @author cmaemo
 */
public class Medicamento {

    private Integer idMedicamento;
    private String codigoMedicamento;    
    private String categoriaMedicamento;
    private String tituloMedicamento;
    private double valorMedicamento;
    private String composicaoMedicamento;
    private String posologiaMedicamento;

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public void setCodigoMedicamento(String codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }

    public String getCategoriaMedicamento() {
        return categoriaMedicamento;
    }

    public void setCategoriaMedicamento(String categoriaMedicamento) {
        this.categoriaMedicamento = categoriaMedicamento;
    }

    public String getTituloMedicamento() {
        return tituloMedicamento;
    }

    public void setTituloMedicamento(String tituloMedicamento) {
        this.tituloMedicamento = tituloMedicamento;
    }

    public double getValorMedicamento() {
        return valorMedicamento;
    }

    public void setValorMedicamento(double valorMedicamento) {
        this.valorMedicamento = valorMedicamento;
    }

    public String getComposicaoMedicamento() {
        return composicaoMedicamento;
    }

    public void setComposicaoMedicamento(String composicaoMedicamento) {
        this.composicaoMedicamento = composicaoMedicamento;
    }

    public String getPosologiaMedicamento() {
        return posologiaMedicamento;
    }

    public void setPosologiaMedicamento(String posologiaMedicamento) {
        this.posologiaMedicamento = posologiaMedicamento;
    }
    
    @Override

    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idMedicamento;
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
        final Medicamento other = (Medicamento) obj;
        if (this.idMedicamento != other.idMedicamento) {
            return false;
        }
        return true;
    }
}
