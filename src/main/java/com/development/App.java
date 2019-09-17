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

import com.google.gson.Gson;

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
                
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(response.body());
                fileWriter.close();
            }

            String line = "";
            String fileContent = "";
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while ((line = bufferedReader.readLine()) != null) {
                fileContent += line;
            }
            
            bufferedReader.close();
            fileReader.close();

            Gson gson = new Gson();
            Answer answer = gson.fromJson(fileContent, Answer.class);
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            String decifrado = "";

            for (int x = 0; x < answer.getCifrado().length(); x++) {
                char ch = answer.getCifrado().charAt(x);
                char chAux = ch;

                if (Character.isLetter(ch)) {
                    int y = (alphabet.indexOf(ch) - answer.getNumeroCasas());

                    if (y < 0) {
                        y = ((alphabet.length() - 1) + (y + 1));
                    }

                    chAux = alphabet.charAt(y);
                }

                decifrado += chAux;
            }

            answer.setDecifrado(decifrado);

            System.out.println(answer.getNumeroCasas());
            System.out.println(answer.getToken());
            System.out.println(answer.getCifrado());
            System.out.println(answer.getDecifrado());
            System.out.println(answer.getResumoCriptografico());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
