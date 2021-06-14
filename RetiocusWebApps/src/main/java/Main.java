import com.example.retiocus.RetiocusWebApps.websocket.endpoints.ChatServerEndpoint;
import com.example.retiocus.RetiocusWebApps.websocket.endpoints.ChatsOfUserServerEndpoint;
import com.example.retiocus.RetiocusWebApps.websocket.endpoints.UsersWithCommonThemesServerEndpoint;
import org.glassfish.tyrus.server.Server;

public class Main {
    public static void main(String[] args) {
        Server servidorEndpoints=new Server("retiocuswebapps.azurewebsites.net",80,"/websockets",
                UsersWithCommonThemesServerEndpoint.class,
                ChatServerEndpoint.class,
                ChatsOfUserServerEndpoint.class);
        try {
            servidorEndpoints.start();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
