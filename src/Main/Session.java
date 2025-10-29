
package Main;

import Config.conf;
import java.time.*;

public class Session {
    public static int Session(int id){
        conf config = new conf();
        
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        
            if(id != 0){
                
                String sql = "SELECT * FROM users WHERE i_id = ?";
                java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(sql, id);
               
                java.util.Map<String, Object> user = result.get(0);
                String badge = user.get("u_badge").toString();
                
                sql = "INSERT INTO session(u_id, s_badge, s_date, s_time, s_context) VALUES (?,?,?,?,?)";
                config.addRecord(sql, id, badge, date, time, "Logged In");
                
            }else if(id == 0){
                
                String sql = "SELECT * FROM session WHERE i_id = ?";
                java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(sql, id);
               
                java.util.Map<String, Object> user = result.get(0);
                String badge = user.get("u_badge").toString();
                
                sql = "INSERT INTO session(u_id, s_badge, s_date, s_time, s_context) VALUES (?,?,?,?,?)";
                config.addRecord(sql, id, badge, date, time, "Logged Out");
            }
        return 0;
    }
}
