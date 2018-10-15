package app.hub;

import java.io.IOException;

import netgame.common.Hub;
import pkgGame.Action;
import pkgGame.Sudoku;

public class GameHub extends Hub {

	private Sudoku HubSudoku = null;

	public GameHub(int port) throws IOException {
		super(port);
		this.setAutoreset(true);
	}

	@Override
	protected void messageReceived(int playerID, Object message) {

		System.out.println("Action received from the hub");

		if (HubSudoku == null)
			try {
				HubSudoku = new Sudoku(9);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		sendToAll(HubSudoku);

		if (message instanceof Action) {

			Action a = (Action) message;

			switch (a.getAction()) {
			case IsValidValue:
				resetOutput();
				sendToAll(HubSudoku);	
				break;			
			case SetValue: 
				resetOutput();
				sendToAll(HubSudoku);	
				break;
			case ShowValueValues: 
				resetOutput();
				sendToAll(HubSudoku);					
				break;
			}
		}
	}
}
