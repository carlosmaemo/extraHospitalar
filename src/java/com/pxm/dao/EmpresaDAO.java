/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Empresa;
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
public class EmpresaDAO {

    public boolean verificar(Empresa empresa) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from empresa where codigoEmpresa='" + empresa.getCodigoEmpresa() + "'");

            ResultSet rs = ps.executeQuery();

            Empresa p = new Empresa();

            while (rs.next()) {
                p.setCodigoEmpresa(rs.getString("codigoEmpresa"));
            }

            if (p.getCodigoEmpresa() != null) {

                if (p.getCodigoEmpresa().equals(empresa.getCodigoEmpresa())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar a empresa!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;

    }

    public void actualizar(Empresa empresa) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update empresa set nomeEmpresa=?, nuitEmpresa=?, contactoEmpresa=?, emailEmpresa=?, enderecoEmpresa=? where codigoEmpresa=?");

            ps.setString(1, empresa.getNomeEmpresa());
            ps.setString(2, empresa.getNuitEmpresa());
            ps.setString(3, empresa.getContactoEmpresa());
            ps.setString(4, empresa.getEmailEmpresa());
            ps.setString(5, empresa.getEnderecoEmpresa());

            ps.setString(6, empresa.getCodigoEmpresa());

            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar a empresa!", ex);
        }

    }

    public void salvar(Empresa empresa) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `empresa`(`nomeEmpresa`, `nuitEmpresa`, `contactoEmpresa`, `emailEmpresa`, `enderecoEmpresa`, `codigoEmpresa`) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, empresa.getNomeEmpresa());
            ps.setString(2, empresa.getNuitEmpresa());
            ps.setString(3, empresa.getContactoEmpresa());
            ps.setString(4, empresa.getEmailEmpresa());
            ps.setString(5, empresa.getEnderecoEmpresa());
            ps.setString(6, empresa.getCodigoEmpresa());
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar a empresa!", ex);
        }

    }

    public void deletar(int idEmpresa) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from empresa where idEmpresa=?");
            ps.setInt(1, idEmpresa);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover a empresa!", ex);
        }
    }

    public List<Empresa> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from empresa");

            ResultSet rs = pst.executeQuery();

            List<Empresa> empresas = new ArrayList();

            while (rs.next()) {

                Empresa ps = new Empresa();

                ps.setIdEmpresa(rs.getInt("idEmpresa"));
                ps.setNomeEmpresa(rs.getString("nomeEmpresa"));
                ps.setNuitEmpresa(rs.getString("nuitEmpresa"));
                ps.setContactoEmpresa(rs.getString("contactoEmpresa"));
                ps.setEmailEmpresa(rs.getString("emailEmpresa"));
                ps.setEnderecoEmpresa(rs.getString("enderecoEmpresa"));
                ps.setCodigoEmpresa(rs.getString("codigoEmpresa"));

                empresas.add(ps);

            }

            return empresas;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar a empresa!", ex);
        }

    }

    public List<Empresa> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = null;

            if ("positivo".equals(tipo)) {

                ps = conexao.prepareStatement("Select * from empresa where nomeEmpresa LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Empresa> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps.executeQuery();
            List<Empresa> relatorios = new ArrayList();

            while (rs.next()) {

                Empresa ps2 = new Empresa();
                ps2.setIdEmpresa(rs.getInt("idEmpresa"));
                ps2.setNomeEmpresa(rs.getString("nomeEmpresa"));
                ps2.setNuitEmpresa(rs.getString("nuitEmpresa"));
                ps2.setContactoEmpresa(rs.getString("contactoEmpresa"));
                ps2.setEmailEmpresa(rs.getString("emailEmpresa"));
                ps2.setEnderecoEmpresa(rs.getString("enderecoEmpresa"));
                ps2.setCodigoEmpresa(rs.getString("codigoEmpresa"));

                relatorios.add(ps2);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar a empresa!", ex);
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

            ps = conexao.prepareStatement("INSERT INTO `empresa`(`nomeEmpresa`, `nuitEmpresa`, `contactoEmpresa`, `emailEmpresa`, `enderecoEmpresa`, `codigoEmpresa`) VALUES (?, ?, ?, ?, ?, ?)");

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
                            String nomeEmpresa = nextCell.toString();
                            ps.setString(1, nomeEmpresa);
                            break;
                        case 1:
                            String nuitEmpresa = nextCell.toString();
                            ps.setString(2, nuitEmpresa);
                        case 2:
                            String contactoEmpresa = nextCell.toString();
                            ps.setString(3, contactoEmpresa);
                        case 3:
                            String emailEmpresa = nextCell.toString();
                            ps.setString(4, emailEmpresa);
                        case 4:
                            String enderecoEmpresa = nextCell.toString();
                            ps.setString(5, enderecoEmpresa);
                        case 5:
                            String codigoEmpresa = nextCell.toString();
                            ps.setString(6, codigoEmpresa);
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
