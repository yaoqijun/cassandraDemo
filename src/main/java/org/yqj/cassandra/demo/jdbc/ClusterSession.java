package org.yqj.cassandra.demo.jdbc;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import lombok.Data;

/**
 * Created by yaoqijun.
 * Date:2015-11-02
 * Email:yaoqj@terminus.io
 * Descirbe:测试连接
 */
@Data
public class ClusterSession {

    private Cluster cluster;

    private Session session;

    /**
     * 连接对应的数据库内容
     * @param node
     */
    public void connect(String node,String namespace){
        cluster = Cluster.builder().addContactPoint(node).withPort(9042).build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        this.session = cluster.connect(namespace);
    }

    /**
     * cassandra 数据库中插入操作
     */
    public void insertData(){
        PreparedStatement preparedStatement = session.prepare("insert into student (id , fname , lname ) VALUES ( ?, ?, ?);");
        BoundStatement boundStatement = new BoundStatement(preparedStatement);
        session.execute(boundStatement.bind(5,"lucy","yun"));
    }

    /**
     * 录入数据内容
     */
    public void loadData(){
        ResultSet resultSet = session.execute("select * from student;");
        for(Row row : resultSet){
            System.out.println(row.getInt("id")+"   "+row.getString("fname")+"   "+row.getString("lname"));
        }
    }

    public void close(){
        cluster.close();
    }
}
