module com.example.miniproyect2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miniproyect2 to javafx.fxml;
    exports com.example.miniproyect2;
}