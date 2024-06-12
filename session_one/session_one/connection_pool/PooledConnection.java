class PooledConnection {
    private final Connection connection;
    private long lastUsedTime;

    public PooledConnection(Connection connection) {
        this.connection = connection;
        this.lastUsedTime = System.currentTimeMillis();
    }

    public Connection getConnection() {
        return connection;
    }

    public long getLastUsedTime() {
        return lastUsedTime;
    }

    public void updateLastUsedTime() {
        this.lastUsedTime = System.currentTimeMillis();
    }
}
