import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.regex.Pattern;

public class DataFrame {

    private final List<List<Object>> data;
    private final LinkedHashMap<String, Class<?>> columns;

    private float min;
    private float max;

    public DataFrame(List<List<Object>> data, LinkedHashMap<String, Class<?>> columns) {
        this.data = data;
        this.columns = columns;
    }

    public DataFrame(Object[][] data, LinkedHashMap<String, Class<?>> columns) {
        this.data = new ArrayList<>();
        for (Object[] row : data) {
            List<Object> rowData = new ArrayList<>(Arrays.asList(row));
            this.data.add(rowData);
        }
        this.columns = columns;
    }

    public DataFrame(String fileName) throws NoSuchFileException {
        this.data = new ArrayList<>();
        this.columns = new LinkedHashMap<>();

        String[] extension = fileName.split("\\.");
        if (!extension[extension.length - 1].equals("csv")) {
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

    public int getIndexesColone(String nomColonne) {
        int index = 0;
        for (String columnName : columns.keySet()) {
            if (columnName.equals(nomColonne)) {
                return index;
            }
            index++;
        }
        throw new IllegalArgumentException("Le nom de la colonne est invalide");
    }

    // Méthode pour ajouter une ligne au DataFrame
    public void ajouterLigne(List<Object> ligne) {
        // Validation des données d'entrée
        if (ligne == null || ligne.size() != columns.size()) {
            throw new IllegalArgumentException("La ligne est invalide");
        }
        data.add(new ArrayList<>(ligne));
    }

    // Méthode pour supprimer une ligne du DataFrame
    public void supprimerLigne(int indexLigne) {
        if (indexLigne < 0 || indexLigne >= data.size()) {
            throw new IllegalArgumentException("L'index de la ligne à supprimer est invalide");
        }
        data.remove(indexLigne);
    }

    // Méthode pour supprimer une colonne du DataFrame
    public void supprimerColonne(String nomColonne) {
        if (!columns.containsKey(nomColonne)) {
            throw new IllegalArgumentException("Le nom de la colonne à supprimer est invalide");
        }
        int indexColonne = getIndexesColone(nomColonne);
        columns.remove(nomColonne);
        for (List<Object> rowData : data) {
            rowData.remove(indexColonne);
        }
    }

    // Méthode pour obtenir une valeur du DataFrame
    public Object obtenirValeur(int indexLigne, String nomColonne) {
        if (indexLigne < 0 || indexLigne >= data.size()) {
            throw new IllegalArgumentException("L'index de la ligne à obtenir est invalide");
        }
        if (!columns.containsKey(nomColonne)) {
            throw new IllegalArgumentException("Le nom de la colonne à obtenir est invalide");
        }
        int indexColonne = getIndexesColone(nomColonne);
        return data.get(indexLigne).get(indexColonne);
    }

    public DataFrame select_line(int[] indexes) {
        List<List<Object>> new_data = new ArrayList<>();
        for (int i = 0; i < indexes.length; i++) {
            new_data.add(data.get(indexes[i]));
        }
        return new DataFrame(new_data, columns);
    }

    public DataFrame select_column(String[] column_names) {
        int[] indexes = new int[column_names.length];
        List<List<Object>> new_data = new ArrayList<>();
        LinkedHashMap<String, Class<?>> new_columns = new LinkedHashMap<>();
        for (int i = 0; i < column_names.length; i++) {
            indexes[i] = -1;
            for (int j = 0; j < data.get(0).size(); j++) {
                if (column_names[i].equals(columns.keySet().toArray()[j])) {
                    indexes[i] = j;
                    new_columns.put(column_names[i], columns.get(column_names[i]));
                    break;
                }
            }
            if (indexes[i] == -1) {
                throw new IllegalArgumentException("Column not found");
            }
        }
        for (List<Object> line : data) {
            List<Object> new_line = new ArrayList<>();
            for (int index : indexes) {
                new_line.add(line.get(index));
            }
            new_data.add(new_line);
        }

        return new DataFrame(new_data, new_columns);
    }

    //Fonction de sélection qui prend en entrée un booléen du type "A > 2" et retourne un DataFrame contenant les lignes pour lesquelles la condition est vraie.
    public DataFrame select(String condition) {
        Pattern numbers = Pattern.compile("-?[0-9]+");
        Pattern floats = Pattern.compile("-?[0-9]+\\.[0-9]+");
        //récupérer la colonne et la valeur de la condition
        String[] brutValues = condition.split("[=!><]+");
        int column = getIndexesColone(brutValues[0].trim());
        String operator = condition.replaceAll("[^=!<>]", "");
        String brutValue = brutValues[1].trim();

        final Class<?> columnType = this.columns.get(brutValues[0].trim());
        if(numbers.matcher(brutValue).matches() && !columnType.equals(Integer.class)) {
            throw new IllegalArgumentException("Value should be integer");
        } else if (floats.matcher(brutValue).matches() && !columnType.equals(Double.class)) {
            throw new IllegalArgumentException("Value should be float");
        }

        //récupérer la colonne à partir de son nom
        DataFrame d = select_column(columns.keySet().toArray(new String[0]));

        //récupérer les données de la colonne selon la condition
        List<List<Object>> new_data = new ArrayList<>();
        if(numbers.matcher(brutValue).matches()) {
            final int value = Integer.parseInt(brutValue);
            for (int i = 0; i < d.data.size(); i++) {
                switch (operator) {
                    case ">" -> {
                        if ((int) d.data.get(i).get(column) > value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "<" -> {
                        if ((int) d.data.get(i).get(column) < value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case ">=" -> {
                        if ((int) d.data.get(i).get(column) >= value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "<=" -> {
                        if ((int) d.data.get(i).get(column) <= value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "==" -> {
                        if ((int) d.data.get(i).get(column) == value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "!=" -> {
                        if ((int) d.data.get(i).get(column) != value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    default -> throw new IllegalArgumentException("Int Operator not found");
                }
            }
        } else if (floats.matcher(brutValue).matches()) {
            final double value = Double.parseDouble(brutValue);
            for (int i = 0; i < d.data.size(); i++) {
                switch (operator) {
                    case ">" -> {
                        if ((double) d.data.get(i).get(column) > value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "<" -> {
                        if ((double) d.data.get(i).get(column) < value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case ">=" -> {
                        if ((double) d.data.get(i).get(column) >= value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "<=" -> {
                        if ((double) d.data.get(i).get(column) <= value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "==" -> {
                        if ((double) d.data.get(i).get(column) == value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "!=" -> {
                        if ((double) d.data.get(i).get(column) != value) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    default -> throw new IllegalArgumentException("Float Operator not found");
                }
            }
        } else {
            if (!columnType.equals(String.class)) {
                throw new IllegalArgumentException("Value should be string");
            }
            for (int i = 0; i < d.data.size(); i++) {
                switch (operator) {
                    case "==" -> {
                        if (d.data.get(i).get(column).toString().equals(brutValue)) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    case "!=" -> {
                        if (!d.data.get(i).get(column).toString().equals(brutValue)) {
                            new_data.add(d.data.get(i));
                        }
                    }
                    default -> throw new IllegalArgumentException("String Operator not found");
                }
            }
        }
        return new DataFrame(new_data, this.columns);
    }

    public void display() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        // Trouver la largeur maximale de chaque colonne
        int[] maxWidths = getMaxWidth();

        StringBuilder sb = new StringBuilder();

        // En-têtes
        sb.append(String.format("%4s", "")).append("\t"); // En-tête vide pour la première colonne
        int index = 0;
        for (String key : columns.keySet()) {
            sb.append(String.format("%" + (maxWidths[index++] + 1) + "s", key)).append("\t");
        }
        sb.append("\n");

        // Données
        int i = 0;
        for (List<Object> row : data) {
            sb.append(String.format("%4d", i++)).append("\t"); // Index des lignes
            int j = 0;
            for (Object obj : row) {
                sb.append(String.format("%" + (maxWidths[j++] + 1) + "s", obj)).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void displayFirstLines(int n) {
        if (n > data.size()) {
            System.out.println("DataFrame a moins de " + n + " lignes");
            n = data.size();
        }
        // Trouver la largeur maximale de chaque colonne
        int[] maxWidths = getMaxWidth();

        System.out.println("Voici les " + n + " premières lignes:");

        StringBuilder sb = getEnTete();

        // Données
        int i = 0;
        for (List<Object> row : data) {
            sb.append(String.format("%4d", i++)).append("\t"); // Index des lignes
            int j = 0;
            for (Object obj : row) {
                sb.append(String.format("%" + (maxWidths[j++] + 1) + "s", obj)).append("\t");
            }
            sb.append("\n");
            if (i == n) {
                break;
            }
        }
        System.out.println(sb.toString());
    }

    public void displayLastLines(int n) {
        if (n > data.size()) {
            System.out.println("DataFrame a moins de " + n + " lignes");
            n = data.size();
        }
        // Trouver la largeur maximale de chaque colonne
        int[] maxWidths = getMaxWidth();

        System.out.println("Voici les " + n + " dernières lignes:");

        StringBuilder sb = getEnTete();

        // Données
        int i = 0;
        for (int j = data.size() - n; j < data.size(); j++) {
            List<Object> row = data.get(j);
            sb.append(String.format("%4d", i++)).append("\t"); // Index des lignes
            int k = 0;
            for (Object obj : row) {
                sb.append(String.format("%" + (maxWidths[k++] + 1) + "s", obj)).append("\t");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private int[] getMaxWidth() {
        int[] maxWidths = new int[columns.size()];
        for (List<Object> row : data) {
            for (int i = 0; i < row.size(); i++) {
                maxWidths[i] = Math.max(maxWidths[i], String.valueOf(row.get(i)).length());
            }
        }
        return maxWidths;
    }

    public StringBuilder getEnTete() {
        int[] maxWidths = getMaxWidth();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%4s", "")).append("\t"); // En-tête vide pour la première colonne
        int index = 0;
        for (String key : columns.keySet()) {
            sb.append(String.format("%" + (maxWidths[index++] + 1) + "s", key)).append("\t");
        }
        sb.append("\n");
        return sb;
    }

    public float min(String col) {
        int index = 0;
        for (String key : columns.keySet()) {
            if (key.equals(col)) {
                break;
            }
            index++;
        }
        if (index == columns.size()) {
            System.err.println("La colonne " + col + " n'existe pas");
            return -1;
        }
        // Check if the column is numeric
        if (!columns.get(col).getSimpleName().equals("Float") && !columns.get(col).getSimpleName().equals("Integer") && !columns.get(col).getSimpleName().equals("Double") && !columns.get(col).getSimpleName().equals("Long")) {
            // Afficher le type de la colonne
            System.err.println("type = " + columns.get(col).getSimpleName() + " pour la colonne " + col);
            System.err.println("La colonne " + col + " n'est pas numérique");
            return -2;
        }

        min = Float.MAX_VALUE;

        for (List<Object> row : data) {
            Object obj = row.get(index);
            float value = ((Number) obj).floatValue();
            if (value < min) {
                min = value;
            }
        }

        return min;
    }

    public float min(int numCol) {
        if (numCol >= columns.size() || numCol < 0) {
            System.out.println("La colonne " + numCol + " n'existe pas");
            return -1;
        }
        // Check if the column is numeric
        List<Object> row1 = data.get(0);
        if (!row1.get(numCol).getClass().getSimpleName().equals("Float") && !row1.get(numCol).getClass().getSimpleName().equals("Integer") && !row1.get(numCol).getClass().getSimpleName().equals("Double") && !row1.get(numCol).getClass().getSimpleName().equals("Long")) {
            System.out.println("La colonne numéro " + numCol + " n'est pas numérique");
            return -2;
        }

        min = Float.MAX_VALUE;

        for (List<Object> row : data) {
            Object obj = row.get(numCol);
            float value = ((Number) obj).floatValue();
            if (value < min) {
                min = value;
            }
        }

        return min;
    }

    public float max(String col) {
        int index = 0;
        for (String key : columns.keySet()) {
            if (key.equals(col)) {
                break;
            }
            index++;
        }

        if (index == columns.size()) {
            System.out.println("La colonne " + col + " n'existe pas");
            return -1;
        }

        // Check if the column is numeric
        if (!columns.get(col).getSimpleName().equals("Float") && !columns.get(col).getSimpleName().equals("Integer") && !columns.get(col).getSimpleName().equals("Double") && !columns.get(col).getSimpleName().equals("Long")) {
            // Afficher le type de la colonne
            System.err.println("type = " + columns.get(col).getSimpleName() + " pour la colonne " + col);
            System.err.println("La colonne " + col + " n'est pas numérique");
            return -2;
        }

        max = Float.MIN_VALUE;

        for (List<Object> row : data) {
            Object obj = row.get(index);
            float value = ((Number) obj).floatValue();
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    public float max(int numCol) {
        if (numCol >= columns.size() || numCol < 0) {
            System.out.println("La colonne " + numCol + " n'existe pas");
            return -1;
        }

        // Check if the column is numeric
        List<Object> row1 = data.get(0);
        if (!row1.get(numCol).getClass().getSimpleName().equals("Float") && !row1.get(numCol).getClass().getSimpleName().equals("Integer") && !row1.get(numCol).getClass().getSimpleName().equals("Double") && !row1.get(numCol).getClass().getSimpleName().equals("Long")) {
            System.out.println("La colonne numéro " + numCol + " n'est pas numérique");
            return -2;
        }

        max = Float.MIN_VALUE;

        for (List<Object> row : data) {
            Object obj = row.get(numCol);
            float value = ((Number) obj).floatValue();
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    public float mean(String col) {
        int index = 0;
        for (String key : columns.keySet()) {
            if (key.equals(col)) {
                break;
            }
            index++;
        }

        if (index == columns.size()) {
            System.out.println("La colonne " + col + " n'existe pas");
            return -1;
        }

        // Check if the column is numeric
        if (!columns.get(col).getSimpleName().equals("Float") && !columns.get(col).getSimpleName().equals("Integer") && !columns.get(col).getSimpleName().equals("Double") && !columns.get(col).getSimpleName().equals("Long")) {
            // Afficher le type de la colonne
            System.err.println("type = " + columns.get(col).getSimpleName() + " pour la colonne " + col);
            System.err.println("La colonne " + col + " n'est pas numérique");
            return -2;
        }

        float sum = 0;
        for (List<Object> row : data) {
            Object obj = row.get(index);
            float value = ((Number) obj).floatValue();
            sum += value;
        }

        return sum / data.size();
    }

    public float mean(int numCol) {
        if (numCol >= columns.size() || numCol < 0) {
            System.out.println("La colonne " + numCol + " n'existe pas");
            return -1;
        }

        // Check if the column is numeric
        List<Object> row1 = data.get(0);
        if (!row1.get(numCol).getClass().getSimpleName().equals("Float") && !row1.get(numCol).getClass().getSimpleName().equals("Integer") && !row1.get(numCol).getClass().getSimpleName().equals("Double") && !row1.get(numCol).getClass().getSimpleName().equals("Long")) {
            System.out.println("La colonne numéro " + numCol + " n'est pas numérique");
            return -2;
        }

        float sum = 0;
        for (List<Object> row : data) {
            Object obj = row.get(numCol);
            float value = ((Number) obj).floatValue();
            sum += value;
        }

        // arrondir à 1 chiffre après la virgule
        float res = sum / data.size();
        res = (float) (Math.round(res * 10.0) / 10.0);

        return res;
    }

    public float sum(String col) {
        int index = 0;
        for (String key : columns.keySet()) {
            if (key.equals(col)) {
                break;
            }
            index++;
        }

        if (index == columns.size()) {
            System.out.println("La colonne " + col + " n'existe pas");
            return -1;
        }

        // Check if the column is numeric
        if (!columns.get(col).getSimpleName().equals("Float") && !columns.get(col).getSimpleName().equals("Integer") && !columns.get(col).getSimpleName().equals("Double") && !columns.get(col).getSimpleName().equals("Long")) {
            // Afficher le type de la colonne
            System.err.println("type = " + columns.get(col).getSimpleName() + " pour la colonne " + col);
            System.err.println("La colonne " + col + " n'est pas numérique");
            return -2;
        }

        float sum = 0;
        for (List<Object> row : data) {
            Object obj = row.get(index);
            float value = ((Number) obj).floatValue();
            sum += value;
        }
        return sum;
    }

    public float sum(int numCol) {
        if (numCol >= columns.size() || numCol < 0) {
            System.out.println("La colonne " + numCol + " n'existe pas");
            return -1;
        }

        // Check if the column is numeric
        List<Object> row1 = data.get(0);
        if (!row1.get(numCol).getClass().getSimpleName().equals("Float") && !row1.get(numCol).getClass().getSimpleName().equals("Integer") && !row1.get(numCol).getClass().getSimpleName().equals("Double") && !row1.get(numCol).getClass().getSimpleName().equals("Long")) {
            System.out.println("La colonne numéro " + numCol + " n'est pas numérique");
            return -2;
        }

        float sum = 0;
        for (List<Object> row : data) {
            Object obj = row.get(numCol);
            float value = ((Number) obj).floatValue();
            sum += value;
        }
        return sum;
    }

    private class DataFrameParser {

        private final Scanner scanner;

        private DataFrameParser(Scanner scanner) {
            this.scanner = scanner;
            findColumnsName();
            fillData();
        }

        private void findColumnsName() {
            String[] names = scanner.nextLine().trim().split(",");
            for (String name : names) {
                columns.put(name, null);
            }
        }

        private void fillData() {
            boolean typeFound = false;
            List<String> colsNames = new ArrayList<>(columns.keySet());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] datas = line.split(",");

                if (!typeFound) {
                    int j = 0;
                    for (String col : columns.keySet()) {
                        columns.put(col, findDataType(datas[j]));
                        j++;
                    }
                    typeFound = true;
                }

                List<Object> lineData = new ArrayList<>();
                int i = 0;
                for (String data : datas) {
                    Class<?> colClass = columns.get(colsNames.get(i));
                    if (colClass == Integer.class) {
                        lineData.add(Integer.parseInt(data));
                    } else if (colClass == Double.class) {
                        lineData.add(Double.parseDouble(data));
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
            Pattern floats = Pattern.compile("-?[0-9]+\\.[0-9]+");

            if (numbers.matcher(data).matches()) {
                return Integer.class;
            } else if (floats.matcher(data).matches()) {
                return Double.class;
            }
            return String.class;
        }
    }
}
