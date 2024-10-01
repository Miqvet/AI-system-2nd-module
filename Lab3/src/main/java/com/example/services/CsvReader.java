package com.example.services;
import com.example.data.StudentData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<StudentData> readCsv(String filePath) {
        List<StudentData> studentDataList = new ArrayList<>();
        String line = "";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // Пропустить заголовок (первую строку)
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                // Разделение строки по запятым
                String[] values = line.split(",");

                // Преобразование строки к соответствующим типам
                short hoursStudied = Short.parseShort(values[0]);
                short previousScore = Short.parseShort(values[1]);
                boolean extracurricular = values[2].equalsIgnoreCase("Yes");
                short sleepHours = Short.parseShort(values[3]);
                short sampleQuestionPapers = Short.parseShort(values[4]);
                float performanceIndex = Float.parseFloat(values[5]);

                // Создание объекта StudentData и добавление его в список
                StudentData studentData = new StudentData(hoursStudied, previousScore, extracurricular, sleepHours, sampleQuestionPapers, performanceIndex);
                studentDataList.add(studentData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studentDataList;
    }
}
