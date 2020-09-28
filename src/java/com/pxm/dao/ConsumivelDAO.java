/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Consumivel;
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
public class ConsumivelDAO {

    public boolean verificar(Consumivel consumivel) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from consumivel where codigoConsumivel='" + consumivel.getCodigoConsumivel() + "'");

            ResultSet rs = ps.executeQuery();

            Consumivel p = new Consumivel();

            while (rs.next()) {
                p.setCodigoConsumivel(rs.getString("codigoConsumivel"));
            }

            if (p.getCodigoConsumivel() != null) {

                if (p.getCodigoConsumivel().equals(consumivel.getCodigoConsumivel())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar o consumível!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public void actualizar(Consumivel consumivel) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update consumivel set categoriaConsumivel=?, tituloConsumivel=?, valorConsumivel=?, composicaoConsumivel=?, posologiaConsumivel=? where codigoConsumivel=?");

            ps.setString(1, consumivel.getCategoriaConsumivel());
            ps.setString(2, consumivel.getTituloConsumivel());
            ps.setDouble(3, consumivel.getValorConsumivel());
            ps.setString(4, consumivel.getComposicaoConsumivel());
            ps.setString(5, consumivel.getPosologiaConsumivel());

            ps.setString(6, consumivel.getCodigoConsumivel());

            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar o consumível!", ex);
        }

    }

    public void salvar(Consumivel consumivel) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `consumivel`(`codigoConsumivel`, `categoriaConsumivel`, `tituloConsumivel`, `valorConsumivel`, `composicaoConsumivel`, `posologiaConsumivel`) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, consumivel.getCodigoConsumivel());
            ps.setString(2, consumivel.getCategoriaConsumivel());
            ps.setString(3, consumivel.getTituloConsumivel());
            ps.setDouble(4, consumivel.getValorConsumivel());
            ps.setString(5, consumivel.getComposicaoConsumivel());
            ps.setString(6, consumivel.getPosologiaConsumivel());

            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar o consumivel!", ex);
        }

    }

    public void deletar(int idConsumivel) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from consumivel where idConsumivel=?");
            ps.setInt(1, idConsumivel);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o consumivel!", ex);
        }
    }

    public List<Consumivel> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from consumivel");

            ResultSet rs = pst.executeQuery();

            List<Consumivel> consumiveis = new ArrayList();

            while (rs.next()) {

                Consumivel ps = new Consumivel();

                ps.setIdConsumivel(rs.getInt("idConsumivel"));
                ps.setCodigoConsumivel(rs.getString("codigoConsumivel"));
                ps.setCategoriaConsumivel(rs.getString("categoriaConsumivel"));
                ps.setTituloConsumivel(rs.getString("tituloConsumivel"));
                ps.setValorConsumivel(rs.getDouble("valorConsumivel"));
                ps.setComposicaoConsumivel(rs.getString("composicaoConsumivel"));
                ps.setPosologiaConsumivel(rs.getString("posologiaConsumivel"));

                consumiveis.add(ps);

            }

            return consumiveis;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o consumivel!", ex);
        }

    }
    
    public List<Consumivel> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps1 = null;

            if ("positivo".equals(tipo)) {

                ps1 = conexao.prepareStatement("Select * from consumivel where tituloConsumivel LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Consumivel> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps1.executeQuery();
            List<Consumivel> relatorios = new ArrayList();

            while (rs.next()) {

                Consumivel ps = new Consumivel();

                ps.setIdConsumivel(rs.getInt("idConsumivel"));
                ps.setCodigoConsumivel(rs.getString("codigoConsumivel"));
                ps.setCategoriaConsumivel(rs.getString("categoriaConsumivel"));
                ps.setTituloConsumivel(rs.getString("tituloConsumivel"));
                ps.setValorConsumivel(rs.getDouble("valorConsumivel"));
                ps.setComposicaoConsumivel(rs.getString("composicaoConsumivel"));
                ps.setPosologiaConsumivel(rs.getString("posologiaConsumivel"));

                relatorios.add(ps);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o consumível!", ex);
        }
    }

}
