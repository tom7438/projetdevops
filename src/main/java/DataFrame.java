import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataFrame {
    List<List<Object>> data;
    HashMap<String, Class<?>> columns;

    public DataFrame(List<List<Object>> data, HashMap<String, Class<?>> columns) {
        this.data = data;
        this.columns = columns;
    }

    public List<List<Object>> getData() {
        return data;
    }

    /*public HashMap<String, Class<?>> getColumns() {
        return columns;
    }*/

    public DataFrame select_line(int[] indexes) {
        List<List<Object>> new_data = new ArrayList<>();
        for (int i = 0; i < indexes.length; i++) {
            new_data.add(data.get(indexes[i]));
        }
        return new DataFrame(new_data, columns);
    }

    public DataFrame select_column(String[] column_names) {
        int [] indexes = new int[column_names.length];
        List<List<Object>> new_data = new ArrayList<>();
        HashMap<String, Class<?>> new_columns = new HashMap<>();
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
}
