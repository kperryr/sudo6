package pkgGame;

public class Player {

	private String PlayerName;
	private int iPlayerID;

	public Player(String playerName, int iPlayerID) {
		super();
		PlayerName = playerName;
		this.iPlayerID = iPlayerID;
	}

	public String getPlayerName() {
		return PlayerName;
	}

	public void setPlayerName(String playerName) {
		PlayerName = playerName;
	}

	public int getiPlayerID() {
		return iPlayerID;
	}

	public void setiPlayerID(int iPlayerID) {
		this.iPlayerID = iPlayerID;
	}

}
