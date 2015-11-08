package org.yqj.cassandra.demo.springdata.persondemo;

import lombok.Data;
import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;

/**
 * Created by yaoqijun.
 * Date:2015-11-02
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
@Table(value = "person")
@Data
public class Person {

    public Person(){}

    public Person(String id, String name, Integer age, Date birth){
        this.id = id;
        this.name = name;
        this.age = age;
        this.birth = birth;
    }

    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id ;

    @Column("name")
    private String name;

    @Column("age")
    private Integer age;

    @PrimaryKeyColumn(name = "birth", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date birth;

    @Override
    public String toString(){
        return "Person [id=" + id + ", name=" + name + ", age=" + age + ", birth="+birth.toString()+"]";
    }
}
