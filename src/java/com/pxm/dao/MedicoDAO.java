/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Medico;
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
public class MedicoDAO {

    public boolean verificar(Medico medico) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from medico where ormmMedico='" + medico.getOrmmMedico() + "'");

            ResultSet rs = ps.executeQuery();

            Medico p = new Medico();

            while (rs.next()) {
                p.setOrmmMedico(rs.getString("ormmMedico"));
            }

            if (p.getOrmmMedico() != null) {

                if (p.getOrmmMedico().equals(medico.getOrmmMedico())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar o medico!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;

    }

    public void actualizar(Medico medico) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update medico set nomeMedico=?, apelidoMedico=?, contactoMedico=?, enderecoMedico=?, especialidadeMedico=? where ormmMedico=?");

            ps.setString(1, medico.getNomeMedico());
            ps.setString(2, medico.getApelidoMedico());
            ps.setString(3, medico.getContactoMedico());
            ps.setString(4, medico.getEnderecoMedico());
            ps.setString(5, medico.getEspecialidadeMedico());
            ps.setString(6, medico.getOrmmMedico());

            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar o medico!", ex);
        }

    }

    public void salvar(Medico medico) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `medico`(`nomeMedico`, `apelidoMedico`, `contactoMedico`, `enderecoMedico`, `ormmMedico`, `especialidadeMedico`) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, medico.getNomeMedico());
            ps.setString(2, medico.getApelidoMedico());
            ps.setString(3, medico.getContactoMedico());
            ps.setString(4, medico.getEnderecoMedico());
            ps.setString(5, medico.getOrmmMedico());
            ps.setString(6, medico.getEspecialidadeMedico());

            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar o medico!", ex);
        }

    }

    public void deletar(int idMedico) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from medico where idMedico=?");
            ps.setInt(1, idMedico);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o medico!", ex);
        }
    }

    public List<Medico> buscar() throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from medico");

            ResultSet rs = pst.executeQuery();

            List<Medico> medicos = new ArrayList();

            while (rs.next()) {

                Medico ps = new Medico();

                ps.setIdMedico(rs.getInt("idMedico"));
                ps.setNomeMedico(rs.getString("nomeMedico"));
                ps.setApelidoMedico(rs.getString("apelidoMedico"));
                ps.setContactoMedico(rs.getString("contactoMedico"));
                ps.setEnderecoMedico(rs.getString("enderecoMedico"));
                ps.setOrmmMedico(rs.getString("ormmMedico"));
                ps.setEspecialidadeMedico(rs.getString("especialidadeMedico"));

                medicos.add(ps);

            }

            return medicos;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o medico!", ex);
        }

    }
    
    public List<Medico> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps1 = null;

            if ("positivo".equals(tipo)) {

                ps1 = conexao.prepareStatement("Select * from medico where nomeMedico LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Medico> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps1.executeQuery();
            List<Medico> relatorios = new ArrayList();

            while (rs.next()) {

                Medico ps = new Medico();

                ps.setIdMedico(rs.getInt("idMedico"));
                ps.setNomeMedico(rs.getString("nomeMedico"));
                ps.setApelidoMedico(rs.getString("apelidoMedico"));
                ps.setContactoMedico(rs.getString("contactoMedico"));
                ps.setEnderecoMedico(rs.getString("enderecoMedico"));
                ps.setOrmmMedico(rs.getString("ormmMedico"));
                ps.setEspecialidadeMedico(rs.getString("especialidadeMedico"));

                relatorios.add(ps);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o médico!", ex);
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

            ps = conexao.prepareStatement("INSERT INTO `medico`(`nomeMedico`, `apelidoMedico`, `contactoMedico`, `enderecoMedico`, `ormmMedico`, `especialidadeMedico`) VALUES (?, ?, ?, ?, ?, ?)");

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
                            String nomeMedico = nextCell.toString();
                            ps.setString(1, nomeMedico);
                            break;
                        case 1:
                            String apelidoMedico = nextCell.toString();
                            ps.setString(2, apelidoMedico);
                        case 2:
                            String contactoMedico = nextCell.toString();
                            ps.setString(3, contactoMedico);
                        case 3:
                            String enderecoMedico = nextCell.toString();
                            ps.setString(4, enderecoMedico);
                        case 4:
                            String ormmMedico = nextCell.toString();
                            ps.setString(5, ormmMedico);
                        case 5:
                            String especialidadeMedico = nextCell.toString();
                            ps.setString(6, especialidadeMedico);
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
