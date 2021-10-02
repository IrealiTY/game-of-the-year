import React, { useState } from 'react'
import { Submission } from '../../models/submission'
import { Giveaway } from '../submission/Giveaway'
import { GOTY } from '../submission/GOTY'
import { MostAnticipated } from '../submission/MostAnticipated'
import { Name } from '../submission/Name'
import { OldGame } from '../submission/OldGame'
import { Paginator } from './Paginator'

export interface SubmissionsProps {
  submissions: Submission[]
}

export const Submissions = (props: SubmissionsProps) => {
  const [index, setIndex] = useState(0)
  return (
    <React.Fragment>
      <Paginator
        totalPages={props.submissions.length}
        pageIndex={index}
        setIndex={setIndex}
      />
      <SubmissionResult
        submission={props.submissions[index]}
      ></SubmissionResult>
    </React.Fragment>
  )
}

export interface SubmissionResultProps {
  submission: Submission
}

const SubmissionResult = (props: SubmissionResultProps) => {
  return (
    <React.Fragment>
      <Name readonly name={props.submission.name} />
      <GOTY readonly games={props.submission.gamesOfTheYear} />
      <OldGame readonly bestOldGame={props.submission.bestOldGame} />
      <MostAnticipated
        readonly
        mostAnticipated={props.submission.mostAnticipated}
      />
      <Giveaway readonly enteredGiveaway={props.submission.enteredGiveaway} />
    </React.Fragment>
  )
}
