package com.job.micro.accounttx.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personId")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String identification;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String telephone;

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;

        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ?
                (hibernateProxy.getHibernateLazyInitializer().getPersistentClass())
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy
                ? (hibernateProxy.getHibernateLazyInitializer().getPersistentClass())
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Person person = (Person) o;
        if (!person.canEqual(this)) return false;

        return getIdentification() != null
                && Objects.equals(getIdentification(), person.getIdentification());
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Person;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object identificationObj = this.getIdentification();
        result = result * PRIME + (identificationObj == null ? 43 : identificationObj.hashCode());
        return result;
    }

}
