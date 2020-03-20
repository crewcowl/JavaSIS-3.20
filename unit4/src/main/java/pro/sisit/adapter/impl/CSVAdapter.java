package pro.sisit.adapter.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import pro.sisit.adapter.CSVConverter;
import pro.sisit.adapter.IOAdapter;


public class CSVAdapter<T extends CSVConverter> implements IOAdapter<T> {

    private Class<T> entityType;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final int BUFF_LIMIT = 5000;

    public CSVAdapter(Class<T> entityType, BufferedReader reader,
        BufferedWriter writer) {
        this.entityType = entityType;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public T read(int rowIndex) {
        T entity = null;
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

        try {
            entity = entityType.getDeclaredConstructor().newInstance();
            entity.setCSVLine(text);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Set data error",e);
        }

        return entity;
    }


    @Override
    public int append(T entity) {
        try {
            writer.write(entity.getCSVLine() + "\n");
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
