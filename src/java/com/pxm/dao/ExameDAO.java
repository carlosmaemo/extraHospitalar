/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Exame;
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
public class ExameDAO {

    public boolean verificar(Exame exame) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from exame where codigoExame='" + exame.getCodigoExame() + "'");

            ResultSet rs = ps.executeQuery();

            Exame p = new Exame();

            while (rs.next()) {
                p.setCodigoExame(rs.getString("codigoExame"));
            }

            if (p.getCodigoExame() != null) {

                if (p.getCodigoExame().equals(exame.getCodigoExame())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar o exame!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public void actualizar(Exame exame) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update exame set categoriaExame=?, tituloExame=?, valorExame=?, descricaoExame=? where codigoExame=?");

            ps.setString(1, exame.getCategoriaExame());
            ps.setString(2, exame.getTituloExame());
            ps.setString(3, exame.getValorExame());
            ps.setString(4, exame.getDescricaoExame());

            ps.setString(5, exame.getCodigoExame());

            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar o exame!", ex);
        }

    }

    public void salvar(Exame exame) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `exame`(`codigoExame`, `categoriaExame`, `tituloExame`, `valorExame`, `descricaoExame`) VALUES (?, ?, ?, ?, ?)");

            ps.setString(1, exame.getCodigoExame());
            ps.setString(2, exame.getCategoriaExame());
            ps.setString(3, exame.getTituloExame());
            ps.setString(4, exame.getValorExame());
            ps.setString(5, exame.getDescricaoExame());

            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar o exame!", ex);
        }

    }

    public void deletar(int idExame) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from exame where idExame=?");
            ps.setInt(1, idExame);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o exame!", ex);
        }
    }

    public List<Exame> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from exame");

            ResultSet rs = pst.executeQuery();

            List<Exame> exames = new ArrayList();

            while (rs.next()) {

                Exame ps = new Exame();

                ps.setIdExame(rs.getInt("idExame"));
                ps.setCodigoExame(rs.getString("codigoExame"));
                ps.setCategoriaExame(rs.getString("categoriaExame"));
                ps.setTituloExame(rs.getString("tituloExame"));
                ps.setValorExame(rs.getString("valorExame"));
                ps.setDescricaoExame(rs.getString("descricaoExame"));

                exames.add(ps);

            }

            return exames;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o exame!", ex);
        }

    }

    public List<Exame> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps1 = null;

            if ("positivo".equals(tipo)) {

                ps1 = conexao.prepareStatement("Select * from exame where tituloExame LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Exame> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps1.executeQuery();
            List<Exame> relatorios = new ArrayList();

            while (rs.next()) {

                Exame ps = new Exame();

                ps.setIdExame(rs.getInt("idExame"));
                ps.setCodigoExame(rs.getString("codigoExame"));
                ps.setCategoriaExame(rs.getString("categoriaExame"));
                ps.setTituloExame(rs.getString("tituloExame"));
                ps.setValorExame(rs.getString("valorExame"));
                ps.setDescricaoExame(rs.getString("descricaoExame"));

                relatorios.add(ps);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o exame!", ex);
        }
    }

}
