package bwg.jobTracker.job_tracker.repository;

import bwg.jobTracker.job_tracker.entity.ReferralSource;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralSourceRepository extends JpaRepository<ReferralSource, Long> {
    List<ReferralSource> findAllByReferralSourceType(ReferralSourceType referralSourceType);
}
