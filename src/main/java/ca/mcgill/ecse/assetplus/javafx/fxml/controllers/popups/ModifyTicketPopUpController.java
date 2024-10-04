package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.time.LocalDate;
import java.sql.Date;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;

public class ModifyTicketPopUpController {

    private int ticketId;

    private static TOMaintenanceTicket ticket;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private TextArea descriptionField;
    
    @FXML
    private Rectangle fieldBg;
    
    @FXML
    private Button modifyTicketButton;
   
    @FXML
    private DatePicker raisedDateField;
    
    @FXML
    private TextField ticketNumberField;
    
    @FXML
    private TextField typeField;
    
    @FXML
    private Label updateTicketError;

    @FXML
    void initialize(){ 
        updateTicketError.setText(null);
        
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void updateTicketClicked(ActionEvent event) {
        String ticketNumberString = ticketNumberField.getText();
        String description = descriptionField.getText();
        LocalDate date = raisedDateField.getValue();
        Date raisedDate = Date.valueOf(date);
      

        if (description == null || description.trim().isEmpty() || raisedDate == null || raisedDate == null){
                updateTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage1"));
        }
        else{
            int ticketNumber = Integer.parseInt(ticketNumberString);
            String err = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(ticketNumber, (java.sql.Date)raisedDate, description);
            ViewUtils.callController("");   
            if (err == ""){
                ticketNumberField.setText("");
                descriptionField.setText("");

                raisedDateField.setValue(null);
                updateTicketError.setText(null);
                AssetPlusFXMLView.getInstance().closePopUpWindow();    
            }
            else{
                updateTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage2"));
            }  

        }
    }

    public void setTicketId(int id) {
        ticketId = id;
        // set editable to false so that the user cannot choose from the calendar
        raisedDateField.setEditable(false);
        ticketNumberField.setEditable(false);
        
        ticket = AssetPlusFeatureSet6Controller.getTicket(ticketId);
        
        ticketNumberField.setText(Integer.toString(ticketId));
        descriptionField.setText(ticket.getDescription());
        raisedDateField.setValue(ticket.getRaisedOnDate().toLocalDate());
        
        updateTicketError.setText(null);
  }
}
