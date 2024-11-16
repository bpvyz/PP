import java.util.Hashtable;

public class KWTable {

    private Hashtable<String, Integer> mTable;

    public KWTable() {
        // Inicijalizacija hash tabele koja pamti ključne reči
        mTable = new Hashtable<String, Integer>();
        mTable.put("main", Integer.valueOf(sym.MAIN));
        mTable.put("int", Integer.valueOf(sym.INT));
        mTable.put("char", Integer.valueOf(sym.CHAR));
        mTable.put("float", Integer.valueOf(sym.FLOAT));
        mTable.put("bool", Integer.valueOf(sym.BOOL));
        mTable.put("loop", Integer.valueOf(sym.LOOP));
        mTable.put("redo", Integer.valueOf(sym.REDO));
        mTable.put("true", Integer.valueOf(sym.BOOL_CONST));
        mTable.put("false", Integer.valueOf(sym.BOOL_CONST));
    }

    /**
     * Vraća ID ključne reči 
     */
    public int find(String keyword) {
        Integer symbol = mTable.get(keyword);
        if (symbol != null)
            return symbol.intValue();
        
        // Ako reč nije pronađena u tabeli ključnih reči, tretira se kao identifikator
        return sym.ID;
    }
}
