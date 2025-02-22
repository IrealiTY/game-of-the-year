import axios from 'axios'
import { Properties } from '../state/properties/reducer'
import { dateStringToTwelveHourString } from '../util/time'

export interface BackendProperties {
  tiePoints: number[]
  gotyYear: number
  deadline: string
  hasGiveaway: boolean
  giveawayAmountUSD: number
}

const toProperties = ({
  tiePoints,
  gotyYear,
  deadline,
  hasGiveaway,
  giveawayAmountUSD,
}: BackendProperties): Properties => ({
  tiePoints,
  year: gotyYear,
  deadline: dateStringToTwelveHourString(deadline),
  hasGiveaway,
  giveawayAmountUSD,
  maxGamesOfTheYear: tiePoints.length,
  isGotyConcluded: new Date(deadline) <= new Date(),
})

export const propertiesService = {
  getProperties: (): Promise<Properties> =>
    axios
      .get<BackendProperties>('/properties')
      .then((axiosResponse) => toProperties(axiosResponse.data)),
}
