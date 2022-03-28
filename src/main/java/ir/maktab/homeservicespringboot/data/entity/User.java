package ir.maktab.homeservicespringboot.data.entity;

import ir.maktab.homeservicespringboot.data.enums.UserState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String family;

    @Column(unique = true, nullable = false,length = 25)
    private String email;

    @Column(nullable = false, length = 8)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserState state;

    @CreationTimestamp
    private Date RegistrationDate;

    private double balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != 0 && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
