/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Chamath
 */
public class ConnectionManager {
    ResultSet rs=null;
    PreparedStatement pst=null;
    static Connection con=null;
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cnc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
            } catch (Exception ex) {
                System.out.println("Failed to create the database connection."+ex); 
            }
        return con;
    }
    public void insertStudent(String id,String fName,String lName,String nic,String dob,String email,String phone,String crs){
        try{
            con=getConnection();
            String sql="insert into student (id,fname,lname,nic,dob,email,tele,cid)"+ "values(?,?,?,?,?,?,?,?)";
            pst=con.prepareStatement(sql);
            pst.setString(1,id);
            pst.setString(2,fName);
            pst.setString(3,lName);
            pst.setString(4,nic);
            pst.setString(5,dob);
            pst.setString(6,email);
            pst.setString(7,phone);
            pst.setString(8,crs);
            pst.execute();
            }
        catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
    }
    public void insertPayment(Object[] array){
        try{
            con=getConnection();
            String sql="insert into payment(paymentNo,sid,fname,lname,semester,amount,paymentDate)"+ "values(?,?,?,?,?,?,?)";
            pst=con.prepareStatement(sql);
            pst.setString(1,array[0].toString());
            pst.setString(2,array[1].toString());
            pst.setString(3,array[2].toString());
            pst.setString(4,array[3].toString());
            pst.setString(5,array[4].toString());
            pst.setString(6,array[5].toString());
            pst.setString(7,array[6].toString());
            pst.execute();
            }
        catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
    }
    public void insertAttend(Object[] array){
        try{
            con=getConnection();
            String sql="insert into attendence(no,sid,cid,date,slot1,slot2)"+ "values(?,?,?,?,?,?)";
            pst=con.prepareStatement(sql);
            pst.setString(1,null);
            pst.setString(2,array[0].toString());
            pst.setString(3,array[1].toString());
            pst.setString(4,array[2].toString());
            pst.setString(5,array[3].toString());
            pst.setString(6,array[4].toString());
            pst.execute();
            }
        catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
    }
    public void updateProfile(String id,String password,String fname,String lname,String nic,String dob,String mail,String phone,String user,InputStream inputStream){
        String sql="update student set fname=?,lname=?,nic=?,dob=?,email=?,tele=?,userName=?,password=?,dp=? where id='"+id+"'";
        try {
            con=getConnection();
            pst=con.prepareStatement(sql);
            pst.setString(1,fname);
            pst.setString(2,lname);
            pst.setString(3,nic);
            pst.setString(4,dob);
            pst.setString(5,mail);
            pst.setString(6,phone);
            pst.setString(7,user);
            pst.setString(8,password);
            pst.setBinaryStream(9,inputStream);
            JOptionPane.showMessageDialog(new Profile(),"Update Successfull");
            pst.execute();
        } catch (Exception ex) {
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
            
    }
    public void updateResult(String id,String sid,String cid,String name,String grade){
        String sql="update result set sid=?,cid=?,name=?,grade=? where sid='"+id+"'";
        try {
            con=getConnection();
            pst=con.prepareStatement(sql);
            pst.setString(1,sid);
            pst.setString(2,cid);
            pst.setString(3,name);
            pst.setString(4,grade);
            JOptionPane.showMessageDialog(new Profile(),"Update Successfull");
            pst.execute();
        } catch (Exception ex) {
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
            
    }
    public void updateStudent(String value,String id,String fName,String lName,String nic,String dob,String email,String phone,String crs){
           try{
            con=getConnection();
            String sql="update student set id=?,fname=?,lname=?,nic=?,dob=?,email=?,tele=?,cid=? where id='"+value+"'";
            pst=con.prepareStatement(sql);
            pst.setString(1,id);
            pst.setString(2,fName);
            pst.setString(3,lName);
            pst.setString(4,nic);
            pst.setString(5,dob);
            pst.setString(6,email);
            pst.setString(7,phone);
            pst.setString(8,crs);
            pst.execute();
        }
        catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
    }
    public void updateAttendence(String value,String id,String cid,String date,String slot1,String slot2){
           try{
            con=getConnection();
            String sql="update attendence set sid=?,cid=?,date=?,slot1=?,slot2=? where no='"+value+"'";
            pst=con.prepareStatement(sql);
            pst.setString(1,id);
            pst.setString(2,cid);
            pst.setString(3,date);
            pst.setString(4,slot1);
            pst.setString(5,slot2);
            pst.execute();
        }
        catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
    }
    public void insertRegister(String fName,String lName,String nic,String dob,String email,String course,String phone){
           try{
            con=getConnection();
            String sql="insert into register(regNo,fName,lName,nic,dob,email,course,phone)"+ "values(?,?,?,?,?,?,?,?)";
            pst=con.prepareStatement(sql);
            pst.setString(1,null);
            pst.setString(2,fName);
            pst.setString(3,lName);
            pst.setString(4,nic);
            pst.setString(5,dob);
            pst.setString(6,email);
            pst.setString(7,course);
            pst.setString(8,phone);
            pst.execute();
        }
        catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
    }
    public void insertResult(Object[] o){
           try{
            con=getConnection();
            String sql="insert into result(sid,cid,name,grade)"+ "values(?,?,?,?)";
            pst=con.prepareStatement(sql);
            pst.setString(1,o[0].toString());
            pst.setString(2,o[2].toString());
            pst.setString(3,o[1].toString());
            pst.setString(4,o[3].toString());
            pst.execute();
        }
        catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
    }
    public void updatePayment(String value,Object[]array){
        try{
            con=getConnection();
            String sql="update payment set paymentNo=?,sid=?,fname=?,lname=?,semester=?,amount=?,paymentDate=? where paymentNo='"+value+"'";
            pst=con.prepareStatement(sql);
            pst.setString(1,array[0].toString());
            pst.setString(2,array[1].toString());
            pst.setString(3,array[2].toString());
            pst.setString(4,array[3].toString());
            pst.setString(5,array[4].toString());
            pst.setString(6,array[5].toString());
            pst.setString(7,array[6].toString());
            pst.execute();
        }
        catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
    }
    
    public boolean validateMail(final String email)
    {
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void printReport() throws JRException{
        try{
        con=getConnection();
        JasperDesign j=JRXmlLoader.load("C:\\Users\\Chamath\\Documents\\NetBeansProjects\\StudentManagement\\src\\studentmanagement\\report1.jrxml");
        String sql="select * from register";
        JRDesignQuery update=new JRDesignQuery();
        update.setText(sql);
        j.setQuery(update);
        JasperReport jr=JasperCompileManager.compileReport(j);
        JasperPrint jp=JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
    }
        catch(Exception ex){
            System.out.println(ex);
    }finally{
            try{
                con.close();
            }
            catch(Exception ex){
            System.out.println(ex);
        }
        }
  }
}
