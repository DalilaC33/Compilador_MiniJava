package ast.visitor;

//esta clase sirve para almacenar un error encontrado en el type check
public class Error {
	
	public Simbolo simbolo ;
	public String mensaje;

	public Error(Simbolo simbolo, String mensaje) {
		this.simbolo = simbolo;
		this.mensaje = mensaje;
		
	}
	
	public int getLine() {
		return simbolo.linea;
	}
}
