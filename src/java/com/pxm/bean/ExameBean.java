/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.bean;

import com.pxm.dao.ExameDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Exame;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.model.UploadedFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.PartialResponseWriter;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.FileUtils;

/**
 *
 * @author cmaemo
 */
@ManagedBean
@SessionScoped
public class ExameBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Exame exame = new Exame();

    private List<Exame> exames = new ArrayList<>();
    private final ExameDAO exameDao = new ExameDAO();

    private String valor_pesquisa;

    private UploadedFile ficheiro;
    private String caminho = "E:\\temp";

    @PostConstruct
    public void init() {
        try {
            listar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adicionar() throws ClassNotFoundException, SQLException, ErroSistema {

        if (exame.getCodigoExame().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (exame.getCategoriaExame().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Categoria' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (exame.getTituloExame().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Título' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (exame.getValorExame() == 0) {
            addMensagem("Campo vazio!", "O campo 'Valor (MT)' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (exameDao.verificar(exame) == true && editar == false) {
            addMensagem("Exame existente!", "O exame já encontra-se registrado.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (exameDao.verificar(exame) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            exameDao.actualizar(exame);
            exame.setIdExame(null);
            exame.setCodigoExame("");
            exame.setCategoriaExame("");
            exame.setTituloExame("");
            exame.setValorExame(0);
            exame.setDescricaoExame("");

            listar();
            addMensagem("Actualizado!", "Dados do exame actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                exameDao.salvar(exame);
                exame.setIdExame(null);
                exame.setCodigoExame("");
                exame.setCategoriaExame("");
                exame.setTituloExame("");
                exame.setValorExame(0);
                exame.setDescricaoExame("");

                listar();

                addMensagem("Registrado!", "Exame registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (exame != null) {

            if (!"".equals(exame.getCodigoExame()) || !"".equals(exame.getCategoriaExame())
                    || !"".equals(exame.getTituloExame()) || !"".equals(exame.getValorExame())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                exame.setIdExame(null);
                exame.setCodigoExame("");
                exame.setCategoriaExame("");
                exame.setTituloExame("");
                exame.setValorExame(0);
                exame.setDescricaoExame("");

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                exame.setIdExame(null);
                exame.setCodigoExame("");
                exame.setCategoriaExame("");
                exame.setTituloExame("");
                exame.setValorExame(0);
                exame.setDescricaoExame("");

                listar();
            }
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

            Path caminhoFicheiroApagar = Paths.get("E:\\temp\\temp-exame." + ficheiroExtensao);

            Files.deleteIfExists(caminhoFicheiroApagar);

            Files.copy(input, new File(caminho, "temp-exame." + ficheiroExtensao).toPath());
            String caminhoFicheiro = caminho + "\\temp-exame." + ficheiroExtensao;

            if (exameDao.carregar(caminhoFicheiro) == true) {

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

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            exames = exameDao.buscar();
            if (exames == null || exames.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de exames.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Exame c) throws ClassNotFoundException, SQLException {
        try {
            exameDao.deletar(c.getIdExame());
            listar();
            addMensagem("Removido!", "Exame removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Exame c) {
        exame = c;
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

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
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
                    exames = exameDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    exames = exameDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            } else {
                valor_pesquisa = "";
                listar();
            }

            if (exames == null || exames.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de exame com este valor.", FacesMessage.SEVERITY_WARN);
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
