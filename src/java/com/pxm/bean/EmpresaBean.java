/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.bean;

import com.pxm.dao.ConsumivelDAO;
import com.pxm.dao.EmpresaDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Consumivel;
import com.pxm.model.Empresa;
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
public class EmpresaBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Empresa empresa = new Empresa();

    private List<Empresa> empresas = new ArrayList<>();
    private final EmpresaDAO empresaDao = new EmpresaDAO();

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

                    Path caminhoFicheiroApagar = Paths.get("E:\\temp\\temp-empresa." + ficheiroExtensao);

                    Files.deleteIfExists(caminhoFicheiroApagar);

                    Files.copy(input, new File(caminho, "temp-empresa." + ficheiroExtensao).toPath());
                    String caminhoFicheiro = caminho + "\\temp-empresa." + ficheiroExtensao;

                    if (empresaDao.carregar(caminhoFicheiro) == true) {

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

        if (empresa.getNomeEmpresa().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Nome' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (empresa.getNuitEmpresa().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'NUIT' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (empresa.getCodigoEmpresa().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (empresaDao.verificar(empresa) == true && editar == false) {
            addMensagem("Empresa existente!", "A empresa já encontra-se registrada.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (empresaDao.verificar(empresa) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            empresaDao.actualizar(empresa);
            empresa.setIdEmpresa(null);
            empresa.setNomeEmpresa("");
            empresa.setNuitEmpresa("");
            empresa.setContactoEmpresa("");
            empresa.setEmailEmpresa("");
            empresa.setEnderecoEmpresa("");
            empresa.setCodigoEmpresa("");

            listar();
            addMensagem("Actualizado!", "Dados da empresa actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                empresaDao.salvar(empresa);
                empresa.setIdEmpresa(null);
                empresa.setNomeEmpresa("");
                empresa.setNuitEmpresa("");
                empresa.setContactoEmpresa("");
                empresa.setEmailEmpresa("");
                empresa.setEnderecoEmpresa("");
                empresa.setCodigoEmpresa("");

                listar();

                addMensagem("Registrado!", "Empresa registrada com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (empresa != null) {

            if (!"".equals(empresa.getNomeEmpresa()) || !"".equals(empresa.getNuitEmpresa())
                    || !"".equals(empresa.getCodigoEmpresa())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                empresa.setIdEmpresa(null);
                empresa.setNomeEmpresa("");
                empresa.setNuitEmpresa("");
                empresa.setContactoEmpresa("");
                empresa.setEmailEmpresa("");
                empresa.setEnderecoEmpresa("");
                empresa.setCodigoEmpresa("");

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                empresa.setIdEmpresa(null);
                empresa.setNomeEmpresa("");
                empresa.setNuitEmpresa("");
                empresa.setContactoEmpresa("");
                empresa.setEmailEmpresa("");
                empresa.setEnderecoEmpresa("");
                empresa.setCodigoEmpresa("");

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            empresas = empresaDao.buscar();
            if (empresas == null || empresas.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de empresas.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Empresa c) throws ClassNotFoundException, SQLException {
        try {
            empresaDao.deletar(c.getIdEmpresa());
            listar();
            addMensagem("Removido!", "Empresa removida com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Empresa c) {
        empresa = c;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
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
                    empresas = empresaDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    empresas = empresaDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            } else {
                valor_pesquisa = "";
                listar();
            }

            if (empresas == null || empresas.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de empresa com este valor.", FacesMessage.SEVERITY_WARN);
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
