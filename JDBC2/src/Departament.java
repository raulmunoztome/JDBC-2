import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Departament {
	private List<Empleat> empleados;
	private int codi_dept;
	private String nom;
	private String ciutat;
	
	public Departament(int codi_dept, String nom, String ciutat) {

		this.codi_dept = codi_dept;
		this.nom = nom;
		this.ciutat = ciutat;
		this.empleados = new ArrayList<>();
	}
	

	public String getNom() {
		return nom;
	}
	


	public List<Empleat> getEmpleados() {
		return empleados;
	}


	public boolean addEmpleado(Empleat empleado) {
		if(empleado != null) return empleados.add(empleado);
		return false;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getCiutat() {
		return ciutat;
	}


	public void setCiutat(String ciutat) {
		this.ciutat = ciutat;
	}


	public int getCodi_dept() {
		return codi_dept;
	}


	@Override
	public int hashCode() {

		return Objects.hash(codi_dept);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Departament other = (Departament) obj;

		return Objects.equals(codi_dept, other.codi_dept);

	}


	@Override
	public String toString() {
		return "Departament [codi_dept=" + codi_dept + ", nom=" + nom + ", ciutat=" + ciutat + ", Empleados= "+empleados.size()+"]";
	}
	
	
}
