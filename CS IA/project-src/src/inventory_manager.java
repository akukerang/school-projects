import java.sql.*;

import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
public class inventory_manager {
	
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	static ResultSet rs2 = null;
	static PreparedStatement ps = null;
	public static DefaultTableModel itemData;
	public static DefaultTableModel soldData;
	
	
	public static void main(String[] args)  {
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory?" +
                    "user=gabriel&password=password");
			
			itemData = new DefaultTableModel();
			itemData.addColumn("ID");
			itemData.addColumn("Name");
			itemData.addColumn("Location");
			itemData.addColumn("Image");
			itemData.addColumn("Link");
			itemData.addColumn("Date Added");
			
			soldData = new DefaultTableModel();
			soldData.addColumn("ID");
			soldData.addColumn("Name");
			soldData.addColumn("Location");
			soldData.addColumn("Image");
			soldData.addColumn("Link");
			soldData.addColumn("Date Sold");

			managerGUI mg = new managerGUI();
 

		} catch (SQLException e) {
		    System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}
	
	
	


}
	
	public static void insertInventory(String name, String location, String image, String link) throws SQLException {
        LocalDateTime currTime = LocalDateTime.now();  
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        String currTimeFormatted = currTime.format(format);
	     String q1 = "INSERT INTO ITEMS (NAME, LOCATION, IMAGE, LINK, DATEADDED)" 
	     +" VALUES (?, ?, ?, ?, ?)";
	     
	     PreparedStatement ps = conn.prepareStatement(q1);
	     ps.setString(1, name);
	     ps.setString(2,  location);
	     ps.setString(3,  image);
	     ps.setString(4, link);
	     ps.setString(5, currTimeFormatted);
	     ps.execute();
	     
	  
	}
	
	public static void showItems() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ITEMS");
	     while (rs.next())
	      {
	        int id = rs.getInt("id");
	        String name = rs.getString("name");
	        String location = rs.getString("location");
	        String image = rs.getString("image");
	        String link = rs.getString("link");
	        Date dateD = rs.getDate("dateadded");
	        String date = dateD +"";
	        
	        // print the results
	        System.out.format("%s, %s, %s,%s, %s, %s\n", id, name, location, image, link,date);
	      }
	     stmt.close();
	}
	
	public static void deleteFromItem(String entry, String entryValue) throws SQLException{
		Statement stmt = conn.createStatement();
	
		
		String sql = "DELETE FROM ITEMS WHERE " +entry.toUpperCase()+" = \"" + entryValue + "\"";
		
		stmt.executeUpdate(sql);
		
		
		
		
		stmt.close();
	}
	
	
	public static void sellItem(String name, String location, String image, String link) throws SQLException{
        LocalDateTime currTime = LocalDateTime.now();  
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        String currTimeFormatted = currTime.format(format);
	     String q1 = "INSERT INTO SOLDITEMS (NAME, LOCATION, IMAGE, LINK, DATESOLD)" 
	     +" VALUES (?, ?, ?, ?, ?)";
	     
	     PreparedStatement ps = conn.prepareStatement(q1);
	     ps.setString(1, name);
	     ps.setString(2,  location);
	     ps.setString(3,  image);
	     ps.setString(4, link);
	     ps.setString(5, currTimeFormatted);
	     ps.execute();

	}
	
	
	public static void refreshTable() throws SQLException {
		
		itemData.setRowCount(0);
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ITEMS");
	     while (rs.next())
	      {
	        int id = rs.getInt("id");
	        String name = rs.getString("name");
	        String location = rs.getString("location");
	        String image = rs.getString("image");
	        String link = rs.getString("link");
	        String dateD = rs.getString("dateadded");
	        itemData.addRow(new Object[] {id, name, location, image, link,dateD});
	        
	        
	      }
		
	     
	     
	}
	
	
	public static void refreshSoldTable() throws SQLException {
		
		soldData.setRowCount(0);
		
		Statement stmt = conn.createStatement();
		ResultSet rs2 = stmt.executeQuery("SELECT * FROM SOLDITEMS");
	     while (rs2.next())
	      {
	        int id = rs2.getInt("id");
	        String name = rs2.getString("name");
	        String location = rs2.getString("location");
	        String image = rs2.getString("image");
	        String link = rs2.getString("link");
	        String dateD = rs2.getString("datesold");
	        soldData.addRow(new Object[] {id, name, location, image, link,dateD});
	        
	        
	      }
	     
	}
	
	
	
}
