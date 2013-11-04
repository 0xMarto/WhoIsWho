package controllers;

 /*
 * User: Jose Gonzalez
 * Date: 02/09/13
 * Time: 17:24
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Util {
    List<String> fileList;
    List<String> fileExtensions;


    public void decompress(String zipFile, String outputFolder) {
        byte[] buffer = new byte[1024];
        fileExtensions = new ArrayList<String>();

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

                //System.out.println("file unzip : " + newFile.getAbsoluteFile());
                fileExtensions.add(getExtension(newFile));

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

            //System.out.println("Done");

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

        for (String fileExtension : fileExtensions) {
            if (fileExtension.equals("png") || fileExtension.equals("jpg")) {
                images++;
            } else if (fileExtension.equals("txt")) {
                text++;
            } else if (fileExtension.equals("")) {
            } else {
                return false;
            }
        }
        return images == 24 && text == 1;
    }

    private String getExtension(File file) {
        String path = "" + file.getAbsoluteFile();
        String extencion = "";
        final int beginIndex = file.getName().lastIndexOf('.');
        if (beginIndex != -1) {
            extencion = file.getName().substring(beginIndex + 1);
        }
        //System.out.println("la extencion es: " + extencion); //prints extencion
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

    public static void delete(File file)
            throws IOException{

        if(file.isDirectory()){

            //directory is empty, then delete it
            if(file.list().length==0){

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            }else{

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if(file.list().length==0){
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }

        }else{
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }


    public static void main(String[] args) {
        Util util = new Util();
        File file = null;
        //file = new File(".txt");
        //util.decompress("famosos.zip", "prueba");
        System.out.println("integridad "+util.verifyIntegrity());
        /*try {
            System.out.println(String.valueOf(util.verifyTXT(file))+"");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }    */
    }

}
