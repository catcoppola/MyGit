import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class Project2 extends Frame
 {
	MenuBar mbar;
	Menu first,second,third;
        MenuItem m1,m2,m3,m4,m5,m6,m7,m8,m9;
	TextField t1,t2,t3,t4,t5,t6;
	Label l1,l2,l3,l4,l5,l6;
	Button b1,b2,b3,b4;
	Panel p1,p2;

	int count=0;
	int total_rec,rec_count;

	Connection cn;
	ResultSet rs;
	PreparedStatement pst;
	Statement st;

        public Project2()
	{
                super("Water Bill Management");
		mbar=new MenuBar();

		first=new Menu("Customer");
		second=new Menu("Bill");
                third=new Menu("Help");

                m1=new MenuItem("Add");
		m2=new MenuItem("Remove");
		m3=new MenuItem("Update");
		m4=new MenuItem("Exit");
		m5=new MenuItem("Generate");
		m6=new MenuItem("Cost");
                m7=new MenuItem("Instructions");
                m8=new MenuItem("Query");
		m9=new MenuItem("Show Details");
	


                t1=new TextField(20);
		t2=new TextField(20);
		t3=new TextField(20);
		t4=new TextField(20);
		t5=new TextField(20);
		t6=new TextField(20);
		t1.setBackground(new Color(226,233,163));
		t2.setBackground(new Color(226,233,163));
		t3.setBackground(new Color(226,233,163));
		t4.setBackground(new Color(226,233,163));
		t5.setBackground(new Color(226,233,163));
                t6.setBackground(new Color(226,233,163));	
		
		l1=new Label("         Meter No :");
		l2=new Label("         Name        ");
		l3=new Label("         City            ");
		l4=new Label("         Address   ");
		l5=new Label("         Phone No     ");
                l6=new Label("         Query (if any)     "); 		
 
		b1=new Button("Submit");
		b1.setBackground(new Color(171,249,147));
		b2=new Button("Update");
		b2.setBackground(new Color(171,249,147));
		b3=new Button("Submit Query");
		b3.setBackground(new Color(171,249,147));
		b4=new Button("Show");
		b4.setBackground(new Color(171,249,147));
		
		setLayout(new GridLayout(2,1));
		setMenuBar(mbar);
		setBackground(new Color(87,160,215));
		
		p1=new Panel();
                p1.setLayout(new FlowLayout(FlowLayout.CENTER,45,15));
		
		p1.add(l1);
		p1.add(t1);
		p1.add(new Label(" "));

		p1.add(l2);
		p1.add(t2);
		p1.add(new Label(" "));

		p1.add(l3);
		p1.add(t3);
		p1.add(new Label(" "));

		p1.add(l4);
		p1.add(t4);
		p1.add(new Label(" "));

		p1.add(l5);
		p1.add(t5);
		p1.add(new Label(" "));
		
		p1.add(l6);
		p1.add(t6);
		p1.add(new Label(" "));
            
                add(p1);

	        p2=new Panel();
                p2.setLayout(new FlowLayout(FlowLayout.CENTER,45,15));
		
                p2.add(b1);
		p2.add(b2);
                p2.add(b3);
		 p2.add(b4);
		  
		add(p2);

		mbar.add(first);
		mbar.add(second);
                mbar.add(third);

		first.add(m1);
		first.add(m2);
		first.add(m3);
		first.add(m9);
		first.addSeparator();
		first.add(m4);
		second.add(m5);
		second.add(m6);
                third.add(m7);
                third.addSeparator();
                third.add(m8);


		addWindowListener(new B());

		m1.addActionListener(new A());
		m2.addActionListener(new A());
		m3.addActionListener(new A());
		m4.addActionListener(new A());
		m5.addActionListener(new A());
		m6.addActionListener(new A());
                m7.addActionListener(new A());
                m8.addActionListener(new A());
		m9.addActionListener(new A());
                

		b1.addActionListener(new C());
		b2.addActionListener(new C());
                b3.addActionListener(new C());
	b4.addActionListener(new C());	
		addWindowListener(new B());

		setSize(500,500);
		setVisible(true);

	try
		{

				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			cn=DriverManager.getConnection("jdbc:odbc:jvvnl","","");
				
				t1.setEditable(false);
				t6.setEditable(false);
				t2.setEditable(false);
				t3.setEditable(false); 
				t4.setEditable(false);
				t5.setEditable(false);  
				
			        t1.setText("");
				t2.setText("");
				t3.setText("");
				t4.setText("");
				t5.setText("");
				t6.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}


	class A implements ActionListener
	{
                int cost=2;
                public void actionPerformed(ActionEvent e)
		{
			MenuItem x=(MenuItem) e.getSource();
			if(x.equals(m4))	
				System.exit(0);
			else if(x.equals(m1))
			{
				t1.setText("");
				t2.setText("");
				t3.setText("");
				t4.setText("");
				t5.setText("");
				t6.setText("");
				try
				{
					String str="SELECT * FROM rec";
					st=cn.createStatement();
					rs=st.executeQuery(str);
					if(rs.next()==false)
						t1.setText(""+1);
					else
					{
						count=rs.getInt(1);
						while(rs.next())
						{
							count=rs.getInt(1);
						}
						t1.setText(""+(count+1));
					}
				}
				catch(Exception y)
				{
					System.out.println(y);
				}
			}
			else if(x.equals(m2))
			{
				try
				{
					String str="DELETE FROM rec WHERE meterno=?";
					pst=cn.prepareStatement(str);
					String id=JOptionPane.showInputDialog(null,"Enter the Customer's Meter No");
					int meterno;
					if(id==null)
						meterno=0;
					else
						meterno=Integer.parseInt(id);
					pst.setInt(1,meterno);
					int a=pst.executeUpdate();
					if(a<1)
						JOptionPane.showMessageDialog(null,"Record Not Found");
					else
					{
                                                JOptionPane.showMessageDialog(null,"Record Has Been Deleted");
						total_rec--;
					}
					
				}
				catch(Exception y)
				{
					
				}
			}
			
                        else if(x.equals(m3))
			{
				t1.setEditable(true);
				JOptionPane.showMessageDialog(null,"Please change the values and Press Update Button");
			}

			else if(x.equals(m5))
			{
				t1.setEditable(false);
                                String id=JOptionPane.showInputDialog(null,"Enter the no. of units consumed in the past month");
   
                                int bill=Integer.parseInt(id)*cost;

                                JOptionPane.showMessageDialog(null,Integer.toString(bill)+" Rs.is your bill");
			}                        

			else if(x.equals(m6))
			{
				t1.setEditable(false);
                                JOptionPane.showMessageDialog(null,"The cost is "+Integer.toString(cost)+" rupees per unit consumed");
			}

			else if(x.equals(m7))
			{
				t1.setEditable(false);
                                JOptionPane.showMessageDialog(null,"These are the instructions...");
			}

			else if(x.equals(m8))
			{
				t1.setEditable(true);
				t6.setEditable(true);
				t2.setEditable(false);
				t3.setEditable(false); 
				t4.setEditable(false);
				t5.setEditable(false);         
                                        
					t1.setText("");
					t2.setText("");
					t3.setText("");
					t4.setText("");
					t5.setText("");
					t6.setText("");
                               
                                JOptionPane.showMessageDialog(null,"Enter your meter no. and query, and then press 'Submit Query' button");
			}

			
			else if(x.equals(m9))
			{
				t1.setEditable(true);
				t6.setEditable(false);
				t2.setEditable(false);
				t3.setEditable(false); 
				t4.setEditable(false);
				t5.setEditable(false);         
                                        
					t1.setText("");
					t2.setText("");
					t3.setText("");
					t4.setText("");
					t5.setText("");
					t6.setText("");
				JOptionPane.showMessageDialog(null,"Enter your meter no. and then press 'Show' button");	
			}

						
			
		}


	}
	
	class B extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}

	class C implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String str1=e.getActionCommand();
			if(str1.equals("Submit"))
			{
				try
				{
					String str="INSERT INTO rec VALUES(?,?,?,?,?.?)";
					pst=cn.prepareStatement(str);
					
					pst.setInt(1,Integer.parseInt(t1.getText()));
					pst.setString(2,t2.getText());
					pst.setString(3,t3.getText());
					pst.setString(4,t4.getText());
					pst.setString(5,t5.getText());
					pst.setString(6,t6.getText());
									
					pst.executeUpdate();
					total_rec++;
					count=count+1;
					t1.setText(""+count);
					t2.setText("");
					t3.setText("");
					t4.setText("");
					t5.setText("");
					t6.setText("");

				}
				catch(Exception y)
				{
					System.out.println(y);
				}
			}
			else if(str1.equals("Update"))
			{
				try
				{
                                        
                                        int meterno=Integer.parseInt(t1.getText());
					String str="UPDATE rec SET name=? WHERE meterno=?";
					String str2="UPDATE rec SET city=? WHERE meterno=?";
					String str3="UPDATE rec SET address=? WHERE meterno=?";
					String str4="UPDATE rec SET phoneno=? WHERE meterno=?";
					
					pst=cn.prepareStatement(str);
					pst.setString(1,t2.getText());
					pst.setInt(2,meterno);
					pst.executeUpdate();

					pst=cn.prepareStatement(str2);
					pst.setString(1,t3.getText());
					pst.setInt(2,meterno);
					pst.executeUpdate();

					pst=cn.prepareStatement(str3);
					pst.setString(1,t4.getText());
					pst.setInt(2,meterno);
					pst.executeUpdate();

					pst=cn.prepareStatement(str4);
					pst.setString(1,t5.getText());
					pst.setInt(2,meterno);
					pst.executeUpdate();

					t1.setText("");
					t2.setText("");
					t3.setText("");
					t4.setText("");
					t5.setText("");
					t6.setText("");
					
					JOptionPane.showMessageDialog(null,"Updation has been done");

					t1.setEditable(false);

				}
				catch(Exception y)
				{
					System.out.println(y);
				}
			}
                        

			else if(str1.equals("Show"))
			{
				try
				{
                                        
                                        int meterno=Integer.parseInt(t1.getText());
					String str="SELECT * FROM rec";
					st=cn.createStatement();
					rs=st.executeQuery(str);
					
		while(rs.next())
				{
					

					if(rs.getInt(1)==meterno)
					{
							
					t2.setText(rs.getString(2));
					t3.setText(rs.getString(3));
					t4.setText(rs.getString(4));
					t5.setText(rs.getString(5));
					t6.setText(rs.getString(6));											
					}			
				}
					
					t1.setEditable(false);

				}
				catch(Exception y)
				{
					System.out.println(y);
				}
			}

		else
                                if(str1.equalsIgnoreCase("Submit Query"))
                                {
				try
				{
                                        
                                        int meterno=Integer.parseInt(t1.getText());
                                        String str="UPDATE rec SET query=? WHERE meterno=?";

					pst=cn.prepareStatement(str);
                                        pst.setString(1,t6.getText());
					pst.setInt(2,meterno);
					pst.executeUpdate();

					t1.setText("");
					t2.setText("");
					t3.setText("");
					t4.setText("");
					t5.setText("");
                                        t6.setText("");
					
                                        JOptionPane.showMessageDialog(null,"Updation in Query has been done");

		        t1.setEditable(false);
                                        t2.setEditable(false);
                                        t3.setEditable(false);
                                        t4.setEditable(false);
                                        t5.setEditable(false);
		        t6.setEditable(false);	

				}
				catch(Exception y)
				{
					System.out.println(y);
				}

                                }
		}
	
	}

	public static void main(String args[])
	{
                Project2 x=new Project2();
	}



 }
	
