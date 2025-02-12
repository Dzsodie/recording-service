package com.zetoinc.recording_service.service;

import com.zetoinc.recording_service.model.Recording;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecordingService {
    List<Recording> getAllRecordings();
    void updateRecording(Recording updatedRecording);
}
