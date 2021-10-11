import * as React from 'react'
import styled from 'styled-components'
import { useState } from 'react'
import NewQuestion from '../NewQuestion'
export default function AddQuestion({ saveQuestion }) {
  const [question, setQuestions] = useState({
    questionText: '',
    answers: [
      {
        answerText: '',
        correct: false,
      },
      {
        answerText: '',
        correct: false,
      },
    ],
  })

  const handleAnswerTextInput = (e, index) => {
    const newQuestionObject = { ...question }
    newQuestionObject.answers[index].answerText = e.target.value

    setQuestions(newQuestionObject)
  }

  const handleAnswerCorrectStatusChange = (e, index) => {
    const newQuestionObject = { ...question }

    for (let i = 0; i < newQuestionObject.answers.length; i++) {
      newQuestionObject.answers[i].correct = i === index
    }
    console.log(newQuestionObject)
    setQuestions(newQuestionObject)
  }

  const createNewAnswerOption = () => {
    const newQuestionObject = { ...question }
    newQuestionObject.answers.push({
      answerText: '',
      correct: false,
    })
    setQuestions(newQuestionObject)
  }

  const handleQuestionTextInput = e => {
    setQuestions({ ...question, questionText: e.target.value })
  }
  return (
    <section>
      <Heading>Add Question Page</Heading>
      <FormContainer>
      <NewQuestion
        question={question}
        answers={question.answers}
        handleAnswerTextInput={handleAnswerTextInput}
        handleAnswerCorrectStatusChange={handleAnswerCorrectStatusChange}
        handleQuestionTextInput={handleQuestionTextInput}
      />
      </FormContainer>
      <ButtonContainer>
      <Button onClick={createNewAnswerOption}>Create Answeroption</Button>
      <Button
        onClick={() => {
          saveQuestion(question)
        }}
      >
        Save Question
      </Button>
      </ButtonContainer>
    </section>
  )
}

const Heading=styled.h2`
  font-family: 'Montserrat', sans-serif;
  text-align: center;
  
`
const FormContainer=styled.section`
  display: flex;
  justify-content: center;
  font-family: 'Montserrat', sans-serif;
`
const ButtonContainer=styled.section`
 align-content: center;
`
const Button =styled.button`
  box-shadow: inset 0px 1px 0px 0px #ffffff;
  background-color: #757780;
  border-radius: 6px;
  border: 1px solid #dcdcdc;
  display: inline-block;
  cursor: pointer;
  color: white;
  font-family: 'Montserrat', sans-serif;
  font-size: 15px;
  font-weight: bold;
  padding: 6px 24px;
  text-decoration: none;

  &:hover {
    background: linear-gradient(to bottom, #dfdfdf 5%, #ededed 100%);
    background-color: #dfdfdf;
    color: #757780;
  }
  &:active {
    position: relative;
    top: 1px;
  }
    
`