package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "share")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Share implements Serializable {
    private static final long serialVersionUID = 1L;
    @Version  // Trường phiên bản cho Optimistic Locking
    private int version;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"application", "hibernateLazyInitializer"})
    private User userId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "videoId", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"application", "hibernateLazyInitializer"})
    private Video videoId;

    @Column(name = "emails")
    private String emails;

    @Column(name = "shareDate")
    @CreationTimestamp
    private Timestamp shareDate;

}
