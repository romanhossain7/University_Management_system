package university_management_system;

public class Student {
    private String name, id, dept, sec, phone, advisor;

    public Student(String name, String id, String dept, String sec, String phone, String advisor) {
        this.name = name;
        this.id = id;
        this.dept = dept;
        this.sec = sec;
        this.phone = phone;
        this.advisor = advisor;
    }

    // Getters
    public String getName() { return name; }
    public String getId() { return id; }
    public String getDept() { return dept; }
    public String getSec() { return sec; }
    public String getPhone() { return phone; }
    public String getAdvisor() { return advisor; }
}
