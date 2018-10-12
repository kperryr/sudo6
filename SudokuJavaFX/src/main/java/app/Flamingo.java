package app;

import java.io.IOException;

import app.controller.BlackJackController;
import app.controller.ClientServerStartController;
import app.controller.GameBorderController;
import app.hub.GameHub;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import netgame.common.Client;
import pkgCore.GamePlay;
import pkgCore.Player;
import pkgCore.Table;

public class Flamingo extends Application {
	
	private Stage primaryStage;	
	private GameHub gHub = null;
	private AnchorPane GameBorderPane = null;
	private AnchorPane BlackJackBorderPane = null;
	private GameBorderController GBC = null;
	private BlackJackController BJC;
	private GameClient gClient = null;
	private Player appPlayer;
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		showClientServer(primaryStage);

	}
	
	public Player getAppPlayer() {
		return appPlayer;
	}

	public void setAppPlayer(Player appPlayer) {
		this.appPlayer = appPlayer;
	}

	public void showPoker(boolean bStartHub, String strComputerName, int iPort, String strPlayerName) {




		
		HandleRoot();

		ShowBlackJack();		
		
/*		if (!bStartHub) {
		
			Action act = new Action();
			act.setAction(eAction.TableState);
			act.setPlayer(this.getPlayer());
			this.messageSend(act);
		}*/
	}
	
	
	
	public void showClientServer(Stage primaryStage) {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			
			loader =  new FXMLLoader(getClass().getResource("/game/app/view/ClientServerStart.fxml"));
			 
			BorderPane ClientServerOverview = (BorderPane) loader.load();

			Scene scene = new Scene(ClientServerOverview);

			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			ClientServerStartController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void HandleRoot() {
		//Parent root;
		try {
						
			FXMLLoader loader = new FXMLLoader();			
			loader =  new FXMLLoader(getClass().getResource("/game/app/view/GameBorder.fxml"));					
			GameBorderPane = (AnchorPane) loader.load();
			Scene scene = new Scene(GameBorderPane);
			primaryStage.setScene(scene);
			GBC = loader.getController();
			GBC.setMainApp(this);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void StartBlackJack(boolean bStartHub, String strComputerName, int iPort, String strPlayerName)
	{
		if (bStartHub) {
			try {
				gHub = new GameHub(iPort);
 
			} catch (Exception e) {
				System.out.println("Error: Can't listen on port " + iPort);
				return;
			}
		}
		try {
			gClient = new GameClient(strComputerName, iPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setAppPlayer(new Player(strPlayerName, gClient.getID()));
		
		ShowBlackJack();
		
	}
	
	public void ShowBlackJack()
	{		 
		try {
						
			FXMLLoader loader = new FXMLLoader();			
			loader =  new FXMLLoader(getClass().getResource("/game/app/view/BlackJack.fxml"));					
			BlackJackBorderPane = (AnchorPane) loader.load();
			Scene scene = new Scene(BlackJackBorderPane);
			primaryStage.setScene(scene);
			BJC = loader.getController();
			BJC.setMainApp(this);
		
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void messageSend(final Object message)
	{
		System.out.println("Sending message from MainApp");
		gClient.messageSend(message);	
	}
	
	
	
	private class GameClient extends Client {

		public GameClient(String hubHostName, int hubPort) throws IOException {
			super(hubHostName, hubPort);
		}

		/*
		 * messageSend - One single place to send messages
		 */
		protected void messageSend(Object message)
		{
			//System.out.println("Sending message from MainApp.Client");
			resetOutput();
			super.send(message);
		}
		
		/*
		 * messageReceived will get an Object message... it's up to you to determine
		 * what should happen to that the message.
		 * 
		 * If it's a Table, handle Table - level action
		 * If it's a GamePlay, handle GamePlay - level action
		 */
		@Override
		protected void messageReceived(final Object message) {
			Platform.runLater(() -> {		
				System.out.println("Message Received.  The message: " + message);
				
				if (message instanceof String)
				{				
					System.out.println("Message Received from hub " + message);
				}
				else if (message instanceof Table)
				{
					BJC.HandleTableState((Table)message); 
				}
				else if (message instanceof GamePlay)
				{
					
				}
				
			});
		}
		
		
		@Override
		/*
		 * serverShutdown - Call the hard exit.
		 */
	    protected void serverShutdown(String message) {
	    	
			Platform.runLater(() -> {		
				Platform.exit();
		        System.exit(0);
			});
	    }
	

	}

}
