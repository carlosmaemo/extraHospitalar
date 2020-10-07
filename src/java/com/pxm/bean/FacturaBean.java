package com.pxm.bean;

import com.pxm.dao.FacturaDAO;
import com.pxm.exception.ErroSistema;
import com.pxm.model.ConsultaTemp;
import com.pxm.model.ConsumivelTemp;
import com.pxm.model.ExameTemp;
import com.pxm.model.Factura;
import com.pxm.model.InternamentoTemp;
import com.pxm.model.MedicamentoTemp;
import com.pxm.model.VisitaTemp;
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

/**
 *
 * @author cmaemo
 */
@ManagedBean
@SessionScoped
public class FacturaBean {

    private Factura factura = new Factura();
    private final FacturaDAO facturaDao = new FacturaDAO();
    private List<String> nids = new ArrayList<>();
    private List<String> consultas = new ArrayList<>();
    private List<String> consumiveis = new ArrayList<>();
    private List<String> internamentos = new ArrayList<>();
    private List<String> medicamentos = new ArrayList<>();
    private List<String> visitas = new ArrayList<>();
    private List<String> exames = new ArrayList<>();
    private List<String> medicos = new ArrayList<>();
    private List<String> empresas = new ArrayList<>();

    private List<Factura> facturas = new ArrayList<>();
    private List<Factura> facturasRegistosMedicos = new ArrayList<>();
    private List<Factura> facturasRegistosEmpresas = new ArrayList<>();
    private List<Factura> facturasRegistosData = new ArrayList<>();

    private ArrayList<ConsultaTemp> consultasTemp = new ArrayList<ConsultaTemp>();
    private ArrayList<ExameTemp> examesTemp = new ArrayList<ExameTemp>();
    private ArrayList<ConsumivelTemp> consumiveisTemp = new ArrayList<ConsumivelTemp>();
    private ArrayList<InternamentoTemp> internamentosTemp = new ArrayList<InternamentoTemp>();
    private ArrayList<MedicamentoTemp> medicamentosTemp = new ArrayList<MedicamentoTemp>();
    private ArrayList<VisitaTemp> visitasTemp = new ArrayList<VisitaTemp>();

    private double consultaValor, exameValor, consumivelValor, internamentoValor, medicamentoValor, visitaValor;
    private String consulta, consultaData, consultaHora, consultaMedico, consultaCodigo, consultaCategoria, consultaTitulo, consultaDescricao;
    private String exame, exameData, exameHora, exameCategoria, exameCodigo, exameTitulo, exameDescricao;
    private String consumivel, consumivelData, consumivelHora, consumivelCategoria, consumivelCodigo, consumivelTitulo, consumivelComposicao, consumivelPosologia;
    private String internamento, internamentoData, internamentoHora, internamentoTempo, internamentoCodigo, internamentoTipo;
    private String medicamento, medicamentoData, medicamentoHora, medicamentoCategoria, medicamentoCodigo, medicamentoTitulo, medicamentoComposicao, medicamentoPosologia;
    private String visita, visitaData, visitaHora, visitaTempo, visitaCodigo, visitaTipo;

    @PostConstruct
    public void init() {
        pesquisarNids();
        pesquisarConsultas();
        pesquisarExames();
        pesquisarConsumiveis();
        pesquisarInternamentos();
        pesquisarMedicamentos();
        pesquisarVisitas();
        pesquisarMedicos();

        try {
            listar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<ConsultaTemp> getConsultasTemp() {
        return consultasTemp;
    }

    public void setConsultasTemp(ArrayList<ConsultaTemp> consultasTemp) {
        this.consultasTemp = consultasTemp;
    }

    public String adicionarConsulta() {
        if (consulta.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Consulta/Procedimento' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consultaData.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Data' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consultaHora.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Hora' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consultaMedico.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Médico' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            ConsultaTemp consultaTemp = new ConsultaTemp(this.consulta, consultaData, consultaHora, consultaMedico, consultaValor, consultaCodigo, consultaCategoria, consultaTitulo, consultaDescricao);
            consultasTemp.add(consultaTemp);
        }
        return null;
    }

    public String adicionarConsumivel() {
        if (consumivel.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Consumível' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consumivelData.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Data' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consumivelHora.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Hora' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            ConsumivelTemp consumivelTemp = new ConsumivelTemp(this.consumivel, consumivelData, consumivelHora, consumivelCategoria, consumivelValor, consumivelCodigo, consumivelTitulo, consumivelComposicao, consumivelPosologia);
            consumiveisTemp.add(consumivelTemp);
        }
        return null;
    }

    public String adicionarInternamento() {
        if (internamento.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Internamento' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (internamentoData.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Data' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (internamentoHora.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Hora' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            InternamentoTemp internamentoTemp = new InternamentoTemp(this.internamento, internamentoData, internamentoHora, internamentoTempo, internamentoValor, internamentoCodigo, internamentoTipo);
            internamentosTemp.add(internamentoTemp);
        }
        return null;
    }

    public String adicionarMedicamento() {
        if (medicamento.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Medicamento' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicamentoData.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Data' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicamentoHora.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Hora' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            MedicamentoTemp medicamentoTemp = new MedicamentoTemp(this.medicamento, medicamentoData, medicamentoHora, medicamentoCategoria, medicamentoValor, medicamentoCodigo, medicamentoTitulo, medicamentoComposicao, medicamentoPosologia);
            medicamentosTemp.add(medicamentoTemp);
        }
        return null;
    }

    public String adicionarVisita() {
        if (visita.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Visita' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (visitaData.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Data' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (visitaHora.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Hora' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            VisitaTemp visitaTemp = new VisitaTemp(this.visita, visitaData, visitaHora, visitaTempo, visitaValor, visitaCodigo, visitaTipo);
            visitasTemp.add(visitaTemp);
        }
        return null;
    }

    public String adicionarExame() {
        if (exame.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Exame' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (exameData.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Data' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (exameHora.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Hora' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {

            ExameTemp exameTemp = new ExameTemp(this.exame, exameData, exameHora, exameCategoria, exameValor, exameCodigo, exameTitulo, exameDescricao);

            examesTemp.add(exameTemp);

        }

        return null;
    }

    public String removerConsulta(ConsultaTemp consulta) {
        consultasTemp.remove(consulta);
        return null;
    }

    public String removerConsumivel(ConsumivelTemp consumivel) {
        consumiveisTemp.remove(consumivel);
        return null;
    }

    public String removerInternamento(InternamentoTemp internamento) {
        internamentosTemp.remove(internamento);
        return null;
    }

    public String removerMedicamento(MedicamentoTemp medicamento) {
        medicamentosTemp.remove(medicamento);
        return null;
    }

    public String removerVisita(VisitaTemp visita) {
        visitasTemp.remove(visita);
        return null;
    }

    public String removerExame(ExameTemp exame) {
        examesTemp.remove(exame);
        return null;
    }

    public List<String> getNids() {
        pesquisarNids();
        return nids;
    }

    public void setNids(List<String> nids) {
        this.nids = nids;
    }

    public String getConsultaData() {
        return consultaData;
    }

    public void setConsultaData(String consultaData) {
        this.consultaData = consultaData;
    }

    public String getConsultaHora() {
        return consultaHora;
    }

    public void setConsultaHora(String consultaHora) {
        this.consultaHora = consultaHora;
    }

    public String getConsultaMedico() throws ErroSistema {

        return consultaMedico;
    }

    public void setConsultaMedico(String consultaMedico) {
        this.consultaMedico = consultaMedico;
    }

    public List<String> getConsultas() {
        pesquisarConsultas();
        return consultas;
    }

    public void setConsultas(List<String> consultas) {
        this.consultas = consultas;
    }

    public List<String> getConsumiveis() {
        pesquisarConsumiveis();
        return consumiveis;
    }

    public void setConsumiveis(List<String> consumiveis) {
        this.consumiveis = consumiveis;
    }

    public List<String> getInternamentos() {
        pesquisarInternamentos();
        return internamentos;
    }

    public void setInternamentos(List<String> internamentos) {
        this.internamentos = internamentos;
    }

    public List<String> getMedicamentos() {
        pesquisarMedicamentos();
        return medicamentos;
    }

    public void setMedicamentos(List<String> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public List<String> getVisitas() {
        pesquisarVisitas();
        return visitas;
    }

    public void setVisitas(List<String> visitas) {
        this.visitas = visitas;
    }

    public List<String> getExames() {
        pesquisarExames();
        return exames;
    }

    public void setExames(List<String> exames) {
        this.exames = exames;
    }

    public List<String> getMedicos() {
        pesquisarMedicos();
        return medicos;
    }

    public List<String> getEmpresas() {
        pesquisarEmpresas();
        return empresas;
    }

    public void setEmpresas(List<String> empresas) {
        this.empresas = empresas;
    }

    public void setMedicos(List<String> medicos) {
        this.medicos = medicos;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getExame() {
        return exame;
    }

    public void setExame(String exame) {
        this.exame = exame;
    }

    public String getExameData() {
        return exameData;
    }

    public void setExameData(String exameData) {
        this.exameData = exameData;
    }

    public String getExameHora() {
        return exameHora;
    }

    public void setExameHora(String exameHora) {
        this.exameHora = exameHora;
    }

    public String getExameCategoria() {
        return exameCategoria;
    }

    public void setExameCategoria(String exameCategoria) {
        this.exameCategoria = exameCategoria;
    }

    public double getExameValor() {
        return exameValor;
    }

    public void setExameValor(double exameValor) {
        this.exameValor = exameValor;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public double getConsultaValor() {
        return consultaValor;
    }

    public void setConsultaValor(double consultaValor) {
        this.consultaValor = consultaValor;
    }

    public String getConsultaCodigo() {
        return consultaCodigo;
    }

    public void setConsultaCodigo(String consultaCodigo) {
        this.consultaCodigo = consultaCodigo;
    }

    public String getConsultaCategoria() {
        return consultaCategoria;
    }

    public void setConsultaCategoria(String consultaCategoria) {
        this.consultaCategoria = consultaCategoria;
    }

    public String getConsultaTitulo() {
        return consultaTitulo;
    }

    public void setConsultaTitulo(String consultaTitulo) {
        this.consultaTitulo = consultaTitulo;
    }

    public String getConsultaDescricao() {
        return consultaDescricao;
    }

    public void setConsultaDescricao(String consultaDescricao) {
        this.consultaDescricao = consultaDescricao;
    }

    public ArrayList<ConsumivelTemp> getConsumiveisTemp() {
        return consumiveisTemp;
    }

    public void setConsumiveisTemp(ArrayList<ConsumivelTemp> consumiveisTemp) {
        this.consumiveisTemp = consumiveisTemp;
    }

    public ArrayList<InternamentoTemp> getInternamentosTemp() {
        return internamentosTemp;
    }

    public void setInternamentosTemp(ArrayList<InternamentoTemp> internamentosTemp) {
        this.internamentosTemp = internamentosTemp;
    }

    public ArrayList<MedicamentoTemp> getMedicamentosTemp() {
        return medicamentosTemp;
    }

    public void setMedicamentosTemp(ArrayList<MedicamentoTemp> medicamentosTemp) {
        this.medicamentosTemp = medicamentosTemp;
    }

    public ArrayList<VisitaTemp> getVisitasTemp() {
        return visitasTemp;
    }

    public void setVisitasTemp(ArrayList<VisitaTemp> visitasTemp) {
        this.visitasTemp = visitasTemp;
    }

    public String getConsumivel() {
        return consumivel;
    }

    public void setConsumivel(String consumivel) {
        this.consumivel = consumivel;
    }

    public String getConsumivelData() {
        return consumivelData;
    }

    public void setConsumivelData(String consumivelData) {
        this.consumivelData = consumivelData;
    }

    public String getConsumivelHora() {
        return consumivelHora;
    }

    public void setConsumivelHora(String consumivelHora) {
        this.consumivelHora = consumivelHora;
    }

    public String getConsumivelCategoria() {
        return consumivelCategoria;
    }

    public void setConsumivelCategoria(String consumivelCategoria) {
        this.consumivelCategoria = consumivelCategoria;
    }

    public double getConsumivelValor() {
        return consumivelValor;
    }

    public void setConsumivelValor(double consumivelValor) {
        this.consumivelValor = consumivelValor;
    }

    public String getInternamento() {
        return internamento;
    }

    public void setInternamento(String internamento) {
        this.internamento = internamento;
    }

    public String getInternamentoData() {
        return internamentoData;
    }

    public void setInternamentoData(String internamentoData) {
        this.internamentoData = internamentoData;
    }

    public String getInternamentoHora() {
        return internamentoHora;
    }

    public void setInternamentoHora(String internamentoHora) {
        this.internamentoHora = internamentoHora;
    }

    public String getInternamentoTempo() {
        return internamentoTempo;
    }

    public void setInternamentoTempo(String internamentoTempo) {
        this.internamentoTempo = internamentoTempo;
    }

    public double getInternamentoValor() {
        return internamentoValor;
    }

    public void setInternamentoValor(double internamentoValor) {
        this.internamentoValor = internamentoValor;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getMedicamentoData() {
        return medicamentoData;
    }

    public void setMedicamentoData(String medicamentoData) {
        this.medicamentoData = medicamentoData;
    }

    public String getMedicamentoHora() {
        return medicamentoHora;
    }

    public void setMedicamentoHora(String medicamentoHora) {
        this.medicamentoHora = medicamentoHora;
    }

    public String getMedicamentoCategoria() {
        return medicamentoCategoria;
    }

    public void setMedicamentoCategoria(String medicamentoCategoria) {
        this.medicamentoCategoria = medicamentoCategoria;
    }

    public double getMedicamentoValor() {
        return medicamentoValor;
    }

    public void setMedicamentoValor(double medicamentoValor) {
        this.medicamentoValor = medicamentoValor;
    }

    public String getVisita() {
        return visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }

    public String getVisitaData() {
        return visitaData;
    }

    public void setVisitaData(String visitaData) {
        this.visitaData = visitaData;
    }

    public String getVisitaHora() {
        return visitaHora;
    }

    public void setVisitaHora(String visitaHora) {
        this.visitaHora = visitaHora;
    }

    public String getVisitaTempo() {
        return visitaTempo;
    }

    public void setVisitaTempo(String visitaTempo) {
        this.visitaTempo = visitaTempo;
    }

    public double getVisitaValor() {
        return visitaValor;
    }

    public void setVisitaValor(double visitaValor) {
        this.visitaValor = visitaValor;
    }

    public ArrayList<ExameTemp> getExamesTemp() {
        return examesTemp;
    }

    public void setExamesTemp(ArrayList<ExameTemp> examesTemp) {
        this.examesTemp = examesTemp;
    }

    public void addMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public void pesquisarNids() {

        try {
            nids = facturaDao.buscarNid();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarConsultas() {
        try {
            consultas = facturaDao.buscarConsulta();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarConsumiveis() {
        try {
            consumiveis = facturaDao.buscarConsumivel();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarInternamentos() {
        try {
            internamentos = facturaDao.buscarInternamento();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarMedicamentos() {
        try {
            medicamentos = facturaDao.buscarMedicamento();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarVisitas() {
        try {
            visitas = facturaDao.buscarVisita();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarExames() {

        try {
            exames = facturaDao.buscarExame();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarMedicos() {

        try {
            medicos = facturaDao.buscarMedico();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarEmpresas() {

        try {
            empresas = facturaDao.buscarEmpresa();
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void confirmar(String nid, int idUsuario) {
        try {
            factura = facturaDao.confirmar(nid, idUsuario);
        } catch (ErroSistema ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<Factura> getFacturasRegistosMedicos() {
        return facturasRegistosMedicos;
    }

    public void setFacturasRegistosMedicos(List<Factura> facturasRegistosMedicos) {
        this.facturasRegistosMedicos = facturasRegistosMedicos;
    }

    public List<Factura> getFacturasRegistosEmpresas() {
        return facturasRegistosEmpresas;
    }

    public void setFacturasRegistosEmpresas(List<Factura> facturasRegistosEmpresas) {
        this.facturasRegistosEmpresas = facturasRegistosEmpresas;
    }

    public List<Factura> getFacturasRegistosData() {
        return facturasRegistosData;
    }

    public void setFacturasRegistosData(List<Factura> facturasRegistosData) {
        this.facturasRegistosData = facturasRegistosData;
    }

    // REGISTRO DE FACTURA
    public void salvar(Factura factura, int idUsuario) throws ErroSistema {

        String[] parte = consultaMedico.split("_/");
        factura.setIdMedico(facturaDao.verificarIDMedico(parte[1]));
        factura.setIdUsuario(idUsuario);

        if (factura.getNomePaciente() == null || factura.getNidPaciente() == null) {
            addMensagem("Falha ao gravar!", "Paciente não identificado.", FacesMessage.SEVERITY_WARN);
        } else if (consultasTemp.isEmpty()) {
            addMensagem("Falha ao gravar!", "Consulta não especificada.", FacesMessage.SEVERITY_WARN);
        } else {

            facturaDao.salvar(factura, consultasTemp, consumiveisTemp, examesTemp, internamentosTemp, medicamentosTemp, visitasTemp);

            consultasTemp.clear();
            examesTemp.clear();
            internamentosTemp.clear();
            visitasTemp.clear();
            medicamentosTemp.clear();
            consumiveisTemp.clear();

            factura.setIdFactura(null);
            factura.setIdPaciente(null);
            factura.setIdMedico(null);
            factura.setIdUsuario(null);

            factura.setData("");
            factura.setNomePaciente("");
            factura.setApelidoPaciente("");
            factura.setContactoPaciente("");
            factura.setEnderecoPaciente("");
            factura.setNidPaciente("");
            factura.setSexoPaciente("");
            factura.setCodigoEmpresa("");
            factura.setNomeEmpresa("");

            consulta = "";
            consultaData = "";
            consultaHora = "";
            consultaMedico = "";

            exame = "";
            exameData = "";
            exameHora = "";

            internamento = "";
            internamentoData = "";
            internamentoHora = "";

            visita = "";
            visitaData = "";
            visitaHora = "";

            medicamento = "";
            medicamentoData = "";
            medicamentoHora = "";

            consumivel = "";
            consumivelData = "";
            consumivelHora = "";

            pesquisarNids();
            pesquisarConsultas();
            pesquisarExames();
            pesquisarConsumiveis();
            pesquisarInternamentos();
            pesquisarMedicamentos();
            pesquisarVisitas();
            pesquisarMedicos();

            try {
                listar();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            addMensagem("Gravado!", "Factura gravada com sucesso.", FacesMessage.SEVERITY_INFO);

        }
    }

    public void cancelar() {

        consultasTemp.clear();
        examesTemp.clear();
        internamentosTemp.clear();
        visitasTemp.clear();
        medicamentosTemp.clear();
        consumiveisTemp.clear();

        factura.setIdFactura(null);
        factura.setIdPaciente(null);
        factura.setIdMedico(null);
        factura.setIdUsuario(null);

        factura.setData("");
        factura.setNomePaciente("");
        factura.setApelidoPaciente("");
        factura.setContactoPaciente("");
        factura.setEnderecoPaciente("");
        factura.setNidPaciente("");
        factura.setSexoPaciente("");
        factura.setCodigoEmpresa("");
        factura.setNomeEmpresa("");

        consulta = "";
        consultaData = "";
        consultaHora = "";
        consultaMedico = "";

        exame = "";
        exameData = "";
        exameHora = "";

        internamento = "";
        internamentoData = "";
        internamentoHora = "";

        visita = "";
        visitaData = "";
        visitaHora = "";

        medicamento = "";
        medicamentoData = "";
        medicamentoHora = "";

        consumivel = "";
        consumivelData = "";
        consumivelHora = "";

        pesquisarNids();
        pesquisarConsultas();
        pesquisarExames();
        pesquisarConsumiveis();
        pesquisarInternamentos();
        pesquisarMedicamentos();
        pesquisarVisitas();
        pesquisarMedicos();

        try {
            listar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        addMensagem("Cancelado!", "Factura cancelada com sucesso.", FacesMessage.SEVERITY_INFO);
    }

    public void listar() throws ClassNotFoundException, SQLException {
        try {
            facturas = facturaDao.buscar();
            if (facturas == null || facturas.isEmpty()) {
                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro de facturas.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Factura c) throws ClassNotFoundException, SQLException {
        try {
            facturaDao.deletar(c.getIdFactura());
            listar();
            addMensagem("Removido!", "Factura removida com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void pesquisarFiltro() throws ClassNotFoundException, SQLException {
        try {

            if (factura.getNidFiltro().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == true) {

                facturas = facturaDao.buscarFiltro(factura, "nid", factura.getNidFiltro());

            } else if (factura.getNidFiltro().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == true) {

                facturas = facturaDao.buscarFiltro(factura, "nid", factura.getNidFiltro());

            } else if (factura.getNidFiltro().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == false) {

                facturas = facturaDao.buscarFiltro(factura, "nid", factura.getNidFiltro());

            } else if (factura.getNidFiltro().isEmpty() == true
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == false) {

                facturas = facturaDao.buscarFiltro(factura, "data", factura.getNidFiltro());

            } else if (factura.getNidFiltro().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == false) {

                facturas = facturaDao.buscarFiltro(factura, "nid_data", factura.getNidFiltro());

            } else if (factura.getNidFiltro().isEmpty() == true
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == true) {

                facturas = new ArrayList();

            } else {

                facturas = new ArrayList();
            }

            if (facturas == null || facturas.isEmpty()) {
                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro de facturas.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }

    public void pesquisarFiltroRegisto() throws ClassNotFoundException, SQLException {

        try {

            if (factura.getOrmm().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == true) {

                facturasRegistosMedicos = facturaDao.buscarFiltroRegisto(factura, "ormm", factura.getOrmm());

            } else if (factura.getOrmm().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == true) {

                facturasRegistosMedicos = facturaDao.buscarFiltroRegisto(factura, "ormm", factura.getOrmm());

            } else if (factura.getOrmm().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == false) {

                facturasRegistosMedicos = facturaDao.buscarFiltroRegisto(factura, "ormm", factura.getOrmm());

            } else if (factura.getOrmm().isEmpty() == true
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == false) {

                facturasRegistosMedicos = facturaDao.buscarFiltroRegisto(factura, "data", factura.getOrmm());

            } else if (factura.getOrmm().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == false) {

                facturasRegistosMedicos = facturaDao.buscarFiltroRegisto(factura, "ormm_data", factura.getOrmm());

            } else if (factura.getOrmm().isEmpty() == true
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == true) {

                facturasRegistosMedicos = new ArrayList();

            } else {

                facturasRegistosMedicos = new ArrayList();
            }

            if (facturasRegistosMedicos == null || facturasRegistosMedicos.isEmpty()) {
                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }

    public void pesquisarFiltroRegistoEmpresa() throws ClassNotFoundException, SQLException {

        try {

            if (factura.getLstEmpresa().isEmpty() == false
                    && factura.getDataInicialEmpresa().isEmpty() == true
                    && factura.getDataFinalEmpresa().isEmpty() == true) {

                facturasRegistosEmpresas = facturaDao.buscarFiltroRegistoEmpresa(factura, "codigo", factura.getLstEmpresa());

            } else if (factura.getLstEmpresa().isEmpty() == false
                    && factura.getDataInicialEmpresa().isEmpty() == false
                    && factura.getDataFinalEmpresa().isEmpty() == true) {

                facturasRegistosEmpresas = facturaDao.buscarFiltroRegistoEmpresa(factura, "codigo", factura.getLstEmpresa());

            } else if (factura.getLstEmpresa().isEmpty() == false
                    && factura.getDataInicialEmpresa().isEmpty() == true
                    && factura.getDataFinalEmpresa().isEmpty() == false) {

                facturasRegistosEmpresas = facturaDao.buscarFiltroRegistoEmpresa(factura, "codigo", factura.getLstEmpresa());

            } else if (factura.getLstEmpresa().isEmpty() == true
                    && factura.getDataInicialEmpresa().isEmpty() == false
                    && factura.getDataFinalEmpresa().isEmpty() == false) {

                facturasRegistosEmpresas = facturaDao.buscarFiltroRegistoEmpresa(factura, "data", factura.getLstEmpresa());

            } else if (factura.getLstEmpresa().isEmpty() == false
                    && factura.getDataInicialEmpresa().isEmpty() == false
                    && factura.getDataFinalEmpresa().isEmpty() == false) {

                facturasRegistosEmpresas = facturaDao.buscarFiltroRegistoEmpresa(factura, "codigo_data", factura.getLstEmpresa());

            } else if (factura.getLstEmpresa().isEmpty() == true
                    && factura.getDataInicialEmpresa().isEmpty() == true
                    && factura.getDataFinalEmpresa().isEmpty() == true) {

                facturasRegistosEmpresas = new ArrayList();

            } else {

                facturasRegistosEmpresas = new ArrayList();
            }

            if (facturasRegistosEmpresas == null || facturasRegistosEmpresas.isEmpty()) {
                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }

    public void pesquisarFiltroRegistoData() throws ClassNotFoundException, SQLException {

        try {

            if (factura.getDataInicialData().isEmpty() == false
                    && factura.getDataFinalData().isEmpty() == false) {

                facturasRegistosData = facturaDao.buscarFiltroRegistoData(factura);

            } else {

                facturasRegistosData = new ArrayList();

            }

            if (facturasRegistosData == null || facturasRegistosData.isEmpty()) {
                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }

    public void cancelarFiltro() throws ClassNotFoundException, SQLException {

        if (factura != null) {

            if (!"".equals(factura.getNidFiltro()) || !"".equals(factura.getDataInicial()) || !"".equals(factura.getDataFinal())) {

                factura.setNidFiltro("");
                factura.setDataInicial("");
                factura.setDataFinal("");

                listar();
                pesquisarNids();

            } else {

                factura.setNidFiltro("");
                factura.setDataInicial("");
                factura.setDataFinal("");

                listar();
                pesquisarNids();
            }
        }
    }

    public void cancelarFiltroRegisto() throws ClassNotFoundException, SQLException {
        facturasRegistosMedicos = new ArrayList();
        factura.setDataInicial("");
        factura.setDataFinal("");
        pesquisarMedicos();
    }

    public void cancelarFiltroRegistoEmpresa() throws ClassNotFoundException, SQLException {
        facturasRegistosEmpresas = new ArrayList();
        factura.setDataInicialEmpresa("");
        factura.setDataFinalEmpresa("");
        pesquisarEmpresas();
    }

    public void cancelarFiltroRegistoData() throws ClassNotFoundException, SQLException {
        facturasRegistosData = new ArrayList();
        factura.setDataInicialData("");
        factura.setDataFinalData("");
    }

    public void print(Factura factura, int idUsuario) throws ClassNotFoundException, SQLException {

        try {
            facturaDao.printFactura(factura, idUsuario);
        } catch (ErroSistema | IOException ex) {
            Logger.getLogger(FacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printRegistoData() throws ClassNotFoundException, SQLException {
        try {
            facturaDao.printFacturaRegistoData(factura);
        } catch (ErroSistema | IOException ex) {
            Logger.getLogger(FacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printRegistoMedico() throws ClassNotFoundException, SQLException {
        try {

            if (factura.getOrmm().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == true) {

                if (facturasRegistosMedicos == null || facturasRegistosMedicos.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoMedico(factura);
                }

            } else if (factura.getOrmm().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == true) {

                if (facturasRegistosMedicos == null || facturasRegistosMedicos.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoMedico(factura);
                }

            } else if (factura.getOrmm().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == false) {

                if (facturasRegistosMedicos == null || facturasRegistosMedicos.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoMedico(factura);
                }

            } else if (factura.getOrmm().isEmpty() == true
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == false) {

                if (facturasRegistosMedicos == null || facturasRegistosMedicos.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoMedicoFiltro(factura);
                }

            } else if (factura.getOrmm().isEmpty() == false
                    && factura.getDataInicial().isEmpty() == false
                    && factura.getDataFinal().isEmpty() == false) {

                if (facturasRegistosMedicos == null || facturasRegistosMedicos.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoMedicoFiltro(factura);
                }

            } else if (factura.getOrmm().isEmpty() == true
                    && factura.getDataInicial().isEmpty() == true
                    && factura.getDataFinal().isEmpty() == true) {

                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);

            } else {

                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
            }

        } catch (ErroSistema | IOException ex) {
            Logger.getLogger(FacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printRegistoEmpresa() throws ClassNotFoundException, SQLException {
        try {

            if (factura.getLstEmpresa().isEmpty() == false
                    && factura.getDataInicialEmpresa().isEmpty() == true
                    && factura.getDataFinalEmpresa().isEmpty() == true) {

                if (facturasRegistosEmpresas == null || facturasRegistosEmpresas.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoEmpresa(factura);
                }

            } else if (factura.getLstEmpresa().isEmpty() == false
                    && factura.getDataInicialEmpresa().isEmpty() == false
                    && factura.getDataFinalEmpresa().isEmpty() == true) {

                if (facturasRegistosEmpresas == null || facturasRegistosEmpresas.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoEmpresa(factura);
                }

            } else if (factura.getLstEmpresa().isEmpty() == false
                    && factura.getDataInicialEmpresa().isEmpty() == true
                    && factura.getDataFinalEmpresa().isEmpty() == false) {

                if (facturasRegistosEmpresas == null || facturasRegistosEmpresas.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoEmpresa(factura);
                }

            } else if (factura.getLstEmpresa().isEmpty() == true
                    && factura.getDataInicialEmpresa().isEmpty() == false
                    && factura.getDataFinalEmpresa().isEmpty() == false) {

                if (facturasRegistosEmpresas == null || facturasRegistosEmpresas.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoEmpresaFiltro(factura);
                }

            } else if (factura.getLstEmpresa().isEmpty() == false
                    && factura.getDataInicialEmpresa().isEmpty() == false
                    && factura.getDataFinalEmpresa().isEmpty() == false) {

                if (facturasRegistosEmpresas == null || facturasRegistosEmpresas.isEmpty()) {
                    addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
                } else {
                    facturaDao.printFacturaRegistoEmpresaFiltro(factura);
                }

            } else if (factura.getLstEmpresa().isEmpty() == true
                    && factura.getDataInicialEmpresa().isEmpty() == true
                    && factura.getDataFinalEmpresa().isEmpty() == true) {

                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);

            } else {

                addMensagem("Nenhum registro encontrado!", "Não foi encontrado nehnum registro.", FacesMessage.SEVERITY_WARN);
            }

        } catch (ErroSistema | IOException ex) {
            Logger.getLogger(FacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
