module eu.andreatt.examenatt_dein {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens eu.andreatt.examenatt_dein to javafx.fxml;
    exports eu.andreatt.examenatt_dein;
}