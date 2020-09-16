/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Visita;
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
public class VisitaDAO {

    public boolean verificar(Visita visita) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from visita where codigoVisita='" + visita.getCodigoVisita() + "'");

            ResultSet rs = ps.executeQuery();

            Visita p = new Visita();

            while (rs.next()) {
                p.setCodigoVisita(rs.getString("codigoVisita"));
            }

            if (p.getCodigoVisita() != null) {

                if (p.getCodigoVisita().equals(visita.getCodigoVisita())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar a visita!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;

    }

    public void actualizar(Visita visita) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update visita set tipoVisita=?, tempoVisita=?, valorVisita=? where codigoVisita=?");

            ps.setString(1, visita.getTipoVisita());
            ps.setString(2, visita.getTempoVisita());
            ps.setString(3, visita.getValorVisita());
            ps.setString(4, visita.getCodigoVisita());

            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar a visita!", ex);
        }

    }

    public void salvar(Visita visita) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `visita`(`codigoVisita`, `tipoVisita`, `tempoVisita`, `valorVisita`) VALUES (?, ?, ?, ?)");

            ps.setString(1, visita.getCodigoVisita());
            ps.setString(2, visita.getTipoVisita());
            ps.setString(3, visita.getTempoVisita());
            ps.setString(4, visita.getValorVisita());
            
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar a visita!", ex);
        }

    }

    public void deletar(int idVisita) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from visita where idVisita=?");
            ps.setInt(1, idVisita);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover a visita!", ex);
        }
    }

    public List<Visita> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from visita");

            ResultSet rs = pst.executeQuery();

            List<Visita> visitas = new ArrayList();

            while (rs.next()) {

                Visita ps = new Visita();

                ps.setIdVisita(rs.getInt("idVisita"));
                ps.setCodigoVisita(rs.getString("codigoVisita"));
                ps.setTipoVisita(rs.getString("tipoVisita"));
                ps.setTempoVisita(rs.getString("tempoVisita"));
                ps.setValorVisita(rs.getString("valorVisita"));

                visitas.add(ps);

            }

            return visitas;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar a visita!", ex);
        }

    }
    
    public List<Visita> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps1 = null;

            if ("positivo".equals(tipo)) {

                ps1 = conexao.prepareStatement("Select * from visita where tipoVisita LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Visita> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps1.executeQuery();
            List<Visita> relatorios = new ArrayList();

            while (rs.next()) {

                Visita ps = new Visita();

                ps.setIdVisita(rs.getInt("idVisita"));
                ps.setCodigoVisita(rs.getString("codigoVisita"));
                ps.setTipoVisita(rs.getString("tipoVisita"));
                ps.setTempoVisita(rs.getString("tempoVisita"));
                ps.setValorVisita(rs.getString("valorVisita"));

                relatorios.add(ps);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar a visita!", ex);
        }
    }

}
