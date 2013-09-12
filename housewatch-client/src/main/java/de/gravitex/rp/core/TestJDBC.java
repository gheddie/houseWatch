package de.gravitex.rp.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class TestJDBC {
	
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://localhost/postgres";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWD = "pgvedder";

	public static void main(String[] argv) {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// Select fitting database driver and connect:
			Class.forName(DB_DRIVER);
			cn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
			st = cn.createStatement();
			rs = st.executeQuery("select * from accountcurrent");
			// Get meta data:
			ResultSetMetaData rsmd = rs.getMetaData();
			int i, n = rsmd.getColumnCount();
			// Print table content:
			for (i = 0; i < n; i++)
				System.out.print("+---------------");
			System.out.println("+");
			for (i = 1; i <= n; i++)
				// Attention: first column with 1 instead of 0
				System.out.print("| "
						+ extendStringTo14(rsmd.getColumnName(i)));
			System.out.println("|");
			for (i = 0; i < n; i++)
				System.out.print("+---------------");
			System.out.println("+");
			while (rs.next()) {
				for (i = 1; i <= n; i++)
					// Attention: first column with 1 instead of 0
					System.out.print("| "
							+ extendStringTo14(rs.getString(i)));
				System.out.println("|");
			}
			for (i = 0; i < n; i++)
				System.out.print("+---------------");
			System.out.println("+");
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				if (null != rs)
					rs.close();
			} catch (Exception ex) {
			}
			try {
				if (null != st)
					st.close();
			} catch (Exception ex) {
			}
			try {
				if (null != cn)
					cn.close();
			} catch (Exception ex) {
			}
		}
	}

	// Extend String to length of 14 characters
	private static final String extendStringTo14(String s) {
		if (null == s)
			s = "";
		final String sFillStrWithWantLen = "              ";
		final int iWantLen = sFillStrWithWantLen.length();
		final int iActLen = s.length();
		if (iActLen < iWantLen)
			return (s + sFillStrWithWantLen).substring(0, iWantLen);
		if (iActLen > 2 * iWantLen)
			return s.substring(0, 2 * iWantLen);
		return s;
	}
}