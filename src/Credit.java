//class: Credit
//function: Handles Credit Table in DB

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


public class Credit extends proj  
{
    public Credit() {}
    
    // instance variables
    private String cid;
    private String Defaultcc;
    private int cc_number;
    private String email;
    private String Expire_Date;
   
  
  
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
	ResultSet rs = dmd.getTables (null, null, "CREDIT", null);
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
	sbremove.append(" DROP TABLE Credit CASCADE CONSTRAINTS ");
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
    public void removetuple(String cid) throws SQLException
    {
        StringBuffer tpremove=new StringBuffer();
        tpremove.append(" DELETE FROM Credit ");
        tpremove.append(" WHERE CID = ");
        tpremove.append(cid);
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



    /** Method to create the Credit table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE Credit "); 
	sbCreate.append(" ( ");
	sbCreate.append("     CID VARCHAR2(25), "); 
	sbCreate.append("     Defaultcc VARCHAR2(25), "); 
	sbCreate.append("     cc_number INTEGER, "); 
	sbCreate.append("     EMAIL VARCHAR2(25),  ");
	sbCreate.append("     Expire_Date VARCHAR2(25),  ");
	sbCreate.append("     PRIMARY KEY (CID), ");
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
    public void print(Credit[] db)
    {
	
	    System.out.println("\ncid  Default  number  email  Expire");
	    System.out.println("__________________________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Credit mdb =db[i];
		System.out.println(mdb.getcid() + "  " + mdb.getDefaultcc() + "  " + mdb.getcc_number() + "  "+ mdb.getemail() +"  "+ mdb.getExpire_Date());
	    }
       
    }
	  
  
    /**
     * @return
     * @throws SQLException
     */
    public Credit[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT CID, Defaultcc, cc_number, EMAIL, Expire_Date FROM Credit ");
    
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
			Credit tuple = new Credit();
			tuple.setcid(rs.getString("CID"));
			tuple.setDefaultcc(rs.getString("Defaultcc"));
			tuple.setcc_number(rs.getInt("cc_number"));
			tuple.setemail(rs.getString("EMAIL"));
			tuple.setExpire_Date(rs.getString("Expire_Date"));
		
          
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
    return (Credit[])collection.toArray(new Credit[0]);  
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
		System.out.println("Credit Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO Credit (CID, Defaultcc, cc_number, EMAIL, Expire_Date) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + cid + "','" + Defaultcc + "', '" + cc_number + "','" + email + "','" + Expire_Date + "')");

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

    public String getcid() {
	return cid;
    }

    public void setcid(String cid) {
	this.cid = cid;
    }
  
    public int getcc_number() {
	return cc_number;
    }
  
    public void setcc_number(int cc_number) {
	this.cc_number = cc_number;
    }
  
    public String getemail() {
	return email;
    }
  
    public void setemail(String email) {
	this.email = email;
    }
  
    public String getExpire_Date() {
	return Expire_Date;
    }
  
    public void setExpire_Date(String Expire_Date) {
	this.Expire_Date = Expire_Date;
    }
    
    public String getDefaultcc() {
	return Defaultcc;
    }
  
    public void setDefaultcc(String Defaultcc) {
	this.Defaultcc = Defaultcc;
    }

}
 