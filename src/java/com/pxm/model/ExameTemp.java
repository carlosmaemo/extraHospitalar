package com.pxm.model;

import com.pxm.dao.FacturaDAO;
import com.pxm.exception.ErroSistema;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cmaemo
 */
public class ExameTemp {

    private int ordem;

    private Integer idExame, idFactura;

    private String exame, exameData, exameHora, exameCategoria, exameCodigo, exameTitulo, exameDescricao;
    private double exameValor, exameValorTotal;
    private int exameQuantidade;
    private boolean canEdit;

    private FacturaDAO facturaDao = new FacturaDAO();

    public ExameTemp(String exame, String exameData, String exameHora, String exameCategoria, double exameValor, double exameValorTotal, String exameCodigo, String exameTitulo, String exameDescricao, int exameQuantidade) {
        this.exame = exame;
        this.exameData = exameData;
        this.exameHora = exameHora;
        this.exameCategoria = exameCategoria;
        this.exameValor = exameValor;
        this.exameValorTotal = exameValorTotal;
        this.exameCodigo = exameCodigo;
        this.exameTitulo = exameTitulo;
        this.exameDescricao = exameDescricao;
        this.exameQuantidade = exameQuantidade;
        canEdit = false;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Integer getIdExame() {
        return idExame;
    }

    public void setIdExame(Integer idExame) {
        this.idExame = idExame;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getExame() {
        String[] parte = exame.split("_/");

        return parte[0];
    }

    public void setExame(String exame) {
        this.exame = exame;
    }

    public String getExameData() {
        return exameData + " / " + exameHora;
    }

    public void setExameData(String exameData) {
        this.exameData = exameData;
    }

    public String getExameHora() {
        return exameHora;
    }

    public String getExameCategoria() throws ErroSistema {
        
        String[] parte = exame.split("_/");
        exameCategoria = facturaDao.verificarDadosExame(parte[1], "categoriaExame");
        
        return exameCategoria;
    }

    public String getExameCodigo() throws ErroSistema {
        
        String[] parte = exame.split("_/");
        exameCodigo = facturaDao.verificarDadosExame(parte[1], "codigoExame");
        return exameCodigo;
    }

    public void setExameCodigo(String exameCodigo) {
        this.exameCodigo = exameCodigo;
    }

    public String getExameTitulo() throws ErroSistema {
        String[] parte = exame.split("_/");
        exameTitulo = facturaDao.verificarDadosExame(parte[1], "tituloExame");
        return exameTitulo;
    }

    public void setExameTitulo(String exameTitulo) {
        this.exameTitulo = exameTitulo;
    }

    public String getExameDescricao() throws ErroSistema {
        String[] parte = exame.split("_/");
        exameDescricao = facturaDao.verificarDadosExame(parte[1], "descricaoExame");
        return exameDescricao;
    }

    public void setExameDescricao(String exameDescricao) {
        this.exameDescricao = exameDescricao;
    }

    public void setExameCategoria(String exameCategoria) {
        this.exameCategoria = exameCategoria;
    }
    
    public void setExameHora(String exameHora) {
        this.exameHora = exameHora;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public double getExameValor() throws ErroSistema {

        String[] parte = exame.split("_/");
        exameValor = facturaDao.verificarDadosValorExame(parte[1]);

        return exameValor;
    }

    public void setExameValor(double exameValor) {

        this.exameValor = exameValor;
    }

    public double getExameValorTotal() {
        
        exameValorTotal = exameValor * exameQuantidade;
        return exameValorTotal;
    }

    public void setExameValorTotal(double exameValorTotal) {
        
        exameValorTotal = exameValor * exameQuantidade;
        this.exameValorTotal = exameValorTotal;
    }

    public int getExameQuantidade() {
        return exameQuantidade;
    }

    public void setExameQuantidade(int exameQuantidade) {
        this.exameQuantidade = exameQuantidade;
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
        final ExameTemp other = (ExameTemp) obj;
        if (this.ordem != other.ordem) {
            return false;
        }
        return true;
    }

}
