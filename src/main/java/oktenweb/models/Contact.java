package oktenweb.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity(name = "ContactsTrySecurity2")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"restaurant", "client"})
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String contactName;
    String email;

    public Contact(String contactName, String email) {
        this.contactName = contactName;
        this.email = email;
    }

    @ManyToOne(cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY)
    Restaurant restaurant;
    @ManyToOne(cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY)
    Client client;
}
