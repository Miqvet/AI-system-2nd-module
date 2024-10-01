package com.example.regression;

import com.example.data.StudentData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinearRegressionMultipleModels {

    // Метод для извлечения признаков из списка данных
    private static double[][] extractFeatures(List<StudentData> dataList, String[] selectedFeatures, boolean includeSyntheticFeature) {
        int n = dataList.size();
        int m = selectedFeatures.length + (includeSyntheticFeature ? 1 : 0); // Количество признаков, включая синтетический
        double[][] features = new double[n][m];

        for (int i = 0; i < n; i++) {
            StudentData student = dataList.get(i);
            int featureIndex = 0;
            for (String feature : selectedFeatures) {
                switch (feature) {
                    case "Hours Studied":
                        features[i][featureIndex] = student.getHoursStudied();
                        break;
                    case "Previous Scores":
                        features[i][featureIndex] = student.getPreviousScope();
                        break;
                    case "Extracurricular Activities":
                        features[i][featureIndex] = student.isExtracurricular() ? 1.0 : 0.0;
                        break;
                    case "Sleep Hours":
                        features[i][featureIndex] = student.getSleepHours();
                        break;
                    case "Sample Question Papers Practiced":
                        features[i][featureIndex] = student.getSampleQuestionPapers();
                        break;
                }
                featureIndex++;
            }
            // Добавляем синтетический признак: "Учебная производительность" = Hours Studied * Sleep Hours
            if (includeSyntheticFeature) {
                features[i][selectedFeatures.length] = student.getHoursStudied() * student.getSleepHours();
            }
        }
        return features;
    }

    // Метод для обучения модели и оценки ее результатов
    public static void evaluateModel(String[] selectedFeatures, List<StudentData> trainData, List<StudentData> testData, boolean addSyntheticFeature) {
        // Подготовка данных
        double[][] trainFeatures = extractFeatures(trainData, selectedFeatures, addSyntheticFeature);
        double[] trainLabels = getTargetValues(trainData);

        double[][] testFeatures = extractFeatures(testData, selectedFeatures, addSyntheticFeature);
        double[] testLabels = getTargetValues(testData);

        // Создаем и обучаем модель линейной регрессии
        LinearRegression model = new LinearRegression();
        model.fit(trainFeatures, trainLabels);

        // Оцениваем модель
        double mse = model.meanSquaredError(testFeatures, testLabels);
        double rSquared = model.rSquared(testFeatures, testLabels);

        // Выводим результаты
        System.out.println("Среднеквадратическая ошибка (MSE): " + String.format("%.4f", mse));
        System.out.println("R^2 коэффициент: " + String.format("%.4f", rSquared));

        // Вывод коэффициентов
        double[] coefficients = model.getCoefficients();
        System.out.println("Coefficients:");
        for (int i = 0; i < coefficients.length; i++) {
            System.out.println("b" + i + ": " + String.format("%.4f", coefficients[i]));
        }
        System.out.println();
    }

    // Метод для получения целевых значений (Performance Index)
    public static double[] getTargetValues(List<StudentData> data) {
        return data.stream().mapToDouble(StudentData::getPerformanceIndex).toArray();
    }

    // Разделение данных на обучающую и тестовую выборки (например, 80% для обучения, 20% для теста)
    public static List<List<StudentData>> trainTestSplit(List<StudentData> data, double trainSize) {
        List<StudentData> trainData = new ArrayList<>();
        List<StudentData> testData = new ArrayList<>();
        Random rand = new Random();

        for (StudentData student : data) {
            if (rand.nextDouble() < trainSize) {
                trainData.add(student);
            } else {
                testData.add(student);
            }
        }

        List<List<StudentData>> splitData = new ArrayList<>();
        splitData.add(trainData);
        splitData.add(testData);
        return splitData;
    }
}