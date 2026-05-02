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
				//MOSTRAR DEPARTAMENTOS
				try {
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
				//MOSTRAR LOS TRABAJADORES DE LOS DEPARTAMENTOS
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
				//CREAR DEPARTAMENTO Y AÑADIR A BD
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
				//ELIMINAR DEPARTAMENTO DE LA BD
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
				//ELIMINAR EMPLEADO
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
				//AÑADIR EMPLEADO A LA BD
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
				//AUMENTO DE SALARIO A DEPT
				System.out.println("indica el código del departamento: ");
				try {
					int codi_d = Integer.parseInt(sc.nextLine());
					System.out.println("indica el porcentaje de aumento de salario (min 1 - max 100):");
					double cantidad = Double.parseDouble(sc.nextLine());
					
					Departament filtrado = gestor.consultaDepartamentPerCodi(codi_d);
					
					if(filtrado != null) {
						int empModificados = gestor.aumentarSalarioPorDepartamento(filtrado, (cantidad/100.0)+1);
						
						if(empModificados > 0) System.out.println("Se ha subido el sueldo a "+empModificados+" empleados de:\n"+filtrado);
						else if(empModificados == 0) System.out.println("no se ha modificado ni 1 salario");
					}
					else System.out.println("Departamento no encontrado");
					
				} catch (NumberFormatException e) {
					
					System.out.println(e.getMessage());
				}catch (SQLException e) {
					
					System.out.println(e.getMessage());
				}
				
				break;
			case 8:
				//AUMENTO DE COMISION A EMP
				System.out.println("Introduce el ID del empleado: ");
				
				try {
					
					int id_emp = Integer.parseInt(sc.nextLine());
					System.out.println("introduce la nueva comisión del empleado "+id_emp);
					double com = Double.parseDouble(sc.nextLine());
					int result = gestor.modificarComisionPorCodi(id_emp, com);
					
					if(result == 1) System.out.println("Se ha modificado la comision de id: '"+id_emp+"' a "+com+"€");
					else if(result == 0) System.out.println("No se ha modificado ni 1 comisión.");
					
				} catch (NumberFormatException e) {
					
					System.out.println(e.getMessage());
					
				} catch (SQLException e) {
					
					System.out.println(e.getMessage());
					
				}
				
				break;
				
			default:
				break;
			}
			//despues de terminar cada opcion se cierra la conexion y no se deja abierta si el usuario no la vuelve a utilizar
			GestorConnexions.tancarConnexio();
		}
		System.out.println("Adios!\n");
		
		
		sc.close();
		
		
		
		
	}
	
	public static void pintarMenu() {
		System.out.println("indique el número de la opción:\n");
		System.out.println("1 -> Mostrar Departamentos.");
		System.out.println("2 -> Mostrar Empleados.");
		System.out.println("3 -> Añadir un departamento a la BD.");
		System.out.println("4 -> Eliminar un departamento de la BD.");
		System.out.println("5 -> Eliminar un empleado de la BD.");
		System.out.println("6 -> Añadir empleado a la BD." );
		System.out.println("7 -> Aumentar salario a los trabajadores de un departamento. en %");
		System.out.println("8 -> Modificar la comisión de un empleado.");
		System.out.println("0 -> SALIR");
		
	}

}
