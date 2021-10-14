package de.neuefische.devquiz.service;

import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.repo.QuestionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class QuestionServiceTest {

    QuestionRepo questionRepo = mock(QuestionRepo.class);
    QuestionService questionService;

    @Test
    @DisplayName("returns a list of all existing questions")
    void listQuestions() {

    }

    @Test
    @DisplayName("Should throw a exception when the given id is not in the db")
    void testGet_IdNotFound() {
        //GIVEN
        Question questionToAdd = new Question(
           "205",
           "Question with id '205'",
           null
        );
        questionRepo.save(questionToAdd);

        when(questionRepo.findById("209")).thenThrow(NullPointerException.class);

        //WHEN
        verify(questionRepo).save(questionToAdd);
        Assertions.assertThrows(NullPointerException.class, () -> {
            questionService.get("209");
        });


    }

    @Test
    void addQuestion() {
    }
}