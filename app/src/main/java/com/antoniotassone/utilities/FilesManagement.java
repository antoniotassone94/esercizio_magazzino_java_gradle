package com.antoniotassone.utilities;

import com.antoniotassone.models.Models;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class FilesManagement{
    private static final String USER_DIRECTORY = "user_application";

    private FilesManagement(){}

    public static String getUserDirectory(){
        return USER_DIRECTORY;
    }

    public static List<String> readListFiles(){
        return FilesManagement.readFile(FilesManagement.USER_DIRECTORY + "/filesystem.txt");
    }

    public static List<String> readFile(String fileName){
        if(fileName == null){
            return new LinkedList<>();
        }
        if(fileName.isEmpty()){
            return new LinkedList<>();
        }
        FileInputStream fis;
        try{
            fis = new FileInputStream(fileName);
        }catch(FileNotFoundException exception){
            exception.printStackTrace();
            return new LinkedList<>();
        }
        InputStreamReader isr = new InputStreamReader(fis,StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(isr);
        List<String> output = new LinkedList<>();
        try{
            String line = in.readLine();
            while(line != null){
                output.add(line);
                line = in.readLine();
            }
        }catch(IOException exception){
            exception.printStackTrace();
            output.clear();
        }finally{
            try{
                in.close();
            }catch(IOException exception1){
                exception1.printStackTrace();
                output.clear();
            }
            try{
                isr.close();
            }catch(IOException exception1){
                exception1.printStackTrace();
                output.clear();
            }
            try{
                fis.close();
            }catch(IOException exception1){
                exception1.printStackTrace();
                output.clear();
            }
        }
        return output;
    }

    public static boolean writeFile(String fileName,List<String> content){
        if(fileName == null || content == null){
            return false;
        }
        if(fileName.isEmpty()){
            return false;
        }
        FileOutputStream fos;
        try{
            fos = new FileOutputStream(fileName);
        }catch(FileNotFoundException exception){
            exception.printStackTrace();
            return false;
        }
        OutputStreamWriter osw = new OutputStreamWriter(fos,StandardCharsets.UTF_8);
        BufferedWriter out = new BufferedWriter(osw);
        boolean output;
        try{
            for(String line:content){
                out.write(line);
                out.newLine();
                out.flush();
            }
            output = true;
        }catch(IOException exception){
            exception.printStackTrace();
            output = false;
        }finally{
            try{
                out.close();
            }catch(IOException exception1){
                exception1.printStackTrace();
                output = false;
            }
            try{
                osw.close();
            }catch(IOException exception1){
                exception1.printStackTrace();
                output = false;
            }
            try{
                fos.close();
            }catch(IOException exception1){
                exception1.printStackTrace();
                output = false;
            }
        }
        return output;
    }

    public static <T extends Models<T>> boolean writeObjectToFile(Models<T> object){
        if(object == null){
            return false;
        }
        String className = object.getClass().getName();
        String[] details = className.split("[.]");
        String fileName = details[details.length - 1].toLowerCase() + "_" + object.getId() + ".json";
        List<String> rows = new LinkedList<>();
        rows.add(object.toString());
        List<String> files = FilesManagement.readListFiles();
        files.add(fileName);
        if(FilesManagement.writeFile(FilesManagement.USER_DIRECTORY + "/" + fileName,rows)){
            return FilesManagement.writeFile(FilesManagement.USER_DIRECTORY + "/filesystem.txt",files);
        }
        return false;
    }

    public static <T extends Models<T>> boolean deleteObject(Models<T> object){
        if(object == null){
            return false;
        }
        String className = object.getClass().getName();
        String[] details = className.split("[.]");
        String fileName = details[details.length - 1].toLowerCase() + "_" + object.getId() + ".json";
        String pathName = FilesManagement.USER_DIRECTORY + "/" + fileName;
        File file = new File(pathName);
        if(!file.exists()){
            return false;
        }
        List<String> files = FilesManagement.readListFiles();
        int i = 0;
        while(i < files.size() && !files.get(i).equals(fileName)){
            i++;
        }
        if(i >= files.size()){
            return false;
        }
        String existing = files.remove(i);
        if(existing.equals(fileName)){
            if(file.delete()){
                return FilesManagement.writeFile(FilesManagement.USER_DIRECTORY + "/filesystem.txt",files);
            }
            return false;
        }
        return false;
    }
}