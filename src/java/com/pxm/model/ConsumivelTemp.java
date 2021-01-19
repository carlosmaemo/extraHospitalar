package com.pxm.model;

import com.pxm.dao.FacturaDAO;
import com.pxm.exception.ErroSistema;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cmaemo
 */
public class ConsumivelTemp {

    private int ordem;

    private Integer idConsumivel, idFactura;

    private String consumivel, consumivelData, consumivelHora, consumivelCategoria, consumivelCodigo, consumivelTitulo, consumivelComposicao, consumivelPosologia;
    private double consumivelValor;
    private boolean canEdit;
    
    private FacturaDAO facturaDao = new FacturaDAO();

    public ConsumivelTemp(String consumivel, String consumivelData, String consumivelHora, String consumivelCategoria, double consumivelValor, String consumivelCodigo, String consumivelTitulo, String consumivelComposicao, String consumivelPosologia) {
        this.consumivel = consumivel;
        this.consumivelData = consumivelData;
        this.consumivelHora = consumivelHora;
        this.consumivelCategoria = consumivelCategoria;
        this.consumivelValor = consumivelValor;
        this.consumivelCodigo = consumivelCodigo;
        this.consumivelTitulo = consumivelTitulo;
        this.consumivelComposicao = consumivelComposicao;
        this.consumivelPosologia = consumivelPosologia;
        canEdit = false;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Integer getIdConsumivel() {
        return idConsumivel;
    }

    public void setIdConsumivel(Integer idConsumivel) {
        this.idConsumivel = idConsumivel;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getConsumivel() {
        
        String[] parte = consumivel.split("_/");

        return parte[0];
    }

    public String getConsumivelCodigo() throws ErroSistema {
        
        String[] parte = consumivel.split("_/");
        consumivelCodigo = facturaDao.verificarDadosConsumivel(parte[1], "codigoConsumivel");
        
        return consumivelCodigo;
    }

    public void setConsumivelCodigo(String consumivelCodigo) {
        this.consumivelCodigo = consumivelCodigo;
    }

    public String getConsumivelTitulo() throws ErroSistema {
        
        String[] parte = consumivel.split("_/");
        consumivelTitulo = facturaDao.verificarDadosConsumivel(parte[1], "tituloConsumivel");
        
        return consumivelTitulo;
    }

    public void setConsumivelTitulo(String consumivelTitulo) {
        this.consumivelTitulo = consumivelTitulo;
    }

    public String getConsumivelComposicao() throws ErroSistema {
        
        String[] parte = consumivel.split("_/");
        consumivelComposicao = facturaDao.verificarDadosConsumivel(parte[1], "composicaoConsumivel");
        return consumivelComposicao;
    }

    public void setConsumivelComposicao(String consumivelComposicao) {
        this.consumivelComposicao = consumivelComposicao;
    }

    public String getConsumivelPosologia() throws ErroSistema {
        
        String[] parte = consumivel.split("_/");
        consumivelPosologia = facturaDao.verificarDadosConsumivel(parte[1], "posologiaConsumivel");
        
        return consumivelPosologia;
    }

    public void setConsumivelPosologia(String consumivelPosologia) {
        this.consumivelPosologia = consumivelPosologia;
    }
    
    
    public void setConsumivel(String consumivel) {
        this.consumivel = consumivel;
    }

    public String getConsumivelData() {
        return consumivelData + " / " + consumivelHora;
    }

    public void setConsumivelData(String consumivelData) {
        this.consumivelData = consumivelData;
    }

    public String getConsumivelHora() {
        return consumivelHora;
    }

    public void setConsumivelHora(String consumivelHora) {
        this.consumivelHora = consumivelHora;
    }

    public String getConsumivelCategoria() throws ErroSistema {
        
        String[] parte = consumivel.split("_/");
        consumivelCategoria = facturaDao.verificarDadosConsumivel(parte[1], "categoriaConsumivel");
        
        return consumivelCategoria;
    }

    public void setConsumivelCategoria(String consumivelCategoria) {
        this.consumivelCategoria = consumivelCategoria;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public double getConsumivelValor() throws ErroSistema {

        String[] parte = consumivel.split("_/");
        consumivelValor = facturaDao.verificarDadosValorConsumivel(parte[1]);
        
        return consumivelValor;
    }

    public void setConsumivelValor(double consumivelValor) {
        
        this.consumivelValor = consumivelValor;
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
        final ConsumivelTemp other = (ConsumivelTemp) obj;
        if (this.ordem != other.ordem) {
            return false;
        }
        return true;
    }

}
