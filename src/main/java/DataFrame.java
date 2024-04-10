import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFrame {

    private class DataFrameParser {

        private final Scanner scanner;

        private DataFrameParser(Scanner scanner) {
            this.scanner = scanner;
            findColumnsName();
            fillData();
        }

        private void findColumnsName() {
            String[] names = scanner.nextLine().trim().split(",");
            for(String name : names) {
                columns.put(name, null);
            }
        }

        private void fillData() {
            boolean typeFound = false;
            List<String> colsNames = new ArrayList<>(columns.keySet());

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] datas = line.split(",");

                if(!typeFound) {
                    int j = 0;
                    for(String col : columns.keySet()) {
                        columns.put(col, findDataType(datas[j]));
                        j++;
                    }
                    typeFound = true;
                }

                List<Object> lineData = new ArrayList<>();
                int i = 0;
                for(String data : datas) {
                    Class<?> colClass = columns.get(colsNames.get(i));
                    if(colClass == Integer.class) {
                        lineData.add(Integer.parseInt(data));
                    } else if(colClass == Float.class) {
                        lineData.add(Float.parseFloat(data));
                    } else {
                        lineData.add(data);
                    }
                    i++;
                }
                data.add(lineData);
            }
        }

        private Class<?> findDataType(String data) {
            Pattern numbers = Pattern.compile("-?[0-9]+");
            Pattern floats = Pattern.compile("-?[0-9]+.[0-9]+");

            if(numbers.matcher(data).matches()) {
                return Integer.class;
            } else if(floats.matcher(data).matches()) {
                return Float.class;
            }
            return String.class;
        }
    }

    List<List<Object>> data;
    LinkedHashMap<String, Class<?>> columns;

    public DataFrame(String fileName) throws NoSuchFileException {
        this.data = new ArrayList<>();
        this.columns = new LinkedHashMap<>();

        String[] extension = fileName.split("\\.");
        if(!extension[extension.length-1].equals("csv")) {
            throw new NoSuchFileException("Wrong file extension, should be csv");
        }

        File file = new File(fileName);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new NoSuchFileException(e.getMessage());
        }
        new DataFrameParser(scanner);
    }

    public List<List<Object>> getData() {
        return data;
    }

    public LinkedHashMap<String, Class<?>> getColumns() {
        return columns;
    }
}
