package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ConsoleServer {
    private Vector<ClientHandler> users;

    public ConsoleServer(){
        users = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            server = new ServerSocket(6000);
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                System.out.printf("Client [%s] connected\n", socket.getInetAddress());
                subscribe(new ClientHandler(this, socket));
            }

    } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                 } catch (IOException e) {
                       e.printStackTrace();
                    }

            try {
                server.close();
                } catch (IOException e) {
                   e.printStackTrace();
                     }
            }
        }

        public void subscribe (ClientHandler client){
            users.add(client);
        }

    public void unsubscribe (ClientHandler client){
        users.remove(client);
    }

        public void broadcastMessage (String str){
            for (ClientHandler c: users) {
                c.sendMsg(str);

            }
        }
    }
