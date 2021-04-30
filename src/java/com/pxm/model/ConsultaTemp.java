package com.pxm.model;

import com.pxm.dao.FacturaDAO;
import com.pxm.exception.ErroSistema;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cmaemo
 */
public class ConsultaTemp {

    private int ordem;

    private Integer idConsulta, idFactura;

    private String consulta, consultaData, consultaHora, consultaMedico, consultaCodigo, consultaCategoria, consultaTitulo, consultaDescricao;
    private double consultaValor, consultaValorTotal;
    private int consultaQuantidade;
    private boolean canEdit;

    private FacturaDAO facturaDao = new FacturaDAO();

    public ConsultaTemp(String consulta, String consultaData, String consultaHora, String consultaMedico, double consultaValor, double consultaValorTotal, String consultaCodigo, String consultaCategoria, String consultaTitulo, String consultaDescricao, int consultaQuantidade) {
        this.consulta = consulta;
        this.consultaData = consultaData;
        this.consultaHora = consultaHora;
        this.consultaMedico = consultaMedico;
        this.consultaValor = consultaValor;
        this.consultaValorTotal = consultaValorTotal;
        this.consultaCodigo = consultaCodigo;
        this.consultaCategoria = consultaCategoria;
        this.consultaTitulo = consultaTitulo;
        this.consultaDescricao = consultaDescricao;
        this.consultaQuantidade = consultaQuantidade;
        canEdit = false;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getConsulta() {

        String[] parte = consulta.split("_/");

        return parte[0];
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getConsultaData() {
        return consultaData + " / " + consultaHora;
    }

    public void setConsultaData(String consultaData) {
        this.consultaData = consultaData;
    }

    public String getConsultaHora() {
        return consultaHora;
    }

    public void setConsultaHora(String consultaHora) {
        this.consultaHora = consultaHora;
    }

    public String getConsultaMedico() {
        
        String[] parte = consultaMedico.split("_/");

        return parte[0];
    }

    public void setConsultaMedico(String consultaMedico) {
        this.consultaMedico = consultaMedico;
    }

    public String getConsultaCodigo() throws ErroSistema {
        
        String[] parte = consulta.split("_/");
        consultaCodigo = facturaDao.verificarDadosConsulta(parte[1], "codigoConsulta");
      
        return consultaCodigo;
    }

    public void setConsultaCodigo(String consultaCodigo) {
        this.consultaCodigo = consultaCodigo;
    }

    public String getConsultaCategoria() throws ErroSistema {
        
        String[] parte = consulta.split("_/");
        consultaCategoria = facturaDao.verificarDadosConsulta(parte[1], "categoriaConsulta");
        
        return consultaCategoria;
    }

    public void setConsultaCategoria(String consultaCategoria) {
        this.consultaCategoria = consultaCategoria;
    }

    public String getConsultaTitulo() throws ErroSistema {
        
        String[] parte = consulta.split("_/");
        consultaTitulo = facturaDao.verificarDadosConsulta(parte[1], "tituloConsulta");
        
        return consultaTitulo;
    }

    public void setConsultaTitulo(String consultaTitulo) {
        this.consultaTitulo = consultaTitulo;
    }

    public String getConsultaDescricao() throws ErroSistema {
        
        String[] parte = consulta.split("_/");
        consultaDescricao = facturaDao.verificarDadosConsulta(parte[1], "descricaoConsulta");
        
        return consultaDescricao;
    }

    public void setConsultaDescricao(String consultaDescricao) {
        this.consultaDescricao = consultaDescricao;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public double getConsultaValor() throws ErroSistema {

        String[] parte = consulta.split("_/");
        consultaValor = facturaDao.verificarDadosValorConsulta(parte[1]);

        return consultaValor;
    }

    public void setConsultaValor(double consultaValor) {
        this.consultaValor = consultaValor;
    }

    public double getConsultaValorTotal() {
        consultaValorTotal = consultaValor * consultaQuantidade;
        return consultaValorTotal;
    }

    public void setConsultaValorTotal(double consultaValorTotal) {
        consultaValorTotal = consultaValor * consultaQuantidade;
        this.consultaValorTotal = consultaValorTotal;
    }

    
    
    public int getConsultaQuantidade() {
        return consultaQuantidade;
    }

    public void setConsultaQuantidade(int consultaQuantidade) {
        this.consultaQuantidade = consultaQuantidade;
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
        final ConsultaTemp other = (ConsultaTemp) obj;
        if (this.ordem != other.ordem) {
            return false;
        }
        return true;
    }

}
