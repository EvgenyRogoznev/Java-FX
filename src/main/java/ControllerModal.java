import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerModal {

    @FXML
    private Button buttonCloseModal;

    @FXML
    public void initialize() {
        buttonCloseModal.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) buttonCloseModal.getScene().getWindow();
                stage.close();
            }
        });
    }
}
