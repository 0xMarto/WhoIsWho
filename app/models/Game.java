package models;

import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Mart0
 * Date: 4/24/12
 */
public class Game {
    private String gameId;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private TurnState currentState;
    private boolean start;
    private int leavers;

    public Game() {
        gameId = UUID.randomUUID().toString();
    }

    void startGame() {
        start = true;
        leavers = 0;
        setRandomTurn();
        notifyStart();
        generateDefaultStrategies();
        notifyTurn();
    }

    private void setRandomTurn() {
        Random turnRoller = new Random();
        int roll = turnRoller.nextInt(2) + 1;
        currentPlayer = roll == 1 ? playerOne : playerTwo;
        currentState = TurnState.ASKING;
    }

    private void generateDefaultStrategies() {
//        todo Implement player card picking...
    }

    public void setPlayerA(Player playerOne) {
        this.playerOne = playerOne;
        message(playerOne, "wait", "Waiting for other player to join.....");
    }

    private void notifyStart() {
        message(getCurrentPlayer(), "start", "Let's play WHO IS WHO, you are playing against " + getAlternative().getUsername());
        message(getAlternative(), "start", "Let's play WHO IS WHO, you are playing against " + getCurrentPlayer().getUsername());
    }

    private void notifyTurn() {
        if (currentState == TurnState.ASKING) {
            message(getCurrentPlayer(), "ask", "It's your turn, Ask a question.");
            message(getAlternative(), "wait", "Other player's turn!");
        } else {
            message(getAlternative(), "answer", "Answer the question.");
        }
    }

    private void AskCalculation(Player player, String questionAbout, String questionValue, String questionString) {
        if (questionAbout.equals("") || questionValue.equals("") || questionString.equals("")) {
            message(player, "mistake", "Please, Choose valid question and then Press ASK button");
        } else {
            message(getCurrentPlayer(), "my-ask", questionString);
            message(getAlternative(), "op-ask", questionString);
            changeTurn();
        }
    }

    private void answerCalculation(Player player, String answer) {
        if (answer.equals("")) {
            message(player, "mistake", "Please, Choose a valid answer");
        } else {
            message(getCurrentPlayer(), "op-answer", answer);
            message(getAlternative(), "my-answer", answer);
            changeTurn();
        }
    }

    public void leave(Player player) {
        leavers++;
        if (playerOne != null && playerTwo != null) {
            Player notQuitter = isCurrent(player) ? getAlternative() : getCurrentPlayer();
            message(notQuitter, "leave", "Other played left the game!");
        }
    }

    public void chat(Player player, String talk) {
        if (start) {
            chatMessage(getCurrentPlayer(), "chat", player.getUsername(), talk);
            chatMessage(getAlternative(), "chat", player.getUsername(), talk);
        } else {
            message(player, "wait", "Still Waiting for oponent....");
        }
    }

    public void answer(Player player, String answer) {
        if (getAlternative() == player) {
            answerCalculation(player, answer);
            notifyTurn();
        } else {
            message(player, "wait", "Not your move!");
        }
    }


    public void ask(Player player, String questionAbout, String questionValue, String questionString) {
        if (start) {
            if (getCurrentPlayer() == player) {
                AskCalculation(player, questionAbout, questionValue, questionString);
                notifyTurn();
            } else {
                message(player, "wait", "Not your move!");
            }
        } else {
            message(player, "wait", "Still Waiting for oponent....");
        }
    }

    private void chatMessage(Player playerTo, String type, String playerFrom, String talk) {
        final ObjectNode json = Json.newObject();
        json.put("name", playerFrom);
        json.put("type", type);
        json.put("message", talk);
        playerTo.getChannel().write(json);
    }

    public static void message(Player player, String type, String message) {
        final ObjectNode json = Json.newObject();
        json.put("type", type);
        json.put("message", message);
        player.getChannel().write(json);
    }

    private boolean isCurrent(Player player) {
        return player == getCurrentPlayer();
    }

    private void changeTurn() {
        if (currentState == TurnState.ASKING) {
            currentState = TurnState.ANSWERING;
        } else {
            currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
            currentState = TurnState.ASKING;
        }
    }

    public boolean isPlayerOneDefined() {
        return playerOne != null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getAlternative() {
        return currentPlayer == playerOne ? playerTwo : playerOne;
    }

    public boolean isPlayerTwoDefined() {
        return playerTwo != null;
    }

    public void setPlayerB(Player playerB) {
        this.playerTwo = playerB;
    }

    public String getGameId() {
        return gameId;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isEmpty() {
        boolean result = false;
        if (leavers == 2) result = true;
        if (leavers == 1 && start == false) result = true;
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                ", start=" + start +
                ", leavers=" + leavers +
                '}';
    }

    private enum TurnState {ASKING, ANSWERING}
}
