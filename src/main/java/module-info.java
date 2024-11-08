module eu.andreatt.examenatt_dein {
    requires javafx.controls;
    requires javafx.fxml;


    opens eu.andreatt.examenatt_dein to javafx.fxml;
    exports eu.andreatt.examenatt_dein;
}