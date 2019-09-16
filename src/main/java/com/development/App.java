package com.development;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class App 
{
    public static void main( String[] args )
    {
        String token = "";

        try {
            File file = new File("answer.json");
            
            if (file.createNewFile()) {
                URI uri = new URI("https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=" + token);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                //System.out.println(response.body());
                
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(response.body());
                fileWriter.close();
            }

            String line = "";
            String fileContent = "";
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            fileReader.close();
            
            while ((line = bufferedReader.readLine()) != null) {
                fileContent += line;
            }
            
            System.out.println(fileContent);
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
