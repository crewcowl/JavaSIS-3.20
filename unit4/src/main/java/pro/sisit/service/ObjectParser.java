package pro.sisit.service;

import java.util.ArrayList;
import java.util.Collections;

public class ObjectParser {

    private final String DELIMITER = ";";

    public ArrayList<String> getObject (String text) {
        String [] data = text.split(DELIMITER);
        ArrayList<String> obj = new ArrayList<>();
        Collections.addAll(obj, data);
        return obj;
    }

    public String getCSV (ArrayList<String> obj) {
        StringBuilder text = new StringBuilder();
        for(String object: obj) {
            text.append(object).append(DELIMITER);
        }
        return text.toString();
    }
}
