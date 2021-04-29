package CalculadoraJflexCup.Compilador;

import java_cup.runtime.*;

%%

%class Lexer
%unicode
%cup
%line
%column

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace    = {LineTerminator} | [ \t\f]

Digit          = [0-9]
IntNumber      = {Digit} {Digit}*
DouNumber      = ({Digit}*[.])?{Digit}+
Letter         = [a-zA-Z]
Identifier     = {Letter} ({Letter}|{Digit})* 

%%

<YYINITIAL> { 
    {IntNumber}     { return symbol(sym.INT_NUMBER, new Double(Double.parseDouble(yytext()))); }
    {DouNumber}     { return symbol(sym.DOU_NUMBER, new Double(Double.parseDouble(yytext()))); }
    {Identifier}    { return symbol(sym.IDENT, yytext()); }
    "+"             { return symbol(sym.PLUS); }
    "-"             { return symbol(sym.MINUS); }
    "*"             { return symbol(sym.MUL); }
    "/"             { return symbol(sym.DIV); }
    "("             { return symbol(sym.LPAREN); }
    ")"             { return symbol(sym.RPAREN); }
    "="             { return symbol(sym.ASSIGN); }

    {WhiteSpace} {}
}

.|\n            { throw new Error("Illegal character <" + yytext() + ">");}

