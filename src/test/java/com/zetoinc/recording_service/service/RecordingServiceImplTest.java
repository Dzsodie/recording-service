package com.zetoinc.recording_service.service;

import com.zetoinc.recording_service.model.Recording;
import com.zetoinc.recording_service.repository.RecordingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordingServiceImplTest {

    @Mock
    private RecordingRepository recordingRepository;

    @InjectMocks
    private RecordingServiceImpl recordingService;

    private Recording sampleRecording;

    @BeforeEach
    void setUp() {
        sampleRecording = new Recording();
        sampleRecording.setId(1L);
        sampleRecording.setSedation("Mild");
        sampleRecording.setActivation("None");
        sampleRecording.setMedication("Paracetamol");
    }

    @Test
    void testGetAllRecordings_ShouldReturnListOfRecordings() {
        List<Recording> mockRecordings = Arrays.asList(sampleRecording, new Recording());
        when(recordingRepository.findAll()).thenReturn(mockRecordings);

        List<Recording> result = recordingService.getAllRecordings();

        assertEquals(2, result.size(), "Should return 2 recordings");
        verify(recordingRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRecordings_ShouldReturnEmptyList_WhenNoRecordings() {
        when(recordingRepository.findAll()).thenReturn(List.of());

        List<Recording> result = recordingService.getAllRecordings();

        assertTrue(result.isEmpty(), "Should return an empty list");
        verify(recordingRepository, times(1)).findAll();
    }

    @Test
    void testUpdateRecording_ShouldUpdateExistingRecording() {
        when(recordingRepository.findById(sampleRecording.getId())).thenReturn(Optional.of(sampleRecording));

        Recording updatedRecording = new Recording();
        updatedRecording.setId(1L);
        updatedRecording.setSedation("Deep");
        updatedRecording.setActivation("Strong");
        updatedRecording.setMedication("Ibuprofen");

        recordingService.updateRecording(updatedRecording);

        ArgumentCaptor<Recording> captor = ArgumentCaptor.forClass(Recording.class);
        verify(recordingRepository).save(captor.capture());

        Recording savedRecording = captor.getValue();
        assertEquals("Deep", savedRecording.getSedation(), "Sedation should be updated");
        assertEquals("Strong", savedRecording.getActivation(), "Activation should be updated");
        assertEquals("Ibuprofen", savedRecording.getMedication(), "Medication should be updated");
    }

    @Test
    void testUpdateRecording_ShouldNotUpdate_WhenRecordingNotFound() {
        when(recordingRepository.findById(anyLong())).thenReturn(Optional.empty());

        Recording updatedRecording = new Recording();
        updatedRecording.setId(99L);
        updatedRecording.setSedation("Light");
        updatedRecording.setActivation("None");
        updatedRecording.setMedication("Aspirin");

        recordingService.updateRecording(updatedRecording);

        verify(recordingRepository, never()).save(any(Recording.class));
    }
}
