package com.pxm.util;

import com.pxm.exception.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conecxao {
    
    private static Connection conexao;
    
    // MAC: /Users/cmaemo/Desktop/Proxmeru/Repositórios/extraHospitalar/db
    // WIN: E:\\db_extra_hospitalar.db
    
    private static String URL_CONEXAO = "jdbc:sqlite:/Users/cmaemo/Desktop/Proxmeru/Repositórios/extraHospitalar/db/db_extra_hospitalar.db";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    public String db = URL_CONEXAO;
    
    public static Connection getConexao() throws ErroSistema {
        if(conexao == null) {
            
            try {
                Class.forName("org.sqlite.JDBC");
                conexao = DriverManager.getConnection(URL_CONEXAO);
            } catch (ClassNotFoundException ex) {
                throw new ErroSistema("O drive de banco de dados não foi encontrado!", ex);
            } catch (SQLException ex) {
                throw new ErroSistema("Não foi possível conectar ao base de dados!", ex);
            }
        }
        return conexao;
    }
    
    public static void fecharConexao() throws ErroSistema {
        if(conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao fechar conexão com a base de dados!", ex);
            }
        }
    }

    public static String getURL_CONEXAO() {
        return URL_CONEXAO;
    }
    
}
