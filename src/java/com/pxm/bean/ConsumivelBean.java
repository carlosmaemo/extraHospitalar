/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.bean;

import com.pxm.dao.ConsultaDAO;
import com.pxm.dao.ConsumivelDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Consulta;
import com.pxm.model.Consumivel;
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
public class ConsumivelBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Consumivel consumivel = new Consumivel();

    private List<Consumivel> consumiveis = new ArrayList<>();
    private final ConsumivelDAO consumivelDao = new ConsumivelDAO();

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

    public void adicionar() throws ClassNotFoundException, SQLException, ErroSistema {

        if (consumivel.getCodigoConsumivel().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consumivel.getCategoriaConsumivel().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Categoria' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consumivel.getTituloConsumivel().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Título' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consumivel.getValorConsumivel() == 0) {
            addMensagem("Campo vazio!", "O campo 'Valor (MT)' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consumivelDao.verificar(consumivel) == true && editar == false) {
            addMensagem("Consumível existente!", "O consumível já encontra-se registrado.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (consumivelDao.verificar(consumivel) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            consumivelDao.actualizar(consumivel);
            consumivel.setIdConsumivel(null);
            consumivel.setCodigoConsumivel("");
            consumivel.setCategoriaConsumivel("");
            consumivel.setTituloConsumivel("");
            consumivel.setValorConsumivel(0);
            consumivel.setComposicaoConsumivel("");
            consumivel.setPosologiaConsumivel("");

            listar();
            addMensagem("Actualizado!", "Dados do consumível actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                consumivelDao.salvar(consumivel);
                consumivel.setIdConsumivel(null);
                consumivel.setCodigoConsumivel("");
                consumivel.setCategoriaConsumivel("");
                consumivel.setTituloConsumivel("");
                consumivel.setValorConsumivel(0);
                consumivel.setComposicaoConsumivel("");
                consumivel.setPosologiaConsumivel("");

                listar();

                addMensagem("Registrado!", "Consumível registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
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

            Path caminhoFicheiroApagar = Paths.get("D:\\temp\\temp-consumivel." + ficheiroExtensao);

            Files.deleteIfExists(caminhoFicheiroApagar);

            Files.copy(input, new File(caminho, "temp-consumivel." + ficheiroExtensao).toPath());
            String caminhoFicheiro = caminho + "\\temp-consumivel." + ficheiroExtensao;

            if (consumivelDao.carregar(caminhoFicheiro) == true) {

                addMensagem("Carregado!", "Arquivo carregado com sucesso.", FacesMessage.SEVERITY_INFO);
                
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
    
    public void cancelar() throws ClassNotFoundException, SQLException {

        if (consumivel != null) {

            if (!"".equals(consumivel.getCodigoConsumivel()) || !"".equals(consumivel.getCategoriaConsumivel())
                    || !"".equals(consumivel.getTituloConsumivel()) || !"".equals(consumivel.getValorConsumivel())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                consumivel.setIdConsumivel(null);
                consumivel.setCodigoConsumivel("");
                consumivel.setCategoriaConsumivel("");
                consumivel.setTituloConsumivel("");
                consumivel.setValorConsumivel(0);
                consumivel.setComposicaoConsumivel("");
                consumivel.setPosologiaConsumivel("");

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                consumivel.setIdConsumivel(null);
                consumivel.setCodigoConsumivel("");
                consumivel.setCategoriaConsumivel("");
                consumivel.setTituloConsumivel("");
                consumivel.setValorConsumivel(0);
                consumivel.setComposicaoConsumivel("");
                consumivel.setPosologiaConsumivel("");

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            consumiveis = consumivelDao.buscar();
            if (consumiveis == null || consumiveis.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de consumíveis.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Consumivel c) throws ClassNotFoundException, SQLException {
        try {
            consumivelDao.deletar(c.getIdConsumivel());
            listar();
            addMensagem("Removido!", "Consumível removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Consumivel c) {
        consumivel = c;
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

    public Consumivel getConsumivel() {
        return consumivel;
    }

    public void setConsumivel(Consumivel consumivel) {
        this.consumivel = consumivel;
    }

    public List<Consumivel> getConsumiveis() {
        return consumiveis;
    }

    public void setConsumiveis(List<Consumivel> consumiveis) {
        this.consumiveis = consumiveis;
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
                    consumiveis = consumivelDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    consumiveis = consumivelDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            }
            else {
                valor_pesquisa = "";
                listar();
            }
                
            if (consumiveis == null || consumiveis.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de consumível com este valor.", FacesMessage.SEVERITY_WARN);
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
