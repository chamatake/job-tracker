package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.ReferralSourceDTO;
import bwg.jobTracker.job_tracker.dto.request.ReferralSourceCreateRequest;
import bwg.jobTracker.job_tracker.entity.ReferralSource;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import bwg.jobTracker.job_tracker.repository.ReferralSourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReferralSourceServiceTest {
    @Mock
    private ReferralSourceRepository repository;
    private ReferralSourceService referralSourceService;

    private static final String REF_NAME = "Super Secret Job Source";
    private static final Long REF_ID = 5L;

    @BeforeEach
    void setup() {
        referralSourceService = new ReferralSourceService(repository);
    }

    @Test
    public void testAdd_happyPath() {
        ReferralSourceCreateRequest request = new ReferralSourceCreateRequest(
                REF_NAME, ReferralSourceType.COMPANY_WEBSITE);
        ReferralSource added = new ReferralSource(REF_ID, REF_NAME, ReferralSourceType.COMPANY_WEBSITE);

        when(repository.save(any(ReferralSource.class))).thenReturn(added);

        ReferralSourceDTO actual = referralSourceService.add(request);
        assertEquals(request.getName(), actual.name());
        assertEquals(request.getReferralSourceType(), actual.referralSourceType());
        assertNotNull(actual.id());
    }

    @Test
    public void testFindAll_happyPath() {
        ReferralSource ref_1 = new ReferralSource(43L, REF_NAME + "-app", ReferralSourceType.APP);
        ReferralSource ref_2 = new ReferralSource(44L, REF_NAME + "-web", ReferralSourceType.COMPANY_WEBSITE);
        List<ReferralSource> existing = List.of(ref_1, ref_2);

        when(repository.findAll()).thenReturn(existing);

        List<ReferralSourceDTO> actual = referralSourceService.findAll();

        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toReferralSourceDTO(ref_2)));
        assertTrue(actual.contains(MapperUtil.toReferralSourceDTO(ref_1)));
    }

    @Test
    public void testFindAllBySourceType_happyPath() {
        ReferralSource refSearch_1 = new ReferralSource(31L, REF_NAME + "-search1", ReferralSourceType.SEARCH);
        ReferralSource refSearch_2 = new ReferralSource(32L, REF_NAME + "-search2", ReferralSourceType.SEARCH);
        List<ReferralSource> existingSearch = List.of(refSearch_1, refSearch_2);

        // TEST SEARCH
        when(repository.findAllByReferralSourceType(ReferralSourceType.SEARCH)).thenReturn(existingSearch);
        List<ReferralSourceDTO> actualSearch = referralSourceService.findAllBySourceType(ReferralSourceType.SEARCH);
        assertEquals(existingSearch.size(), actualSearch.size());
        assertTrue(actualSearch.contains(MapperUtil.toReferralSourceDTO(refSearch_1)));
        assertTrue(actualSearch.contains(MapperUtil.toReferralSourceDTO(refSearch_2)));

    }
}