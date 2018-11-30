import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MSQlisten {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(14451);
        List<Socket> client = new ArrayList<Socket>();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            client.add(clientSocket);
            new Thread(new upload(clientSocket,client)).start();
        }
    }

    private static class upload implements Runnable{

        private Socket clientSocket;
        List<Socket> client_list;

        public upload(Socket socket, List<Socket> client_list){
            this.clientSocket = socket;
            this.client_list = client_list;
        }

        @Override
        public void run() {
            try {
                byte[] data = new byte[1024];

                while (true) {
                    InputStream is = clientSocket.getInputStream();

                    int length = is.read(data);
                    String rec = new String(data, 0, length);

                    if (rec.equals("0")){
                        historyWrite.historyWrite("有毒气体");
                    }else if (rec.equals("1")){
                        historyWrite.historyWrite("非法入侵");
                    }else if (rec.equals("100")){
                        ResultSet inread = historyRead.read(clientSocket);
                        OutputStream os = clientSocket.getOutputStream();
                        String sendrec = "";
                        while (inread.next()){
                            sendrec = sendrec + "|" + inread.getString("times").substring(11,16) + "      "+inread.getString("msg");
                        }
                        System.out.println(sendrec);
                        os.write(sendrec.getBytes());
                    }else {
                        OutputStream os = clientSocket.getOutputStream();
                        os.write("wrong data".getBytes());
                        os.flush();
                    }

                }
            }catch (Exception ex){

            }
        }
    }
}
