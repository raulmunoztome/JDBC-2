import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GestorJDBC {
	
 public GestorJDBC() {
	 
 }
 
 public Set<Departament> MostrarDepartamentos(boolean ambEmpleats) throws SQLException {
	 
	 String sql;
	 Departament dep = null;
	 Map<Integer,Departament> listado = new HashMap<>();
	 Connection conex = GestorConnexions.obtenirConnexio();
     
     
	 if(ambEmpleats) {
		 sql = "SELECT D.*,E.* FROM DEPARTAMENTS D"
		 		+ " LEFT JOIN EMPLEATS E ON D.codi_dept = E.codi_dept";
	     try (Statement st = conex.createStatement(); ResultSet resultat = st.executeQuery(sql)){
				
				while (resultat.next()) {
					
					int codi_dep = resultat.getInt(1);
					if(!listado.containsKey(codi_dep)) {
						dep = new Departament(codi_dep,resultat.getString(2),resultat.getString(3));
						listado.put(codi_dep, dep);
					}
					
					if(resultat.getObject(4) != null){

					Empleat emp = new Empleat(listado.get(codi_dep),resultat.getInt(4),resultat.getString(5),resultat.getString(6),
							resultat.getDate(7).toLocalDate(),resultat.getDouble(8),resultat.getDouble(9));
					listado.get(codi_dep).addEmpleado(emp);
					}
					
				}
				
				return new HashSet<>(listado.values());
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());
				return null;
			}
	     
	 }
	 
		 sql = "SELECT * FROM DEPARTAMENTS";
		 Set<Departament> departamentos = new HashSet<>();
		 
	     try (Statement st = conex.createStatement(); ResultSet resultat = st.executeQuery(sql)){
				
				while (resultat.next()) {
					dep = new Departament(resultat.getInt(1),resultat.getString(2),resultat.getString(3));
					departamentos.add(dep);
				}
				return departamentos;
			} catch (SQLException e) {

				System.out.println(e.getMessage());
				return null;
			}
	     
	 }
     
 }

