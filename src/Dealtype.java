//class: Dealtype
//function: Handles Dealtype Table in DB

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


public class Dealtype extends proj  
{
    public Dealtype() {}
    
    // instance variables
    private String Category;
   
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
	ResultSet rs = dmd.getTables (null, null, "DEALTYPE", null);
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
	sbremove.append(" DROP TABLE Dealtype CASCADE CONSTRAINTS ");
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
    public void removetuple(String iid) throws SQLException
    {
        StringBuffer tpremove=new StringBuffer();
        tpremove.append(" DELETE FROM Dealtype ");
        tpremove.append(" WHERE Category = ");
        tpremove.append("'"+ Category +"'");
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



    /** Method to create the Dealtype table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE Dealtype "); 
	sbCreate.append(" ( ");
	sbCreate.append("     Category VARCHAR2(25), "); 
	sbCreate.append("     PRIMARY KEY (Category) ");
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
    public void print(Dealtype[] db)
    {
	
	    System.out.println("\nCategory");
	    System.out.println("________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Dealtype mdb =db[i];
		System.out.println(mdb.getCategory());
	    }
       
    }
	  
  
    /**
     * @return
     * @throws SQLException
     */
    public Dealtype[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT Category FROM Dealtype ");
    
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
			Dealtype tuple = new Dealtype();
			tuple.setCategory(rs.getString("Category"));
		         
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
    return (Dealtype[])collection.toArray(new Dealtype[0]);  
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
		System.out.println("Dealtype Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    	  
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO Dealtype (Category) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + Category + "')");

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
    //check if category exists
    public static boolean doescatexist(String cat) throws SQLException
    {
    Dealtype m=new Dealtype();
	Connection con=m.getConnection();
	boolean a=false;
	//create SQL statement
	StringBuffer sbselect = new StringBuffer();
	sbselect.append(" SELECT Category ");
	sbselect.append(" FROM DEALTYPE ");
	sbselect.append(" WHERE CATEGORY=");
	sbselect.append("'" + cat + "'");

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

    }//end of checkcat

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
  
    public String getCategory() {
	return Category;
    }
  
    public void setCategory(String Category) {
	this.Category = Category;
    }

}
 