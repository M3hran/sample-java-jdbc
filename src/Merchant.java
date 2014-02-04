//class: Merchant
//function: Handles Merchant Table in DB

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


public class Merchant extends proj  
{
    public Merchant() {}
    
    // instance variables
    private String mid;
    private String name;
    private String hq;
   
  
  
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
	ResultSet rs = dmd.getTables (null, null, "MERCHANT", null);
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
	sbremove.append(" DROP TABLE Merchant CASCADE CONSTRAINTS ");
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
    public void removetuple(String mid) throws SQLException
    {
        StringBuffer tpremove=new StringBuffer();
        tpremove.append(" DELETE FROM Merchant ");
        tpremove.append(" WHERE mid = ");
        tpremove.append(mid);
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

    /** Method to create the Merchant table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE MERCHANT "); 
	sbCreate.append(" ( ");
	sbCreate.append("     mid VARCHAR2(25), "); 
	sbCreate.append("     name VARCHAR2(25),  ");
	sbCreate.append("     hq VARCHAR2(25), ");
	sbCreate.append("     PRIMARY KEY (mid) ");
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

    //method to print results of querry from array
    public void print(Merchant[] db)
    {
	
	    System.out.println("\nmid  name  hq");
	    System.out.println("____________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Merchant mdb =db[i];
		System.out.println(mdb.getmid() + "  " + mdb.getname() + "  " + mdb.gethq());
	    }
       
    }
  
    /**
     * @return
     * @throws SQLException
     */
    public Merchant[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT mid, name, hq FROM Merchant ");
    
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
			Merchant tuple = new Merchant();
	   		tuple.setmid(rs.getString("mid"));
			tuple.setname(rs.getString("name"));
			tuple.sethq(rs.getString("hq"));
		
          
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
    return (Merchant[])collection.toArray(new Merchant[0]);  
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
		System.out.println("Merchant Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO Merchant (mid, name, hq) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + mid + "', '" + name + "','" + hq +  "')");


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
    public static boolean doesmidexist(String mid) throws SQLException
    {
    Merchant m=new Merchant();
	Connection con=m.getConnection();
	boolean a=false;
	//creat SQL statement
	StringBuffer sbselect = new StringBuffer();
	sbselect.append(" SELECT MID ");
	sbselect.append(" FROM MERCHANT ");
	sbselect.append(" WHERE MID=");
	sbselect.append("'" + mid + "'");

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

    }//end of checkmid
    

  
    //
    // Getter and setter methods
    //

  
    public String getmid() {
	return mid;
    }
  
    public void setmid(String mid) {
	this.mid = mid;
    }
  
    public String getname() {
	return name;
    }
  
    public void setname(String name) {
	this.name = name;
    }
  
    public String gethq() {
	return hq;
    }
  
    public void sethq(String hq) {
	this.hq = hq;
    }

}
 

