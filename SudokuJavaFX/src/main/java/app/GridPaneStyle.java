package app;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
 
public class GridPaneStyle extends Application {
  @Override public void start(final Stage stage) {
    // create a grid with some sample data.
    GridPane grid = new GridPane();
    grid.addRow(0, new Label("1"), new Label("2"), new Label("3"));
    grid.addRow(1, new Label("A"), new Label("B"), new Label("C"));
    
    // make all of the Controls and Panes inside the grid fill their grid cell, 
    // align them in the center and give them a filled background.
    // you could also place each of them in their own centered StackPane with 
    // a styled background to achieve the same effect.
    for (Node n: grid.getChildren()) {
      if (n instanceof Control) {
        Control control = (Control) n;
        control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        control.setStyle("-fx-background-color: cornsilk; -fx-alignment: center;");
      }
      if (n instanceof Pane) {
        Pane pane = (Pane) n;
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.setStyle("-fx-background-color: cornsilk; -fx-alignment: center;");
      }
    }

    // style the grid so that it has a background and gaps around the grid and between the 
    // grid cells so that the background will show through as grid lines.
    grid.setStyle("-fx-background-color: palegreen; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");
    // turn layout pixel snapping off on the grid so that grid lines will be an even width.
    grid.setSnapToPixel(false);

    // set some constraints so that the grid will fill the available area.
    ColumnConstraints oneThird = new ColumnConstraints();
    oneThird.setPercentWidth(100/3.0);
    oneThird.setHalignment(HPos.CENTER);
    grid.getColumnConstraints().addAll(oneThird, oneThird, oneThird);
    RowConstraints oneHalf = new RowConstraints();
    oneHalf.setPercentHeight(100/2.0);
    oneHalf.setValignment(VPos.CENTER);
    grid.getRowConstraints().addAll(oneHalf, oneHalf);
    
    // layout the scene in a stackpane with some padding so that the grid is centered 
    // and it is easy to see the outer grid lines.
    StackPane layout = new StackPane();
    layout.setStyle("-fx-background-color: whitesmoke; -fx-padding: 10;");
    layout.getChildren().addAll(grid);
    stage.setScene(new Scene(layout, 600, 400));
    stage.show();
    
    // can be uncommented to show the grid lines for debugging purposes, but not particularly useful for styling purposes.
    //grid.setGridLinesVisible(true);
  }
  public static void main(String[] args) { launch(); }
}