package com.pxm.model;

/**
 *
 * @author cmaemo
 */
public class Medico {

    private Integer idMedico;
    private String nomeMedico;     
    private String apelidoMedico;
    private String contactoMedico;
    private String enderecoMedico;
    private String ormmMedico;
    private String especialidadeMedico;

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getApelidoMedico() {
        return apelidoMedico;
    }

    public void setApelidoMedico(String apelidoMedico) {
        this.apelidoMedico = apelidoMedico;
    }

    public String getContactoMedico() {
        return contactoMedico;
    }

    public void setContactoMedico(String contactoMedico) {
        this.contactoMedico = contactoMedico;
    }

    public String getEnderecoMedico() {
        return enderecoMedico;
    }

    public void setEnderecoMedico(String enderecoMedico) {
        this.enderecoMedico = enderecoMedico;
    }

    public String getOrmmMedico() {
        return ormmMedico;
    }

    public void setOrmmMedico(String ormmMedico) {
        this.ormmMedico = ormmMedico;
    }

    public String getEspecialidadeMedico() {
        return especialidadeMedico;
    }

    public void setEspecialidadeMedico(String especialidadeMedico) {
        this.especialidadeMedico = especialidadeMedico;
    }
    
    @Override

    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idMedico;
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
        final Medico other = (Medico) obj;
        if (this.idMedico != other.idMedico) {
            return false;
        }
        return true;
    }
}
