package ast;

public abstract class ClassDecl extends Node {

	public int line;
	public ClassDecl(int line) {
		super(line);
		this.line = line;
	}

}
