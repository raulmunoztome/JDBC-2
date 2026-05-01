import java.util.Scanner;

public class Consultas {

	public static void main(String[] args) {
		
		GestorJDBC gestor = new GestorJDBC();
		Scanner sc = new Scanner(System.in);

		
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
			case 0:
				break;
				
			case 1:
				break;
				
			case 2:
				break;
				
			case 3:
				break;
				
			case 4:
				break;
				
			case 5:
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
		System.out.println("0 -> SALIR");
		
	}

}
