import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import { Submission } from '../../api/submissionService'
import {
  selectIsLoading,
  selectSubmissions,
} from '../../state/results/selectors'
import { Card } from '../Card'
import { Loading } from '../Loading'
import { Giveaway } from '../submission/Giveaway'
import { GOTY } from '../submission/GOTY'
import { MostAnticipated } from '../submission/MostAnticipated'
import { Name } from '../submission/Name'
import { OldGame } from '../submission/OldGame'
import { Paginator } from './Paginator'

export interface SubmissionsProps {}

export const Submissions = (props: SubmissionsProps) => {
  const [index, setIndex] = useState(0)
  const isLoading = useSelector(selectIsLoading)
  const submissions = useSelector(selectSubmissions)
  if (isLoading) {
    return <Loading />
  }
  if (!(submissions?.length > 0)) {
    return (
      <Card>
        <h2>No submissions yet</h2>
      </Card>
    )
  }
  return (
    <React.Fragment>
      <Paginator
        totalPages={submissions.length}
        pageIndex={index}
        setIndex={setIndex}
      />
      <SubmissionResult submission={submissions[index]}></SubmissionResult>
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
