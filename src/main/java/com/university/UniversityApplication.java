package com.university; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.university.gui.StudentGUI;

@SpringBootApplication(scanBasePackages = {"com.university"})
public class UniversityApplication implements CommandLineRunner {

    @Autowired
    private StudentGUI studentGUI;

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }

    @Override
    public void run(String[] args) {
       
        if (studentGUI != null) {
            System.out.println("Initializing GUI...");
            studentGUI.createAndShowGUI();
        } else {
            System.err.println("Error: StudentGUI bean is not initialized.");
        }
    }
}
