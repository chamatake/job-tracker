package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.repository.ReferralSourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferralSourceServiceTest {
    @Mock
    private ReferralSourceRepository repository;

    private ReferralSourceService referralSourceService;

    @BeforeEach
    void setup() {
        referralSourceService = new ReferralSourceService(repository);
    }

    @Test
    public void testAdd_happyPath() {

    }
}