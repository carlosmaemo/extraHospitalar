package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.ConsultaTemp;
import com.pxm.model.ConsumivelTemp;
import com.pxm.model.ExameTemp;
import com.pxm.model.Factura;
import com.pxm.model.InternamentoTemp;
import com.pxm.model.MedicamentoTemp;
import com.pxm.model.VisitaTemp;
import com.pxm.util.Conecxao;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author cmaemo
 */
public class FacturaDAO {

    public List<String> buscarNid() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT nidPaciente FROM paciente;");

            ResultSet rs = ps.executeQuery();

            List<String> pacientes = new ArrayList<>();

            while (rs.next()) {

                pacientes.add(rs.getString("nidPaciente"));
            }

            return pacientes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os nids dos pacientes!", ex);

        }

    }

    public List<String> buscarConsulta() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT tituloConsulta, codigoConsulta FROM consulta;");
            ResultSet rs = ps.executeQuery();
            List<String> consultas = new ArrayList<>();
            while (rs.next()) {
                consultas.add(rs.getString("tituloConsulta") + "_/" + rs.getString("codigoConsulta"));
            }
            return consultas;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar as consultas!", ex);
        }
    }

    public List<String> buscarConsumivel() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT tituloConsumivel, codigoConsumivel FROM consumivel;");
            ResultSet rs = ps.executeQuery();
            List<String> consumiveis = new ArrayList<>();
            while (rs.next()) {
                consumiveis.add(rs.getString("tituloConsumivel") + "_/" + rs.getString("codigoConsumivel"));
            }
            return consumiveis;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os consumíveis!", ex);
        }
    }

    public List<String> buscarInternamento() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT tipoInternamento, codigoInternamento FROM internamento;");
            ResultSet rs = ps.executeQuery();
            List<String> internamentos = new ArrayList<>();
            while (rs.next()) {
                internamentos.add(rs.getString("tipoInternamento") + "_/" + rs.getString("codigoInternamento"));
            }
            return internamentos;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os internamentos!", ex);
        }
    }

    public List<String> buscarMedicamento() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT tituloMedicamento, codigoMedicamento FROM medicamento;");
            ResultSet rs = ps.executeQuery();
            List<String> medicamentos = new ArrayList<>();
            while (rs.next()) {
                medicamentos.add(rs.getString("tituloMedicamento") + "_/" + rs.getString("codigoMedicamento"));
            }
            return medicamentos;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os medicamentos!", ex);
        }
    }

    public List<String> buscarVisita() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT tipoVisita, codigoVisita FROM visita;");
            ResultSet rs = ps.executeQuery();
            List<String> visitas = new ArrayList<>();
            while (rs.next()) {
                visitas.add(rs.getString("tipoVisita") + "_/" + rs.getString("codigoVisita"));
            }
            return visitas;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar as visitas!", ex);
        }
    }

    public List<String> buscarExame() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT tituloExame, codigoExame FROM exame;");

            ResultSet rs = ps.executeQuery();

            List<String> exames = new ArrayList<>();

            while (rs.next()) {

                exames.add(rs.getString("tituloExame") + "_/" + rs.getString("codigoExame"));
            }

            return exames;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os exames!", ex);

        }
    }

    // VERIFICAR DADOS CONSULTA
    public String verificarDadosConsulta(String codigo, String campo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from consulta where codigoConsulta=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String a = rs.getString(campo);
                return a;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar os dados da consulta!", ex);
        }
    }
    
    public double verificarDadosValorConsulta(String codigo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select valorConsulta from consulta where codigoConsulta=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double a = rs.getDouble("valorConsulta");
                return a;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar o valor da consulta!", ex);
        }
    }

    // VERIFICAR DADOS CONSUMIVEL
    public String verificarDadosConsumivel(String codigo, String campo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from consumivel where codigoConsumivel=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String a = rs.getString(campo);
                return a;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar os dados do consumivel!", ex);
        }
    }
    public double verificarDadosValorConsumivel(String codigo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select valorConsumivel from consumivel where codigoConsumivel=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double a = rs.getDouble("valorConsumivel");
                return a;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar o valor do consumivel!", ex);
        }
    }
    
    // VERIFICAR DADOS INTERNAMENTO
    public String verificarDadosInternamento(String codigo, String campo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from internamento where codigoInternamento=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String a = rs.getString(campo);
                return a;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar os dados do internamento!", ex);
        }
    }
    public double verificarDadosValorInternamento(String codigo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select valorInternamento from internamento where codigoInternamento=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double a = rs.getDouble("valorInternamento");
                return a;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar o valor do internamento!", ex);
        }
    }

    // VERIFICAR DADOS MEDICAMENTO
    public String verificarDadosMedicamento(String codigo, String campo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from medicamento where codigoMedicamento=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String a = rs.getString(campo);
                return a;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar os dados do medicamento!", ex);
        }
    }
    public double verificarDadosValorMedicamento(String codigo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select valorMedicamento from medicamento where codigoMedicamento=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double a = rs.getDouble("valorMedicamento");
                return a;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar o valor do medicamento!", ex);
        }
    }

    // VERIFICAR DADOS VISITA
    public String verificarDadosVisita(String codigo, String campo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from visita where codigoVisita=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String a = rs.getString(campo);
                return a;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar os dados da visita!", ex);
        }
    }
    public double verificarDadosValorVisita(String codigo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select valorVisita from visita where codigoVisita=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double a = rs.getDouble("valorVisita");
                return a;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar o valor do medicamento!", ex);
        }
    }

    // VERIFICAR DADOS EXAME
    public String verificarDadosExame(String codigo, String campo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from exame where codigoExame=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String a = rs.getString(campo);
                return a;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar os dados do exame!", ex);
        }
    }
    public double verificarDadosValorExame(String codigo) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select valorExame from exame where codigoExame=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double a = rs.getDouble("valorExame");
                return a;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar o valor do exame!", ex);
        }
    }

    // VERIFICAR ID MEDICO
    public Integer verificarIDMedico(String codigoMedico) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from medico where ormmMedico=?");
            ps.setString(1, codigoMedico);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int a = rs.getInt("idMedico");
                return a;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar o id do médico!", ex);
        }
    }

    public List<String> buscarMedico() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT nomeMedico, apelidoMedico, ormmMedico FROM medico;");

            ResultSet rs = ps.executeQuery();

            List<String> medicos = new ArrayList<>();

            while (rs.next()) {

                medicos.add(rs.getString("nomeMedico") + " " + rs.getString("apelidoMedico") + "_/" + rs.getString("ormmMedico"));
            }

            return medicos;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os médicos!", ex);

        }
    }

    public Factura confirmar(String c, int idUsuario) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from paciente where nidPaciente=?");
            PreparedStatement pst2 = conexao.prepareStatement("Select * from usuario where idUsuario=?");

            pst.setString(1, c);
            pst.execute();

            pst2.setInt(1, idUsuario);
            pst2.execute();

            ResultSet rs = pst.executeQuery();
            ResultSet rs2 = pst2.executeQuery();

            Factura ps = new Factura();

            while (rs2.next()) {

                //ps.setClinico(rs2.getString("nomeUsuario"));
                //ps.setClinicoMorada(rs2.getString("moradaUsuario"));
                //ps.setClinicoContacto(rs2.getString("contactoUsuario"));
            }

            while (rs.next()) {

                ps.setIdPaciente(rs.getInt("idPaciente"));
                ps.setNomePaciente(rs.getString("nomePaciente"));
                ps.setApelidoPaciente(rs.getString("apelidoPaciente"));
                ps.setContactoPaciente(rs.getString("contactoPaciente"));
                ps.setEnderecoPaciente(rs.getString("enderecoPaciente"));
                ps.setNidPaciente(rs.getString("nidPaciente"));
                ps.setSexoPaciente(rs.getString("sexoPaciente"));
                ps.setNomeEmpresa(rs.getString("nomeEmpresa"));
                ps.setCodigoEmpresa(rs.getString("codigoEmpresa"));

            }

            return ps;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o paciente!", ex);
        }
    }

    public void salvar(Factura factura, ArrayList<ConsultaTemp> consultas, ArrayList<ConsumivelTemp> consumiveis, ArrayList<ExameTemp> exames, ArrayList<InternamentoTemp> internamentos, ArrayList<MedicamentoTemp> medicamentos, ArrayList<VisitaTemp> visitas) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            String comma1 = "";
            StringBuilder todasConsultas = new StringBuilder();
            for (ConsultaTemp m : consultas) {
                todasConsultas.append(comma1);
                todasConsultas.append(m);
                comma1 = ", ";
            }

            String comma2 = "";
            StringBuilder todosConsumiveis = new StringBuilder();
            for (ConsumivelTemp m : consumiveis) {
                todosConsumiveis.append(comma2);
                todosConsumiveis.append(m);
                comma2 = ", ";
            }

            String comma3 = "";
            StringBuilder todosExames = new StringBuilder();
            for (ExameTemp m : exames) {
                todosExames.append(comma3);
                todosExames.append(m);
                comma3 = ", ";
            }

            String comma4 = "";
            StringBuilder todosInternamentos = new StringBuilder();
            for (InternamentoTemp m : internamentos) {
                todosInternamentos.append(comma4);
                todosInternamentos.append(m);
                comma4 = ", ";
            }

            String comma5 = "";
            StringBuilder todosMedicamentos = new StringBuilder();
            for (MedicamentoTemp m : medicamentos) {
                todosMedicamentos.append(comma5);
                todosMedicamentos.append(m);
                comma5 = ", ";
            }

            String comma6 = "";
            StringBuilder todasVisitas = new StringBuilder();
            for (VisitaTemp m : visitas) {
                todasVisitas.append(comma6);
                todasVisitas.append(m);
                comma6 = ", ";
            }

            int chaveGerada = 0;

            PreparedStatement ps = conexao.prepareStatement("INSERT INTO `factura`(`data`, `idPaciente`, `idMedico`, `idUsuario`) VALUES (?, ?, ?, ?)");

            PreparedStatement ps1 = conexao.prepareStatement("INSERT INTO `factura_consulta`(`idFactura`, `codigoConsulta`, `categoriaConsulta`, `tituloConsulta`, `descricaoConsulta`, `valorConsulta`) VALUES (?, ?, ?, ?, ?, ?)");
            PreparedStatement ps2 = conexao.prepareStatement("INSERT INTO `factura_consumivel`(`idFactura`, `codigoConsumivel`, `categoriaConsumivel`, `tituloConsumivel`, `valorConsumivel`, `composicaoConsumivel`, `posologiaConsumivel`) VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement ps3 = conexao.prepareStatement("INSERT INTO `factura_exame`(`idFactura`, `codigoExame`, `categoriaExame`, `tituloExame`, `valorExame`, `descricaoExame`) VALUES (?, ?, ?, ?, ?, ?)");
            PreparedStatement ps4 = conexao.prepareStatement("INSERT INTO `factura_internamento`(`idFactura`, `codigoInternamento`, `tipoInternamento`, `tempoInternamento`, `valorInternamento`) VALUES (?, ?, ?, ?, ?)");
            PreparedStatement ps5 = conexao.prepareStatement("INSERT INTO `factura_medicamento`(`idFactura`, `codigoMedicamento`, `categoriaMedicamento`, `tituloMedicamento`, `valorMedicamento`, `composicaoMedicamento`, `posologiaMedicamento`) VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement ps6 = conexao.prepareStatement("INSERT INTO `factura_visita`(`idFactura`, `codigoVisita`, `tipoVisita`, `tempoVisita`, `valorVisita`) VALUES (?, ?, ?, ?, ?)");

            ps.setString(1, factura.getDataRegistro());
            ps.setInt(2, factura.getIdPaciente());
            ps.setInt(3, factura.getIdMedico());
            ps.setInt(4, factura.getIdUsuario());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                chaveGerada = rs.getInt(1);
            }

            for (ConsultaTemp m : consultas) {
                ps1.setInt(1, chaveGerada);
                ps1.setString(2, m.getConsultaCodigo());
                ps1.setString(3, m.getConsultaCategoria());
                ps1.setString(4, m.getConsultaTitulo());
                ps1.setString(5, m.getConsultaDescricao());
                ps1.setDouble(6, m.getConsultaValor());
                ps1.execute();
            }

            for (ConsumivelTemp m : consumiveis) {
                ps2.setInt(1, chaveGerada);
                ps2.setString(2, m.getConsumivelCodigo());
                ps2.setString(3, m.getConsumivelCategoria());
                ps2.setString(4, m.getConsumivelTitulo());
                ps2.setDouble(5, m.getConsumivelValor());
                ps2.setString(6, m.getConsumivelComposicao());
                ps2.setString(7, m.getConsumivelPosologia());
                ps2.execute();
            }

            for (ExameTemp m : exames) {
                ps3.setInt(1, chaveGerada);
                ps3.setString(2, m.getExameCodigo());
                ps3.setString(3, m.getExameCategoria());
                ps3.setString(4, m.getExameTitulo());
                ps3.setDouble(5, m.getExameValor());
                ps3.setString(6, m.getExameDescricao());
                ps3.execute();
            }

            for (InternamentoTemp m : internamentos) {
                ps4.setInt(1, chaveGerada);
                ps4.setString(2, m.getInternamentoCodigo());
                ps4.setString(3, m.getInternamentoTipo());
                ps4.setString(4, m.getInternamentoTempo());
                ps4.setDouble(5, m.getInternamentoValor());
                ps4.execute();
            }

            for (MedicamentoTemp m : medicamentos) {
                ps5.setInt(1, chaveGerada);
                ps5.setString(2, m.getMedicamentoCodigo());
                ps5.setString(3, m.getMedicamentoCategoria());
                ps5.setString(4, m.getMedicamentoTitulo());
                ps5.setDouble(5, m.getMedicamentoValor());
                ps5.setString(6, m.getMedicamentoComposicao());
                ps5.setString(7, m.getMedicamentoPosologia());
                ps5.execute();
            }

            for (VisitaTemp m : visitas) {
                ps6.setInt(1, chaveGerada);
                ps6.setString(2, m.getVisitaCodigo());
                ps6.setString(3, m.getVisitaTipo());
                ps6.setString(4, m.getVisitaTempo());
                ps6.setDouble(5, m.getVisitaValor());
                ps6.execute();
            }

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao facturar a consulta!", ex);
        }
    }

    public void deletar(int idFactura) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps1 = conexao.prepareStatement("delete from factura_visita where idFactura=?");
            ps1.setInt(1, idFactura);
            ps1.execute();

            PreparedStatement ps2 = conexao.prepareStatement("delete from factura_medicamento where idFactura=?");
            ps2.setInt(1, idFactura);
            ps2.execute();

            PreparedStatement ps3 = conexao.prepareStatement("delete from factura_internamento where idFactura=?");
            ps3.setInt(1, idFactura);
            ps3.execute();

            PreparedStatement ps4 = conexao.prepareStatement("delete from factura_exame where idFactura=?");
            ps4.setInt(1, idFactura);
            ps4.execute();

            PreparedStatement ps5 = conexao.prepareStatement("delete from factura_consumivel where idFactura=?");
            ps5.setInt(1, idFactura);
            ps5.execute();

            PreparedStatement ps6 = conexao.prepareStatement("delete from factura_consulta where idFactura=?");
            ps6.setInt(1, idFactura);
            ps6.execute();

            PreparedStatement ps7 = conexao.prepareStatement("delete from factura where idFactura=?");
            ps7.setInt(1, idFactura);
            ps7.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover a factura!", ex);
        }
    }

    public List<Factura> buscar() throws ErroSistema {

        List<Factura> facturas = new ArrayList();

        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement pst1 = conexao.prepareStatement("Select * from factura");
            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {

                Factura factura = new Factura();

                factura.setIdFactura(rs1.getInt("idFactura"));
                factura.setData(rs1.getString("data"));
                factura.setIdPaciente(rs1.getInt("idPaciente"));
                factura.setIdMedico(rs1.getInt("idMedico"));
                factura.setIdUsuario(rs1.getInt("idUsuario"));

                PreparedStatement pst2 = conexao.prepareStatement("Select * from paciente where idPaciente=?");
                pst2.setInt(1, factura.getIdPaciente());
                ResultSet rs2 = pst2.executeQuery();

                if (rs2.next()) {
                    factura.setNomePaciente(rs2.getString("nomePaciente"));
                    factura.setApelidoPaciente(rs2.getString("apelidoPaciente"));
                    factura.setContactoPaciente(rs2.getString("contactoPaciente"));
                    factura.setEnderecoPaciente(rs2.getString("enderecoPaciente"));
                    factura.setNidPaciente(rs2.getString("nidPaciente"));
                    factura.setSexoPaciente(rs2.getString("sexoPaciente"));
                    factura.setNomeEmpresa(rs2.getString("nomeEmpresa"));
                    factura.setCodigoEmpresa(rs2.getString("codigoEmpresa"));
                }

                PreparedStatement pst3 = conexao.prepareStatement("Select * from medico where idMedico=?");
                pst3.setInt(1, factura.getIdMedico());
                ResultSet rs3 = pst3.executeQuery();

                if (rs3.next()) {
                    factura.setNomeMedico(rs3.getString("nomeMedico") + " " + rs3.getString("apelidoMedico"));
                }

                PreparedStatement pst4 = conexao.prepareStatement("Select * from usuario where idUsuario=?");
                pst4.setInt(1, factura.getIdUsuario());
                ResultSet rs4 = pst4.executeQuery();

                if (rs4.next()) {
                    factura.setNomeUsuario(rs4.getString("nomeUsuario"));
                }

                facturas.add(factura);

            }

            return facturas;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar facturas!", ex);
        }
    }

    public List<Factura> buscarFiltro(Factura factura, String tipo, String nid) throws ErroSistema {

        try {

            boolean idPaciente = false;

            Factura facturaPaciente = new Factura();

            Connection conexao = Conecxao.getConexao();

            PreparedStatement psPaciente = conexao.prepareStatement("Select * from paciente where nidPaciente=?");
            psPaciente.setString(1, nid);
            ResultSet rsPaciente = psPaciente.executeQuery();

            if (rsPaciente.next()) {
                facturaPaciente.setIdPaciente(rsPaciente.getInt("idPaciente"));
                facturaPaciente.setNomePaciente(rsPaciente.getString("nomePaciente"));
                facturaPaciente.setApelidoPaciente(rsPaciente.getString("apelidoPaciente"));
                facturaPaciente.setContactoPaciente(rsPaciente.getString("contactoPaciente"));
                facturaPaciente.setEnderecoPaciente(rsPaciente.getString("enderecoPaciente"));
                facturaPaciente.setNidPaciente(rsPaciente.getString("nidPaciente"));
                facturaPaciente.setSexoPaciente(rsPaciente.getString("sexoPaciente"));
                facturaPaciente.setNomeEmpresa(rsPaciente.getString("nomeEmpresa"));
                facturaPaciente.setCodigoEmpresa(rsPaciente.getString("codigoEmpresa"));

                idPaciente = true;
            }

            PreparedStatement ps = null;

            if ("nid".equals(tipo)) {

                if (idPaciente == false) {
                    List<Factura> facturas = new ArrayList();
                    return facturas;
                }

                ps = conexao.prepareStatement("Select * from factura where idPaciente = ?");
                ps.setInt(1, facturaPaciente.getIdPaciente());

            } else if ("data".equals(tipo)) {

                ps = conexao.prepareStatement("Select * from factura where data >= ? and data <= ?");
                ps.setString(1, factura.getDataInicial());
                ps.setString(2, factura.getDataFinal());

            } else if ("nid_data".equals(tipo)) {

                if (idPaciente == false) {
                    List<Factura> facturas = new ArrayList();
                    return facturas;
                }

                ps = conexao.prepareStatement("Select * from factura where (data >= ? and data <= ?) and idPaciente = ?");
                ps.setString(1, factura.getDataInicial());
                ps.setString(2, factura.getDataFinal());
                ps.setInt(3, facturaPaciente.getIdPaciente());

            } else if ("vazio".equals(tipo)) {

                List<Factura> facturas = new ArrayList();
                return facturas;

            }

            ResultSet rs = ps.executeQuery();
            List<Factura> facturas = new ArrayList();

            while (rs.next()) {

                Factura facturaRelatorio = new Factura();

                facturaRelatorio.setIdFactura(rs.getInt("idFactura"));
                facturaRelatorio.setData(rs.getString("data"));
                facturaRelatorio.setIdPaciente(rs.getInt("idPaciente"));
                facturaRelatorio.setIdMedico(rs.getInt("idMedico"));
                facturaRelatorio.setIdUsuario(rs.getInt("idUsuario"));

                if (idPaciente == false) {
                    PreparedStatement pst2 = conexao.prepareStatement("Select * from paciente where idPaciente=?");
                    pst2.setInt(1, facturaRelatorio.getIdPaciente());
                    ResultSet rs2 = pst2.executeQuery();

                    if (rs2.next()) {
                        facturaRelatorio.setNomePaciente(rs2.getString("nomePaciente"));
                        facturaRelatorio.setApelidoPaciente(rs2.getString("apelidoPaciente"));
                        facturaRelatorio.setContactoPaciente(rs2.getString("contactoPaciente"));
                        facturaRelatorio.setEnderecoPaciente(rs2.getString("enderecoPaciente"));
                        facturaRelatorio.setNidPaciente(rs2.getString("nidPaciente"));
                        facturaRelatorio.setSexoPaciente(rs2.getString("sexoPaciente"));
                        facturaRelatorio.setNomeEmpresa(rs2.getString("nomeEmpresa"));
                        facturaRelatorio.setCodigoEmpresa(rs2.getString("codigoEmpresa"));
                    }
                } else {
                    facturaRelatorio.setNomePaciente(facturaPaciente.getNomePaciente());
                    facturaRelatorio.setApelidoPaciente(facturaPaciente.getApelidoPaciente());
                    facturaRelatorio.setContactoPaciente(facturaPaciente.getContactoPaciente());
                    facturaRelatorio.setEnderecoPaciente(facturaPaciente.getEnderecoPaciente());
                    facturaRelatorio.setNidPaciente(facturaPaciente.getNidPaciente());
                    facturaRelatorio.setSexoPaciente(facturaPaciente.getSexoPaciente());
                    facturaRelatorio.setNomeEmpresa(facturaPaciente.getNomeEmpresa());
                    facturaRelatorio.setCodigoEmpresa(facturaPaciente.getCodigoEmpresa());

                }

                PreparedStatement pst3 = conexao.prepareStatement("Select * from medico where idMedico=?");
                pst3.setInt(1, facturaRelatorio.getIdMedico());
                ResultSet rs3 = pst3.executeQuery();

                if (rs3.next()) {
                    facturaRelatorio.setNomeMedico(rs3.getString("nomeMedico") + " " + rs3.getString("apelidoMedico"));
                }

                PreparedStatement pst4 = conexao.prepareStatement("Select * from usuario where idUsuario=?");
                pst4.setInt(1, facturaRelatorio.getIdUsuario());
                ResultSet rs4 = pst4.executeQuery();

                if (rs4.next()) {
                    facturaRelatorio.setNomeUsuario(rs4.getString("nomeUsuario"));
                }

                facturas.add(facturaRelatorio);

            }

            return facturas;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar facturas!", ex);
        }
    }

    public void printFactura(Factura factura, Integer idUsuario) throws ErroSistema, IOException {

        Map<String, Object> params = new HashMap<>();
        params.put("nidPaciente", factura.getNidPaciente());
        params.put("idFactura", factura.getIdFactura());

        try {

            Connection conexao = Conecxao.getConexao();

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            ServletOutputStream responseStream = response.getOutputStream();
            InputStream caminho = getClass().getResourceAsStream("../report/factura.jrxml");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + factura.getNomePaciente()+ " " + factura.getApelidoPaciente()+ " - " + factura.getData() + ".pdf\"");

            JasperReport pathReport = JasperCompileManager.compileReport(caminho);
            JasperPrint preencher = JasperFillManager.fillReport(pathReport, params, conexao);
            JasperExportManager.exportReportToPdfStream(preencher, responseStream);

            responseStream.flush();
            responseStream.close();
            context.renderResponse();
            context.responseComplete();

            Conecxao.fecharConexao();

        } catch (JRException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
