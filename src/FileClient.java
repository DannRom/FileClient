import java.io.*;
import java.net.Socket;

class FileClient {

    // Declare Constants
    static int SERVER_PORT = 10007;
    static String SERVER_HOST = "127.0.0.1";
    static String DEFAULT_FILE_DIRECTORY = "src/test.txt"; // If using the command line, remove "src/".

    public static void main(String[] args) {

        // If the user uses the command line, they will have the option of selecting a file.
        String fileToUpload = args.length == 0 ? DEFAULT_FILE_DIRECTORY : args[0];
        String serverHostname = SERVER_HOST;
        System.out.println("Attempting to connect to host " +
                serverHostname + " on port " + SERVER_PORT + ".");

        File file = new File(fileToUpload);

        // Initialize resource dependent objects as parameters within the try block.
        // All objects will automatically close at the end of the try block.
        // The serverSocket is created to connect to server.
        // Once the client has connected, the "out" object is declared to send file data
        // to the server. A file reader is also created.
        try (Socket serverSocket = new Socket(serverHostname, SERVER_PORT);
             OutputStream outputStream = serverSocket.getOutputStream();
             BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))
        ) {
            System.out.println("Connection successful.\nUploading file...");
            byte[] data = new byte[(int) file.length()]; // Initialize the size of the byte array.
            inputStream.read(data,0, data.length); // Read the file data into array.
            outputStream.write(data,0, data.length); // Stream data to server.
            System.out.println("File upload complete.");
        } catch (IOException e) {
            // print detailed error stack trace.
            e.printStackTrace();
        }
    }
}
