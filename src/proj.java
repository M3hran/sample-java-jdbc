// JDBC libraries
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// JDK libraries
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class proj 
{ 
    String driver = "oracle.jdbc.driver.OracleDriver";
    String jdbc_url = "jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g";
    //MUST ENTER ORACLE USERNAME AND PASSWORD
    private static String username;
    private static String  password;
    
    public String getu()
    {
	return this.username;
    }
    public String getp()
    {
	return this.password;
    }
    public void setu(String u)
    {
	this.username =u;
    }
    public void setp(String p)
    {
	this.password=p;
    }

    private static void menu()
    {
	System.out.println();
	System.out.println("______MENU______");
	System.out.println("1.Display Tables");
	System.out.println("2.Modify Tables");
	System.out.println("3.Search for Deals");
	System.out.println("4.Open Query");
	System.out.println("5.Exit");
	System.out.print("Enter: ");

    }
    private static void moddbmenu()
    {
	System.out.println("----Modify Menu----");
	System.out.println("1.Add record");
	System.out.println("2.Delete record");
	System.out.print("Enter#: ");
    }
    private static void searchmenu()
    {
	System.out.println("----Search for Deals By:----");
	System.out.println("1.Merchant name");
	System.out.println("2.Location");
	System.out.print("Enter#: ");
    }
    private static void dbmenu()
    {
	System.out.println("----Select DB----");
	System.out.println("1. Customer");
	System.out.println("2. Deal");
	System.out.println("3. Merchant");
	System.out.println("4. Purchase");
	System.out.println("5. Branch");
	System.out.println("6. Credit");
	System.out.println("7. Voucher");
	System.out.println("8. Dealtype");
	System.out.println("9. Interest");
	System.out.println("10.Location");
	System.out.println("11.Review");
	System.out.print("Enter#: ");
    }
    private static void addmenu()
    {
	System.out.println("----Select DB----");
	System.out.println("1. Customer");
	System.out.println("2. Deal");
	System.out.println("3. Merchant");
	System.out.println("4. Purchase");
	System.out.print("Enter#: ");
    }
    //populating tables from file
    private static void poptables(String input)
    {
	int db=0;
	int fd=0;
	
	StringTokenizer in = new StringTokenizer(input);

	while (in.hasMoreTokens()) {
		String t= in.nextToken();

		//parse block for CUSTOMER		
		if (db==0){	
		System.out.println("\nPopulating table: CUSTOMER");
		db=db+1;
		while(!t.equals("#")){
			fd=0;
		    Customer c=new Customer();
			//parse line			
			while(!t.equals(";")){
			
			    if (fd==0){c.setemail(t);}	
				if (fd==1){c.setfname(t);}
				if (fd==2){c.setlname(t);}
				if (fd==3){c.setage(Integer.parseInt(t));}
		   		if (fd==4){c.setgender(t);}
				if (fd==5){c.setaddress(t);}
				fd=fd+1;
				t= in.nextToken();
			}
			t=in.nextToken();
			try
		    {
			c.insertData();
		    }catch(SQLException sqlException)
		    {
			while (sqlException != null)
			    {
				sqlException.printStackTrace();
				sqlException = sqlException.getNextException();
			    }
		    }catch (Exception e)
		    {
			e.printStackTrace();
		    }
			//END OF LINE
	      } 
		}//END OF CUSTOMER BLCOK
		
		//parse block for CREDIT
		if (db==1){	
		System.out.println("\nPopulating table: CREDIT");
		db=db+1;
		t=in.nextToken();
		while(!t.equals("#")){
			fd=0;
		    Credit c=new Credit();
			//parse line			
			while(!t.equals(";")){
			
			    if (fd==0){c.setcid(t);}	
				if (fd==1){c.setemail(t);}
				if (fd==2){c.setDefaultcc(t);}
				if (fd==3){c.setExpire_Date(t);}
		   		if (fd==4){c.setcc_number(Integer.parseInt(t));}

				fd=fd+1;
				t= in.nextToken();
			}
			t=in.nextToken();
			try
		    {
			c.insertData();
		    }catch(SQLException sqlException)
		    {
			while (sqlException != null)
			    {
				sqlException.printStackTrace();
				sqlException = sqlException.getNextException();
			    }
		    }catch (Exception e)
		    {
			e.printStackTrace();
		    }
			//END OF LINE
	      } 
		}//END OF Credit BLCOK
		
		//parse block for DEALTYPE
		if (db==2){	
		System.out.println("\nPopulating table: DEALTYPE");
		db=db+1;
		t=in.nextToken();
		while(!t.equals("#")){
			fd=0;
		    Dealtype c=new Dealtype();
			//parse line			
			while(!t.equals(";")){
			
			    if (fd==0){c.setCategory(t);}	
	
				fd=fd+1;
				t= in.nextToken();
			}
			t=in.nextToken();
			try
		    {
			c.insertData();
		    }catch(SQLException sqlException)
		    {
			while (sqlException != null)
			    {
				sqlException.printStackTrace();
				sqlException = sqlException.getNextException();
			    }
		    }catch (Exception e)
		    {
			e.printStackTrace();
		    }
			//END OF LINE
	      } 
		}//END OF dealtype block
		
		//parse block from Merchant
		if (db==3){	
		System.out.println("\nPopulating table: MERCHANT");
		db=db+1;
		t=in.nextToken();
		while(!t.equals("#")){
			fd=0;
		    Merchant c=new Merchant();
			//parse line			
			while(!t.equals(";")){
			
			    if (fd==0){c.setmid(t);}	
				if (fd==1){c.setname(t);}
				if (fd==2){c.sethq(t);}

				fd=fd+1;
				t= in.nextToken();
			}
			t=in.nextToken();
			try
		    {
			c.insertData();
		    }catch(SQLException sqlException)
		    {
			while (sqlException != null)
			    {
				sqlException.printStackTrace();
				sqlException = sqlException.getNextException();
			    }
		    }catch (Exception e)
		    {
			e.printStackTrace();
		    }
			//END OF LINE
	      } 
		}//END OF merchant block
		
		//parse block for Location
		if (db==4){	
		System.out.println("\nPopulating table: LOCATION");
		db=db+1;
		t=in.nextToken();
		while(!t.equals("#")){
			fd=0;
		    Location c=new Location();
			//parse line			
			while(!t.equals(";")){
			
			        if (fd==0){c.setlid(t);}	
				if (fd==1){c.setemail(t);}
				if (fd==2){c.setCity(t);}
				if (fd==3){c.setState(t);}
				if (fd==4){c.setCountry(t);}
				if (fd==5){c.setContinent(t);}
				if (fd==6){c.setDefualtadd(Integer.parseInt(t));}
				
				fd=fd+1;
				t= in.nextToken();
			}
			t=in.nextToken();
			try
		    {
			c.insertData();
		    }catch(SQLException sqlException)
		    {
			while (sqlException != null)
			    {
				sqlException.printStackTrace();
				sqlException = sqlException.getNextException();
			    }
		    }catch (Exception e)
		    {
			e.printStackTrace();
		    }
			//END OF LINE
	      } 
		}//END OF location block
		
		//Parse block for interest
		if (db==5){	
		System.out.println("\nPopulating table: INTEREST");
		db=db+1;
		t=in.nextToken();
		while(!t.equals("#")){
			fd=0;
		    Interest c=new Interest();
			//parse line			
			while(!t.equals(";")){
			
			    if (fd==0){c.setiid(t);}	
				if (fd==1){c.setemail(t);}
				if (fd==2){c.setCategory(t);}

				fd=fd+1;
				t= in.nextToken();
			}
			t=in.nextToken();
			try
		    {
			c.insertData();
		    }catch(SQLException sqlException)
		    {
			while (sqlException != null)
			    {
				sqlException.printStackTrace();
				sqlException = sqlException.getNextException();
			    }
		    }catch (Exception e)
		    {
			e.printStackTrace();
		    }
			//END OF LINE
	      } 
		}//END OF Interest block
		
		//parse block for Deal
				if (db==6){	
				System.out.println("\nPopulating table: DEAL");
				db=db+1;
				t=in.nextToken();
				while(!t.equals("#")){
					fd=0;
				    Deal c=new Deal();
					//parse line			
					while(!t.equals(";")){
					
					        if (fd==0){c.setdid(t);}	
						if (fd==1){c.setCategory(t);}
						if (fd==2){c.setmid(t);}
						if (fd==3){c.setExpires(t);}
						if (fd==4){c.setStart_Date(t);}
						if (fd==5){c.setEnd_Date(Integer.parseInt(t));}
						if (fd==6){c.setorg_price(Integer.parseInt(t));}
						if (fd==7){c.setDeal_price(Integer.parseInt(t));}
						if (fd==8){c.setqty(Integer.parseInt(t));}
						if (fd==9){c.setdescription(t);}
						
						fd=fd+1;
						t= in.nextToken();
					}
					t=in.nextToken();
					try
				    {
					c.insertData();
				    }catch(SQLException sqlException)
				    {
					while (sqlException != null)
					    {
						sqlException.printStackTrace();
						sqlException = sqlException.getNextException();
					    }
				    }catch (Exception e)
				    {
					e.printStackTrace();
				    }
					//END OF LINE
			      } 
				}//END OF deal block
		
				//parse block for Review
				if (db==7){	
				System.out.println("\nPopulating table: REVIEW");
				db=db+1;
				t=in.nextToken();
				while(!t.equals("#")){
					fd=0;
				    Review c=new Review();
					//parse line			
					while(!t.equals(";")){
					
					    if (fd==0){c.setrid(t);}	
						if (fd==1){c.setdid(t);}
						if (fd==2){c.setmid(t);}
						if (fd==3){c.setemail(t);}
						if (fd==4){c.setRating(Integer.parseInt(t));}
						if (fd==5){c.setdescription(t);}

						fd=fd+1;
						t= in.nextToken();
					}
					t=in.nextToken();
					try
				    {
					c.insertData();
				    }catch(SQLException sqlException)
				    {
					while (sqlException != null)
					    {
						sqlException.printStackTrace();
						sqlException = sqlException.getNextException();
					    }
				    }catch (Exception e)
				    {
					e.printStackTrace();
				    }
					//END OF LINE
			      } 
				}//END OF Review block
		
				//parse block for Purchase
				if (db==8){	
				System.out.println("\nPopulating table: PURCHASE");
				db=db+1;
				t=in.nextToken();
				while(!t.equals("#")){
					fd=0;
				    Purchase c=new Purchase();
					//parse line			
					while(!t.equals(";")){
					
					    if (fd==0){c.setpid(t);}	
						if (fd==1){c.setrid(t);}
						if (fd==2){c.setemail(t);}
						if (fd==3){c.setdate(t);}
						if (fd==4){c.setqty(Integer.parseInt(t));}

						fd=fd+1;
						t= in.nextToken();
					}
					t=in.nextToken();
					try
				    {
					c.insertData();
				    }catch(SQLException sqlException)
				    {
					while (sqlException != null)
					    {
						sqlException.printStackTrace();
						sqlException = sqlException.getNextException();
					    }
				    }catch (Exception e)
				    {
					e.printStackTrace();
				    }
					//END OF LINE
			      } 
				}//END OF Purchase block
				
				//parse block for Branch
				if (db==9){	
				System.out.println("\nPopulating table: BRANCH");
				db=db+1;
				t=in.nextToken();
				while(!t.equals("#")){
					fd=0;
				    Branch c=new Branch();
					//parse line			
					while(!t.equals(";")){
					
					    if (fd==0){c.setbid(t);}	
						if (fd==1){c.setstreet(t);}
						if (fd==2){c.setCity(t);}
						if (fd==3){c.setState(t);}
						if (fd==4){c.setzip(Integer.parseInt(t));}
						if (fd==5){c.setCountry(t);}
						if (fd==6){c.setdid(t);}

						fd=fd+1;
						t= in.nextToken();
					}
					t=in.nextToken();
					try
				    {
					c.insertData();
				    }catch(SQLException sqlException)
				    {
					while (sqlException != null)
					    {
						sqlException.printStackTrace();
						sqlException = sqlException.getNextException();
					    }
				    }catch (Exception e)
				    {
					e.printStackTrace();
				    }
					//END OF LINE
			      } 
				}//END OF Branch block
				
				//parse block for Voucher
				if (db==10){	
				System.out.println("\nPopulating table: VOUCHER");
				db=db+1;
				t=in.nextToken();
				while(!t.equals("#")){
					fd=0;
				    Voucher c=new Voucher();
					//parse line			
					while(!t.equals(";")){
					
					    if (fd==0){c.setvid(t);}	
						if (fd==1){c.setpid(t);}
						if (fd==2){c.setvfor(t);}
						if (fd==3){c.setstatus(t);}
						if (fd==4){c.setbid(t);}
						if (fd==5){c.setdid(t);}

						fd=fd+1;
						t= in.nextToken();
					}
					t=in.nextToken();
					try
				    {
					c.insertData();
				    }catch(SQLException sqlException)
				    {
					while (sqlException != null)
					    {
						sqlException.printStackTrace();
						sqlException = sqlException.getNextException();
					    }
				    }catch (Exception e)
				    {
					e.printStackTrace();
				    }
					//END OF LINE
			      } 
				}//END OF Voucher block
				
	 }//END OF INPUT
    }//end of pop tables
    
    //drop all existing tables    
    private static void dropoldtables()
    {
	//drop CUSTOMER 
	Customer c1=new Customer();
	Connection con= c1.getConnection();
	try{
	    
	    if ( c1.doesTableExist(con))
		{
		    System.out.println("CUSTOMER table exists. Dropping old table.....");
		    c1.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }
	//drop Deal
	
	try{
	    Deal d=new Deal();
	    Connection con1= d.getConnection();
	    if (d.doesTableExist(con1))
		{
		    System.out.println("Deal table exists. Dropping old table.....");
		    d.removetable(con1);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }
	//drop Merchant

	try{
	    Merchant m=new Merchant();
	   Connection con2= m.getConnection();
	    if (m.doesTableExist(con2))
		{
		    System.out.println("Merchant table exists. Dropping old table.....");
		    m.removetable(con2);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }
	//drop Branch
	Branch t=new Branch();
	try{
	   
	    if ( t.doesTableExist(con))
		{
		    System.out.println("Branch table exists. Dropping old table.....");
		    t.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }
	//drop Purchase
	Purchase s1=new Purchase();
	try{
	   
	    if ( s1.doesTableExist(con))
		{
		    System.out.println("Purchase table exists. Dropping old table.....");
		    s1.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }

	//drop Credit
	Credit c2=new Credit();
	try{
	    
	    if ( c2.doesTableExist(con))
		{
		    System.out.println("Credit table exists. Dropping old table.....");
		    c2.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }

	//drop Interest
	Interest i=new Interest();
	try{
	   
	    if ( i.doesTableExist(con))
		{
		    System.out.println("Interest table exists. Dropping old table.....");
		    i.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }	

	//drop Dealtype
	Dealtype dd=new Dealtype();
	try{
	   
	    if ( dd.doesTableExist(con))
		{
		    System.out.println("Dealtype table exists. Dropping old table.....");
		    dd.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }	

	//drop Review
	Review r=new Review();
	try{
	 
	    if ( r.doesTableExist(con))
		{
		    System.out.println("Dealtype table exists. Dropping old table.....");
		    r.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }	
	
	//drop Location
	Location l=new Location();
	try{
	   
	    if ( l.doesTableExist(con))
		{
		    System.out.println("Location table exists. Dropping old table.....");
		    l.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }		
	
	//drop Voucher
	Voucher v=new Voucher();
	try{
	   
	    if ( v.doesTableExist(con))
		{
		    System.out.println("Voucher table exists. Dropping old table.....");
		    v.removetable(con);
		}
	
	}catch (SQLException sqlException){
	    while (sqlException != null)
		{
		    sqlException.printStackTrace();
		    sqlException = sqlException.getNextException();
		}
	}catch ( Exception e)
	    {
		e.printStackTrace();
	    }		
    }//end of droptables
    
    //add for Customer
    private static void addcustomer(){
    	boolean flag=true;
		//loop for adding records   
		while (flag){
			Scanner customer = new Scanner(System.in);
			Scanner customer2 =new Scanner(System.in);
			Scanner cc=new Scanner(System.in);
			Scanner ccc=new Scanner(System.in);
			Scanner cccc=new Scanner(System.in);
			Scanner ccccc=new Scanner(System.in);
			System.out.print("Enter Customer Email: ");
			String email=customer.nextLine();
			System.out.print("Enter First Name: ");
			String fname=customer2.next();
			System.out.print("Enter Last Name: ");
			String lname=cc.next();
			System.out.print("Enter Age: ");
			int age=ccc.nextInt();
			System.out.print("Enter Gender(M or F): ");
			String gender=cccc.next();
			System.out.print("Enter Address: ");
			String address=ccccc.nextLine();
			
			System.out.println("Inserting Customer...");
			Customer c=new Customer();
	  		c.setemail(email);
			c.setfname(fname);
			c.setlname(lname);
			c.setage(age);
			c.setgender(gender);
			c.setaddress(address);
			try
			    {
				c.insertData();
			    }catch(SQLException sqlException)
			    {
				while (sqlException != null)
				    {
					sqlException.printStackTrace();
					sqlException = sqlException.getNextException();
				    }
			    }catch (Exception e)
			    {
				e.printStackTrace();
			    }
			System.out.print("Add agian?(y/n): ");
			String again=customer.next();
			
			if (again.equals("n") || again.equals("N") ){
			    System.out.println("done");
			    flag=false;    
			}
		    }//end of add loop		
    }//end of add for customer
    
    //add for deal
    private static void adddeal(){
    	boolean flag=true;
        while (flag)
            {
                Scanner t = new Scanner(System.in);
                Scanner t1= new Scanner(System.in);
                Scanner t2= new Scanner(System.in);
                Scanner t3=new Scanner(System.in);
                Scanner t4=new Scanner(System.in);
                Scanner t5=new Scanner(System.in);
                Scanner t6=new Scanner(System.in);
                Scanner t7=new Scanner(System.in);
                Scanner t8=new Scanner(System.in);
                Scanner t9=new Scanner(System.in);

                System.out.print("Enter Deal ID: ");
                String did=t.next();
                System.out.print("Enter Merchent ID: ");
                String mid=t1.next();
                //add merchant first if it doesn't exist
                checkmid(mid);
                System.out.print("Enter Deal Description: ");
                String description=t2.nextLine();
                System.out.print("Enter Deal Category: ");
                String category=t3.nextLine();
                //add Category type first if it doesn't Exists
                checkcat(category);
                System.out.print("Enter Start Date: ");
                String Start_Date=t4.nextLine();
                System.out.print("Enter End Date: ");
                int End_Date=t5.nextInt();
                System.out.print("Enter Expires Date: ");
                String Expires=t6.nextLine();
                System.out.print("Enter Original Price: ");
                float org_price=t7.nextFloat();
                System.out.print("Enter Deal Price: ");
                float Deal_price=t8.nextFloat();
                System.out.print("Enter Quantity: ");
                int qty=t9.nextInt();

                System.out.println("Inserting Deal...");
                Deal c=new Deal();
                c.setdid(did);
                c.setmid(mid);
                c.setStart_Date(Start_Date);
                c.setEnd_Date(End_Date);
                c.setExpires(Expires);
                c.setdescription(description);
                c.setCategory(category);
                c.setorg_price(org_price);
                c.setDeal_price(Deal_price);
                c.setqty(qty);
                try
                    {
                        c.insertData();
                    }catch(SQLException sqlException)
                    {
                        while (sqlException != null)
                            {
                                sqlException.printStackTrace();
                                sqlException = sqlException.getNextException();
                            }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                System.out.print("Add agian?(y/n): ");
                String again=t4.next();
                if (again.equals("n") || again.equals("N") ){
                    System.out.println("done.");
                    flag=false;
                }
            }
    }//end of add for deal
    public static void addmerchant(){
		boolean flag=true;
        while (flag)
            {
                Scanner t = new Scanner(System.in);
                System.out.print("Enter Merchant's ID: ");
                String mid=t.nextLine();
                System.out.print("Enter Merchant's Name: ");
                String name=t.nextLine();
                System.out.print("Enter Merchant's HQ: ");
                String hq=t.nextLine();

                System.out.println("Inserting Merchant...");
                Merchant c=new Merchant();
                c.setmid(mid);
                c.setname(name);
                c.sethq(hq);
                try
                    {
                        c.insertData();
                    }catch(SQLException sqlException)
                    {
                        while (sqlException != null)
                            {
                                sqlException.printStackTrace();
                                sqlException = sqlException.getNextException();
                            }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                System.out.print("Add agian?(y/n): ");
                String again=t.next();
                if (again.equals("n") || again.equals("N") ){
                    System.out.println("done.");
                    flag=false;
                }
            }//end of while  
    }//end of add for merchant
    //add for review
    public static void addreview(){
		boolean flag=true;
        while (flag)
            {
                Scanner t = new Scanner(System.in);
                Scanner t1 = new Scanner(System.in);
                System.out.print("Enter Review ID: ");
                String rid=t.nextLine();
                System.out.print("Enter Deal ID: ");
                String did=t.nextLine();
                //check to see if deal exists
                checkdid(did);
                System.out.print("Enter Merchant ID: ");
                String mid=t.nextLine();
                //check to see if merchant exists
                checkmid(mid);
                System.out.print("Enter Customer Email: ");
                String email=t.nextLine();
                //check to see if customer exists
                checkmail(email);
                System.out.print("Enter Rating(1-5): ");
                int rating=t.nextInt();
                System.out.print("Enter Description: ");
                String description=t1.nextLine();

                System.out.println("Inserting Review...");
                Review c=new Review();
                c.setrid(rid);
                c.setdid(did);
                c.setmid(mid);
                c.setemail(email);
                c.setRating(rating);
                c.setdescription(description);
                try
                    {
                        c.insertData();
                    }catch(SQLException sqlException)
                    {
                        while (sqlException != null)
                            {
                                sqlException.printStackTrace();
                                sqlException = sqlException.getNextException();
                            }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                System.out.print("Add agian?(y/n): ");
                String again=t.next();
                if (again.equals("n") || again.equals("N") ){
                    System.out.println("done.");
                    flag=false;
                }
            }//end of while  
    }//end of add for merchant
    
    //add for purchase
    public static void addpurchase(){
    	boolean flag=true;
    	while (flag)
        {
            Scanner t = new Scanner(System.in);
            System.out.print("Enter Purchase ID: ");
            String pid=t.next();
            System.out.print("Enter Email: ");
            String email=t.next();
            //check to see if customer exists
            checkmail(email);
            System.out.print("Enter Quantity: ");
            int qty=t.nextInt();
			System.out.print("Enter Purchase Date(ddmmyy): ");
			String ddd=t.next();       
            System.out.print("Enter Review ID: ");
            String rid=t.next();
            //check to see if review exists
            checkreview(rid);
            System.out.println("Inserting Purchase...");
            
            Purchase c=new Purchase();
            c.setpid(pid);
            c.setrid(rid);
            c.setqty(qty);
            c.setemail(email);
            c.setdate(ddd);
            try
                {
                    c.insertData();
                }catch(SQLException sqlException)
                {
                    while (sqlException != null)
                        {
                            sqlException.printStackTrace();
                            sqlException = sqlException.getNextException();
                        }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            System.out.print("Add agian?(y/n): ");
            String again=t.next();
            if (again.equals("n") || again.equals("N") ){
                System.out.println("done.");
                flag=false;
            }
        }//end of while  
    }//end of add for purchase
    
    public static void adddealtype(){
		boolean flag=true;
        while (flag)
            {
                Scanner t = new Scanner(System.in);
                System.out.print("Enter Category: ");
                String cat=t.nextLine();
            
                System.out.println("Inserting Deal Type...");
                Dealtype c=new Dealtype();
                c.setCategory(cat);
 
                try
                    {
                        c.insertData();
                    }catch(SQLException sqlException)
                    {
                        while (sqlException != null)
                            {
                                sqlException.printStackTrace();
                                sqlException = sqlException.getNextException();
                            }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                System.out.print("Add agian?(y/n): ");
                String again=t.next();
                if (again.equals("n") || again.equals("N") ){
                    System.out.println("done.");
                    flag=false;
                }
            }//end of while  
    }//end of add for merchant
    
    //create buffer from file
    private static String readFileAsString(String filePath) throws java.io.IOException{
        byte[] buffer = new byte[(int) new File(filePath).length()];
        BufferedInputStream f = null;
        try {
            f = new BufferedInputStream(new FileInputStream(filePath));
            f.read(buffer);
        } finally {
            if (f != null) try { f.close(); } catch (IOException ignored) { }
        }
        return new String(buffer);
    }
    
    //check for deal
    public static void checkdid(String did)
    {
    	 Deal m=new Deal();
	try{
	   
	    if (!m.doesdidexist(did)){
		System.out.println("Deal ID you entered DOESNOT EXIST!");
		System.out.println("Creating New DEAL now..");
		     adddeal();
	    }
	}catch (SQLException sqlException){
	 while (sqlException != null)
	{
	    sqlException.printStackTrace();
	    sqlException=sqlException.getNextException();
	}
	}catch (Exception e)
	    {
		e.printStackTrace();
	    }
	
    }
    
    //check for merchant
    public static void checkmid(String mid)
    {
    	 Merchant m=new Merchant();
	try{
	   
	    if (!m.doesmidexist(mid)){
		System.out.println("Merchant ID you entered DOESNOT EXIST!");
		System.out.println("Creating New MERCHANT now..");
		     addmerchant();
	    }
	}catch (SQLException sqlException){
	 while (sqlException != null)
	{
	    sqlException.printStackTrace();
	    sqlException=sqlException.getNextException();
	}
	}catch (Exception e)
	    {
		e.printStackTrace();
	    }
	
    }
    //check for dealtype
    public static void checkcat(String cat)
    {
    	 Dealtype m=new Dealtype();
	try{
	   
	    if (!m.doescatexist(cat)){
		System.out.println("Category Type you entered DOESNOT EXIST!");
		System.out.println("Creating New TYPE now..");
		     adddealtype();
	    }
	}catch (SQLException sqlException){
	 while (sqlException != null)
	{
	    sqlException.printStackTrace();
	    sqlException=sqlException.getNextException();
	}
	}catch (Exception e)
	    {
		e.printStackTrace();
	    }
	
    }
    //check for customer
    public static void checkmail(String mail)
    {
    	 Customer m=new Customer();
	try{
	   
	    if (!m.doesmailexist(mail)){
		System.out.println("Email you entered DOESNOT EXIST!");
		System.out.println("Creating New CUSTOMER now..");
		     addcustomer();
	    }
	}catch (SQLException sqlException){
	 while (sqlException != null)
	{
	    sqlException.printStackTrace();
	    sqlException=sqlException.getNextException();
	}
	}catch (Exception e)
	    {
		e.printStackTrace();
	    }
	
    }
    //check for review
    public static void checkreview(String rid)
    {
    	 Review m=new Review();
	try{
	   
	    if (!m.doesridexist(rid)){
		System.out.println("RID you entered DOESNOT EXIST!");
		System.out.println("Creating New REVIEW now..");
		     addreview();
	    }
	}catch (SQLException sqlException){
	 while (sqlException != null)
	{
	    sqlException.printStackTrace();
	    sqlException=sqlException.getNextException();
	}
	}catch (Exception e)
	    {
		e.printStackTrace();
	    }
	
    }
    
    
    //converts string to date
    public static Date todate(String str)
    {
    	Date d=null;

    	try{
	    		
	    	DateFormat formatter;
	    	formatter = new SimpleDateFormat("ddMMyy");
	    	d = (Date) formatter.parse(str);
		}catch(ParseException e){
    		System.out.println("DateParse Exception:" +e);
    	}
    		
    	return d;

    }
   
    public static void main(String arg[])
    {
    String filename = "input.txt";	
	proj p= new proj();
	System.out.print("\f");
	        
	//enter username,pwd for sqlplus
	Scanner id = new Scanner(System.in);
	System.out.println("\nSQL Connection---");
	System.out.println("\nEnter Username: ");
        String u = id.nextLine();
	System.out.println("\nEnter Password: ");
	String pw = id.nextLine();
	p.setu(u);
	p.setp(pw);

    int option = 1;
    String input="";
    try{
    	input=p.readFileAsString(filename);
    }catch(IOException e){
    	e.printStackTrace();
    }
    
    //First drop all old tables
	p.dropoldtables();
	//Generate Tables in order
	p.poptables(input);

	//display main interface
        while ( option != 5 ) {
	    p.menu();
	    Scanner scan = new Scanner(System.in);
	    option=scan.nextInt();

	    while (option !=1 && option !=2 && option !=3 && option !=4 && option !=5){
		System.out.println("Invalid input: try again.(1,2,3,4,5).");
		p.menu();
		
		option=scan.nextInt();
	    }
	    
	    //Option 1	    
	    if (option ==1){
			Scanner display = new Scanner(System.in);
	    		
	    		//display tables option
			    p.dbmenu();
			    int q = display.nextInt();
			    
			    //display Customer
			    if (q==1){
				Customer[] db;
				try {
				    Customer c = new Customer();
				    db = c.loadAll();
				    c.print(db);
				} catch (SQLException e) {
				    e.printStackTrace();
				}
			    }
			    //display Merchant
			    if (q==3){
				Merchant[] db;
	                        try {
	                            Merchant c = new Merchant();
	                            db = c.loadAll();
				    c.print(db);
				} catch (SQLException e) {
	                            e.printStackTrace();
	                        }
			    }
			    //display Deal
			    if (q==2){
				Deal[] db;
				try {
				    Deal c=new Deal();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    }
			    //display Purchase
			    if (q==4){
				Purchase[] db;
				try {
				    Purchase c=new Purchase();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    }
			    //display Branch
			    if (q==5){
				Branch[] db;
				try {
				    Branch c=new Branch();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    }
			    //display Credit
			    if (q==6){
				Credit[] db;
				try {
				    Credit c=new Credit();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    }
			    //display Voucher
			    if (q==7){
				Voucher[] db;
				try {
				    Voucher c=new Voucher();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    }
			    //display Dealtype
			    if (q==8){
				Dealtype[] db;
				try {
				    Dealtype c=new Dealtype();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    }
			    //display Interest
			    if (q==9){
				Interest[] db;
				try {
				    Interest c=new Interest();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    }
			    //display Location
			    if (q==10){
				Location[] db;
				try {
				    Location c=new Location();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    }
			    //display Review
			    if (q==11){
				Review[] db;
				try {
				    Review c=new Review();
				    db=c.loadAll();
				    c.print(db);
				}catch(SQLException e){
				    e.printStackTrace();
				}
			    } 
			    
	    }//end of option1
	    

	    //modify db
	    if (option == 2 ){
		p.addmenu();
		Scanner scan1=new Scanner(System.in);
		int db=scan1.nextInt();
		

		p.moddbmenu();
	
		Scanner scan2 = new Scanner(System.in);
		int option2 = scan2.nextInt();
	
		//add for customer
		if ( db == 1 && option2 == 1){p.addcustomer();}
		//add for Deal
		if (db ==2 && option2==1){p.adddeal();}
		//add for Merchant
		if (db==3 && option2 ==1){addmerchant();}
		//add for Purchase
		if (db==4 && option2==1){addpurchase();}
		
		Scanner g=new Scanner(System.in);

		//delete for Customer
		if (db==1 && option2==2)
		    {
			System.out.print("Enter Customer Email to remove: ");
			String email=g.nextLine();
			
			//insert sql to remove
			Customer c= new Customer();
			try{
			    c.removetuple(email);
			}catch (SQLException e){
			    e.printStackTrace();
			}			
		    }
		/*
		//delete for db2
		if (db==2 && option2==2)
		    {
			System.out.print("Enter Branch ID to remove: ");
			String tid=g.next();
			
			//insert sql to remove
			Branch c= new Branch();
                        try{
                            c.removetuple(tid);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }


		    }
		    */
		//delete for Deal
		if (db==2 && option2==2)
		    {
			System.out.print("Enter Deal ID to remove: ");
			String did=g.next();
			
			//insert sql to remove
			Deal c= new Deal();
                        try{
                            c.removetuple(did);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
		    }
		//delete for Merchant
		if (db==3 && option2==2)
		    {
			System.out.print("Enter Merchant ID to remove: ");
			String mid=g.next();
		    
			//insert sql to remove
			Merchant c= new Merchant();
                        try{
                            c.removetuple(mid);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
		    }
		//delete for Purchase
		if (db==4 && option2==2)
		    {
			System.out.print("Enter Purchase ID to remove: ");
			String pid=g.next();

			//insert sql to remove
			Purchase c= new Purchase();
                        try{
                            c.removetuple(pid);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
		    }

	    }//end of modify
	    //beginning of option 3 --search
	    if (option ==3)
		{
		    p.searchmenu();
		    Scanner s = new Scanner(System.in);
		    int f=s.nextInt();
		    System.out.println("----Search----");
		    
		    //search by Merchant name		    
		    if (f==1)
			{
			    System.out.print("Enter Merchant Name: ");
			    String name=s.next();
			    
			    try{
				Deal c=new Deal();
				Deal[] results;
				results=c.find(name);
				System.out.println("---Search Results:");
				c.print(results);
			    }catch(SQLException e){
				e.printStackTrace();
			    }
			}
		    if (f==2)
		    {
		    	
		    	Deal d= new Deal();
		    	
		    	Scanner l = new Scanner(System.in);
		    	System.out.println("-----Enter Location info");
		    	System.out.println("Enter Street: ");
		    	String street=l.next();
		    	System.out.println("Enter City: ");
		    	String city=l.next();
		    	System.out.println("Enter State: ");
		    	String state=l.next();
		    	System.out.println("Enter Country: ");
		    	String country=l.next();
		    	System.out.println("Enter Zip: ");
		    	int zip=l.nextInt();
		    	
		    	
		    	
		    	
		    	
		    	
		    	System.out.println("---Search For:");
		    	System.out.println("1.All Deals");
		    	System.out.println("2.Open Deals");
		    	System.out.println("Enter: ");
		    	int o= l.nextInt();
		    	
		    
	    		 try{
	 				Deal c=new Deal();
	 				Deal[] results;
	 				results=c.find2(street,city,state,country,zip,o);
	 				System.out.println("---Search Results:");
	 				c.print(results);
	 			    }catch(SQLException e){
	 				e.printStackTrace();
	 			    }

		    }
			
		    //search in db2
		    /*
		    if (f==2)
			{
			    System.out.print("Enter Branch ID: ");
			    String tid=s.next();
			    try{ 
				Branch t=new Branch();
				Branch[] results;
				results=t.find(tid);
				System.out.println("---Search Results:");
				t.print(results);
			    }catch(SQLException e){
				e.printStackTrace();
			    }

			}
			*/
		    //search in db3
		    /*
		    if (f==3)
			{
			    System.out.println("----Search by----");
			    System.out.println("1.Deal Name");
			    System.out.println("2.Deal Description");
			    int k=s.nextInt();
			    try{
				Deal o=new Deal();
				Deal[] results;
				System.out.println("----Search----");
				//search by name
				if (k==1){
				    System.out.print("Enter Deal Name: ");
				    String data=s.next();
				    results=o.find(data,k);
				    o.print(results);
				}
				//search by description
				if (k==2){
				    System.out.print("Enter Deal Description: ");
				    String data=s.next();
				    results=o.find(data,k);
				    o.print(results);
				}
			    }catch(SQLException e){
				e.printStackTrace();
			    }
			    
			}
			*/
		    //search in db4
		    if (f==4)
			{
			    System.out.print("Enter Merchant's Name: ");
			    String name=s.next();

			    //insert sql to find Name
			    //Merchant s=new Merchant();
			    //s.fin(name);


			}
		    //search in db5
		    if (f==5)
			{
			    System.out.print("Enter Purchase ID: ");
			    String sid=s.next();
			    
			    //insert sql to find SID
			    //Purchase s1=new Purchase();
			    //s1.find(sid);
			}
		    
			    
		}//end of search option

	    //display option
	    if (option == 3){

		

		/*
		if (d==2){
		    Deal[] db;
		    try{
			Deal c=new Deal();
			db = c.find("low", 3);
			c.print(db);
		    }
		    catch(SQLException e){
			e.printStackTrace();
		    }
		}
		*/

 
	    }//end of display stats


	//p.menu();

	}//end of main menu loop   
    } //end main()
}