package models;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by w&w
 * Date: 4/24/12
 */

public class GameClassic extends GameWhoIsWho{

    public GameClassic() {
        super();
    }

    protected void loadCardsMap() {
        cardsMap = new HashMap<String, String>();
        cardsMap.put("RICHARD", new Card("RICHARD","brawn", false, false, false, true, true, true, 'M', false));
        cardsMap.put("GEORGE", new Card("GEORGE", "white", true, false, false, false, false, false, 'M', false));
        cardsMap.put("ANNA", new Card("ANNA", "black", false, false, false, false, false, false, 'F', true));
        cardsMap.put("ALEX", new Card("ALEX", "black", false, false, false, false, false, true, 'M', false));
        cardsMap.put("SAM", new Card("SAM", "white", false, false, true, true, false, false, 'M', false));
        cardsMap.put("MARIA", new Card("MARIA", "brawn", true, true, false, false, true, false, 'F', false));
        cardsMap.put("WILLIAM", new Card("WILLIAM", "orange", false, false, false, true, false, false, 'M', false));
        cardsMap.put("ALFRED", new Card("ALFRED", "orange", false, true, false, false, false, true, 'M', false));
        cardsMap.put("CHARLES", new Card("CHARLES", "yellow", false, false, false, false, false, true, 'M', false));
        cardsMap.put("TOM", new Card("TOM", "black", false, false, true, true, false, false, 'M', false));
        cardsMap.put("ANITA", new Card("ANITA", "yellow", false, true, false, false, false, false, 'F', false));
        cardsMap.put("ROBERT", new Card("ROBERT", "brawn", false, false, false, false, false, false, 'M', true));
        cardsMap.put("FRANK", new Card("FRANK", "orange", false, false, false, false, false, false, 'M', false));
        cardsMap.put("PABLO", new Card("PABLO", "white", false, false, true, false, false, false, 'M', false));
        cardsMap.put("PETER", new Card("PETER", "white", false, false, false, false, false, false, 'M', true));
        cardsMap.put("CLAIRE", new Card("CLAIRE", "orange", true, false, true, false, false, false, 'F', false));
        cardsMap.put("DAVID", new Card("DAVID", "yellow", false, false, false, false, true, false, 'M', false));
        cardsMap.put("JOE", new Card("JOE", "yellow", false, false, true, false, false, false, 'M', false));
        cardsMap.put("BERNARD", new Card("BERNARD", "brawn", true, false, false, false, false, false, 'M', true));
        cardsMap.put("GERMAN", new Card("GERMAN", "orange", false, false, false, true, false, false, 'M', true));
        cardsMap.put("SUSAN", new Card("SUSAN", "white", false, true, false, false, false, false, 'F', false));
        cardsMap.put("MANNY", new Card("MANNY", "brawn", false, false, false, false, false, true, 'M', true));
        cardsMap.put("ERNEST", new Card("ERNEST", "yellow", true, false, false, false, false, false, 'M', false));
        cardsMap.put("PHILIP", new Card("PHILIP", "black", false, false, false, false, true, false, 'M', false));
    }

    protected Card pickRandomCard() {
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
