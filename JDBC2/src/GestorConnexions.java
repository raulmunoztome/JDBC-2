import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorConnexions {

	private static Connection connexio = null;

	private static int connectar() {
		//elegir si utilizo Windows o Linux
		//String url = "jdbc:mysql://localhost/bd_empleats";
		//String user = "cfgs";
		//String pwd = "sol19";
		String url = "jdbc:mysql://localhost/bd_empleats";
		String user = "root";
		String pwd = "";

		try {
			connexio = DriverManager.getConnection(url, user, pwd);
			return 0;
		} catch (SQLException e) {
			
			System.out.println("ERROR: " + e.getMessage());
			
			return -1;
		}
	}

	public static boolean isConnected() throws SQLException {
		if (connexio == null || connexio.isClosed()) {
			return false;
		} else {
			return true;
		}

	}

	public static Connection obtenirConnexio() {
		try {
			if (!isConnected()) {
				connectar();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connexio;
	}

	public static void tancarConnexio() {
		try {
			if (connexio != null) {
				connexio.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}