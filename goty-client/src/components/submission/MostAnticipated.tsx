import React from 'react'
import { useStore } from 'react-redux'
import { Game } from '../../api/gameService'
import { createUpdateMostAnticipatedAction } from '../../state/submission/actions'
import { generateRules } from '../../util/generate-rules'
import { SingleGame } from './shared/SingleGame'

export interface MostAnticipatedProps {
  readonly: boolean
  mostAnticipated: Game | null
}

const rules: (string | JSX.Element)[] = ['Anything not released']

export const MostAnticipated = (props: MostAnticipatedProps) => {
  const store = useStore()
  const handleSelect = (mostAnticipated: Game | null) => {
    if (!props.readonly) {
      store.dispatch(createUpdateMostAnticipatedAction(mostAnticipated))
    }
  }
  return (
    <SingleGame
      title="What game are you looking forward to most?"
      readonly={props.readonly}
      game={props.mostAnticipated}
      handleSelect={handleSelect}
      content={generateRules(props.readonly, rules)}
    />
  )
}
