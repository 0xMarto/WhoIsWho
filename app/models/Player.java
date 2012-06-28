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
    private String Card;

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

    public String getCard() {
        return Card;
    }

    public void setCard(String card) {
        Card = card;
    }
}
