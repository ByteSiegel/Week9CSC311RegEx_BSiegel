package org.example.week9csc311regex_bsiegel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A JavaFX application that functions as a registration form with specific input validations.
 *
 * <p>The form includes fields for First Name, Last Name, Email, Date of Birth, and Zip Code.
 * Input validation is performed using regular expressions, and the "Add" button is enabled
 * only when all inputs are valid. Upon successful validation and clicking "Add," the application
 * navigates to a new user interface.</p>
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

        // First Name
        Label firstNameLabel = new Label("First Name:");
        firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");
        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameField, 1, 0);

        // Last Name
        Label lastNameLabel = new Label("Last Name:");
        lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        grid.add(lastNameLabel, 0, 1);
        grid.add(lastNameField, 1, 1);

        // Email
        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("Enter your email");
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);

        // Date of Birth
        Label dobLabel = new Label("Date of Birth:");
        dobField = new TextField();
        dobField.setPromptText("MM/DD/YYYY");
        grid.add(dobLabel, 0, 3);
        grid.add(dobField, 1, 3);

        // Zip Code
        Label zipCodeLabel = new Label("Zip Code:");
        zipCodeField = new TextField();
        zipCodeField.setPromptText("Enter your zip code");
        grid.add(zipCodeLabel, 0, 4);
        grid.add(zipCodeField, 1, 4);

        // Validation Labels
        Label firstNameValidationLabel = new Label();
        grid.add(firstNameValidationLabel, 2, 0);
        Label lastNameValidationLabel = new Label();
        grid.add(lastNameValidationLabel, 2, 1);
        Label emailValidationLabel = new Label();
        grid.add(emailValidationLabel, 2, 2);
        Label dobValidationLabel = new Label();
        grid.add(dobValidationLabel, 2, 3);
        Label zipCodeValidationLabel = new Label();
        grid.add(zipCodeValidationLabel, 2, 4);

        // Add Button
        addButton = new Button("Add");
        addButton.setDisable(true);
        grid.add(addButton, 1, 5);

        // Focus listeners for validation
        firstNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                isFirstNameValid = validateFirstName();
                if (isFirstNameValid) {
                    firstNameValidationLabel.setText("✓");
                    firstNameValidationLabel.setTextFill(Color.GREEN);
                } else {
                    firstNameValidationLabel.setText("Invalid (2-25 chars)");
                    firstNameValidationLabel.setTextFill(Color.RED);
                }
                updateAddButtonState();
            }
        });

        lastNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                isLastNameValid = validateLastName();
                if (isLastNameValid) {
                    lastNameValidationLabel.setText("✓");
                    lastNameValidationLabel.setTextFill(Color.GREEN);
                } else {
                    lastNameValidationLabel.setText("Invalid (2-25 chars)");
                    lastNameValidationLabel.setTextFill(Color.RED);
                }
                updateAddButtonState();
            }
        });

        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                isEmailValid = validateEmail();
                if (isEmailValid) {
                    emailValidationLabel.setText("✓");
                    emailValidationLabel.setTextFill(Color.GREEN);
                } else {
                    emailValidationLabel.setText("Invalid email");
                    emailValidationLabel.setTextFill(Color.RED);
                }
                updateAddButtonState();
            }
        });

        dobField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                isDobValid = validateDob();
                if (isDobValid) {
                    dobValidationLabel.setText("✓");
                    dobValidationLabel.setTextFill(Color.GREEN);
                } else {
                    dobValidationLabel.setText("Invalid date format");
                    dobValidationLabel.setTextFill(Color.RED);
                }
                updateAddButtonState();
            }
        });

        zipCodeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                isZipCodeValid = validateZipCode();
                if (isZipCodeValid) {
                    zipCodeValidationLabel.setText("✓");
                    zipCodeValidationLabel.setTextFill(Color.GREEN);
                } else {
                    zipCodeValidationLabel.setText("Invalid zip code");
                    zipCodeValidationLabel.setTextFill(Color.RED);
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
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Validates the First Name input using a regular expression.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateFirstName() {
        String firstName = firstNameField.getText();
        return firstName.matches(".{2,25}");
    }

    /**
     * Validates the Last Name input using a regular expression.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateLastName() {
        String lastName = lastNameField.getText();
        return lastName.matches(".{2,25}");
    }

    /**
     * Validates the Email input to accept only Farmingdale valid email addresses.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateEmail() {
        String email = emailField.getText();
        return email.matches("^[A-Za-z0-9._%+-]+@farmingdale\\.edu$");
    }

    /**
     * Validates the Date of Birth input to ensure it is in MM/DD/YYYY format.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateDob() {
        String dob = dobField.getText();
        return dob.matches("^\\d{2}/\\d{2}/\\d{4}$");
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
        successLabel.setFont(new Font("Arial", 24));
        VBox vbox = new VBox(successLabel);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
    }
}
