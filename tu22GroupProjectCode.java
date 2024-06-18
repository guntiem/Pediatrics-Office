package tu22GroupProjectCode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tu22GroupProjectCode extends Application {

    private Stage primaryStage;
    private Scene mainMenuScene;
    private Scene patientProfileScene;
    private Scene prescriptionScene;
    private Scene messageScene;
    private Scene patientMessageScene;
    private Scene appointmentScene;
    private Scene newPatientScene;
    private Scene vitalSignsScene;
    private Scene patientHistoryScene;
    private Scene messageManagementScene;
    
    private ArrayList<String> patientUsernameList = new ArrayList<String>(); // ArrayList containing all patient IDs

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Pediatric Doctor Office");

        // Create login scenes for different user roles
        Scene patientLoginScene = createLoginScene("Patient Login", "Patient View");
        Scene doctorLoginScene = createLoginScene("Doctor Login", "Doctor View");
        Scene nurseLoginScene = createLoginScene("Nurse Login", "Nurse View");
        Scene createPatientAccount = createCreatePatientAccountScene();
        

        // Create main menu scene
        createMainMenuScene(patientLoginScene, doctorLoginScene, nurseLoginScene, createPatientAccount);

        // Create patient profile scene
        createPatientProfileScene();

        // Create vital signs scene
        createVitalSignsScene();

        // Create patient history scene
        createPatientHistoryScene();

        // Create message management scene
        createMessageManagementScene();

        // Set the main menu scene
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }


    // Create a login scene for a specific user role
    private Scene createLoginScene(String title, String mainMenuTitle) {
        Label titleLabel = new Label(title);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        // Event handler for login button
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Perform authentication based on user role
            if (title.equals("Patient Login")) {
                if (authenticatePatient(username, password)) {
                    primaryStage.setScene(createInfoDisplayScene("Patient Information"));
                } else {
                    System.out.println("Patient authentication failed.");
                }
            } else if (title.equals("Doctor Login")) {
                if (authenticateDoctor(username, password)) {
                    primaryStage.setScene(createInfoDisplayScene("Doctor Information"));
                } else {
                    System.out.println("Doctor authentication failed.");
                }
            } else if (title.equals("Nurse Login")) {
                if (authenticateNurse(username, password)) {
                    primaryStage.setScene(createInfoDisplayScene("Nurse Information"));
                } else {
                    System.out.println("Nurse authentication failed.");
                }
            }
        });

        VBox root = new VBox(20, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        return new Scene(root, 500, 500);
    }

    // Create the main menu scene
    private void createMainMenuScene(Scene patientLoginScene, Scene doctorLoginScene, Scene nurseLoginScene, 
    		Scene createPatientAccount) {
    	// main UI
    	HBox main = new HBox();
    	// Two sections within main UI
    	VBox lMain = new VBox(80);
    	lMain.setMinWidth(300);
    	lMain.setMinHeight(350);
        Text title = new Text("	 Little Hearts\n	 Pediatrics.");
        title.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
        title.setFill(Color.rgb(22,100,105));
        Text missionStmt = new Text("Your health,\nOur mission.");
        missionStmt.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 36));
        missionStmt.setFill(Color.rgb(22,100,105));
        Text webUrl = new Text("www.littlehearts.com");
        webUrl.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 10));
        webUrl.setFill(Color.rgb(22,100,105));
        lMain.getChildren().addAll(title, missionStmt, webUrl);
    	
    	VBox rMain = new VBox(30);
    	rMain.setMinWidth(300);
    	rMain.setMinHeight(320);
    	rMain.setStyle("-fx-border-color: blue; -fx-border-width: 2;");
    	Text role = new Text("What is your role:");
        title.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
    	// Create buttons for different user roles
        Button patientButton = new Button("Patient View");
        Button doctorButton = new Button("Doctor View");
        Button nurseButton = new Button("Nurse View");
        Button createPatient = new Button("Create Patient");
        // Set action for patient button
        patientButton.setOnAction(event -> primaryStage.setScene(patientLoginScene));
        // Set action for doctor button
        doctorButton.setOnAction(event -> primaryStage.setScene(doctorLoginScene));
        // Set action for nurse button
        nurseButton.setOnAction(event -> primaryStage.setScene(nurseLoginScene));
        // Set action for create patient
        createPatient.setOnAction(event -> primaryStage.setScene(createPatientAccount));
        rMain.getChildren().addAll(role, patientButton, doctorButton, nurseButton, createPatient);
        BackgroundFill rBackgroundFill = new BackgroundFill(Color.WHITE, null, null);
        Background rBackground = new Background(rBackgroundFill);
        rMain.setBackground(rBackground);
        rMain.setAlignment(Pos.CENTER);
        
        main.getChildren().addAll(lMain, rMain);
        main.setPadding(new Insets(20));
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        main.setBackground(background);

        // Create the main menu scene
        mainMenuScene = new Scene(main, 625, 350);
    }

    private Scene createCreatePatientAccountScene() {
    	// main UI
    	HBox main = new HBox();
    	// Two sections within main UI
    	VBox lMain = new VBox(80);
    	lMain.setMinWidth(300);
    	lMain.setMinHeight(350);
        Text title = new Text("	 Little Hearts\n	 Pediatrics.");
        title.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
        title.setFill(Color.rgb(22,100,105));
        Text missionStmt = new Text("Your health,\nOur mission.");
        missionStmt.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 36));
        missionStmt.setFill(Color.rgb(22,100,105));
        Text webUrl = new Text("www.littlehearts.com");
        webUrl.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 10));
        webUrl.setFill(Color.rgb(22,100,105));
        lMain.getChildren().addAll(title, missionStmt, webUrl);
    	
    	VBox rMain = new VBox(25);
    	rMain.setMinWidth(300);
    	rMain.setMinHeight(320);
    	rMain.setStyle("-fx-border-color: blue; -fx-border-width: 2;");
        HBox rTop = new HBox(5);
        VBox rTopL = new VBox(10);
    	Text rTitle = new Text("Create Account");
        title.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        title.setFill(Color.rgb(22,100,105));
        Text firstName = new Text("First Name");
        firstName.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        TextField firstNameField = new TextField();
        Text birthday = new Text("Birthday Name");
        birthday.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        TextField bdayField = new TextField();
        rTopL.getChildren().addAll(firstName, firstNameField, birthday, bdayField);
        VBox rTopR = new VBox(10);
        Text lastName = new Text("Last Name");
        lastName.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        TextField lastNameField = new TextField();
        Text pnum = new Text("Phone Number");
        pnum.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        TextField pnumField = new TextField();
        rTopR.getChildren().addAll(lastName, lastNameField, pnum, pnumField);
        rTop.getChildren().addAll(rTopL, rTopR);
        
        VBox rBottom = new VBox(10);
        Text username = new Text("Username");
        username.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        TextField usernameField = new TextField();
        Text pass = new Text("Password");
        pass.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        TextField passField = new TextField();
        Button createPatient = new Button("Create Patient");
        // Set action for patient button
        // Inside createCreatePatientAccountScene method
        Label errorMessage = new Label("");
        errorMessage.setTextFill(Color.RED);

        createPatient.setOnAction(e -> {
            // declare strings to store patient information
            String fName = firstNameField.getText();
            String lName = lastNameField.getText();
            String bday = bdayField.getText();
            String phoneNumber = pnumField.getText();
            String uname = usernameField.getText();
            String pword = passField.getText();
            
            firstNameField.clear();
            lastNameField.clear();
            bdayField.clear();
            pnumField.clear();
            usernameField.clear();
            passField.clear();

            // create the patient
            Patient newPatient = createPatient(fName, lName, bday, phoneNumber, uname, pword, usernameField, passField, errorMessage);

            // check if the patient was successfully created
            if (newPatient != null) {
                // Open the new window as if the patient logged in
                primaryStage.setScene(createInfoDisplayScene("Patient Information"));
            }
        });


        rBottom.getChildren().addAll(username, usernameField, pass, passField, createPatient, errorMessage);

        
    	
        rMain.getChildren().addAll(rTop, rBottom);
        BackgroundFill rBackgroundFill = new BackgroundFill(Color.WHITE, null, null);
        Background rBackground = new Background(rBackgroundFill);
        rMain.setBackground(rBackground);
        rMain.setAlignment(Pos.CENTER);
        
        main.getChildren().addAll(lMain, rMain);
        main.setPadding(new Insets(20));
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        main.setBackground(background);

        return mainMenuScene = new Scene(main, 625, 350);
    }
    
    private class Patient {
        private String firstName = "";
        private String lastName = "";
        private String bday = "";
        private String phoneNum = "";
        private String username = "";
        private String password = "";

        private Patient(String fn, String ln, String bd, String pnum, String un, String pw) {
            firstName = fn;
            lastName = ln;
            bday = bd;
            phoneNum = pnum;
            username = un;
            password = pw;
        }

        public String getPatientName() {
            return firstName + " " + lastName;
        }
    }
    
    private Patient createPatient(String fn, String ln, String bday, String pnum, String un, String pw, TextField usernameField, TextField passField, Label errorMessage) {
        if (!checkPatientUN(un)) {
            // Username already exists, clear the fields and show an error message
            usernameField.clear();
            passField.clear();
            errorMessage.setText("Username already exists. Please choose a different one.");
            return null;
        } else {
            // Username is unique, add it to the list and create the patient
            patientUsernameList.add(un);
            Patient patient = new Patient(fn, ln, bday, pnum, un, pw);

            // Create a file with the patient's information
            String filename = String.format("%s_PatientInfo.txt", un);
            try (PrintWriter out = new PrintWriter(filename)) {
                out.println(patient.toString());
            } catch (FileNotFoundException e) {
                System.err.println("Error creating patient file: " + e.getMessage());
            }

            return patient;
        }
    }

    
    private boolean checkPatientUN(String un) {
        if (patientUsernameList.contains(un)) {
            return false;
        }
        return true;
    }
    
    // Create the patient profile scene
    private void createPatientProfileScene() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Patient Section
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        DatePicker dobPicker = new DatePicker();
        dobPicker.setPromptText("MM/DD/YYYY");
        Button createButton = new Button("Create");
        TextField patientSearchField = new TextField();
        patientSearchField.setPromptText("MM/DD/YYYY");
        Button searchButton = new Button("Search");
        Button enterVitalsButton = new Button("Enter Vitals");

        // Vitals Section
        TextField weightField = new TextField();
        weightField.setPromptText("Weight");
        TextField heightField = new TextField();
        heightField.setPromptText("Height");
        TextField bodyTempField = new TextField();
        bodyTempField.setPromptText("Body Temp");
        TextField bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Blood Pressure");
        TextField allergiesField = new TextField();
        allergiesField.setPromptText("Known allergies");
        TextField healthConcernsField = new TextField();
        healthConcernsField.setPromptText("Health Concerns");

        // Doctor Section (hidden for nurses)
        TextField physicalTestResultsField = new TextField();
        physicalTestResultsField.setPromptText("Physical Test Results");
        TextField prescriptionField = new TextField();
        prescriptionField.setPromptText("Prescription");

        // Buttons
        Button viewMessagesButton = new Button("View Messages");
        Button viewPatientHealthHistoryButton = new Button("View Patient Health History");
        viewPatientHealthHistoryButton.setOnAction(e -> primaryStage.setScene(patientHistoryScene)); // Event handler for the button
        Button submitForTodayVisitButton = new Button("Submit for today's visit");

        // Layout for Patient Section
        gridPane.add(new Label("Patient"), 0, 0);
        gridPane.add(firstNameField, 0, 1);
        gridPane.add(lastNameField, 0, 2);
        gridPane.add(dobPicker, 0, 3);
        gridPane.add(createButton, 1, 3);
        gridPane.add(patientSearchField, 0, 4);
        gridPane.add(searchButton, 1, 4);
        gridPane.add(enterVitalsButton, 0, 5);

        // Layout for Vitals Section
        gridPane.add(new Label("Vitals:"), 2, 0);
        gridPane.add(weightField, 2, 1);
        gridPane.add(heightField, 2, 2);
        gridPane.add(bodyTempField, 2, 3);
        gridPane.add(bloodPressureField, 2, 4);
        gridPane.add(allergiesField, 2, 5);
        gridPane.add(healthConcernsField, 2, 6);

        // Layout for Doctor Section (conditionally added based on user role)
        // if (currentUserRole.equals("Doctor")) {
        gridPane.add(new Label("For Doctor Use Only:"), 4, 0);
        gridPane.add(physicalTestResultsField, 4, 1);
        gridPane.add(prescriptionField, 4, 2);
        // }

        // Layout for Buttons
        gridPane.add(viewMessagesButton, 4, 5);
        gridPane.add(viewPatientHealthHistoryButton, 4, 6);
        gridPane.add(submitForTodayVisitButton, 4, 7);

        // Back Button
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(event -> primaryStage.setScene(mainMenuScene));
        gridPane.add(backButton, 4, 8);

        patientProfileScene = new Scene(gridPane, 1024, 768);
    }

    // Create the vital signs scene
    private void createVitalSignsScene() {
        Label titleLabel = new Label("Vital Signs Entry");
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(event -> primaryStage.setScene(mainMenuScene));

        VBox root = new VBox(20, titleLabel, backButton);
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        vitalSignsScene = new Scene(root, 300, 200);
    }

    // Create the patient history scene
    private void createPatientHistoryScene() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Select date of visit
        DatePicker visitDatePicker = new DatePicker();
        visitDatePicker.setPromptText("MM/DD/YYYY");

        // Health history text areas
        TextArea healthIssuesArea = new TextArea();
        healthIssuesArea.setPromptText("Health Issues");
        TextArea prescribedMedicationArea = new TextArea();
        prescribedMedicationArea.setPromptText("Prescribed Medication");
        TextArea historyOfImmunizationsArea = new TextArea();
        historyOfImmunizationsArea.setPromptText("History of Immunizations");

        // Layout
        gridPane.add(new Label("Select Date of Visit"), 0, 0);
        gridPane.add(visitDatePicker, 0, 1);
        gridPane.add(new Label("Health Issues"), 1, 0);
        gridPane.add(healthIssuesArea, 1, 1);
        gridPane.add(new Label("Prescribed Medication"), 1, 2);
        gridPane.add(prescribedMedicationArea, 1, 3);
        gridPane.add(new Label("History of Immunizations"), 1, 4);
        gridPane.add(historyOfImmunizationsArea, 1, 5);

        // Back Button
        Button backButton = new Button("Back to Patient Health Profile");
        backButton.setOnAction(event -> primaryStage.setScene(patientProfileScene));
        gridPane.add(backButton, 1, 6);

        patientHistoryScene = new Scene(gridPane, 1024, 768);
    }

    // Create the message management scene
    private void createMessageManagementScene() {
        Label titleLabel = new Label("Message Management");
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(event -> primaryStage.setScene(mainMenuScene));

        VBox root = new VBox(20, titleLabel, backButton);
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        messageManagementScene = new Scene(root, 300, 200);
    }

    // Method to authenticate patient
    private boolean authenticatePatient(String username, String password) {
        // Implement patient authentication logic
        return true; // Dummy implementation, replace with actual logic
    }

    // Method to authenticate doctor
    private boolean authenticateDoctor(String username, String password) {
        // Implement doctor authentication logic
        return true; // Dummy implementation, replace with actual logic
    }

    // Method to authenticate nurse
    private boolean authenticateNurse(String username, String password) {
        // Implement nurse authentication logic
        return true; // Dummy implementation, replace with actual logic
    }

    // Create the common information display scene
    private Scene createInfoDisplayScene(String userType) {
        Label titleLabel = new Label(userType + " Information Display");

        // Adding additional buttons based on user type
        VBox root = new VBox(20);
        root.getChildren().add(titleLabel);

        if (userType.equals("Doctor Information")) {
            Button patientProfileButton = new Button("Patient Profile");
            Button prescriptionButton = new Button("Prescription");
            Button messageButton = new Button("Message");

            patientProfileButton.setOnAction(e -> primaryStage.setScene(patientProfileScene));
            prescriptionButton.setOnAction(e -> primaryStage.setScene(prescriptionScene));
            messageButton.setOnAction(e -> primaryStage.setScene(messageScene));

            root.getChildren().addAll(patientProfileButton, prescriptionButton, messageButton);
        } else if (userType.equals("Nurse Information")) {
					Button vitalSignsButton = new Button("Vital Signs");
					Button patientHistoryButton = new Button("Patient History");
			       Button messageManagementButton = new Button("Message Management");
					Button newPatientButton = new Button("New Patient");
 
        		   vitalSignsButton.setOnAction(e -> primaryStage.setScene(vitalSignsScene));
		            patientHistoryButton.setOnAction(e -> primaryStage.setScene(patientHistoryScene));
		            messageManagementButton.setOnAction(e -> primaryStage.setScene(messageManagementScene));
        		      newPatientButton.setOnAction(e -> primaryStage.setScene(newPatientScene));
        		
        				root.getChildren().addAll(vitalSignsButton, patientHistoryButton, messageManagementButton, newPatientButton);
        } 
        else if (userType.equals("Patient Information")) {
                   Button profileButton = new Button("My Profile ");
                   Button appointmentButton = new Button("Appointments");
                   Button messageButton = new Button("Messages");
 
    				profileButton.setOnAction(e -> primaryStage.setScene(patientProfileScene));
    				appointmentButton.setOnAction(e -> primaryStage.setScene(appointmentScene));
        			messageButton.setOnAction(e -> primaryStage.setScene(patientMessageScene));
  
        				root.getChildren().addAll(profileButton, appointmentButton, messageButton);
    	}
    
					Button backButton = new Button("Back to Main Menu");
					backButton.setOnAction(event -> primaryStage.setScene(mainMenuScene));
	
				   root.getChildren().add(backButton);
	
			       root.setPadding(new Insets(20));
			       root.setSpacing(10);
		
			        return new Scene(root, 500, 500);
        		   }
        	
        		    public static void main(String[] args) {
        		        launch(args);
        		   }
        		}
        		