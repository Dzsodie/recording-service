package com.zetoinc.recording_service.service;

import com.zetoinc.recording_service.model.Recording;
import com.zetoinc.recording_service.repository.RecordingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing recording data.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RecordingServiceImpl implements RecordingService {

    private final RecordingRepository recordingRepository;

    /**
     * Fetches all recordings from the database.
     *
     * @return list of recordings
     */
    public List<Recording> getAllRecordings() {
        List<Recording> recordings = recordingRepository.findAll();
        log.info("Fetched {} recordings from database.", recordings.size());
        return recordings;
    }

    /**
     * Update recordings from frontend.
     *
     */
    @Override
    @Transactional
    public void updateRecording(Recording updatedRecording) {
        Optional<Recording> existing = recordingRepository.findById(updatedRecording.getId());

        if (existing.isPresent()) {
            Recording recording = existing.get();
            recording.setSedation(updatedRecording.getSedation());
            recording.setActivation(updatedRecording.getActivation());
            recording.setMedication(updatedRecording.getMedication());
            recordingRepository.save(recording);
            log.info("Updated recording in DB: {}", recording);
        } else {
            log.warn("Recording not found: ID {}", updatedRecording.getId());
        }
    }
}
