package models;

import com.google.gson.Gson;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * User: Jose Gonzalez
 * Date: 28/10/13
 * Time: 17:50
 */

public abstract class GameWhoIsWho {
    protected String gameId;
    protected Player playerOne;
    protected Player playerTwo;
    protected Player currentPlayer;
    protected TurnState currentState;
    protected boolean start;
    protected boolean end;
    protected int leavers;
    Map cardsMap;
    CurrentQuestion currentQuestion;

    public GameWhoIsWho() {
        gameId = UUID.randomUUID().toString();
    }

    void startGame() {
        start = true;
        end = false;
        leavers = 0;
        setRandomTurn();
        notifyStart();
        setPlayersCards();
        notifyTurn();
    }

    private void setRandomTurn() {
        Random turnRoller = new Random();
        int roll = turnRoller.nextInt(2) + 1;
        currentPlayer = roll == 1 ? playerOne : playerTwo;
        currentState = TurnState.ASKING;
    }

    private void setPlayersCards() {
        loadCardsMap();

        playerOne.setCard(pickRandomCard());
        message(playerOne, "yourCard", "Your card is:" + playerOne.getCard().getNamee());

        playerTwo.setCard(pickRandomCard());
        message(playerTwo, "yourCard", "Your card is:" + playerTwo.getCard().getNamee());
    }

    private void loadCardsMap() {
    }

    public void setPlayerA(Player playerOne) {
        this.playerOne = playerOne;
        message(playerOne, "wait", "Waiting for other player to join.....");
    }

    private void notifyStart() {
        message(getCurrentPlayer(), "start", "Let's play WHO IS WHO, You're playing against " + getAlternative().getUsername());
        message(getAlternative(), "start", "Let's play WHO IS WHO, You're playing against " + getCurrentPlayer().getUsername());
    }

    private Card pickRandomCard() {
        return null;
    }

    public Card getCardFromDb(String id) {
        return getCardFromDB("http://dpoi2012api.appspot.com/api/1.0/view?credential=w&id=", id);
    }

    public Card getCardFromDB(String id, String urlCardInfo){
        String requestUrl = urlCardInfo + id;
        try {
            URL url = new URL(requestUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine, result = "";
            while ((inputLine = in.readLine()) != null) {
                result = result.concat(inputLine);
            }
            in.close();

            result = result.split("payload")[1];
            result = result.substring(2, result.length() - 1);

            Card card = new Gson().fromJson(result, Card.class);
            return card;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("GameClassic: " + this.gameId + " - ERROR: Card id no found");
        return null;
    }

    private void notifyTurn() {
        if (currentState == TurnState.ASKING) {
            message(getCurrentPlayer(), "ask", "It's your turn, Ask a Question");
            message(getAlternative(), "wait", "Other player's turn!");
        } else {
            message(getAlternative(), "answer", "Answer the Question. (Remember your card is " + getAlternative().getCard().getNamee() + ")");
            message(getCurrentPlayer(), "wait", "Wait for " + getAlternative().getUsername() + " answer");
        }
    }

    private void askCalculation(Player player, String questionAbout, String questionValue, String questionString) {
        if (questionAbout.equals("") || questionValue.equals("") || questionString.equals("")) {
            message(player, "mistake", "Please, Choose valid Question and then Press ASK button");
        } else {
            message(getCurrentPlayer(), "my-ask", questionString);
            message(getAlternative(), "op-ask", questionString);
            changeTurn();
            notifyTurn();
        }
    }

    private void answerCalculation(Player player, String answer) {
        if (answer.equals("")) {
            message(player, "mistake", "Please, Choose a valid answer");
        } else {
            message(getCurrentPlayer(), "op-answer", answer);
            message(getAlternative(), "my-answer", answer);
            if (!answer.equalsIgnoreCase(currentQuestion.getRightAnswer())) {
                getAlternative().lied();
                message(getAlternative(), "lie", getAlternative().getLies() + "");
            }
            changeTurn();
            notifyTurn();
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
            message(player, "wait", "Still Waiting for opponent....");
        }
    }

    public void answer(Player player, String answer) {
        if (getAlternative() == player) {
            answerCalculation(player, answer);
        } else {
            message(player, "wait", "Not your move!");
        }
    }

    public void ask(Player player, String questionAbout, String questionValue, String questionString) {
        if (start) {
            if (getCurrentPlayer() == player) {
                currentQuestion = new CurrentQuestion(questionAbout, questionValue, questionString, getAlternative().getCard());
                askCalculation(player, questionAbout, questionValue, questionString);
            } else {
                message(player, "wait", "Not your move!");
            }
        } else {
            message(player, "wait", "Still Waiting for opponent....");
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
        return "GameClassic{" +
                "playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                ", start=" + start +
                ", leavers=" + leavers +
                '}';
    }

    public void guess(Player player, String guessCard) {
        if (start) {
            if (!end) {
                if (getCurrentPlayer() == player) {
                    guessCalculation(player, guessCard);
                } else {
                    message(player, "wait", "Not your move!");
                }
            } else {
                message(player, "end", "Game Ended: the winner is " + getCurrentPlayer().getUsername());
            }

        } else {
            message(player, "wait", "Still Waiting for opponent....");
        }
    }

    protected void guessCalculation(Player player, String guessCard) {
        if (getAlternative().getCard().getNamee().equalsIgnoreCase(guessCard)) {
            message(getCurrentPlayer(), "my-guess", "You guess " + guessCard);
            message(getAlternative(), "op-guess", player.getUsername() + " guess " + guessCard);

            if (getCurrentPlayer().getLies() <= getAlternative().getLies()) {
                message(getAlternative(), "end", "SORRY, YOU LOSE ");
                message(getCurrentPlayer(), "end", "CONGRATULATIONS, YOU WIN");
            } else {
                message(getAlternative(), "end", "The guess was right but " + player.getUsername() + " lose for LIER!!");
                message(getAlternative(), "end", "CONGRATULATIONS, YOU WIN");

                message(getCurrentPlayer(), "end", "The guess was right but YOU lose for LIER!!");
                message(getCurrentPlayer(), "end", "SORRY, YOU LOSE");

                currentPlayer = getAlternative();
            }
            end = true;
        } else {
            message(getCurrentPlayer(), "my-guess", "You guess wrong!!, The card is not " + guessCard);
            message(getAlternative(), "op-guess", player.getUsername() + " guess " + guessCard);
            changeTurn();
            changeTurn();
            notifyTurn();
        }
    }

    private enum TurnState {ASKING, ANSWERING}
}
