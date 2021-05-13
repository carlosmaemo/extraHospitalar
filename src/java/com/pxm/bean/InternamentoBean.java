/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.bean;

import com.pxm.dao.InternamentoDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Consumivel;
import com.pxm.model.Internamento;
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
public class InternamentoBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Internamento internamento = new Internamento();

    private List<Internamento> internamentos = new ArrayList<>();
    private final InternamentoDAO internamentoDao = new InternamentoDAO();

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
       
        if(ficheiro != null) {
            
        try (InputStream input = ficheiro.getInputstream()) {
            String nomeFicheiro = ficheiro.getFileName();

                if (nomeFicheiro != null) {
                    
            String[] parte = nomeFicheiro.split(Pattern.quote("."));
            String ficheiroNome = parte[0];
            String ficheiroExtensao = parte[1];

            Path caminhoFicheiroApagar = Paths.get("D:\\temp\\temp-internamento." + ficheiroExtensao);

            Files.deleteIfExists(caminhoFicheiroApagar);

            Files.copy(input, new File(caminho, "temp-internamento." + ficheiroExtensao).toPath());
            String caminhoFicheiro = caminho + "\\temp-internamento." + ficheiroExtensao;

            if (internamentoDao.carregar(caminhoFicheiro) == true) {

                addMensagem("Carregado!", "Ficheiro carregado com sucesso.", FacesMessage.SEVERITY_INFO);
                
                pesquisar("falso");

            } else {

                addMensagem("Falha!", "Ocorreu uma falha ao carregar o ficheiro.", FacesMessage.SEVERITY_INFO);

            }
            }
                else {
                    addMensagem("Ficheiro em falta!", "Ficheiro não especificado.", FacesMessage.SEVERITY_WARN);
                }
        }
                
        } else {
            addMensagem("Ficheiro em falta!", "Ficheiro não especificado.", FacesMessage.SEVERITY_WARN);
        }

    }
    
    public void adicionar() throws ClassNotFoundException, SQLException, ErroSistema {

        if (internamento.getCodigoInternamento().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (internamento.getTipoInternamento().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Tipo' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (internamento.getTempoInternamento().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Tempo' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (internamento.getValorInternamento() == 0) {
            addMensagem("Campo vazio!", "O campo 'Valor (MT)' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (internamentoDao.verificar(internamento) == true && editar == false) {
            addMensagem("Internamento existente!", "O internamento já encontra-se registrado.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (internamentoDao.verificar(internamento) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            internamentoDao.actualizar(internamento);
            internamento.setIdInternamento(null);
            internamento.setCodigoInternamento("");
            internamento.setTipoInternamento("");
            internamento.setTempoInternamento("");
            internamento.setValorInternamento(0);

            listar();
            addMensagem("Actualizado!", "Dados do internamento actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                internamentoDao.salvar(internamento);
                internamento.setIdInternamento(null);
                internamento.setCodigoInternamento("");
                internamento.setTipoInternamento("");
                internamento.setTempoInternamento("");
                internamento.setValorInternamento(0);

                listar();

                addMensagem("Registrado!", "Internamento registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (internamento != null) {

            if (!"".equals(internamento.getCodigoInternamento()) || !"".equals(internamento.getTipoInternamento())
                    || !"".equals(internamento.getTempoInternamento()) || !"".equals(internamento.getValorInternamento())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                internamento.setIdInternamento(null);
                internamento.setCodigoInternamento("");
                internamento.setTipoInternamento("");
                internamento.setTempoInternamento("");
                internamento.setValorInternamento(0);

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                internamento.setIdInternamento(null);
                internamento.setCodigoInternamento("");
                internamento.setTipoInternamento("");
                internamento.setTempoInternamento("");
                internamento.setValorInternamento(0);

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            internamentos = internamentoDao.buscar();
            if (internamentos == null || internamentos.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de internamentos.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Internamento c) throws ClassNotFoundException, SQLException {
        try {
            internamentoDao.deletar(c.getIdInternamento());
            listar();
            addMensagem("Removido!", "Internamento removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Internamento c) {
        internamento = c;
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

    public Internamento getInternamento() {
        return internamento;
    }

    public void setInternamento(Internamento internamento) {
        this.internamento = internamento;
    }

    public List<Internamento> getInternamentos() {
        return internamentos;
    }

    public void setInternamentos(List<Internamento> internamentos) {
        this.internamentos = internamentos;
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
                    internamentos = internamentoDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    internamentos = internamentoDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            }
            else {
                valor_pesquisa = "";
                listar();
            }
                
            if (internamentos == null || internamentos.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de internamento com este valor.", FacesMessage.SEVERITY_WARN);
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
