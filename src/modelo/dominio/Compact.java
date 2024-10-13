package modelo.dominio;

public class Compact {
	private long entrada;
	private long salida;
	
	public Compact(long entrada, long salida) {
		super();
		this.entrada = entrada;
		this.salida = salida;
	}

	public long getEntrada() {
		return entrada;
	}

	public void setEntrada(long entrada) {
		this.entrada = entrada;
	}

	public long getSalida() {
		return salida;
	}

	public void setSalida(long salida) {
		this.salida = salida;
	}
	
	public void sumarEntradas() {
		this.entrada = entrada++;
	}
	public void sumarSalidas() {
		this.salida = salida++;
	}
	
	public boolean flagCompact() {
		long warningFlag=50;
		long flag=entrada/salida*1;
		if(flag>=warningFlag){
			return true;
		}
		return false;
	}
	
	
}
