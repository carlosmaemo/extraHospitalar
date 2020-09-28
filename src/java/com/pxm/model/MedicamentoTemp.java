package com.pxm.model;

import com.pxm.dao.FacturaDAO;
import com.pxm.exception.ErroSistema;

/**
 *
 * @author cmaemo
 */
public class MedicamentoTemp {

    private int ordem;

    private Integer idMedicamento, idFactura;

    private String medicamento, medicamentoData, medicamentoHora, medicamentoCategoria, medicamentoCodigo, medicamentoTitulo, medicamentoComposicao, medicamentoPosologia;
    private double medicamentoValor;
    private boolean canEdit;
    
    private FacturaDAO facturaDao = new FacturaDAO();

    public MedicamentoTemp(String medicamento, String medicamentoData, String medicamentoHora, String medicamentoCategoria, double medicamentoValor, String medicamentoCodigo, String medicamentoTitulo, String medicamentoComposicao, String medicamentoPosologia) {
        this.medicamento = medicamento;
        this.medicamentoData = medicamentoData;
        this.medicamentoHora = medicamentoHora;
        this.medicamentoCategoria = medicamentoCategoria;
        this.medicamentoValor = medicamentoValor;
        this.medicamentoCodigo = medicamentoCodigo;
        this.medicamentoTitulo = medicamentoTitulo;
        this.medicamentoComposicao = medicamentoComposicao;
        this.medicamentoPosologia = medicamentoPosologia;
        canEdit = false;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getMedicamento() {
        
        String[] parte = medicamento.split("_/");

        return parte[0];
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getMedicamentoData() {
        return medicamentoData + " / " + medicamentoHora;
    }

    public void setMedicamentoData(String medicamentoData) {
        this.medicamentoData = medicamentoData;
    }

    public String getMedicamentoHora() {
        return medicamentoHora;
    }

    public void setMedicamentoHora(String medicamentoHora) {
        this.medicamentoHora = medicamentoHora;
    }

    public String getMedicamentoCategoria() throws ErroSistema {
        
        String[] parte = medicamento.split("_/");
        medicamentoCategoria = facturaDao.verificarDadosMedicamento(parte[1], "categoriaMedicamento");
        
        return medicamentoCategoria;
    }

    public void setMedicamentoCategoria(String medicamentoCategoria) {
        this.medicamentoCategoria = medicamentoCategoria;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public double getMedicamentoValor() throws ErroSistema {

        String[] parte = medicamento.split("_/");
        medicamentoValor = facturaDao.verificarDadosValorMedicamento(parte[1]);
        
        return medicamentoValor;
    }

    public void setMedicamentoValor(double medicamentoValor) {
        this.medicamentoValor = medicamentoValor;
    }

    public String getMedicamentoCodigo() throws ErroSistema {
        
        String[] parte = medicamento.split("_/");
        medicamentoCodigo = facturaDao.verificarDadosMedicamento(parte[1], "codigoMedicamento");
        return medicamentoCodigo;
    }

    public void setMedicamentoCodigo(String medicamentoCodigo) {
        this.medicamentoCodigo = medicamentoCodigo;
    }

    public String getMedicamentoTitulo() throws ErroSistema {
        String[] parte = medicamento.split("_/");
        medicamentoTitulo = facturaDao.verificarDadosMedicamento(parte[1], "tituloMedicamento");
        return medicamentoTitulo;
    }

    public void setMedicamentoTitulo(String medicamentoTitulo) {
        this.medicamentoTitulo = medicamentoTitulo;
    }

    public String getMedicamentoComposicao() throws ErroSistema {
        String[] parte = medicamento.split("_/");
        medicamentoComposicao = facturaDao.verificarDadosMedicamento(parte[1], "composicaoMedicamento");
        return medicamentoComposicao;
    }

    public void setMedicamentoComposicao(String medicamentoComposicao) {
        this.medicamentoComposicao = medicamentoComposicao;
    }

    public String getMedicamentoPosologia() throws ErroSistema {
        String[] parte = medicamento.split("_/");
        medicamentoPosologia = facturaDao.verificarDadosMedicamento(parte[1], "posologiaMedicamento");
        return medicamentoPosologia;
    }

    public void setMedicamentoPosologia(String medicamentoPosologia) {
        this.medicamentoPosologia = medicamentoPosologia;
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
        final MedicamentoTemp other = (MedicamentoTemp) obj;
        if (this.ordem != other.ordem) {
            return false;
        }
        return true;
    }

}
