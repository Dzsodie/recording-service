package com.zetoinc.recording_service.repository;

import com.zetoinc.recording_service.model.Recording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Recording} entities.
 *
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations
 * for the {@link Recording} entity. It interacts with the underlying database
 * using Spring Data JPA.
 * </p>
 *
 * <p>
 * Since {@code JpaRepository} provides built-in methods such as {@code save()},
 * {@code findById()}, {@code findAll()}, {@code deleteById()}, and more,
 * there is no need to explicitly define them.
 * </p>
 *
 * <p>
 * Additional custom query methods can be defined as needed.
 * </p>
 *
 * @author Zsuzsa Makara
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository
public interface RecordingRepository extends JpaRepository<Recording, Long> {
}
