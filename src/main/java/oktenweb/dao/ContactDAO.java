package oktenweb.dao;


import oktenweb.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDAO extends JpaRepository<Contact, Integer> {
}
