import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread{

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(6324);

            while (true){
                Socket socket = serverSocket.accept();

                //JOptionPane.showMessageDialog(null,"new util.client connect");
                chatSocket cs = new chatSocket(socket);
                System.out.println("new socket connect: " + cs.socket.getInetAddress());
                cs.start();
                serverManager.getServerManager().add(cs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
