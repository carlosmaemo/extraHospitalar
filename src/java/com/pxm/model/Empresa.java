package com.pxm.model;

/**
 *
 * @author cmaemo
 */
public class Empresa {
    
    private Integer idEmpresa;
    private String nomeEmpresa;   
    private String nuitEmpresa;
    private String contactoEmpresa;
    private String emailEmpresa;
    private String enderecoEmpresa;
    private String codigoEmpresa;

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getNuitEmpresa() {
        return nuitEmpresa;
    }

    public void setNuitEmpresa(String nuitEmpresa) {
        this.nuitEmpresa = nuitEmpresa;
    }

    public String getContactoEmpresa() {
        return contactoEmpresa;
    }

    public void setContactoEmpresa(String contactoEmpresa) {
        this.contactoEmpresa = contactoEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getEnderecoEmpresa() {
        return enderecoEmpresa;
    }

    public void setEnderecoEmpresa(String enderecoEmpresa) {
        this.enderecoEmpresa = enderecoEmpresa;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idEmpresa;
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
        final Empresa other = (Empresa) obj;
        if (this.idEmpresa != other.idEmpresa) {
            return false;
        }
        return true;
    }
}
