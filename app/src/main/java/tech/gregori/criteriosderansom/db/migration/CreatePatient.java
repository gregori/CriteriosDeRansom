package tech.gregori.criteriosderansom.db.migration;

import tech.gregori.criteriosderansom.db.Database;
import tech.gregori.criteriosderansom.db.table.PatientTable;

public class CreatePatient implements Database.DatabaseMigration {
    @Override
    public String up() {
        return "CREATE TABLE " + PatientTable.TABLE_NAME + "( "
                + PatientTable.PRIMARY_KEY + " integer primary key autoincrement, "
                + PatientTable.COLUMN_NAME + " string, "
                + PatientTable.COLUMN_AGE + " integer, "
                + PatientTable.COLUMN_LEUKOCYTES + " integer, "
                + PatientTable.COLUMN_GLUCOSE + " integer, "
                + PatientTable.COLUMN_AST_TGO + " integer, "
                + PatientTable.COLUMN_LDH + " integer, "
                + PatientTable.COLUMN_LITIASIS + " boolean" +
                ")";
    }

    @Override
    public String down() {
        return "DROP TABLE IF EXISTS " + PatientTable.TABLE_NAME;
    }
}
