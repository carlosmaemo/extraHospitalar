/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.bean;

import com.pxm.dao.MedicoDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Medico;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author cmaemo
 */
@ManagedBean
@SessionScoped
public class MedicoBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Medico medico = new Medico();

    private List<Medico> medicos = new ArrayList<>();
    private final MedicoDAO medicoDao = new MedicoDAO();

    private String valor_pesquisa;
    
    private UploadedFile ficheiro;
    private String caminho = "D:\\temp";
    
    @PostConstruct
    public void init() {
        try {
            listar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void carregar() throws ClassNotFoundException, SQLException, IOException, ErroSistema {
       
        try (InputStream input = ficheiro.getInputstream()) {
            String nomeFicheiro = ficheiro.getFileName();

            String[] parte = nomeFicheiro.split(Pattern.quote("."));
            String ficheiroNome = parte[0];
            String ficheiroExtensao = parte[1];

            Path caminhoFicheiroApagar = Paths.get("D:\\temp\\temp-medico." + ficheiroExtensao);

            Files.deleteIfExists(caminhoFicheiroApagar);

            Files.copy(input, new File(caminho, "temp-medico." + ficheiroExtensao).toPath());
            String caminhoFicheiro = caminho + "\\temp-medico." + ficheiroExtensao;

            if (medicoDao.carregar(caminhoFicheiro) == true) {

                addMensagem("Carregado!", "Arquivo carregado com sucesso.", FacesMessage.SEVERITY_INFO);
                
                pesquisar("falso");

            } else {

                addMensagem("Falha!", "Ocorreu uma falha ao carregar o arquivo.", FacesMessage.SEVERITY_INFO);

            }
        }

    }
    
    public void adicionar() throws ClassNotFoundException, SQLException, ErroSistema {

        if (medico.getNomeMedico().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Nome' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medico.getApelidoMedico().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Apelido' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medico.getEspecialidadeMedico().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Especialidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicoDao.verificar(medico) == true && editar == false) {
            addMensagem("Médico existente!", "O médico já encontra-se registrado.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (medicoDao.verificar(medico) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            medicoDao.actualizar(medico);
            medico.setIdMedico(null);
            medico.setNomeMedico("");
            medico.setApelidoMedico("");
            medico.setContactoMedico("");
            medico.setEnderecoMedico("");
            medico.setOrmmMedico("");
            medico.setEspecialidadeMedico("");

            listar();
            addMensagem("Actualizado!", "Dados do médico actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                medicoDao.salvar(medico);
                medico.setIdMedico(null);
                medico.setNomeMedico("");
                medico.setApelidoMedico("");
                medico.setContactoMedico("");
                medico.setEnderecoMedico("");
                medico.setOrmmMedico("");
                medico.setEspecialidadeMedico("");

                listar();

                addMensagem("Registrado!", "Médico registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (medico != null) {

            if (!"".equals(medico.getNomeMedico()) || !"".equals(medico.getApelidoMedico())
                    || !"".equals(medico.getEspecialidadeMedico())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                medico.setIdMedico(null);
                medico.setNomeMedico("");
                medico.setApelidoMedico("");
                medico.setContactoMedico("");
                medico.setEnderecoMedico("");
                medico.setOrmmMedico("");
                medico.setEspecialidadeMedico("");

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                medico.setIdMedico(null);
                medico.setNomeMedico("");
                medico.setApelidoMedico("");
                medico.setContactoMedico("");
                medico.setEnderecoMedico("");
                medico.setOrmmMedico("");
                medico.setEspecialidadeMedico("");

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            medicos = medicoDao.buscar();
            if (medicos == null || medicos.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de médicos.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Medico c) throws ClassNotFoundException, SQLException {
        try {
            medicoDao.deletar(c.getIdMedico());
            listar();
            addMensagem("Removido!", "Médico removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Medico c) {
        medico = c;
        editar = true;
        modo_edicao = true;
    }

    public void addMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public void reload() {

        try {
            countReload++;
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession(false);
            session.invalidate();
            FacesContext temp = FacesContext.getCurrentInstance();
            temp.getExternalContext().redirect("index.jsf");
        } catch (IOException ex) {
        }
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean isModo_edicao() {
        return modo_edicao;
    }

    public void setModo_edicao(boolean modo_edicao) {
        this.modo_edicao = modo_edicao;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }
    
    public String getValor_pesquisa() {
        return valor_pesquisa;
    }

    public void setValor_pesquisa(String valor_pesquisa) {
        this.valor_pesquisa = valor_pesquisa;
    }

    public void pesquisar(String tipo) throws ClassNotFoundException, SQLException {
        try {

            if (tipo.equals("verdadeiro")) {
                if (valor_pesquisa.isEmpty()) {
                    medicos = medicoDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    medicos = medicoDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            }
            else {
                valor_pesquisa = "";
                listar();
            }
                
            if (medicos == null || medicos.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de médico com este valor.", FacesMessage.SEVERITY_WARN);
            }

        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }

    public UploadedFile getFicheiro() {
        return ficheiro;
    }

    public void setFicheiro(UploadedFile ficheiro) {
        this.ficheiro = ficheiro;
    }
    
}
