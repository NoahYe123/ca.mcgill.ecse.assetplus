package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;
import java.util.Random;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class AddTicketPopUpController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addTicketButton;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField raiserField;

    @FXML
    private DatePicker raisedDateField;

    @FXML
    private Label addTicketError;

    @FXML
    void initialize(){

        ticketNumberField.setEditable(true);
     
        // set editable to false so that the user cannot choose from the calendar
        raisedDateField.setEditable(false);
        // set default value to today
        raisedDateField.setValue(LocalDate.now());
        addTicketError.setText(null);

    }
    
    @FXML
    void addTicketClicked(ActionEvent event) {
        String ticketNumberString = ticketNumberField.getText();
        String description = descriptionField.getText();
        LocalDate date = raisedDateField.getValue();
        Date raisedDate = Date.valueOf(date);
       
        if (description == null || description.trim().isEmpty() || raisedDate == null){
                addTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage1"));
        }else{
            
            String err = AssetPlusFeatureSet4Controller.addMaintenanceTicket(Integer.parseInt(ticketNumberString), (java.sql.Date)raisedDate, description);
            ViewUtils.callController("");
            if (err == ""){
                ticketNumberField.setText("");
                descriptionField.setText("");
                raiserField.setText("");
             
                raisedDateField.setValue(null);
                addTicketError.setText(null);
                AssetPlusFXMLView.getInstance().closePopUpWindow();    
            }
            else{
                addTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage2"));
            }  

        }
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
}


