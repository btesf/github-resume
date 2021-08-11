package ml.bereket.githubresume.service;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ResumeServiceTest {

    private ResumeService resumeService;

    @BeforeEach
    void setUp() {
        resumeService = new ResumeServiceImpl();
    }
}