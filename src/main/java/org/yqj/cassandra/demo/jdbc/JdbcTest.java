package org.yqj.cassandra.demo.jdbc;

/**
 * Created by yaoqijun.
 * Date:2015-11-02
 * Email:yaoqj@terminus.io
 * Descirbe:    通过驱动的方式连接对应的数据内容
 */
public class JdbcTest {

    public static void main(String []args){
        ClusterSession session = new ClusterSession();
        session.connect("127.0.0.1","terminus_data");
        session.insertData();
        session.loadData();
        session.close();
    }

}
