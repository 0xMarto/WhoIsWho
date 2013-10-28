package models;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.F;
import play.libs.Json;
import play.mvc.WebSocket;

import java.util.ArrayList;

/**
 * Created by Mart0
 * Date: 4/24/12
 */
public class ConnectionHandler {
    private static ArrayList<GameClassic> gameClassicList = new ArrayList<GameClassic>();
    private static int gamesPlayed = 0;
    private static int activeGames = 0;

    public static void join(String username, WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
        GameClassic lastGameClassic = getLastGame();
        if (!lastGameClassic.isPlayerOneDefined()) {
            final Player player = new Player(username, out, lastGameClassic.getGameId());
            lastGameClassic.setPlayerA(player);
            bingInWebSocket(in, player);
        } else if (!lastGameClassic.isPlayerTwoDefined()) {
            final Player player = new Player(username, out, lastGameClassic.getGameId());
            lastGameClassic.setPlayerB(player);
            bingInWebSocket(in, player);
            lastGameClassic.startGame();
        } else {
            createNewGame();
            join(username, in, out);
//            out.write(createServerFullMsg());
        }
    }

    private static GameClassic getLastGame() {
        GameClassic last;
        if (gameClassicList.isEmpty()) {
            createNewGame();
            last = gameClassicList.get(0);
        } else {
            last = gameClassicList.get(gameClassicList.size() - 1);
        }
        return last;
    }

    private static void createNewGame() {
        activeGames++;
        gamesPlayed++;
        gameClassicList.add(new GameClassic());
    }

    private static JsonNode createServerFullMsg() {
        final ObjectNode json = Json.newObject();
        json.put("error", "The server is full, try again later.");
        return json;
    }

    private static void bingInWebSocket(WebSocket.In<JsonNode> in, final Player player) {
        in.onMessage(new F.Callback<JsonNode>() {
            public void invoke(JsonNode jsonNode) throws Throwable {
                GameClassic gameClassic = getGameById(player.getGameId());
                String messageType = jsonNode.get("type").asText();
                System.out.println("GameClassic: " + gameClassic.getGameId() + " - Event Received: Type = " + messageType);
                if (gameClassic.isStart()) {
                    if (messageType.equals("chat")) {
//                        Chat behavior
                        final String talk = jsonNode.get("text").asText();
                        gameClassic.chat(player, talk);
                    } else if (messageType.equals("question")) {
//                        Question behavior
                        final String questionString = jsonNode.get("questionString").asText();
                        final String questionAbout = jsonNode.get("questionAbout").asText();
                        final String questionValue = jsonNode.get("questionValue").asText();
                        gameClassic.ask(player, questionAbout, questionValue, questionString);
                    } else if (messageType.equals("answer")) {
//                        Answer behavior
                        final String answer = jsonNode.get("answer").asText();
                        gameClassic.answer(player, answer);
                    } else if (messageType.equals("guess")) {
//                        Guess behavior
                        final String guessCard = jsonNode.get("guessCard").asText();
                        gameClassic.guess(player, guessCard.toUpperCase());
                    }
                } else {
//                    Waiting for another player
                    gameClassic.chat(player, "");
                }
                if (messageType.equals("serverInfo")) {
                    GameClassic.message(player, "info", "  " + activeGames + " Active Games" + " - " +
                            gamesPlayed + " Total Games Played");
                }
            }
        });

        in.onClose(new F.Callback0() {
            public void invoke() throws Throwable {
                GameClassic gameClassic = getGameById(player.getGameId());
                gameClassic.leave(player);
                if (gameClassic.isEmpty()) {
                    gameClassicList.remove(gameClassicList.indexOf(gameClassic));
                    activeGames--;
                }
            }
        });
    }

    private static GameClassic getGameById(String gameId) {
        for (GameClassic gameClassic : gameClassicList) {
            if (gameClassic.getGameId().equals(gameId)) {
                return gameClassic;
            }
        }
        return null;
    }


}
