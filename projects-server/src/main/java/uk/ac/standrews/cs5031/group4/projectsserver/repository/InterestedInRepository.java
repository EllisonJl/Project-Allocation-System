package uk.ac.standrews.cs5031.group4.projectsserver.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedIn;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedInId;

public interface InterestedInRepository extends CrudRepository<InterestedIn, InterestedInId> {

}
