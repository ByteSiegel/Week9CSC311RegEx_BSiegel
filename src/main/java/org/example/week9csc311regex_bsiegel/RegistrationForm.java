package org.example.week9csc311regex_bsiegel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A JavaFX application that functions as a registration form with specific input validations.
 *
 * <p>The form includes fields for First Name, Last Name, Email, Date of Birth, and Zip Code.
 * Input validation is performed using regular expressions and date parsing, and the "Add" button is enabled
 * only when all inputs are valid. Upon successful validation and clicking "Add," the application
 * navigates to a new user interface.</p>
 *
 * <p>This updated version includes a CSS style sheet to enhance the appearance of the UI components.</p>
 *
 * @author Benjamin Siegel
 */
public class RegistrationForm extends Application {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField dobField;
    private TextField zipCodeField;
    private Button addButton;

    private boolean isFirstNameValid = false;
    private boolean isLastNameValid = false;
    private boolean isEmailValid = false;
    private boolean isDobValid = false;
    private boolean isZipCodeValid = false;

    /**
     * The main entry point for all JavaFX applications.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by setting up the user interface and event handling.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        // Set up the UI components
        primaryStage.setTitle("Registration Form");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        // Set column constraints to fix column widths
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(100); // Width for labels like "First Name:"
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(200); // Width for input fields
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(150); // Width for validation labels
        grid.getColumnConstraints().addAll(col1, col2, col3);

        // First Name
        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.getStyleClass().add("label");
        firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");
        firstNameField.getStyleClass().add("text-field");
        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameField, 1, 0);

        // Last Name
        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.getStyleClass().add("label");
        lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        lastNameField.getStyleClass().add("text-field");
        grid.add(lastNameLabel, 0, 1);
        grid.add(lastNameField, 1, 1);

        // Email
        Label emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("label");
        emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.getStyleClass().add("text-field");
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);

        // Date of Birth
        Label dobLabel = new Label("Date of Birth:");
        dobLabel.getStyleClass().add("label");
        dobField = new TextField();
        dobField.setPromptText("MM/DD/YYYY");
        dobField.getStyleClass().add("text-field");
        grid.add(dobLabel, 0, 3);
        grid.add(dobField, 1, 3);

        // Zip Code
        Label zipCodeLabel = new Label("Zip Code:");
        zipCodeLabel.getStyleClass().add("label");
        zipCodeField = new TextField();
        zipCodeField.setPromptText("Enter your zip code");
        zipCodeField.getStyleClass().add("text-field");
        grid.add(zipCodeLabel, 0, 4);
        grid.add(zipCodeField, 1, 4);

        // Validation Labels with fixed widths
        Label firstNameValidationLabel = new Label();
        firstNameValidationLabel.setPrefWidth(150);
        firstNameValidationLabel.getStyleClass().add("validation-label");
        grid.add(firstNameValidationLabel, 2, 0);

        Label lastNameValidationLabel = new Label();
        lastNameValidationLabel.setPrefWidth(150);
        lastNameValidationLabel.getStyleClass().add("validation-label");
        grid.add(lastNameValidationLabel, 2, 1);

        Label emailValidationLabel = new Label();
        emailValidationLabel.setPrefWidth(150);
        emailValidationLabel.getStyleClass().add("validation-label");
        grid.add(emailValidationLabel, 2, 2);

        Label dobValidationLabel = new Label();
        dobValidationLabel.setPrefWidth(150);
        dobValidationLabel.getStyleClass().add("validation-label");
        grid.add(dobValidationLabel, 2, 3);

        Label zipCodeValidationLabel = new Label();
        zipCodeValidationLabel.setPrefWidth(150);
        zipCodeValidationLabel.getStyleClass().add("validation-label");
        grid.add(zipCodeValidationLabel, 2, 4);

        // Add Button
        addButton = new Button("Add");
        addButton.setDisable(true);
        addButton.getStyleClass().add("button");
        grid.add(addButton, 1, 5);

        // Focus listeners for validation
        firstNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                isFirstNameValid = validateFirstName();
                if (isFirstNameValid) {
                    firstNameValidationLabel.setText("✓");
                    firstNameValidationLabel.setTextFill(Color.GREEN);
                    firstNameField.getStyleClass().remove("invalid-field");
                } else {
                    firstNameValidationLabel.setText("Invalid (2-25 chars)");
                    firstNameValidationLabel.setTextFill(Color.RED);
                    if (!firstNameField.getStyleClass().contains("invalid-field")) {
                        firstNameField.getStyleClass().add("invalid-field");
                    }
                }
                updateAddButtonState();
            }
        });

        lastNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                isLastNameValid = validateLastName();
                if (isLastNameValid) {
                    lastNameValidationLabel.setText("✓");
                    lastNameValidationLabel.setTextFill(Color.GREEN);
                    lastNameField.getStyleClass().remove("invalid-field");
                } else {
                    lastNameValidationLabel.setText("Invalid (2-25 chars)");
                    lastNameValidationLabel.setTextFill(Color.RED);
                    if (!lastNameField.getStyleClass().contains("invalid-field")) {
                        lastNameField.getStyleClass().add("invalid-field");
                    }
                }
                updateAddButtonState();
            }
        });

        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                isEmailValid = validateEmail();
                if (isEmailValid) {
                    emailValidationLabel.setText("✓");
                    emailValidationLabel.setTextFill(Color.GREEN);
                    emailField.getStyleClass().remove("invalid-field");
                } else {
                    emailValidationLabel.setText("Invalid email");
                    emailValidationLabel.setTextFill(Color.RED);
                    if (!emailField.getStyleClass().contains("invalid-field")) {
                        emailField.getStyleClass().add("invalid-field");
                    }
                }
                updateAddButtonState();
            }
        });

        dobField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                isDobValid = validateDob();
                if (isDobValid) {
                    dobValidationLabel.setText("✓");
                    dobValidationLabel.setTextFill(Color.GREEN);
                    dobField.getStyleClass().remove("invalid-field");
                } else {
                    dobValidationLabel.setText("Invalid date");
                    dobValidationLabel.setTextFill(Color.RED);
                    if (!dobField.getStyleClass().contains("invalid-field")) {
                        dobField.getStyleClass().add("invalid-field");
                    }
                }
                updateAddButtonState();
            }
        });

        zipCodeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                isZipCodeValid = validateZipCode();
                if (isZipCodeValid) {
                    zipCodeValidationLabel.setText("✓");
                    zipCodeValidationLabel.setTextFill(Color.GREEN);
                    zipCodeField.getStyleClass().remove("invalid-field");
                } else {
                    zipCodeValidationLabel.setText("Invalid zip code");
                    zipCodeValidationLabel.setTextFill(Color.RED);
                    if (!zipCodeField.getStyleClass().contains("invalid-field")) {
                        zipCodeField.getStyleClass().add("invalid-field");
                    }
                }
                updateAddButtonState();
            }
        });

        // Add Button Action
        addButton.setOnAction(e -> {
            if (isFirstNameValid && isLastNameValid && isEmailValid && isDobValid && isZipCodeValid) {
                showSuccessUI(primaryStage);
            }
        });

        Scene scene = new Scene(grid, 650, 350);

        // Load the CSS style sheet
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Validates the First Name input using a regular expression.
     * Ensures the name is between 2 and 25 characters.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateFirstName() {
        String firstName = firstNameField.getText();
        return firstName.matches(".{2,25}");
    }

    /**
     * Validates the Last Name input using a regular expression.
     * Ensures the name is between 2 and 25 characters.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateLastName() {
        String lastName = lastNameField.getText();
        return lastName.matches(".{2,25}");
    }

    /**
     * Validates the Email input to accept only valid Farmingdale email addresses.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateEmail() {
        String email = emailField.getText();
        return email.matches("^[A-Za-z0-9._%+-]+@farmingdale\\.edu$");
    }

    /**
     * Validates the Date of Birth input to ensure it is a valid date in MM/DD/YYYY format.
     * Uses DateTimeFormatter and LocalDate to parse and validate the date.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateDob() {
        String dob = dobField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(dob, formatter);
            // Optionally, check if the date is in the future
            return !parsedDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Validates the Zip Code input to ensure it is a 5-digit number.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateZipCode() {
        String zipCode = zipCodeField.getText();
        return zipCode.matches("^\\d{5}$");
    }

    /**
     * Updates the state of the "Add" button based on the validity of all inputs.
     */
    private void updateAddButtonState() {
        addButton.setDisable(!(isFirstNameValid && isLastNameValid && isEmailValid && isDobValid && isZipCodeValid));
    }

    /**
     * Displays a new user interface upon successful registration.
     *
     * @param primaryStage the primary stage for this application
     */
    private void showSuccessUI(Stage primaryStage) {
        Label successLabel = new Label("Registration Successful!");
        successLabel.getStyleClass().add("success-label");
        VBox vbox = new VBox(successLabel);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 400, 200);

        // Load the CSS style sheet
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
    }
}
