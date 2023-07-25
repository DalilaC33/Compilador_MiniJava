package ast.visitor;

import java.util.HashMap;
import java.util.Map;

import ast.And;
import ast.ArrayAssign;
import ast.ArrayLength;
import ast.ArrayLookup;
import ast.Assign;
import ast.Block;
import ast.BooleanType;
import ast.Call;
import ast.ClassDeclExtends;
import ast.ClassDeclSimple;
import ast.ClassType;
import ast.Expr;
import ast.False;
import ast.Goal;
import ast.Identifier;
import ast.IdentifierExpr;
import ast.If;
import ast.IntArrayType;
import ast.IntType;
import ast.IntegerLiteral;
import ast.LessThan;
import ast.MainClass;
import ast.MethodDecl;
import ast.Minus;
import ast.Mult;
import ast.NewArray;
import ast.NewObject;
import ast.Not;
import ast.Param;
import ast.Plus;
import ast.Print;
import ast.This;
import ast.True;
import ast.Type;
import ast.VarDecl;
import ast.While;

public class VisitTCH implements Visitor {
	
	//esta clase recorre la lista de simbolos y busca los errores en
	//las sentencias, si las encuentra las guarda en la lista de errores
	HashMap<Identifier, Simbolo> listaSimbolos = new HashMap<>();
	HashMap<Identifier, Simbolo> listaParametrosActual = new HashMap<>();
	HashMap<Integer, Error> errores = new HashMap<>();
	HashMap<Identifier, Simbolo> listaClases ;
	HashMap<Identifier, Simbolo> listaVariables;
	HashMap<Identifier, Simbolo> listaMetodos;
	HashMap<Identifier, Simbolo> listaParametros;
	HashMap<Identifier, Simbolo> listaAsignaciones;
	HashMap<Identifier, Simbolo> listaActual;
	Simbolo claseActual;
	Simbolo simboloActual;
	Simbolo identActual;
	
	
	public VisitTCH(HashMap<Identifier, Simbolo> listaSimbolos) {
		this.listaSimbolos = listaSimbolos;
		filtrarClases();
	}

	@Override
	public void visit(Goal n) {
		visit(n.m);
		for (int i = 0; i < n.cl.size(); i++) {
			System.out.println();
			visit(n.cl.get(i));
		}
		printErrores();
	}

	@Override
	public HashMap<Identifier, Simbolo> getlista() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visit(MainClass n) {
		visit(n.s);		
	}

	@Override
	public void visit(ClassDeclSimple n) {
		//obtengo la clase en la que me encuentro de la tabla de simbolos 
		claseActual=getSimbolo(n.i);
		simboloActual = claseActual;
		listaActual = listaClases;
		visit(n.i);

		//guardo las variables de la clase
		filtrarVariables(simboloActual);
		
		for (int i = 0; i < n.vl.size(); i++) {
			Simbolo variable = getSimbolo(n.vl.get(i).i);
			simboloActual = variable;
			visit(n.vl.get(i));
		}	
		
		//guardo los metodos y parametros de la clase en la que estoy
		filtrarMetodos();	
		filtrarParametros();
		for (int i = 0; i < n.ml.size(); i++) {
		    Simbolo metodo = getSimbolo(n.ml.get(i).i);
		    simboloActual = metodo;
		    listaActual = listaMetodos;
		    visit(n.ml.get(i));
		    
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassDeclExtends n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDecl n) {
		visit(n.i);	
	}

	@Override
	public void visit(MethodDecl n) {
		listaParametrosActual = new HashMap<>();
		listaAsignaciones =  new HashMap<>();
		visit(n.t);
		
		//obtengo los parametros de el metodo si es que hay
		for (int i = 0; i < n.fl.size(); i++) {
			Simbolo parametro = getSimbolo(n.fl.get(i).i);
			listaParametrosActual.put(n.fl.get(i).i, parametro);
			visit(n.fl.get(i));
			
		}
		
		//verifico si se repiten los nombres
		if(elementosRepetidos(listaParametrosActual)) {
			Error e = new Error(null,"el nombre de los parametros no pueden ser iguales, error en linea "+n.fl.line);
			addListError(e, n.line);
		}
		visit(n.i);
		 	
		
		for (int i = 0; i < n.vl.size(); i++) {			
			visit(n.vl.get(i));			
		}
		Simbolo metodo = listaSimbolos.get(n.i);
		simboloActual = metodo;
		
		//verifico si las variables que uso son correctas
		filtrarVariables(simboloActual);
		for (int i = 0; i < n.sl.size(); i++) {
			visit(n.sl.get(i));
			
		}
		filtrarVariables(metodo);		
		visit(n.e);
		
		//verifico si el retorno del metodo es correcto
		if(!expEsUn(n.e).equals(n.t.getClass().getName())) {
			Error e = new Error(null,"la expresion del retorno en la "+n.line+" debe ser tipo "+n.t.getClass().getName());
			addListError(e, n.line);
		}
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Param n) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntArrayType n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BooleanType n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntType n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassType n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Block n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(If n) {
		//me aseguro que se evalue un boolean
		if(!(esUn(n.e).equals("ast.BooleanType"))) {
			Error e = new Error(null,"la expresion del if en la linea "+n.line+" debe ser tipo boolean"+esUn(n.e));
			addListError(e, n.line);
		}
		visit(n.e);
		visit(n.s1);
		visit(n.s2);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(While n) {
		if(!(esUn(n.e).equals("ast.BooleanType"))) {
			Error e = new Error(null,"la expresion en el while de la linea "+n.line+" debe ser tipo boolean"+esUn(n.e));
			addListError(e, n.line);
		}
		visit(n.e);
		visit(n.s);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Print n) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Assign n) {	
		
		//verifico que existe esta variable
		Simbolo variable = buscarEnLista(listaActual,n.i.s,n.line,esUn(n.e));
		if(variable != null) {
			listaAsignaciones.put(n.i, variable);
			
			//si es un tipo classType obtengo su clase
			String tipo = variable.tipoDato.getClass().getName();				
			if(variable.tipoDato.getClass().getName().equals("ast.ClassType")) {
				ClassType ct = (ClassType)variable.tipoDato;
				tipo = ct.className;
			}
			
		    //verifico que se asigne un dato de tipo correcto
			if(!(expEsUn(n.e).equals(tipo))){
				Error e = new Error(variable,"la expresion de la linea "+n.line+" es incorrecto para el tipo de dato "+ variable.tipoDato.getClass().getName());
				addListError(e, n.line);
			}
		}
			visit(n.e);	
	}

	//funciona igual que el assign pero verifica que el index sea un int
	@Override
	public void visit(ArrayAssign n) {
		if(!(expEsUn(n.e1).equals("ast.IntType"))) {
			Error e = new Error(null,"la expresion de la linea "+n.line+" es incorrecto, el index de '"+n.i.toString()+"' debe ser tipo int");
			addListError(e, n.line);
		}
		Simbolo variable = buscarEnLista(listaActual,n.i.s,n.line,esUn(n.e2));
		if(variable != null) {
			listaAsignaciones.put(n.i, variable);
			
			//si es un tipo classType obtengo su clase
			String tipo = variable.tipoDato.getClass().getName();				
			if(variable.tipoDato.getClass().getName().equals("ast.ClassType") ) {
				ClassType ct = (ClassType)variable.tipoDato;
				tipo = ct.className;
			}
			 
			String f = expEsUn(n.e2);
			if(tipo.equals("ast.IntArrayType")) {
				tipo = "ast.IntType";
			}
			if(!(f.equals(tipo))){
				Error e = new Error(variable,"la expresion de la linea "+n.line+" es incorrecto para el tipo de dato "+ variable.tipoDato.getClass().getName());
				addListError(e, n.line);
			}
		}
			visit(n.e2);	
			
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(And n) {
		visit(n.e1);
		//verifica que se evalue un boolean
		if(!(esUn(n.e1).equals("ast.BooleanType") && esUn(n.e2).equals("ast.BooleanType"))){
			Error e = new Error(null,"la expresiones de la linea "+n.line+" deben ser de tipo un boolean ");
			addListError(e, n.line);
		}	
	}

	@Override
	public void visit(LessThan n) {
		//verifica que se evalue un boolean
		if(!(expEsUn(n.e1).equals("ast.IntType") && expEsUn(n.e2).equals("ast.IntType")) ) {
			Error e = new Error(simboloActual,"no se puede realizar la operacion 'menor que' porque la expresion de la linea "+n.line+" no se inicializo para IntType");
			addListError(e, n.line);
		}
		
	}

	@Override
	public void visit(Plus n) {	
		//verifica que las expresiones sean int
		if(!(expEsUn(n.e1).equals("ast.IntType") && expEsUn(n.e2).equals("ast.IntType")) ) {
			Error e = new Error(simboloActual,"no se puede realizar la operacion suma porque la expresion de la linea "+n.line+" no se inicializo para IntType ");
			addListError(e, n.line);
		}	
	}

	@Override
	public void visit(Minus n) {
		//verifica que las expresiones sean int
		if(!(expEsUn(n.e1).equals("ast.IntType") && expEsUn(n.e2).equals("ast.IntType")) ) {
			Error e = new Error(simboloActual,"no se puede realizar la operacion resta porque la expresion de la linea "+n.line+" no se inicializo para IntType ");
			addListError(e, n.line);
		}
	}

	@Override
	public void visit(Mult n) {
		//verifica que las expresiones sean int
		if(!(expEsUn(n.e1).equals("ast.IntType") && expEsUn(n.e2).equals("ast.IntType")) ) {
			Error e = new Error(simboloActual,"no se puede realizar la operacion multiplicacion porque la expresion de la linea "+n.line+" no se inicializo para IntType ");
			addListError(e, n.line);
		}
	}

	@Override
	public void visit(ArrayLookup n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayLength n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Call n) {
		// TODO Auto-generated method stub
		visit(n.i);
		//verifica que exista este metodo
		Simbolo metodo = buscarEnLista(listaMetodos,n.i.s,n.line,n.getClass().getName());
			if(metodo == null) {
				Error e = new Error(null,"no existe el metodo"+n.i+", error en la linea "+ n.line);
				addListError(e, n.line);
			}

		}
		
		
		
	

	@Override
	public void visit(IntegerLiteral n) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(True n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(False n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IdentifierExpr n) {		
		identActual = buscarEnLista(listaAsignaciones,n.s,n.line,esUn(n));

	}

	@Override
	public void visit(This n) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NewArray n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NewObject n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Not n) {
		//verifica que sea un boolean
		if(!(esUn(n.e).equals("ast.BooleanType"))){
			Error e = new Error(null,"la expresion de la linea "+n.line+" deben ser de tipo un boolean ");
			addListError(e, n.line);
		}		
	}

	@Override
	public void visit(Identifier n) {
		//verifica que no se repitan variables con mismo nombre, o metodo con mismo nombre y parametros
		if(seRepite(listaActual,simboloActual)) {
			Error e = new Error(simboloActual,"Ya existe "+simboloActual.nombre+" de tipo "+simboloActual.tipo+" con este nombre, error en la linea "+simboloActual.linea);
			addListError(e, simboloActual.linea);
		}
		
		if(simboloActual.tipo.equals("Metodo con parametros")) {
			if(seRepite(listaActual,simboloActual)) {
				
				Error e = new Error(simboloActual,"Ya existe "+simboloActual.nombre+" de tipo "+simboloActual.tipo+" con este nombre, error en la linea "+simboloActual.linea);
				addListError(e, simboloActual.linea);
			}
		}
	}
	
	public void addListError(Error e,int clave) {
		  errores.put(clave,e);
	}
	
	public Simbolo getSimbolo(Identifier i) {
		return listaSimbolos.get(i);
	}
	
	//este metodo verifica si un elemento se repite en la lista
	public boolean seRepite(HashMap<Identifier, Simbolo> lista, Simbolo s) {
		int contador = 0; 
		if(s.tipo.equals("Metodo con parametros")) {
			HashMap<Identifier, Simbolo> prmEncontrados = new  HashMap<Identifier, Simbolo>();
			//busco en la lista de parametros 
			for (HashMap.Entry<Identifier, Simbolo> entry : listaParametros.entrySet()) {
				if(entry.getValue().deMetodo.equals(s.nombre) && entry.getValue().deClase.equals(s.deClase) && entry.getValue().linea != s.linea) {		
					prmEncontrados.put(entry.getKey(), entry.getValue());
				   }
			}		
			if(compararHashMaps(listaParametrosActual,prmEncontrados)){
				return true;
			}else {
				return false;
			}		
		}
		
		
		for (HashMap.Entry<Identifier, Simbolo> entry : listaActual.entrySet()) {
		   if(entry.getValue().nombre.equals(s.nombre) && (entry.getValue().tipo.equals("Metodo")||entry.getValue().tipo.equals("Clase"))) {
			   contador++;
			   if(contador>1) {
				   return true;
			   }
		   }
		   
		   
		}
		return false;
	}
	
	//los metodos de una clase
	public void filtrarMetodos() {
		HashMap<Identifier, Simbolo> hashMapFiltrado = new HashMap<>();
        for (HashMap.Entry<Identifier, Simbolo> entry : listaSimbolos.entrySet()) {
        	if((entry.getValue().tipo.equals("Metodo") ||entry.getValue().tipo.equals("Metodo con parametros")) && entry.getValue().deClase.equals(claseActual.nombre))  {
        		hashMapFiltrado.put(entry.getKey(), entry.getValue());
        	}
        }
        listaMetodos = hashMapFiltrado;
	}
	
	//los parametros de metodos de una clase
	public void filtrarParametros() {
		HashMap<Identifier, Simbolo> hashMapFiltrado = new HashMap<>();
        for (HashMap.Entry<Identifier, Simbolo> entry : listaSimbolos.entrySet()) {
        	if(entry.getValue().tipo.equals("Parametro") && entry.getValue().deClase.equals(claseActual.nombre))  {
        		hashMapFiltrado.put(entry.getKey(), entry.getValue());
        	}
        }
        listaParametros = hashMapFiltrado;
	}
	
	//los parametros de un metodo
	public HashMap<Identifier, Simbolo> filtrarParametros(Simbolo metodo) {
		HashMap<Identifier, Simbolo> hashMapFiltrado = new HashMap<>();
        for (HashMap.Entry<Identifier, Simbolo> entry : listaSimbolos.entrySet()) {
        	if(entry.getValue().tipo.equals("Parametro") && entry.getValue().deClase.equals(claseActual.nombre) && entry.getValue().deMetodo.equals(metodo.nombre) && entry.getValue().lineaMetodo == metodo.linea)  {
        		hashMapFiltrado.put(entry.getKey(), entry.getValue());
        	}
        }
        return hashMapFiltrado;
	}
	
	
	public void filtrarVariablesPClase(Simbolo s) {
		HashMap<Identifier, Simbolo> hashMapFiltrado = new HashMap<>();
        for (HashMap.Entry<Identifier, Simbolo> entry : listaSimbolos.entrySet()) {
        	if(entry.getValue().tipo.equals("Variable") && entry.getValue().deMetodo.equals(s.nombre)  && entry.getValue().lineaClase==claseActual.linea ) {
        		hashMapFiltrado.put(entry.getKey(), entry.getValue());
        	}
        	if(entry.getValue().tipo.equals("Parametro") && entry.getValue().deMetodo.equals(s.nombre)  && entry.getValue().lineaClase==claseActual.linea  ) {
        		hashMapFiltrado.put(entry.getKey(), entry.getValue());
        	}
        	
        	if(entry.getValue().tipo.equals("Variable") && entry.getValue().deMetodo.equals("")  && entry.getValue().lineaClase==claseActual.linea ) {
        		hashMapFiltrado.put(entry.getKey(), entry.getValue());
        	}
        }
        listaVariables = hashMapFiltrado;
	}
	
	
	public void filtrarClases() {
		HashMap<Identifier, Simbolo> hashMapFiltrado = new HashMap<>();
        for (HashMap.Entry<Identifier, Simbolo> entry : listaSimbolos.entrySet()) {
        	if(entry.getValue().tipo.equals("Clase")  ) {
        		hashMapFiltrado.put(entry.getKey(), entry.getValue());
        	}
        }
        listaClases = hashMapFiltrado;
	}
	
	public void filtrarVariables(Simbolo s) {
		HashMap<Identifier, Simbolo> hashMapFiltrado = new HashMap<>();		
		if(s.tipo.equals("Clase")) {
	        for (HashMap.Entry<Identifier, Simbolo> entry : listaSimbolos.entrySet()) {
	        	if(entry.getValue().tipo.equals("Variable") && entry.getValue().deClase.equals(s.nombre)&& entry.getValue().lineaClase == claseActual.linea) {
	        		hashMapFiltrado.put(entry.getKey(), entry.getValue());
	        	}
	        }
		}
		if(s.tipo.equals("Metodo")||s.tipo.equals("Metodo con parametros")) {
	        for (HashMap.Entry<Identifier, Simbolo> entry : listaSimbolos.entrySet()) {
	        	if(entry.getValue().tipo.equals("Variable")|| entry.getValue().tipo.equals("Parametro")) {
	
		        	if( entry.getValue().deClase.equals(claseActual.nombre)&& entry.getValue().lineaClase == claseActual.linea  && (entry.getValue().deMetodo.equals("")||entry.getValue().deMetodo.equals(s.nombre)) ) {
		        		if( entry.getValue().lineaMetodo == s.linea || entry.getValue().lineaMetodo == 0) {	        			
		        			hashMapFiltrado.put(entry.getKey(), entry.getValue());
		        		}
		        	}
	        	}
	        }
		}
        listaActual = hashMapFiltrado;
	}
	
	
	
	public void printLista(HashMap<Identifier, Simbolo> l) {
		System.out.print("Lista de "+claseActual+":");
		System.out.println();
		if(l!=null) {
			for (Map.Entry<Identifier, Simbolo> s : l.entrySet()) {
				  	 String clave = s.getKey().toString();
				     String valor = s.getValue().nombre;
				     Type tipoD = s.getValue().tipoDato;
				     String tipo = s.getValue().tipo;
				     String deClase = s.getValue().deClase;
				     String deMetodo = s.getValue().deMetodo;
				     System.out.println("Clave: " + clave +", Tipo dato: "+tipoD+ " ,Tipo:" + tipo  +" ,Valor: " + valor+" ,de clase: "+ deClase+" ,de metodo:"+ deMetodo+" linea:"+s.getValue().linea+" padre en: "+s.getValue().lineaClase+ "linea metodo:" +s.getValue().lineaMetodo );
		    }
		}
	}
	
	public void printErrores() {
			System.out.println("Errores: ");
			for (Map.Entry<Integer, Error> s : errores.entrySet()) {
				     String valor = s.getValue().mensaje;
				     System.out.println(valor);
		    }
						
		}
	
	//busca en una lista un simbolo por su nombre
	public Simbolo buscarEnLista(HashMap<Identifier, Simbolo> listaActual,String nombre, int linea, String tipo) {
		Simbolo variable = null;
		
		for(HashMap.Entry<Identifier, Simbolo> entry : listaActual.entrySet()) {
			if(entry.getValue().nombre.equals(nombre) && entry.getValue().tipo.equals("Parametro") ) {
				 variable = entry.getValue(); 
				 return variable;
			}
		}
		
		for(HashMap.Entry<Identifier, Simbolo> entry : listaActual.entrySet()) {
			if(entry.getValue().nombre.equals(nombre)) {
				 variable = entry.getValue(); 
			}
		}
		if(variable == null) {
			Error e = new Error(null,"no se inicializo ningun '"+nombre+"' de tipo "+tipo+", error en la linea "+linea);
			addListError(e, linea);
		}
		
		return variable;
	}
	
	//recibe una expresion y devuelve el tipo de la expresion
	public String expEsUn(Expr e) {
		if(esUn(e).equals("ast.IdentifierExpr")) {
			IdentifierExpr ie = (IdentifierExpr) e;
			Simbolo var = buscarEnLista(listaAsignaciones,ie.s,ie.line,"ast.IdentifierExpr");
			if(var != null) {
				return var.tipoDato.getClass().getName();
			}
		}
		
		if(esUn(e).equals("NewObject")) {
			NewObject ne = (NewObject) e;
				return ne.i.s;	
		}
		
		
		
		
		if(esUn(e).equals("ast.Call")) {
			Call ne = (Call) e;
			Simbolo var = buscarEnLista(listaMetodos,ne.i.s,ne.line,"ast.IdentifierExpr");
			if(var!=null) {
				return var.tipoDato.getClass().getName();
			}
			
			Error err = new Error(null,"no se inicializo ningun '"+ne.i+", error en la linea "+ne.line);
			addListError(err, ne.line);
				
		}
			return esUn(e);
	}
	

}
