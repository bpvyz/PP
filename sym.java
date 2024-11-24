public class sym {
    public final static int EOF = 0; // Kraj ulaza (#)

    // Ključne reči
    public final static int MAIN = 1;
    public final static int INT = 2;
    public final static int CHAR = 3;
    public final static int FLOAT = 4;
    public final static int BOOL = 5;
    public final static int LOOP = 6;
    public final static int REDO = 7;

    // Tipovi konstanti
    public final static int BOOL_CONST = 8;
    public final static int INT_CONST = 9;
    public final static int FLOAT_CONST = 10;
    public final static int CHAR_CONST = 11;

    // Identifikator
    public final static int ID = 12;

    // Operator dodele
    public final static int ASSIGN = 13;

    // Logički operatori
    public final static int OR = 14;
    public final static int AND = 15;

    // Relacioni operatori
    public final static int LT = 16;
    public final static int LE = 17;
    public final static int GT = 18;
    public final static int GE = 19;
    public final static int EQ = 20;
    public final static int NE = 21;

    // Specijalni simboli
    public final static int LBRACE = 22;    // "{"
    public final static int RBRACE = 23;    // "}"
    public final static int LEFTPAR = 24;  // "("
    public final static int RIGHTPAR = 25; // ")"
    public final static int SEMICOLON = 26; // ";"
    public final static int COMMA = 27;    // ","
}
