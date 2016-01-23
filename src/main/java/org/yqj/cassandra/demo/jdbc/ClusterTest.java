package org.yqj.cassandra.demo.jdbc;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.UUID;

/**
 * Created by yaoqijun.
 * Date:2015-11-08
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
public class ClusterTest {

    //cluster cassandra 集群环境接口连接方式
    private static Cluster cluster;

    //cassandra Session
    private static Session session;

    public static void main(String[] args) {
        System.out.println("cluster main test function");
        connect("127.0.0.1");
//        createSchema();
//        insertData();
//        querySchama();
        testStatement();
        close();
        System.out.println("finish");
    }

    public static void testStatement(){
        PreparedStatement statement = session.prepare("insert into songs (id, title, album, artist, tags) " +
                "VALUES (?, ?, ?, ?, ?);");
        BoundStatement boundStatement = new BoundStatement(statement);
        Set<String> tags = Sets.newHashSet();
        tags.add("yao"); tags.add("qi"); tags.add("jun");
        session.execute(boundStatement.bind(UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf50"),
                "La Petite Tonkinoise'",
                "Bye Bye Blackbird'",
                "Joséphine Baker",
                tags ));
    }

    /**
     * 获取连接信息内容
     * @param node  node民称
     */
    public static void connect(String node) {
        cluster = Cluster.builder()
                .addContactPoint(node)
                .build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n",
                metadata.getClusterName());
        for (Host host : metadata.getAllHosts() ) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }
        session = cluster.connect("terminus_data");
    }

    public static void createSchema(){
        session.execute(
                "CREATE TABLE IF NOT EXISTS songs (" +
                        "id uuid PRIMARY KEY," +
                        "title text," +
                        "album text," +
                        "artist text," +
                        "tags set<text>," +
                        "data blob" +
                        ");");
        session.execute(
                "CREATE TABLE IF NOT EXISTS playlists (" +
                        "id uuid," +
                        "title text," +
                        "album text, " +
                        "artist text," +
                        "song_id uuid," +
                        "PRIMARY KEY (id, title, album, artist)" +
                        ");");
    }

    public static void insertData(){
        session.execute(
                "INSERT INTO songs (id, title, album, artist, tags) " +
                        "VALUES (" +
                        "756716f7-2e54-4715-9f00-91dcbea6cf50," +
                        "'La Petite Tonkinoise'," +
                        "'Bye Bye Blackbird'," +
                        "'Joséphine Baker'," +
                        "{'jazz', '2013'})" +
                        ";");
        session.execute(
                "INSERT INTO playlists (id, song_id, title, album, artist) " +
                        "VALUES (" +
                        "2cc9ccb7-6221-4ccb-8387-f22b6a1b354d," +
                        "756716f7-2e54-4715-9f00-91dcbea6cf50," +
                        "'La Petite Tonkinoise'," +
                        "'Bye Bye Blackbird'," +
                        "'Joséphine Baker'" +
                        ");");
    }

    public static void querySchama(){
        ResultSet results = session.execute("SELECT * FROM playlists " +
                "WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");
        System.out.println(String.format("%-30s\t%-20s\t%-20s\n%s", "title", "album", "artist",
                "-------------------------------+-----------------------+--------------------"));
        for (Row row : results) {
            System.out.println(String.format("%-30s\t%-20s\t%-20s", row.getString("title"),
                    row.getString("album"),  row.getString("artist")));
        }
        System.out.println();
    }

    public static void close(){
        session.close();
        cluster.close();
    }
}
