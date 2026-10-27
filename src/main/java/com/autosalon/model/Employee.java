package com.autosalon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "experience")
    private int experience;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "showroom_id", nullable = false)
    private Showroom showroom;

    public Employee() {}

    public Employee(String name, String position, int experience, Showroom showroom) {
        this.name = name;
        this.position = position;
        this.experience = experience;
        this.showroom = showroom;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public Showroom getShowroom() { return showroom; }
    public void setShowroom(Showroom showroom) { this.showroom = showroom; }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", position=" + position
                + ", experience=" + experience + "]";
    }
}