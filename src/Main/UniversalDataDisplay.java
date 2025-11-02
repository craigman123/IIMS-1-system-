
package Main;

import Config.conf;

public class UniversalDataDisplay {
    public static int ShowAllRecords(){
        
        return 0;
    }
    
    public static int ShowUserSession(){
        
        return 0;
    }
    
    public static int ShowAllInmateType(int type){
        System.out.println("\n--- USER DISPLAY INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT t_id, i_id, t_name, t_level FROM type";
        
        String[] headers = {"Type ID", "Inmate ID", "Ty"};
        
        String[] cols = {"u_id", "u_name", "u_pass", "u_position", "u_badge", "u_approval", "date_added"};
        
        config.viewInmate(sqlQuery, headers, cols);
        return 0;
    }
    
    public static int ShowallUser(){
        System.out.println("\n--- USER DISPLAY INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT u_id, u_name, 'HASHED' AS u_pass, u_position, u_badge, u_approval, date_added FROM users";
        
        String[] headers = {"User ID", "User Name", "User Pass", "User Position", "Unique Badge", "System Access", "Date Added"};
        
        String[] cols = {"u_id", "u_name", "u_pass", "u_position", "u_badge", "u_approval", "date_added"};
        
        config.viewInmate(sqlQuery, headers, cols);
        return 0;
    }
    
    public static int ShowLogs(){
        System.out.println("\n--- VIEWING HISTORY INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT h_id, i_id, u_id, h_name, h_context, h_date FROM logs";
        
        String[] headers = {"Log ID", "Inmate ID", "User ID", "Inmate Name", "Log Action", "Log Date"};
        
        String[] cols = {"h_id", "i_id", "u_id", "h_name", "h_context", "h_date"};
        
        config.viewInmate(sqlQuery, headers, cols);
        return 0;
    }
    
    public static int ShowInmateData(){
        System.out.println("\n--- VIEWING HISTORY INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT i_id, u_id, i_name, i_type, i_infoStatus FROM inmate";
        
        String[] headers = {"Inmate ID", "User ID", "Inmate Name", "Inmate Type", "Data Status"};
        
        String[] cols = {"i_id", "u_id", "i_name", "i_type", "i_infoStatus"};
        
        config.viewInmate(sqlQuery, headers, cols);
        
        return 0;
    }
    
    public static int ShowRecordData(int id){
        System.out.println("\n--- VIEWING INMATE RECORDS ---");
        conf config = new conf();

        String sqlQuery = "SELECT r.r_id, r.i_id, r.r_name, r.r_stat, r.r_recordStatus " +
                          "FROM record r WHERE r.i_id = " + id;

        String[] headers = {"Record ID", "Inmate ID", "Record Name", "Record Status", "Case Status"};
        String[] cols = {"r_id", "i_id", "r_name", "r_stat", "r_recordStatus"};

        config.viewRecords(sqlQuery, headers, cols);
        
        return 0;
    }
    
    public static void viewInmateInformation(){
        System.out.println("\n--- VIEWING INMATE INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT i_id, u_id, i_name, i_age, i_gender, i_nationality, i_impri, i_stat,"
                + " i_record_quan, i_type, i_apprehended, i_date_register, i_infoStatus FROM inmate";
        
        String[] headers = {"ID", "User ID", "Inmate Name", "Age", "Gender", "Nationaility", "Imprison yrs",
            "Status", "Record Quant.", "Type", "Date Apprehend", "Date Regist.", "Information Status"};
        
        String[] cols = {"i_id", "u_id", "i_name", "i_age", "i_gender", "i_nationality", "i_impri", "i_stat", 
            "i_record_quan", "i_type", "i_apprehended", "i_date_register", "i_infoStatus"};
        
        config.viewInmate(sqlQuery, headers, cols);
        
    }
    
    public static void viewInmateRecord(int id) {
    System.out.println("\n--- VIEWING INMATE RECORDS ---");
    conf config = new conf();
    
    String sqlQuery = "SELECT r.r_id, r.i_id, r.r_name, r.r_stat, r.r_conviction_time, r.r_date_commited, r.r_recordStatus " +
                      "FROM record r WHERE r.i_id = " + id;
    
    String[] headers = {"Record ID", "Inmate ID", "Record Name", "Record Status", "Time Sentence", "Date Commited", "Case Status"};
    String[] cols = {"r_id", "i_id", "r_name", "r_stat", "r_conviction_time", "r_date_commited", "r_recordStatus"};
    
    config.viewRecords(sqlQuery, headers, cols);
    
    }
}
