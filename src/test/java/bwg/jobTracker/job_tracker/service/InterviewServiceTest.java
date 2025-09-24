package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.BaseTest;
import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.InterviewDTO;
import bwg.jobTracker.job_tracker.dto.request.InterviewCreateRequest;
import bwg.jobTracker.job_tracker.entity.*;
import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import bwg.jobTracker.job_tracker.enums.InterviewType;
import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import bwg.jobTracker.job_tracker.repository.InterviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterviewServiceTest extends BaseTest {
    @Mock
    private InterviewRepository repository;
    private InterviewService interviewService;
    private final String notes = """
            Open here I flung the shutter, when, with many a flirt and flutter,
            In there stepped a stately Raven of the saintly days of yore;
                Not the least obeisance made he; not a minute stopped or stayed he;
                But, with mien of lord or lady, perched above my chamber door—
            Perched upon a bust of Pallas just above my chamber door—
                        Perched, and sat, and nothing more.""";

    @BeforeEach
    public void setup() {
        this.interviewService = new InterviewService(repository);
    }

    @Test
    public void testAdd_happyPath() {
        JobPosting parentPosting = makeJobPostingDummyGandalf();
        JobApplication parentApp = makeJobApplicationDummyNoStatuses(JOB_APPLICATION_ID, parentPosting);
        parentApp.updateStatus(ApplicationStatusType.APPLIED);
        InterviewCreateRequest request = new InterviewCreateRequest(parentApp, InterviewType.FIRST_INTERVIEW, DATE_g);
        request.setNotes(notes);
        Interview added = new Interview(parentApp, InterviewType.FIRST_INTERVIEW);
        added.setId(INTERVIEW_ID);
        added.setInterviewDate(DATE_g);
        added.setNotes(notes);

        when(repository.save(any(Interview.class))).thenReturn(added);

        InterviewDTO actual = interviewService.add(request);
        assertNotNull(actual.id());
        assertEquals(MapperUtil.toJobApplicationDTO(parentApp), actual.jobApplication());
        assertEquals(InterviewType.FIRST_INTERVIEW, actual.interviewType());
        assertEquals(DATE_g, actual.interviewDate());
        assertEquals(notes, actual.notes());
    }

    @Test
    public void testFindAll_happyPath() {
        JobPosting parentPosting = makeJobPostingDummyGandalf();
        JobApplication parentApp = makeJobApplicationDummyNoStatuses(JOB_APPLICATION_ID, parentPosting);
        parentApp.updateStatus(ApplicationStatusType.APPLIED);
        parentApp.updateStatus(ApplicationStatusType.INTERVIEWING);
        Interview first = new Interview(parentApp, InterviewType.FIRST_INTERVIEW);
        Interview second = new Interview(parentApp, InterviewType.TECHNICAL_INTERVIEW);
        Interview third = new Interview(parentApp, InterviewType.FINAL_INTERVIEW);
        List<Interview> existing = List.of(first, second, third);

        when(repository.findAll()).thenReturn(existing);

        List<InterviewDTO> actual = interviewService.findAll();
        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toInterviewDTO(first)));
        assertTrue(actual.contains(MapperUtil.toInterviewDTO(second)));
        assertTrue(actual.contains(MapperUtil.toInterviewDTO(third)));
    }

    @Test
    public void testFindAllByInterviewDate_happyPath() {
        JobPosting posting1 = makeJobPostingDummyGandalf();
        JobApplication parent1 = makeJobApplicationDummyFirstStatus(
                JOB_APPLICATION_ID, posting1, ApplicationStatusType.APPLIED
        );
        parent1.updateStatus(ApplicationStatusType.INTERVIEWING);
        Interview interview1 = new Interview(parent1, InterviewType.FIRST_INTERVIEW);
        interview1.setInterviewDate(DATE_PI);

        JobPosting posting2 = makeJobPostingDummy(
                new Company("Luthor Corp"),
                new ReferralSource("Schmoogle", ReferralSourceType.SEARCH),
                OfficeSituation.UNKNOWN
        );
        JobApplication parent2 = makeJobApplicationDummyFirstStatus(
                789L, posting2, ApplicationStatusType.APPLIED
        );
        parent2.updateStatus(ApplicationStatusType.INTERVIEWING);
        Interview interview2 = new Interview(parent2, InterviewType.CODING_ASSESSMENT);
        interview2.setInterviewDate(DATE_PI);

        List<Interview> existing = List.of(interview1, interview2);
        when(repository.findAllByInterviewDate(any(LocalDate.class))).thenReturn(existing);

        List<InterviewDTO> actual = interviewService.findAllByInterviewDate("2025-03-14");
        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toInterviewDTO(interview1)));
        assertTrue(actual.contains(MapperUtil.toInterviewDTO(interview2)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "March 14, 2025", "03-14-2025", "3/14/2025", "2025/3/14"
    })
    public void testFindAllByInterviewDate_badDateParse_shouldThrow(String badDate) {
        assertThrows(ResponseStatusException.class, () -> interviewService.findAllByInterviewDate(badDate));
    }
}