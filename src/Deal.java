//class: Deal
//function: Handles Deal Table in DB

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


public class Deal extends proj  
{
    public Deal() {}
    
    // instance variables
    private String did;
    private String Category;
    private String mid;
    private String description; 
    private String Expires;
    private String Start_Date;
    private int End_Date;
    private float org_price;
    private float Deal_price;
    private int qty;
  
  
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
	ResultSet rs = dmd.getTables (null, null, "DEAL", null);
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
	sbremove.append(" DROP TABLE DEAL CASCADE CONSTRAINTS ");
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
    public void removetuple(String did) throws SQLException
    {
        StringBuffer tpremove=new StringBuffer();
        tpremove.append(" DELETE FROM DEAL ");
        tpremove.append(" WHERE did = ");
        tpremove.append("'" + did + "'");
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




    /** Method to create the Deal table
     * @param connection java.sql.Connection object
     * @throws SQLException
     */
    public void createTable(Connection connection) throws SQLException
    {
	// create the SQL for the table
	StringBuffer sbCreate = new StringBuffer();
	sbCreate.append("CREATE TABLE DEAL"); 
	sbCreate.append("( ");
	sbCreate.append(" did VARCHAR2(25),"); 
	sbCreate.append(" Category VARCHAR2(25),");
	sbCreate.append(" mid VARCHAR2(25),");
	sbCreate.append(" description VARCHAR2(25),");
	sbCreate.append(" Expires VARCHAR2(25),");
	sbCreate.append(" Start_Date VARCHAR2(25),");
	sbCreate.append(" End_Date INTEGER,");
	sbCreate.append(" org_price FLOAT,");
	sbCreate.append(" Deal_price FLOAT,");
	sbCreate.append(" QTY INTEGER,");
	sbCreate.append(" PRIMARY KEY(did),");
	sbCreate.append(" FOREIGN KEY(Category) references DEALTYPE(Category),");
	sbCreate.append(" FOREIGN KEY(mid) references MERCHANT(mid)");
	sbCreate.append(") ");
	

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
    
  
    /**
     * @return
     * @throws SQLException
     */
    public Deal[] loadAll() throws SQLException {
	// get the connection
	Connection connection = getConnection();
        
	// create the SELECT SQL
	StringBuffer sbSelect = new StringBuffer();
	sbSelect.append(" SELECT did, Category, mid, description, Expires, Start_Date, End_Date, org_price, Deal_price, QTY FROM DEAL ");
    
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
			// as a Deal object
			Deal tuple = new Deal();
			tuple.setdid(rs.getString("did"));
			tuple.setCategory(rs.getString("Category"));
			tuple.setmid(rs.getString("mid"));
			tuple.setdescription(rs.getString("description"));
			tuple.setExpires(rs.getString("Expires"));
			tuple.setStart_Date(rs.getString("Start_Date"));
			tuple.setEnd_Date(rs.getInt("End_Date"));
			tuple.setorg_price(rs.getFloat("org_price"));
			tuple.setDeal_price(rs.getFloat("Deal_price"));
			tuple.setqty(rs.getInt("QTY"));

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
    return (Deal[])collection.toArray(new Deal[0]);  
    }
    
    //method to find Deal by merchant name
    public Deal[] find(String name) throws SQLException{
        Connection connection=getConnection();

        //create the SELECT SQL                                                 
        StringBuffer sbselect = new StringBuffer();
        sbselect.append(" SELECT d.did, d.Category, d.mid, d.description, d.Expires, d.Start_Date, d.End_Date, d.org_price, d.Deal_price, d.QTY ");
        sbselect.append(" FROM Deal d, Merchant m ");	
	    sbselect.append(" WHERE m.mid=d.mid AND m.name LIKE ");
	    sbselect.append("'"+ "%"+ name +"%"+"'");
	
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
                            Deal tuple = new Deal();
                            tuple.setdid(rs.getString("did"));
                            tuple.setCategory(rs.getString("Category"));
                            tuple.setmid(rs.getString("mid"));
                            tuple.setdescription(rs.getString("description"));
                            tuple.setExpires(rs.getString("Expires"));
                            tuple.setStart_Date(rs.getString("Start_Date"));
                            tuple.setEnd_Date(rs.getInt("End_Date"));
                            tuple.setorg_price(rs.getFloat("org_price"));
                            tuple.setDeal_price(rs.getFloat("Deal_price"));
                            tuple.setqty(rs.getInt("QTY"));
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
        return(Deal[])collection.toArray(new Deal[0]);
    }
    
    //method to find deal based on location
    public Deal[] find2(String street,String city,String state,String country,int zip, int opt) throws SQLException{
        Connection connection=getConnection();
        StringBuffer sbselect = new StringBuffer();
        //all deals
        if (opt ==1){
   
        sbselect.append(" SELECT d.did, d.Category, d.mid, d.description, d.Expires, d.Start_Date, d.End_Date, d.org_price, d.Deal_price, d.QTY ");
        sbselect.append(" FROM Deal d, Branch b ");	
	    sbselect.append(" WHERE d.did=b.did AND b.street=  ");
	    sbselect.append("'"+ street +"'");
	    sbselect.append("  AND b.city=  ");
	    sbselect.append("'"+ city +"'");
	    sbselect.append("  AND b.state=  ");
	    sbselect.append("'"+ state +"'");
	    sbselect.append("  AND b.country=  ");
	    sbselect.append("'"+ country +"'");
	    sbselect.append("  AND b.zip=  ");
	    sbselect.append("'"+ zip +"'");
	    	    
        }
        //open deals
        if (opt ==2){
          
            sbselect.append(" SELECT d.did, d.Category, d.mid, d.description, d.Expires, d.Start_Date, d.End_Date, d.org_price, d.Deal_price, d.QTY ");
            sbselect.append(" FROM Deal d, Branch b ");	
    	    sbselect.append(" WHERE d.did=b.did AND b.street=  ");
    	    sbselect.append("'"+ street +"'");
    	    sbselect.append("  AND b.city=  ");
    	    sbselect.append("'"+ city +"'");
    	    sbselect.append("  AND b.state=  ");
    	    sbselect.append("'"+ state +"'");
    	    sbselect.append("  AND b.country=  ");
    	    sbselect.append("'"+ country +"'");
    	    sbselect.append("  AND b.zip=  ");
    	    sbselect.append("'"+ zip +"'");
    	    sbselect.append("  AND d.end_date > 20120505  ");
    	    	    
        }
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
                            Deal tuple = new Deal();
                            tuple.setdid(rs.getString("did"));
                            tuple.setCategory(rs.getString("Category"));
                            tuple.setmid(rs.getString("mid"));
                            tuple.setdescription(rs.getString("description"));
                            tuple.setExpires(rs.getString("Expires"));
                            tuple.setStart_Date(rs.getString("Start_Date"));
                            tuple.setEnd_Date(rs.getInt("End_Date"));
                            tuple.setorg_price(rs.getFloat("org_price"));
                            tuple.setDeal_price(rs.getFloat("Deal_price"));
                            tuple.setqty(rs.getInt("QTY"));
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
        return(Deal[])collection.toArray(new Deal[0]);
    }
    
    
    
    //method to print results of querry from array                              
    public void print(Deal[] db)
    {

	System.out.println("\ndid  Category  mid  description  Expires  Start_Date  End_Date  Org_Price  Deal_Price  QTY");
	System.out.println("__________________________________________________________________");
	System.out.println(db.length);
	for (int i=0; i<db.length; i++){
	    Deal mdb =db[i];
	    System.out.println(mdb.getdid() + "  " + mdb.getCategory() + "  " +
			       mdb.getmid() + "  " + mdb.getdescription() +"  "+mdb.getExpires()+"  " +mdb.getStart_Date()+"  "+mdb.getEnd_Date()+ "  "+mdb.getorg_price()+"  "+mdb.getDeal_price()+"  "+mdb.getqty() );
	}

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
		System.out.println("Deal Table doesn't exist. Creating new instance.....");
		createTable(connection);
	    }
    
	
    
	// create the INSERT SQL

	StringBuffer sbInsert = new StringBuffer();
	sbInsert.append(" INSERT INTO DEAL (did, Category, mid, description, Expires, Start_Date, End_Date, org_price, Deal_price, QTY) ");
	sbInsert.append(" VALUES ");
	sbInsert.append(" ('" + did + "', '" + Category + "','" + mid + "','" + description + "','" + Expires + "' ,'" + Start_Date + "','" + End_Date + "','" + org_price + "','" + Deal_price + "','" + qty + "' )");

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
    //check to see if deal exists
    public static boolean doesdidexist(String did) throws SQLException
    {
    Deal m=new Deal();
	Connection con=m.getConnection();
	boolean a=false;
	//creat SQL statement
	StringBuffer sbselect = new StringBuffer();
	sbselect.append(" SELECT DID ");
	sbselect.append(" FROM DEAL");
	sbselect.append(" WHERE DID=");
	sbselect.append("'" + did + "'");

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

    public String getdid() {
	return did;
    }

    public void setdid(String did) {
	this.did = did;
    }
  
    public String getCategory() {
	return Category;
    }
  
    public void setCategory(String Category) {
	this.Category = Category;
    }
  
    public String getmid() {
	return mid;
    }

    public void setmid(String mid){
	this.mid =mid;
    }

    public String getdescription(){
	return description;
    }
    
    public void setdescription(String description){
	this.description=description;
    }

    public float getorg_price(){
	return org_price;
    }

    public void setorg_price(float org_price){
	this.org_price=org_price;
    }
    
    public float getDeal_price(){
	return Deal_price;
    }

    public void setDeal_price(float Deal_price){
	this.Deal_price=Deal_price;
    }
    
    public String getExpires() {
	return Expires;
    }

    public void setExpires(String Expires){
	this.Expires =Expires;
    }
    
    public String getStart_Date() {
	return Start_Date;
    }

    public void setStart_Date(String Start_Date){
	this.Start_Date =Start_Date;
    }
    
    public int getEnd_Date() {
	return End_Date;
    }

    public void setEnd_Date(int End_Date){
	this.End_Date =End_Date;
    }
    
    public int getqty(){
	return qty;
    }

    public void setqty(int qty){
	this.qty=qty;
    }


}//end of class
 

