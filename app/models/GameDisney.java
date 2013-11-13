package models;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

/**
 * User: Jose Gonzalez
 * Date: 11/5/13
 * Time: 4:19 AM
 */

public class GameDisney extends GameWhoIsWho{

    public GameDisney() {
        super();
    }

    protected void loadCardsMap() {
        cardsMap = new HashMap<String, Card>();
        cardsMap.put("ARIEL", new Card("ARIEL", "orange", false, true, true, false, false, false, 'F', false));
        cardsMap.put("ARTHUR", new Card("ARTHUR", "yellow", false, false, false, false, false, false, 'M', false));
        cardsMap.put("CARL", new Card("CARL", "white", false, false, true, false, false, false, 'M', true));
        cardsMap.put("CUASI", new Card("CUASI", "orange", false, false, false, false, false, false, 'M', true));
        cardsMap.put("FEBO", new Card("FEBO", "yellow", false, true, false, false, true, false, 'M', false));
        cardsMap.put("GEPETO", new Card("GEPETO", "white", false, false, true, false, false, true, 'M', true));
        cardsMap.put("GRUMPY", new Card("GRUMPY", "white", true, false, false, true, true, false, 'M', true));
        cardsMap.put("HOOK", new Card("HOOK", "black", true, true, false, false, false, true, 'M', false));
        cardsMap.put("ISMA", new Card("ISMA", "black", true, false, false, false, false, false, 'F', false));
        cardsMap.put("JANE", new Card("JANE", "brawn", false, false, false, false, false, false, 'F', false));
        cardsMap.put("JAZMIN", new Card("JAZMIN", "black", false, true, false, false, false, false, 'F', false));
        cardsMap.put("JIM", new Card("JIM", "brawn", false, false, false, false, false, true, 'M', true));
        cardsMap.put("JOHN", new Card("JOHN", "yellow", false, true, false, true, false, false, 'M', true));
        cardsMap.put("KUZCO", new Card("KUZCO", "black", false, true, false, false, false, false, 'M', false));
        cardsMap.put("LINGUINI", new Card("LINGUINI", "orange", true, false, false, false, false, false, 'M', false));
        cardsMap.put("MILO", new Card("MILO", "brawn", false, false, true, false, false, true, 'M', false));
        cardsMap.put("MR SMEE", new Card("MR SMEE", "white", false, false, true, true, false, false, 'M', false));
        cardsMap.put("PETER", new Card("PETER", "orange", true, false, false, false, false, false, 'M', true));
        cardsMap.put("PHIL", new Card("PHIL", "orange", false, false, false, true, true, false, 'M', true));
        cardsMap.put("SULTAN", new Card("SULTAN", "white", true, false, false, true, true, true, 'M', false));
        cardsMap.put("TARZAN", new Card("TARZAN", "brawn", false, true, false, false, false, false, 'M', false));
        cardsMap.put("TONY", new Card("TONY", "black", false, false, false, true, false, true, 'M', true));
        cardsMap.put("WITCH", new Card("WITCH", "white", true, true, false, false, false, false, 'F', true));
        cardsMap.put("YAO", new Card("YAO", "black", false, false, false, false, false, true, 'M', true));
    }

    protected Card pickRandomCard() {
        String cards[] = {"ARIEL", "ARTHUR", "CARL", "CUASI", "FEBO", "GEPETO", "GRUMPY", "HOOK", "ISMA", "JANE",
                "JAZMIN", "JIM", "JOHN", "KUZCO", "LINGUINI", "MILO", "MR SMEE", "PETER", "PHIL", "SULTAN", "TARZAN",
                "TONY", "WITCH", "YAO"};

        Random turnRoller = new Random();
        int roll = turnRoller.nextInt(24);

        System.out.println("GameDisney: " + this.gameId + " - Loading Card:" + cards[roll]);
        Card card = (Card) cardsMap.get(cards[roll]);
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
        System.out.println("GameDisney: " + this.gameId + " - ERROR: Card id no found");
        return null;
    }

    @Override
    public String toString() {
        return "GameDisney{" +
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
                message(player, "end", "GameDisney Ended: the winner is " + getCurrentPlayer().getUsername());
            }

        } else {
            message(player, "wait", "Still Waiting for opponent....");
        }
    }

}