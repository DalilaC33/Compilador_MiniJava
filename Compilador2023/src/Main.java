import java.io.File;
import java.io.InputStreamReader;
import ast.Goal;
import ast.visitor.MiniJavaPrintVisitor;
import ast.visitor.VisitTCH;
import ast.visitor.Visitor;
import ast.visitor.VisitorSm;
import java_cup.runtime.Symbol;
import java.io.FileInputStream;
public class Main {

	public static void main(String[] args) {
		InputStreamReader isr = new InputStreamReader(System.in);
		Scanner s = new Scanner(isr);
		parser p = new parser(s);
		try {
		
		
		//FileInputStream f =  new FileInputStream(new File("Example.txt"));
		//InputStreamReader isr = new InputStreamReader(f);
		
	
		
			Symbol root = p.parse();
			Visitor mj = new MiniJavaPrintVisitor();
			Visitor vs = new VisitorSm();
			Goal g = (Goal) root.value;
			vs.visit(g);
			Visitor ck = new VisitTCH(vs.getlista());
			ck.visit(g);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void report_error(String message, Object info) {
		
		System.err.print(message);
		System.err.flush();
		if (info instanceof Symbol)
			if (((Symbol) info).left != -1)
				System.err.println(" at line " + ((Symbol) info).left + " of input");
			else
				System.err.println("");
		else
			System.err.println("");
	}
}