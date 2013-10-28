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
 * Created by w&w
 * Date: 4/24/12
 */

public class GameClassic extends GameWhoIsWho{

    public GameClassic() {
        super();
    }

    private void loadCardsMap() {
        cardsMap = new HashMap<String, String>();
        cardsMap.put("RICHARD", "0e6067");
        cardsMap.put("GEORGE", "0fbac2");
        cardsMap.put("ANNA", "22a8a4");
        cardsMap.put("ALEX", "2eafcf");
        cardsMap.put("SAM", "3f7f60");
        cardsMap.put("MARIA", "40c79c");
        cardsMap.put("WILLIAM", "48166f");
        cardsMap.put("ALFRED", "4e3b5c");
        cardsMap.put("CHARLES", "63db8f");
        cardsMap.put("TOM", "694599");
        cardsMap.put("ANITA", "6f4433");
        cardsMap.put("ROBERT", "7028d6");
        cardsMap.put("FRANK", "83f097");
        cardsMap.put("PABLO", "861d72");
        cardsMap.put("PETER", "91718d");
        cardsMap.put("CLAIRE", "ac4871");
        cardsMap.put("DAVID", "ade557");
        cardsMap.put("JOE", "b3e0af");
        cardsMap.put("BERNARD", "bb88ca");
        cardsMap.put("GERMAN", "c46e40");
        cardsMap.put("SUSAN", "dca3b5");
        cardsMap.put("MANNY", "dea8ed");
        cardsMap.put("ERNEST", "e08c9c");
        cardsMap.put("PHILIP", "e5ec68");
    }

    private Card pickRandomCard() {
        String cards[] = {"RICHARD", "FRANK", "MANNY", "DAVID", "MARIA", "ANITA", "SUSAN", "ANNA", "GEORGE", "ALEX",
                "SAM", "WILLIAM", "ALFRED", "CHARLES", "TOM", "ROBERT", "PABLO", "PETER", "CLARIE", "JOE", "BERNARD",
                "GERMAN", "ERNEST", "PHILIP"};

        Random turnRoller = new Random();
        int roll = turnRoller.nextInt(24);

        System.out.println("GameClassic: " + this.gameId + " - Loading Card:" + cards[roll]);
        Card card = getCardFromDb(cardsMap.get(cards[roll]).toString());
        return card;
    }

    public Card getCardFromDb(String id) {
        String requestUrl = "http://dpoi2012api.appspot.com/api/1.0/view?credential=w&id=" + id;
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
                message(player, "end", "GameClassic Ended: the winner is " + getCurrentPlayer().getUsername());
            }

        } else {
            message(player, "wait", "Still Waiting for opponent....");
        }
    }

}
