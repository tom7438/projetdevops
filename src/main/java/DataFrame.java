import java.util.LinkedHashMap;
import java.util.List;

public class DataFrame {
    List<List<Object>> data;
    LinkedHashMap<String, Class<?>> columns;

    public DataFrame(List<List<Object>> data, LinkedHashMap<String, Class<?>> columns) {
        this.data = data;
        this.columns = columns;
    }

    /*public List<List<Object>> getData() {
        return data;
    }

    public LinkedHashMap<String, Class<?>> getColumns() {
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
