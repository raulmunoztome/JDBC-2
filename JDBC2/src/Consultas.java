import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Consultas {

	public static void main(String[] args) {
		
		GestorJDBC gestor = new GestorJDBC();
		
		Scanner sc = new Scanner(System.in);
		
		Set<Departament> listado_resultado = new HashSet<>();

		
		String opcion = "";
		int filtro = 100;
		while(!opcion.equals("0")) {
			pintarMenu();
			opcion = sc.nextLine();
			
			try {
				filtro = Integer.parseInt(opcion);
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}
			
			switch(filtro) {
				
			case 1:
				try 
				{
					listado_resultado = gestor.MostrarDepartamentos(false);

				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
				for(Departament d : listado_resultado) {
					System.out.println(d);
				}
				break;
				
			case 2:
				try 
				{
					listado_resultado = gestor.MostrarDepartamentos(true);
					
				} catch (SQLException e) {
					
					System.out.println(e.getMessage());
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
				for(Departament d : listado_resultado) {
					
					System.out.println(d);
					
					for(Empleat e : d.getEmpleados()) {
						
						System.out.println(e);
					}
				}
				break;
				
			case 3:
				System.out.println("indique el nombre del departamento:");
				String nomD = sc.nextLine();
				System.out.println("indique la ciudad donde está:");
				String ciudad = sc.nextLine();
				System.out.println("Indique el código del departamento: ");
			
				try {
					int codi_dep = Integer.parseInt(sc.nextLine());
					Departament dept = new Departament(codi_dep,nomD,ciudad);
					int respuesta = gestor.addDepartamento(dept);
					
					if(respuesta>0)System.out.println("Departamento añadido");
					else if(respuesta == -1062) System.out.println("Departamento duplicado");
					else System.out.println("error al introducir el departamento");
					
				} catch (NumberFormatException e) {
					
					System.out.println("Error en el codi: "+e.getMessage());
					
				}catch (SQLException e) {
					
					System.out.println("Error en la bd[ "+e.getErrorCode()+" ]: "+e.getMessage());
				}
				
				break;
				
			case 4:
				System.out.println("Indique el ID del departamento: ");
				try {
					int codi_dep = Integer.parseInt(sc.nextLine());
					
					int respuesta = gestor.deleteDepartamento(new Departament(codi_dep,"no-importa","esto-tampoco"));
					
					if(respuesta == 0)System.out.println("No se ha encontrado ese departamento con ese ID");
					else if(respuesta == 1)System.out.println("Se ha eliminado el departamento");
					else System.out.println("Se ha borrado mas de un registro");
					
				} catch (NumberFormatException e) {
					
					System.out.println(e.getMessage());
				} catch (SQLException e) {
					
					if (e.getErrorCode() == 1451) {
					        System.out.println("No se puede borrar: Este departamento tiene empleados asignados.");
					}
					else System.out.println("error -> "+e.getErrorCode());
				}
				break;
				
			case 5:
				System.out.println("Introduce el ID del empleado: ");
				
				try {
					
					int codi_e =Integer.parseInt(sc.nextLine()); 
					//departamento y clientes "fantasmas" para borrarlo con el campo que realmente los identifica
					Departament x = new Departament(9999,"nada","no-importa");
					int respuesta = gestor.deleteEmpleat(new Empleat(x,codi_e,"persona","trabajo",LocalDate.now(),0,0));
					
					if(respuesta == 0)System.out.println("No se ha encontrado trabajador con ese ID");
					else if(respuesta == 1)System.out.println("Se ha eliminado el trabajador");
					else System.out.println("Se ha borrado mas de un registro");
					
				} catch (SQLException e) {
					
					System.out.println("error -> "+e.getErrorCode());
				}catch(Exception e) {
					
					System.out.println(e.getMessage());
				}
				
				break;
			case 6:
				try {
					System.out.println("Introduce el ID del empleado: ");
					int codi_e =Integer.parseInt(sc.nextLine()); 
					System.out.println("introduce el codigo del departamento al cual pertenece: ");
					int codi_d = Integer.parseInt(sc.nextLine());
					System.out.println("Introduce el apellido: ");
					String apellido = sc.nextLine();
					System.out.println("introduce su oficio: ");
					String oficio = sc.nextLine();
					System.out.println("Introduce su salario:");
					int salario = Integer.parseInt(sc.nextLine());
					System.out.println("Introduce su comision, 0 si no tiene.");
					int comision = Integer.parseInt(sc.nextLine());
					
					Departament seleccionado = gestor.consultaDepartamentPerCodi(codi_d);
					if(seleccionado != null) {
						Empleat guardar = new Empleat(seleccionado, codi_e,apellido,oficio,LocalDate.now(),salario,comision);
						
						int respuesta = gestor.addEmpleat(guardar);
						
						if(respuesta == 0)System.out.println("No se ha añadido nada");
						else System.out.println("Se ha añadido el trabajador");
					}
					else System.out.println("id de departamento no válido");
				} catch (SQLException e) {
					
					System.out.println("error -> "+e.getErrorCode());
				}catch(Exception e) {
					
					System.out.println(e.getMessage());
				}
				
				break;
			case 7:
				
				break;
			case 8:
				
				break;
				
			default:
				break;
			}
		}
		System.out.println("Adios!\n");
		GestorConnexions.tancarConnexio();
		
		
		
		
	}
	
	public static void pintarMenu() {
		System.out.println("indique el número de la opción:\n");
		System.out.println("1 -> Mostrar Departamentos.");
		System.out.println("2 -> Mostrar Empleados.");
		System.out.println("3 -> Añadir un departamento a la BD.");
		System.out.println("4 -> Eliminar un departamento de la BD.");
		System.out.println("5 -> Eliminar un empleado de la BD.");
		System.out.println("6 -> Añadir empleado a la BD. IMPLEMENTADO HASTA AQUI" );
		System.out.println("7 -> Aumentar salario a los trabajadores de un departamento. en %");
		System.out.println("8 -> Modificar la comisión de un empleado.");
		System.out.println("0 -> SALIR");
		
	}

}
