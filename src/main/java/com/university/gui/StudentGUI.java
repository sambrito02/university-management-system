package com.university.gui;


import com.university.model.Student;
import com.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Component
public class StudentGUI {
    @Autowired
    private StudentService studentService;

    private JFrame frame;
    private JTextField nameField, emailField, majorField;
    private DefaultListModel<String> studentListModel;

    public void createAndShowGUI() {
    	if (GraphicsEnvironment.isHeadless()) {
            System.err.println("GUI is not supported in this environment.");
            return; // Exit gracefully
        }

        frame = new JFrame("Student Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        frame.setLayout(new BorderLayout());
        
        frame = new JFrame("Student Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        frame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2));
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        formPanel.add(new JLabel("Major:"));
        majorField = new JTextField();
        formPanel.add(majorField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(this::addStudent);
        formPanel.add(addButton);

        frame.add(formPanel, BorderLayout.NORTH);

        studentListModel = new DefaultListModel<>();
        JList<String> studentList = new JList<>(studentListModel);
        JScrollPane scrollPane = new JScrollPane(studentList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Selected Student");
        deleteButton.addActionListener(e -> deleteStudent(studentList.getSelectedValue()));
        frame.add(deleteButton, BorderLayout.SOUTH);

        loadStudents();

        frame.setVisible(true);
    }

    private void loadStudents() {
        List<Student> students = studentService.getAllStudents();
        studentListModel.clear();
        for (Student student : students) {
            studentListModel.addElement(student.getId() + ": " + student.getName());
        }
    }

    private void addStudent(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
        String major = majorField.getText();

        if (name.isEmpty() || email.isEmpty() || major.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setMajor(major);

        studentService.saveStudent(student);

        JOptionPane.showMessageDialog(frame, "Student added successfully!");
        loadStudents();
    }

    private void deleteStudent(String selectedStudent) {
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(frame, "Please select a student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long studentId = Long.valueOf(selectedStudent.split(":")[0]);
        studentService.deleteStudent(studentId);

        JOptionPane.showMessageDialog(frame, "Student deleted successfully!");
        loadStudents();
    }
}
