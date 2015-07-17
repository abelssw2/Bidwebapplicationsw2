/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid;

/**
 *
 * @author abel
 */
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.Random;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;




public class Users  {
  
    Integer capacity;
    public  String email;
    public  String role;
    

    
    
    
    private static final Random rand = new Random((new Date()).getTime());

    public Users() {
    }
    
    

    public static String encrypt(String str) {

        BASE64Encoder encoder = new BASE64Encoder();

        byte[] salt = new byte[8];

        rand.nextBytes(salt);

        return encoder.encode(salt) + encoder.encode(str.getBytes());

    }

    public static String decrypt(String encstr) {

        if (encstr.length() > 12) {

            String cipher = encstr.substring(12);

            BASE64Decoder decoder = new BASE64Decoder();

            try {

                return new String(decoder.decodeBuffer(cipher));

            } catch (IOException e) {

	    //  throw new InvalidImplementationException(
	    //Fail
            }

        }

        return null;

    }
    
    public  boolean selectcreadie(String user, String password) {
        Connection con = null;
        PreparedStatement ps = null;
       String enPassword= encrypt(password);
       
       
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "SELECT u.id, u.username,u.password,u.role,u.capacity,u.email FROM useraccount u where u.username =?");
            ps.setString(1, user);
        

            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
            {
                capacity=Integer.parseInt(rs.getString("capacity"));
                
                email=rs.getString("email");
                role=rs.getString("role");
               
              //setCapacity(Integer.parseInt(rs.getString("capacity")));
                
                System.out.println("--------------------------------------------------------- \n");
                System.out.println(decrypt(rs.getString("password")));
            if( decrypt(rs.getString("password")) == null ? password != null : !decrypt(rs.getString("password")).equals(password)){
                return false;
            }else{
                  return true;
            }
              
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }

    }
        
    public static boolean login(String user, String password) {
        Connection con = null;
        PreparedStatement ps = null;
       String enPassword= encrypt(password);
       
       
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "SELECT u.id, u.username,u.password,u.role,u.capacity,u.email FROM useraccount u where u.username =?");
            ps.setString(1, user);
        

            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
            {
                
               
              //setCapacity(Integer.parseInt(rs.getString("capacity")));
                
                System.out.println("--------------------------------------------------------- \n");
                System.out.println(decrypt(rs.getString("password")));
            if( decrypt(rs.getString("password")) == null ? password != null : !decrypt(rs.getString("password")).equals(password)){
                return false;
            }else{
                  return true;
            }
              
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
    }
}
