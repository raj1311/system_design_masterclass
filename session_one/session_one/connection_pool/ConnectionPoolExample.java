import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolExample {
    public static void main(String[] args) {
        ConnectionPool pool = new ConnectionPool(2, 5, 3000); // 3 seconds idle timeout
        
        try {
            List<Connection> connectionsAcquired = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Connection conn = pool.acquireConnection();
                connectionsAcquired.add(conn);
                System.out.println("Acquired connection " + conn.getId());
            }

            for (int i = 0; i < 5; i++) {
                pool.releaseConnection(connectionsAcquired.get(i));
            }
            Thread.sleep(10000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}