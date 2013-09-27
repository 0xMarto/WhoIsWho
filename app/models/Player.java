package models;

import org.codehaus.jackson.JsonNode;
import play.mvc.WebSocket;

/**
 * Created by Mart0
 * Date: 4/24/12
 */
public class Player {
    private String username;
    private WebSocket.Out<JsonNode> channel;
    private String gameId;
    private Card Card;
    private int lies;

    public Player(String name, WebSocket.Out<JsonNode> out, String id) {
        username = name;
        channel = out;
        gameId = id;
    }

    public String getUsername() {
        return username;
    }

    public WebSocket.Out<JsonNode> getChannel() {
        return channel;
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return username;
    }

    public Card getCard() {
        return Card;
    }

    public void setCard(Card card) {
        Card = card;
    }

    public int getLies() {
        return lies;
    }

    public void lied() {
        lies++;
    }
}
