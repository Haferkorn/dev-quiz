package de.neuefische.devquiz.repo;

import de.neuefische.devquiz.model.Question;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


class QuestionRepoTest {

    @Test
    void listQuestions() {
        // GIVEN
        QuestionRepo questionRepo = new QuestionRepo();
        questionRepo.addQuestion(new Question("1", "Whats the most beautiful lombock?", null));
        questionRepo.addQuestion(new Question("2", "Whats the most beautiful dog?", null));
        questionRepo.addQuestion(new Question("3", "Whats the most beautiful hamcrest?", null));
        List<Question> actual = List.of(
                new Question("1", "Whats the most beautiful lombock?", null),
                new Question("2", "Whats the most beautiful dog?", null),
                new Question("3", "Whats the most beautiful hamcrest?", null)
        );

        // WHEN
        List<Question> expected = questionRepo.listQuestions();

        // THEN
        assertEquals(expected, actual);

    }

    @Test
    void get() {
        // GIVEN
        QuestionRepo questionRepo = new QuestionRepo();
        questionRepo.addQuestion(new Question("1", "Wie sagt man am besten guten Tag?", null));
        questionRepo.addQuestion(new Question("2", "Was ist das?", null));
        questionRepo.addQuestion(new Question("3", "Question 1238?", null));

        // WHEN
        Question expected1 = new Question("1", "Wie sagt man am besten guten Tag?", null);
        Question expected2 = new Question("2", "Was ist das?", null);
        Question expected3 = new Question("3", "Question 1238?", null);


        // THEN
        assertEquals(Optional.of(expected1), questionRepo.get("1"));
        assertEquals(Optional.of(expected2), questionRepo.get("2"));
        assertEquals(Optional.of(expected3), questionRepo.get("3"));


    }

    @Test
    void addQuestion() {
        // GIVEN
        Question questionToAdd = new Question("4", "Hallo", null);
        QuestionRepo questionRepo = new QuestionRepo();

        // WHEN
        questionRepo.addQuestion(questionToAdd);

        //THEN
        assertEquals(questionRepo.get("4"), Optional.of(questionToAdd));
    }
}