package com.database.model;


import javax.persistence.*;

@Entity
@Table(name = "system")
public class System {

    @Id
    @GeneratedValue
    @Column(name ="id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    public System() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
