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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private double consultaValor, consultaValorTotal, exameValor, exameValorTotal, consumivelValor, consumivelValorTotal, internamentoValor, internamentoValorTotal, medicamentoValor, medicamentoValorTotal, visitaValor, visitaValorTotal;
    private int consultaQuantidade = 1;
    private int exameQuantidade = 1;
    private int consumivelQuantidade = 1;
    private int internamentoQuantidade = 1;
    private int medicamentoQuantidade = 1;
    private int visitaQuantidade = 1;
    private String consultaData, exameData, consumivelData, internamentoData, medicamentoData, visitaData;
    private String consultaHora, exameHora, consumivelHora, internamentoHora, medicamentoHora, visitaHora;
    private String consulta, consultaMedico, consultaCodigo, consultaCategoria, consultaTitulo, consultaDescricao;
    private String exame, exameCategoria, exameCodigo, exameTitulo, exameDescricao;
    private String consumivel, consumivelCategoria, consumivelCodigo, consumivelTitulo, consumivelComposicao, consumivelPosologia;
    private String internamento, internamentoTempo, internamentoCodigo, internamentoTipo;
    private String medicamento, medicamentoCategoria, medicamentoCodigo, medicamentoTitulo, medicamentoComposicao, medicamentoPosologia;
    private String visita, visitaTempo, visitaCodigo, visitaTipo;

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

        consultaData = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        consultaHora = new SimpleDateFormat("HH:mm:ss").format(new Date());

        if (consulta.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Consulta/Procedimento' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consultaMedico.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Médico' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consultaQuantidade == 0) {
            addMensagem("Campo vazio!", "O campo 'Quantidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            ConsultaTemp consultaTemp = new ConsultaTemp(this.consulta, consultaData, consultaHora, consultaMedico, consultaValor, consultaValorTotal, consultaCodigo, consultaCategoria, consultaTitulo, consultaDescricao, consultaQuantidade);
            consultasTemp.add(consultaTemp);
        }
        return null;
    }

    public String adicionarConsumivel() {

        consumivelData = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        consumivelHora = new SimpleDateFormat("HH:mm:ss").format(new Date());

        if (consumivel.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Consumível' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (consumivelQuantidade == 0) {
            addMensagem("Campo vazio!", "O campo 'Quantidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            ConsumivelTemp consumivelTemp = new ConsumivelTemp(this.consumivel, consumivelData, consumivelHora, consumivelCategoria, consumivelValor, consumivelValorTotal, consumivelCodigo, consumivelTitulo, consumivelComposicao, consumivelPosologia, consumivelQuantidade);
            consumiveisTemp.add(consumivelTemp);
        }
        return null;
    }

    public String adicionarInternamento() {

        internamentoData = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        internamentoHora = new SimpleDateFormat("HH:mm:ss").format(new Date());

        if (internamento.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Internamento' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (internamentoQuantidade == 0) {
            addMensagem("Campo vazio!", "O campo 'Quantidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            InternamentoTemp internamentoTemp = new InternamentoTemp(this.internamento, internamentoData, internamentoHora, internamentoTempo, internamentoValor, internamentoValorTotal, internamentoCodigo, internamentoTipo, internamentoQuantidade);
            internamentosTemp.add(internamentoTemp);
        }
        return null;
    }

    public String adicionarMedicamento() {

        medicamentoData = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        medicamentoHora = new SimpleDateFormat("HH:mm:ss").format(new Date());

        if (medicamento.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Medicamento' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicamentoQuantidade == 0) {
            addMensagem("Campo vazio!", "O campo 'Quantidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            MedicamentoTemp medicamentoTemp = new MedicamentoTemp(this.medicamento, medicamentoData, medicamentoHora, medicamentoCategoria, medicamentoValor, medicamentoValorTotal, medicamentoCodigo, medicamentoTitulo, medicamentoComposicao, medicamentoPosologia, medicamentoQuantidade);
            medicamentosTemp.add(medicamentoTemp);
        }
        return null;
    }

    public String adicionarVisita() {

        visitaData = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        visitaHora = new SimpleDateFormat("HH:mm:ss").format(new Date());

        if (visita.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Visita' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (visitaQuantidade == 0) {
            addMensagem("Campo vazio!", "O campo 'Quantidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            VisitaTemp visitaTemp = new VisitaTemp(this.visita, visitaData, visitaHora, visitaTempo, visitaValor, visitaValorTotal, visitaCodigo, visitaTipo, visitaQuantidade);
            visitasTemp.add(visitaTemp);
        }
        return null;
    }

    public String adicionarExame() {

        exameData = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        exameHora = new SimpleDateFormat("HH:mm:ss").format(new Date());

        if (exame.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Exame' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (exameQuantidade == 0) {
            addMensagem("Campo vazio!", "O campo 'Quantidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            ExameTemp exameTemp = new ExameTemp(this.exame, exameData, exameHora, exameCategoria, exameValor, exameValorTotal, exameCodigo, exameTitulo, exameDescricao, exameQuantidade);
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

    public int getConsultaQuantidade() {
        return consultaQuantidade;
    }

    public void setConsultaQuantidade(int consultaQuantidade) {
        this.consultaQuantidade = consultaQuantidade;
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

    public double getConsultaValorTotal() {
        consultaValorTotal = consultaValor * consultaQuantidade;
        return consultaValorTotal;
    }

    public void setConsultaValorTotal(double consultaValorTotal) {
        consultaValorTotal = consultaValor * consultaQuantidade;
        this.consultaValorTotal = consultaValorTotal;
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

    public double getExameValorTotal() {
        return exameValorTotal;
    }

    public void setExameValorTotal(double exameValorTotal) {
        this.exameValorTotal = exameValorTotal;
    }

    public double getConsumivelValorTotal() {
        return consumivelValorTotal;
    }

    public void setConsumivelValorTotal(double consumivelValorTotal) {
        this.consumivelValorTotal = consumivelValorTotal;
    }

    public double getInternamentoValorTotal() {
        return internamentoValorTotal;
    }

    public void setInternamentoValorTotal(double internamentoValorTotal) {
        this.internamentoValorTotal = internamentoValorTotal;
    }

    public double getMedicamentoValorTotal() {
        return medicamentoValorTotal;
    }

    public void setMedicamentoValorTotal(double medicamentoValorTotal) {
        this.medicamentoValorTotal = medicamentoValorTotal;
    }

    public double getVisitaValorTotal() {
        return visitaValorTotal;
    }

    public void setVisitaValorTotal(double visitaValorTotal) {
        this.visitaValorTotal = visitaValorTotal;
    }

    public int getExameQuantidade() {
        return exameQuantidade;
    }

    public void setExameQuantidade(int exameQuantidade) {
        this.exameQuantidade = exameQuantidade;
    }

    public int getConsumivelQuantidade() {
        return consumivelQuantidade;
    }

    public void setConsumivelQuantidade(int consumivelQuantidade) {
        this.consumivelQuantidade = consumivelQuantidade;
    }

    public int getInternamentoQuantidade() {
        return internamentoQuantidade;
    }

    public void setInternamentoQuantidade(int internamentoQuantidade) {
        this.internamentoQuantidade = internamentoQuantidade;
    }

    public int getMedicamentoQuantidade() {
        return medicamentoQuantidade;
    }

    public void setMedicamentoQuantidade(int medicamentoQuantidade) {
        this.medicamentoQuantidade = medicamentoQuantidade;
    }

    public int getVisitaQuantidade() {
        return visitaQuantidade;
    }

    public void setVisitaQuantidade(int visitaQuantidade) {
        this.visitaQuantidade = visitaQuantidade;
    }

    public String getExameCodigo() {
        return exameCodigo;
    }

    public void setExameCodigo(String exameCodigo) {
        this.exameCodigo = exameCodigo;
    }

    public String getExameTitulo() {
        return exameTitulo;
    }

    public void setExameTitulo(String exameTitulo) {
        this.exameTitulo = exameTitulo;
    }

    public String getExameDescricao() {
        return exameDescricao;
    }

    public void setExameDescricao(String exameDescricao) {
        this.exameDescricao = exameDescricao;
    }

    public String getConsumivelCodigo() {
        return consumivelCodigo;
    }

    public void setConsumivelCodigo(String consumivelCodigo) {
        this.consumivelCodigo = consumivelCodigo;
    }

    public String getConsumivelTitulo() {
        return consumivelTitulo;
    }

    public void setConsumivelTitulo(String consumivelTitulo) {
        this.consumivelTitulo = consumivelTitulo;
    }

    public String getConsumivelComposicao() {
        return consumivelComposicao;
    }

    public void setConsumivelComposicao(String consumivelComposicao) {
        this.consumivelComposicao = consumivelComposicao;
    }

    public String getConsumivelPosologia() {
        return consumivelPosologia;
    }

    public void setConsumivelPosologia(String consumivelPosologia) {
        this.consumivelPosologia = consumivelPosologia;
    }

    public String getInternamentoCodigo() {
        return internamentoCodigo;
    }

    public void setInternamentoCodigo(String internamentoCodigo) {
        this.internamentoCodigo = internamentoCodigo;
    }

    public String getInternamentoTipo() {
        return internamentoTipo;
    }

    public void setInternamentoTipo(String internamentoTipo) {
        this.internamentoTipo = internamentoTipo;
    }

    public String getMedicamentoCodigo() {
        return medicamentoCodigo;
    }

    public void setMedicamentoCodigo(String medicamentoCodigo) {
        this.medicamentoCodigo = medicamentoCodigo;
    }

    public String getMedicamentoTitulo() {
        return medicamentoTitulo;
    }

    public void setMedicamentoTitulo(String medicamentoTitulo) {
        this.medicamentoTitulo = medicamentoTitulo;
    }

    public String getMedicamentoComposicao() {
        return medicamentoComposicao;
    }

    public void setMedicamentoComposicao(String medicamentoComposicao) {
        this.medicamentoComposicao = medicamentoComposicao;
    }

    public String getMedicamentoPosologia() {
        return medicamentoPosologia;
    }

    public void setMedicamentoPosologia(String medicamentoPosologia) {
        this.medicamentoPosologia = medicamentoPosologia;
    }

    public String getVisitaCodigo() {
        return visitaCodigo;
    }

    public void setVisitaCodigo(String visitaCodigo) {
        this.visitaCodigo = visitaCodigo;
    }

    public String getVisitaTipo() {
        return visitaTipo;
    }

    public void setVisitaTipo(String visitaTipo) {
        this.visitaTipo = visitaTipo;
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
            consultaQuantidade = 1;

            exame = "";
            exameData = "";
            exameHora = "";
            exameQuantidade = 1;

            internamento = "";
            internamentoData = "";
            internamentoHora = "";
            internamentoQuantidade = 1;

            visita = "";
            visitaData = "";
            visitaHora = "";
            visitaQuantidade = 1;

            medicamento = "";
            medicamentoData = "";
            medicamentoHora = "";
            medicamentoQuantidade = 1;

            consumivel = "";
            consumivelData = "";
            consumivelHora = "";
            consumivelQuantidade = 1;

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
        consultaQuantidade = 1;

        exame = "";
        exameData = "";
        exameHora = "";
        exameQuantidade = 1;

        internamento = "";
        internamentoData = "";
        internamentoHora = "";
        internamentoQuantidade = 1;

        visita = "";
        visitaData = "";
        visitaHora = "";
        visitaQuantidade = 1;

        medicamento = "";
        medicamentoData = "";
        medicamentoHora = "";
        medicamentoQuantidade = 1;

        consumivel = "";
        consumivelData = "";
        consumivelHora = "";
        consumivelQuantidade = 1;

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
