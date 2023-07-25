package ast.visitor;

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

import java.util.HashMap;
import java.util.Map;

public class VisitorSm implements Visitor {

	//inicializo mi tabla simbolos donde se almacena todo lo que hay en el programa
	HashMap<Identifier, Simbolo> listaSimbolos = new HashMap<>();
	private Simbolo temporal;
	private Simbolo claseActual;

		public void visit(Goal n) {
			visit(n.m);
			for (int i = 0; i < n.cl.size(); i++) {
				visit(n.cl.get(i));
			}
			
					}
		
		public void addList(Simbolo s,Identifier clave) {
		  listaSimbolos.put(clave, s);
		}
		
		
	
		
		public Simbolo getSimbol(Identifier i) {
			return listaSimbolos.get(i);
		}
		
		//metodo para imprimir la lista de simbolos
		public void printList() {
		
			for (Map.Entry<Identifier, Simbolo> s : listaSimbolos.entrySet()) {
				  	 String clave = s.getKey().toString();
				     String valor = s.getValue().nombre;
				     Type tipoD = s.getValue().tipoDato;
				     String tipo = s.getValue().tipo;
				     String deClase = s.getValue().deClase;
				     String deMetodo = s.getValue().deMetodo;
				     System.out.println("Clave: " + clave +", Tipo dato: "+tipoD+ " ,Tipo:" + tipo  +" ,Valor: " + valor+" ,de clase: "+ deClase+"linea clase:"+s.getValue().lineaClase+" ,de metodo:"+ deMetodo + "linea metodo: "+s.getValue().lineaMetodo);
		    }		
		}

		@Override
		public void visit(MainClass n) {
			Simbolo clase = new Simbolo("Clase",null,"Main",n.line,n.i1.toString(),"",0,0); 
			Identifier main = new Identifier("Main",n.line);
			addList(clase,main);
			temporal = clase;
			visit(n.s);
			
		}

		@Override
		public void visit(ClassDeclSimple n) {
		    
			//se crea un simbolo por cada objeto en la clase y se agg a la tabla de simbolos
			Simbolo clase = new Simbolo("Clase",null,n.i.toString(),n.line,"","",0,0);
			claseActual = clase;
			addList(clase,n.i);
			temporal = clase;

			for (int i = 0; i < n.vl.size(); i++) {
				visit(n.vl.get(i));

			}
			
			for (int i = 0; i < n.ml.size(); i++) {
				visit(n.ml.get(i));
							
			}
			
		}

		@Override
		public void visit(ClassDeclExtends n) {
			Simbolo clase = new Simbolo("Clase",null,n.i.toString(),n.line,n.j.toString(),"",n.j.line,0); 
			temporal = clase;
			for (int i = 0; i < n.vl.size(); i++) {
				visit(n.vl.get(i));
				
			}
			
			for (int i = 0; i < n.ml.size(); i++) {
				temporal = clase;
				visit(n.ml.get(i));
			}			
			addList(clase,n.i);		
		}

		@Override
		public void visit(VarDecl n) {
			
			Simbolo var = new Simbolo("Variable",n.t,n.i.toString(),n.ln,claseActual.nombre,temporal.deMetodo,claseActual.linea,temporal.linea); 
			if(temporal.tipo.equals("Clase")) {
				var.lineaMetodo = 0;
			}
			addList(var,n.i);
			
		}

		@Override
		public void visit(MethodDecl n) {
			Simbolo md = new Simbolo("Metodo",n.t,n.i.toString(),n.ln,claseActual.nombre,"",temporal.linea,n.i.line); 
			temporal = md;
			
			if(n.fl.size()>0) {
				md.tipo = "Metodo con parametros";	
				for (int i = 0; i < n.fl.size(); i++) {
					visit(n.fl.get(i));
					}
				}
			
				md.linea = n.fl.line;
				temporal = md;
				addList(md,n.i);
				for (int i = 0; i < n.vl.size(); i++) {
					visit(n.vl.get(i));	
				}
				for (int i = 0; i < n.sl.size(); i++) {
					visit(n.sl.get(i));		
				}
				visit(n.e);		
		}

		@Override
		public void visit(Param n) {
			Simbolo prm = new Simbolo("Parametro",n.t,n.i.toString(),n.ln,claseActual.nombre,temporal.nombre,claseActual.linea,n.i.line);	
			addList(prm,n.i);
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(While n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(Print n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(Assign n) {
		
			
		}

		@Override
		public void visit(ArrayAssign n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(And n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(LessThan n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(Plus n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(Minus n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(Mult n) {
			// TODO Auto-generated method stub
			
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
			// TODO Auto-generated method stub
			
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(Identifier n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public HashMap<Identifier, Simbolo> getlista() {
			// TODO Auto-generated method stub
			return listaSimbolos;
		}

	

}
