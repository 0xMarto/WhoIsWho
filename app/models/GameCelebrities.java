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
 * Date: 28/10/13
 * Time: 16:33
 */

public class GameCelebrities extends GameWhoIsWho{

    public GameCelebrities() {
        super();
    }

    protected void loadCardsMap() {
        cardsMap = new HashMap<String, Card>();
        cardsMap.put("ANISTON", new Card("ANISTON", "yellow", false, true, true, false, false, false, 'F', false));
        cardsMap.put("BECKHAM", new Card("BECKHAM", "yellow", false, false, false, false, true, false, 'M', false));
        cardsMap.put("CRUZ", new Card("CRUZ", "black", true, true, false, false, false, false, 'F', false));
        cardsMap.put("DAVIDS", new Card("DAVIDS", "brawn", false, true, true, false, false, false, 'M', false));
        cardsMap.put("DEPP", new Card("DEPP", "orange", true, true, false, false, false, false, 'M', false));
        cardsMap.put("FOX", new Card("FOX", "black", false, true, false, false, false, false, 'F', false));
        cardsMap.put("FRANCHELLA", new Card("FRANCHELLA", "black", false, false, false, false, false, true, 'M', false));
        cardsMap.put("GANDHI", new Card("GANDHI", "white", false, false, true, true, false, true, 'M', true));
        cardsMap.put("GARCIA", new Card("GARCIA", "brawn", false, true, true, false, false, true, 'M', false));
        cardsMap.put("HATHAWAY", new Card("HATHAWAY", "brawn", false, false, false, true, false, false, 'F', false));
        cardsMap.put("IBRAHIMOVICH", new Card("IBRAHIMOVICH", "brawn", false, true, false, false, false, true, 'M', true));
        cardsMap.put("JACKSON", new Card("JACKSON", "black", false, true, false, false, false, false, 'M', false));
        cardsMap.put("MESSI", new Card("MESSI", "brawn", false, false, false, false, true, false, 'M', true));
        cardsMap.put("NORRIS", new Card("NORRIS", "brawn", true, false, false, false, true, false, 'M', false));
        cardsMap.put("POTTER", new Card("POTTER", "brawn", false, false, true, false, false, false, 'M', false));
        cardsMap.put("RON", new Card("RON", "orange", false, true, false, false, false, false, 'M', false));
        cardsMap.put("RYAN", new Card("RYAN", "yellow", true, false, false, false, false, false, 'M', false));
        cardsMap.put("SLASH", new Card("SLASH", "black", true, true, true, false, false, false, 'M', false));
        cardsMap.put("STONE", new Card("STONE", "orange", false, true, false, false, false, false, 'F', false));
        cardsMap.put("STREEP", new Card("STREEP", "white", false, false, false, false, false, false, 'F', false));
        cardsMap.put("TORRES", new Card("TORRES", "yellow", false, false, false, false, false, false, 'M', false));
        cardsMap.put("VERON", new Card("VERON", "black", false, false, false, true, true, false, 'M', false));
        cardsMap.put("WILSON", new Card("WILSON", "yellow", false, true, false, false, false, false, 'M', true));
        cardsMap.put("ZIDANE", new Card("ZIDANE", "brawn", false, false, false, true, true, false, 'M', false));
    }

    protected Card pickRandomCard() {
        String cards[] = {"ANISTON", "BECKHAM", "CRUZ", "DAVIDS", "DEPP", "FOX", "FRANCHELLA", "GANDHI", "GARCIA", "HATHAWAY",
                "IBRAHIMOVICH", "JACKSON", "MESSI", "NORRIS", "POTTER", "RON", "RYAN", "SLASH", "STONE", "STREEP", "TORRES",
                "VERON", "WILSON", "ZIDANE"};

        Random turnRoller = new Random();
        int roll = turnRoller.nextInt(24);

        System.out.println("GameClassic: " + this.gameId + " - Loading Card:" + cards[roll]);
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
        System.out.println("GameCelebrities: " + this.gameId + " - ERROR: Card id no found");
        return null;
    }

    @Override
    public String toString() {
        return "GameCelebrities{" +
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
                message(player, "end", "GameCelebrities Ended: the winner is " + getCurrentPlayer().getUsername());
            }

        } else {
            message(player, "wait", "Still Waiting for opponent....");
        }
    }

}
