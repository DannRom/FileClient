import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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

        // Initialize resource dependent objects as parameters within the try block.
        // All objects will automatically close at the end of the try block.
        // The serverSocket is created to connect to server.
        // Once the client has connected, the "out" object is declared to send file data
        // to the server. A file reader is also created.
        try (Socket serverSocket = new Socket(serverHostname, SERVER_PORT);
             PrintWriter out = new PrintWriter(
                     serverSocket.getOutputStream(), true);
             BufferedReader fileIn = new BufferedReader(
                     new FileReader(fileToUpload))
        ) {
            // If the past connections and object creation are successful, then we
            // are able to proceed in uploading the file data to the server.
            System.out.println("Uploading " + fileToUpload + " to server.");

            // The client will continue sending data to the server until it reaches end of file.
            for (String line = fileIn.readLine(); line != null; line = fileIn.readLine())
                out.println(line);

            // The upload is complete at this point.
            System.out.println("Upload Complete.");
        } catch (IOException e) {
            // print detailed error stack trace.
            e.printStackTrace();
        }
    }
}
