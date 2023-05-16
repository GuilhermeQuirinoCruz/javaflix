package model.database;

public class DatabaseFactory {
    
    private static final String DATABASE = "MySQL";
    private static Database database;
    
    public static Database getDatabase() {
        if (database == null) {
            if (DATABASE.equals("MySQL")) {
                database = new DatabaseMysql();
            } else {
                database = null;
            }
        }
        
        return database;
    }
}
