/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.bean;

import com.pxm.dao.PacienteDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Paciente;
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
public class PacienteBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Paciente paciente = new Paciente();

    private List<Paciente> pacientes = new ArrayList<>();
    private List<String> codigoEmpresas = new ArrayList<>();
    private final PacienteDAO pacienteDao = new PacienteDAO();

    private String valor_pesquisa;
    
    private UploadedFile ficheiro;
    private String caminho = "D:\\temp";

    @PostConstruct
    public void init() {

        pesquisarCodigoEmpresas();

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

            Path caminhoFicheiroApagar = Paths.get("D:\\temp\\temp-paciente." + ficheiroExtensao);

            Files.deleteIfExists(caminhoFicheiroApagar);

            Files.copy(input, new File(caminho, "temp-paciente." + ficheiroExtensao).toPath());
            String caminhoFicheiro = caminho + "\\temp-paciente." + ficheiroExtensao;

            if (pacienteDao.carregar(caminhoFicheiro) == true) {

                addMensagem("Carregado!", "Arquivo carregado com sucesso.", FacesMessage.SEVERITY_INFO);
                
                pesquisar("falso");

            } else {

                addMensagem("Falha!", "Ocorreu uma falha ao carregar o arquivo.", FacesMessage.SEVERITY_INFO);

            }
        }

    }
    
    public void adicionar() throws ClassNotFoundException, SQLException, ErroSistema {

        if (paciente.getNomePaciente().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Nome' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (paciente.getApelidoPaciente().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Apelido' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (paciente.getNidPaciente().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'NID' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (pacienteDao.verificar(paciente) == true && editar == false) {
            addMensagem("Paciente existente!", "O paciente já encontra-se registrado.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (pacienteDao.verificar(paciente) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            pacienteDao.actualizar(paciente);
            paciente.setIdPaciente(null);
            paciente.setNomePaciente("");
            paciente.setApelidoPaciente("");
            paciente.setContactoPaciente("");
            paciente.setEnderecoPaciente("");
            paciente.setNidPaciente("");
            paciente.setSexoPaciente("");
            paciente.setCodigoEmpresa("");
            paciente.setNomeEmpresa("");
                paciente.setNrSeguroPaciente("");

            listar();
            addMensagem("Actualizado!", "Dados do paciente actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                pacienteDao.salvar(paciente);
                paciente.setIdPaciente(null);
                paciente.setNomePaciente("");
                paciente.setApelidoPaciente("");
                paciente.setContactoPaciente("");
                paciente.setEnderecoPaciente("");
                paciente.setNidPaciente("");
                paciente.setSexoPaciente("");
                paciente.setCodigoEmpresa("");
                paciente.setNomeEmpresa("");
                paciente.setNrSeguroPaciente("");

                listar();

                addMensagem("Registrado!", "Paciente registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (paciente != null) {

            if (!"".equals(paciente.getNomePaciente()) || !"".equals(paciente.getApelidoPaciente())
                    || !"".equals(paciente.getNidPaciente())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                paciente.setIdPaciente(null);
                paciente.setNomePaciente("");
                paciente.setApelidoPaciente("");
                paciente.setContactoPaciente("");
                paciente.setEnderecoPaciente("");
                paciente.setNidPaciente("");
                paciente.setSexoPaciente("");
                paciente.setCodigoEmpresa("");
                paciente.setNomeEmpresa("");
                paciente.setNrSeguroPaciente("");

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                paciente.setIdPaciente(null);
                paciente.setNomePaciente("");
                paciente.setApelidoPaciente("");
                paciente.setContactoPaciente("");
                paciente.setEnderecoPaciente("");
                paciente.setNidPaciente("");
                paciente.setSexoPaciente("");
                paciente.setCodigoEmpresa("");
                paciente.setNomeEmpresa("");
                paciente.setNrSeguroPaciente("");

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            pacientes = pacienteDao.buscar();
            if (pacientes == null || pacientes.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de pacientes.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Paciente c) throws ClassNotFoundException, SQLException {
        try {
            pacienteDao.deletar(c.getIdPaciente());
            listar();
            addMensagem("Removido!", "Paciente removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Paciente c) {
        paciente = c;
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
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
                    pacientes = pacienteDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    pacientes = pacienteDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            } else {
                valor_pesquisa = "";
                listar();
            }

            if (pacientes == null || pacientes.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de paciente com este valor.", FacesMessage.SEVERITY_WARN);
            }

        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }

    public List<String> getCodigoEmpresas() {

        pesquisarCodigoEmpresas();

        return codigoEmpresas;
    }

    public void setCodigoEmpresas(List<String> codigoEmpresas) throws ErroSistema {

        this.codigoEmpresas = codigoEmpresas;
    }

    public void pesquisarCodigoEmpresas() {

        try {
            codigoEmpresas = pacienteDao.buscarCodigoEmpresas();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UploadedFile getFicheiro() {
        return ficheiro;
    }

    public void setFicheiro(UploadedFile ficheiro) {
        this.ficheiro = ficheiro;
    }
    
}
