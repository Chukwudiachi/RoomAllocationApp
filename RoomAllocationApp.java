import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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

public class RoomAllocationApp extends JFrame {
    private List<Room> rooms;
    private JTextArea outputArea;
    private JTextField roomNameField;
    private JTextField studentNameField;
    private JTextField matricNumberField;

    public RoomAllocationApp() {
        rooms = loadRoomsFromFile();

        // This is the swing GUI component
        outputArea = new JTextArea(15, 30);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        roomNameField = new JTextField(15);
        studentNameField = new JTextField(15);
        matricNumberField = new JTextField(15);

        JButton showRoomsButton = new JButton("Show Available Rooms");
        showRoomsButton.addActionListener(this::showAvailableRooms);

        JButton bookRoomButton = new JButton("Book Room");
        bookRoomButton.addActionListener(this::bookRoom);

        JButton saveRoomsButton = new JButton("Save Room Data");
        saveRoomsButton.addActionListener(this::saveRoomsToFile);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Room Name:"));
        inputPanel.add(roomNameField);
        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(studentNameField);
        inputPanel.add(new JLabel("Matric Number:"));
        inputPanel.add(matricNumberField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showRoomsButton);
        buttonPanel.add(bookRoomButton);
        buttonPanel.add(saveRoomsButton);

        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //This function loads the room names from a file called availableRooms.txt
    private List<Room> loadRoomsFromFile() {
        List<Room> loadedRooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("availableRooms.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedRooms.add(new Room(line));
            }
        } catch (IOException e) {
            showError("Error loading room data from file: " + e.getMessage());
        }
        return loadedRooms;
    }

    //This function shows the rooms available when the button is clicked on the gui interface
    private void showAvailableRooms(ActionEvent e) {
        outputArea.setText("Available Rooms:\n");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                outputArea.append(room.getRoomName() + "\n");
            }
        }
    }

    //This function is used to book a room
    private void bookRoom(ActionEvent e) {
        String roomName = roomNameField.getText().trim();
        String studentName = studentNameField.getText().trim();
        String matricNumber = matricNumberField.getText().trim();

        if (roomName.isEmpty() || studentName.isEmpty() || matricNumber.isEmpty()) {
            outputArea.setText("All fields must be filled out.");
            return;
        }

        Room roomToBook = findRoomByName(roomName);

        if (roomToBook != null) {
            if (roomToBook.isAvailable()) {
                Student student = new Student(studentName, matricNumber);
                roomToBook.allocateRoom(student);
                outputArea.setText("Room " + roomName + " has been booked by " + studentName);
            } else {
                outputArea.setText("Room " + roomName + " is already booked.");
            }
        } else {
            outputArea.setText("Room " + roomName + " not found.");
        }
    }

    private Room findRoomByName(String roomName) {
        for (Room room : rooms) {
            if (room.getRoomName().equalsIgnoreCase(roomName)) {
                return room;
            }
        }
        return null;
    }

    //This function saves the booked rooms with the details of the person that booked it to a file called rooms.txt
    private void saveRoomsToFile(ActionEvent e) {
        try (FileWriter writer = new FileWriter("rooms.txt")) {
            for (Room room : rooms) {
                Student student = room.getAllocatedStudent();
                if (student != null) {
                    String roomData = "Room " + room.getRoomName() +
                            ": " + student.getStudentName() +
                            " (Matric No: " + student.getMatricNumber() + ")\n";
                    writer.write(roomData);
                }
            }
            JOptionPane.showMessageDialog(this, "Successfully saved room data to the file.");
        } catch (IOException ex) {
            showError("An error occurred while saving room data: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoomAllocationApp::new);
    }
}
