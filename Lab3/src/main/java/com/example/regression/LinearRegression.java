package com.example.regression;

import com.example.data.StudentData;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class LinearRegression {
    // Метод для получения коэффициентов
    private double[] coefficients;

    public void fit(double[][] X, double[] y) {
        int n = X.length;       // Количество примеров
        int m = X[0].length;    // Количество признаков

        // Массив для хранения коэффициентов b1, b2, ...
        this.coefficients = new double[m + 1]; // Включаем b0 (свободный член)

        // Вычисляем средние значения для каждого признака и целевой переменной
        double[] featureMeans = new double[m];
        double targetMean = 0.0;
        for (int i = 0; i < n; i++) {
            targetMean += y[i];
            for (int j = 0; j < m; j++) {
                featureMeans[j] += X[i][j];
            }
        }
        targetMean /= n;
        for (int j = 0; j < m; j++) {
            featureMeans[j] /= n;
        }

        // Вычисляем коэффициенты b1, b2, ...
        for (int j = 0; j < m; j++) {
            double numerator = 0.0;
            double denominator = 0.0;
            for (int i = 0; i < n; i++) {
                numerator += (X[i][j] - featureMeans[j]) * (y[i] - targetMean);
                denominator += (X[i][j] - featureMeans[j]) * (X[i][j] - featureMeans[j]);
            }
            coefficients[j + 1] = numerator / denominator;
        }

        // Вычисляем свободный член a
        double a = targetMean;
        for (int j = 0; j < m; j++) {
            a -= coefficients[j + 1] * featureMeans[j];
        }
        coefficients[0] = a; // Свободный член a
    }

    // Метод для предсказания значений на основе признаков X
    public double[] predict(double[][] X) {
        int n = X.length;
        int m = X[0].length;
        double[] predictions = new double[n];

        for (int i = 0; i < n; i++) {
            double prediction = coefficients[0]; // b0 (свободный член)
            for (int j = 0; j < m; j++) {
                prediction += coefficients[j + 1] * X[i][j]; // b1*x1, b2*x2, ...
            }
            predictions[i] = prediction;
        }
        return predictions;
    }

    // Метод для вычисления среднего квадратического отклонения (MSE)
    public double meanSquaredError(double[][] X, double[] yActual) {
        double[] yPredicted = predict(X);
        double sumSquaredErrors = 0.0;
        for (int i = 0; i < yActual.length; i++) {
            double error = yPredicted[i] - yActual[i];
            sumSquaredErrors += error * error;
        }
        return sumSquaredErrors / yActual.length;
    }

    // Метод для вычисления коэффициента детерминации (R^2)
    public double rSquared(double[][] X, double[] yActual) {
        double[] yPredicted = predict(X);
        double mean = Arrays.stream(yActual).average().orElse(0.0);
        double totalSumSquares = Arrays.stream(yActual).map(val -> Math.pow(val - mean, 2)).sum();
        double residualSumSquares = 0.0;
        for (int i = 0; i < yActual.length; i++) {
            residualSumSquares += Math.pow(yActual[i] - yPredicted[i], 2);
        }
        return 1 - (residualSumSquares / totalSumSquares);
    }

}
