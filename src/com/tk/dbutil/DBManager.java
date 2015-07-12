package com.tk.dbutil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

public class DBManager {

	private static String url;

	public static void init(String url) {
		DBManager.url = url;
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DBManager.url);
	}

	public static void setParams(PreparedStatement pstmt, Object... params) throws SQLException {
		if((params == null) || (params.length == 0)) {
			return;
		}

		for(int i=0; i<params.length; i++) {
			Object param = params[i];
			if(param == null) {
				pstmt.setNull(i+1, Types.NULL);
			}
			else if(param instanceof Integer) {
				pstmt.setInt(i+1, (Integer)param);
			}
			else if(param instanceof String) {
				pstmt.setString(i+1, (String)param);
			}
			else if(param instanceof Double) {
				pstmt.setDouble(i+1, (Double)param);
			}
			else if(param instanceof Long) {
				pstmt.setLong(i+1, (Long)param);
			}
			else if(param instanceof Boolean) {
				pstmt.setBoolean(i+1, (Boolean)param);
			}
			else if(param instanceof Timestamp) {
				pstmt.setTimestamp(i+1, (Timestamp)param);
			}
			else if(param instanceof Date) {
				pstmt.setDate(i+1, (Date)param);
			}
		}
	}

	public static int executeUpdate(String sql) throws SQLException {
		return DBManager.executeUpdate(sql, new Object[]{});
	}

	public static int executeUpdate(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			DBManager.setParams(pstmt, params);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		finally {
			if(pstmt != null) {
				pstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
	}

	public static long getCount(String sql) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
	}
}
