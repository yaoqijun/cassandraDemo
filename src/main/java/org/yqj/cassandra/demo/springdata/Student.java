package org.yqj.cassandra.demo.springdata;

import lombok.Data;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by yaoqijun.
 * Date:2015-11-02
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
@Data
@Table
public class Student {

    @PrimaryKey
    private Integer id ;

    private String fname;

    private String lname;
}
