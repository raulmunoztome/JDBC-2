import java.sql.Connection;
import java.sql.PreparedStatement;
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
 
 public Set<Departament> MostrarDepartamentos(boolean ambEmpleats) throws Exception, SQLException {
	 
	 String sql;
	 Departament dep = null;
	 Map<Integer,Departament> listado;
	 Connection conex = GestorConnexions.obtenirConnexio();
     
     
	 if(ambEmpleats) {
		 
		 listado = new HashMap<>();
		 
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
			} 
	     
	 }
 
 	public int addDepartamento(Departament dep) throws SQLException {
 		Connection con = GestorConnexions.obtenirConnexio();
    	
    	String sql = "INSERT INTO departaments VALUES (?,?,?)";
    	PreparedStatement ps = con.prepareStatement(sql);
    	
    	ps.setInt(1, dep.getCodi_dept());
    	ps.setString(2, dep.getNom());
    	ps.setString(3, dep.getCiutat());
    	
    	return ps.executeUpdate();
 	}
 	
 	public int addEmpleat(Empleat emp)throws SQLException {
 		Connection con = GestorConnexions.obtenirConnexio();
    	
    	String sql = "INSERT INTO empleats (codi_emp,cognom,ofici,data_alta,salari,comissio,codi_dept) VALUES (?,?,?,?,?,?,?)";
    	
    	try(PreparedStatement ps = con.prepareStatement(sql)){
    		
	    	ps.setInt(1, emp.getCodi_emp());
	    	ps.setString(2, emp.getCognom());
	    	ps.setString(3, emp.getOfici());
	    	ps.setDate(4,java.sql.Date.valueOf(emp.getData_alta()));
	    	ps.setDouble(5, emp.getSalari());
	    	ps.setDouble(6, emp.getComissio());
	    	ps.setInt(7, emp.getDepartament().getCodi_dept());
	    	
	    	return ps.executeUpdate();
    	}
 	}
 	
 	
 	public int deleteDepartamento(Departament dep) throws SQLException{
 		
 		Connection con = GestorConnexions.obtenirConnexio();
 		String sql = "DELETE FROM departaments "
 				+ "WHERE codi_dept = ?";
 		
 		try(PreparedStatement prepared = con.prepareStatement(sql)){
 			
	 		prepared.setInt(1,dep.getCodi_dept());
	 		
	 		return prepared.executeUpdate();
 		}
 	}
 	
 	public int deleteEmpleat(Empleat emp) throws SQLException{
 		
 		Connection con = GestorConnexions.obtenirConnexio();
 		String sql = "DELETE FROM EMPLEATS "
 				+ "WHERE codi_emp = ?";
 		
 		try(PreparedStatement prepared = con.prepareStatement(sql)){
 			
	 		prepared.setInt(1,emp.getCodi_emp());
	 		
	 		return prepared.executeUpdate();
 		}
 	}
 	
 	public Departament consultaDepartamentPerCodi(int codiD) throws SQLException {
 		
 		Connection con = GestorConnexions.obtenirConnexio();
 		String sql = "SELECT * FROM DEPARTAMENTS "
 				+ "WHERE codi_dept = ?";
 		
 		try(PreparedStatement preparo = con.prepareStatement(sql)){
 			
	 		preparo.setInt(1,codiD);
	 		ResultSet recibido = preparo.executeQuery();
 		
	 		if(recibido.next()) return (new Departament(recibido.getInt(1),recibido.getString(2),recibido.getString(3)));
	 		
	 		return null;
 		}
 				
 	}
 	
 	public int aumentarSalarioPorDepartamento(Departament dep, double porcentaje) throws SQLException {
 		Connection con = GestorConnexions.obtenirConnexio();
 		
 		String sql = "UPDATE EMPLEATS SET salari = salari * ? "
 				+ "WHERE codi_dept = ?";
 		
 		if(porcentaje < 0) return 0;
 		
 		try(PreparedStatement prep = con.prepareStatement(sql)){
 			
	 		prep.setDouble(1, porcentaje);
	 		prep.setInt(2, dep.getCodi_dept());
	 		
	 		return prep.executeUpdate();
 		}
 	}
 	
 	public int modificarComisionPorCodi(int codi_em, double com) throws SQLException {
 		Connection con = GestorConnexions.obtenirConnexio();
 		String sql = "UPDATE EMPLEATS SET comissio = ? WHERE codi_emp = ?";
 		
 		if(codi_em == 0 || com < 0)return 0;
 		
 		try(PreparedStatement prepared = con.prepareStatement(sql)){
 			
	 		prepared.setDouble(1, com);
	 		prepared.setInt(2, codi_em);
	 		
	 		return prepared.executeUpdate();
 		}
 	}
 	     
 }

