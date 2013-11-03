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
        cardsMap = new HashMap<String, String>();
        cardsMap.put("ANISTON", "0e6067");
        cardsMap.put("BECKHAM", "0fbac2");
        cardsMap.put("CRUZ", "22a8a4");
        cardsMap.put("DAVIDS", "2eafcf");
        cardsMap.put("DEPP", "3f7f60");
        cardsMap.put("FOX", "40c79c");
        cardsMap.put("FRANCHELLA", "48166f");
        cardsMap.put("GANDHI", "4e3b5c");
        cardsMap.put("GARCIA", "63db8f");
        cardsMap.put("HATHAWAY", "694599");
        cardsMap.put("IBRAHIMOVICH", "6f4433");
        cardsMap.put("JACKSON", "7028d6");
        cardsMap.put("MESSI", "83f097");
        cardsMap.put("NORRIS", "861d72");
        cardsMap.put("POTTER", "91718d");
        cardsMap.put("RON", "ac4871");
        cardsMap.put("RYAN", "ade557");
        cardsMap.put("SLASH", "b3e0af");
        cardsMap.put("STONE", "bb88ca");
        cardsMap.put("STREEP", "c46e40");
        cardsMap.put("TORRES", "dca3b5");
        cardsMap.put("VERON", "dea8ed");
        cardsMap.put("WILSON", "e08c9c");
        cardsMap.put("ZIDANE", "e5ec68");
    }

    protected Card pickRandomCard() {
        String cards[] = {"ANISTON", "BECKHAM", "CRUZ", "DAVIDS", "DEPP", "FOX", "FRANCHELLA", "GANDHI", "GARCIA", "HATHAWAY",
                "IBRAHIMOVICH", "JACKSON", "MESSI", "NORRIS", "POTTER", "RON", "RYAN", "SLASH", "STONE", "STREEP", "TORRES",
                "VERON", "WILSON", "ZIDANE"};

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
