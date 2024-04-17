import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Parser_DataFrameTest {

    @Test
    public void noFile() {
        boolean fail = true;
        try {
            DataFrame dataFrame = new DataFrame("");
        } catch (NoSuchFileException e) {
            fail = false;
        }
        if(fail) {
            fail();
        }
    }

    @Test
    public void nonExistingFile() {
        boolean fail = true;
        try {
            DataFrame dataFrame = new DataFrame("test.csv");
        } catch (NoSuchFileException e) {
            fail = false;
        }
        if(fail) {
            fail();
        }
    }

    @Test
    public void wrongExtension() {
        boolean fail = true;
        try {
            DataFrame dataFrame = new DataFrame("resources/file.txt");
        } catch (NoSuchFileException e) {
            fail = false;
        }
        if(fail) {
            fail();
        }
    }

    private String randomString(int length) {
        Random random = new Random();
        char[] chars = new char[length];

        for(int i = 0; i < length; i++) {
            if(random.nextBoolean()) {
                chars[i] = (char) ('a' + random.nextInt(26));
            } else {
                chars[i] = (char) ('A' + random.nextInt(26));
            }
        }

        return String.valueOf(chars);
    }

    @Test
    public void randomCorrect() {
        Random random = new Random();
        Object[][] values = new Object[3][3];
        for(int i = 0; i < values.length; i++) {
            for(int j = 0; j < values[i].length; j++) {
                switch(j) {
                    case 0:
                        values[i][j] = random.nextInt(100) - 200;
                        break;
                    case 1:
                        values[i][j] = randomString(random.nextInt(10) + 5);
                        break;
                    case 2:
                        values[i][j] = random.nextDouble();
                        break;
                }
            }
        }

        try {
            File file = new File(Paths.get("src", "test", "resources", "simple.csv").toAbsolutePath().toString());
            FileWriter writer = new FileWriter(file);
            String[] colsNames = new String[] {"Entier", "String", "Flottant"};
            for(int i = 0; i < colsNames.length; i++) {
                writer.write(colsNames[i]);
                if(i + 1 < colsNames.length) {
                    writer.write(",");
                }
            }
            writer.write("\n");

            for(int i = 0; i < values.length; i++) {
                for(int j = 0; j < values[i].length; j++) {
                    writer.write(values[i][j].toString());
                    if(j + 1 < values[i].length) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }
            writer.close();

            DataFrame dataFrame = new DataFrame(file.getAbsolutePath());
            LinkedHashMap<String, Class<?>> framecols = dataFrame.getColumns();
            List<String> frameColsName = new ArrayList<>(framecols.keySet());
            for(int i = 0; i < frameColsName.size(); i++) {
                if(!frameColsName.get(i).equals(colsNames[i])) {
                    throw new Exception("Nom de colonnes différents");
                }
            }

            List<List<Object>> data = dataFrame.getData();
            for(int i = 0; i < values.length; i++) {
                for(int j = 0; j < values[i].length; j++) {
                    if(!data.get(i).get(j).equals(values[i][j])) {
                        throw new Exception("Different values at col " + colsNames[j] + " line " + (i + 1));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}