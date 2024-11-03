package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;

import java.sql.Date;
import java.time.LocalDate;

public class AddNotePopUpController {

  private int ticketId;

  @FXML
  private Label instructionLabel;

  @FXML
  private Button addNoteButton;

  @FXML
  private Button cancelButton;

  @FXML
  private DatePicker datePicker;

  @FXML
  private TextArea descriptionField;

  @FXML
  private Label errorMessage;

  
  @FXML
  public void initialize() {
    ticketId = -1;
    errorMessage.setText("");

    LocalDate localDate = LocalDate.now();
    datePicker.setValue(localDate);
  }

  @FXML
  void AddNote(ActionEvent event) {
    String desc = descriptionField.getText();
    Date date = Date.valueOf(datePicker.getValue());

    if (desc.isEmpty()) {
      errorMessage.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.AddNote_ErrorDescription"));
    } else if (AssetPlusFeatureSet7Controller.addMaintenanceNote(date, desc, ticketId).isEmpty()) {
      errorMessage.setText("");
      ViewUtils.callController("");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
  }

  @FXML
  void Cancel(ActionEvent event) {
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  public void setTicketId(int id) {
    ticketId = id;
  }

}
