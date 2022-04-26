import java.awt.*;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
public class managerGUI extends inventory_manager{
	private static JFrame f;
	private static JFrame f2;
	private static JFrame fsold;
	
	private static JPanel titlepanel;
	private static JPanel p1; //Main Menu
	private static JPanel p2; //Insert Items 
	private static JPanel p3; //View Item w/ entry edit 
	private static JPanel p4; //View List w/ Search Bar
	private static JPanel soldItemTable;
	private static JPanel mainButtonPanel;
	
	private static Button viewSold;
	private static Button viewTable;
	private static Button submitB;
	
	private static JLabel title;
	private static JLabel nameLabel;
	private static JLabel locLabel;
	private static JLabel imgLabel;
	private static JLabel linkLabel;
	private static JLabel feedb;

	private static JTextField nameField;

	private static JTextField locField;
	private static JTextField imgField;
	private static JTextField linkField;
	
	private static Image frameIcon;
	
	static Font f1 = new Font("Arial", Font.PLAIN, 18);
	static Font font2 = new Font("Arial", Font.PLAIN, 15);

	
	public managerGUI() {
		gui();
	}
	
	public void gui() {
		f = new JFrame("Inventory Manager");
		f.setVisible(true);
		f.setSize(720,576);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		
		frameIcon = Toolkit.getDefaultToolkit().getImage("src/icon.jpg");
		f.setIconImage(frameIcon);
		
		
		
		
		titlepanel = new JPanel();
		f.add(titlepanel, BorderLayout.PAGE_START);
		titlepanel.setBackground(Color.gray);
		
		title = new JLabel(); //Title/Header 
		title.setText("Inventory Manager");
		title.setForeground(Color.black);
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setBorder(new EmptyBorder(10,30,10,30));
		titlepanel.add(title);
		title.setVisible(true);
		
		p2 = new JPanel(new SpringLayout());
		p2.setBorder(new EmptyBorder(5,20,5,20));
		
		nameLabel = new JLabel("Name: ", JLabel.TRAILING); 
        nameLabel.setFont(f1);
        p2.add(nameLabel); 
		nameField = new JTextField(10);
		p2.add(nameField);
		nameLabel.setLabelFor(nameField);
		
        locLabel = new JLabel("Location: ", JLabel.TRAILING); 
        locLabel.setFont(f1);
        p2.add(locLabel); 
		locField = new JTextField(10);
		p2.add(locField);
		locLabel.setLabelFor(locField);
		
        imgLabel = new JLabel("Image Link: ", JLabel.TRAILING); 
        imgLabel.setFont(f1);
        p2.add(imgLabel); 
		imgField = new JTextField(10);
		p2.add(imgField);
		imgLabel.setLabelFor(imgField);
		
        linkLabel = new JLabel("Listing Link: ", JLabel.TRAILING); 
        linkLabel.setFont(f1);
        p2.add(linkLabel); 
		linkField = new JTextField(10);
		p2.add(linkField);
		linkLabel.setLabelFor(linkField);
		
		
		SpringUtilities.makeCompactGrid(p2, 4, 2, 6, 6, 6, 6);
		f.add(p2, BorderLayout.CENTER);

		mainButtonPanel = new JPanel(new SpringLayout());
		
		submitB = new Button("Insert new item");
		submitB.setFont(font2);
		submitB.setVisible(true);
		submitB.setBackground(Color.LIGHT_GRAY);
		
        feedb = new JLabel(); //Feedback to say if item is added or failed to add
       
		submitB.addActionListener(new ActionListener() //Inserts a new item
		{
			  public void actionPerformed(ActionEvent e)  
			  {
				  //insertInventory(String name, String location, String image)
				  feedb.setVisible(false);
				  
				  if(nameField.getText().equals("") || locField.getText().equals("") || imgField.getText().equals("")) {
					  feedb.setText("Make sure every field is filled");
					  feedb.setVisible(true);
					  feedb.setForeground(Color.red);

					  f.pack();

				  } else {
				  
				  try {
					  insertInventory(nameField.getText(),locField.getText(), imgField.getText(), linkField.getText());
					  feedb.setText("Entry added");
					  feedb.setVisible(true);
					  feedb.setForeground(Color.green);

					  refreshTable();
					  f.pack();


				  } catch(SQLException expcetion) {
					  feedb.setText("Entry failed to add");
					  feedb.setForeground(Color.red);
					  feedb.setVisible(true);
					  System.out.println(expcetion);
					  f.pack();

				  }
				  
				  }
				  f.pack();

			  }
		});
		
		viewTable = new Button("View Items");
		viewTable.setFont(font2);
		viewTable.setBackground(Color.LIGHT_GRAY);
		
		viewSold = new Button("View Sold Items");
		viewSold.setFont(font2);
		viewSold.setBackground(Color.LIGHT_GRAY);
		
		mainButtonPanel.add(feedb);
		mainButtonPanel.add(submitB);
		mainButtonPanel.add(viewTable);
		mainButtonPanel.add(viewSold);
		
		SpringUtilities.makeCompactGrid(mainButtonPanel, 4, 1, 6, 6, 6, 6);

		viewTable.addActionListener(new ActionListener() //Views item table
		{
			  public void actionPerformed(ActionEvent e)  
			  {
				  openTablePage();
				  
			  }
		});
		
		
		viewSold.addActionListener(new ActionListener() //Views sold item table
		{
			  public void actionPerformed(ActionEvent e)  
			  {
				  openSoldTablePage();
				  
			  }
		});
		
		f.add(mainButtonPanel, BorderLayout.PAGE_END);

	      f.pack();

	}
	
	
	public static void openTablePage() {
		f2 = new JFrame("View items");
		f2.setSize(800,640);
		f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f2.setLayout(new BorderLayout());
		f2.setResizable(false);
		p4 = new JPanel();
		try {
			refreshTable();
			JTable t1 = new JTable(itemData);
			JScrollPane scrollPane = new JScrollPane(t1);
			t1.setFillsViewportHeight(true);
			p4.add(scrollPane);
			f2.add(p4, BorderLayout.CENTER);
			f2.setVisible(true);

			
			TableColumnModel columnModel = t1.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(50);
			columnModel.getColumn(0).setResizable(false);
			columnModel.getColumn(1).setPreferredWidth(180);
			columnModel.getColumn(1).setResizable(false);
			columnModel.getColumn(2).setPreferredWidth(180);
			columnModel.getColumn(2).setResizable(false);
			columnModel.getColumn(3).setPreferredWidth(120);
			columnModel.getColumn(3).setResizable(false);
			columnModel.getColumn(4).setPreferredWidth(120);
			columnModel.getColumn(4).setResizable(false);
			columnModel.getColumn(5).setPreferredWidth(200);
			columnModel.getColumn(5).setResizable(false);
			t1.setDefaultEditor(Object.class, null);
		    t1.getTableHeader().setReorderingAllowed(false);
			
			JTextField filterField = RowFilterUtil.createRowFilter(t1);
		    JPanel search = new JPanel();
			JLabel searchL = new JLabel("Search: ");
			search.add(searchL);
		    search.add(filterField);
		    f2.add(search, BorderLayout.NORTH);
		    
		    JPanel footer = new JPanel();
		    footer.setBackground(Color.gray);
		    f2.add(footer, BorderLayout.PAGE_END);
		    JTextField enterId = new JTextField();
		    enterId.setSize(190, 20);
		    Button b1 = new Button("Submit");
		    JLabel j1 = new JLabel("Go to item page, Enter item ID: ");
		    j1.setForeground(Color.white);
	        enterId.setPreferredSize(new Dimension(50,20));

		    footer.add(j1);
		    footer.add(enterId);
		    footer.add(b1);
		    f2.pack();
		    
			b1.addActionListener(new ActionListener() //goes to view item page for the item id
					{
						  public void actionPerformed(ActionEvent e)  
						  {		try {
							   viewItem(enterId.getText());
						  } catch(SQLException e2) {
							  System.out.println(e2);
						  }
						  }
					});

		    

		} catch (SQLException e1) {
			System.out.println(e1);
		}
		
		
		
		
	}
	
	public static void openSoldTablePage() {
		fsold = new JFrame("View sold items");
		fsold.setSize(800,1200);
		fsold.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fsold.setLayout(new BorderLayout());
		fsold.setResizable(false);
		soldItemTable = new JPanel();
		try {
			refreshSoldTable();
			JTable t2 = new JTable(soldData);
			JScrollPane scrollPane = new JScrollPane(t2);
			t2.setFillsViewportHeight(true);
			soldItemTable.add(scrollPane);
			fsold.add(soldItemTable, BorderLayout.CENTER);
			fsold.setVisible(true);

			
			TableColumnModel columnModel = t2.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(50);
			columnModel.getColumn(0).setResizable(false);
			columnModel.getColumn(1).setPreferredWidth(180);
			columnModel.getColumn(1).setResizable(false);
			columnModel.getColumn(2).setPreferredWidth(180);
			columnModel.getColumn(2).setResizable(false);
			columnModel.getColumn(3).setPreferredWidth(120);
			columnModel.getColumn(3).setResizable(false);
			columnModel.getColumn(4).setPreferredWidth(120);
			columnModel.getColumn(4).setResizable(false);
			columnModel.getColumn(5).setPreferredWidth(200);
			columnModel.getColumn(5).setResizable(false);
			t2.setDefaultEditor(Object.class, null);
		    t2.getTableHeader().setReorderingAllowed(false);
			
			JTextField filterField2 = RowFilterUtil.createRowFilter(t2);
		    JPanel search2 = new JPanel();
			JLabel searchL2 = new JLabel("Search: ");
			search2.add(searchL2);
		    search2.add(filterField2);
		    fsold.add(search2, BorderLayout.NORTH);
		    

		    fsold.pack();
		    
	

		    

		} catch (SQLException e1) {
			System.out.println(e1);
		}
		
		
		
		
	}
	
	
	public static void viewItem(String n) throws SQLException{
		int id = Integer.parseInt(n);
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ITEMS WHERE ID = "+id);
		String name = "";
		String location = "";
		String imageLink = "";
		String linkLink = "";
		while(rs.next()) {
		    name = rs.getString("name");
		    location = rs.getString("location");
		    imageLink = rs.getString("image");
		    linkLink = rs.getString("link");
		}
		JFrame f3 = new JFrame("Viewing item id "+n);
		f3.setSize(400,400);
		f3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f3.setLayout(new BorderLayout());
		f3.setResizable(false);
		p3 = new JPanel(new SpringLayout());
		
		JLabel namel = new JLabel("Name: ", JLabel.TRAILING);
		p3.add(namel);
		JTextField nameT = new JTextField(10);
		namel.setLabelFor(nameT);
		nameT.setText(name);
		p3.add(nameT);
			
		JLabel locationL = new JLabel("Location: ", JLabel.TRAILING);
		p3.add(locationL);
		JTextField locationT = new JTextField(10);
		locationL.setLabelFor(locationT);
		locationT.setText(location);
		p3.add(locationT);
		
		JLabel imageL = new JLabel("Image: ", JLabel.TRAILING);
		p3.add(imageL);
		JTextField imageT = new JTextField(10);
		imageL.setLabelFor(imageT);
		imageT.setText(imageLink);
		p3.add(imageT);
		
		JLabel linkL = new JLabel("Link: ", JLabel.TRAILING);
		p3.add(linkL);
		JTextField linkT = new JTextField(10);
		linkL.setLabelFor(linkT);
		linkT.setText(linkLink);
		p3.add(linkT);
		
	   SpringUtilities.makeCompactGrid(p3, 4, 2, 6, 6, 6, 6);
		
		JPanel buttonpanel = new JPanel(new SpringLayout());
		
		

		Button b1 = new Button("Update Entry");
		b1.setBackground(Color.LIGHT_GRAY);
		Button b2 = new Button("Delete Entry");
		b2.setBackground(Color.LIGHT_GRAY);
		Button b3 = new Button("Item Sold");
		b3.setBackground(Color.LIGHT_GRAY);
		buttonpanel.add(b1);  //Update Entry
		buttonpanel.add(b2);  //Delete Entry
		buttonpanel.add(b3); //Sells item
		
		b1.addActionListener(new ActionListener() //updates the entry by id
				{
					  public void actionPerformed(ActionEvent e)  
					  {		
						  try {
							Statement stmt = conn.createStatement();
							String sql = "UPDATE ITEMS SET "
									+"NAME='"+nameT.getText()+"', "
									+"LOCATION='"+locationT.getText()+"', "
									+"LINK='"+linkT.getText()+"', "
									+"IMAGE='"+imageT.getText()+"' "
									+"WHERE ID = "+n;
							stmt.executeUpdate(sql);
							refreshTable();
							f3.dispatchEvent(new WindowEvent(f3, WindowEvent.WINDOW_CLOSING));
							viewItem(n);

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						  
						  
						  
						  
						  
					  }
				});
		
		b2.addActionListener(new ActionListener() //deletes the entry
				{
					  public void actionPerformed(ActionEvent e)  
					  {		
						  try {
							deleteFromItem("id", n);
							f3.setVisible(false);
							refreshTable();
							f2.pack();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					  }
				});
		
		
		
		b3.addActionListener(new ActionListener() //deletes the entry
				{
					  public void actionPerformed(ActionEvent e)  
					  {		
						  try {
							deleteFromItem("id", n);
							f3.setVisible(false);
							sellItem(nameT.getText(), locationT.getText(), linkT.getText(), imageT.getText());
							refreshTable();
							refreshSoldTable();
							f2.pack();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					  }
				});

		SpringUtilities.makeCompactGrid(buttonpanel, 1, 3, 6, 6, 6, 6);

		try {
		    URL url = new URL(imageLink);
		    BufferedImage image = ImageIO.read(url);
		    ImageIcon imgIco = new ImageIcon(image.getScaledInstance(500, 500, Image.SCALE_SMOOTH));
		    JLabel imageLab = new JLabel(imgIco);
		    imageLab.setBorder(BorderFactory.createEmptyBorder(25,50,25,50));
			f3.add(imageLab, BorderLayout.PAGE_END);
		} catch (IOException e13) {
			System.out.println(e13);
		}
		
		p3.setOpaque(true);
		f3.add(p3, BorderLayout.PAGE_START);
		f3.add(buttonpanel, BorderLayout.CENTER);
		f3.pack();
		f3.setVisible(true);
		
		
	}

	
	
}
