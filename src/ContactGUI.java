
/**
 *
 * @author Kamyar
 */

import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Application;
import static javafx. application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Scanner;

import java.nio.file.Path;
import java.io.BufferedWriter;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.io.PrintWriter;

public class ContactGUI extends Application {
    Button clearBtn = null;
    Button updateBtn = null;//put update button variable outsdie of class 
    Button addBtn = null;//put add button variable outsdie of class 
    Button deleteBtn = null;
    TextField nameField = null;
    TextField cellphoneField = null;
    TextField emailField = null;
    Label cvLbl = null;
    
    Button firstBtn = null;//JM
    Button prevBtn = null;
    Button nextBtn = null;
    Button lastBtn = null;
    
    private File file;
    private int currentPointer = 0;
    private ArrayList<Contact> contactList = new ArrayList<Contact>();//3.5 name new arraylist contactList

    
    Stage mainStage = null;//refer to 3.6 (stage)
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        BorderPane root = new BorderPane();//create the main panel,
        Scene scene = new Scene(root, 830, 230);//setup screen/wondows length,
        
// components of left box         
        VBox leftVbox = new VBox(10);      
        Button LoadBtn = new Button("Load");
        LoadBtn.prefWidth(Double.MAX_VALUE);
        LoadBtn.setMaxWidth(Double.MAX_VALUE);
        LoadBtn.setOnAction(event -> loadHandler());   
        Button SaveBtn = new Button("Save");
        SaveBtn.prefWidth(Double.MAX_VALUE);
        SaveBtn.setMaxWidth(Double.MAX_VALUE);
        SaveBtn.setOnAction(event -> saveHandler());            
        Button exitBtn = new Button("Exit");
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn.setOnAction(event -> exitHandler());  //3.4            
        leftVbox.getChildren().addAll(new Label("Databse management"), LoadBtn, SaveBtn, exitBtn);//arrange left zone label and button and shows on Screen       
        root.setLeft(leftVbox);// to show on panel
        
// components of center box        
         VBox centerBox = new VBox(10);
	 GridPane gridPane = new GridPane();//to start grid pane
	 Label ctLbl = new Label("Contact");//contact label
	  cvLbl = new Label("0 of 0");//0 of 0 label
	 Label nameLabel = new Label("Name");
	 Label cellphoneLabel = new Label("Cell Phone");
	 Label emailLabel = new Label("Email");
	 nameField = new TextField();//blank        
         nameField.textProperty().addListener((obs, oldText, newText) -> textfieldHandler());
         cellphoneField = new TextField();//blank
         cellphoneField.textProperty().addListener((obs, oldText, newText) -> textfieldHandler());    
	 emailField = new TextField();//blank
         emailField.textProperty().addListener((obs, oldText, newText) -> textfieldHandler());       
         gridPane.add(ctLbl, 0, 0);//set up control label
         gridPane.add(nameLabel, 0, 1);//set up name label
         gridPane.add(cellphoneLabel, 0, 2);
         gridPane.add(emailLabel, 0, 3); 
         
         gridPane.add(cvLbl, 1, 0);//set up cv label
         gridPane.add(nameField, 1, 1);//set up name field
         gridPane.add(cellphoneField, 1,2);//set up cellphone field
         gridPane.add(emailField, 1, 3);//set up email field
                  
	 HBox btnBox = new HBox(10);
	  firstBtn = new Button("First");
        firstBtn.prefWidth(Double.MAX_VALUE);
        firstBtn.setMaxWidth(Double.MAX_VALUE);
        firstBtn.setOnAction(event -> First());     
	prevBtn = new Button("Prev");
        prevBtn.prefWidth(Double.MAX_VALUE);
        prevBtn.setMaxWidth(Double.MAX_VALUE);
        prevBtn.setOnAction(event -> Prev());   
        prevBtn.setDisable(true);/////////disable=true
        
	nextBtn = new Button("Next");
        nextBtn.prefWidth(Double.MAX_VALUE);
        nextBtn.setMaxWidth(Double.MAX_VALUE);
        nextBtn.setOnAction(event -> Next());   
	  
        lastBtn = new Button("Last");
        lastBtn.prefWidth(Double.MAX_VALUE);
        lastBtn.setMaxWidth(Double.MAX_VALUE);
        lastBtn.setOnAction(event -> Last());  
	
        Label nmLbl = new Label("Navigation management");
         btnBox.getChildren().addAll(firstBtn, prevBtn, nextBtn, lastBtn);
         gridPane.add(btnBox, 1, 4);
         gridPane.add(nmLbl,1, 5 );
         nextBtn.setDisable(true);

// components of right box
         VBox rightBox = new VBox(10);        
	 Label cmLbl = new Label("Contact management");
	 Button clearBtn = new Button("Clear");
        clearBtn.prefWidth(Double.MAX_VALUE);
        clearBtn.setMaxWidth(Double.MAX_VALUE);
        clearBtn.setOnAction(event -> clearHandler());
	updateBtn = new Button("Update"); 
        updateBtn.prefWidth(Double.MAX_VALUE);
        updateBtn.setMaxWidth(Double.MAX_VALUE);
        updateBtn.setOnAction(event -> updateHandler()); 
        updateBtn.setDisable(true);
        addBtn = new Button("Add");
        addBtn.prefWidth(Double.MAX_VALUE);
        addBtn.setMaxWidth(Double.MAX_VALUE);
        addBtn.setOnAction(event -> addHandler());  
        addBtn.setDisable(true);
        
        
	Button deleteBtn = new Button("Delete");       
        deleteBtn.prefWidth(Double.MAX_VALUE);
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        deleteBtn.setOnAction(event -> deleteHandler());  
        VBox rightVbpx =  new VBox(5);//, clearBtn, updateBtn, addBtn, deleteBtn);
        rightVbpx.getChildren().addAll(new Label("Contact management"), clearBtn, updateBtn, addBtn, deleteBtn);     
        root.setRight(rightVbpx);
        root.setCenter(gridPane);
        primaryStage.setTitle("Contact List Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
         
        
    }
    
    public void loadHandler() //throws FileNotFoundException
    {
        try {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(mainStage);
            if (file != null) {
                Scanner scan = new Scanner(file);
                contactList.clear();
                currentPointer = 0;
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    System.out.println(line);
                    String[] token = line.split("\\s*,\\s*");
                    String name = token[0];
                    String phone = token[1];
                    String email = token[2];
                    
                    this.nameField.setText(name);//to show name contact when load
                    this.cellphoneField.setText(phone);//to show phone contact when load
                    this.emailField.setText(email);//to show email contact when load
                    Contact contact = new Contact(name, phone, email);//use Contact class assign new object
                    contactList.add(contact);//add Contact class
                    ++currentPointer;//contactList add, so currentPointer add
                    
                    cvLbl.setText("? of " + currentPointer);
                    System.out.println(token[0]);
                    System.out.println(token[1]);
                    System.out.println(token[2]);
                }
                currentPointer = contactList.size() -1;
                scan.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        for( Contact c : contactList) {
            System.out.println(c);
        }
    }
    
    private void saveHandler() {
        PrintWriter pw = null;
        try {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(mainStage);
            if (file != null) {
                 pw = new PrintWriter(file);
                for( Contact c : contactList) {//FOREACH
                    System.out.println(c);
                    pw.println(c.getName() + "," + c.getCellphone() + "," + c.getEmail());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            if(pw != null)
                pw.close();
        }
    }
        
    private void exitHandler() {
        System.out.println("Shutting down application!");
        System.exit(0);
    }
    
    private void clearHandler() {
        
        System.out.println("Clearing field data");
        nameField.clear();
        cellphoneField.clear();
        emailField.clear();
    }
    
    private void updateHandler() {
        System.out.println("Updating contact");
        Contact contact = contactList.get(currentPointer);
        contact.setName(nameField.getText());
        contact.setCellphone(cellphoneField.getText());
        contact.setEmail(emailField.getText());
    }
    
    private void addHandler() {
        System.out.println("Adding contact");
        Contact contact = new Contact(nameField.getText(),cellphoneField.getText(),emailField.getText());
        contactList.add(contact);
    }
    
    private void deleteHandler() {
        System.out.println("Deleting contact");
        contactList.remove(currentPointer);
        --currentPointer;
    }
    

    private void First() {
        System.out.println("First");
        currentPointer = 0;
        Contact contact =  contactList.get(currentPointer);
        nameField.setText(contact.getName());
        cellphoneField.setText(contact.getCellphone());
        emailField.setText(contact.getEmail());
    }
    
    private void Prev() {
        System.out.println("Prev");
        if (currentPointer > 0) {
            currentPointer--;
            Contact contact =  contactList.get(currentPointer);
            nameField.setText(contact.getName());
            cellphoneField.setText(contact.getCellphone());
            emailField.setText(contact.getEmail());
            System.out.println("currentPointer="+currentPointer);
        }
    }
    
    private void Next(){
        System.out.println("Next");

        if ( currentPointer < contactList.size()-1 ) {
            currentPointer++;
                    Contact contact =  contactList.get(currentPointer);

            nameField.setText(contact.getName());
            cellphoneField.setText(contact.getCellphone());
            emailField.setText(contact.getEmail());
            System.out.println("currentPointer="+currentPointer);
        }
    }
    
    private void Last() {
        System.out.println("Last");
        currentPointer = contactList.size() - 1;
        Contact contact =  contactList.get(currentPointer);
            nameField.setText(contact.getName());
            cellphoneField.setText(contact.getCellphone());
            emailField.setText(contact.getEmail());
        }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void textfieldHandler() {
                 
        updateBtn.setDisable(false);
        addBtn.setDisable(false);
        prevBtn.setDisable(false);
        nextBtn.setDisable(false);
    }

}
