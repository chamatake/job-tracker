CREATE TABLE IF NOT EXISTS company (
	id BIGINT NOT NULL AUTO_INCREMENT,
	company_name VARCHAR(1000) NOT NULL,
	CONSTRAINT PK_Company PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS referral_source (
	id BIGINT NOT NULL AUTO_INCREMENT,
	referral_name VARCHAR(500) NOT NULL,
	referral_source_type VARCHAR(100) NOT NULL,

	CONSTRAINT PK_ReferralSource PRIMARY KEY (id),
	CONSTRAINT CK_ReferralSource_ReferralSourceType_enum
		CHECK (referral_source_type IN ('APP', 'COMPANY_WEBSITE', 'SEARCH', 'INTERNAL'))
);

CREATE TABLE IF NOT EXISTS job_posting (
	id BIGINT NOT NULL AUTO_INCREMENT,
	company_id BIGINT NOT NULL,
	title VARCHAR(1000) NOT NULL,
	requisition_id VARCHAR(100),
	posting_url VARCHAR(2000),
	salary_range_min INT,
	salary_range_max INT,
	office_situation VARCHAR(100),
	required_tech VARCHAR(5000), -- will come as a comma delimited string from Java
	preferred_tech VARCHAR(5000), -- will come as a comma delimited string from Java
	referral_source_id BIGINT,
	`VERSION` BIGINT,

	CONSTRAINT PK_JobPosting PRIMARY KEY (id),
	CONSTRAINT FK_JobPosting_CompanyId FOREIGN KEY (company_id)
		REFERENCES company(id) ON UPDATE CASCADE,
	CONSTRAINT FK_JobPosting_ReferralSourceId FOREIGN KEY (referral_source_id)
		REFERENCES referral_source(id) ON UPDATE CASCADE,
	CONSTRAINT CK_JobPosting_OfficeSituation_enum
		CHECK (office_situation IN ('HYBRID', 'ONSITE', 'REMOTE', 'UNKNOWN'))
);

CREATE TABLE IF NOT EXISTS job_application (
	id BIGINT NOT NULL AUTO_INCREMENT,
	job_posting_id BIGINT,
	current_status_id BIGINT,
	applied_date DATE,
	resume_filename VARCHAR(1000),
	cover_letter_filename VARCHAR(1000),
	`VERSION` BIGINT,

	CONSTRAINT PK_JobApplication PRIMARY KEY (id),
	CONSTRAINT FK_JobApplication_JobPostingId FOREIGN KEY (job_posting_id)
		REFERENCES job_posting(id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS application_status (
	id BIGINT NOT NULL AUTO_INCREMENT,
	job_application_id BIGINT NOT NULL,
	application_status_type VARCHAR(100) NOT NULL,
	active_date DATE,
	inactive_date DATE,
	`VERSION` BIGINT,

	CONSTRAINT PK_ApplicationStatus PRIMARY KEY (id),
	CONSTRAINT FK_ApplicationStatus_JobApplicationId FOREIGN KEY (job_application_id)
		REFERENCES job_application(id) ON UPDATE CASCADE,
	CONSTRAINT CK_ApplicationStatus_ApplicationStatusType_enum
		CHECK (application_status_type IN ('APPLIED', 'REJECTED', 'SCREENING', 'CODE_ASSESSMENT', 'INTERVIEWING'))
);

ALTER TABLE job_application
    ADD CONSTRAINT FK_JobApplication_CurrentStatusId FOREIGN KEY (current_status_id)
        REFERENCES application_status(id) ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS interview (
	id BIGINT NOT NULL AUTO_INCREMENT,
	job_application_id BIGINT NOT NULL,
	interview_type VARCHAR(100) NOT NULL,
	interview_date DATE,
	notes VARCHAR(10000),
	`VERSION` BIGINT,

	CONSTRAINT PK_Interview PRIMARY KEY (id),
	CONSTRAINT FK_Interview_JobApplicationId FOREIGN KEY (job_application_id)
		REFERENCES job_application(id) ON UPDATE CASCADE,
	CONSTRAINT CK_Interview_InterviewType_enum
		CHECK (interview_type IN ('INITIAL_SCREEN', 'CODING_ASSESSMENT', 'FIRST_INTERVIEW', 'TECHNICAL_INTERVIEW', 'FINAL_INTERVIEW', 'MIDDLE_INTERVIEW'))
);