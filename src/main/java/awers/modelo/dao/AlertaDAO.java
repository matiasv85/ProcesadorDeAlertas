package awers.modelo.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import awers.modelo.tablas.AlertaTbl;

public class AlertaDAO {
	private DataSource dataSource;
	private String tabla;

	static Logger log = Logger.getLogger(AlertaDAO.class.getName());
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String QRY_OBTENER = "SELECT * FROM %s ";
	
	private static final String QRY_INSERT = "INSERT INTO %s (client_number, " + " telefono, "
	        + " codigo_alerta, " + "	duracion, " + "	fecha_activacion) " + "VALUES(?,?,?,?,?) ";

	private static final String QRY_DELETE = "DELETE FROM %s WHERE telefono = ? and codigo_alerta = ?";

	Connection conn = null;

	public List<AlertaTbl> obtener() {
		List<AlertaTbl> alertas = new ArrayList<AlertaTbl>();
		try {
			this.conn = this.dataSource.getConnection();

			PreparedStatement ps = this.conn.prepareStatement(this.getQueryObtener());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				alertas.add(new AlertaTbl(rs.getString("client_number"), rs.getString("telefono"),
				        rs.getString("codigo_alerta"), rs.getInt("duracion"), this.sdf.parse(rs
				                .getObject("fecha_activacion").toString())));
			}
			ps.close();

		} catch (Exception e) {
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
		return alertas;
	}

	public void guardar(AlertaTbl alerta) {
		try {
			this.conn = this.dataSource.getConnection();

			PreparedStatement ps = this.conn.prepareStatement(this.getQueryInsert());
			ps.setString(1, alerta.getClientNumber());
			ps.setString(2, alerta.getTelefono());
			ps.setString(3, alerta.getCodigoAlerta());
			ps.setInt(4, alerta.getDuracion());
			ps.setObject(5, this.sdf.format(new Date()));

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

	public void eliminarPorId(String id, String dir) {
		try {
			this.conn = this.dataSource.getConnection();
			PreparedStatement ps = this.conn.prepareStatement(this.getQueryDelete());
			ps.setString(1, id);
			ps.setString(2, dir);
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

	public String getTabla() {
		return this.tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getQueryObtener() {
		return String.format(QRY_OBTENER, this.tabla);
	}

	public String getQueryInsert() {
		return String.format(QRY_INSERT, this.tabla);
	}

	public String getQueryDelete() {
		return String.format(QRY_DELETE, this.tabla);
	}
}
