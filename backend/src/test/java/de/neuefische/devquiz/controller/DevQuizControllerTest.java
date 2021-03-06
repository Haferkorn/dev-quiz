package de.neuefische.devquiz.controller;

import de.neuefische.devquiz.model.Answer;
import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.model.ValidationInfo;
import de.neuefische.devquiz.repo.QuestionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DevQuizControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private QuestionRepo questionRepo;

    @BeforeEach
    public void clearDb() {
        questionRepo.deleteAll();
    }

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Should return a list with all questions from db")
    void testListQuestion() {
        // GIVEN
        questionRepo.save(new Question("1", "Question with ID '1'", null));
        questionRepo.save(new Question("2", "Question with ID '2'", null));
        questionRepo.save(new Question("3", "Question with ID '3'", null));
        // WHEN
        ResponseEntity<Question[]> responseEntity = testRestTemplate.getForEntity("/api/question", Question[].class);
        // THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContainingInAnyOrder(
                new Question("1", "Question with ID '1'", null),
                new Question("2", "Question with ID '2'", null),
                new Question("3", "Question with ID '3'", null)
        ));

    }

    @Test
    @DisplayName("Should return a question object with the given id")
    void testGet() {
        // GIVEN
        Question question = new Question("302", "Question with ID '302'", null);
        questionRepo.save(question);
        // WHEN
        ResponseEntity<Question> responseEntity = testRestTemplate.getForEntity("/api/question/" + question.getId(), Question.class);
        // THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(new Question("302", "Question with ID '302'", null)));
    }

    @Test
    @DisplayName("Should add a new question item to the db")
    void testAddQuestion() {
        // GIVEN
        Question questionToAdd = new Question("22", "This is a question", null);

        // WHEN
        ResponseEntity<Question> postResponseEntity = testRestTemplate.postForEntity("/api/question/", questionToAdd, Question.class);
        Question actual = postResponseEntity.getBody();

        // THEN
        assertThat(postResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(actual);
        assertThat(actual, is(new Question("22", "This is a question", null)));

        // THEN - check via GET
        ResponseEntity<Question> getResponse = testRestTemplate.getForEntity("/api/question/" + questionToAdd.getId(), Question.class);
        Question persistedQuestion = getResponse.getBody();

        assertNotNull(persistedQuestion);
        assertThat(persistedQuestion.getId(), is(questionToAdd.getId()));
        assertThat(persistedQuestion.getQuestionText(), is(questionToAdd.getQuestionText()));
    }

    @Test
    @DisplayName("Should check if Validationinfo Object coming from the Frontend corresponds with correct answer")
    void testToValidate() {
        // GIVEN
        Question questionToValidate = new Question(
                "205",
                "Question with id '205'",
                List.of(new Answer("1", "Antwort, die es zu validieren gilt",true),new Answer("2", "Antwort, die es zu validieren gilt",false))

        );
        ResponseEntity<Question> postResponseEntity = testRestTemplate.postForEntity("/api/question/", questionToValidate, Question.class);
        ValidationInfo validationInfo = new ValidationInfo("205", "1");

        // WHEN
        ResponseEntity<ValidationInfo> postResponseEntity2 = testRestTemplate.postForEntity("/api/question/validate", validationInfo, ValidationInfo.class);
        ValidationInfo actual = postResponseEntity2.getBody();

        // THEN
        assertThat(postResponseEntity2.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(actual);
        assertThat(actual, is(validationInfo));

    }

    @Test
    @DisplayName("Should check if Validationinfo Object coming from the Frontend corresponds not with correct answer")
    void testToValidate2() {
        // GIVEN
        Question questionToValidate = new Question(
                "205",
                "Question with id '205'",
                List.of(new Answer("1", "Antwort, die es zu validieren gilt",false),new Answer("2", "Antwort, die es zu validieren gilt",true))

        );
        testRestTemplate.postForEntity("/api/question/", questionToValidate, Question.class);
        ValidationInfo validationInfoComingFromFrontend = new ValidationInfo("205", "2");

        // WHEN
        ResponseEntity<ValidationInfo> postResponseEntity2 = testRestTemplate.postForEntity("/api/question/validate", validationInfoComingFromFrontend, ValidationInfo.class);
        ValidationInfo actual = postResponseEntity2.getBody();

        // THEN
        assertThat(postResponseEntity2.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(actual);
        assertThat(actual, is(new ValidationInfo("205","2")));

    }
}