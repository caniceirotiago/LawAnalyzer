package LAproject;

import LAproject.model.LawParameter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import LAproject.aiap.ArtificialInteligenceAPI;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        if (args.length == 0) {
//            System.err.println("Please provide the law text as a command line argument.");
//            System.exit(1);
//        }
//
//        String lawText = args[0];
        String lawText = "1. Os parâmetros para o dimensionamento das áreas destinadas a estacionamento a considerar\n" +
                "em operações urbanísticas sujeitas a controlo prévio são os seguintes, sem prejuízo da\n" +
                "legislação específica relativa a acessibilidade de pessoas com mobilidade condicionada e\n" +
                "relativa ao setor do turismo:\n" +
                "a) edificação destinada a habitação em moradia unifamiliar/bifamiliar:\n" +
                "a.1) número mínimo de lugares de estacionamento a prever no interior do lote ou parcela:\n" +
                "i) 1 lugar/fogo com área de construção ≤ 200 m2;\n" +
                "ii) 2 lugares/fogo com área de construção > 200 m2;\n" +
                "a.2) o número total de lugares resultante da aplicação dos critérios anteriores é acrescido\n" +
                "em 20% que se destina a estacionamento a ceder para o domínio público;\n" +
                "b) edificação destinada a habitação coletiva:\n" +
                "b.1) número mínimo de lugares de estacionamento a prever no interior do lote ou parcela:\n" +
                "i) habitação com indicação de tipologia:\n" +
                "i.1) 1 lugar/fogo T0 e T1;\n" +
                "i.2) 1,5 lugares/fogo T2 e T3;\n" +
                "i.3) 2 lugares/fogo ≥ T4;\n" +
                "ii) habitação sem indicação de tipologia:\n" +
                "ii.1) 1 lugar/fogo quando a área média do fogo < 120 m2;\n" +
                "ii.2) 1,5 lugares/fogo quando a área média do fogo está entre 120 m2 e\n" +
                "200 m2;\n" +
                "ii.3) 2 lugares/fogo quando a área média do fogo > 200 m2;\n" +
                "b.2) o número total de lugares resultante da aplicação dos critérios anteriores é acrescido\n" +
                "em 20% que se destina a estacionamento a ceder para o domínio público;";

        try {
            String response = ArtificialInteligenceAPI.analyzeLawText(lawText);
            System.out.println("Response: " + response);

            // Extract the JSON part from the response, removing the ```json and ``` delimiters
            String content = extractJsonContent(response);
            System.out.println("Extracted content: " + content);

            // Create a lenient JsonReader
            JsonReader reader = new JsonReader(new StringReader(content));
            reader.setLenient(true);

            JsonArray parametersArray = JsonParser.parseReader(reader).getAsJsonArray();
            List<LawParameter<?>> lawParameters = new ArrayList<>();

            for (int i = 0; i < parametersArray.size(); i++) {
                JsonObject parameterObject = parametersArray.get(i).getAsJsonObject();
                String propertyName = parameterObject.get("property_name").getAsString();
                String valueType = parameterObject.get("value_type").getAsString();

                if ("boolean".equals(valueType)) {
                    boolean value = parameterObject.get("value").getAsBoolean();
                    lawParameters.add(new LawParameter<>(propertyName, i + 1, value));
                } else {
                    double value = parameterObject.get("value").getAsDouble();
                    lawParameters.add(new LawParameter<>(propertyName, i + 1, value));
                }
            }

            // Print all extracted parameters
            for (LawParameter<?> parameter : lawParameters) {
                System.out.println("Extracted Parameter: " + parameter.getLawName() + " - " + parameter.getLawParameter());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.err.println("Failed to parse JSON: " + e.getMessage());
        }
    }

    private static String extractJsonContent(String response) {
        // Remove the leading and trailing ```json and ``` delimiters
        int start = response.indexOf("```json");
        int end = response.lastIndexOf("```");

        if (start != -1 && end != -1) {
            String jsonContent = response.substring(start + 7, end).trim();
            // Clean up the JSON string
            jsonContent = jsonContent.replaceAll("\\\\n", "").replaceAll("\\\\", "");
            return jsonContent;
        }

        return response.trim();
    }
}