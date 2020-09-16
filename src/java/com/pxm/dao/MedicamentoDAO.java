/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Medicamento;
import com.pxm.util.Conecxao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author cmaemo
 */
public class MedicamentoDAO {

    public boolean verificar(Medicamento medicamento) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from medicamento where codigoMedicamento='" + medicamento.getCodigoMedicamento() + "'");

            ResultSet rs = ps.executeQuery();

            Medicamento p = new Medicamento();

            while (rs.next()) {
                p.setCodigoMedicamento(rs.getString("codigoMedicamento"));
            }

            if (p.getCodigoMedicamento() != null) {

                if (p.getCodigoMedicamento().equals(medicamento.getCodigoMedicamento())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar o medicamento!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public void actualizar(Medicamento medicamento) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update medicamento set categoriaMedicamento=?, tituloMedicamento=?, valorMedicamento=?, composicaoMedicamento=?, posologiaMedicamento=? where codigoMedicamento=?");

            ps.setString(1, medicamento.getCategoriaMedicamento());
            ps.setString(2, medicamento.getTituloMedicamento());
            ps.setString(3, medicamento.getValorMedicamento());
            ps.setString(4, medicamento.getComposicaoMedicamento());
            ps.setString(5, medicamento.getPosologiaMedicamento());

            ps.setString(6, medicamento.getCodigoMedicamento());

            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar o medicamento!", ex);
        }

    }

    public void salvar(Medicamento medicamento) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `medicamento`(`codigoMedicamento`, `categoriaMedicamento`, `tituloMedicamento`, `valorMedicamento`, `composicaoMedicamento`, `posologiaMedicamento`) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, medicamento.getCodigoMedicamento());
            ps.setString(2, medicamento.getCategoriaMedicamento());
            ps.setString(3, medicamento.getTituloMedicamento());
            ps.setString(4, medicamento.getValorMedicamento());
            ps.setString(5, medicamento.getComposicaoMedicamento());
            ps.setString(6, medicamento.getPosologiaMedicamento());

            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar o medicamento!", ex);
        }
    }

    public void deletar(int idMedicamento) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from medicamento where idMedicamento=?");
            ps.setInt(1, idMedicamento);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o medicamento!", ex);
        }
    }

    public List<Medicamento> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from medicamento");

            ResultSet rs = pst.executeQuery();

            List<Medicamento> medicamentos = new ArrayList();

            while (rs.next()) {

                Medicamento ps = new Medicamento();

                ps.setIdMedicamento(rs.getInt("idMedicamento"));
                ps.setCodigoMedicamento(rs.getString("codigoMedicamento"));
                ps.setCategoriaMedicamento(rs.getString("categoriaMedicamento"));
                ps.setTituloMedicamento(rs.getString("tituloMedicamento"));
                ps.setValorMedicamento(rs.getString("valorMedicamento"));
                ps.setComposicaoMedicamento(rs.getString("composicaoMedicamento"));
                ps.setPosologiaMedicamento(rs.getString("posologiaMedicamento"));

                medicamentos.add(ps);

            }

            return medicamentos;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o medicamento!", ex);
        }
    }
    
    public List<Medicamento> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps1 = null;

            if ("positivo".equals(tipo)) {

                ps1 = conexao.prepareStatement("Select * from medicamento where tituloMedicamento LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Medicamento> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps1.executeQuery();
            List<Medicamento> relatorios = new ArrayList();

            while (rs.next()) {

                Medicamento ps = new Medicamento();

                ps.setIdMedicamento(rs.getInt("idMedicamento"));
                ps.setCodigoMedicamento(rs.getString("codigoMedicamento"));
                ps.setCategoriaMedicamento(rs.getString("categoriaMedicamento"));
                ps.setTituloMedicamento(rs.getString("tituloMedicamento"));
                ps.setValorMedicamento(rs.getString("valorMedicamento"));
                ps.setComposicaoMedicamento(rs.getString("composicaoMedicamento"));
                ps.setPosologiaMedicamento(rs.getString("posologiaMedicamento"));

                relatorios.add(ps);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o medicamento!", ex);
        }
    }

}
