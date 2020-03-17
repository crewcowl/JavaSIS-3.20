package pro.sisit.adapter.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import pro.sisit.adapter.CSVImpl;
import pro.sisit.adapter.IOAdapter;
import pro.sisit.model.Book;

// 1. TODO: написать реализацию адаптера

public class CSVAdapter<T extends CSVImpl> implements IOAdapter<T> {

    private Class<T> entityType;
    private BufferedReader reader;
    private BufferedWriter writer;


    public CSVAdapter(Class<T> entityType, BufferedReader reader,
        BufferedWriter writer) {
        this.entityType = entityType;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public T read(int rowIndex) {
        int Index = 0;
        T entity = null;
        String text = null;



        try {
            for (int i = 0; i < rowIndex; i++) {
                text = reader.readLine();
                if(text == null) throw new RuntimeException("несуществующая строка");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            entity = entityType.getDeclaredConstructor().newInstance();
            entity.getCSVLine(text);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return entity;
    }


    @Override
    public int append(T entity) {
        int rowIndex = 0;
        try {
            writer.write(entity.setCSVLine());
            writer.flush();
            while (reader.readLine() != null) {
                rowIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowIndex;
    }
}
