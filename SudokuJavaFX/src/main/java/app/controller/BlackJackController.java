package app.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

import app.Flamingo;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import pkgCore.Action;
import pkgCore.GamePlay;
import pkgCore.Player;
import pkgCore.Table;
import pkgEnum.eAction;
import pkgInterfaces.iPlayer;

public class BlackJackController implements Initializable {
	private Flamingo FlamingoGame;

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	private HBox hBoxDeck;

	@FXML
	private HBox hboxP1Cards;
	@FXML
	private HBox hboxP2Cards;
	@FXML
	private HBox hboxCommunity;

	@FXML
	private Label lblNameP1;
	@FXML
	private Label lblNameP2;

	@FXML
	private ToggleButton btnPos1SitLeave;
	@FXML
	private ToggleButton btnPos2SitLeave;

	private int iAnimationLength = 250;

	private int iDrawCardP1 = 0;
	private int iDrawCardP2 = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setMainApp(Flamingo FlamingoGame) {
		this.FlamingoGame = FlamingoGame;
	}

	@FXML
	public void btnHit_Click(ActionEvent event) {
		System.out.println("Hit clicked");
		int iPosition = 0;
		int iDrawCard = 0;
		Button btnHit = (Button) event.getSource();
		switch (btnHit.getId()) {
		case "btnHitP1":
			iPosition = 1;
			iDrawCard = iDrawCardP1;
			break;
		case "btnHitP2":
			iPosition = 2;
			iDrawCard = iDrawCardP2;
			break;
		}

		SequentialTransition seqDealTable = new SequentialTransition();

		// Transitions work by moving an item from 'Point A' to 'Point B'.
		// pntDeck = Point A
		// pntCardDealt = Point B
		// the code below will figure out where Point A and Point B are on the scene

		Point2D pntCardDealt = null;

		pntCardDealt = FindPoint(getCardHBox(iPosition), iDrawCard);

		Point2D pntDeck = FindPoint(hBoxDeck, 0);

		// Create a brand animation new image, drop it on the main screen. The new image
		// will be:
		// * created
		// * transitioned
		// * removed

		// Create the animation image and put it on the main scene:
		final ImageView img = BuildImage(0);
		img.setX(pntDeck.getX());
		img.setY(pntDeck.getY() - 33);
		ImageView imgDealCard = BuildImage(11);
		mainAnchor.getChildren().add(img);

		// Create the Translation transition (we're using a Path, but this is how you do
		// a translate):
		TranslateTransition transT = CreateTranslateTransition(pntDeck, pntCardDealt, img);

		// Create a Rotate transition
		RotateTransition rotT = CreateRotateTransition(img);

		// Create a Scale transition (we're not using it, but this is how you do it)
		ScaleTransition scaleT = CreateScaleTransition(img);

		// Create a Path transition
		PathTransition pathT = CreatePathTransition(pntDeck, pntCardDealt, img);

		// Create a new Parallel transition.
		ParallelTransition patTMoveRot = new ParallelTransition();

		// Add transitions you want to execute currently to the parallel transition
		patTMoveRot.getChildren().addAll(rotT, pathT);
		// patTMoveRot.getChildren().addAll(pathT, rotT);

		// Create a new Parallel transition to fade in/fade out
		ParallelTransition patTFadeInFadeOut = createFadeTransition(
				(ImageView) getCardHBox(iPosition).getChildren().get(iDrawCard), imgDealCard.getImage());

		// Create a new sequential transition
		SequentialTransition seqDeal = new SequentialTransition();

		// Add the two parallel transitions to the sequential transition
		seqDeal.getChildren().addAll(patTMoveRot, patTFadeInFadeOut);

		// Set up event handler to remove the animation image after the transition is
		// complete
		seqDeal.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				mainAnchor.getChildren().remove(img);
			}
		});

		// Add the sequential transistion to the main sequential transision and play
		seqDealTable.getChildren().add(seqDeal);

		seqDealTable.setInterpolator(Interpolator.EASE_OUT);
		seqDealTable.play();
		iDrawCard++;

		switch (btnHit.getId()) {
		case "btnHitP1":
			iDrawCardP1++;
			break;
		case "btnHitP2":
			iDrawCardP2++;
			break;
		}

	}

	@FXML
	public void btnStand_Click(ActionEvent event) {
		System.out.println("Stand clicked");
	}

	/*
	 * @FXML public void btnStart_Click(ActionEvent event) { Button btn =
	 * (Button)event.getSource();
	 * 
	 * System.out.println(btn.getText());
	 * 
	 * System.out.println("Start clicked"); }
	 */

	@FXML
	void btnStart_Click(ActionEvent event) {
		

		hboxP1Cards.getChildren().clear();
		hboxP2Cards.getChildren().clear();

		hBoxDeck.getChildren().clear();
		hBoxDeck.getChildren().add(BuildImage(0));

		for (int a = 0; a < 5; a++) {
			for (int b = 1; b < 3; b++) {
				getCardHBox(b).getChildren().add(BuildImage(-2));
			}
		}

	}

	public void btnSitLeave_Click(ActionEvent event) {

		ToggleButton btn = (ToggleButton) event.getSource();
		System.out.println("Button was clicked");

		Action a = new Action();
		Player p = this.FlamingoGame.getAppPlayer();

		if (btn.getText().equals("Sit")) {
			a.seteAction(eAction.Sit);
		} else if (btn.getText().equals("Leave")) {
			a.seteAction(eAction.Leave);
		}

		switch (btn.getId()) {
		case "btnPos1SitLeave":
			p.setiPlayerPosition(1);
			break;

		case "btnPos2SitLeave":
			p.setiPlayerPosition(2);
			break;
		}
		FlamingoGame.setAppPlayer(p);

		a.setActPlayer(p);

		this.FlamingoGame.messageSend(a);

	}

	private HBox getCardHBox(int iPosition) {
		switch (iPosition) {
		case 0:
			return hboxCommunity;
		case 1:
			return hboxP1Cards;
		case 2:
			return hboxP2Cards;

		default:
			return null;
		}

	}

	private ImageView BuildImage(int iCardNbr) {
		String strImgPath = null;

		int iWidth = 72;
		int iHeight = 96;
		switch (iCardNbr) {
		case -1:
			strImgPath = "/img/b1fh.png";
			break;
		case -2:
			strImgPath = "/img/blank.png";
			break;
		case 0:
			strImgPath = "/img/b1fv.png";
			break;
		default:
			strImgPath = "/img/" + iCardNbr + ".png";
		}

		ImageView i1 = new ImageView(
				new Image(getClass().getResourceAsStream(strImgPath), iWidth, iHeight, true, true));
		return i1;
	}

	public void HandleTableState(Table t) {

		btnPos1SitLeave.setText("Sit");
		btnPos1SitLeave.setVisible(true);
		btnPos2SitLeave.setText("Sit");
		btnPos2SitLeave.setVisible(true);
		lblNameP1.setText("");
		lblNameP2.setText("");

		Platform.runLater(() -> {

			boolean bCurrentPlayerSeated = false;

			for (iPlayer TablePlayer : t.GetTablePlayers(FlamingoGame.getAppPlayer().getPlayerID())) {
				if (TablePlayer.isME()) {
					bCurrentPlayerSeated = true;
				}
				else
				{
					GetPlayerSitLeaveButton(TablePlayer.getiPlayerPosition()).setVisible(false);
				}
				GetPlayerLabel(TablePlayer.getiPlayerPosition()).setText(TablePlayer.getPlayerName());
			}

			if (bCurrentPlayerSeated) {
				GetPlayerSitLeaveButton(FlamingoGame.getAppPlayer().getiPlayerPosition()).setText("Leave");
				for (ToggleButton tb : AllOtherSitLeaveButtons(FlamingoGame.getAppPlayer().getiPlayerPosition())) {
					tb.setVisible(false);
				}
			}
		});
	}

	private ArrayList<ToggleButton> AllOtherSitLeaveButtons(int iPlayerPosition) {

		HashMap<Integer, ToggleButton> hmSitLeaveButtons = new HashMap<Integer, ToggleButton>();

		hmSitLeaveButtons.put(1, btnPos1SitLeave);
		hmSitLeaveButtons.put(2, btnPos2SitLeave);
		hmSitLeaveButtons.remove(iPlayerPosition);

		return new ArrayList<ToggleButton>(hmSitLeaveButtons.values());
	}

	private ToggleButton GetPlayerSitLeaveButton(int iPlayerPosition) {
		switch (iPlayerPosition) {
		case 1:
			return btnPos1SitLeave;
		case 2:
			return btnPos2SitLeave;
		}
		return null;
	}

	private Label GetPlayerLabel(int iPlayerPosition) {
		switch (iPlayerPosition) {
		case 1:
			return lblNameP1;
		case 2:
			return lblNameP2;
		}
		return null;
	}

	public void HandleGameState(GamePlay gp) {

		// Coming Soon....!
	}

	private Point2D FindPoint(HBox hBoxCard, int iCardNbr) {

		ImageView imgvCardFaceDown = (ImageView) hBoxCard.getChildren().get(iCardNbr);
		Bounds bndCardDealt = imgvCardFaceDown.localToScene(imgvCardFaceDown.getBoundsInLocal());
		Point2D pntCardDealt = new Point2D(bndCardDealt.getMinX() + iCardNbr, bndCardDealt.getMinY());

		return pntCardDealt;

	}

	private PathTransition CreatePathTransition(Point2D fromPoint, Point2D toPoint, ImageView img) {
		Path path = new Path();

		// TODO: Fix the Path transition. My Path looks terrible... do something cool :)

		path.getElements().add(new MoveTo(fromPoint.getX(), fromPoint.getY()));
		path.getElements().add(new CubicCurveTo(toPoint.getX() * 2, toPoint.getY() * 2, toPoint.getX() / 3,
				toPoint.getY() / 3, toPoint.getX(), toPoint.getY()));
		// path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(750));
		pathTransition.setPath(path);
		pathTransition.setNode(img);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount((int) 1f);
		pathTransition.setAutoReverse(false);

		return pathTransition;

	}

	private ScaleTransition CreateScaleTransition(ImageView img) {
		ScaleTransition st = new ScaleTransition(Duration.millis(iAnimationLength), img);
		st.setByX(.25f);
		st.setByY(.25f);
		st.setCycleCount((int) 1f);
		st.setAutoReverse(true);

		return st;
	}

	private RotateTransition CreateRotateTransition(ImageView img) {

		RotateTransition rotateTransition = new RotateTransition(Duration.millis(iAnimationLength / 2), img);
		rotateTransition.setByAngle(180F);
		rotateTransition.setCycleCount(2);
		rotateTransition.setAutoReverse(false);

		return rotateTransition;
	}

	private TranslateTransition CreateTranslateTransition(Point2D fromPoint, Point2D toPoint, ImageView img) {

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(iAnimationLength), img);

		translateTransition.setFromX(0);
		translateTransition.setToX(toPoint.getX() - fromPoint.getX());
		translateTransition.setFromY(0);
		translateTransition.setToY(toPoint.getY() - fromPoint.getY());
		translateTransition.setCycleCount(1);
		translateTransition.setAutoReverse(false);

		return translateTransition;
	}

	private ParallelTransition createFadeTransition(final ImageView imgVFadeOut, final Image imgFadeIn) {

		FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(iAnimationLength), imgVFadeOut);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);
		fadeOutTransition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				imgVFadeOut.setImage(imgFadeIn);
			}
		});

		FadeTransition fadeInTransition = new FadeTransition(Duration.millis(iAnimationLength), imgVFadeOut);
		fadeInTransition.setFromValue(0.0);
		fadeInTransition.setToValue(1.0);
		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeOutTransition, fadeInTransition);
		return parallelTransition;
	}
}
