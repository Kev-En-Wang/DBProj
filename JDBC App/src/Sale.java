import java.sql.Date;

public class Sale {
    private Date date;
    private int id, vehicleId, custId, empId;
    private float discount, totPrice;


    //Setters and getters:
    public void setEID(int empId){
        this.empId=empId;
    }

    public int getEID(){
        return this.empId;
    }

    public void setPrice(float x){
        this.totPrice=x;
    }

    public float getPrice(){
        return this.totPrice;
    }

    public void setDiscount(float x){
        this.discount=x;
    }

    public float getDiscount(){
        return this.discount;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return this.date;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return this.id;
    }

    public void setVehicleId(int id){
        this.vehicleId=id;
    }

    public int getVehicleId(){
        return this.vehicleId;
    }

    public void setCustId(int id){
        this.custId=id;
    }

    public int getCustId(){
        return this.custId;
    }


    //Constructor for a sale item
    public Sale(int custId, Date date, int vid, float disc, float price, int sid, int eid){
        this.custId=custId;
        this.date=date;
        this.vehicleId=vid;
        this.totPrice=price;
        this.id=sid;
        this.discount=disc;
        this.empId=eid;
    }
} 
