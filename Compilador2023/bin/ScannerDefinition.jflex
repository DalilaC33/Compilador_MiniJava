
/*package de.jflex.example.standalone;*/
import java_cup.runtime.*;
%%

%public
%class Scanner
%cup
%unicode
%line
%column


%{
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }
%}

whitespace=[\r\n]|[ \t]

%%
"System" { return symbol(sym.SYSTEM, yytext()); }
"out" { return symbol(sym.OUT, yytext()); }
"println" { return symbol(sym.PRINT, yytext()); }

/* operators */
"+" { return symbol(sym.PLUS, yytext()); }
"=" { return symbol(sym.EQ, yytext()); }
"!" { return symbol(sym.NEGATION, yytext()); }
"-" { return symbol(sym.SUB, yytext()); }
"*" { return symbol(sym.MULT, yytext()); }
"<" { return symbol(sym.MINUS, yytext()); }
"&&" { return symbol(sym.AND, yytext()); }


/* delimiters */
"(" { return symbol(sym.O_PAREN, yytext()); }
")" { return symbol(sym.C_PAREN, yytext()); }
";" { return symbol(sym.SEMICOLON, yytext()); }
"public" { return symbol(sym.PUBLIC, yytext()); }
"static" { return symbol(sym.STATIC, yytext()); }
"void" { return symbol(sym.VOID, yytext()); }
"main" { return symbol(sym.MAIN, yytext()); }
"class" { return symbol(sym.CLASS, yytext()); }
"if" { return symbol(sym.IF, yytext()); }
"while" { return symbol(sym.WHILE, yytext()); }
"else" { return symbol(sym.ELSE, yytext()); }
"length" { return symbol(sym.LENGTH, yytext()); }
"this" { return symbol(sym.THIS, yytext()); }
"new" { return symbol(sym.NEW, yytext()); }
"false" { return symbol(sym.FALSE, yytext());} 
"true" { return symbol(sym.FALSE, yytext()); }
"extends" { return symbol(sym.EXTENDS, yytext()); }
"{" { return symbol(sym.O_CBRACKET, yytext()); }
"}" { return symbol(sym.C_CBRACKET, yytext()); }
"[" { return symbol(sym.O_SBRACKET, yytext()); }
"]" { return symbol(sym.C_SBRACKET, yytext()); }
"," { return symbol(sym.COMA, yytext()); }
"." { return symbol(sym.POINT, yytext()); }
"int" { return symbol(sym.INT, yytext()); }
"boolean" { return symbol(sym.BOOLEAN, yytext()); }
"String" { return symbol(sym.STRING, yytext()); }
"return" { return symbol(sym.RETURN, yytext()); }
/* identifiers */
[a-zA-Z] ([a-zA-Z]|[0-9]|_)* { return symbol(sym.IDENTIFIER, yytext()); }
[0-9]+ { return symbol(sym.INTEGER_LITERAL, new Integer(yytext())); }
{whitespace}+ {/*ignore*/}
\/\/(.?)* {/*ignore*/}
\/\*(.)*\*\/ {/*ignore*/}

. { System.err.println(
	"\nunexpected character in input: '" + yytext() + "' at line " +
	(yyline+1) + " column " + (yycolumn+1));
  }