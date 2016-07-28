package calculator_views;
import calculator_views.*;
import net.proteanit.sql.DbUtils;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Choice;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Welcome_view extends JFrame {

	private JPanel contentPane;
	private JTextField CategoryName;
	private JTextField EnterTypeName;
	private JTextField TfEnterAppName;
	public JComboBox comboApp;
	public JComboBox comboType;
	public JComboBox comboName;
	public static Connection conn; //for the category combo box connection to sql database
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JTable resultTable;
	public JTextField num_users;
	private JTable WorkLoadtable;
	private JTable tableResource;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome_view frame = new Welcome_view();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
////////////////////////////////////To update/fill Category comboBox////////////////////////////////////
	
 public void FillComboCategory()
 {
	 try{
			String query="select * from AppCategory";
			PreparedStatement pstate= conn.prepareStatement(query);
			ResultSet result_set= pstate.executeQuery();
			
			while (result_set.next())
					{
				comboApp.addItem(result_set.getObject("Category"));
					}
			pstate.close();
			result_set.close();
			
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error in filling Combo Box");	
		}

 }
 
 ///////////////////////////////////To fill the types of Applications ComboBox///////////////////////////////////
 
 public void FillComboType(String Table)
 {
	 try{
			String query="select * from '"+Table+"'";
			PreparedStatement ps= conn.prepareStatement(query);
			ResultSet result_set= ps.executeQuery();
			while (result_set.next())
					{
				
				comboType.addItem(result_set.getObject("Type"));
			
					}
			ps.close();
			result_set.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error in filling Type Combo Box");	
		}

 }
 
////////////////////////////////////////////////To fill the Name of Applications ComboBox/////////////////////////////////

 public void FillComboName()
 {
	 try{
		 		 
			String query="select AppName from Busniess_App where Type = '"+comboEntry(comboType)+"'";
				
			PreparedStatement ps= conn.prepareStatement(query);
			ResultSet result_set= ps.executeQuery();
			while (result_set.next())
					{
				comboName.addItem(result_set.getObject("AppName"));
			
					}
			ps.close();
			result_set.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error in filling Type Combo Box");	
		}

 }
 ////////////////////////////This method gets the selected value of a ComboBox////////////////////////////////////////////////
 
 private String comboEntry(JComboBox comboentry)
 {
	 String entry = (String) comboentry.getSelectedItem();
	 return entry;
 }
 
 ////////////////////////////////////////////////To Update User Selection to DB/////////////////////////////////////////////////////////
 
 private void Store (String num)
 {
	 try {
			DelStore("UserSelection");
			//String num= user.getText();
		 	String query= "insert INTO UserSelection (category,type,app,users) VALUES (?,?,?,?)";
		 	
			PreparedStatement ps= conn.prepareStatement(query);
			ps.setString(1,(String) comboApp.getSelectedItem());
			ps.setString(2,(String) comboType.getSelectedItem());
			ps.setString(3,(String) comboName.getSelectedItem());
			ps.setString(4, num );
			//ps.setString(5,"Physical");
			ps.execute();
			//JOptionPane.showMessageDialog(null, "Selection Saved");	
			ps.close();
			}
			
	catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Selection not saved");	
		}
 }

/////////////////////////////////// TO update the Database On Each User Selection ///////////////////////////////////
 
 private void DelStore(String Table)
 {
	 try {
			
		 	String query= "delete from '"+Table+"'";
		 	
			PreparedStatement ps= conn.prepareStatement(query);
			ps.execute();
			//JOptionPane.showMessageDialog(null, "History Deleted");
			
			ps.close();
		}
			
	catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "History not deleted");	
		}
	
 }
 
///////////////////////////////////Record Selection ///////////////////////////////////
 
 private void RecordSelection(JComboBox comboSelect, String col)
 {
	 
			String record = comboEntry(comboSelect); 
			//JOptionPane.showMessageDialog(null, "Selected Category is :'"+record+"'");
			FillComboType(record);		

 }
///////////////////////////////////Record Selection ///////////////////////////////////
 
 private void RecordSelectionName(JComboBox comboSelect, String col)
 {
	
			String record = comboEntry(comboSelect); 
		 	//JOptionPane.showMessageDialog(null, "Selected Type is :'"+record+"'");
			FillComboName();
			
 }
/////////////////////////////////// This method generates MODEL of the selected Application ///////////////////////////////////
 
 public void model(String suser)
 {
	 double core = 0;
	 double RAM = 0;
	 int user;
	 user = Integer.parseInt(suser); // to convert the string input value to integer 
	 
	// JOptionPane.showMessageDialog(null, user);
	 String Type = comboEntry(comboType);
	 
	 if (Type.equals("Video On Demand"))
	 {	
			  	core = Math.ceil(user*0.04);
				 RAM = Math.ceil(user*55.8);		 
	 }
	 
	 String r_core = Double.toString(core);
	 String r_RAM = Double.toString(RAM);
//	 JOptionPane.showMessageDialog(null, "core:'"+r_core+"'");
//	 JOptionPane.showMessageDialog(null, "RAM:'"+r_RAM+"'");
	 
	 try {
			DelStore("Results"); // to update the DB from current user selection
		 	String query= "insert INTO Results (core,RAM_MB) VALUES (?,?)";
			PreparedStatement ps= conn.prepareStatement(query);
			ps.setString(1,r_core);
			ps.setString(2,r_RAM);
			ps.execute();
			//JOptionPane.showMessageDialog(null, "Result Saved");
			ps.close();
		}
			
	catch (SQLException e1) {
			
		// TODO Auto-generated catch block
			e1.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Selection not saved");	
		
	}
	 
	 // to store the values in File
	 Save_Results sr = new Save_Results();
	 sr.save(comboEntry(comboApp), comboEntry(comboType), comboEntry(comboType), num_users.getText(),core, RAM);
	 
 }
 
	/**
	 * Create the frame.
	 */
	public Welcome_view() {
		setTitle("Resource Estimation Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 455);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFileMenu = new JMenu("File");
		mnFileMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnFileMenu);
		
		JMenuItem menuitem_Exit = new JMenuItem("Exit");
		menuitem_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menuitem_Exit.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		mnFileMenu.add(menuitem_Exit);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel Application = new JPanel();
		setContentPane(contentPane);
		conn= DBInteractions.DbConnector();
		PreparedStatement pstate;
		ResultSet result_set;
				
		JTabbedPane tabbedPane_main = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_main.setFont(new Font("Tahoma", Font.BOLD, 13));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(tabbedPane_main, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane_main, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		tabbedPane_main.addTab("Application", null, Application, null);
		Application.setLayout(null);
		
		JTabbedPane tabbedPane_app = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_app.setFont(new Font("Tahoma", Font.BOLD, 12));
		tabbedPane_app.setBounds(10, 11, 647, 313);
		Application.add(tabbedPane_app);
		
		JPanel Category = new JPanel();
		tabbedPane_app.addTab("Category", null, Category, null);
		Category.setLayout(null);
		
		JPanel Type = new JPanel();
		tabbedPane_app.addTab("Type", null, Type, null);
		Type.setLayout(null);
		
		JPanel Name = new JPanel();
		tabbedPane_app.addTab("Name", null, Name, null);
		Name.setLayout(null);
		
		JLabel SelectAppCategory = new JLabel("Select Application Category");
		SelectAppCategory.setFont(new Font("Tahoma", Font.BOLD, 11));
		SelectAppCategory.setBounds(24, 11, 174, 23);
		Category.add(SelectAppCategory);
		JLabel lblCategoryName = new JLabel("Category Name:");
		lblCategoryName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCategoryName.setBounds(268, 36, 103, 20);
		Category.add(lblCategoryName);
		
		CategoryName = new JTextField();
		CategoryName.setBounds(395, 36, 203, 20);
		Category.add(CategoryName);
		CategoryName.setColumns(10);
		
		// Add new category!
		JButton addCategoryButton = new JButton("Add");
		addCategoryButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		addCategoryButton.setToolTipText("Please Enter the Name of the New Category!");
		addCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (CategoryName.getText().equals(""))
					{
					JOptionPane.showMessageDialog(null, "Enter Valid Category Name");
					}
				else{
					String query= "insert INTO AppCategory (Category) VALUES (?)";
					PreparedStatement ps= conn.prepareStatement(query);
					ps.setString(1,CategoryName.getText());
					ps.execute();
					JOptionPane.showMessageDialog(null, "New Category Added");
					int itemCount = comboApp.getItemCount();
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboApp.removeItemAt(0);
				     }
					FillComboCategory();
					ps.close();
					// To set the Text Field Blank again
					CategoryName.setText("");}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error in Adding new category");	
				}

			}
		});
		addCategoryButton.setBounds(452, 83, 112, 23);
		Category.add(addCategoryButton);
				
		comboApp = new JComboBox();
		comboApp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboApp.setBounds(24, 45, 174, 23);
		Category.add(comboApp);		
		
		JButton EditButton = new JButton("Edit");
		EditButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		EditButton.setToolTipText("Please Select Category from the DropBox that you want to Edit and Enter a new name");
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String comboEntry= (String) comboApp.getSelectedItem();
					
					String query= "update AppCategory set Category= '"+CategoryName.getText()+"' where Category= '"+comboEntry+"'";
					//To avoid Null Entries
					if (CategoryName.getText().equals(""))
						{
						JOptionPane.showMessageDialog(null, "Enter Valid Category Name");
						}
					else{
					PreparedStatement ps= conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Category Updated");
					int itemCount = comboApp.getItemCount();
					
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboApp.removeItemAt(0);
				     }
					FillComboCategory();
					// To set the Text Field Blank again
					CategoryName.setText("");
					ps.close();}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Category Cannot be Updated");
				}
			}
		});
		EditButton.setBounds(452, 117, 112, 23);
		Category.add(EditButton);
		
		JButton btnButtonDelete = new JButton("Delete");
		btnButtonDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnButtonDelete.setToolTipText("Please Select the category from the dropbox that you want to delete");
		btnButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String comboEntry= comboEntry(comboApp);
					
					String query= "delete from AppCategory where Category= '"+comboEntry+"'";
					
					PreparedStatement ps= conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Category Deleted");
					int itemCount = comboApp.getItemCount();
					
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboApp.removeItemAt(0);
				     }
					FillComboCategory();
					
					ps.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Category Cannot be Deleted");
				}
			}
		});
		btnButtonDelete.setBounds(452, 151, 112, 23);
		Category.add(btnButtonDelete);
		
		//Jumping to the next panel
		
		JButton Category_DoneButton = new JButton("Next");
		Category_DoneButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		Category_DoneButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane_app.setSelectedIndex(1);
				int itemCount = comboType.getItemCount();
				
				// To Update ComboBox
			    for(int i=0;i<itemCount;i++){
			        comboType.removeItemAt(0);
			     }
				RecordSelection(comboApp, "category");
				
			}
		});
		Category_DoneButton.setBounds(528, 234, 70, 23);
		Category.add(Category_DoneButton);
		comboType= new JComboBox();
		comboType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboType.setBounds(36, 48, 154, 20);
		Type.add(comboType);
		
		
		JLabel lblApplicationType = new JLabel("Select Application Type");
		lblApplicationType.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApplicationType.setBounds(36, 11, 135, 26);
		Type.add(lblApplicationType);
		
		EnterTypeName = new JTextField();
		EnterTypeName.setBounds(413, 37, 219, 20);
		Type.add(EnterTypeName);
		EnterTypeName.setColumns(10);
		
		JLabel lblEnterTypeName = new JLabel("Enter Type Name :");
		lblEnterTypeName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEnterTypeName.setBounds(299, 36, 119, 20);
		Type.add(lblEnterTypeName);
		
		JButton btnAdd_Type = new JButton("Add");
		btnAdd_Type.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAdd_Type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (EnterTypeName.getText().equals(""))
					{
					JOptionPane.showMessageDialog(null, "Enter Valid Type Name");
					}
				else{
					String Table = comboEntry(comboApp);
					String query= "insert INTO '"+Table+"' (Type) VALUES (?)";
					PreparedStatement ps= conn.prepareStatement(query);
					ps.setString(1,EnterTypeName.getText());
					ps.execute();
					JOptionPane.showMessageDialog(null, "New Type Added");
					int itemCount = comboType.getItemCount();
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboType.removeItemAt(0);
				     }
					FillComboType(Table);
					ps.close();
					// To set the Text Field Blank again
					EnterTypeName.setText("");}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error in Adding new Type");	
				}
			}
		});
		btnAdd_Type.setBounds(468, 76, 129, 31);
		Type.add(btnAdd_Type);
		
		JButton btnPrevious_Type = new JButton("Previous");
		btnPrevious_Type.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPrevious_Type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_app.setSelectedIndex(0);
			}
		});
		btnPrevious_Type.setBounds(419, 251, 85, 23);
		Type.add(btnPrevious_Type);
		
		JButton btnNext_Type = new JButton("Next");
		btnNext_Type.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNext_Type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_app.setSelectedIndex(2);
				int itemCount = comboName.getItemCount();
				
				// To Update ComboBox
			    for(int i=0;i<itemCount;i++){
			    	comboName.removeItemAt(0);
			     }
				RecordSelectionName(comboType, "type");
			}
		});
		btnNext_Type.setBounds(555, 251, 77, 23);
		Type.add(btnNext_Type);
		
		JButton btnEdit_Type = new JButton("Edit");
		btnEdit_Type.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit_Type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String comboEntry= (String) comboType.getSelectedItem();
					String Table = comboEntry(comboApp);
					String query= "update '"+Table+"' set Type= '"+EnterTypeName.getText()+"' where Type= '"+comboEntry+"'";
					//To avoid Null Entries
					if (EnterTypeName.getText().equals(""))
						{
						JOptionPane.showMessageDialog(null, "Enter Valid Type: Null not accepted");
						}
					else{
					PreparedStatement ps= conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Type Updated");
					int itemCount = comboType.getItemCount();
					
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboType.removeItemAt(0);
				     }
					FillComboType(Table);
					ps.close();
					// To set the Text Field Blank again
					EnterTypeName.setText("");}
								
					} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Type Cannot be Updated");
				}
			}
		});
		btnEdit_Type.setBounds(468, 118, 129, 29);
		Type.add(btnEdit_Type);
		
		JButton btnTypeDelete = new JButton("Delete");
		btnTypeDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTypeDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String comboEntry= comboEntry(comboType);
					String Table = comboEntry(comboApp);
					String query= "delete from '"+Table+"' where Type= '"+comboEntry+"'";
					
					PreparedStatement ps= conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Type Deleted");
					int itemCount = comboType.getItemCount();
					
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboType.removeItemAt(0);
				     }
					FillComboType(Table);
					
					ps.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Type Cannot be Deleted");
				}
			}
		});
		btnTypeDelete.setBounds(468, 158, 129, 31);
		Type.add(btnTypeDelete);
		
		
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 616, 263);
		Name.add(panel);
		
		JLabel SelectApp_Name = new JLabel("Select Application");
		SelectApp_Name.setFont(new Font("Tahoma", Font.BOLD, 11));
		SelectApp_Name.setBounds(25, 24, 168, 14);
		panel.add(SelectApp_Name);
		
		comboName = new JComboBox();
		comboName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboName.setBounds(25, 49, 168, 20);
		panel.add(comboName);
		
		TfEnterAppName = new JTextField();
		TfEnterAppName.setFont(new Font("Tahoma", Font.BOLD, 11));
		TfEnterAppName.setColumns(10);
		TfEnterAppName.setBounds(415, 49, 191, 20);
		panel.add(TfEnterAppName);
		
		JLabel LblApplicationName = new JLabel("Enter Application Name :");
		LblApplicationName.setFont(new Font("Tahoma", Font.BOLD, 11));
		LblApplicationName.setBounds(260, 49, 145, 20);
		panel.add(LblApplicationName);
		
		JButton button = new JButton("Add");
		button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (TfEnterAppName.getText().equals(""))
					{
					JOptionPane.showMessageDialog(null, "Enter Valid Type Name");
					}
				else{
					//String Type = comboEntry(comboType);
					String query= "insert INTO Busniess_App(Type,AppName) VALUES (?,?)";
					PreparedStatement ps= conn.prepareStatement(query);
					ps.setString(1,(String) comboType.getSelectedItem());
					ps.setString(2,TfEnterAppName.getText());
					
					ps.execute();
					
					
					int itemCount = comboName.getItemCount();
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboName.removeItemAt(0);
				     }
					FillComboName();
					ps.close();
					// To set the Text Field Blank again
					TfEnterAppName.setText("");}
					JOptionPane.showMessageDialog(null, "New Type Added");
				} 
				catch (SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error in Adding new Application");	
				}
			}
			
		});
		button.setBounds(442, 80, 117, 28);
		panel.add(button);
		
		JButton Previous_Name = new JButton("Previous");
		Previous_Name.setFont(new Font("Tahoma", Font.BOLD, 11));
		Previous_Name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_app.setSelectedIndex(1);
			}
		});
		Previous_Name.setBounds(399, 229, 85, 23);
		panel.add(Previous_Name);
		
		JButton Next_Name = new JButton("Next");
		Next_Name.setFont(new Font("Tahoma", Font.BOLD, 11));
		Next_Name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_app.setSelectedIndex(3);
				
			}
		});
		Next_Name.setBounds(521, 229, 71, 23);
		panel.add(Next_Name);
		
		JButton edit_name = new JButton("Edit");
		edit_name.setFont(new Font("Tahoma", Font.BOLD, 11));
		edit_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String comboEntry= (String) comboName.getSelectedItem();
					
					String query= "update Busniess_App set AppName= '"+TfEnterAppName.getText()+"' where AppName= '"+comboEntry+"'";
					//To avoid Null Entries
					if (TfEnterAppName.getText().equals(""))
						{
						JOptionPane.showMessageDialog(null, "Enter Valid Type: Null not accepted");
						}
					else{
					PreparedStatement ps= conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Name Updated");
					int itemCount = comboName.getItemCount();
					
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboName.removeItemAt(0);
				     }
					FillComboName();
					ps.close();
					// To set the Text Field Blank again
					TfEnterAppName.setText("");}
								
					} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Name Cannot be Updated");
				}
			}
		});
		edit_name.setBounds(442, 119, 117, 28);
		panel.add(edit_name);
		
		JButton delete_name = new JButton("Delete");
		delete_name.setFont(new Font("Tahoma", Font.BOLD, 11));
		delete_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String comboEntry= comboEntry(comboName);
					String Type = comboEntry(comboType);
					String query= "delete from Busniess_App where AppName= '"+comboEntry+"'";
					
					PreparedStatement ps= conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Application Deleted");
					int itemCount = comboName.getItemCount();
					
					// To Update ComboBox
				    for(int i=0;i<itemCount;i++){
				        comboName.removeItemAt(0);
				     }
					FillComboName();
					
					ps.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Type Cannot be Deleted");
				}
			}
		});
		delete_name.setBounds(442, 161, 117, 28);
		panel.add(delete_name);
		
		JPanel UserSpecification = new JPanel();
		tabbedPane_app.addTab("UserSpecification", null, UserSpecification, null);
		UserSpecification.setLayout(null);
		
		JLabel lblNumOfUsers = new JLabel("Enter Number of Users");
		lblNumOfUsers.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNumOfUsers.setBounds(37, 26, 319, 14);
		UserSpecification.add(lblNumOfUsers);
		
		JButton button_Previous_User = new JButton("Previous");
		button_Previous_User.setFont(new Font("Tahoma", Font.BOLD, 11));
		button_Previous_User.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_app.setSelectedIndex(2);
			}
		});
		button_Previous_User.setBounds(388, 251, 85, 23);
		UserSpecification.add(button_Previous_User);
		//for the radio button
		
		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (num_users.getText().equals(""))
				{
				JOptionPane.showMessageDialog(null, "Enter Number of Users/Jobs: NULL value not accepted");
				}
				else{
				String noOfUsers = num_users.getText();
				Store(noOfUsers);
				try{
					String query="select Workloads from Workloads where Application = '"+comboEntry(comboType)+"'";
					PreparedStatement pstate= conn.prepareStatement(query);
					ResultSet result_set= pstate.executeQuery();
					WorkLoadtable.setModel(DbUtils.resultSetToTableModel(result_set));
					pstate.close();
					result_set.close();
					
				}
				catch (Exception ep)
				{
					JOptionPane.showMessageDialog(null, "Error in filling Table");	
				}
				String PageJob="Scientific ";
				String PageDB="Business Processing";
				
				if(comboEntry(comboApp).equals(PageJob)){
					tabbedPane_app.setSelectedIndex(4);
				}	
				else if (comboEntry(comboApp).equals(PageDB)){
					tabbedPane_app.setSelectedIndex(5);
				}	
				else
				{
					JOptionPane.showMessageDialog(null,comboEntry(comboApp)+PageJob);
					tabbedPane_main.setSelectedIndex(1);
				}
			}}
		});
		btnNext.setBounds(531, 251, 77, 23);
		UserSpecification.add(btnNext);
		
		JPanel userpanel = new JPanel();
		userpanel.setBounds(37, 51, 225, 32);
		UserSpecification.add(userpanel);
		userpanel.setLayout(null);
		
		num_users = new JTextField();
		num_users.setBounds(0, 0, 225, 32);
		userpanel.add(num_users);
		num_users.setFont(new Font("Tahoma", Font.BOLD, 12));
		num_users.setColumns(10);
		
		JPanel Jobs = new JPanel();
		tabbedPane_app.addTab("Job Specifications", null, Jobs, null);
		Jobs.setLayout(null);
		
		JLabel label = new JLabel("Enter the Number of Map and Reduce jobs");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(45, 33, 319, 14);
		Jobs.add(label);
		
		JLabel label_1 = new JLabel("Enter the Number of Map Jobs");
		label_1.setBounds(45, 58, 209, 14);
		Jobs.add(label_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(45, 83, 148, 20);
		Jobs.add(textField);
		
		JLabel label_2 = new JLabel("Enter the Number of Reduce Jobs");
		label_2.setBounds(45, 116, 209, 14);
		Jobs.add(label_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(45, 141, 148, 20);
		Jobs.add(textField_1);
		
		JButton button_JobPrevious = new JButton("Previous");
		button_JobPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_app.setSelectedIndex(3);
			}
		});
		button_JobPrevious.setFont(new Font("Tahoma", Font.BOLD, 11));
		button_JobPrevious.setBounds(412, 250, 85, 23);
		Jobs.add(button_JobPrevious);
		
		JButton button_JobNext = new JButton("Next");
		button_JobNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_main.setSelectedIndex(1);
			}
		});
		button_JobNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		button_JobNext.setBounds(555, 250, 77, 23);
		Jobs.add(button_JobNext);
		
		JPanel DB = new JPanel();
		tabbedPane_app.addTab("DataBase Specifications", null, DB, null);
		DB.setLayout(null);
		
		JLabel lblEnterTheSize = new JLabel("Enter the Size of DataBase");
		lblEnterTheSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEnterTheSize.setBounds(32, 39, 319, 14);
		DB.add(lblEnterTheSize);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(32, 73, 148, 20);
		DB.add(textField_2);
		
		JButton button_DBPre = new JButton("Previous");
		button_DBPre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_app.setSelectedIndex(3);
			}
		});
		button_DBPre.setFont(new Font("Tahoma", Font.BOLD, 11));
		button_DBPre.setBounds(412, 250, 85, 23);
		DB.add(button_DBPre);
		
		JButton button_DBNext = new JButton("Next");
		button_DBNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane_main.setSelectedIndex(1);
			}
		});
		button_DBNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		button_DBNext.setBounds(555, 250, 77, 23);
		DB.add(button_DBNext);
		
		JPanel Workloads = new JPanel();
		tabbedPane_main.addTab("Workloads", null, Workloads, null);
		Workloads.setLayout(null);
		
		JButton bWorkNext = new JButton("Next");
		bWorkNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		bWorkNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				String user=num_users.getText();
				model(user);
				try{
					String query="select * from UserSelection";
					PreparedStatement pstate= conn.prepareStatement(query);
					ResultSet result_set= pstate.executeQuery();
					resultTable.setModel(DbUtils.resultSetToTableModel(result_set));
					
					pstate.close();
					result_set.close();
					
				}
				catch (Exception ep)
				{
					JOptionPane.showMessageDialog(null, "Error in filling Combo Box");	
				}
				try{
					String query="select * from Results";
					PreparedStatement pstate= conn.prepareStatement(query);
					ResultSet result_set= pstate.executeQuery();
					tableResource.setModel(DbUtils.resultSetToTableModel(result_set));
					pstate.close();
					result_set.close();
					
				}
				catch (Exception ep)
				{
					JOptionPane.showMessageDialog(null, "Error in filling Combo Box");	
				}	
			
				tabbedPane_main.setSelectedIndex(2);
			}
		});
		bWorkNext.setBounds(543, 296, 70, 23);
		Workloads.add(bWorkNext);
		
		JLabel lbWorkloadLable = new JLabel("WorkLoad Generated by the Application");
		lbWorkloadLable.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbWorkloadLable.setBounds(209, 29, 275, 14);
		Workloads.add(lbWorkloadLable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(71, 60, 542, 190);
		Workloads.add(scrollPane_1);
		
		WorkLoadtable = new JTable();
		WorkLoadtable.setBackground(Color.LIGHT_GRAY);
		WorkLoadtable.setFont(new Font("Tahoma", Font.BOLD, 13));
		scrollPane_1.setViewportView(WorkLoadtable);
		
		
		JPanel Results = new JPanel();
		tabbedPane_main.addTab("Results", null, Results, null);
		Results.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 45, 581, 75);
		Results.add(scrollPane);
		
		resultTable = new JTable();
		scrollPane.setViewportView(resultTable);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(216, 211, 231, 38);
		Results.add(scrollPane_3);
		
		tableResource = new JTable();
		tableResource.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		scrollPane_3.setViewportView(tableResource);
		
		JLabel lblSelectedApplication = new JLabel("Selected Application ");
		lblSelectedApplication.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSelectedApplication.setBounds(264, 21, 147, 14);
		Results.add(lblSelectedApplication);
		
		JLabel lblNewLabel = new JLabel("Core- 2.1 GHZ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(229, 186, 92, 14);
		Results.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("RAM in MB");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(366, 186, 84, 14);
		Results.add(lblNewLabel_1);
		
		JLabel lblResourcesEstimationFor = new JLabel("Resources Estimation for the selected number of users");
		lblResourcesEstimationFor.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblResourcesEstimationFor.setBounds(169, 142, 377, 14);
		Results.add(lblResourcesEstimationFor);
	
		FillComboCategory();	
		
		
	}
	}

