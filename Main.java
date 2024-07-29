

import LAproject.Extractor;
import LAproject.Normalizer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Execute Extractor 4 times
        for (int i = 1; i <= 4; i++) {
            System.out.println("Running extractor instance " + i + "...");
            try {
                Extractor.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Execute Normalizer
        System.out.println("Running normalizer...");
        try {
            Normalizer.main(new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
