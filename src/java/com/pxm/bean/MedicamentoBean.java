/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.bean;

import com.pxm.dao.MedicamentoDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.Medicamento;
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

/**
 *
 * @author cmaemo
 */
@ManagedBean
@SessionScoped
public class MedicamentoBean {

    private int countReload = 0;

    private boolean editar = false;
    private boolean modo_edicao = false;

    public Medicamento medicamento = new Medicamento();

    private List<Medicamento> medicamentos = new ArrayList<>();
    private final MedicamentoDAO medicamentoDao = new MedicamentoDAO();

    private String valor_pesquisa;
    
    @PostConstruct
    public void init() {
        try {
            listar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adicionar() throws ClassNotFoundException, SQLException, ErroSistema {

        if (medicamento.getCodigoMedicamento().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicamento.getCategoriaMedicamento().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Categoria' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicamento.getTituloMedicamento().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Título' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicamento.getValorMedicamento().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Valor (MT)' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicamentoDao.verificar(medicamento) == true && editar == false) {
            addMensagem("Medicamento existente!", "O medicamento já encontra-se registrado.", FacesMessage.SEVERITY_WARN);

            editar = false;
            modo_edicao = false;

        } else if (medicamentoDao.verificar(medicamento) == true && editar == true) {

            editar = false;
            modo_edicao = false;

            medicamentoDao.actualizar(medicamento);
            medicamento.setIdMedicamento(null);
            medicamento.setCodigoMedicamento("");
            medicamento.setCategoriaMedicamento("");
            medicamento.setTituloMedicamento("");
            medicamento.setValorMedicamento("");
            medicamento.setComposicaoMedicamento("");
            medicamento.setPosologiaMedicamento("");

            listar();
            addMensagem("Actualizado!", "Dados do medicamento actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {

                editar = false;
                modo_edicao = false;
                medicamentoDao.salvar(medicamento);
                medicamento.setIdMedicamento(null);
                medicamento.setCodigoMedicamento("");
                medicamento.setCategoriaMedicamento("");
                medicamento.setTituloMedicamento("");
                medicamento.setValorMedicamento("");
                medicamento.setComposicaoMedicamento("");
                medicamento.setPosologiaMedicamento("");

                listar();

                addMensagem("Registrado!", "Medicamento registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (medicamento != null) {

            if (!"".equals(medicamento.getCodigoMedicamento()) || !"".equals(medicamento.getCategoriaMedicamento())
                    || !"".equals(medicamento.getTituloMedicamento()) || !"".equals(medicamento.getValorMedicamento())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                editar = false;
                modo_edicao = false;

                medicamento.setIdMedicamento(null);
                medicamento.setCodigoMedicamento("");
                medicamento.setCategoriaMedicamento("");
                medicamento.setTituloMedicamento("");
                medicamento.setValorMedicamento("");
                medicamento.setComposicaoMedicamento("");
                medicamento.setPosologiaMedicamento("");

                listar();

            } else {

                editar = false;
                modo_edicao = false;

                medicamento.setIdMedicamento(null);
                medicamento.setCodigoMedicamento("");
                medicamento.setCategoriaMedicamento("");
                medicamento.setTituloMedicamento("");
                medicamento.setValorMedicamento("");
                medicamento.setComposicaoMedicamento("");
                medicamento.setPosologiaMedicamento("");

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {

        editar = false;

        try {
            medicamentos = medicamentoDao.buscar();
            if (medicamentos == null || medicamentos.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de medicamentos.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Medicamento c) throws ClassNotFoundException, SQLException {
        try {
            medicamentoDao.deletar(c.getIdMedicamento());
            listar();
            addMensagem("Removido!", "Medicamento removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Medicamento c) {
        medicamento = c;
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

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
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
                    medicamentos = medicamentoDao.buscarFiltro(valor_pesquisa, "negativo");
                } else {
                    medicamentos = medicamentoDao.buscarFiltro(valor_pesquisa, "positivo");
                }
            }
            else {
                valor_pesquisa = "";
                listar();
            }
                
            if (medicamentos == null || medicamentos.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum registro de medicamento com este valor.", FacesMessage.SEVERITY_WARN);
            }

        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }


}
