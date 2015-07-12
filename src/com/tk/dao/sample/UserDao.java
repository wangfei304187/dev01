package com.tk.dao.sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tk.dbutil.DBManager;

public class UserDao {

	public long getCount() throws SQLException {
		String sql = "SELECT COUNT(*) FROM User";
		return DBManager.getCount(sql);
	}

	public int insert(User user) throws SQLException {
		String sql = "INSERT INTO User VALUES (?, ?, ?)";
		return DBManager.executeUpdate(sql, user.getId(), user.getName(), user.getPassword());
	}

	public int update(User user) throws SQLException {
		String sql = "UPDATE User SET name = ?, password = ? WHERE id = ?";
		return DBManager.executeUpdate(sql, user.getName(), user.getPassword(), user.getId());
	}

	public int delete(String id) throws SQLException {
		String sql = "DELETE FROM User WHERE id = ?";
		return DBManager.executeUpdate(sql, id);
	}

	public User findById(String id) throws SQLException {
		String sql = "SELECT name, password FROM User WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			DBManager.setParams(pstmt, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				User u = new User();
				u.setId(rs.getLong(1));
				u.setName(rs.getString(2));
				u.setPassword(rs.getString(3));
				return u;
			}

			return null;
		}
		finally {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
	}
}
