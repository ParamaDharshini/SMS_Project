package sms_project;

public class Student {
    private int id;
    private String name;
    private String email;
    private String course;

    public Student(int id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + email + "\t" + course;
    }
}
