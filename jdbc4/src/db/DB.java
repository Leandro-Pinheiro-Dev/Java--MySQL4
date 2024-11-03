package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

    // VARIÁVEL DE CONEXÃO ESTÁTICA USADA PARA GERENCIAR A CONEXÃO COM O BANCO DE DADOS
    private static Connection conn = null;
	
    // MÉTODO PARA ESTABELECER E RETORNAR A CONEXÃO COM O BANCO DE DADOS
    public static Connection getConnection() {
        // VERIFICA SE A CONEXÃO JÁ FOI ESTABELECIDA
        if (conn == null) {
            try {
                // CARREGA AS PROPRIEDADES DO BANCO DE DADOS A PARTIR DO ARQUIVO
                Properties props = loadProperties();
                // OBTÉM A URL DO BANCO A PARTIR DAS PROPRIEDADES
                String url = props.getProperty("dburl");
                // ESTABELECE A CONEXÃO COM O BANCO USANDO A URL E AS PROPRIEDADES
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                // LANÇA UMA EXCEÇÃO PERSONALIZADA EM CASO DE ERRO NA CONEXÃO
                throw new DbException(e.getMessage());
            }
        }
        return conn; // RETORNA A CONEXÃO
    }
	
    // MÉTODO PARA FECHAR A CONEXÃO COM O BANCO DE DADOS
    public static void closeConnection() {
        if (conn != null) { // VERIFICA SE A CONEXÃO ESTÁ ABERTA
            try {
                conn.close(); // FECHA A CONEXÃO
            } catch (SQLException e) {
                throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO EM CASO DE ERRO AO FECHAR A CONEXÃO
            }
        }
    }
	
    // MÉTODO PRIVADO PARA CARREGAR AS PROPRIEDADES DO BANCO DE DADOS A PARTIR DO ARQUIVO "db.properties"
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties(); // INICIALIZA O OBJETO PROPRIEDADES
            props.load(fs); // CARREGA AS PROPRIEDADES DO ARQUIVO
            return props; // RETORNA AS PROPRIEDADES CARREGADAS
        } catch (IOException e) {
            throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO EM CASO DE ERRO DE I/O
        }
    }
	
    // MÉTODO PARA FECHAR UM OBJETO STATEMENT
    public static void closeStatement(Statement st) {
        if (st != null) { // VERIFICA SE O STATEMENT NÃO É NULO
            try {
                st.close(); // FECHA O STATEMENT
            } catch (SQLException e) {
                throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO EM CASO DE ERRO AO FECHAR O STATEMENT
            }
        }
    }

    // MÉTODO PARA FECHAR UM OBJETO RESULTSET
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) { // VERIFICA SE O RESULTSET NÃO É NULO
            try {
                rs.close(); // FECHA O RESULTSET
            } catch (SQLException e) {
                throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO EM CASO DE ERRO AO FECHAR O RESULTSET
            }
        }
    }
}
