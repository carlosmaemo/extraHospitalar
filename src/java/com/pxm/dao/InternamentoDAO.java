/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Internamento;
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
public class InternamentoDAO {

    public boolean verificar(Internamento internamento) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from internamento where codigoInternamento='" + internamento.getCodigoInternamento() + "'");

            ResultSet rs = ps.executeQuery();

            Internamento p = new Internamento();

            while (rs.next()) {
                p.setCodigoInternamento(rs.getString("codigoInternamento"));
            }

            if (p.getCodigoInternamento() != null) {

                if (p.getCodigoInternamento().equals(internamento.getCodigoInternamento())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar o internamento!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public void actualizar(Internamento internamento) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update internamento set tipoInternamento=?, tempoInternamento=?, valorInternamento=? where codigoInternamento=?");

            ps.setString(1, internamento.getTipoInternamento());
            ps.setString(2, internamento.getTempoInternamento());
            ps.setString(3, internamento.getValorInternamento());
            ps.setString(4, internamento.getCodigoInternamento());

            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar o internamento!", ex);
        }

    }

    public void salvar(Internamento internamento) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `internamento`(`codigoInternamento`, `tipoInternamento`, `tempoInternamento`, `valorInternamento`) VALUES (?, ?, ?, ?)");

            ps.setString(1, internamento.getCodigoInternamento());
            ps.setString(2, internamento.getTipoInternamento());
            ps.setString(3, internamento.getTempoInternamento());
            ps.setString(4, internamento.getValorInternamento());

            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar o internamento!", ex);
        }

    }

    public void deletar(int idInternamento) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from internamento where idInternamento=?");
            ps.setInt(1, idInternamento);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o internamento!", ex);
        }
    }

    public List<Internamento> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from internamento");

            ResultSet rs = pst.executeQuery();

            List<Internamento> internamentos = new ArrayList();

            while (rs.next()) {

                Internamento ps = new Internamento();

                ps.setIdInternamento(rs.getInt("idInternamento"));
                ps.setCodigoInternamento(rs.getString("codigoInternamento"));
                ps.setTipoInternamento(rs.getString("tipoInternamento"));
                ps.setTempoInternamento(rs.getString("tempoInternamento"));
                ps.setValorInternamento(rs.getString("valorInternamento"));

                internamentos.add(ps);

            }

            return internamentos;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o internamento!", ex);
        }
    }
    
    public List<Internamento> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps1 = null;

            if ("positivo".equals(tipo)) {

                ps1 = conexao.prepareStatement("Select * from internamento where tipoInternamento LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Internamento> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps1.executeQuery();
            List<Internamento> relatorios = new ArrayList();

            while (rs.next()) {

                Internamento ps = new Internamento();

                ps.setIdInternamento(rs.getInt("idInternamento"));
                ps.setCodigoInternamento(rs.getString("codigoInternamento"));
                ps.setTipoInternamento(rs.getString("tipoInternamento"));
                ps.setTempoInternamento(rs.getString("tempoInternamento"));
                ps.setValorInternamento(rs.getString("valorInternamento"));

                relatorios.add(ps);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o internamento!", ex);
        }
    }

}
