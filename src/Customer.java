//Class: Customers
//function: Handles Customers Table in DB

// JDBC libraries
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// JDK libraries
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Customer extends proj  
{
    public Customer() {}
    
    // instance variables
    private String email;
    private String fname;
    private String lname;
    private int age;
    private String gender;
    private String address;
   
  
  
    /** Method to get the connection for the database
     * @return java.sql.Connection object
     */
    public Connection getConnection() {
	// register the JDBC driver
	try {
	    Class.forName(super.driver);
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    
	// create a connection
	Connection connection = null;
	try {
	    connection = DriverManager.getConnection (super.jdbc_url,super.getu(), super.getp());
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return connection;
    }
  
    /** Method to check if a table exists
     * @param connection java.sql.Connection object
     * @return true is the table exists, false otherwise
     * @throws SQLException
     */
    public boolean doesTableExist(Connection connection) throws SQLException {
	boolean bTableExists = false;
    
	// check the meta data of the connection
	DatabaseMetaData dmd = connection.getMetaData();
	ResultSet rs = dmd.getTables (null, null, "CUSTOMER", null);
	while (rs.next()){
	    bTableExists =  true;
	}
	rs.close(); // close the result set
	return bTableExists;    
    }
    //method to remove a table
    public void removetable(Connection connection) throws SQLException
    {
	StringBuffer sbremove = new StringBuffer();
	sbremove.append(" DROP TABLE CUSTOMER CASCADE CONSTRAINTS ");
	Statement statement =null;
	try {
	    statement = connection.createStatement();
	    statement.executeUpdate (sbremove.toString());
	}catch (SQLException e) {
	    throw e;
	}finally{
	    statement.close();
	}
    }
    //method remove a tuple
    public void removetuple(String email) throws SQLException
    {
	StringBuffer tpremove=new StringBuffer();
	tpremove.append(" DELETE FROM CUSTOMER ");
	tpremove.append(" WHERE email = ");
	tpremove.append("'" + email + "'");
	Statement statement =null;
	System.out.println("Removing record....");

	try {
	    statement = this.getConnection().createStatement();
	    statement.executeUpdate (tpremove.toString());
	}catch (SQLException e){
	    throw e;
	}finally{
	    statement.close();
	}
    }

    /** Method to create the CUSTOMER table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE CUSTOMER "); 
	sbCreate.append(" ( ");
	sbCreate.append("     email VARCHAR2(25), "); 
	sbCreate.append("     fname VARCHAR2(25), "); 
	sbCreate.append("     lname VARCHAR2(25), "); 
	sbCreate.append("     AGE INTEGER,  ");
	sbCreate.append("     GENDER VARCHAR2(1) NOT NULL CHECK (GENDER IN ('M','F')),  ");
	sbCreate.append("     address VARCHAR2(25), "); 
	sbCreate.append("     PRIMARY KEY (email) ");
	sbCreate.append(" ) ");
    
	// create the table
	Statement statement = null;
	try {
	    statement = connection.createStatement();
	    statement.executeUpdate (sbCreate.toString());
	} catch (SQLException e) {
	    throw e;
	} finally {
	    statement.close();
	}
    }
    /*
    //method to find a customer by fname
    public Customer[] find(String fname) throws SQLException{
	Connection connection=getConnection();
	
	//create the SELECT SQL
	StringBuffer sbselect = new StringBuffer();
	sbselect.append(" SELECT email, fname, AGE, GENDER ");
	sbselect.append(" FROM CUSTOMER ");
	sbselect.append(" WHERE fname LIKE ");
	sbselect.append("'"+ "%"+ fname +"%"+"'");
	
	Statement statement=null;
	ResultSet rs=null;
	ArrayList collection = new ArrayList();
	
	try
	    {
		statement=connection.createStatement();
		rs=statement.executeQuery(sbselect.toString());
		if( rs != null){
		    while (rs.next())
			{
			    Customer tuple = new Customer();
			    tuple.setemail(rs.getString("email"));
			    tuple.setfname(rs.getString("fname"));
			    tuple.setage(rs.getInt("AGE"));
			    tuple.setgender(rs.getString("GENDER"));
			    collection.add(tuple);
			}
		}
	    }catch (SQLException e)
	    {
		throw e;
	    } finally
	    {
		rs.close();
		statement.close();
		close(connection);
	    }
	return(Customer[])collection.toArray(new Customer[0]);
    }
    */
    //method to print results of querry from array
    public void print(Customer[] db)
    {
	
	    System.out.println("\nemail \tfname \tlname       Age\t Gender\taddress");
	    System.out.println("________________________________________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Customer mdb =db[i];
		System.out.println(mdb.getemail() + "\t" + mdb.getfname() + "\t" + mdb.getlname() + "\t" + mdb.getage() + "\t" + mdb.getgender() + "\t" + mdb.getaddress());
	    }
       
    }
	  
    /**
     * @return
     * @throws SQLException
     */
    public Customer[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT email, fname, lname, AGE, GENDER, address FROM CUSTOMER ");
    
	Statement statement = null;
	ResultSet rs = null;
	ArrayList collection = new ArrayList();
    try
	{
	    // create the statement
	    statement = connection.createStatement();
	    // Insert the data
	    rs = statement.executeQuery(sbSelect.toString());
	    if (rs != null) {    
		// when the resultset is not null, there are records returned
		while (rs.next())
		    {
			// loop through each result and store the data
			// as an MusicCollectionDatabase object
			Customer tuple = new Customer();
			tuple.setemail(rs.getString("email"));
			tuple.setfname(rs.getString("fname"));
			tuple.setlname(rs.getString("lname"));
			tuple.setage(rs.getInt("AGE"));
			tuple.setgender(rs.getString("GENDER"));
			tuple.setaddress(rs.getString("address"));
          
			// store it in the list
			collection.add(tuple);
		    }        
	    }
	} catch (SQLException e)
	{
	    throw e;
	} finally
	{  
	    rs.close();
	    statement.close();
	    close(connection);
	}
    
    //   return the array
    return (Customer[])collection.toArray(new Customer[0]);  
    }

    /**
     * @throws SQLException
     */
    public void insertData () throws SQLException
    {
	// get the connection
	Connection connection = getConnection();
	

	// create new table
	if (!this.doesTableExist(connection))
	    {
		// create the table
		System.out.println("CUSTOMERS Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO CUSTOMER (email, fname, lname, AGE, GENDER, address) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + email + "', '" + fname + "','" + lname + "','" + age + "','" + gender + "', '" + address + "')");

	// create the statement
	Statement statement = connection.createStatement();
    try
	{
	    // Insert the data
	    statement.executeUpdate (sbInsert.toString());
	} catch (SQLException e)
	{
	    throw e;
	} finally
	{  
	    statement.close();
	    close(connection);
	}
    }
    //check to see if customer exists
    public static boolean doesmailexist(String mail) throws SQLException
    {
    Customer m=new Customer();
	Connection con=m.getConnection();
	boolean a=false;
	//creat SQL statement
	StringBuffer sbselect = new StringBuffer();
	sbselect.append(" SELECT EMAIL ");
	sbselect.append(" FROM CUSTOMER");
	sbselect.append(" WHERE EMAIL=");
	sbselect.append("'" + mail + "'");

	Statement statement=null;
	ResultSet rs=null;
	ArrayList collection = new ArrayList();
	try{
	    statement=con.createStatement();
	    rs=statement.executeQuery(sbselect.toString());
	    if ( rs.next()){
		a=true;
	    }
	    
	}catch(SQLException e)
	    {
			throw e;
	    }finally
	    {
		rs.close();
		statement.close();
		m.close(con);
	    }
	return a;

    }//end of checkmail

    /**
     * @param connection
     * @throws SQLException
     */
    public void close(Connection connection) throws SQLException
    {
    try
	{
	    connection.close();
	} catch (SQLException e)
	{
	    throw e;
	}
    }
  
  
    //
    // Getter and setter methods
    //

    public String getemail() {
	return email;
    }

    public void setemail(String email) {
	this.email = email;
    }
  
    public String getfname() {
	return fname;
    }
  
    public void setfname(String fname) {
	this.fname = fname;
    }
    
    public String getlname() {
	return lname;
    }
  
    public void setlname(String lname) {
	this.lname = lname;
    }
  
    public int getage() {
	return age;
    }
  
    public void setage(int age) {
	this.age = age;
    }
  
    public String getgender() {
	return gender;
    }
  
    public void setgender(String gender) {
	this.gender = gender;
    }
    
    public String getaddress() {
	return address;
    }
  
    public void setaddress(String address) {
	this.address = address;
    }

}
 

