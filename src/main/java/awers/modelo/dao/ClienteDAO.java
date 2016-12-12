package awers.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import awers.modelo.tablas.Cliente;


public class ClienteDAO {
	private DataSource dataSource;
	private static String TABLA = "contactos";
	
	static Logger log = Logger.getLogger(ClienteDAO.class.getName());
	
	@SuppressWarnings("unused")
	private static String QRY_OBTENER = "SELECT * "
			+"FROM "+TABLA+" "
			+"WHERE telefono = ? ";
	
	private static String QRY_OBTENER_ACTIVO = "SELECT * "
			+"FROM "+TABLA+" "
			+"WHERE telefono = ? "
			+ "AND EvtType4 = ? ";
	
	Connection conn = null;
	
	public Cliente obtener(String telefono, String dir){
		Cliente cliente = null;
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(QRY_OBTENER_ACTIVO);
			
			ps.setString(1, telefono);
			ps.setString(2, dir);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cliente = new Cliente(rs.getString("nombre"),
						rs.getString("numero_cuenta"),
						rs.getString("telefono"),
						Integer.valueOf(rs.getString("EvtType5")),
						dir);
				
				
			}
			ps.close();
 
		} catch (SQLException e) {
			log.error(e.getMessage());
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
			
		}
		return cliente;
	}
	
	
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
