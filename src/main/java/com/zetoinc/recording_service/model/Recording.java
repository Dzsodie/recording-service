package com.zetoinc.recording_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Recording in the system.
 *
 * <p>
 * This entity is mapped to the {@code recordings} table in the database
 * and stores information about different types of medical recordings.
 * </p>
 *
 * <p>
 * Each recording has a unique ID, title, duration, status, and optional
 * fields for sedation, activation, and medication.
 * </p>
 *
 * <p>
 * The {@code status} field is an {@link Enum} representing different stages
 * of a recording: SCHEDULED, RECORDED, or REPORTED.
 * </p>
 *
 * @author Zsuzsa Makara
 */
@Entity
@Table(name = "recordings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recording {

    /**
     * Unique identifier for the recording.
     * Auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    /**
     * Title of the recording.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Duration of the recording in seconds.
     * Cannot be null.
     */
    @Column(nullable = false)
    private int duration;

    /**
     * Status of the recording.
     * This is an enumerated type with possible values:
     * <ul>
     *   <li>{@code SCHEDULED} - The recording is planned but not yet taken.</li>
     *   <li>{@code RECORDED} - The recording has been completed.</li>
     *   <li>{@code REPORTED} - The recording has been analyzed and reported.</li>
     * </ul>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    /**
     * Sedation details used during the recording (if applicable).
     * Can be null.
     */
    @Column
    private String sedation;

    /**
     * Activation method used in the recording process (if applicable).
     * Can be null.
     */
    @Column
    private String activation;

    /**
     * Medication used during the recording (if applicable).
     * Can be null.
     */
    @Column
    private String medication;
}
