module org.example.week9csc311regex_bsiegel {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.week9csc311regex_bsiegel to javafx.fxml;
    exports org.example.week9csc311regex_bsiegel;
}