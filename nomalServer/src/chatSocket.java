import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class chatSocket extends Thread{
    Socket socket;

    public chatSocket(Socket s){
        this.socket = s;
    }

    public void out(int out){
        try {
            socket.getOutputStream().write(out);
        } catch (IOException e) {
            serverManager.getServerManager().remove(this);
            System.out.println(this.socket.getInetAddress() + " disconnect");
            //e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            int line = -1;

            while ((line = bufferedReader.read()) != -1){
                System.out.println(line);
                serverManager.getServerManager().publish(this,line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            serverManager.getServerManager().remove(this);
            System.out.println(this.socket.getInetAddress() + " disconnect");
            //e.printStackTrace();
        }
    }
}
