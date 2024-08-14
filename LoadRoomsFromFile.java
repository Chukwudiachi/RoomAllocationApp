public static void main (String[] agrs) {
public List<Room> loadRoomsFromFile() {
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