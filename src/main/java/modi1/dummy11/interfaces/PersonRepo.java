package modi1.dummy11.interfaces;

import modi1.dummy11.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Integer> {

}
