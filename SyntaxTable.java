import java.io.IOException;
import java.util.*;

public class SyntaxTable {
    HashMap<String, Integer> mapaVrste = new HashMap<>();
    HashMap<String, Integer> mapaKolone = new HashMap<>();

    String[][] sintaksnaTabela = new String[20][13]; // 20 neterminala x 13 terminala
    String[] smene = new String[12]; // Ukupno 12 smena

    public SyntaxTable() {
        // mapiranje neterminala na vrste
        mapaVrste.put("RL", 0);   // RedoLoop
        mapaVrste.put("E", 1);    // Expression
        mapaVrste.put("E'", 2);   // Expression'
        mapaVrste.put("A", 3);    // AndExpression
        mapaVrste.put("A'", 4);   // AndExpression'
        mapaVrste.put("T", 5);    // Term
        mapaVrste.put("S", 6);    // Statement
        mapaVrste.put("loop", 7);
        mapaVrste.put("(", 8);
        mapaVrste.put(")", 9);
        mapaVrste.put("{", 10);
        mapaVrste.put("redo", 11);
        mapaVrste.put(";", 12);
        mapaVrste.put("}", 13);
        mapaVrste.put("||", 14);
        mapaVrste.put("&&", 15);
        mapaVrste.put("ID", 16);
        mapaVrste.put("CONST", 17);
        mapaVrste.put("=", 18);
        mapaVrste.put("#", 19);

        // mapiranje terminala na kolone
        mapaKolone.put("loop", 0);
        mapaKolone.put("(", 1);
        mapaKolone.put(")", 2);
        mapaKolone.put("{", 3);
        mapaKolone.put("redo", 4);
        mapaKolone.put(";", 5);
        mapaKolone.put("}", 6);
        mapaKolone.put("||", 7);
        mapaKolone.put("&&", 8);
        mapaKolone.put("ID", 9);
        mapaKolone.put("CONST", 10);
        mapaKolone.put("=", 11);
        mapaKolone.put("#", 12); // EOF

        // smene
        smene[0] = "loop ( E ) { S redo ( E ) ; S }"; // RL -> loop ( E ) { S redo ( E ) ; S }
        smene[1] = "A E'";                            // E -> A E'
        smene[2] = "|| A E'";                         // E' -> || A E'
        smene[3] = "";                                // E' -> ε
        smene[4] = "T A'";                            // A -> T A'
        smene[5] = "&& T A'";                         // A' -> && T A'
        smene[6] = "";                                // A' -> ε
        smene[7] = "ID";                              // T -> ID
        smene[8] = "CONST";                           // T -> CONST
        smene[9] = "RL";                              // S -> RL
        smene[10] = "ID = E ;";                       // S -> ID = E ;

		for(int i=0; i<19; i++)
		{
			for(int j=0; j<13; j++)
			{
				sintaksnaTabela[i][j] = "";
			}
		}
		
		for(int i=7; i<19;i++)
		{
			sintaksnaTabela[i][i-7] = "pop";
		}
		
		sintaksnaTabela[18][12] = "acc";
		
        // dodavanje pravila u sintaksnu tabelu
        sintaksnaTabela[0][0] = "0"; // RL -> loop ( E ) { S redo ( E ) ; S }
        
        sintaksnaTabela[1][9] = "1"; // E -> A E'
        sintaksnaTabela[1][10] = "1"; // E -> A E'
        
        sintaksnaTabela[2][2] = "3"; // E' -> ε
        sintaksnaTabela[2][5] = "3"; // E' -> ε
        sintaksnaTabela[2][7] = "2"; // E' -> || A E'


        sintaksnaTabela[3][9] = "4"; // A -> T A'
        sintaksnaTabela[3][10] = "4"; // A -> T A'
        
        sintaksnaTabela[4][2] = "6"; // A' -> ε
        sintaksnaTabela[4][5] = "6"; // A' -> ε
        sintaksnaTabela[4][7] = "6"; // A' -> ε
        sintaksnaTabela[4][8] = "5"; // A' -> && T A'    
        
        sintaksnaTabela[5][9] = "7"; // T -> ID
        sintaksnaTabela[5][10] = "8"; // T -> CONST
        
        sintaksnaTabela[6][0] = "9"; // S -> RL
        sintaksnaTabela[6][9] = "10"; // S -> ID = E ;
		
		for(int i=0; i<19; i++)
		{
			for(int j=0; j<13; j++)
			{
				if(sintaksnaTabela[i][j] == "")
					sintaksnaTabela[i][j] = "err";
			}
		}
    }

    public void reverse(String a[])
	{
		String[] b = new String[a.length];
        int j = a.length;
        for (int i = 0; i < a.length; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }
        a = b;
		
	}
	public String M(String top, Yytoken next)
	{
		String[] ss = top.split(" ");
		if(next.m_index == 9) //ID
		{
			if(ss[0].equals("ID"))
			{
				return "pop";
			}
			return sintaksnaTabela[mapaVrste.get(top)][mapaKolone.get("ID")];
		}
		if(next.m_index == 10) //CONST
		{
			if(ss[0].equals("CONST"))
				return "pop";
			else
				return sintaksnaTabela[mapaVrste.get(top)][mapaKolone.get("CONST")];
		}
		if(ss[0].equals("#") && next.m_text.equals("#")) //dodaj #
		{
			return "acc";
		}
		if(ss[0].equals(next.m_text))
		{
			return "pop";
		}
		if(mapaKolone.containsKey(next.m_text))
		return sintaksnaTabela[mapaVrste.get(top)][mapaKolone.get(next.m_text)];
		
		return "err";
	}
	public boolean SA_LL1(MPLexer lexer)
	{
		Stack<String> stek = new Stack<>();
		stek.push("#");
		stek.push("RL");
		boolean prepoznat = false;
		boolean greska = false;
		String s;
		String[] niz;
		try {
			 Yytoken next = lexer.next_token();
			do
			{
				s = M(stek.peek(), next);
				switch(s)
				{
				case "pop":
					stek.pop();
					next = lexer.next_token();
					break;
				case "acc":
					prepoznat = true;
					break;
				case "err":
					greska = true;
					break;
				default:
					stek.pop();
					niz = smene[Integer.parseInt(s)].split(" ");
					String[] b = new String[niz.length];
			        int j = niz.length;
			        for (int i = 0; i < niz.length; i++) {
			            b[j - 1] = niz[i];
			            j = j - 1;
			        }
					for(String str : b)
					{
						if(!str.equals(""))
						stek.push(str);
					}
					break;
				}
				
				
				
			}while(!(prepoznat || greska));
			
			return prepoznat;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prepoznat;
		
	}


}
