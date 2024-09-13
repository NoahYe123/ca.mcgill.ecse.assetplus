package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Date;

public class ViewUtils {
  
  /** Calls the controller and shows an error, if applicable. */
  public static boolean callController(String result) {
    if (result.isEmpty()) {
      AssetPlusFXMLView.getInstance().refresh();
      return true;
    }
    showError(result);
    return false;
  }

  /** Calls the controller and returns true on success. This method is included for readability. */
  public static boolean successful(String controllerResult) {
    return callController(controllerResult);
  }

  /**
   * Creates a popup window.
   *
   * @param title: title of the popup window
   * @param message: message to display
   */
  public static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }

  public static void showError(String message) {
    makePopupWindow("Error", message);
  }

  public static ObservableList<TOMaintenanceTicket> getMaintenanceTickets() {
    List<TOMaintenanceTicket> ticket = AssetPlusFeatureSet6Controller.getTickets();
    return FXCollections.observableList(ticket);
  }


  public static ObservableList<TOMaintenanceNote> getTicketNotes(int id) {
    TOMaintenanceTicket ticket = AssetPlusFeatureSet6Controller.getTicket(id);

    if (ticket==null) {
      return null;
    }
    return FXCollections.observableList(ticket.getNotes());
  }
  
  public static TOMaintenanceTicket getTicket(int ticketId) {
	    return AssetPlusFeatureSet6Controller.getTicket(ticketId);
  }


  public static void deleteTicketsWithIds(List<Integer> ticketIds) {
    for (int id: ticketIds) {
      AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(id);
    }
  }
  
}
