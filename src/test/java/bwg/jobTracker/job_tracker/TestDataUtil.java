package bwg.jobTracker.job_tracker;

import bwg.jobTracker.job_tracker.dto.request.InterviewCreateRequest;
import bwg.jobTracker.job_tracker.entity.*;
import bwg.jobTracker.job_tracker.enums.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.*;

public class TestDataUtil {
    private static final LocalDate START_DATE = LocalDate.parse("2025-03-14");

    public static ApplicationStatus makeApplicationStatus(ApplicationStatusType type, JobApplication parent, @Nullable
    LocalDate activeDate) {
        ApplicationStatus status = new ApplicationStatus();
        status.setId(112233L);
        status.setJobApplication(parent);
        status.setApplicationStatusType(type);
        status.setActiveDate(Objects.isNull(activeDate) ? LocalDate.now() : activeDate);

        return status;
    }

    public static List<ApplicationStatus> makeApplicationStatusList(JobApplication parent, int count) {
        List<ApplicationStatus> statuses = new ArrayList<>();
        Random randomInts = new Random();
        ApplicationStatusType[] enumTypes = ApplicationStatusType.values();

        for (int i = 0; i <= count; i++) {
            ApplicationStatus singleStatus = new ApplicationStatus();
            singleStatus.setId(11011L + i);
            singleStatus.setJobApplication(parent);
            singleStatus.setActiveDate(START_DATE.plusDays(1 + i));
            singleStatus.setApplicationStatusType(enumTypes[randomInts.nextInt(enumTypes.length)]);
            singleStatus.setInactiveDate(LocalDate.now());
            statuses.add(singleStatus);
        }
        return statuses;
    }

    public static Interview makeInterview(InterviewType type) {
        Interview interview = new Interview();
        interview.setJobApplication(new JobApplication());
        interview.setInterviewType(type);
        interview.setInterviewDate(START_DATE);
        interview.setNotes("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt");

        return interview;
    }

    public static InterviewCreateRequest makeInterviewRequest(InterviewType type) {
        InterviewCreateRequest request = new InterviewCreateRequest();
        request.setJobApplication(new JobApplication());
        request.setInterviewType(type);
        request.setInterviewDate(START_DATE);
        request.setNotes("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt");

        return request;
    }

    public static Set<Technology> makeTechSet(int count) {
        Set<Technology> techs = new HashSet<>();
        Random randomInts = new Random();
        Technology[] enumTechs = Technology.values();

        for (int i = 0; i <= count; i++) {
            techs.add(enumTechs[randomInts.nextInt(enumTechs.length)]);
        }
        return techs;
    }
}
