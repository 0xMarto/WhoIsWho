package models;

import scala.util.parsing.json.JSONObject;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.*;
/**
 * Created with IntelliJ IDEA.
 * User: debian
 * Date: 5/19/12
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class Card {

    private String name;
    private String hairColor;
    private boolean hat;
    private boolean longHair;
    private boolean glasses;
    private boolean bold;
    private boolean beard;
    private boolean mustache;
    private Character sex;
    private boolean bigNose;


    public Card(String name) {

        this.name = name;
        loadAttributes(name);


    }

    public void loadAttributes(String name){

    makeRequest(name);

    }

    public void makeRequest(String name){

        // importante: agregar desde Idea la libreria Gson-1.1.jar ( te lo ofrece automaticamente )

        String requestUrl = "http://<GAE-URL>?name="+name;  // aca va la URL del server GAE
        try {
            URL url = new URL(requestUrl.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine, result = "";
            while ((inputLine = in.readLine()) != null) {
                result = result.concat(inputLine);
            }
            in.close();

            new Gson().fromJson(result, Card.class);

            // hasta aca tengo El objeto card que pedi a GSON todo llenito


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public boolean isHat() {
        return hat;
    }

    public void setHat(boolean hat) {
        this.hat = hat;
    }

    public boolean isLongHair() {
        return longHair;
    }

    public void setLongHair(boolean longHair) {
        this.longHair = longHair;
    }

    public boolean isGlasses() {
        return glasses;
    }

    public void setGlasses(boolean glasses) {
        this.glasses = glasses;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isBeard() {
        return beard;
    }

    public void setBeard(boolean beard) {
        this.beard = beard;
    }

    public boolean isMustache() {
        return mustache;
    }

    public void setMustache(boolean mustache) {
        this.mustache = mustache;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public boolean isBigNose() {
        return bigNose;
    }

    public void setBigNose(boolean bigNose) {
        this.bigNose = bigNose;
    }
}

