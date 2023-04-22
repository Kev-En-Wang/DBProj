/*
 * Made by Kevin Wang 2023
 * Modified from my previous work
 * ID 991681013
 * This is the controller for the add screen
 */
import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddSaleController {
    //Variables for each component
    @FXML private TextField custid;
    @FXML private TextField vid;
    @FXML private TextField empid;
    @FXML private TextField discount;
    @FXML private DatePicker date;
    @FXML private TextField price;
    @FXML private Button cancel;
    @FXML private Button confirm;

    @FXML private void initialize() {
        cancel.setOnAction( e -> cancelClicked() );
        confirm.setOnAction( e -> confirmClicked() );
    }
    /*Outer Methods************************************************/
    // Close this window
    private void cancelClicked(){
         Stage stage = (Stage) cancel.getScene().getWindow();
         //This tells the primary page to refresh the table view
         stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

         stage.close();
    }

    //Saves whatever you have and closes the window
    private void confirmClicked(){
        //Gets a database object
        DatabaseAccess da = new DatabaseAccess();

        //This creates a sale with the specified data
        int cid = Integer.parseInt(custid.getText());
        int vehid = Integer.parseInt(vid.getText());
        int eid= Integer.parseInt(empid.getText());
        float disc = Float.valueOf(discount.getText());
        float totPrice = Float.valueOf(price.getText());
        Date date2 = Date.valueOf(date.getValue());

        try{
            da.addSales(date2, vehid, cid, eid, disc, totPrice);
        }
        catch(Exception e){
            Alert added = new Alert(AlertType.INFORMATION, 
                        "One of your fields is invalid.");
        added.show();
        }
        
        //Lets the user know it's been added
        Alert added = new Alert(AlertType.INFORMATION, 
                        "Sale has been added");
        added.show();
        cancelClicked();
    }
}
