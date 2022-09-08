package jdbcApps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeManagementPortal {

	public static void main(String[] args) {

		Scanner scn = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement ps = null;
		String qry = "";

		System.out.println("Welcome to Employee Management Portal!!!");
		System.out.println("Please login with your credentials");    // login information
		System.out.println("Please enter username");
		String username = scn.next();
		System.out.println("Please enter password");
		String password = scn.next();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeemanagementportal", "root", "root");
			qry = "select count(*) from admin_info where username = ? and password = ?";
			ps = conn.prepareStatement(qry);

			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet result = ps.executeQuery();
			result.next();
			int count = result.getInt(1);     //checking login details
			if (count == 1) {
				System.out.println("login succesful");
				while (true) {         // options for doing continuous  crud operations until user wants to exit 

					System.out.println("choose your option: \n\t 1.To insert employee details \n\t "     // options
							+ "2.Show all employee details \n\t" + " 3.Update employee details \n\t"
							+ " 4.Delete employee details \n\t " + "5. exit\n\t");
					int n = scn.nextInt();

					if (n == 1) {                               // Insert

						System.out.println("Please enter new employee details");
						System.out.println("please enter employee id");
						int id = scn.nextInt();
						System.out.println("please enter employee name");
						String name = scn.next();
						System.out.println("please enter your mobileno");
						long mobileno = scn.nextLong();
						System.out.println("please enter your dept");
						String dept = scn.next();
						System.out.println("please enter your location");
						String location = scn.next();
						qry = "insert into employee_info values(?,?,?,?,?)";
						try {
							ps = conn.prepareStatement(qry);
							ps.setInt(1, id);
							ps.setString(2, name);
							ps.setLong(3, mobileno);
							ps.setString(4, dept);
							ps.setString(5, location);
							// send & execute sql query
							int r = ps.executeUpdate();
							// process or gather results
							if (r == 0) {
								System.out.println("insertion is not successful\n");
							} else {
								System.out.println("Thank you...insertion succesful\n");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					} else if (n == 2) {                     // reading table data

						System.out.println("Employee details:- ");
						qry = "select * from employee_info";
						ps = conn.prepareStatement(qry);
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getLong(3) + " "
									+ rs.getString(4) + " " + rs.getString(5));
						}
						System.out.println();
						
					} else if (n == 3) {                      // update 
						System.out.println("Please enter Employee id");
						int eid = scn.nextInt();
						System.out.println("Please enter new Mobile number");
						long emn = scn.nextLong();
						System.out.println("Please enter new location");
						String eloc = scn.next();
						qry = "update employee_info set mobile_no = ?, location = ? where id = ?";
						ps = conn.prepareStatement(qry);
						ps.setLong(1, emn);
						ps.setString(2, eloc);
						ps.setInt(3, eid);

						int update = ps.executeUpdate();
						if (update == 1) {
							System.out.println("Details are updated\n");
						} else {
							System.out.println("Details are not updated\n");
						}
						
						
						
					} else if (n == 4) {                               // delete 
						System.out.println("please enter employee id");
						int deleteId = scn.nextInt();
						qry = "delete from employee_info where id = ?";
						ps = conn.prepareStatement(qry);
						ps.setInt(1, deleteId);
						int deleteRow = ps.executeUpdate();
						if (deleteRow == 1) {
							System.out.println("Record deleted!!!\n");
						} else {
							System.out.println("Record is not deleted\n");
						}
					}

					else if (n == 5) {                               // to come out of the database or table
						System.out.println("Thank you...your crud operations are performed.\n");
						break;
			
					} else {
						System.out.println("Please enter valid option!!!\n");   // for choosing wrong option

					}
				}

				
			} else {
				System.out.println("invalid credentials...");   //if user name and password doesn't match
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {                       //closing all objects
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scn.close();
		}

	}

}
