//class: INTEREST
//function: Handles INTEREST Table in DB

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


public class Interest extends proj  
{
    public Interest() {}
    
    // instance variables
    private String iid;
    private String Category;
    private String email;
   
   
  
  
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
	ResultSet rs = dmd.getTables (null, null, "INTEREST", null);
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
	sbremove.append(" DROP TABLE INTEREST CASCADE CONSTRAINTS ");
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
        tpremove.append(" DELETE FROM INTEREST ");
        tpremove.append(" WHERE IID = ");
        tpremove.append(iid);
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



    /** Method to create the INTEREST table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE INTEREST "); 
	sbCreate.append(" ( ");
	sbCreate.append("     IID VARCHAR2(25), "); 
	sbCreate.append("     Category VARCHAR2(25), "); 
	sbCreate.append("     EMAIL VARCHAR2(25),  ");
	sbCreate.append("     PRIMARY KEY (iid), ");
	sbCreate.append("     FOREIGN KEY (Category) ");
	sbCreate.append("     REFERENCES DEALTYPE(Category),");
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
    public void print(Interest[] db)
    {
	
	    System.out.println("\nIID  Category  email");
	    System.out.println("____________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Interest mdb =db[i];
		System.out.println(mdb.getiid() + "  " + mdb.getCategory() + "  " + mdb.getemail());
	    }
       
    }
	  
  
    /**
     * @return
     * @throws SQLException
     */
    public Interest[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT IID, Category, EMAIL FROM INTEREST ");
    
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
			Interest tuple = new Interest();
			tuple.setiid(rs.getString("IID"));
			tuple.setCategory(rs.getString("Category"));
			tuple.setemail(rs.getString("EMAIL"));
		
          
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
    return (Interest[])collection.toArray(new Interest[0]);  
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
		System.out.println("INTEREST Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO INTEREST (IID, Category, EMAIL) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + iid + "','" + Category + "','" + email + "')");

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

    public String getiid() {
	return iid;
    }

    public void setiid(String iid) {
	this.iid = iid;
    }
  
    public String getemail() {
	return email;
    }
  
    public void setemail(String email) {
	this.email = email;
    }
  
    public String getCategory() {
	return Category;
    }
  
    public void setCategory(String Category) {
	this.Category = Category;
    }

}
 