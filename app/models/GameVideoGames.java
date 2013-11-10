package models;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

/**
 * Jose Gonzalez
 * Date: 11/10/13
 * Time: 7:31 AM
 */

public class GameVideoGames extends GameWhoIsWho{

    public GameVideoGames() {
        super();
    }

    protected void loadCardsMap() {
        cardsMap = new HashMap<String, Card>();
        cardsMap.put("ASH", new Card("ASH", "black", true, false, false, false, false, false, 'M', false));
        cardsMap.put("BAYONETTA", new Card("BAYONETTA", "black", false, true, true, false, false, false, 'F', false));
        cardsMap.put("BO RAI CHO", new Card("BO RAI CHO", "black", false, true, false, false, true, true, 'M', true));
        cardsMap.put("BOWSER", new Card("BOWSER", "orange", false, true, false, false, false, false, 'M', true));
        cardsMap.put("GRUNTILDA", new Card("GRUNTILDA", "black", true, true, false, false, false, false, 'F', true));
        cardsMap.put("JAX", new Card("JAX", "black", false, false, false, false, false, true, 'M', false));
        cardsMap.put("JIHL NABAAT", new Card("JIHL NABAAT", "yellow", false, true, true, false, false, false, 'F', false));
        cardsMap.put("JINPACHI", new Card("JINPACHI", "white", false, false, false, true, true, true, 'M', true));
        cardsMap.put("JOHNNY CAGE", new Card("JOHNNY CAGE", "brown", false, false, true, false, false, false, 'M', false));
        cardsMap.put("KO HORO", new Card("KO HORO", "brown", false, true, false, false, false, true, 'M', false));
        cardsMap.put("KRATOS", new Card("KRATOS", "black", false, false, false, true, true, false, 'M', false));
        cardsMap.put("LINK", new Card("LINK", "yellow", true, true, false, false, false, false, 'M', false));
        cardsMap.put("MARIO", new Card("MARIO", "black", true, false, false, false, false, true, 'M', true));
        cardsMap.put("MAY", new Card("MAY", "brown", true, true, false, false, false, false, 'F', false));
        cardsMap.put("NAKIRO", new Card("NAKIRO", "orange", false, true, false, false, false, false, 'F', false));
        cardsMap.put("OAK", new Card("OAK", "white", false, false, false, false, false, false, 'M', false));
        cardsMap.put("OLD MUSHROM", new Card("OLD MUSHROM", "white", true, false, true, true, false, true, 'M', false));
        cardsMap.put("PAUL PHOENIX", new Card("PAUL PHOENIX", "yellow", false, false, false, false, true, true, 'M', false));
        cardsMap.put("PEACH", new Card("PEACH", "yellow", true, true, false, false, false, false, 'F', false));
        cardsMap.put("ROBOTNIK", new Card("ROBOTNIK", "brown", false, false, true, true, false, true, 'M', true));
        cardsMap.put("SHUJINKO", new Card("SHUJINKO", "white", false, true, false, false, true, true, 'M', false));
        cardsMap.put("SINDEL", new Card("SINDEL", "white", false, true, false, false, false, false, 'F', false));
        cardsMap.put("TANYA", new Card("TANYA", "black", false, false, false, false, false, false, 'F', false));
        cardsMap.put("WARIO", new Card("WARIO", "brown", true, false, false, false, false, true, 'M', true));
    }

    protected Card pickRandomCard() {
        String cards[] = {"ASH", "BAYONETTA", "BO RAI CHO", "BOWSER", "GRUNTILDA", "JAX", "JIHL NABAAT", "JINPACHI", "JOHNNY CAGE", "KO HORO",
                "KRATOS", "LINK", "MARIO", "MAY", "NAKIRO", "OAK", "OLD MUSHROM", "PAUL PHOENIX", "PEACH", "ROBOTNIK", "SHUJINKO",
                "SINDEL", "TANYA", "WARIO"};

        Random turnRoller = new Random();
        int roll = turnRoller.nextInt(24);

        System.out.println("GameVideoGame: " + this.gameId + " - Loading Card:" + cards[roll]);
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
        System.out.println("GameVideoGame: " + this.gameId + " - ERROR: Card id no found");
        return null;
    }

    @Override
    public String toString() {
        return "GameVideoGame{" +
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
                message(player, "end", "GameVideoGame Ended: the winner is " + getCurrentPlayer().getUsername());
            }

        } else {
            message(player, "wait", "Still Waiting for opponent....");
        }
    }

}