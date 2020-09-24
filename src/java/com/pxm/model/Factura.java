package com.pxm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cmaemo
 */
public class Factura {

    private Integer idFactura;
    private Integer idPaciente;
    private Integer idMedico;
    private Integer idUsuario;
    
    private String data, dataRegistro;
    private String nomePaciente;
    private String apelidoPaciente;
    private String contactoPaciente;
    private String enderecoPaciente;
    private String nidPaciente;
    private String sexoPaciente;
    private String codigoEmpresa;
    private String nomeEmpresa;
    
    private String nidFiltro, dataInicial, dataFinal;
    
    private String nomeMedico, nomeUsuario;

    public Integer getIdFactura() {
        return idFactura;
    }

    public Integer getIdMedico() {
        
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataRegistro() {
        
        this.dataRegistro = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        
        this.dataRegistro = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        this.dataRegistro = dataRegistro;
    }

    public String getNidFiltro() {
        return nidFiltro;
    }

    public void setNidFiltro(String nidFiltro) {
        this.nidFiltro = nidFiltro;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }


    
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getApelidoPaciente() {
        return apelidoPaciente;
    }

    public void setApelidoPaciente(String apelidoPaciente) {
        this.apelidoPaciente = apelidoPaciente;
    }

    public String getContactoPaciente() {
        return contactoPaciente;
    }

    public void setContactoPaciente(String contactoPaciente) {
        this.contactoPaciente = contactoPaciente;
    }

    public String getEnderecoPaciente() {
        return enderecoPaciente;
    }

    public void setEnderecoPaciente(String enderecoPaciente) {
        this.enderecoPaciente = enderecoPaciente;
    }

    public String getNidPaciente() {
        return nidPaciente;
    }

    public void setNidPaciente(String nidPaciente) {
        this.nidPaciente = nidPaciente;
    }

    public String getSexoPaciente() {
        return sexoPaciente;
    }

    public void setSexoPaciente(String sexoPaciente) {
        this.sexoPaciente = sexoPaciente;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idFactura;
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
        final Factura other = (Factura) obj;
        if (this.idFactura != other.idFactura) {
            return false;
        }
        return true;
    }

}
