class Room {
    private String roomName;
    private boolean isAvailable;
    private Student allocatedStudent;

    public Room(String roomName) {
        this.roomName = roomName;
        this.isAvailable = true; 
    }

    public void allocateRoom(Student student) {
        if (isAvailable) {
            allocatedStudent = student;
            isAvailable = false;
        }
    }

    public Student getAllocatedStudent() {
        return allocatedStudent;
    }

    public String getRoomName() {
        return roomName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}

class Student {
    private String studentName;
    private String matricNumber;

    public Student(String studentName, String matricNumber) {
        this.studentName = studentName;
        this.matricNumber = matricNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getMatricNumber() {
        return matricNumber;
    }
}
