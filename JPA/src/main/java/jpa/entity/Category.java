package jpa.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
@NamedQueries({
    @NamedQuery(name="Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name="Category.findByUser", query = "SELECT c FROM Category c WHERE c.user.id = :userId")
})
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   // cột id (PK)

    @Column(name="categoryname", nullable=false, columnDefinition="NVARCHAR(255)")
    private String categoryname;

    @Column(name="images", columnDefinition="NVARCHAR(MAX)")
    private String images;

    // Quan hệ: Nhiều Category thuộc 1 User
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    // Constructors
    public Category() {}
    public Category(String categoryname, String images, User user) {
        this.categoryname = categoryname;
        this.images = images;
        this.user = user;
    }

    // Getters/Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCategoryname() { return categoryname; }
    public void setCategoryname(String categoryname) { this.categoryname = categoryname; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Category [id=" + id + ", categoryname=" + categoryname + ", images=" + images + "]";
    }
}

