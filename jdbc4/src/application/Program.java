package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;

public class Program {

    // ATIVIDADE: O PROGRAMA CONECTA-SE AO BANCO DE DADOS E ATUALIZA O SALÁRIO BASE
    // DE TODOS OS VENDEDORES DO DEPARTAMENTO ESPECÍFICO ADICIONANDO UM VALOR FIXO AO SALÁRIO.
	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement st = null;
		try {
			// OBTÉM UMA CONEXÃO COM O BANCO DE DADOS
			conn = DB.getConnection();
	
			// PREPARA UMA DECLARAÇÃO SQL PARA ATUALIZAR O SALÁRIO BASE DOS VENDEDORES DO DEPARTAMENTO ESPECÍFICO
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET BaseSalary = BaseSalary + ? "
					+ "WHERE "
					+ "(DepartmentId = ?)");

			// DEFINE OS VALORES DOS PARÂMETROS DA DECLARAÇÃO
			st.setDouble(4, 200.0); // VALOR A SER ADICIONADO AO SALÁRIO BASE
			st.setInt(5, 2); // ID DO DEPARTAMENTO QUE SERÁ ATUALIZADO
			
			// EXECUTA A DECLARAÇÃO DE ATUALIZAÇÃO E RETORNA O NÚMERO DE LINHAS AFETADAS
			int rowsAffected = st.executeUpdate();
			
			// EXIBE A QUANTIDADE DE LINHAS ATUALIZADAS NO CONSOLE
			System.out.println("Done! Rows affected: " + rowsAffected);
		}
		catch (SQLException e) {
			e.printStackTrace(); // IMPRIME A PILHA DE ERROS DE SQL
		}  
		finally {
			// FECHA O PREPARED STATEMENT E A CONEXÃO COM O BANCO DE DADOS
			DB.closeStatement(st);
			DB.closeConnection();
		}
	} 
}
