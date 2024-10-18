package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.DisplayMaintenanceTicketController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;

public class ViewTicketPopUpController {

  @FXML
  private TextArea descriptionField;

  @FXML
  private Rectangle fieldBg;

  @FXML
  private TextField raisedOnDateField;

  @FXML
  private TextField ticketNumberField;

    private int ticketId;

    private TOMaintenanceTicket ticket;

    public void setTicketId(int ticketId) {
      this.ticketId = ticketId;
      initializeLabels();
    }

    private void initializeLabels() {
      ticket = ViewUtils.getTicket(ticketId);

      descriptionField.setText(ticket.getDescription());


      raisedOnDateField.setText(ticket.getRaisedOnDate().toString());
      ticketNumberField.setText(String.valueOf(ticket.getId()));
    }
}

