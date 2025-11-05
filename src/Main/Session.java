package Main;

import Config.conf;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Session {

    private static int userId;
    private static boolean active = false;
    private static String loginTime;
    private static int session_id;
    
    public static int getBadge(){
        conf config =  new conf();
        
        String qry = "SELECT u_badge FROM users WHERE u_id = ?";
        java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, userId);
        
        if (result.isEmpty()) {
            System.out.println("Id not found: ");
        } else {
        java.util.Map<String, Object> user = result.get(0);
        String badge = user.get("u_badge").toString();
        
        return Integer.parseInt(badge);
        
        }
        return 0;
    }

    public static int Session(int id) {
        conf config = new conf();
        
        boolean run = true;
        userId = id;
        active = true;
        loginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        System.out.println("\nSession started for user ID: " + userId);
        System.out.println("Login Time: " + loginTime);

        String sql = "INSERT INTO session_logs (u_id, login, s_badge) VALUES (?,?,?)";
        
        while(run){
        session_id = config.addRecordAndReturnId(sql, userId, loginTime, getBadge());
        run = false;
        return session_id;
        
        }

        return userId;
    }

    public static void endSession() {
        if (active) {
            conf config = new conf();
            String logoutTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String sql = "UPDATE session_logs SET logout = ? WHERE s_id = ?";
            config.addRecordAndReturnId(sql, logoutTime, session_id);

            System.out.println("\nSession ended for user ID: " + userId);
            System.out.println("Logout Time: " + logoutTime);

            active = false;
        }else{
            System.out.println("No active session to end: ");
        }
    }
    
    public static int AdminSession(int id) {
        userId = id;


        return userId;
    }

    public static int getUserId() { 
        return userId; }
}

