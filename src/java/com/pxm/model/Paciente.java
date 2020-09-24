package com.pxm.model;

import javax.faces.application.FacesMessage;

/**
 *
 * @author cmaemo
 */
public class Paciente {

    private Integer idPaciente;
    private String nomePaciente;
    private String apelidoPaciente;
    private String contactoPaciente;
    private String enderecoPaciente;
    private String nidPaciente;
    private String sexoPaciente;
    private String codigoEmpresa;
    private String nomeEmpresa;

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
    
    @Override

    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idPaciente;
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
        final Paciente other = (Paciente) obj;
        if (this.idPaciente != other.idPaciente) {
            return false;
        }
        return true;
    }

}
