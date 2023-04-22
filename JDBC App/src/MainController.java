/*
 * Made by Kevin Wang 2023
 * Based off of a previous project, modified
 * for database
 */

 import javafx.collections.FXCollections;
 import javafx.collections.ObservableList;
 import javafx.event.ActionEvent;
 import javafx.event.EventHandler;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import java.io.IOException;
 import java.util.ArrayList;
 
 import javafx.stage.Modality;
 import javafx.stage.Stage;
 import javafx.stage.WindowEvent;
 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.scene.control.*;
 import javafx.scene.control.ChoiceBox;
 import javafx.scene.control.TableView;
 import javafx.scene.control.Alert.AlertType;
 import javafx.scene.control.TableView.TableViewSelectionModel;
 import javafx.scene.control.cell.PropertyValueFactory;
 
 public class MainController extends App {
 
     /********************Control Settings*******************/
     @FXML private Button add;
     @FXML private Button edit;
     @FXML private Button delete;
     @FXML private Button search;
     @FXML private Button refresh;
     @FXML private TextField searchField;
     @FXML private TableView<Sale> output;
     @FXML private TableColumn<Sale, String> salesId;
     @FXML private TableColumn<Sale, String> custCol;
     @FXML private TableColumn<Sale, String> vehCol;
     @FXML private TableColumn<Sale, String> priceCol;
     /*******************************************************/
     /********************TableView Components*******************/
     @FXML private TableViewSelectionModel<Sale> selection; 
     @FXML private ObservableList<Sale> saleListObs;
     @FXML public static ObservableList<Sale> selectedSale;
     /**********************************************************/
     /********************Windown Components*******************/
     @FXML private Stage addScreenStage=new Stage();
     @FXML private Stage editScreenStage=new Stage();
     /*********************************************************/
     @FXML ChoiceBox<String> searchBy = new ChoiceBox<String>();
    
     private DatabaseAccess da = new DatabaseAccess();
     //Startup Code
     @FXML private void initialize() {
 
         //Configuring the choicebox
         searchBy.getItems().add("Sale ID");
         searchBy.getItems().add("Customer ID");
         searchBy.getItems().add("Vehicle ID");
         searchBy.setValue("Sale ID");
         
         //Event Handlers
         add.setOnAction(new addHandler());
         edit.setOnAction(new editHandler());
         delete.setOnAction(new deleteHandler());
         addScreenStage.setOnHidden(new saveOnExit());
         editScreenStage.setOnHidden(new saveOnExit());
         
         //Outer Methods
         search.setOnAction((event) ->  searchClicked());
         refresh.setOnAction(e->refreshClicked());
 
         //This loads the other two modalities so it doesn't load twice
         addScreenStage.initModality(Modality.APPLICATION_MODAL);
         editScreenStage.initModality(Modality.APPLICATION_MODAL);
         
         //Calls on the Database access object
         DatabaseAccess.initialize();

         //This should be fine. We did a good job. Good job us!
         ArrayList<Sale> salesList=da.getSalesList();
        
         /************************Table view part*******************************/
         //This makes an observable list from the loaded list for the controller
         saleListObs = FXCollections.observableArrayList(salesList);
         selection = output.getSelectionModel();
 
 
         //Sets the placeholder for no data
         output.setPlaceholder(new Label("No data"));
 
         //Sets the selection model for the tableview so you can only select one thing at a time
         selection.setSelectionMode(SelectionMode.SINGLE);
 
         //This loads the tableview from whatever information is in the
         //observable list
         tableLoad();
         /*********************************************************************/
     }
 
     /***********************INNER CLASSES*************************************/
     //This opens the Add screen
     private class addHandler implements EventHandler<ActionEvent>{
         @Override
         public void handle(ActionEvent c){
             try{
                 Parent root= FXMLLoader.load(getClass().getResource("AddSale.fxml"));
 
                 Scene addScreen = new Scene(root);
 
                 addScreenStage.setScene(addScreen);
                 addScreenStage.show();
             }
             catch(IOException a){
                 a.printStackTrace();
             }
         }
     }
 
     //This opens the edit window
     private class editHandler implements EventHandler<ActionEvent>{
         @Override
         public void handle(ActionEvent c){
 
             //This sets the selected song
             selectedSale= selection.getSelectedItems();
 
             //This is if nothing was selected
             try{
                selectedSale.get(0).getCustId();
             }
             catch(IndexOutOfBoundsException e){
                 Alert nothing = new Alert(AlertType.WARNING, 
                         "Nothing was selected");
                 nothing.show();
                 return;
             }
 
             try{
                 Parent root= FXMLLoader.load(getClass().getResource("EditSale.fxml"));
 
                 Scene editScreen = new Scene(root);
 
                 editScreenStage.setScene(editScreen);
                 editScreenStage.show();
             }
             catch(IOException a){
                 a.printStackTrace();
             }
         }
     }
     
     //This deletes whatever sale is selected
     private class deleteHandler implements EventHandler<ActionEvent>{
         public void handle(ActionEvent c){
 
             //This deletes the selected song
             selectedSale= selection.getSelectedItems();
             da.deleteSale(selectedSale.get(0));
             
             //This updates the list and reloads the table
             saleListObs = FXCollections.observableArrayList(da.getSalesList());
             tableLoad(); 
         }
     }    
     //This reloads the tableview with new info after you edit it
     private class saveOnExit implements EventHandler<WindowEvent>{
         @Override
         public void handle(WindowEvent closeWindowEvent) {
            saleListObs = FXCollections.observableArrayList(da.getSalesList());
            tableLoad(); 
         }
 
     }
     /*********************************************************/
 
 
     /***********************OUTER METHODS*********************/
     //This searches and displays only what was searched for
     private void searchClicked(){
         ArrayList<Sale> searchSale = new ArrayList<>();
         ArrayList<Sale> searchFrom = da.getSalesList();
         String query = searchField.getText();
         int query2 = Integer.parseInt(query);
 
         String searchType = searchBy.getValue();
         if(searchType=="Customer ID"){
             for(int n=0; n<searchFrom.size(); n++){
                 if(searchFrom.get(n).getCustId()==query2){
                     searchSale.add(searchFrom.get(n));
                 }
         }
         }
         else if(searchType=="Sale ID"){
             for(int n=0; n<searchFrom.size(); n++){
                 if(searchFrom.get(n).getId()==query2){
                    searchSale.add(searchFrom.get(n));
                 }
             }
         }
         else if(searchType=="Vehicle ID"){
            for(int n=0; n<searchFrom.size(); n++){
                if(searchFrom.get(n).getVehicleId()==query2){
                   searchSale.add(searchFrom.get(n));
                }
            }
         }
         else{
             Alert nothing = new Alert(AlertType.WARNING, 
                         "Please select search type");
             nothing.show();
             return;
         }
         
         //This refreshes the table with only what's selected
         saleListObs = FXCollections.observableArrayList(searchSale);
         tableLoad();
     }
 
     //For when someone hits the refresh button
     private void refreshClicked(){
         saleListObs = FXCollections.observableArrayList(da.getSalesList());
         tableLoad();
     }
 
     //This loads the observable list and displays it on the tableview
     @FXML private void tableLoad(){
         salesId.setCellValueFactory(new PropertyValueFactory<Sale, String>("id"));
         custCol.setCellValueFactory(new PropertyValueFactory<Sale, String>("custId"));
         vehCol.setCellValueFactory(new PropertyValueFactory<Sale, String>("vehicleId"));
         priceCol.setCellValueFactory(new PropertyValueFactory<Sale, String>("price"));
         output.setItems(saleListObs);
     }
 
     //This method is called by other windows to get the selected song
     public static Sale getSelectedSale(){
         return selectedSale.get(0);
     }
     /*********************************************************/
 }