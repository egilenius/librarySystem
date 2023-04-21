package com.mycompany.librarysystem;

import java.time.LocalDate;

public class Loan {
    private String title;
    private String finished;
    private LocalDate dueDate;


    public Loan(String title, String finished, LocalDate dueDate) {
        this.title = title;
        this.finished = finished;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}