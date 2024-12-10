package com.job.micro.personclient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "client")
public class Client extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "clientId", nullable = false, unique = true)
    private Long clientId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String status;

    @Override
    public final boolean equals(Object o) {
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

        Client client = (Client) o;
        if (!client.canEqual(this)) return false;

        return getClientId() != null
                && super.equals(o)
                && Objects.equals(getClientId(), client.getClientId());
    }

    @Override
    protected boolean canEqual(final Object other) {
        return other instanceof Client;
    }

    @Override
    public final int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result += super.hashCode();
        final Object clientIdObj = this.getClientId();
        result = result * PRIME + (clientIdObj == null ? 43 : clientIdObj.hashCode());
        return result;
    }
}
