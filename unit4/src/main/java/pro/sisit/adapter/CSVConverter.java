package pro.sisit.adapter;

import java.util.ArrayList;

public interface CSVConverter {

    void setCSVLine (ArrayList<String> text);
    ArrayList<String> getCSVLine();
}
