import java.util.ArrayList;
import java.util.List;

public class serverManager {

    private serverManager(){}
    private static final serverManager SERVER_MANAGER = new serverManager();

    public static serverManager getServerManager(){
        return SERVER_MANAGER;
    }

    List<chatSocket> vector = new ArrayList<>();

    public void add(chatSocket cs){
        vector.add(cs);
    }

    public void remove(chatSocket cs){
        vector.remove(cs);
    }

    public void publish(chatSocket csin, int out){

        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i) != csin){
                vector.get(i).out(out);
            }
        }

    }

}
