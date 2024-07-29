package LAproject;

import LAproject.model.LawParameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Normalizer {
    public static void main(String[] args) {
        File folder = new File("./outputFiles/raw");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (listOfFiles != null && listOfFiles.length >= Configuration.MIN_FILES_TO_NORMALIZE) {
            List<List<LawParameter<?>>> allParameters = new ArrayList<>();

            for (File file : listOfFiles) {
                try (FileReader reader = new FileReader(file)) {
                    Type listType = new TypeToken<List<LawParameter<?>>>() {}.getType();
                    List<LawParameter<?>> parameters = new Gson().fromJson(reader, listType);
                    allParameters.add(parameters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            List<LawParameter<?>> normalizedParameters = normalizeParameters(allParameters);
            String outputPath = "./outputFiles/normalized/parameters" + Instant.now().toString() + ".json";
            writeParametersToJsonFile(normalizedParameters, outputPath);
        } else {
            System.out.println("Not enough files to normalize. At least 4 files are required.");
        }
    }

    private static List<LawParameter<?>> normalizeParameters(List<List<LawParameter<?>>> allParameters) {
        Map<Integer, List<LawParameter<?>>> groupedParameters = new HashMap<>();

        for (List<LawParameter<?>> parameterList : allParameters) {
            for (LawParameter<?> parameter : parameterList) {
                groupedParameters.computeIfAbsent((int)parameter.getLawID(), k -> new ArrayList<>()).add(parameter);
            }
        }

        List<LawParameter<?>> normalizedParameters = new ArrayList<>();
        for (Map.Entry<Integer, List<LawParameter<?>>> entry : groupedParameters.entrySet()) {
            int lawID = entry.getKey();
            List<LawParameter<?>> parameters = entry.getValue();

            String mostFrequentName = findMostFrequentSimilarName(parameters.stream()
                    .map(p -> StringEscapeUtils.unescapeJava(p.getLawName()))
                    .collect(Collectors.toList()));

            Object modeValue = findMode(parameters.stream().map(LawParameter::getLawParameter).collect(Collectors.toList()));
            normalizedParameters.add(new LawParameter<>(mostFrequentName, lawID, modeValue));
        }

        return normalizedParameters;
    }

    private static String findMostFrequentSimilarName(List<String> names) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        Map<String, List<String>> groups = new HashMap<>();

        for (String name : names) {
            boolean added = false;
            for (String key : groups.keySet()) {
                if (levenshtein.apply(key, name) <= 5) { // Limiar de similaridade
                    groups.get(key).add(name);
                    added = true;
                    break;
                }
            }
            if (!added) {
                groups.put(name, new ArrayList<>(Collections.singletonList(name)));
            }
        }

        String mostFrequentName = null;
        int maxCount = 0;

        for (Map.Entry<String, List<String>> entry : groups.entrySet()) {
            int count = entry.getValue().size();
            if (count > maxCount) {
                maxCount = count;
                mostFrequentName = entry.getKey();
            }
        }

        return mostFrequentName;
    }

    private static Object findMode(List<Object> values) {
        Map<Object, Long> frequencyMap = values.stream()
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()));

        return Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static void writeParametersToJsonFile(List<LawParameter<?>> parameters, String fileName) {
        String filePath =  fileName;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(parameters, writer);
            System.out.println("Parameters written to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to write parameters to file: " + e.getMessage());
        }
    }
}
