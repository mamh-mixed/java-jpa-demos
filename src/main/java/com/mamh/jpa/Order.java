package com.mamh.jpa;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jpa_order")
public class Order {
    private Integer id;
    private String oderName;


    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "order_name")
    public String getOderName() {
        return oderName;
    }

    public void setOderName(String oderName) {
        this.oderName = oderName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", oderName='" + oderName + '\'' +
                '}';
    }
}
