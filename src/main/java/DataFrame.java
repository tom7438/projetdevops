import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

public class DataFrame {
    private final List<List<Object>> data;
    private final LinkedHashMap<String, Class<?>> columns;

    public DataFrame(List<List<Object>> data, LinkedHashMap<String, Class<?>> columns) {
        this.data = data;
        this.columns = columns;
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

    // Méthode pour afficher le DataFrame
    public void afficherDataFrame() {
        System.out.println("DataFrame :");
        for (String columnName : columns.keySet()) {
            System.out.print(columnName + "\t");
        }
        System.out.println();
        for (List<Object> rowData : data) {
            for (Object value : rowData) {
                System.out.print(value.toString() + "\t");
            }
            System.out.println();
        }
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
}