

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    private Label menuLabel;

    @FXML
    private Button buttonBack;
    @FXML
    private VBox programmVbox;
    @FXML
    public Label counterLabel;
    @FXML
    private Button buttonStop;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Button buttonStart;
    @FXML
    private MenuItem aboutMItem;
    @FXML
    private MenuItem aboutDevMItem;

    @FXML
    private TextField textFildSetTime ;



    @FXML
    public void initialize() {
        VCounter vC = new VCounter(); //Создаю объект счётчик.


        /*далее реализована работа меню, через сокрытие рабочей программы и показыванеи label,
        с установкой надписи в зависимости от пункта меню и показыванием кнопки назад, которая возвращает
        настройки видимости в исходные.
         */
        aboutMItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                buttonBack.setVisible(true);
                menuLabel.setText("В пробирку посадили микроб ровно в текущее время.\n" +
                        " Каждую минуту микроб делится на два таких же\n" +
                        " микроба, те, в свою очередь, через минуту \n" +
                        "тоже делятся и т. д. В рамках курсового \n" +
                        "проекта должны быть реализованы: \n" +
                        "•\tДружественный графический интерфейс программы.\n" +
                        "   Интуитивно понятное управление. \n" +
                        "•\tСделать так, чтобы пользователь мог отслеживать\n" +
                        "   рост микробов и указывать время, когда количество \n" +
                        "   микробов должно перестать увеличиваться. \n" +
                        "•\tМеню «О программе», содержащее вкладки «Справка», «О\n" +
                        "   разработчике».\n" +
                        "   2019 г. TUSUR");
                menuLabel.setVisible(true);

                programmVbox.setVisible(false);

            }
        });
        aboutDevMItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                buttonBack.setVisible(true);
                menuLabel.setText("Developer Rogoznev E.V. \n My LinkedIn www.linkedin.com/in/evgenyRogoznev \n" +
                        "My VK https://vk.com/id74441389");
                menuLabel.setVisible(true);

                programmVbox.setVisible(false);

            }
        }
        );
        buttonBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                buttonBack.setVisible(false);
                menuLabel.setVisible(false);
                programmVbox.setVisible(true);

            }
        });

        /* далее реализованна работа самой программы*/
//кнопка старт получает  время запуска передаёт его  в отыформатированном виде в текст лейбла
        buttonStart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if(vC.isCounterWork()){     // выкинуть модальное окно
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modalWindow.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage1 = new Stage();
                        stage1.setScene(new Scene(root1));
                        stage1.setTitle("Внимание, идёт эксперимент");
                        stage1.setMinHeight(150);
                        stage1.setMinWidth(300);
                        stage1.setResizable(false);
                        stage1.initModality(Modality.WINDOW_MODAL);
                        stage1.initOwner(buttonStart.getScene().getWindow());
                        stage1.show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }                                                                                                     //для отладки
                else {
                    String startTime;
                    LocalTime time =  LocalTime.now();
                    vC.setStartT(time);
                    startTime=vC.getStartT().format(DateTimeFormatter.ofPattern("HH:mm"));
                    startTimeLabel.setText("Время запуска микроба " + startTime);
                    vC.setCounterWork(true);
                    vC.counter();
                    Thread writeCounter = new Thread(new Runnable() {
                        public void run() {
                            //use another thread so long process does not block gui
                            while (vC.isCounterWork()) {
                                String text = "В настоящий момент количество бактерий = " + vC.getCounterVirus();
                                //обновим текст в Label
                                Platform.runLater(() -> counterLabel.setText(text));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                    break;
                                }
                            }
                            Platform.runLater(() -> startTimeLabel.setText("Приложение не запущено"));
                        }
                    });
                    writeCounter.start();
                }
            }
        }
        );

        buttonStop.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LocalTime stopTime;
                String s=textFildSetTime.getText();
                if (s.length()!=5){
                    stopTime =LocalTime.now();
                }
                else{
                    stopTime= LocalTime.parse (s);
                }
                vC.setStopTime(stopTime);
                if(!(vC.getStopTime().isAfter(LocalTime.now())&&vC.isCounterWork())){
                    startTimeLabel.setText("Приложение не запущено");
                    vC.setCounterWork(false);
                    vC.setCounterVirus(1);
                    vC.stopCounter();
                }
            }
        }
        );
        Pattern p = Pattern.compile("([0-1]|2)" +
                "|([0-1][0-9])|(2[0-3])" +
                "|([0-1][0-9]:)|(2[0-3]:)" +
                "|([0-1][0-9]:[0-5])|(2[0-3]:[0-5])" +
                "|([0-1][0-9]:[0-5][0-9])|(2[0-3]:[0-5][0-9])?");

        textFildSetTime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFildSetTime.setText(oldValue);
        }
        );
    }
}
