package app;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created on: 23.06.2012
 * @author Sebastian Damm
 */
public class GridPaneExample extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(40, 0, 0, 50));
        gridPane.setHgap(5); gridPane.setVgap(5);
        
        Scene scene = new Scene(gridPane, 300, 150);
        
        Label lbUser = new Label("Username:");
        GridPane.setHalignment(lbUser, HPos.RIGHT);
        TextField tfUser = new TextField();
        
        Label lbPass = new Label("Password:");
        GridPane.setHalignment(lbPass, HPos.RIGHT);
        PasswordField tfPass = new PasswordField();
        
        Button btLogin = new Button("Login");
        GridPane.setMargin(btLogin, new Insets(75, 0, 0, 0));

        gridPane.add(lbUser, 0, 0);
        gridPane.add(tfUser, 1, 0);
        gridPane.add(lbPass, 0, 1);
        gridPane.add(tfPass, 1, 1);
        gridPane.add(btLogin, 1, 2);
        
        primaryStage.setTitle("GridPaneExample 1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args)
    {   Application.launch(args);   }
}