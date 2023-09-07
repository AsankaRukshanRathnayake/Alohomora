import java.util.Scanner;
import java.sql.*;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        
        //initial registration*****************************8

        String username;
        int courseDuration;

        System.out.println("----GPA Calculator----");
        System.out.print("Enter your name : ");
        username=input.nextLine();
        System.out.print("Course Duration (In years) : ");
        courseDuration=input.nextInt();

        System.out.println();
        
        //Main menu****************************************

            System.out.println("----GPA Calculator----");
            System.out.println();
            System.out.println("Hello " + username + ", Welcome Back");
            System.out.println();

            while(true) {
            System.out.println("----Menu----");
            System.out.println("1. View Results");
            System.out.println("2. Add Results");
            System.out.println("3. Export data");
            System.out.println("4. Clear data");
            System.out.println("5. Exit");
            System.out.println("");

            System.out.print("Enter your choice : ");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    viewResults();
                    break;

                case 2:
                    //Add results();
                    addResults();
                    break;

                case 3:
                    //Export Data();
                    break;

                case 4:
                    //Clear data:
                    break;

                case 5:
                    //Exit:
                    exit(1);

                default:
                    System.out.println("Wrong choice! Try again.");
            }
            
            System.out.println(" ");

        }

    }
    
    //Option to add results.***************************************888

    public static void addResults(){
        Scanner input = new Scanner(System.in);

        int year;
        String courseCode;
        String grade;
        int credits;
        double gpa;


        System.out.println("----Add Results----");
        System.out.print("Year (1,2,3,4) : ");
        year=input.nextInt();

        System.out.print("Course Code : ");
        courseCode=input.next();
        

        System.out.print("Grade : ");
        grade=input.next();

        System.out.print("Credits : ");
        credits=input.nextInt();

        System.out.print("Confirm (y/n) : ");

        String choice = input.next();

        if (choice.charAt(0)=='y'){
            String url = "jdbc:mysql://localhost:3306/Alohomora";
            String username = "root";
            String password = "1234";

            try {
                Connection c1 = DriverManager.getConnection(url, username, password);

                String sql = "INSERT INTO results (Year,CourseCode,Grade,Credits,GPA) VALUES(?, ?, ?, ?, ?)";

                PreparedStatement s1 = c1.prepareStatement(sql);
                s1.setInt(1,year);
                s1.setString(2,courseCode);
                s1.setString(3, grade);
                s1.setInt(4,credits);
                
                //Create another column in the results table to store values for grades.**************************************888
                
                if (grade.equals("A+"))
                	gpa=4.0;
                else if (grade.equals("A"))
                	gpa=4.0;
                else if (grade.equals("A-"))
                	gpa=4.0;
                else if (grade.equals("B+"))
                	gpa=3.7;
                else if (grade.equals("B"))
                	gpa=3.3;
                else if (grade.equals("B-"))
                	gpa=3.0;
                else if (grade.equals("C+"))
                	gpa=2.7;
                else if (grade.equals("C"))
                	gpa=2.3;
                else if (grade.equals("C-"))
                	gpa=2.0;
                else if (grade.equals("D+"))
                	gpa=1.7;
                else if (grade.equals("D"))
                	gpa=1.0;
                else 
                	gpa=0.0;
                
                s1.setDouble(5, gpa);
                
                System.out.println("");

                int rows = s1.executeUpdate();
                if (rows>0) {
                    System.out.println("Record was added");
                }

                s1.close();
                c1.close();

            } catch (SQLException e) {
                System.out.println("Oops! Error.");
                e.printStackTrace();
            }
        }
    }
    
    
    //Option for viewing results.*******************************************************************8
    
    public static void viewResults() {
    	Scanner input = new Scanner(System.in);
    	
    	String url = "jdbc:mysql://localhost:3306/Alohomora";
		String username = "root";
		String password = "1234";
		
		double totalGPA=0;
		double totalCredits = 0;
		double finalGPA;
		
		try {
			Connection c1 = DriverManager.getConnection(url, username, password);
			//System.out.println("Connected successfully");
			
			String sql = "SELECT * FROM results";
			java.sql.Statement s1 = c1.createStatement();
			ResultSet result = s1.executeQuery(sql);
			
				while(result.next()) {
					String courseCode = result.getString(3);
					int credits = result.getInt(5);
					double gpaForRecord = result.getDouble(6);
					
					totalGPA = totalGPA + (gpaForRecord*credits);				
				}
				
			String sql2 = "select SUM(credits) from results;";
			java.sql.Statement s2 = c1.createStatement();
			ResultSet result2 = s2.executeQuery(sql2);
			 
			while (result2.next()) {
			totalCredits = result2.getInt(1);
			
			System.out.printf("Current GPA : %.2f\n",(totalGPA/totalCredits));
			}
			
			String sql3 = "select count(coursecode) from results;";
			java.sql.Statement s3 = c1.createStatement();
			ResultSet result3 = s3.executeQuery(sql3);
			
			while (result3.next()) {
			int totalcoursecodes = result3.getInt(1);
			
			System.out.println("No of subjects : "+totalcoursecodes);
			}
			System.out.println("Total Credits : "+totalCredits);
			
			
			System.out.println("To view Records");
			System.out.println("1. Year1");
			System.out.println("2. Year2");
			System.out.println("3. Year3");
			System.out.println("4. Year4");
			
			System.out.println("0. Back");
			System.out.println("");
			System.out.print("Enter your choice : ");
			int yearchoice = input.nextInt();
			
			switch(yearchoice) {
			
			case 1:
				viewResultsYear1();
				break;
				
			case 2:
				viewResultsYear2();
				break;
				
			case 3:
				viewResultsYear3();
				break;
				
			case 4:
				viewResultsYear4();
				break;
				
			case 0:
				return;
			}
			
			
			c1.close();
			
		} catch (SQLException e) {
			System.out.println("Oops! Error.");
			e.printStackTrace();
		} 
	}
    
    //following method support to view results in respective years*************************************
    
    public static void viewResultsYear1() {
        Scanner input = new Scanner(System.in);
    	
    	String url = "jdbc:mysql://localhost:3306/Alohomora";
		String username = "root";
		String password = "1234";
		
		double totalGPA=0;
		double totalCredits = 0;
		double finalGPA;
		int totalcoursecodes = 0;
		
		try {
			Connection c1 = DriverManager.getConnection(url, username, password);
			//System.out.println("Connected successfully");
			
			String sql = "SELECT * FROM results group by year having year = 1 ";
			java.sql.Statement s1 = c1.createStatement();
			ResultSet result = s1.executeQuery(sql);
			
				while(result.next()) {
					int id=result.getInt(1);
					int year = result.getInt(2);
					String courseCode = result.getString(3);
					String grade = result.getString(4);
					int credits = result.getInt(5);
					double gpaForRecord = result.getDouble(6);

					
					totalGPA = totalGPA + (gpaForRecord*credits);				
					
					System.out.println("----Year1----");
					System.out.println(id+". Course Code : "+courseCode);
					System.out.println("     Grade : "+grade);
					System.out.println("     Credits : "+credits);
					System.out.println("");
				}
			
			
			String sql2 = "select SUM(credits) from results group by year having year = 1;";
			java.sql.Statement s2 = c1.createStatement();
			ResultSet result2 = s2.executeQuery(sql2);
			 
			while (result2.next()) {
			totalCredits = result2.getInt(1);
			
			
			}
			
			String sql3 = "select count(coursecode) from results group by year having year = 1;";
			java.sql.Statement s3 = c1.createStatement();
			ResultSet result3 = s3.executeQuery(sql3);
			
			while (result3.next()) {
			totalcoursecodes = result3.getInt(1);
			
			}
			System.out.println("No of subjects : "+totalcoursecodes);
			System.out.println("Total Credits : "+totalCredits);
			System.out.printf("GPA for Year1: %.2f\n",(totalGPA/totalCredits));
			
	
			c1.close();
			
		} catch (SQLException e) {
			System.out.println("Oops! Error.");
			e.printStackTrace();
		} 
    }
    
    
    public static void viewResultsYear2() {
        Scanner input = new Scanner(System.in);
    	
    	String url = "jdbc:mysql://localhost:3306/Alohomora";
		String username = "root";
		String password = "1234";
		
		double totalGPA=0;
		double totalCredits = 0;
		double finalGPA;
		int totalcoursecodes = 0;
		
		try {
			Connection c1 = DriverManager.getConnection(url, username, password);
			//System.out.println("Connected successfully");
			
			String sql = "SELECT * FROM results group by year having year = 2 ";
			java.sql.Statement s1 = c1.createStatement();
			ResultSet result = s1.executeQuery(sql);
			
				while(result.next()) {
					int id=result.getInt(1);
					int year = result.getInt(2);
					String courseCode = result.getString(3);
					String grade = result.getString(4);
					int credits = result.getInt(5);
					double gpaForRecord = result.getDouble(6);
					
					totalGPA = totalGPA + (gpaForRecord*credits);				
					
					System.out.println("----Year2----");
					System.out.println(id+". Course Code : "+courseCode);
					System.out.println("     Grade : "+grade);
					System.out.println("     Credits : "+credits);
					System.out.println("");
				}
			
			
			String sql2 = "select SUM(credits) from results group by year having year = 2;";
			java.sql.Statement s2 = c1.createStatement();
			ResultSet result2 = s2.executeQuery(sql2);
			 
			while (result2.next()) {
			totalCredits = result2.getInt(1);
			
			
			}
			
			String sql3 = "select count(coursecode) from results group by year having year = 2;";
			java.sql.Statement s3 = c1.createStatement();
			ResultSet result3 = s3.executeQuery(sql3);
			
			while (result3.next()) {
			totalcoursecodes = result3.getInt(1);
			
			}
			System.out.println("No of subjects : "+totalcoursecodes);
			System.out.println("Total Credits : "+totalCredits);
			System.out.printf("GPA for Year2: %.2f\n",(totalGPA/totalCredits));
			
	
			c1.close();
			
		} catch (SQLException e) {
			System.out.println("Oops! Error.");
			e.printStackTrace();
		} 
    }
    
    public static void viewResultsYear3() {
        Scanner input = new Scanner(System.in);
    	
    	String url = "jdbc:mysql://localhost:3306/Alohomora";
		String username = "root";
		String password = "1234";
		
		double totalGPA=0;
		double totalCredits = 0;
		double finalGPA;
		int totalcoursecodes = 0;
		
		try {
			Connection c1 = DriverManager.getConnection(url, username, password);
			//System.out.println("Connected successfully");
			
			String sql = "SELECT * FROM results group by year having year = 3 ";
			java.sql.Statement s1 = c1.createStatement();
			ResultSet result = s1.executeQuery(sql);
			
				while(result.next()) {
					int id=result.getInt(1);
					int year = result.getInt(2);
					String courseCode = result.getString(3);
					String grade = result.getString(4);
					int credits = result.getInt(5);
					double gpaForRecord = result.getDouble(6);
					
					totalGPA = totalGPA + (gpaForRecord*credits);				
					
					System.out.println("----Year1----");
					System.out.println(id+". Course Code : "+courseCode);
					System.out.println("     Grade : "+grade);
					System.out.println("     Credits : "+credits);
					System.out.println("");
				}
			
			
			String sql2 = "select SUM(credits) from results group by year having year = 3;";
			java.sql.Statement s2 = c1.createStatement();
			ResultSet result2 = s2.executeQuery(sql2);
			 
			while (result2.next()) {
			totalCredits = result2.getInt(1);
			
			
			}
			
			String sql3 = "select count(coursecode) from results group by year having year = 3;";
			java.sql.Statement s3 = c1.createStatement();
			ResultSet result3 = s3.executeQuery(sql3);
			
			while (result3.next()) {
			totalcoursecodes = result3.getInt(1);
			
			}
			System.out.println("No of subjects : "+totalcoursecodes);
			System.out.println("Total Credits : "+totalCredits);
			System.out.printf("GPA for Year3: %.2f\n",(totalGPA/totalCredits));
			
	
			c1.close();
			
		} catch (SQLException e) {
			System.out.println("Oops! Error.");
			e.printStackTrace();
		} 
    }
    
    
    public static void viewResultsYear4() {
        Scanner input = new Scanner(System.in);
    	
    	String url = "jdbc:mysql://localhost:3306/Alohomora";
		String username = "root";
		String password = "1234";
		
		double totalGPA=0;
		double totalCredits = 0;
		double finalGPA;
		int totalcoursecodes = 0;
		
		try {
			Connection c1 = DriverManager.getConnection(url, username, password);
			//System.out.println("Connected successfully");
			
			String sql = "SELECT * FROM results group by year having year = 4 ";
			java.sql.Statement s1 = c1.createStatement();
			ResultSet result = s1.executeQuery(sql);
			
				while(result.next()) {
					int id=result.getInt(1);
					int year = result.getInt(2);
					String courseCode = result.getString(3);
					String grade = result.getString(4);
					int credits = result.getInt(5);
					double gpaForRecord = result.getDouble(6);
					
					totalGPA = totalGPA + (gpaForRecord*credits);				
					
					System.out.println("----Year4----");
					System.out.println(id+". Course Code : "+courseCode);
					System.out.println("     Grade : "+grade);
					System.out.println("     Credits : "+credits);
					System.out.println("");
				}
			
			
			String sql2 = "select SUM(credits) from results group by year having year = 4;";
			java.sql.Statement s2 = c1.createStatement();
			ResultSet result2 = s2.executeQuery(sql2);
			 
			while (result2.next()) {
			totalCredits = result2.getInt(1);
			
			
			}
			
			String sql3 = "select count(coursecode) from results group by year having year = 4;";
			java.sql.Statement s3 = c1.createStatement();
			ResultSet result3 = s3.executeQuery(sql3);
			
			while (result3.next()) {
			totalcoursecodes = result3.getInt(1);
			
			}
			System.out.println("No of subjects : "+totalcoursecodes);
			System.out.println("Total Credits : "+totalCredits);
			System.out.printf("GPA for Year4: %.20f\n",(totalGPA/totalCredits));
			
	
			c1.close();
			
		} catch (SQLException e) {
			System.out.println("Oops! Error.");
			e.printStackTrace();
		} 
    }  
 }

