package org.yqj.cassandra.demo.springdata;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.cassandra.core.CassandraOperations;

/**
 * Created by yaoqijun.
 * Date:2015-11-02
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
public class DataTest {
    public static void main(String[] args) {
        System.out.println("spring data test");
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("terminus-cassandra-context.xml");
        //CassandraTemplate cassandraTemplate = context.getBean(CassandraTemplate.class);
        CassandraOperations cassandraOperations = context.getBean(CassandraOperations.class);
        String sql ="insert into student (id , fname , lname ) VALUES ( 4, 'yao', 'jun');";
        cassandraOperations.execute(sql);
        System.out.println("finish");
    }
}
