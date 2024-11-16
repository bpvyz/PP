// import sekcija

%%

// sekcija opcija i deklaracija
%class MPLexer
%function next_token
%type Yytoken
%line
%column
%debug

%eofval{
    return new Yytoken(sym.EOF, null, yyline, yycolumn);
%eofval}

%{
//dodatni clanovi generisane klase
KWTable kwTable = new KWTable();
Yytoken getKW()
{
    return new Yytoken(kwTable.find(yytext()), yytext(), yyline, yycolumn);
}
%}

// stanja
%xstate COMMENT

// makroi
slovo = [a-zA-Z_]
cifra = [0-9]
hex_cifra = [0-9a-fA-F]

// regularni izrazi za različite tipove konstanti
octal = 0#o{cifra}+
hex = 0#x{hex_cifra}+
dec = 0#d{cifra}+
int_const = {dec}|{octal}|{hex}|{cifra}+
float_const = "0."{cifra}*([eE][+-]?{cifra}+)?
char_const = \'[^']\'
bool_const = true|false

%%

// pravila za komentare
"%"                            { yybegin(COMMENT); }
<COMMENT>~"%"                  { yybegin(YYINITIAL); }

// ignorisanje belina
[\t\n\r ]                      { ; }

// specijalni simboli
"{"                            { return new Yytoken(sym.LBRACE, yytext(), yyline, yycolumn); }
"}"                            { return new Yytoken(sym.RBRACE, yytext(), yyline, yycolumn); }
"("                            { return new Yytoken(sym.LEFTPAR, yytext(), yyline, yycolumn); }
")"                            { return new Yytoken(sym.RIGHTPAR, yytext(), yyline, yycolumn); }
";"                            { return new Yytoken(sym.SEMICOLON, yytext(), yyline, yycolumn); }
","                            { return new Yytoken(sym.COMMA, yytext(), yyline, yycolumn); }

// operator dodele
"="                            { return new Yytoken(sym.ASSIGN, yytext(), yyline, yycolumn); }

// logički operatori
"||"                           { return new Yytoken(sym.OR, yytext(), yyline, yycolumn); }
"&&"                           { return new Yytoken(sym.AND, yytext(), yyline, yycolumn); }

// relacijski operatori
"<="                           { return new Yytoken(sym.LE, yytext(), yyline, yycolumn); }
"<"                            { return new Yytoken(sym.LT, yytext(), yyline, yycolumn); }
">="                           { return new Yytoken(sym.GE, yytext(), yyline, yycolumn); }
">"                            { return new Yytoken(sym.GT, yytext(), yyline, yycolumn); }
"=="                           { return new Yytoken(sym.EQ, yytext(), yyline, yycolumn); }
"!="                           { return new Yytoken(sym.NE, yytext(), yyline, yycolumn); }

// ključne reči
"main"                         { return new Yytoken(sym.MAIN, yytext(), yyline, yycolumn); }
"int"                          { return new Yytoken(sym.INT, yytext(), yyline, yycolumn); }
"char"                         { return new Yytoken(sym.CHAR, yytext(), yyline, yycolumn); }
"float"                        { return new Yytoken(sym.FLOAT, yytext(), yyline, yycolumn); }
"bool"                         { return new Yytoken(sym.BOOL, yytext(), yyline, yycolumn); }
"loop"                         { return new Yytoken(sym.LOOP, yytext(), yyline, yycolumn); }
"redo"                         { return new Yytoken(sym.REDO, yytext(), yyline, yycolumn); }

// identifikatori
{slovo}({slovo}|{cifra})*      { return new Yytoken(sym.ID, yytext(), yyline, yycolumn); }

// konstante
{int_const}                    { return new Yytoken(sym.INT_CONST, yytext(), yyline, yycolumn); }
{float_const}                  { return new Yytoken(sym.FLOAT_CONST, yytext(), yyline, yycolumn); }
{char_const}                   { return new Yytoken(sym.CHAR_CONST, yytext(), yyline, yycolumn); }
{bool_const}                   { return new Yytoken(sym.BOOL_CONST, yytext(), yyline, yycolumn); }

// obrada grešaka
.                              { System.err.println("ERROR: " + yytext()); }
