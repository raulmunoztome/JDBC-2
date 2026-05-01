import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Empleat {
	
	private Departament departament;
	private int codi_emp;
	private String cognom;
	private String ofici;
	private LocalDate data_alta;
	private double salari;
	private double comissio;
	
	private static final DateTimeFormatter FORMATEADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public Empleat(Departament departament, int codi_emp, String cognom, String ofici, LocalDate data_alta,
			double salari, double comissio) throws Exception {
		
		if(data_alta.atStartOfDay().isAfter(LocalDateTime.now())) throw new Exception("error fecha: "+data_alta);
		if(departament == null) throw new Exception("Departamento necesario");
		this.departament = departament;
		
		if(codi_emp < 0) throw new Exception("Codigo erroneo, no puede ser negativo");
		this.codi_emp = codi_emp;
		
		if(cognom.isBlank() || ofici.isBlank() || salari < 0) throw new Exception("error en los datos introducidos del empleado");
		this.cognom = cognom;
		this.ofici = ofici;
		this.data_alta = data_alta;
		this.salari = salari;
		this.comissio = comissio;
	}

	public Departament getDepartament() {
		return departament;
	}

	public void setDepartament(Departament departament) {
		if(departament != null) this.departament = departament;
	}

	public int getCodi_emp() {
		return codi_emp;
	}


	public String getCognom() {
		return cognom;
	}

	public void setCognom(String cognom) {
		this.cognom = cognom;
	}

	public String getOfici() {
		return ofici;
	}

	public void setOfici(String ofici) {
		if(!ofici.isBlank()) this.ofici = ofici;
	}

	public LocalDate getData_alta() {
		return data_alta;
	}

	public void setData_alta(LocalDate data_alta) {
		
		if(data_alta.atStartOfDay().isBefore(LocalDateTime.now())) this.data_alta = data_alta;
	}

	public double getSalari() {
		return salari;
	}

	public void setSalari(double salari) {
		
		if(salari > 0) this.salari = salari;
	}

	public double getComissio() {
		return comissio;
	}

	public void setComissio(double comissio) {
		
		if (comissio >= 0) this.comissio = comissio;
	}

	@Override
	public String toString() {
		return "Empleat [departament=" + departament.getNom() + ", codi_emp=" + codi_emp + ", cognom=" + cognom + ", ofici="
				+ ofici + ", data_alta=" + data_alta.format(FORMATEADOR) + ", salari=" + salari + ", comissio=" + comissio + "]";
	}
	
	
	
	
}
