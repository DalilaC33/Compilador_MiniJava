package ast;

public class VarDecl extends Node {
	public Type t;
	public Identifier i;
	public int ln;

	public VarDecl(Type at, Identifier ai, int ln) {
		super(ln);
		t = at;
		i = ai;
		this.ln = ln;
	}
}
