package de.neuefische.devquiz.service;

import de.neuefische.devquiz.model.Answer;
import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.model.ValidationInfo;
import de.neuefische.devquiz.repo.QuestionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class QuestionServiceTest {

    QuestionRepo questionRepo = mock(QuestionRepo.class);
    QuestionService questionService = new QuestionService(questionRepo);

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
    void validateQuestion() {
        //GIVEN
        Question questionToValidate1 = new Question(
                "205",
                "Question with id '205'",
                List.of(new Answer("1", "Antwort, die es zu validieren gilt",true))

        );

        //WHEN
        when(questionRepo.findById("205")).thenReturn(Optional.of(questionToValidate1));

        //THEN
        ValidationInfo validationInfo1 = new ValidationInfo("205", "1");
        ValidationInfo actual = questionService.validateQuestion(validationInfo1);
        Assertions.assertEquals(validationInfo1, actual);



    }

    @Test
    void validateQuestion2() {
        //GIVEN
        Question questionToValidate1 = new Question(
                "205",
                "Question with id '205'",
                List.of(new Answer("1", "Antwort, die es zu validieren gilt",false),new Answer("2", "Antwort, die es zu validieren gilt",true))

        );

        //WHEN
        when(questionRepo.findById("205")).thenReturn(Optional.of(questionToValidate1));

        //THEN
        ValidationInfo validationInfo1 = new ValidationInfo("205", "1");
        ValidationInfo actual = questionService.validateQuestion(validationInfo1);
        Assertions.assertEquals(new ValidationInfo("205", "2"), actual);



    }




}