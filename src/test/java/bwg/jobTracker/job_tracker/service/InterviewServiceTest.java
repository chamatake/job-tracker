package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.TestDataUtil;
import bwg.jobTracker.job_tracker.dto.InterviewDTO;
import bwg.jobTracker.job_tracker.dto.request.InterviewCreateRequest;
import bwg.jobTracker.job_tracker.entity.Interview;
import bwg.jobTracker.job_tracker.entity.JobApplication;
import bwg.jobTracker.job_tracker.enums.InterviewType;
import bwg.jobTracker.job_tracker.repository.InterviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterviewServiceTest {
    @Mock
    private InterviewRepository repository;
    private InterviewService interviewService;

    @BeforeEach
    public void setup() {
        interviewService = new InterviewService(repository);
    }

    @Test
    public void testAdd_happyPath() {
        InterviewCreateRequest request = TestDataUtil.makeInterviewRequest(InterviewType.TECHNICAL_INTERVIEW);
        Interview added = TestDataUtil.makeInterview(InterviewType.TECHNICAL_INTERVIEW);
        added.setId(2233L);

        when(repository.save(any(Interview.class))).thenReturn(added);

        InterviewDTO actual = interviewService.add(request);

        assertNotNull(actual.id());
        assertEquals(MapperUtil.toJobApplicationDTO(request.getJobApplication()), actual.jobApplication());
        assertEquals(request.getInterviewType(), actual.interviewType());
        assertEquals(request.getInterviewDate(), actual.interviewDate());
        assertEquals(request.getNotes(), actual.notes());
    }
}