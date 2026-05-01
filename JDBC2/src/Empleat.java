import java.time.LocalDate;

public class Empleat {
	
	private Departament departament;
	private int codi_emp;
	private String cognom;
	private String ofici;
	private LocalDate data_alta;
	private double salari;
	private double comissio;
	
	public Empleat(Departament departament, int codi_emp, String cognom, String ofici, LocalDate data_alta,
			double salari, double comissio) {
		this.departament = departament;
		this.codi_emp = codi_emp;
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
		this.departament = departament;
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
		this.ofici = ofici;
	}

	public LocalDate getData_alta() {
		return data_alta;
	}

	public void setData_alta(LocalDate data_alta) {
		this.data_alta = data_alta;
	}

	public double getSalari() {
		return salari;
	}

	public void setSalari(double salari) {
		this.salari = salari;
	}

	public double getComissio() {
		return comissio;
	}

	public void setComissio(double comissio) {
		this.comissio = comissio;
	}
	
	
	
	
}
