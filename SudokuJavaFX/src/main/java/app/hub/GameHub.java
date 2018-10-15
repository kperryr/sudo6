package app.hub;

import java.io.IOException;

import netgame.common.Hub;
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
			HubSudoku = new Sudoku(9);
		
		if (message instanceof Action) {
			
			Action a = (Action)message;

			switch (a.geteAction()) {
			case Sit:
				HubPokerTable.AddPlayerToTable(a.getActPlayer());
				resetOutput();
				sendToAll(HubPokerTable);				
				break;
			case Leave:
				HubPokerTable.RemovePlayerFromTable(a.getActPlayer());
				resetOutput();
				sendToAll(HubPokerTable);
				break;
			case TableState:
				resetOutput();
				sendToAll(HubPokerTable);
			case GameState:
				//TODO: Implement this
				break;
			case StartGameBlackJack:
				//TODO: Implement this	
				eGameType = eGameType.BLACKJACK;

				break;
			case Draw:
				//TODO: Implement this
				break;
			}
				


		}
	}

}
