package com.mamh.jpa;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jpa_item")
public class Item {

    private Integer id;
    private String itemName;

    private Set<Category> categories = new HashSet<Category>();

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @ManyToMany
    @JoinTable( name = "jpa_item_category",
            joinColumns = {
                    @JoinColumn(name = "item_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "categroy_id", referencedColumnName = "id")
            }
    )
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
