//Class: Location
//function: Handles Location Table in DB

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


public class Location extends proj  
{
    public Location() {}
    
    // instance variables
    private String lid;
    private String email;
    private String City;
    private String State;
    private String Country;
    private String Continent;
    private int Defualtadd;
    
 
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
	ResultSet rs = dmd.getTables (null, null, "LOCATION", null);
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
	sbremove.append(" DROP TABLE Location CASCADE CONSTRAINTS ");
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
	tpremove.append(" DELETE FROM Location ");
	tpremove.append(" WHERE lid = ");
	tpremove.append("'"+ lid + "'");
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

    /** Method to create the Location table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE Location "); 
	sbCreate.append(" ( ");
	sbCreate.append("     lid VARCHAR2(25), ");
	sbCreate.append("     email VARCHAR2(25), "); 
	sbCreate.append("     City VARCHAR2(25), "); 	
	sbCreate.append("     State VARCHAR2(25), ");
	sbCreate.append("     Country VARCHAR2(25), "); 
	sbCreate.append("     Continent VARCHAR2(25),  ");
	sbCreate.append("     Defualtadd INTEGER,  ");
	sbCreate.append("     PRIMARY KEY(lid), ");
	sbCreate.append("     FOREIGN KEY(email) references CUSTOMER(email) ");
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
    public void print(Location[] db)
    {
	
	    System.out.println("\nlid  email  City   State  Country  Continent  Defualtadd");
	    System.out.println("_________________________________________________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Location mdb =db[i];
		System.out.println(mdb.getlid() + "  " + mdb.getemail() +"  "+ mdb.getCity() + "  "+ mdb.getState() +"  " + mdb.getCountry() + "  " + mdb.getContinent()+"  " + mdb.getDefualtadd());
	    }
       
    }
	  
    /**
     * @return
     * @throws SQLException
     */
    public Location[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT lid, email, City, State, Country, Continent, Defualtadd FROM Location ");
    
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
			Location tuple = new Location();
			tuple.setlid(rs.getString("lid"));
			tuple.setemail(rs.getString("email"));
			tuple.setCity(rs.getString("City"));
			tuple.setState(rs.getString("State"));
			tuple.setCountry(rs.getString("Country"));
			tuple.setContinent(rs.getString("Continent"));
			tuple.setDefualtadd(rs.getInt("Defualtadd"));

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
    return (Location[])collection.toArray(new Location[0]);  
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
		System.out.println("Location Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO Location (lid, email, City, State, Country, Continent, Defualtadd) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + lid + "','" + email + "','" + City + "','" + State + "','" + Country + "','" + Continent + "', '" + Defualtadd + "')");

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

    public String getemail() {
	return email;
    }

    public void setemail(String email) {
	this.email = email;
    }
  
    public String getlid() {
	return lid;
    }
  
    public void setlid(String lid) {
	this.lid = lid;
    }
    
    public String getCity() {
	return City;
    }
  
    public void setCity(String City) {
	this.City = City;
    }
  
    public int getDefualtadd() {
	return Defualtadd;
    }
  
    public void setDefualtadd(int Defualtadd) {
	this.Defualtadd = Defualtadd;
    }
  
    public String getContinent() {
	return Continent;
    }
  
    public void setContinent(String Continent) {
	this.Continent = Continent;
    }
    
    public String getCountry() {
	return Country;
    }
  
    public void setCountry(String Country) {
	this.Country = Country;
    }
    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }



}
 

