package controllers;

import models.ConnectionHandler;
import org.codehaus.jackson.JsonNode;
import play.api.Play;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.aboutGame;
import views.html.chatRoom;
import views.html.index;
import views.html.ranking;

import java.io.File;
import java.io.IOException;

public class Application extends Controller {

    /**
     * Display the home page.
     */
    public static Result index() {
        return ok(index.render());
    }

    /**
     * Display the about game page.
     */
    public static Result aboutGame() {
        return ok(aboutGame.render());
    }

    /**
     * Display Ranking page.
     */
    public static Result ranking(String username) {
        return ok(ranking.render(username));
    }

    /**
     * Display the chat room.
     */
    public static Result chatRoom(String username, String theme) {
        if (username == null || username.trim().equals("") || username.contains("%")) {
            flash("error", "Please choose a valid username.");
            return redirect(routes.Application.index());
        }
        if (theme == null || theme.trim().equals("") || theme.contains("%")) {
            flash("error", "Please choose a theme.");
            return redirect(routes.Application.index());
        }
        if(theme.contains("cel")){
            return ok(views.html.famousChatRoom.render(username));
        }
        return ok(chatRoom.render(username));
    }

    /**
     * Handle the game webSocket.
     */
    public static WebSocket<JsonNode> game(final String username, final String theme) {
        return new WebSocket<JsonNode>() {

            // Called when the WebSocket Handshake is done.
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
                // Join the user to the GameClassic.
                try {
                    ConnectionHandler.join(username, theme,  in, out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
    * Uploads a .zip o .rar file
    */
    public static Result upload() throws IOException {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart zipFile = body.getFile("zip");
        String fileName = zipFile.getFilename();
        System.out.println("zipfile= " + fileName);
        String suffix = fileName.substring((fileName.length() - 4));
        System.out.println(suffix);

        if (zipFile != null && (suffix.equals(".zip"))) {
            //String fileName = zipFile.getFilename();
            String contentType = zipFile.getContentType();
            File file = zipFile.getFile();
            File finalFile = new File(Play.current().path().getAbsolutePath() + "/public/themes/"
                    + String.valueOf((int) (Math.random() * 10000000)));

            Util util = new Util();
            util.decompress(file.getAbsolutePath(), finalFile.getAbsolutePath());
            File[] listOfFiles = finalFile.listFiles();
            File textFile = null;

            //in case the user upload a zip whit all the files inside a folder
            if(listOfFiles.length==1 && util.getExtension(listOfFiles[0]).equals("")){
                finalFile = new File(listOfFiles[0].getAbsolutePath());
                listOfFiles = finalFile.listFiles();
            }

            for (File tempFile : listOfFiles) {
                final int beginIndex = tempFile.getName().lastIndexOf('.');
                if (beginIndex != -1) {
                    if (tempFile.getName().substring(beginIndex).contains("txt")){
                        System.out.println(file);
                        textFile = tempFile;
                        break;
                    }
                }
            }

            final boolean integrity = util.verifyIntegrity();
            boolean txtIntegrity = false;
            if (textFile != null){
                txtIntegrity = Util.verifyTXT(textFile);
            }

            if (integrity && txtIntegrity) {
                flash("Success","Your theme file was successfully upload!");
                return redirect(routes.Application.index());
            } else {
                if (!integrity){
                    Util.delete(finalFile);
                    flash("Error", "Zip file is not valid. The zip file you upload doesn't have the right files inside!");
                    return redirect(routes.Application.index());
                }
                Util.delete(finalFile);
                flash("Error", "The text file in the zip file doesn't have the required format!");
                return redirect(routes.Application.index());
            }
        } else {
            Util.delete(zipFile.getFile());
            flash("Error", "The file you tried to upload is not a zip file!");
            return redirect(routes.Application.index());
        }
    }

}
