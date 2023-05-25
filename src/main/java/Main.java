import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8082);
        Socket client = serverSocket.accept();
        BufferedWriter dataToClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader dataToServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        if (client.isConnected()){
            dataToClient.write("Доброго вечора, ми з України!");
        }
        String receivedLine = dataToServer.readLine();
        if (containsRussianLetters(receivedLine)) {
            dataToClient.write("Що таке паляниця?");
            String answer = dataToServer.readLine();
            if (answer.equals("хлібина") || answer.equals("хліб")) {
                dataToClient.write(LocalDateTime.now().toString());
                dataToClient.write("Бувай здоровий, козаче!");
            }
        }
        client.close();
        dataToServer.close();
        dataToClient.close();
    }
    public static boolean containsRussianLetters(String input) {
        String pattern = "(?iu)[ыэё]";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.find();
    }
}
