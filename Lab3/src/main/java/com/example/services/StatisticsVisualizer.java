package com.example.services;

import com.example.data.StudentData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsVisualizer extends JFrame {

    public StatisticsVisualizer(String title, List<StudentData> studentDataList) {
        super(title);
        JFreeChart barChart = createChart(studentDataList);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Метод для создания диаграммы
    private JFreeChart createChart(List<StudentData> studentDataList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Добавляем данные в набор для каждой метрики
        dataset.addValue(studentDataList.size(), "Count", "Total Students");

        // Среднее значение
        dataset.addValue(calculateMeanHoursStudied(studentDataList), "Mean", "Hours Studied");
        dataset.addValue(calculateMeanPreviousScore(studentDataList), "Mean", "Previous Scores");
        dataset.addValue(calculateMeanSleepHours(studentDataList), "Mean", "Sleep Hours");
        dataset.addValue(calculateMeanSamplePapers(studentDataList), "Mean", "Sample Papers Practiced");
        dataset.addValue(calculateMeanPerformanceIndex(studentDataList), "Mean", "Performance Index");

        // Стандартное отклонение
        dataset.addValue(calculateStdDevHoursStudied(studentDataList), "Std Dev", "Hours Studied");
        dataset.addValue(calculateStdDevPreviousScore(studentDataList), "Std Dev", "Previous Scores");
        dataset.addValue(calculateStdDevSleepHours(studentDataList), "Std Dev", "Sleep Hours");
        dataset.addValue(calculateStdDevSamplePapers(studentDataList), "Std Dev", "Sample Papers Practiced");
        dataset.addValue(calculateStdDevPerformanceIndex(studentDataList), "Std Dev", "Performance Index");

        // Минимум
        dataset.addValue(Collections.min(extractHoursStudied(studentDataList)), "Min", "Hours Studied");
        dataset.addValue(Collections.min(extractPreviousScores(studentDataList)), "Min", "Previous Scores");
        dataset.addValue(Collections.min(extractSleepHours(studentDataList)), "Min", "Sleep Hours");
        dataset.addValue(Collections.min(extractSamplePapers(studentDataList)), "Min", "Sample Papers Practiced");
        dataset.addValue(Collections.min(extractPerformanceIndex(studentDataList)), "Min", "Performance Index");

        // Максимум
        dataset.addValue(Collections.max(extractHoursStudied(studentDataList)), "Max", "Hours Studied");
        dataset.addValue(Collections.max(extractPreviousScores(studentDataList)), "Max", "Previous Scores");
        dataset.addValue(Collections.max(extractSleepHours(studentDataList)), "Max", "Sleep Hours");
        dataset.addValue(Collections.max(extractSamplePapers(studentDataList)), "Max", "Sample Papers Practiced");
        dataset.addValue(Collections.max(extractPerformanceIndex(studentDataList)), "Max", "Performance Index");

        // Квантили
        dataset.addValue(calculateQuantile(extractHoursStudied(studentDataList), 0.25), "25th Percentile", "Hours Studied");
        dataset.addValue(calculateQuantile(extractHoursStudied(studentDataList), 0.5), "Median", "Hours Studied");
        dataset.addValue(calculateQuantile(extractHoursStudied(studentDataList), 0.75), "75th Percentile", "Hours Studied");

        dataset.addValue(calculateQuantile(extractPreviousScores(studentDataList), 0.25), "25th Percentile", "Previous Scores");
        dataset.addValue(calculateQuantile(extractPreviousScores(studentDataList), 0.5), "Median", "Previous Scores");
        dataset.addValue(calculateQuantile(extractPreviousScores(studentDataList), 0.75), "75th Percentile", "Previous Scores");

        dataset.addValue(calculateQuantile(extractSleepHours(studentDataList), 0.25), "25th Percentile", "Sleep Hours");
        dataset.addValue(calculateQuantile(extractSleepHours(studentDataList), 0.5), "Median", "Sleep Hours");
        dataset.addValue(calculateQuantile(extractSleepHours(studentDataList), 0.75), "75th Percentile", "Sleep Hours");

        dataset.addValue(calculateQuantile(extractSamplePapers(studentDataList).stream().map(s -> (float) s).collect(Collectors.toList()), 0.25), "25th Percentile", "Sample Papers Practiced");
        dataset.addValue(calculateQuantile(extractSamplePapers(studentDataList).stream().map(s -> (float) s).collect(Collectors.toList()), 0.5), "Median", "Sample Papers Practiced");
        dataset.addValue(calculateQuantile(extractSamplePapers(studentDataList).stream().map(s -> (float) s).collect(Collectors.toList()), 0.75), "75th Percentile", "Sample Papers Practiced");

        dataset.addValue(calculateQuantile(extractPerformanceIndex(studentDataList).stream().map(s -> (float) s).collect(Collectors.toList()), 0.25), "25th Percentile", "Performance Index");
        dataset.addValue(calculateQuantile(extractPerformanceIndex(studentDataList).stream().map(s -> (float) s).collect(Collectors.toList()), 0.5), "Median", "Performance Index");
        dataset.addValue(calculateQuantile(extractPerformanceIndex(studentDataList).stream().map(s -> (float) s).collect(Collectors.toList()), 0.75), "75th Percentile", "Performance Index");

        return ChartFactory.createBarChart(
                "Student Data Statistics",
                "Metric",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    // Методы для вычисления статистики по каждому столбцу
    private double calculateMeanHoursStudied(List<StudentData> studentDataList) {
        return studentDataList.stream().mapToDouble(StudentData::getHoursStudied).average().orElse(0.0);
    }

    private double calculateMeanPreviousScore(List<StudentData> studentDataList) {
        return studentDataList.stream().mapToDouble(StudentData::getPreviousScope).average().orElse(0.0);
    }

    private double calculateMeanSleepHours(List<StudentData> studentDataList) {
        return studentDataList.stream().mapToDouble(StudentData::getSleepHours).average().orElse(0.0);
    }

    private double calculateMeanSamplePapers(List<StudentData> studentDataList) {
        return studentDataList.stream().mapToDouble(StudentData::getSampleQuestionPapers).average().orElse(0.0);
    }

    private double calculateMeanPerformanceIndex(List<StudentData> studentDataList) {
        return studentDataList.stream().mapToDouble(StudentData::getPerformanceIndex).average().orElse(0.0);
    }

    // Стандартное отклонение для каждого столбца
    private double calculateStdDevHoursStudied(List<StudentData> studentDataList) {
        double mean = calculateMeanHoursStudied(studentDataList);
        double variance = studentDataList.stream()
                .mapToDouble(s -> Math.pow(s.getHoursStudied() - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    private double calculateStdDevPreviousScore(List<StudentData> studentDataList) {
        double mean = calculateMeanPreviousScore(studentDataList);
        double variance = studentDataList.stream()
                .mapToDouble(s -> Math.pow(s.getPreviousScope() - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    private double calculateStdDevSleepHours(List<StudentData> studentDataList) {
        double mean = calculateMeanSleepHours(studentDataList);
        double variance = studentDataList.stream()
                .mapToDouble(s -> Math.pow(s.getSleepHours() - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    private double calculateStdDevSamplePapers(List<StudentData> studentDataList) {
        double mean = calculateMeanSamplePapers(studentDataList);
        double variance = studentDataList.stream()
                .mapToDouble(s -> Math.pow(s.getSampleQuestionPapers() - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    private double calculateStdDevPerformanceIndex(List<StudentData> studentDataList) {
        double mean = calculateMeanPerformanceIndex(studentDataList);
        double variance = studentDataList.stream()
                .mapToDouble(s -> Math.pow(s.getPerformanceIndex() - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    // Минимальные и максимальные значения
    private List<Float> extractHoursStudied(List<StudentData> studentDataList) {
        List<Float> hoursStudied = new ArrayList<>();
        for (StudentData data : studentDataList) {
            hoursStudied.add((float) data.getHoursStudied());
        }
        return hoursStudied;
    }

    private List<Float> extractPreviousScores(List<StudentData> studentDataList) {
        List<Float> previousScores = new ArrayList<>();
        for (StudentData data : studentDataList) {
            previousScores.add((float) data.getPreviousScope());
        }
        return previousScores;
    }

    private List<Float> extractSleepHours(List<StudentData> studentDataList) {
        List<Float> sleepHours = new ArrayList<>();
        for (StudentData data : studentDataList) {
            sleepHours.add((float) data.getSleepHours());
        }
        return sleepHours;
    }

    private List<Short> extractSamplePapers(List<StudentData> studentDataList) {
        List<Short> samplePapers = new ArrayList<>();
        for (StudentData data : studentDataList) {
            samplePapers.add((short) data.getSampleQuestionPapers());
        }
        return samplePapers;
    }

    private List<Float> extractPerformanceIndex(List<StudentData> studentDataList) {
        List<Float> performanceIndex = new ArrayList<>();
        for (StudentData data : studentDataList) {
            performanceIndex.add(data.getPerformanceIndex());
        }
        return performanceIndex;
    }

    // Квантили
    private double calculateQuantile(List<Float> data, double quantile) {
        int index = (int) Math.ceil(quantile * data.size()) - 1;
        List<Float> sortedData = new ArrayList<>(data);
        Collections.sort(sortedData);
        return sortedData.get(index);
    }

    // Метод запуска визуализации
    public static void visualizeStatistics(List<StudentData> studentDataList) {
        StatisticsVisualizer chart = new StatisticsVisualizer("Student Data Statistics", studentDataList);
        chart.pack();
        chart.setLocationRelativeTo(null);  // Центрирование окна
        chart.setVisible(true);
    }
}