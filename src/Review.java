//Class: Review
//function: Handles Review Table in DB

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


public class Review extends proj  
{
    public Review() {}
    
    // instance variables
    private String rid;
    private String did;
    private String mid;
    private String email;
    private int Rating;
    private String description;
 
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
	ResultSet rs = dmd.getTables (null, null, "REVIEW", null);
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
	sbremove.append(" DROP TABLE Review CASCADE CONSTRAINTS ");
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
	tpremove.append(" DELETE FROM Review ");
	tpremove.append(" WHERE rid = ");
	tpremove.append("'"+ rid +"'");
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

    /** Method to create the Review table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append(" CREATE TABLE Review "); 
	sbCreate.append(" ( ");
	sbCreate.append("     rid VARCHAR2(25), "); 
	sbCreate.append("     did VARCHAR2(25), "); 	
	sbCreate.append("     mid VARCHAR2(25), "); 
	sbCreate.append("     email VARCHAR2(25), "); 
	sbCreate.append("     Rating INTEGER,  ");
	sbCreate.append("     description VARCHAR2(500),  ");
	sbCreate.append("     PRIMARY KEY (rid), ");
	sbCreate.append("     FOREIGN KEY (did) ");
	sbCreate.append("     REFERENCES DEAL(did), ");
	sbCreate.append("     FOREIGN KEY (mid) ");
	sbCreate.append("     REFERENCES MERCHANT(mid), ");
	sbCreate.append("     FOREIGN KEY (email) ");
	sbCreate.append("     REFERENCES CUSTOMER(email) ");
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
    public void print(Review[] db)
    {
	
	    System.out.println("\nrid  did  mid  email  Rating  description");
	    System.out.println("__________________________________________");
	    System.out.println(db.length);
	    for (int i=0; i<db.length; i++){
		Review mdb =db[i];
		System.out.println(mdb.getrid() + "  " + mdb.getdid() + "  " + mdb.getmid() + "  "+ mdb.getemail() +"  "+ mdb.getRating() + "  " + mdb.getdescription());
	    }
       
    }
	  
    /**
     * @return
     * @throws SQLException
     */
    public Review[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT rid, did, mid, email, Rating, description FROM Review ");
    
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
			Review tuple = new Review();
			tuple.setrid(rs.getString("rid"));
			tuple.setdid(rs.getString("did"));
			tuple.setmid(rs.getString("mid"));
			tuple.setemail(rs.getString("email"));
			tuple.setRating(rs.getInt("Rating"));
			tuple.setdescription(rs.getString("description"));
          
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
    return (Review[])collection.toArray(new Review[0]);  
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
		System.out.println("Review Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL
	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO Review (rid, did, mid, email, Rating, description) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + rid + "','" + did + "','" + mid + "','" + email + "','" + Rating + "','" + description + "')");

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
    //check for rid
    public static boolean doesridexist(String rid) throws SQLException
    {
    Review m=new Review();
	Connection con=m.getConnection();
	boolean a=false;
	//creat SQL statement
	StringBuffer sbselect = new StringBuffer();
	sbselect.append(" SELECT RID ");
	sbselect.append(" FROM REVIEW");
	sbselect.append(" WHERE RID=");
	sbselect.append("'" + rid + "'");

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

    }//end of checkrid

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
  
    public String getrid() {
	return rid;
    }
  
    public void setrid(String rid) {
	this.rid = rid;
    }
    
    public String getdid() {
	return did;
    }
  
    public void setdid(String did) {
	this.did = did;
    }
  
    public int getRating() {
	return Rating;
    }
  
    public void setRating(int Rating) {
	this.Rating = Rating;
    }
  
    public String getdescription() {
	return description;
    }
  
    public void setdescription(String description) {
	this.description = description;
    }
    
    public String getmid() {
	return mid;
    }
  
    public void setmid(String mid) {
	this.mid = mid;
    }

}
 

