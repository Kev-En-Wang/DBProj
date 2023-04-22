import java.sql.*;
import java.util.ArrayList;

public class DatabaseAccess {

    private static PreparedStatement stmt;
    private static Connection con;
    private static ArrayList<Sale> salesList = new ArrayList<Sale>();
    private static String url = "jdbc:mysql://34.28.17.214:3306";
    private static String username = "root";
    private static String password = "";

    //For when they want the arraylist
    public ArrayList<Sale> getSalesList(){
        return salesList;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (con == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            return con;
        } else {
            return con;
        }
    }
    public static void initialize(){
        //Establishes a sql connection
        try{
            con = getConnection();
            
            //This creates a database if one doesn't exist
            String createDb = "CREATE DATABASE IF NOT EXISTS AUTOMOBILES";
            stmt = con.prepareStatement(createDb);
            stmt.execute();

            //Uses the database
            String useDb="use automobiles;";
            stmt = con.prepareStatement(useDb);
            stmt.execute();

            /**********************************Table creation if it doesn't exist********************************/
            String createCustomer="CREATE table IF NOT EXISTS customers(id int AUTO_INCREMENT PRIMARY KEY, first_name varchar(250), last_name varchar(250), address varchar(250)"+
            ", phoneNum int, email varchar(250));";
            stmt = con.prepareStatement(createCustomer);
            stmt.execute();

            String createVehicle="CREATE table IF NOT EXISTS vehicles(vin int AUTO_INCREMENT PRIMARY KEY, make varchar(250), model varchar(250), year int, color varchar(250),"+
            "price decimal(10,2));";
            stmt = con.prepareStatement(createVehicle);
            stmt.execute();

            String createService="CREATE table IF NOT EXISTS service(id int AUTO_INCREMENT PRIMARY KEY, name varchar(250), price decimal(10,2)"+");";
            stmt = con.prepareStatement(createService);
            stmt.execute();

            String createEmployee="CREATE table IF NOT EXISTS employee(id int AUTO_INCREMENT PRIMARY KEY, first_name varchar(250), last_name varchar(250), phone int, email"+
            " varchar(250), department varchar(250));";
            stmt = con.prepareStatement(createEmployee);
            stmt.execute();

            String createSales="CREATE table IF NOT EXISTS sales(id int AUTO_INCREMENT PRIMARY KEY, date DATE, vehicle_id int, customer_id int, employee_id int, discount decimal(10,2)"+
            ", total_price decimal(10,2), FOREIGN KEY (vehicle_id) REFERENCES vehicles(vin), FOREIGN KEY (customer_id) REFERENCES customers(id), FOREIGN KEY (employee_id) REFERENCES employee(id));";
            stmt = con.prepareStatement(createSales);
            stmt.execute();

            String saleService="CREATE table IF NOT EXISTS sale_service(sales_id int, service_id int, FOREIGN KEY (service_id) REFERENCES service(id), FOREIGN KEY (sales_id) REFERENCES sales(id));";
            stmt = con.prepareStatement(saleService);
            stmt.execute();
            /****************************************************************************************************************************************************************************************/
            /**
             * Populating database
             */
            /*Check if there are customers in the database. If there are, then you don't need to populate */
            String select = "SELECT * from customers;";
            stmt = con.prepareStatement(select);
            ResultSet result = stmt.executeQuery();
            if(!result.next()){
            //customers data
             String insert = "INSERT INTO customers(first_name, last_name, address, phoneNum, email) VALUES"+
             "('Robin','Baker','101 Ways Drive','88','robinbatwhat@gmail.com');";
             stmt = con.prepareStatement(insert);
             stmt.execute();

             insert = "INSERT INTO customers(first_name, last_name, address, phoneNum, email) VALUES"+
             "('Robert','Paulson','Sidestreet 5','12','unknown');";
             stmt = con.prepareStatement(insert);
             stmt.execute();

             insert = "INSERT INTO customers(first_name, last_name, address, phoneNum, email) VALUES"+
             "('Notta','Reelnaym', 'Burger King across the street','12','bk@bk.com');";
             stmt = con.prepareStatement(insert);
             stmt.execute();
            }

            //populate the employee
            select = "SELECT * from employee;";
            stmt = con.prepareStatement(select);
            result = stmt.executeQuery();
            if (!result.next()){
             String insert = "INSERT INTO employee(first_name, last_name, phone, email, department) VALUES"+
             "('Yonni','Brigs', '333','woah@woop.com','sales');";
             stmt = con.prepareStatement(insert);
             stmt.execute();

             insert = "INSERT INTO employee(first_name, last_name, phone, email, department) VALUES"+
             "('Stacy','Brigs', '444','wow@woop.com','sales');";
             stmt = con.prepareStatement(insert);
             stmt.execute();
            }

            //populate the vehicles
            select = "SELECT * from vehicles;";
            stmt = con.prepareStatement(select);
            result = stmt.executeQuery();
            if (!result.next()){
             String insert = "INSERT INTO vehicles(make, model, year, color, price) VALUES"+
             "('Ford','Sedan', '1931','Black',200.30);";
             stmt = con.prepareStatement(insert);
             stmt.execute();

             insert = "INSERT INTO vehicles(make, model, year, color, price) VALUES"+
             "('Subaru','Low Rider', '1999','White',6969.69);";
             stmt = con.prepareStatement(insert);
             stmt.execute();

             insert = "INSERT INTO vehicles(make, model, year, color, price) VALUES"+
             "('Bentley','Wagon', '2016','Purple',15234.22);";
             stmt = con.prepareStatement(insert);
             stmt.execute();
           

              insert = "INSERT INTO vehicles(make, model, year, color, price) VALUES"+
              "('Volkswagen','GTI', '1942','Green',1337.22);";
              stmt = con.prepareStatement(insert);
              stmt.execute();
           }

            
            
            //populate the sales
            select = "SELECT * from sales;";
            stmt = con.prepareStatement(select);
            result = stmt.executeQuery();
            if (!result.next()){
                String insert = "INSERT INTO sales(date, vehicle_id, customer_id, employee_id, discount, total_price) VALUES"+
                "('20230411','1','1','1','0',200.30);";
                stmt = con.prepareStatement(insert);
                stmt.execute();
   
                insert = "INSERT INTO sales(date, vehicle_id, customer_id, employee_id, discount, total_price) VALUES"+
                "('20230410','1','3','2','300',6669.69);";
                stmt = con.prepareStatement(insert);
                stmt.execute();

                insert = "INSERT INTO sales(date, vehicle_id, customer_id, employee_id, discount, total_price) VALUES"+
                "('20190201','4','3','2','0',1950.32);";
                stmt = con.prepareStatement(insert);
                stmt.execute();

                
                insert = "INSERT INTO sales(date, vehicle_id, customer_id, employee_id, discount, total_price) VALUES"+
                "('20220105','3','1','1','50',950.45);";
                stmt = con.prepareStatement(insert);
                stmt.execute();

                
                insert = "INSERT INTO sales(date, vehicle_id, customer_id, employee_id, discount, total_price) VALUES"+
                "('20000101','2','2','2','500',500.01);";
                stmt = con.prepareStatement(insert);
                stmt.execute();
            }
            con.close();
            load();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    //Populates the arraylist for use
    public static void load(){
        try{
        
        con=getConnection();
        String select = "Use automobiles;";
        stmt=con.prepareStatement(select);
        stmt.execute();

        //Select statement for sales
        select = "SELECT * from sales;";
        stmt=con.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        
        //Clears the list so we don't have duplicates
        salesList.clear();

        //This part adds the data
        while(rs.next()){
            Sale sale = new Sale(rs.getInt(4),rs.getDate(2),rs.getInt(3),rs.getFloat(6),rs.getFloat(7),rs.getInt(1),rs.getInt(5));
            salesList.add(sale);
        }
        con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void addSales(Date dat, int vehId, int custId, int empId, float disc, float pric){
        try{
            //Connecting to db
            con = getConnection();

            String select = "Use automobiles;";
            stmt=con.prepareStatement(select);
            stmt.execute();

            //Variables to be used for the sql statement
            String date = dat.toString();
            String vid = String.valueOf(vehId);
            String cid = String.valueOf(custId);
            String eid = String.valueOf(empId);
            String discount = String.valueOf(disc);
            String price = String.valueOf(pric);

            String insert = String.format("INSERT INTO sales(date, vehicle_id, customer_id, employee_id, discount, total_price) VALUES"+
            "('%s','%s','%s','%s','%s','%s');",date,vid,cid,eid,discount,price);
            stmt=con.prepareStatement(insert);
            stmt.execute();
            con.close();

            //loads the data again so the model has something to use
            load();
        }
        catch(Exception e){
            System.out.println(e);
            throw new Error("CAN'T, DUDE!\n"+e);
        }
    }

    public void deleteSale(Sale sale){
        try{
            con=getConnection();

            String select = "Use automobiles;";
            stmt=con.prepareStatement(select);
            stmt.execute();

            //Gets the id, deletes the id. Dead id :)
            String sid = String.valueOf(sale.getId());
            String delete=String.format("DELETE FROM sales WHERE id = %s",sid);
            stmt=con.prepareStatement(delete);
            stmt.execute();

            load();
        }
        catch(Exception r){
            System.out.println(r);
        }
    }
}
