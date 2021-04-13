/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Empresa;
import com.pxm.model.Paciente;
import com.pxm.util.Conecxao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author cmaemo
 */
public class PacienteDAO {

    public boolean verificar(Paciente paciente) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from paciente where nidPaciente='" + paciente.getNidPaciente() + "'");

            ResultSet rs = ps.executeQuery();

            Paciente p = new Paciente();

            while (rs.next()) {
                p.setNidPaciente(rs.getString("nidPaciente"));
            }

            if (p.getNidPaciente() != null) {

                if (p.getNidPaciente().equals(paciente.getNidPaciente())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar o paciente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;

    }

    public void actualizar(Paciente paciente) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update paciente set nomePaciente=?, apelidoPaciente=?, contactoPaciente=?, enderecoPaciente=?, sexoPaciente=?, nrseguroPaciente=?, codigoEmpresa=?, nomeEmpresa=? where nidPaciente=?");

            ps.setString(1, paciente.getNomePaciente());
            ps.setString(2, paciente.getApelidoPaciente());
            ps.setString(3, paciente.getContactoPaciente());
            ps.setString(4, paciente.getEnderecoPaciente());
            ps.setString(5, paciente.getSexoPaciente());
            ps.setString(6, paciente.getNrSeguroPaciente());
            
            
            String[] parte1 = paciente.getCodigoEmpresa().split("_/");
            String codigoEmpresa = parte1[1];
            
            ps.setString(7, codigoEmpresa);

            String[] parte2 = paciente.getCodigoEmpresa().split("_/");
            String nomeEmpresa = parte2[0];

            ps.setString(8, nomeEmpresa);
            
            ps.setString(9, paciente.getNidPaciente());
            
            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar o paciente!", ex);
        }

    }

    public void salvar(Paciente paciente) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `paciente`(`nomePaciente`, `apelidoPaciente`, `contactoPaciente`, `enderecoPaciente`, `nidPaciente`, `sexoPaciente`, `nrseguroPaciente`, `codigoEmpresa`, `nomeEmpresa`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, paciente.getNomePaciente());
            ps.setString(2, paciente.getApelidoPaciente());
            ps.setString(3, paciente.getContactoPaciente());
            ps.setString(4, paciente.getEnderecoPaciente());
            ps.setString(5, paciente.getNidPaciente());
            ps.setString(6, paciente.getSexoPaciente());
            ps.setString(7, paciente.getNrSeguroPaciente());
            
            String[] parte1 = paciente.getCodigoEmpresa().split("_/");
            String codigoEmpresa = parte1[1];
            
            ps.setString(8, codigoEmpresa);

            String[] parte2 = paciente.getCodigoEmpresa().split("_/");
            String nomeEmpresa = parte2[0];

            ps.setString(9, nomeEmpresa);

            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar o paciente!", ex);
        }

    }

    public void deletar(int idPaciente) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from paciente where idPaciente=?");
            ps.setInt(1, idPaciente);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o paciente!", ex);
        }
    }

    public List<Paciente> buscar() throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from paciente");

            ResultSet rs = pst.executeQuery();

            List<Paciente> pacientes = new ArrayList();

            while (rs.next()) {

                Paciente ps = new Paciente();

                ps.setIdPaciente(rs.getInt("idPaciente"));
                ps.setNomePaciente(rs.getString("nomePaciente"));
                ps.setApelidoPaciente(rs.getString("apelidoPaciente"));
                ps.setContactoPaciente(rs.getString("contactoPaciente"));
                ps.setEnderecoPaciente(rs.getString("enderecoPaciente"));
                ps.setNidPaciente(rs.getString("nidPaciente"));
                ps.setSexoPaciente(rs.getString("sexoPaciente"));
                ps.setNrSeguroPaciente(rs.getString("nrseguroPaciente"));
                ps.setNomeEmpresa(rs.getString("nomeEmpresa"));

                pacientes.add(ps);

            }

            return pacientes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o paciente!", ex);
        }

    }

    public List<Paciente> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps1 = null;

            if ("positivo".equals(tipo)) {

                ps1 = conexao.prepareStatement("Select * from paciente where nomePaciente LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Paciente> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps1.executeQuery();
            List<Paciente> relatorios = new ArrayList();

            while (rs.next()) {

                Paciente ps = new Paciente();

                ps.setIdPaciente(rs.getInt("idPaciente"));
                ps.setNomePaciente(rs.getString("nomePaciente"));
                ps.setApelidoPaciente(rs.getString("apelidoPaciente"));
                ps.setContactoPaciente(rs.getString("contactoPaciente"));
                ps.setEnderecoPaciente(rs.getString("enderecoPaciente"));
                ps.setNidPaciente(rs.getString("nidPaciente"));
                ps.setSexoPaciente(rs.getString("sexoPaciente"));
                ps.setNrSeguroPaciente(rs.getString("nrseguroPaciente"));
                ps.setNomeEmpresa(rs.getString("nomeEmpresa"));

                relatorios.add(ps);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o paciente!", ex);
        }
    }

    public List<String> buscarCodigoEmpresas() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT nomeEmpresa,codigoEmpresa FROM empresa;");

            ResultSet rs = ps.executeQuery();

            List<String> empresas = new ArrayList<>();

            while (rs.next()) {

                empresas.add(rs.getString("nomeEmpresa") + "_/" + rs.getString("codigoEmpresa"));
            }

            return empresas;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o código de empresa!", ex);

        }

    }
    
    public boolean carregar(String caminho) throws ErroSistema, FileNotFoundException, IOException {

        int batchSize = 20;

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            FileInputStream inputStream = new FileInputStream(caminho);

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet primeiraLinha = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = primeiraLinha.iterator();

            ps = conexao.prepareStatement("INSERT INTO `paciente`(`nomePaciente`, `apelidoPaciente`, `contactoPaciente`, `enderecoPaciente`, `nidPaciente`, `sexoPaciente`, `nrseguroPaciente`, `codigoEmpresa`, `nomeEmpresa`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            int count = 0;

            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();

                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            String nomePaciente = nextCell.getStringCellValue();
                            ps.setString(1, nomePaciente);
                            break;
                        case 1:
                            String apelidoPaciente = nextCell.getStringCellValue();
                            ps.setString(2, apelidoPaciente);
                        case 2:
                            String contactoPaciente = nextCell.getStringCellValue();
                            ps.setString(3, contactoPaciente);
                        case 3:
                            String enderecoPaciente = nextCell.getStringCellValue();
                            ps.setString(4, enderecoPaciente);
                        case 4:
                            String nidPaciente = nextCell.getStringCellValue();
                            ps.setString(5, nidPaciente);
                        case 5:
                            String sexoPaciente = nextCell.getStringCellValue();
                            ps.setString(6, sexoPaciente);
                        case 6:
                            String nrseguroPaciente = nextCell.getStringCellValue();
                            ps.setString(7, nrseguroPaciente);
                        case 7:
                            String codigoEmpresa = nextCell.getStringCellValue();
                            ps.setString(8, codigoEmpresa);
                        case 8:
                            String nomeEmpresa = nextCell.getStringCellValue();
                            ps.setString(9, nomeEmpresa);
                    }

                }

                ps.addBatch();

                if (count % batchSize == 0) {
                    ps.executeBatch();
                }

            }

            workbook.close();

            ps.executeBatch();

            Conecxao.fecharConexao();
            
            return true;
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao carregar o arquivo!" + ex.getMessage(), ex);
        }
    }

}
