package com.mamh.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jpa_manager")
public class Manager {
    private int id;
    private String mgrName;

    private Department deptartment;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    @OneToOne(mappedBy = "manager", fetch = FetchType.LAZY)
    public Department getDeptartment() {
        return deptartment;
    }

    public void setDeptartment(Department deptartment) {
        this.deptartment = deptartment;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", mgrName='" + mgrName + '\'' +
                '}';
    }
}
