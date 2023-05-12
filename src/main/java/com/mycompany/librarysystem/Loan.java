package com.mycompany.librarysystem;

import java.time.LocalDate;

public class Loan {
    private String title;
    private String finished;
    private LocalDate dueDate;

    private String userName;

    //For DueScene
    public Loan(String title, LocalDate dueDate, String firstName, String lastname) {
        this.title = title;
        this.dueDate = dueDate;
        this.userName = firstName + " " + lastname;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}