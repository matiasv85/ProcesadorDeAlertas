package awers.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import awers.modelo.tablas.Logmdi;

public class LogmdiDAO {
	private static final Logger log = Logger.getLogger(LogmdiDAO.class);
	private static final String TABLA = "logmdi";
	private static final String CODIGO_ALERTA = "panico";
	
	private DataSource dataSource;
	private Connection conn = null;
	
	private static String QRY_INSERT = "INSERT INTO "+TABLA+"("
			+ "telefono, "
	        + " accion) VALUES("
	        + "?,"
	        + "?) ";
	
	public void guardar(Logmdi logmdi) {
		try {
			this.conn = this.dataSource.getConnection();

			PreparedStatement ps = this.conn.prepareStatement(QRY_INSERT);
			ps.setString(1, logmdi.getTelefono());
			ps.setString(2, CODIGO_ALERTA);
			
			ps.execute();

		} catch (SQLException e) {
			log.error(e.getMessage());

		} finally {
			if (this.conn != null) {
				try {
					this.conn.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}

		}
	}
	
	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
