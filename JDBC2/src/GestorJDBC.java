import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorJDBC {
	
 public GestorJDBC() {
	 
 }
 
 public List<Departament> MostrarDepartamentos() throws SQLException {
	 
	 String sql = "SELECT * FROM DEPARTAMENTS";
	 List<Departament> departamentos = new ArrayList<>();
	 Connection conex = GestorConnexions.obtenirConnexio();
     Statement st = conex.createStatement();
     
     try (ResultSet resultat = st.executeQuery(sql)){
			
			while (resultat.next()) {
				Departament dep = new Departament(resultat.getInt(1),resultat.getString(2),resultat.getString(3));
				departamentos.add(dep);
			}
			return departamentos;
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return null;
		}
     
 }
}
