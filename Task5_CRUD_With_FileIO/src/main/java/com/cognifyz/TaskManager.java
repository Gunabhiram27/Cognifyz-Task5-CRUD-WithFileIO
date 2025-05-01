package com.cognifyz;

import java.io.*;
import java.util.*;

public class TaskManager {
    private static final String FILE_NAME = "tasks.txt";
    private static List<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1;

    public static void main(String[] args) {
        loadTasksFromFile();
        boolean running = true;

        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> createTask();
                case 2 -> viewTasks();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 5 -> running = false;
                default -> System.out.println("Invalid option, try again.");
            }
        }

        System.out.println("👋 Exiting. Goodbye!");
    }

    private static void showMenu() {
        System.out.println("\nTask Manager Menu:");
        System.out.println("1. Create Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Update Task");
        System.out.println("4. Delete Task");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void createTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        Task task = new Task(nextId++, title, description);
        tasks.add(task);
        saveTasksToFile();
        System.out.println("✅ Task created.");
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private static void updateTask() {
        System.out.print("Enter task ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Task task = findTaskById(id);
        if (task != null) {
            System.out.print("Enter new title: ");
            task.setTitle(scanner.nextLine());
            System.out.print("Enter new description: ");
            task.setDescription(scanner.nextLine());
            saveTasksToFile();
            System.out.println("✅ Task updated.");
        } else {
            System.out.println("❌ Task not found.");
        }
    }

    private static void deleteTask() {
        System.out.print("Enter task ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            saveTasksToFile();
            System.out.println("✅ Task deleted.");
        } else {
            System.out.println("❌ Task not found.");
        }
    }

    private static Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    private static void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileString(line);
                tasks.add(task);
                if (task.getId() >= nextId) {
                    nextId = task.getId() + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error loading tasks: " + e.getMessage());
        }
    }

    private static void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.toFileString());
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error saving tasks: " + e.getMessage());
        }
    }
}
