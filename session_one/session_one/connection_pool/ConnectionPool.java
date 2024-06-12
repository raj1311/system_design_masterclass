import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class ConnectionPool {
    private final int minConnections;
    private final int maxConnections;
    private final long idleTimeoutMillis;
    private final Set<PooledConnection> activeConnections;
    private final Queue<PooledConnection> idleConnections;
    private final Lock lock;
    private final Condition condition;
    private final ScheduledExecutorService idleChecker;

    public ConnectionPool(int minConnections, int maxConnections, long idleTimeoutMillis) {
        this.minConnections = minConnections;
        this.maxConnections = maxConnections;
        this.idleTimeoutMillis = idleTimeoutMillis;
        this.activeConnections = new HashSet<>();
        this.idleConnections = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        initializeConnections();

        // Initialize the ScheduledExecutorService to run idle checks periodically
        idleChecker = Executors.newSingleThreadScheduledExecutor();
        idleChecker.scheduleAtFixedRate(this::checkIdleConnections, 
                                        idleTimeoutMillis / 2, 
                                        idleTimeoutMillis / 2, 
                                        TimeUnit.MILLISECONDS);
    }

    // Initialize minimum connections
    private void initializeConnections() {
        for (int i = 0; i < minConnections; i++) {
            idleConnections.add(new PooledConnection(new Connection(i)));
        }
    }

    public Connection acquireConnection() throws InterruptedException {
        lock.lock();
        try {
            while (idleConnections.isEmpty() && activeConnections.size() >= maxConnections) {
                System.out.println("Wait is triggered as it breached max connections");
                condition.await();
            }
            PooledConnection pooledConnection;
            if (!idleConnections.isEmpty()) {
                pooledConnection = idleConnections.poll();
            } else {
                pooledConnection = new PooledConnection(new Connection(activeConnections.size()));
            }
            pooledConnection.updateLastUsedTime();
            activeConnections.add(pooledConnection);
            return pooledConnection.getConnection();
        } finally {
            lock.unlock();
        }
    }

    public void releaseConnection(Connection connection) {
        lock.lock();
        try {
            Iterator<PooledConnection> iterator = activeConnections.iterator();
            while (iterator.hasNext()) {
                PooledConnection pooledConnection = iterator.next();
                if (pooledConnection.getConnection() == connection) {
                    iterator.remove();
                    pooledConnection.updateLastUsedTime();
                    idleConnections.add(pooledConnection);
                    condition.signalAll();
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // Periodic task to check for idle connections
    private void checkIdleConnections() {
        lock.lock();
        try {
            long currentTime = System.currentTimeMillis();
            Iterator<PooledConnection> iterator = idleConnections.iterator();
            while (iterator.hasNext()  && idleConnections.size()>minConnections) {
                PooledConnection pooledConnection = iterator.next();
                if (currentTime - pooledConnection.getLastUsedTime() > idleTimeoutMillis) {
                    iterator.remove();
                    System.out.println("Extra Connection has been removed -> " + pooledConnection.getConnection().getId());
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // Shutdown the ScheduledExecutorService properly
    public void shutdown() {
        idleChecker.shutdown();
        try {
            if (!idleChecker.awaitTermination(1, TimeUnit.SECONDS)) {
                idleChecker.shutdownNow();
            }
        } catch (InterruptedException e) {
            idleChecker.shutdownNow();
        }
    }
}