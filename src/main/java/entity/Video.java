package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "video")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    @Version  // Trường phiên bản cho Optimistic Locking
    private int version;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "href")
    private String href;

    @Column(name = "poster")
    private String poster;

    @Column(name = "views")
    private Integer views;

    @Column(name = "description")
    private String description;

    @Column(name = "isActive")
    private boolean isActive;

}
