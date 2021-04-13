/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.bean;

import com.pxm.dao.VisitaDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Visita;
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
public class VisitaBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Visita visita = new Visita();

    private List<Visita> visitas = new ArrayList<>();
    private final VisitaDAO visitaDao = new VisitaDAO();

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

    public void carregar() throws ClassNotFoundException, SQLException, IOException, ErroSistema {
       
        if(ficheiro != null) {
            
        try (InputStream input = ficheiro.getInputstream()) {
            String nomeFicheiro = ficheiro.getFileName();

                if (nomeFicheiro != null) {
                    
            String[] parte = nomeFicheiro.split(Pattern.quote("."));
            String ficheiroNome = parte[0];
            String ficheiroExtensao = parte[1];

            Path caminhoFicheiroApagar = Paths.get("E:\\temp\\temp-visita." + ficheiroExtensao);

            Files.deleteIfExists(caminhoFicheiroApagar);

            Files.copy(input, new File(caminho, "temp-visita." + ficheiroExtensao).toPath());
            String caminhoFicheiro = caminho + "\\temp-visita." + ficheiroExtensao;

            if (visitaDao.carregar(caminhoFicheiro) == true) {

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

        if (visita.getCodigoVisita().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (visita.getTipoVisita().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Tipo' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (visita.getTempoVisita().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Tempo' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (visita.getValorVisita() == 0) {
            addMensagem("Campo vazio!", "O campo 'Valor (MT)' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (visitaDao.verificar(visita) == true && editar == false) {
            addMensagem("Visita existente!", "A visita já encontra-se registrado.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (visitaDao.verificar(visita) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            visitaDao.actualizar(visita);
            visita.setIdVisita(null);
            visita.setCodigoVisita("");
            visita.setTipoVisita("");
            visita.setTempoVisita("");
            visita.setValorVisita(0);

            listar();
            addMensagem("Actualizado!", "Dados da visita actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                visitaDao.salvar(visita);
                visita.setIdVisita(null);
                visita.setCodigoVisita("");
                visita.setTipoVisita("");
                visita.setTempoVisita("");
                visita.setValorVisita(0);

                listar();

                addMensagem("Registrado!", "Visita registrada com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (visita != null) {

            if (!"".equals(visita.getCodigoVisita()) || !"".equals(visita.getTipoVisita())
                    || !"".equals(visita.getTempoVisita()) || !"".equals(visita.getValorVisita())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                visita.setIdVisita(null);
                visita.setCodigoVisita("");
                visita.setTipoVisita("");
                visita.setTempoVisita("");
                visita.setValorVisita(0);

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                visita.setIdVisita(null);
                visita.setCodigoVisita("");
                visita.setTipoVisita("");
                visita.setTempoVisita("");
                visita.setValorVisita(0);

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            visitas = visitaDao.buscar();
            if (visitas == null || visitas.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de visitas.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Visita c) throws ClassNotFoundException, SQLException {
        try {
            visitaDao.deletar(c.getIdVisita());
            listar();
            addMensagem("Removido!", "Visita removida com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Visita c) {
        visita = c;
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

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }

    public List<Visita> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<Visita> visitas) {
        this.visitas = visitas;
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
                    visitas = visitaDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    visitas = visitaDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            }
            else {
                valor_pesquisa = "";
                listar();
            }
                
            if (visitas == null || visitas.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de visita com este valor.", FacesMessage.SEVERITY_WARN);
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
