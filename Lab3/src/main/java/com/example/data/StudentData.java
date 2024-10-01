package com.example.data;


import lombok.Data;

@Data
public class StudentData {
    private float hoursStudied;
    private float previousScope;
    private boolean extracurricular;
    private float sleepHours;
    private float sampleQuestionPapers;
    private float performanceIndex;

    public StudentData(float hoursStudied, float previousScope, boolean extracurricular, float sleepHours, float sampleQuestionPapers, float performanceIndex) {
        this.hoursStudied = hoursStudied;
        this.previousScope = previousScope;
        this.extracurricular = extracurricular;
        this.sleepHours = sleepHours;
        this.sampleQuestionPapers = sampleQuestionPapers;
        this.performanceIndex = performanceIndex;
    }
}
