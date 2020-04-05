package pro.sisit.adapter.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import pro.sisit.adapter.CSVConverter;
import pro.sisit.adapter.IOAdapter;
import pro.sisit.factory.ClassFactory;
import pro.sisit.model.Entity;
import pro.sisit.service.ObjectParser;


public class CSVAdapter<T extends CSVConverter> implements IOAdapter<T> {

    private Class<T> entityType;
    private BufferedReader reader;
    private BufferedWriter writer;

    private final int BUFF_LIMIT = 5000;
    private final ObjectParser objParser = new ObjectParser();
    private final ClassFactory classFactory = new ClassFactory();

    public CSVAdapter(Class<T> entityType, BufferedReader reader,
        BufferedWriter writer) {
        this.entityType = entityType;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public Entity read(int rowIndex) {
        Entity entity;
        String text = null;

        try {
            reader.mark(BUFF_LIMIT);
            for (int i = 0; i < rowIndex; i++) {
                text = reader.readLine();
                if(text == null) throw new RuntimeException("null is read");
            }
            reader.reset();
        } catch (IOException e) {
            throw new RuntimeException("read error",e);
        }

            entity = classFactory.newT(entityType);
            entity.setCSVLine(objParser.getObject(text));

        return entity;
    }


    @Override
    public int append(T entity) {
        try {
            writer.write(objParser.getCSV(entity.getCSVLine()) + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("write error",e);
        }
        return getIndex();
    }

    public int getIndex () {
        int rowIndex = 0;
        try {
            reader.mark(BUFF_LIMIT);
            while (reader.readLine()!=null) {
                rowIndex++;
            }
            reader.reset();
        } catch (IOException e) {
            throw new RuntimeException("getIndex Error",e);
        }
        return rowIndex;
    }
}
