package sms_project;

import java.sql.*;
import java.util.*;

public class StudentManagement {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> searchStudent();
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addStudent() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Course: ");
            String course = scanner.nextLine();

            String sql = "INSERT INTO students(name, email, course) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, course);
            stmt.executeUpdate();

            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewAllStudents() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            System.out.println("ID\tName\tEmail\tCourse");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                                   rs.getString("name") + "\t" +
                                   rs.getString("email") + "\t" +
                                   rs.getString("course"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter student ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("New Name: ");
            String name = scanner.nextLine();
            System.out.print("New Email: ");
            String email = scanner.nextLine();
            System.out.print("New Course: ");
            String course = scanner.nextLine();

            String sql = "UPDATE students SET name=?, email=?, course=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, course);
            stmt.setInt(4, id);
            int rows = stmt.executeUpdate();

            System.out.println(rows > 0 ? "Student updated." : "Student not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter student ID to delete: ");
            int id = scanner.nextInt();
            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            System.out.println(rows > 0 ? "Student deleted." : "Student not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchStudent() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter student ID to search: ");
            int id = scanner.nextInt();
            String sql = "SELECT * FROM students WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Course: " + rs.getString("course"));
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

