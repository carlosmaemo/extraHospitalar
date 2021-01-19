package com.pxm.model;

import com.pxm.dao.FacturaDAO;
import com.pxm.exception.ErroSistema;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cmaemo
 */
public class InternamentoTemp {

    private int ordem;

    private Integer idInternamento, idFactura;

    private String internamento, internamentoData, internamentoHora, internamentoTempo, internamentoCodigo, internamentoTipo;
    private double internamentoValor;
    private boolean canEdit;
    
    private FacturaDAO facturaDao = new FacturaDAO();

    public InternamentoTemp(String internamento, String internamentoData, String internamentoHora, String internamentoTempo, double internamentoValor, String internamentoCodigo, String internamentoTipo) {
        this.internamento = internamento;
        this.internamentoData = internamentoData;
        this.internamentoHora = internamentoHora;
        this.internamentoTempo = internamentoTempo;
        this.internamentoValor = internamentoValor;
        this.internamentoCodigo = internamentoCodigo;
        this.internamentoTipo = internamentoTipo;
        canEdit = false;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Integer getIdInternamento() {
        return idInternamento;
    }

    public void setIdInternamento(Integer idInternamento) {
        this.idInternamento = idInternamento;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getInternamento() {
        
        String[] parte = internamento.split("_/");

        return parte[0];
    }

    public void setInternamento(String internamento) {
        this.internamento = internamento;
    }

    public String getInternamentoData() {
        return internamentoData + " / " + internamentoHora;
    }

    public void setInternamentoData(String internamentoData) {
        this.internamentoData = internamentoData;
    }

    public String getInternamentoHora() {
        return internamentoHora;
    }

    public void setInternamentoHora(String internamentoHora) {
        this.internamentoHora = internamentoHora;
    }

    public String getInternamentoTempo() throws ErroSistema {
        
        String[] parte = internamento.split("_/");
        internamentoTempo = facturaDao.verificarDadosInternamento(parte[1], "tempoInternamento");
        
        return internamentoTempo;
    }

    public String getInternamentoCodigo() throws ErroSistema {
        String[] parte = internamento.split("_/");
        internamentoCodigo = facturaDao.verificarDadosInternamento(parte[1], "codigoInternamento");
        
        return internamentoCodigo;
    }

    public void setInternamentoCodigo(String internamentoCodigo) {
        this.internamentoCodigo = internamentoCodigo;
    }

    public String getInternamentoTipo() throws ErroSistema {
        String[] parte = internamento.split("_/");
        internamentoTipo = facturaDao.verificarDadosInternamento(parte[1], "tipoInternamento");
        
        return internamentoTipo;
    }

    public void setInternamentoTipo(String internamentoTipo) {
        this.internamentoTipo = internamentoTipo;
    }

    public void setInternamentoTempo(String internamentoTempo) {
        this.internamentoTempo = internamentoTempo;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public double getInternamentoValor() throws ErroSistema {

        String[] parte = internamento.split("_/");
        internamentoValor = facturaDao.verificarDadosValorInternamento(parte[1]);
        
        return internamentoValor;
    }

    public void setInternamentoValor(double internamentoValor) {
        
        this.internamentoValor = internamentoValor;
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
        final InternamentoTemp other = (InternamentoTemp) obj;
        if (this.ordem != other.ordem) {
            return false;
        }
        return true;
    }

}
