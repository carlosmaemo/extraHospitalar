package com.pxm.bean;

import com.pxm.dao.ConsultaDAO;
import com.pxm.dao.DefinicaoDAO;
import com.pxm.dao.UsuarioDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Consulta;
import com.pxm.model.Usuario;
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
public class ConsultaBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Consulta consulta = new Consulta();

    private List<Consulta> consultas = new ArrayList<>();
    private final ConsultaDAO consultaDao = new ConsultaDAO();

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

        if (ficheiro != null) {

            try (InputStream input = ficheiro.getInputstream()) {
                String nomeFicheiro = ficheiro.getFileName();

                if (nomeFicheiro != null) {

                    String[] parte = nomeFicheiro.split(Pattern.quote("."));
                    String ficheiroNome = parte[0];
                    String ficheiroExtensao = parte[1];

                    Path caminhoFicheiroApagar = Paths.get("E:\\temp\\temp-consulta." + ficheiroExtensao);

                    Files.deleteIfExists(caminhoFicheiroApagar);

                    Files.copy(input, new File(caminho, "temp-consulta." + ficheiroExtensao).toPath());
                    String caminhoFicheiro = caminho + "\\temp-consulta." + ficheiroExtensao;

                    if (consultaDao.carregar(caminhoFicheiro) == true) {

                        addMensagem("Carregado!", "Ficheiro carregado com sucesso.", FacesMessage.SEVERITY_INFO);

                        pesquisar("falso");

                    } else {
                        addMensagem("Falha!", "Ocorreu uma falha ao carregar o ficheiro.", FacesMessage.SEVERITY_INFO);
                    }
                } else {
                    addMensagem("Ficheiro em falta!", "Ficheiro não especificado.", FacesMessage.SEVERITY_WARN);
                }
            }
        } else {
            addMensagem("Ficheiro em falta!", "Ficheiro não especificado.", FacesMessage.SEVERITY_WARN);
        }

    }

    public void adicionar() throws ClassNotFoundException, SQLException, ErroSistema {

        if (consulta.getCodigoConsulta().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consulta.getCategoriaConsulta().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Categoria' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consulta.getTituloConsulta().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Título' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consultaDao.verificar(consulta) == true && editar == false) {
            addMensagem("Consulta existente!", "A consulta já encontra-se registrada.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (consultaDao.verificar(consulta) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            consultaDao.actualizar(consulta);
            consulta.setIdConsulta(null);
            consulta.setCodigoConsulta("");
            consulta.setCategoriaConsulta("");
            consulta.setTituloConsulta("");
            consulta.setDescricaoConsulta("");
            consulta.setValorConsulta(0);

            listar();
            addMensagem("Actualizado!", "Dados da consulta actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                consultaDao.salvar(consulta);
                consulta.setIdConsulta(null);
                consulta.setCodigoConsulta("");
                consulta.setCategoriaConsulta("");
                consulta.setTituloConsulta("");
                consulta.setDescricaoConsulta("");
                consulta.setValorConsulta(0);

                listar();

                addMensagem("Registrado!", "Consulta registrada com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (consulta != null) {

            if (!"".equals(consulta.getCodigoConsulta()) || !"".equals(consulta.getCategoriaConsulta())
                    || !"".equals(consulta.getTituloConsulta())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                consulta.setIdConsulta(null);
                consulta.setCodigoConsulta("");
                consulta.setCategoriaConsulta("");
                consulta.setTituloConsulta("");
                consulta.setDescricaoConsulta("");
                consulta.setValorConsulta(0);

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                consulta.setIdConsulta(null);
                consulta.setCodigoConsulta("");
                consulta.setCategoriaConsulta("");
                consulta.setTituloConsulta("");
                consulta.setDescricaoConsulta("");
                consulta.setValorConsulta(0);

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            consultas = consultaDao.buscar();
            if (consultas == null || consultas.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de consultas.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Consulta c) throws ClassNotFoundException, SQLException {
        try {
            consultaDao.deletar(c.getIdConsulta());
            listar();
            addMensagem("Removido!", "Consulta removida com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Consulta c) {
        consulta = c;
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

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
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
                    consultas = consultaDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    consultas = consultaDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            } else {
                valor_pesquisa = "";
                listar();
            }

            if (consultas == null || consultas.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de consulta com este valor.", FacesMessage.SEVERITY_WARN);
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
