package ast.visitor;

import java.util.HashMap;

import ast.*;

public interface Visitor {
	public void visit(Goal n);
	
	public HashMap<Identifier, Simbolo> getlista();

	public void visit(MainClass n);

	public void visit(ClassDeclSimple n);

	public void visit(ClassDeclExtends n);

	public void visit(VarDecl n);

	public void visit(MethodDecl n);

	public void visit(Param n);

	public void visit(IntArrayType n);

	public void visit(BooleanType n);

	public void visit(IntType n);

	public void visit(ClassType n);

	public void visit(Block n);

	public void visit(If n);

	public void visit(While n);

	public void visit(Print n);

	public void visit(Assign n);

	public void visit(ArrayAssign n);

	public void visit(And n);

	public void visit(LessThan n);

	public void visit(Plus n);

	public void visit(Minus n);

	public void visit(Mult n);

	public void visit(ArrayLookup n);

	public void visit(ArrayLength n);

	public void visit(Call n);

	public void visit(IntegerLiteral n);

	public void visit(True n);

	public void visit(False n);

	public void visit(IdentifierExpr n);

	public void visit(This n);

	public void visit(NewArray n);

	public void visit(NewObject n);

	public void visit(Not n);

	public void visit(Identifier n);

	public default void visit(ClassDecl c) {
		// Hack, we could resolve this with OO
		if (c instanceof ClassDeclExtends) {
			visit((ClassDeclExtends) c);
		} else if (c instanceof ClassDeclSimple) {
			visit((ClassDeclSimple) c);
		}

	}

	public default void visit(Expr e) {
		if (e instanceof And) {
			visit((And) e);
		} else if (e instanceof ArrayLength) {
			visit((ArrayLength) e);
		} else if (e instanceof ArrayLookup) {
			visit((ArrayLookup) e);
		} else if (e instanceof Call) {
			visit((Call) e);
		} else if (e instanceof True) {
			visit((True) e);
		} else if (e instanceof IdentifierExpr) {
			visit((IdentifierExpr) e);
		} else if (e instanceof IntegerLiteral) {
			visit((IntegerLiteral) e);
		} else if (e instanceof LessThan) {
			visit((LessThan) e);
		} else if (e instanceof Minus) {
			visit((Minus) e);
		} else if (e instanceof Mult) {
			visit((Mult) e);
		} else if (e instanceof NewArray) {
			visit((NewArray) e);
		} else if (e instanceof NewObject) {
			visit((NewObject) e);
		} else if (e instanceof Not) {
			visit((Not) e);
		} else if (e instanceof Plus) {
			visit((Plus) e);
		} else if (e instanceof This) {
			visit((This) e);
		} else if (e instanceof False) {
			visit((False) e);
		}
	}
	
	public default String esUn(Expr e) {
		
		if (e instanceof And) {
			return "ast.BooleanType";
		} else if (e instanceof ArrayLength) {
			return "ast.IntType";
		} else if (e instanceof ArrayLookup) {
			return "ArrayLookup";
		} else if (e instanceof Call) {
			return e.getClass().getName();
		} else if (e instanceof True) {
			return "ast.BooleanType";
		} else if (e instanceof IdentifierExpr) {
			return e.getClass().getName();
		} else if (e instanceof IntegerLiteral) {
			return "ast.IntType";
		} else if (e instanceof LessThan) {
			return "ast.BooleanType";
		} else if (e instanceof Minus) {
			return "ast.IntType";
		} else if (e instanceof Mult) {
			return "ast.IntType";
		} else if (e instanceof NewArray) {
			return "NewArray";
		} else if (e instanceof NewObject) {
			return "NewObject";
		} else if (e instanceof Not) {
			return "ast.BooleanType";
		} else if (e instanceof Plus) {
			return "ast.IntType";
		} else if (e instanceof This) {
			return "This";
		} else if (e instanceof False) {
			return "ast.BooleanType";
		}
		return "nada";
		
	}

	public default void visit(Type e) {
		if (e instanceof IntType) {
			visit((IntType) e);
		} else if (e instanceof IntArrayType) {
			visit((IntArrayType) e);
		} else if (e instanceof BooleanType) {
			visit((BooleanType) e);
		} else if (e instanceof ClassType) {
			visit((ClassType) e);
		}
	}

	public default void visit(Statement s) {
		if (s instanceof ArrayAssign) {
			visit((ArrayAssign) s);
		} else if (s instanceof Assign) {
			visit((Assign) s);
		} else if (s instanceof Block) {
			visit((Block) s);
		} else if (s instanceof If) {
			visit((If) s);
		} else if (s instanceof Print) {
			visit((Print) s);
		} else if (s instanceof While) {
			visit((While) s);
		}
	}
	
	
    public default boolean compararHashMaps(HashMap<Identifier, Simbolo> map1, HashMap<Identifier, Simbolo> map2) {
        if (map1.size() != map2.size()) {
            return false;
        }       
        for (HashMap.Entry<Identifier, Simbolo> entry : map1.entrySet()) {
        	Simbolo simbolo1 = entry.getValue();
        	int contador = 0;           
        	for (HashMap.Entry<Identifier, Simbolo> entry2 : map2.entrySet()) {
        		Simbolo simbolo2 = entry2.getValue();             
        		if(simbolo1.deMetodo.equals(simbolo2.deMetodo) && simbolo1.deClase.equals(simbolo2.deClase) && simbolo1.nombre.equals(simbolo2.nombre) && simbolo1.tipoDato.getClass()==simbolo2.tipoDato.getClass() && simbolo1.linea != simbolo2.linea) {
    				contador++;
    			}
        	}      	
        	if(contador == map1.size()) {
        		return true;
        	}
        	return false;
		}
        return true;
    }
    
	  public default boolean elementosRepetidos(HashMap<Identifier, Simbolo> map1) {
		   for (HashMap.Entry<Identifier, Simbolo> entry : map1.entrySet()) {
		   		Simbolo simbolo = entry.getValue();
		   		int contador = 0;
		   		for (HashMap.Entry<Identifier, Simbolo> entry2 : map1.entrySet()) {
		   			Simbolo simbolo2 = entry.getValue(); 
		   			if(simbolo.nombre.equals(simbolo2.nombre) ) {
		   				contador++;
		   				if(contador>1) {	   					
		   					return true;
		   				}
					}
		   		}
		   }
	   	 return false;  
      }
   
   
}

