/*
 * Made by Kevin Wang 2023
 * This is the controller for the edit screen
 */

 import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.*;
 import javafx.scene.control.Alert.AlertType;
 import javafx.stage.Stage;
 import javafx.stage.WindowEvent;
 
 public class EditSaleController {
     //Variables for each component
    //Variables for each component
    @FXML private TextField custid;
    @FXML private TextField vid;
    @FXML private TextField empid;
    @FXML private TextField discount;
    @FXML private DatePicker date;
    @FXML private TextField price;
    @FXML private Button cancel;
    @FXML private Button confirm;
    private Sale oldSale;
 //Initializeing method
     @FXML private void initialize() {
         
         /************Events******************/
         cancel.setOnAction( e -> cancelClicked() );
         confirm.setOnAction( e -> confirmClicked() );
 
         /************Setting the textfields for the selected sale*************/
         custid.setText(String.valueOf(MainController.getSelectedSale().getCustId()));
         vid.setText(String.valueOf(MainController.getSelectedSale().getVehicleId()));
         empid.setText(String.valueOf(MainController.getSelectedSale().getEID()));
         discount.setText(String.valueOf(MainController.getSelectedSale().getDiscount()));
         price.setText(String.valueOf(MainController.getSelectedSale().getPrice()));
         date.setValue(MainController.getSelectedSale().getDate().toLocalDate());
         /********************************************************************/
 
         /*************Saving the original entry***************************************/
         oldSale = MainController.getSelectedSale();
         /************************************************************************/
 
     }
 
     // Close this window
     private void cancelClicked(){
         Stage stage = (Stage) cancel.getScene().getWindow();
         //This tells the primary page to refresh the table view
         stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
         //Closes the window
         stage.close();
     }
 

     private void confirmClicked(){
        DatabaseAccess da = new DatabaseAccess();
         
        //This creates a sale with the specified data
        //Literally copied over from addcontroller
        int cid = Integer.parseInt(custid.getText());
        int vehid = Integer.parseInt(vid.getText());
        int eid= Integer.parseInt(empid.getText());
        float disc = Float.valueOf(discount.getText());
        float totPrice = Float.valueOf(price.getText());
        Date date2 = Date.valueOf(date.getValue());

        try{        
            da.addSales(date2, vehid, cid, eid, disc, totPrice);

            //Only new part is here. You delete the old sale
            da.deleteSale(oldSale);
        }
        catch(Exception e){
            Alert added = new Alert(AlertType.INFORMATION, 
                        "One of your fields is invalid.");
        added.show();
        }



         //Lets the user know it's been added
         Alert added = new Alert(AlertType.INFORMATION, 
                         "Sale has been edited");
         added.show();
         cancelClicked();
     }
 }
 