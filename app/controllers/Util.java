package controllers;

 /*
 * User: Jose Gonzalez
 * Date: 02/09/13
 * Time: 17:24
 */

import com.google.common.io.Files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Util {
    List<String> fileList;
    List<String> fileExceptions;


    public void decompress(String zipFile, String outputFolder) {
        byte[] buffer = new byte[1024];
        fileExceptions = new ArrayList<String>();

        try {

            //create output directory is not exists
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {

                String fileName = zipEntry.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                System.out.println("file unzip : " + newFile.getAbsoluteFile());
                fileExceptions.add(getExtension(newFile));

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                if (zipEntry.isDirectory()) {
                    new File(newFile.getParent()).mkdirs();
                } else {
                    FileOutputStream fos = null;

                    new File(newFile.getParent()).mkdirs();

                    fos = new FileOutputStream(newFile);

                    int len;
                    while ((len = zipInputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }

                    fos.close();
                }

                zipEntry = zipInputStream.getNextEntry();
            }

            zipInputStream.closeEntry();
            zipInputStream.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
    Verifies if the folder contains the exact number of images and one .txt,
    returns false otherwise
    */
    public boolean verifyIntegrity() {
        int images = 0;
        int text = 0;

        for (String fileException : fileExceptions) {
            switch (fileException) {
                case "png":
                case "jpg":
                    images++;
                    break;
                case "txt":
                    text++;
                    break;
                case "":
                    break;
                default:
                    return false;
            }
        }
        return images == 24 && text == 1;
    }

    private String getExtension(File file) {
        String path = "" + file.getAbsoluteFile();
        String extencion = Files.getFileExtension(path);
        System.out.println("la extencion es: " + extencion); //prints extencion
        return extencion;
    }


    public static boolean verifyTXT(File file) throws IOException {
        FileReader reader = new FileReader(file);
        BufferedReader shortCutFile = new BufferedReader(reader);
        String line = shortCutFile.readLine();
        StringTokenizer tokenizer;
        int counter = 0;


        while (line != null) {


            tokenizer = new StringTokenizer(line, "-");


            while (tokenizer.hasMoreTokens()) {


                //Verify if the first parameter is a name
                String shortCutLine = tokenizer.nextToken();
                if (shortCutLine.length()>1) {
                    //Go on
                }
                else{
                    return false;
                }


                if(!tokenizer.hasMoreTokens()){
                    return false;
                }


                //Verify if the second parameter is a hair color
                shortCutLine = tokenizer.nextToken();
                if (shortCutLine.contains("o")||shortCutLine.contains("w")||
                        shortCutLine.contains("b")||shortCutLine.contains("y")) {
                    //Go on
                }
                else{
                    return false;
                }


                if(!tokenizer.hasMoreTokens()){
                    return false;
                }


                //Verify the last 8 parameters
                for(int i = 0; i < 8; i++){
                    if(!tokenizer.hasMoreTokens()){
                        return false;
                    }


                    shortCutLine = tokenizer.nextToken();
                    if(i == 6){
                        if (shortCutLine.contains("M")||shortCutLine.contains("F")) {
                            continue;
                        }
                        else{
                            return false;
                        }
                    }
                    else{
                        if (shortCutLine.contains("f")||shortCutLine.contains("t")) {
                            continue;
                        }
                        else{
                            return false;
                        }
                    }
                }


            }
            counter++;
            line = shortCutFile.readLine();


        }


        reader.close();
        if(counter==24){
            return true;
        }
        else{
            return false;
        }


    }


    public static void main(String[] args) {
        Util util = new Util();
        File file = null;
        file = new File("testzip/test.txt");
        try {
            System.out.println(String.valueOf(util.verifyTXT(file))+"");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
