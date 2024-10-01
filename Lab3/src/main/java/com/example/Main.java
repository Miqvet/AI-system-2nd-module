package com.example;

import com.example.data.StudentData;
import com.example.services.CsvReader;
import com.example.services.DataPreprocessor;
import com.example.services.StatisticsVisualizer;

import java.util.List;

import static com.example.regression.LinearRegressionMultipleModels.evaluateModel;
import static com.example.regression.LinearRegressionMultipleModels.trainTestSplit;

public class Main {
    public static void main(String[] args) {
        List<StudentData> studentDataList = CsvReader.readCsv("O:\\Itmo\\5_SEM\\AI system\\lab3\\testData.csv");
        StatisticsVisualizer.visualizeStatistics(studentDataList);

        DataPreprocessor.preprocess(studentDataList);

        // Разделяем данные на обучающую и тестовую выборки (80% на обучение)
        List<List<StudentData>> splitData = trainTestSplit(studentDataList, 0.8);
        List<StudentData> trainData = splitData.get(0);
        List<StudentData> testData = splitData.get(1);

        // Модель 1: Hours Studied, Previous Scores, Extracurricular Activities (без синтетического признака)
        String[] model1Features = {"Hours Studied", "Previous Scores", "Extracurricular Activities"};
        evaluateModel(model1Features, trainData, testData, false);

        // Модель 2: Hours Studied, Sleep Hours, Sample Question Papers Practiced (c учетом синтетического признака)
        String[] model2Features = {"Previous Scores", "Extracurricular Activities", "Sample Question Papers Practiced"};
        evaluateModel(model2Features, trainData, testData, true);

        // Модель 3: Все признаки + синтетический признак (Учебная производительность)
        String[] model3Features = {"Hours Studied", "Previous Scores", "Extracurricular Activities", "Sleep Hours", "Sample Question Papers Practiced"};
        evaluateModel(model3Features, trainData, testData, false);
    }
}