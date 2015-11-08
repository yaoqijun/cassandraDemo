package org.yqj.cassandra.demo.springdata.persondemo;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.List;

/**
 * Created by yaoqijun.
 * Date:2015-11-02
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
public class PersonTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        try {
            //connect
            Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
            Session session = cluster.connect("terminus_data");
            CassandraOperations cassandraOps = new CassandraTemplate(session);

            //test
            testCassandraTimestamp(cassandraOps);
            //testDelete(cassandraOps);
            //queryTest(cassandraOps);
            session.close();
            cluster.close();

            System.out.println("finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertTest(CassandraOperations cassandraOps){
        //cassandraOps.insert(new Person("1234567890", "David", 40, FORMATTER.parseDateTime("1993-08-20").toDate()));
        cassandraOps.insert(new Person("1", "David", 40, FORMATTER.parseDateTime("2015-10-10").toDate()));
        cassandraOps.insert(new Person("2", "David", 40, FORMATTER.parseDateTime("2015-10-11").toDate()));
        cassandraOps.insert(new Person("3", "David", 40, FORMATTER.parseDateTime("2015-10-12").toDate()));
        cassandraOps.insert(new Person("4", "David", 40, FORMATTER.parseDateTime("2015-10-13").toDate()));
        cassandraOps.insert(new Person("5", "David", 40, FORMATTER.parseDateTime("2015-10-14").toDate()));
        cassandraOps.insert(new Person("6", "David", 40, FORMATTER.parseDateTime("2015-10-15").toDate()));
        cassandraOps.insert(new Person("7", "David", 40, FORMATTER.parseDateTime("2015-10-16").toDate()));

    }

    public static void queryTest(CassandraOperations cassandraOps){
        List<Person> persons = cassandraOps.select("select * from person", Person.class);
        persons.forEach(person -> {
            System.out.println(person);
        });
    }
    public static void testDelete(CassandraOperations cassandraOps){
        cassandraOps.truncate("person");
    }

    public static void testCassandraTimestamp(CassandraOperations cassandraOperations){
        //Shell  的显示可能不对
        Select select = QueryBuilder.select().from("person");
        select.allowFiltering().where(QueryBuilder.gte("birth","2015-10-11")).and(QueryBuilder.lte("birth","2015-10-15"));
        System.out.println(select.toString());
        String sql = "SELECT * FROM person WHERE birth>='2015-10-11' AND birth<='2015-10-15' ALLOW FILTERING;";
        List<Person> persons = cassandraOperations.select(select.toString(),Person.class);
        persons.forEach((person)->{
            System.out.println(person);
        });
    }
}
