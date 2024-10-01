package com.example.services;

import com.example.data.StudentData;

import java.util.ArrayList;
import java.util.List;

public class DataPreprocessor {

    // Метод для обработки пропущенных значений
    public static void handleMissingValues(List<StudentData> studentDataList) {
        // Средние значения по столбцам
        double meanHoursStudied = studentDataList.stream()
                .mapToDouble(StudentData::getHoursStudied)
                .average().orElse(0.0);
        double meanPreviousScope = studentDataList.stream()
                .mapToDouble(StudentData::getPreviousScope)
                .average().orElse(0.0);
        double meanSleepHours = studentDataList.stream()
                .mapToDouble(StudentData::getSleepHours)
                .average().orElse(0.0);
        double meanSamplePapers = studentDataList.stream()
                .mapToDouble(StudentData::getSampleQuestionPapers)
                .average().orElse(0.0);
        double meanPerformanceIndex = studentDataList.stream()
                .mapToDouble(StudentData::getPerformanceIndex)
                .average().orElse(0.0);

        for (StudentData student : studentDataList) {
            // Заменяем отсутствующие значения на средние
            if (student.getHoursStudied() == 0) {
                student.setHoursStudied((short) meanHoursStudied);
            }
            if (student.getPreviousScope() == 0) {
                student.setPreviousScope((short) meanPreviousScope);
            }
            if (student.getSleepHours() == 0) {
                student.setSleepHours((short) meanSleepHours);
            }
            if (student.getSampleQuestionPapers() == 0) {
                student.setSampleQuestionPapers((short) meanSamplePapers);
            }
            if (student.getPerformanceIndex() == 0.0) {
                student.setPerformanceIndex((float) meanPerformanceIndex);
            }
        }
    }

    // Метод для нормализации данных
    public static void normalizeData(List<StudentData> studentDataList) {
        // Найти минимальные и максимальные значения для каждого столбца
        short minHoursStudied = (short) studentDataList.stream()
                .mapToDouble(StudentData::getHoursStudied)
                .min().orElse(0);
        short maxHoursStudied = (short) studentDataList.stream()
                .mapToDouble(StudentData::getHoursStudied)
                .max().orElse(1); // избегаем деления на 0

        short minPreviousScope = (short) studentDataList.stream()
                .mapToDouble(StudentData::getPreviousScope)
                .min().orElse(0);
        short maxPreviousScope = (short) studentDataList.stream()
                .mapToDouble(StudentData::getPreviousScope)
                .max().orElse(1);

        short minSleepHours = (short) studentDataList.stream()
                .mapToDouble(StudentData::getSleepHours)
                .min().orElse(0);
        short maxSleepHours = (short) studentDataList.stream()
                .mapToDouble(StudentData::getSleepHours)
                .max().orElse(1);

        short minSamplePapers = (short) studentDataList.stream()
                .mapToDouble(StudentData::getSampleQuestionPapers)
                .min().orElse(0);
        short maxSamplePapers = (short) studentDataList.stream()
                .mapToDouble(StudentData::getSampleQuestionPapers)
                .max().orElse(1);

        float minPerformanceIndex = (float) studentDataList.stream()
                .mapToDouble(StudentData::getPerformanceIndex)
                .min().orElse(0.0);
        float maxPerformanceIndex = (float) studentDataList.stream()
                .mapToDouble(StudentData::getPerformanceIndex)
                .max().orElse(1.0);

        // Нормализация данных
        for (StudentData student : studentDataList) {
            student.setHoursStudied(normalize(student.getHoursStudied(), minHoursStudied, maxHoursStudied));
            student.setPreviousScope(normalize(student.getPreviousScope(), minPreviousScope, maxPreviousScope));
            student.setSleepHours(normalize(student.getSleepHours(), minSleepHours, maxSleepHours));
            student.setSampleQuestionPapers(normalize(student.getSampleQuestionPapers(), minSamplePapers, maxSamplePapers));
            student.setPerformanceIndex(normalize(student.getPerformanceIndex(), minPerformanceIndex, maxPerformanceIndex));
        }
    }

    private static float normalize(float value, float min, float max) {
        if (max - min == 0.0f) {
            return value;
        }
        return (value - min) / (max - min);
    }
    public static void preprocess(List<StudentData> studentDataList) {
        handleMissingValues(studentDataList);
        normalizeData(studentDataList);
    }
}
