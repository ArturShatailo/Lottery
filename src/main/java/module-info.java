module com.example.lotery {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lottery to javafx.fxml;
    exports com.example.lottery;
}