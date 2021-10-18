import * as React from 'react'
import Answer from './Answer'
import styled from 'styled-components'
import {useState} from "react";
import {validateAnswers} from "../service/DevQuizApiService";

function Question({ question }) {

    const [answerIdState,setAnswerIdState]=useState("")
    const [correctAnswerIdState,setCorrectAnswerIdState]=useState("initial")
    const [correctAnswerID,setCorrectAnswerID]=useState("")

    const handleChoice=answerId=>setAnswerIdState(answerId);

    function validateQuestion(){
        const validateObject = {
            questionID: question.id,
            answerID: answerIdState
        }
        validateAnswers(validateObject).then(result=>{
            setCorrectAnswerID(result.answerID)
            if(JSON.stringify(result)===JSON.stringify(validateObject)){
                setCorrectAnswerIdState("correct");
            }else{
                setCorrectAnswerIdState("not-correct");
        }}
        )
    }

  return (
    <QuestionContainer state={correctAnswerIdState}>
      <h3>{question.questionText}</h3>
      <AnswerContainer>
        {question.answers.map(answer => (
          <Answer
              answer={answer}
              key={answer.id}
              questionId={question.id}
              handleChoice={handleChoice}
              correctAnswerId={correctAnswerID}
              />
        ))}
      </AnswerContainer>
      <CheckButton onClick={validateQuestion}>Check Answer</CheckButton>
    </QuestionContainer>
  )
}
export default Question

const QuestionContainer = styled.section`
  width: 400px;
  border: 1px solid #009fb7;
  border-radius: 20px;
  padding: 20px;
  background-color: ${props=>
                  props.state==="initial" ? "white" : 
                  props.state==="correct" ? "#4E6E5D": '#A53F2B'};
  font-family: 'Montserrat', sans-serif;
);
`

const AnswerContainer = styled.section`
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 0 0;
  grid-template-areas:
    '. .'
    '. .';
`
const CheckButton = styled.button`
  box-shadow: inset 0 1px 0 0 #ffffff;
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
