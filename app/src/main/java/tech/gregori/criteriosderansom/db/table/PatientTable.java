package tech.gregori.criteriosderansom.db.table;

public class PatientTable {
    public static final String TABLE_NAME = "patient";
    public static final String PRIMARY_KEY = "_id";

    // Database Columns
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_LEUKOCYTES = "leukocytos";
    public static final String COLUMN_GLUCOSE = "glucose";
    public static final String COLUMN_AST_TGO = "ast_tgo";
    public static final String COLUMN_LDH = "ldh";
    public static final String COLUMN_LITIASIS = "litiasis";

    public static final String[] COLUMNS = {
            PRIMARY_KEY,
            COLUMN_NAME,
            COLUMN_AGE,
            COLUMN_LEUKOCYTES,
            COLUMN_GLUCOSE,
            COLUMN_AST_TGO,
            COLUMN_LDH,
            COLUMN_LITIASIS
    };
}
