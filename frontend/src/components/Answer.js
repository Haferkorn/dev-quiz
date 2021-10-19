import * as React from 'react'
import styled from 'styled-components'
import {useEffect, useState} from "react";

function Answer({ answer, questionId,handleChoice,correctAnswerId }) {
    const [idState, setIdState]=useState("")

    useEffect(()=>{
        validateID()
    },[correctAnswerId])

    function validateID(){
        if (correctAnswerId===""){
            setIdState("initial")
        }else if(answer.id===correctAnswerId){
            setIdState("correct")
        }
        else{
            setIdState("not-correct")
        }

    }

  return (
    <AnswerContainer colorValue={idState}>
      <input type="radio" name={questionId} onChange={()=>handleChoice(answer.id)} />
      <h4>{answer.answerText}</h4>
    </AnswerContainer>
  )
}
export default Answer

const AnswerContainer = styled.section`
  display: flex;
  align-items: center;
  gap: 5px;
  border-radius: 20px;
  margin: 10px;
  background-color: ${props=>
          props.colorValue==="initial" ? "white" :
                  props.colorValue==="correct"? "#A4D4B4": "#FCAA90"}
`
