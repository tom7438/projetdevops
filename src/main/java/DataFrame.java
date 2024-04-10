import java.util.ArrayList;
import java.util.LinkedHashMap;

import java.util.List;

public class DataFrame {
    List<List<Object>> data;
    LinkedHashMap<String, Class<?>> columns;

    public DataFrame(List<List<Object>> data, LinkedHashMap<String, Class<?>> columns) {
        this.data = data;
        this.columns = columns;
    }

    public List<List<Object>> getData() {
        return data;
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
        //récupérer la colonne et la valeur de la condition
        String[] parts = condition.split(" ");
        String column = parts[0];
        String operator = parts[1];
        int value = Integer.parseInt(parts[2]);

        //récupérer la colonne à partir de son nom
        DataFrame d = select_column(new String[] {column});

        //récupérer les données de la colonne selon la condition
        List<List<Object>> new_data = new ArrayList<>();
        for (int i = 0; i < d.data.size(); i++) {
            switch (operator) {
                case ">" -> {
                    if ((int) d.data.get(i).get(0) > value) {
                        new_data.add(d.data.get(i));
                    }
                }
                case "<" -> {
                    if ((int) d.data.get(i).get(0) < value) {
                        new_data.add(d.data.get(i));
                    }
                }
                case ">=" -> {
                    if ((int) d.data.get(i).get(0) >= value) {
                        new_data.add(d.data.get(i));
                    }
                }
                case "<=" -> {
                    if ((int) d.data.get(i).get(0) <= value) {
                        new_data.add(d.data.get(i));
                    }
                }
                case "==" -> {
                    if ((int) d.data.get(i).get(0) == value) {
                        new_data.add(d.data.get(i));
                    }
                }
                case "!=" -> {
                    if ((int) d.data.get(i).get(0) != value) {
                        new_data.add(d.data.get(i));
                    }
                }
                default -> throw new IllegalArgumentException("Operator not found");
            }
        }
        return new DataFrame(new_data, d.columns);
    }

    /*public LinkedHashMap<String, Class<?>> getColumns() {
        return columns;
    }*/
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
        if(n > data.size()) {
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
}
