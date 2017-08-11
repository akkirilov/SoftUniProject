package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.domain.entities.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long>{
	
}
