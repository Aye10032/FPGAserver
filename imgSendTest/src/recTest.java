import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class recTest {

    public static void main(String[] args) throws IOException {

        Socket s = new Socket("127.0.0.1",8889);
        int count = s.getInputStream().read();
        s.close();

        for (int i = 0; i < count; i++) {
            send(i);
        }

    }

    private static void send(int count) throws IOException {
        BufferedImage image ;
        Socket s = new Socket("127.0.0.1",8889);
        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.print("a");
        DataInputStream in = new DataInputStream(s.getInputStream());
        byte[]b = new byte[1024];
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        int length = 0;
        while((length=in.read(b))!=-1){
            bout.write(b, 0, length);
        }
        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        image = ImageIO.read(bin);
        ImageIO.write(image, "png", new File("output-"+count+".png"));
        s.close();
    }

}
