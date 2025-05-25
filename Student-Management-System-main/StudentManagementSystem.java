import java.util.*;
import java.io.*;

class Student {
    private int id;
    private String name;
    private String course;
    private double marks;

    public Student(int id, String name, String course, double marks) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               ", Name: " + name +
               ", Course: " + course +
               ", Marks: " + marks;
    }

    public String toFileString() {
        return id + "," + name + "," + course + "," + marks;
    }

    public static Student fromFileString(String line) {
        try {
            String[] parts = line.split(",");
            return new Student(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                Double.parseDouble(parts[3])
            );
        } catch (Exception e) {
            System.out.println("Error parsing line: " + line);
            return null;
        }
    }
}

public class StudentManagementSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.txt";
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadFromFile();
        int choice = -1;

        do {
            try {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. Display All Students");
                System.out.println("3. Search Student by ID");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Save to File");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();  // Consume leftover newline

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> displayStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 6 -> saveToFile();
                    case 7 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();  // Clear invalid input
            }
        } while (choice != 7);
    }

    private static void addStudent() {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            System.out.print("Enter Marks: ");
            double marks = sc.nextDouble();
            students.add(new Student(id, name, course, marks));
            System.out.println("Student added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter correct data types.");
            sc.nextLine(); // Clear input buffer
        }
    }

    private static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    private static void searchStudent() {
        try {
            System.out.print("Enter ID to search: ");
            int id = sc.nextInt();
            for (Student s : students) {
                if (s.getId() == id) {
                    System.out.println(s);
                    return;
                }
            }
            System.out.println("Student not found.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID input.");
            sc.nextLine();
        }
    }

    private static void updateStudent() {
        try {
            System.out.print("Enter ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            for (Student s : students) {
                if (s.getId() == id) {
                    System.out.print("Enter new Name: ");
                    s.setName(sc.nextLine());

                    System.out.print("Enter new Course: ");
                    s.setCourse(sc.nextLine());

                    System.out.print("Enter new Marks: ");
                    s.setMarks(sc.nextDouble());

                    System.out.println("Student updated.");
                    return;
                }
            }
            System.out.println("Student not found.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter correct data.");
            sc.nextLine(); // Clear buffer
        }
    }

    private static void deleteStudent() {
        try {
            System.out.print("Enter ID to delete: ");
            int id = sc.nextInt();
            Iterator<Student> it = students.iterator();
            while (it.hasNext()) {
                if (it.next().getId() == id) {
                    it.remove();
                    System.out.println("Student deleted.");
                    return;
                }
            }
            System.out.println("Student not found.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID.");
            sc.nextLine();
        }
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.println(s.toFileString());
            }
            System.out.println("Saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                Student s = Student.fromFileString(fileScanner.nextLine());
                if (s != null) {
                    students.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }
}
