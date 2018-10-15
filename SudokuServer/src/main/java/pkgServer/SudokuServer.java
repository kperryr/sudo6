package pkgServer;

import java.io.IOException;

import app.hub.GameHub;

public class SudokuServer {

	private static GameHub gHub = null;
	private static int iPort = 9000;
	
	public static void main(String[] args) {
		
		try {
			gHub = new GameHub(iPort);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
