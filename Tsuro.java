import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.lang.IllegalArgumentException;
import javafx.scene.paint.Color;
import java.lang.NumberFormatException;
/** The main Tsuro class
* @author Luke Dischiave
*/
public class Tsuro extends Application {
private int numRows = 6; // number of
rows from the command line args (default = 6)
private int numColumns = 6; // number of
columns from the command line args (default = 6)
private int numPlayers = 2; // number of
players for the command line args (default = 2)
private int handSize = 3; // stores the
value of the handsize from command line args (default = 3)
private boolean isHighlighted = false; // stores if a
current player's button is already highlighted
private TsuroButton prevPressed = null; // stores the
address of the previous TsuroButton that was pressed
private TsuroButton prevPlacedP1 = null; // stores the
address of the last TsuroButton placed on the board by player 1
private TsuroButton prevPlacedP2 = null; // stores the
address of the last TsuroButton placed on the board by player 2
private boolean isP1Turn = true; // stores if
it's player one's turn
private boolean isP2Turn = false; // stores if
it's player two's turn
TsuroButton[] p1TButton = null; // stores p1's
hand as an array of TsuroButtons
TsuroButton[] p2TButton = null; // stores p2's
hand as an array of TsuroButtons
private boolean isFirstTurn = true; // stores if
it's the the first turn for either player
GridPane board = null; // the
gridpane for the board
private int p1StoneLocation = -1; // stores the
location of p1's stone after the first movement
private int p2StoneLocation = -1; // stores the
location of p2's stone after the first movement
/** returns the number of rows
*@return the number of rows
*/
public int getNumRows() {
return numRows;
}
/** getter method for number of columns
*@return the number of columns
*/
public int getNumColumns() {
return numColumns;
}
/** getter method for number of players
*@return the number of players
*/
public int getNumPlayers() {
return numPlayers;
}
/** getter method for the handsize
*@return the hand size
*/
public int getHandSize() {
return handSize;
}
/** getter method for the playing board
*@return the game board
*/
public GridPane getBoard() {
return this.board;
}
/** getter method for the location of player 1's stone
*@return the int associated with player 1's stone
*/
public int getP1StoneLocation() {
return p1StoneLocation;
}
/** getter method for the location of player 2's stone
*@return the int associated with player 2's stone
*/
public int getP2StoneLocation() {
return p2StoneLocation;
}
/** setter method for the location of player 2's stone
*@param location the integer location of the stone's new location
*/
private void setP2StoneLocation(int location) {
p2StoneLocation = location;
}
/** setter method for the location of player 2's stone
*@param location the integer location of the stone's new location
*/
private void setP1StoneLocation(int location) {
p1StoneLocation = location;
}
/** getter method for the previous button that was pressed
*@return the previous TsuroButton that was pressed
*/
public TsuroButton getPrevPressed() {
return prevPressed;
}
/** getter method for the previous tile that p1 placed on the board
*@return the previous tile that p1 placed on the board
*/
public TsuroButton getPrevPlacedP1() {
return prevPlacedP1;
}
/** getter method for the previous tile that p2 placed on the board
*@return the previous tile that p2 placed on the board
*/
public TsuroButton getPrevPlacedP2() {
return prevPlacedP2;
}
/** setter method for setting the previous tile that p2 placed
*@param prevPlacedP2 the previous tile that p2 placed
*/
private void setPrevPlacedP2(TsuroButton prevPlacedP2) {
this.prevPlacedP2 = prevPlacedP2;
}
/** setter method for setting the previous tile that p1 placed
*@param prevPlacedP1 the previous tile that p1 placed
*/
private void setPrevPlacedP1(TsuroButton prevPlacedP1) {
this.prevPlacedP1 = prevPlacedP1;
}
/** setter method for setting the previous pressed button
*@param previous the previous buttons pressed on a player's hand
*/
private void setPrevPressed(TsuroButton previous) {
prevPressed = previous;
}
/** setter method for setting the number of players
*@param numPlayers the number of players
*/
private void setNumPlayers(int numPlayers) {
this.numPlayers = numPlayers;
}
/** setter method for setting the handsize of each player
*@param handSize the amount of tiles each player gets
*/
private void setHandSize(int handSize) {
this.handSize = handSize;
}
/** setter method for setting the number of columns
*@param numColumns the number of columns of the board
*/
private void setNumColumns(int numColumns) {
this.numColumns = numColumns;
}
/** setter method for setting the number of rows
*@param numRows the number of rows of the board
*/
private void setNumRows(int numRows) {
this.numRows = numRows;
}
/** getter method for seeing if a button is highlighted
*@returns if a button is highlighted
*/
public boolean isHighlighted() {
return isHighlighted;
}
/** Sets up the GUI
* @param primaryStage the main window of the GUI
*/
public void start(Stage primaryStage) {
checkParameters();
setP1Hand();
setP2Hand();
setPlayingBoard(primaryStage);
}
/** a method that creates p1's hand and displays it */
private void setP1Hand() {
// array that holds p1's hand
p1TButton = new TsuroButton[getHandSize()];
// gridpane for player 1
GridPane player1Hand = new GridPane();
// initializing p1's hand
for (int index = 0; index < p1TButton.length; index++) {
p1TButton[index] = new TsuroButton(100, 100);
p1TButton[index].setConnections(p1TButton[index].makeRandomConnectionArray());
p1TButton[index].addStone(Color.CYAN, 6);
player1Hand.addRow(0, p1TButton[index]);
// setting p1's buttons to highlight on press
EventHandler<ActionEvent> highlight = new Highlight();
p1TButton[index].setOnAction(highlight);
}
Scene p1Scene = new Scene(player1Hand);
Stage p1Stage = new Stage();
p1Stage.setScene(p1Scene);
p1Stage.setTitle("Player 1");
p1Stage.show();
}
/** a method that creates p2's hand and displays it */
private void setP2Hand() {
// array that holds p2's hand
p2TButton = new TsuroButton[getHandSize()];
// gridpane for player 2
GridPane player2Hand = new GridPane();
// initializing p2's hand
for (int index = 0; index < p2TButton.length; index++) {
p2TButton[index] = new TsuroButton(100, 100);
p2TButton[index].setConnections(p2TButton[index].makeRandomConnectionArray());
p2TButton[index].addStone(Color.LIGHTGREEN, 2);
player2Hand.addRow(0, p2TButton[index]);
// setting p2's buttons to highlight on press
EventHandler<ActionEvent> highlight = new Highlight();
p2TButton[index].setOnAction(highlight);
p2TButton[index].setDisable(true);
}
Scene p2Scene = new Scene(player2Hand);
Stage p2Stage = new Stage();
p2Stage.setScene(p2Scene);
p2Stage.setTitle("Player 2");
p2Stage.show();
}
/** a method that creates the playing board
*@param primaryStage the main stage
*/
private void setPlayingBoard(Stage primaryStage) {
// the 2D array that holds all the tsuro buttons
TsuroButton[][] tArray = new TsuroButton[getNumRows()][getNumColumns()];
board = new GridPane();
// go through each element of tArray and initialize 2D array, and add each to
the board in order
for (int row = 0; row < tArray.length; row++) { //
this loop goes through each element in a row
for (int column = 0; column < tArray[row].length; column++) { //
this loop goes through each element in a column
tArray[row][column] = new TsuroButton(100, 100);
board.add(tArray[row][column], column, row);
EventHandler<ActionEvent> place = new Place();
tArray[row][column].setOnAction(place);
}
}
Scene playingBoard = new Scene(board);
primaryStage.setScene(playingBoard);
primaryStage.setTitle("Tsuro");
primaryStage.show();
}
/* a method that goes through command line args and sets the fields equal to each
argument appropriately.
* If no arguments are found, the program runs with default settings
*/
private void checkParameters() {
for (String argsString : getParameters().getRaw()) {
int count = 0; // the
current index we're evaulating from cmd args
try {
Integer argsInt = Integer.parseInt(argsString); // stores
the current integer argument we're looking at
// setting the length of the columns
if (count == 0) {
if (argsInt <= 1) {
throw new IllegalArgumentException();
}
setNumColumns(argsInt);
count++;
}
// setting the length of the rows
else if (count == 1) {
if (argsInt <= 1) {
throw new IllegalArgumentException();
}
setNumRows(argsInt);
count++;
}
// setting the handsize
else if (count == 2) {
if (argsInt < 1) {
throw new IllegalArgumentException();
}
setHandSize(argsInt);
count++;
}
// setting the amt of players
else if (count == 3) {
if (argsInt != 2) {
throw new IllegalArgumentException();
}
setNumPlayers(argsInt);
count++;
}
}
catch (Exception e) {
System.out.println("Invalid Argument(s)! Default values will be used.");
setNumPlayers(2);
setNumRows(6);
setNumColumns(6);
setHandSize(3);
}
}
}
/** Method to move p1's stone
* THIS METHOD DOES NOT WORK PROPERLY
*@param placedButton the button to which we are moving the stone to
*/
private void moveP1Stone(TsuroButton placedButton) {
getPrevPlacedP1().removeStone(getP1StoneLocation());
if (getP1StoneLocation() == 0)
setP1StoneLocation(placedButton.getConnections()[4]);
else if (getP1StoneLocation() == 1)
setP1StoneLocation(placedButton.getConnections()[5]);
else if (getP1StoneLocation() == 2)
setP1StoneLocation(placedButton.getConnections()[6]);
else if (getP1StoneLocation() == 3)
setP1StoneLocation(placedButton.getConnections()[7]);
else if (getP1StoneLocation() == 4)
setP1StoneLocation(placedButton.getConnections()[0]);
else if (getP1StoneLocation() == 5)
setP1StoneLocation(placedButton.getConnections()[1]);
else if (getP1StoneLocation() == 6)
setP1StoneLocation(placedButton.getConnections()[2]);
else if (getP1StoneLocation() == 7)
setP1StoneLocation(placedButton.getConnections()[3]);
placedButton.addStone(Color.CYAN, placedButton.getConnections()
[getP1StoneLocation()]);
}
/** Method to move p2's stone
*@param placedButton the button to which we are moving the stone to
*/
private void moveP2Stone(TsuroButton placedButton) {
getPrevPlacedP2().removeStone(getP2StoneLocation());
if (getP2StoneLocation() == 0)
setP2StoneLocation(placedButton.getConnections()[4]);
else if (getP2StoneLocation() == 1)
setP2StoneLocation(placedButton.getConnections()[5]);
else if (getP2StoneLocation() == 2)
setP2StoneLocation(placedButton.getConnections()[6]);
else if (getP2StoneLocation() == 3)
setP2StoneLocation(placedButton.getConnections()[7]);
else if (getP2StoneLocation() == 4)
setP2StoneLocation(placedButton.getConnections()[0]);
else if (getP2StoneLocation() == 5)
setP2StoneLocation(placedButton.getConnections()[1]);
else if (getP2StoneLocation() == 6)
setP2StoneLocation(placedButton.getConnections()[2]);
else if (getP2StoneLocation() == 7)
setP2StoneLocation(placedButton.getConnections()[3]);
placedButton.addStone(Color.CYAN, placedButton.getConnections()
[getP1StoneLocation()]);
}
public static void main(String... args) {
Application.launch(args);
}
/** A method that rotates the indices of a highlighted TsuroButton
* It DOES NOT WORK PROPERLY!
* @param button the TsuroButton that needs its connections to be rotated
* @return newConnections the new rotated indices
*/
private int[] rotateConnections(TsuroButton button) {
int[] newConnections = new int[8];
newConnections[0] = button.getConnections()[7];
newConnections[1] = button.getConnections()[6];
newConnections[2] = button.getConnections()[0];
newConnections[3] = button.getConnections()[1];
newConnections[4] = button.getConnections()[3];
newConnections[5] = button.getConnections()[2];
newConnections[6] = button.getConnections()[4];
newConnections[7] = button.getConnections()[5];
return newConnections;
}
/** A event handler that highlights the current button pressed */
private class Highlight implements EventHandler<ActionEvent> {
/** Override the handle method to indicate what to do if the event occurs
* @param event the event data
*/
public void handle(ActionEvent event) {
// a TsuroButton that stores the location of the button that was pressed
TsuroButton buttonPressed = (TsuroButton)event.getSource();
// if no buttons are highlighted in player's hand
if (!isHighlighted && getPrevPressed() == null) {
buttonPressed.setBackgroundColor(Color.YELLOW);
isHighlighted = true;
setPrevPressed(buttonPressed);
}
// if pressing a button already highlighted
else if (buttonPressed == getPrevPressed()) {
if (buttonPressed.getBackgroundColor() == Color.YELLOW) {
buttonPressed.setConnections(rotateConnections(buttonPressed));
}
else {
buttonPressed.setBackgroundColor(Color.YELLOW);
isHighlighted = true;
}
setPrevPressed(buttonPressed);
}
// if pressing a button that isn't highlighted while another is
else {
buttonPressed.setBackgroundColor(Color.YELLOW);
getPrevPressed().setBackgroundColor(Color.WHITE);
isHighlighted = true;
setPrevPressed(buttonPressed);
}
}
}
/** A event handler that places the current button highlighted onto the board */
private class Place implements EventHandler<ActionEvent> {
/** Override the handle method to indicate what to do if the event occurs
* @param event the event data
*/
public void handle(ActionEvent event) {
int[] p1Connections =
null; // an array of ints that
stores the connections of the button that was pressed while it's p1's turn
int[] p2Connections =
null; // an array of ints that
stores the connections of the button that was pressed while it's p2's turn
TsuroButton buttonPressed =
(TsuroButton)event.getSource(); // a TsuroButton that stores the
location of the button that was pressed
// this code runs when a button has been highlighted, and it's P1's turn
if (getPrevPressed() != null && isP1Turn == true) {
buttonPressed.setConnections(getPrevPressed().getConnections());
// this code runs if it's p1's first turn
if (isFirstTurn) {
p1StoneLocation = buttonPressed.getConnections()[6];
buttonPressed.addStone(Color.CYAN, p1StoneLocation);
prevPlacedP1 = buttonPressed;
}
// this code runs if it's not p1's first turn
else {
moveP1Stone(buttonPressed);
}
getPrevPressed().setBackgroundColor(Color.WHITE);
isP1Turn = false;
isP2Turn = true;
// loop that disables p1's hand and removes the stones from their buttons
for (TsuroButton button : p1TButton) {
p1Connections = button.getConnections();
button.removeStone(6);
button.setConnections(p1Connections);
button.setDisable(true);
}
// loop that enables p2's hand
for (TsuroButton button : p2TButton) {
button.setDisable(false);
}
prevPlacedP1 = buttonPressed;
}
// this code runs when a button has been highlighted and it's p2's turn
else if (getPrevPressed() != null && isP2Turn == true) {
buttonPressed.setConnections(getPrevPressed().getConnections());
getPrevPressed().setBackgroundColor(Color.WHITE);
isP1Turn = true;
isP2Turn = false;
if (isFirstTurn) {
p2StoneLocation = buttonPressed.getConnections()[2];
buttonPressed.addStone(Color.LIGHTGREEN, p2StoneLocation);
isFirstTurn = false;
prevPlacedP2 = buttonPressed;
}
// this code runs if it's not p2's first turn
else {
moveP2Stone(buttonPressed);
}
// loop that enables p1's hand
for (TsuroButton button : p1TButton) {
button.setDisable(false);
}
// loop that disables p2's hand and removes the stones from their buttons
for (TsuroButton button : p2TButton) {
p2Connections = button.getConnections();
button.removeStone(2);
button.setConnections(p2Connections);
button.setDisable(true);
}
prevPlacedP2 = buttonPressed;
}
}
}
}
