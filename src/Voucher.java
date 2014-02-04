//Class: Voucher
//function: Handles Voucher Table in DB

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


public class Voucher extends proj  
{
    public Voucher() {}
    
    // instance variables
    private String vid;
    private String pid;
    private String bid;
    private String did;
    private String vfor;
    private String status;
    
 
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
	ResultSet rs = dmd.getTables (null, null, "VOUCHER", null);
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
	sbremove.append(" DROP TABLE Voucher CASCADE CONSTRAINTS ");
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
    public void removetuple(String pid) throws SQLException
    {
	StringBuffer tpremove=new StringBuffer();
	tpremove.append(" DELETE FROM Voucher ");
	tpremove.append(" WHERE vid = ");
	tpremove.append(pid);
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

    /** Method to create the Voucher table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL vfor the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE Voucher "); 
	sbCreate.append(" ( ");
	sbCreate.append("     vid VARCHAR(25), ");
	sbCreate.append("     pid VARCHAR(25), "); 
	sbCreate.append("     bid VARCHAR(25), "); 	
	sbCreate.append("     did VARCHAR(25), "); 
	sbCreate.append("     vfor VARCHAR(25),  ");
	sbCreate.append("     status VARCHAR(25),  ");
	sbCreate.append("     PRIMARY KEY (vid), ");
	sbCreate.append("     FOREIGN KEY (pid) ");
	sbCreate.append("     REFERENCES PURCHASE(pid), ");
	sbCreate.append("     FOREIGN KEY (bid) ");
	sbCreate.append("     REFERENCES BRANCH(bid), ");
	sbCreate.append("     FOREIGN KEY (did) ");
	sbCreate.append("     REFERENCES DEAL(did) ");
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
    public void print(Voucher[] db)
    {
	
	    System.out.println("\nvid  pid  bid  did  for  status");
	    System.out.println("__________________________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Voucher mdb =db[i];
		System.out.println(mdb.getvid() + "  " + mdb.getpid() +"  "+ mdb.getbid() + "  " + mdb.getdid() + "  " + mdb.getvfor()+"  " + mdb.getstatus());
	    }
       
    }
	  
    /**
     * @return
     * @throws SQLException
     */
    public Voucher[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT vid, pid, bid, did, vfor, status FROM Voucher ");
    
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
			Voucher tuple = new Voucher();
			tuple.setvid(rs.getString("vid"));
			tuple.setpid(rs.getString("pid"));
			tuple.setbid(rs.getString("bid"));
			tuple.setdid(rs.getString("did"));
			tuple.setvfor(rs.getString("vfor"));
			tuple.setstatus(rs.getString("status"));

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
    return (Voucher[])collection.toArray(new Voucher[0]);  
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
		System.out.println("Voucher Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO Voucher (vid, pid, bid, did, vfor, status) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + vid + "','" + pid + "','" + bid + "','" + did + "','" + vfor + "', '" + status + "')");

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
  
  
    //
    // Getter and setter methods
    //

    public String getpid() {
	return pid;
    }

    public void setpid(String pid) {
	this.pid = pid;
    }
  
    public String getvid() {
	return vid;
    }
  
    public void setvid(String vid) {
	this.vid = vid;
    }
    
    public String getbid() {
	return bid;
    }
  
    public void setbid(String bid) {
	this.bid = bid;
    }
  
    public String getstatus() {
	return status;
    }
  
    public void setstatus(String status) {
	this.status = status;
    }
  
    public String getvfor() {
	return vfor;
    }
  
    public void setvfor(String vfor) {
	this.vfor = vfor;
    }
    
    public String getdid() {
	return did;
    }
  
    public void setdid(String did) {
	this.did = did;
    }

}
 

