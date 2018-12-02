import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class sendTest {

    public static void main(String[] args) throws IOException {
        File src = new File("img");

        int count = 0;
        if (src.exists()) {
            File[] files = src.listFiles();
            for (File temp : files) {
                if (temp.isFile()) {
                    count++;
                }
            }
        }

        ServerSocket server = new ServerSocket(8889);
        System.out.println("服务器开启连接...端口为8889");
        Socket s = server.accept();

        s.getOutputStream().write(count);
        s.close();

        s = server.accept();
        for (int i = 0; i < count; i++) {
            System.out.println("一客户端连接服务器，服务器传输图片...");
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            String path = "img/new" + i + ".png";
            BufferedImage image = ImageIO.read(new File(path));    //读取1.gif并传输
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(image, "png", out);
            byte[] b = out.toByteArray();
            dout.write(b);
            s.close();
            System.out.println("图片传送完毕,服务器开启连接...端口为8889");
            s = server.accept();
        }
    }

}
