
package Main;

import Config.conf;

public class GenerateAdminACC {
    
    private static int AdminID;
    
    private static int adminacc(){
        conf config = new conf();
        
        String name = "admin";
        String pass = "1";
        int badge = 1;
        String approve = "Admin";
        
        String HashPass = config.hashPassword(pass);
        
        String qry = "SELECT * FROM users WHERE u_name = ? AND u_pass = ? AND u_badge = ?";
        java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, name, HashPass, badge);
        
        if (!result.isEmpty()) {
            return 0;
        }else{
            qry = "INSERT INTO users(u_name, u_pass, u_badge, u_approval, date_added, u_position) VALUES (?,?,?,?,?,?)";
            config.addRecord(qry, name, HashPass, badge, approve, "Infinity", "admin");
        }
        return 0;
    }
    
    
    public static void CheckAdmin(){
        adminacc();
    }
}
