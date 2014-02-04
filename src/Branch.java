//Class: Branch
//function: Handles Branch Table in DB

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


public class Branch extends proj  
{
    public Branch() {}
    
    // instance variables
    private String bid;
    private String street;
    private String City;
    private String State;
    private String Country;
    private String did;
    private int zip;
    
 
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
	ResultSet rs = dmd.getTables (null, null, "BRANCH", null);
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
	sbremove.append(" DROP TABLE Branch CASCADE CONSTRAINTS ");
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
    public void removetuple(String street) throws SQLException
    {
	StringBuffer tpremove=new StringBuffer();
	tpremove.append(" DELETE FROM Branch ");
	tpremove.append(" WHERE bid = ");
	tpremove.append(street);
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

    /** Method to create the Branch table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE Branch "); 
	sbCreate.append(" ( ");
	sbCreate.append("     bid VARCHAR(25), ");
	sbCreate.append("     street VARCHAR(25), "); 
	sbCreate.append("     City VARCHAR(25), "); 	
	sbCreate.append("     State VARCHAR(25), "); 
	sbCreate.append("     Country VARCHAR(25), "); 
	sbCreate.append("     did VARCHAR(25),  ");
	sbCreate.append("     zip INTEGER,  ");
	sbCreate.append("     PRIMARY KEY (bid), ");
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
    public void print(Branch[] db)
    {
	
	    System.out.println("\nbid  street  City  State  Country  did  zip");
	    System.out.println("__________________________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Branch mdb =db[i];
		System.out.println(mdb.getbid() + "  " + mdb.getstreet() +"  "+ mdb.getCity() +"  "+ mdb.getState() + "  " + mdb.getCountry() + "  " + mdb.getdid()+"  " + mdb.getzip());
	    }
       
    }
	  
    /**
     * @return
     * @throws SQLException
     */
    public Branch[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT bid, street, City, State, Country, did, zip FROM Branch ");
    
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
			Branch tuple = new Branch();
			tuple.setbid(rs.getString("bid"));
			tuple.setstreet(rs.getString("street"));
			tuple.setCity(rs.getString("City"));
			tuple.setState(rs.getString("State"));
			tuple.setCountry(rs.getString("Country"));
			tuple.setdid(rs.getString("did"));
			tuple.setzip(rs.getInt("zip"));

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
    return (Branch[])collection.toArray(new Branch[0]);  
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
		System.out.println("Branch Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO Branch (bid, street, City, State, Country, did, zip ) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + bid + "','" + street + "','" + City + "','" + State + "','" + Country + "','" + did + "', '" + zip + "')");

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

    public String getstreet() {
	return street;
    }

    public void setstreet(String street) {
	this.street = street;
    }
  
    public String getbid() {
	return bid;
    }
  
    public void setbid(String bid) {
	this.bid = bid;
    }
    
    public String getCity() {
	return City;
    }
  
    public void setCity(String City) {
	this.City = City;
    }
    
    public String getState() {
    	return State;
    }
      
    public void setState(String State) {
    	this.State = State;
    }
  
    public int getzip() {
	return zip;
    }
  
    public void setzip(int zip) {
	this.zip = zip;
    }
  
    public String getdid() {
	return did;
    }
  
    public void setdid(String did) {
	this.did = did;
    }
    
    public String getCountry() {
	return Country;
    }
  
    public void setCountry(String Country) {
	this.Country = Country;
    }

}
 

