//class: Purchase
//function: Handles Purchase Table in DB

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


public class Purchase extends proj  
{
    public Purchase() {}
    
    // instance variables
    private String pid;
    private String rid;
    private int qty;
    private String email;
    private String date;
   
  
  
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
	ResultSet rs = dmd.getTables (null, null, "PURCHASE", null);
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
	sbremove.append(" DROP TABLE Purchase CASCADE CONSTRAINTS ");
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
        tpremove.append(" DELETE FROM Purchase ");
        tpremove.append(" WHERE PID = ");
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



    /** Method to create the Purchase table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE Purchase "); 
	sbCreate.append(" ( ");
	sbCreate.append("     PID VARCHAR2(25), "); 
	sbCreate.append("     EMAIL VARCHAR2(25),  ");
	sbCreate.append("     QTY INTEGER, ");
	sbCreate.append("     p_date VARCHAR2(25),  ");
	sbCreate.append("     RID VARCHAR2(25), "); 
	sbCreate.append("     PRIMARY KEY (PID), ");
	sbCreate.append("     FOREIGN KEY (RID) ");
	sbCreate.append("     REFERENCES REVIEW(RID),");
	sbCreate.append("     FOREIGN KEY (EMAIL) ");
	sbCreate.append("     REFERENCES CUSTOMER(EMAIL)");
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
    public void print(Purchase[] db)
    {
	
	    System.out.println("\npid  email  qty  date  rid");
	    System.out.println("__________________________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Purchase mdb =db[i];
		System.out.println(mdb.getpid() + "  " + mdb.getemail() +"  "+ mdb.getqty() + "  " + mdb.getdate()+"  "+ mdb.getrid());
	    }      
    }
	  
    /**
     * @return
     * @throws SQLException
     */
    public Purchase[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT PID, EMAIL, QTY, p_date, RID FROM PURCHASE ");
    
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
			Purchase tuple = new Purchase();
			tuple.setpid(rs.getString("PID"));
			tuple.setemail(rs.getString("EMAIL"));
			tuple.setqty(rs.getInt("QTY"));
			tuple.setdate(rs.getString("p_date"));
			tuple.setrid(rs.getString("RID"));
          
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
    return (Purchase[])collection.toArray(new Purchase[0]);  
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
		System.out.println("Purchase Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO Purchase (PID, EMAIL, QTY, p_date, RID) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + pid + "','" + email + "', '" + qty + "','" + date + "','" + rid + "')");

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
  
    public int getqty() {
	return qty;
    }
  
    public void setqty(int qty) {
	this.qty = qty;
    }
  
    public String getemail() {
	return email;
    }
  
    public void setemail(String email) {
	this.email = email;
    }
  
    public String getdate() {
	return date;
    }
  
    public void setdate(String date) {
	this.date = date;
    }
    
    public String getrid() {
	return rid;
    }
  
    public void setrid(String rid) {
	this.rid = rid;
    }

}
 

