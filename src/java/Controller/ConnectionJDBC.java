package Controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Model.User;
import Model.Video;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import sun.org.mozilla.javascript.internal.Token;
/**
 *
 * @author JoanMarc
 */
public class ConnectionJDBC {

    static Connection connection = null;
    
    public static void connect(){
        
        
            
            
            try {
                try {
                    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConnectionJDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
                connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ISDCM;user=isdcm;password=isdcm");

               
                
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       
    }
    
   
    
  
    
    public static Video getVideoByID(int id){
        ArrayList<Video> videos = null;
        try {
            Statement statement;
            ResultSet rs;
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("SELECT * FROM videos WHERE id="+id+"");
            videos = new ArrayList();
            while(rs.next()){
                Video v = new Video();
                v.setId(rs.getInt("ID"));
                v.setTitle(rs.getString("TITLE"));
                v.setAuthor(rs.getString("AUTHOR"));
                v.setCreation_date(rs.getString("CREATION_DATE"));
                v.setDuration(rs.getString("DURATION"));
                v.setViews(rs.getInt("VIEWS"));
                v.setDescription(rs.getString("DESCRIPTION"));
                v.setFormat(rs.getString("FORMAT"));
                v.setUrl(rs.getString("URL"));
                v.setUploader(rs.getString("UPLOADER"));
                videos.add(v);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(videos.isEmpty()){
            return null;
        }else{
            return videos.get(0);
        }
        
    }
    
    public static int getViewsById(int id){
        int v = -1;
        try {
            Statement statement;
            ResultSet rs;
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("SELECT * FROM videos WHERE id="+id+"");
            while(rs.next()){
                v = rs.getInt("VIEWS");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    
    public static int addView(int id){
        System.out.println("asd2 "+id);
        Video v = getVideoByID(id);
        if (v!=null){
            v.setViews(v.getViews()+1);
            try {
                Statement statement;
                statement = connection.createStatement();
                statement.setQueryTimeout(30);
                return statement.executeUpdate("UPDATE videos SET views="+v.getViews()+" WHERE id="+v.getId());
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionJDBC.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }else{
            return -1;
        }
        
        
        
    }
    
    public static void disconnect(){
        try {
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
