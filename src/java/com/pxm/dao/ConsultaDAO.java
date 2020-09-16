package com.pxm.dao;

import com.pxm.exception.ErroSistema;
import com.pxm.model.Consulta;
import com.pxm.util.Conecxao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ConsultaDAO {

    public boolean verificar(Consulta consulta) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from consulta where codigoConsulta='" + consulta.getCodigoConsulta() + "'");

            ResultSet rs = ps.executeQuery();

            Consulta p = new Consulta();

            while (rs.next()) {
                p.setCodigoConsulta(rs.getString("codigoConsulta"));
            }

            if (p.getCodigoConsulta() != null) {

                if (p.getCodigoConsulta().equals(consulta.getCodigoConsulta())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar a consulta!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;

    }

    public void actualizar(Consulta consulta) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update consulta set categoriaConsulta=?, tituloConsulta=?, descricaoConsulta=? where codigoConsulta=?");

            ps.setString(1, consulta.getCategoriaConsulta());
            ps.setString(2, consulta.getTituloConsulta());
            ps.setString(3, consulta.getDescricaoConsulta());

            ps.setString(4, consulta.getCodigoConsulta());

            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar a consulta!", ex);
        }

    }

    public void salvar(Consulta consulta) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `consulta`(`codigoConsulta`, `categoriaConsulta`, `tituloConsulta`, `descricaoConsulta`) VALUES (?, ?, ?, ?)");

            ps.setString(1, consulta.getCodigoConsulta());
            ps.setString(2, consulta.getCategoriaConsulta());
            ps.setString(3, consulta.getTituloConsulta());
            ps.setString(4, consulta.getDescricaoConsulta());

            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar a consulta!", ex);
        }

    }

    public void deletar(int idConsulta) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from consulta where idConsulta=?");
            ps.setInt(1, idConsulta);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover a consulta!", ex);
        }
    }

    public List<Consulta> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from consulta");

            ResultSet rs = pst.executeQuery();

            List<Consulta> consultas = new ArrayList();

            while (rs.next()) {

                Consulta ps = new Consulta();

                ps.setIdConsulta(rs.getInt("idConsulta"));
                ps.setCodigoConsulta(rs.getString("codigoConsulta"));
                ps.setCategoriaConsulta(rs.getString("categoriaConsulta"));
                ps.setTituloConsulta(rs.getString("tituloConsulta"));
                ps.setDescricaoConsulta(rs.getString("descricaoConsulta"));

                consultas.add(ps);

            }

            return consultas;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar a consulta!", ex);
        }

    }

    public List<Consulta> buscarFiltro(String valor, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps1 = null;

            if ("positivo".equals(tipo)) {

                ps1 = conexao.prepareStatement("Select * from consulta where tituloConsulta LIKE '" + valor + "%'");

            } else if ("negativo".equals(tipo)) {

                List<Consulta> relatorios = new ArrayList();
                return relatorios;
            }

            ResultSet rs = ps1.executeQuery();
            List<Consulta> relatorios = new ArrayList();

            while (rs.next()) {

                Consulta ps = new Consulta();
                ps.setIdConsulta(rs.getInt("idConsulta"));
                ps.setCodigoConsulta(rs.getString("codigoConsulta"));
                ps.setCategoriaConsulta(rs.getString("categoriaConsulta"));
                ps.setTituloConsulta(rs.getString("tituloConsulta"));
                ps.setDescricaoConsulta(rs.getString("descricaoConsulta"));

                relatorios.add(ps);
            }

            return relatorios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar a consulta!", ex);
        }
    }

}
