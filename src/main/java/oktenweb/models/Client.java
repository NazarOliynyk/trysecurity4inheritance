package oktenweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"clientContacts"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@DiscriminatorValue("CLIENT")
public class Client extends User{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    String username;
//    String password;
    String clientName;


    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "restaurant"
    )
    List<Contact> clientContacts = new ArrayList<>();


}
