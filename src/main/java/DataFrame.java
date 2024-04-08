import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataFrame {
    private final List<List<Object>> data;
    private final LinkedHashMap<String, Class<?>> columns;

    public DataFrame(List<List<Object>> data, LinkedHashMap<String, Class<?>> columns) {
        this.data = new ArrayList<>(data);
        this.columns = columns;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public LinkedHashMap<String, Class<?>> getColumns() {
        return columns;
    }

    public int getIndexesColone(String nomColonne) {
//        if (!columns.containsKey(nomColonne)) {
//            throw new IllegalArgumentException("Le nom de la colonne est invalide");
//        } else {
            int indexColonne = 0;
            for (String columnName : columns.keySet()) {
                if (columnName.equals(nomColonne)) {
                    return indexColonne;
                }
                indexColonne++;
            }
//        }
        return -1;
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
        if (ligne.size() != columns.size()) {
            throw new IllegalArgumentException("La ligne ne contient pas le bon nombre de colonnes");
        }
        data.add(ligne);
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
        // Pour chaque ligne, créer une copie modifiable et supprimer l'élément correspondant à l'index de la colonne
        for (int i = 0; i < data.size(); i++) {
            List<Object> ligne = new ArrayList<>(data.get(i));
            ligne.remove(indexColonne);
            data.set(i, ligne); // Mettre à jour la ligne dans la liste de données
        }
        columns.remove(nomColonne);
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