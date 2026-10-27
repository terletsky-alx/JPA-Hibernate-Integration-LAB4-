package com.autosalon.model;

import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "showrooms")
public class Showroom {

    @Id
    @Column(name = "showroom_id", length = 3)
    private String showroomId;

    @Column(name = "showroom_name", unique = true, nullable = false, length = 100)
    private String showroomName;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(
            mappedBy = "showroom",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Employee> employees;

    public Showroom() {}

    public Showroom(String showroomId, String showroomName, String address) {
        this.showroomId = showroomId;
        this.showroomName = showroomName;
        this.address = address;
    }

    public String getShowroomId() { return showroomId; }
    public void setShowroomId(String showroomId) { this.showroomId = showroomId; }

    public String getShowroomName() { return showroomName; }
    public void setShowroomName(String showroomName) { this.showroomName = showroomName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    @Override
    public String toString() {
        return "Showroom [id=" + showroomId + ", name=" + showroomName + ", address=" + address + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(showroomId, showroomName, address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showroom other = (Showroom) o;
        return Objects.equals(showroomId, other.showroomId)
                && Objects.equals(showroomName, other.showroomName)
                && Objects.equals(address, other.address);
    }
}