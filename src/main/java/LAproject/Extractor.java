package LAproject;

import LAproject.model.LawParameter;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import LAproject.aiap.ArtificialInteligenceAPI;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Extractor {
    public static void main(String[] args) {
        String lawText = Configuration.TEXT_TO_ANALYZE;

        try {
            String response = ArtificialInteligenceAPI.analyzeLawText(lawText);
            System.out.println("Response: " + response);

            // Extract the JSON part from the response, removing the ```json and ``` delimiters
            String content = extractJsonContent(response);
            System.out.println("Extracted content: " + content);

            // Normalize Unicode escape sequences
            String normalizedContent = normalizeUnicode(content);
            System.out.println("Normalized content: " + normalizedContent);

            // Create a lenient JsonReader
            JsonReader reader = new JsonReader(new StringReader(normalizedContent));
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
            // Define the path for the output file
            String outputPath = "./outputFiles/raw/extration" + Instant.now().toString() + ".json";
            writeParametersToJsonFile(lawParameters, outputPath);
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

    private static String normalizeUnicode(String input) {
        // Convert Unicode escape sequences to characters
        return input.replace("\\u003e", ">")
                .replace("\\u003c", "<")
                .replace("\\u003d", "=")
                .replace("\\u0022", "\"")
                .replace("\\u0027", "'")
                .replace("\\u005b", "[")
                .replace("\\u005d", "]")
                .replace("\\u007b", "{")
                .replace("\\u007d", "}")
                .replace("\\u002e", ".")
                .replace("\\u002c", ",")
                .replace("\\u003a", ":")
                .replace("\\u003b", ";")
                .replace("\\u003f", "?")
                .replace("\\u0021", "!")
                .replace("\\u0025", "%")
                .replace("\\u0028", "(")
                .replace("\\u0029", ")")
                .replace("\\u002d", "-")
                .replace("\\u002f", "/");
    }

    private static void writeParametersToJsonFile(List<LawParameter<?>> parameters, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(parameters, writer);
            System.out.println("Parameters written to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to write parameters to file: " + e.getMessage());
        }
    }
}
