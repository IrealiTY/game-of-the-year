package com.aleinin.goty.result

import com.aleinin.goty.SubmissionDataHelper
import com.aleinin.goty.submit.Submission
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
internal class ResultServiceTest {
    @Mock
    lateinit var gameRankingService: GameRankingService

    @Mock
    lateinit var gameScoringService: GameScoringService

    @InjectMocks
    lateinit var resultsService: ResultService

    private val mockRankedOldGame = listOf(
        RankedGameResult(
            id = "mostAnticipated",
            title = "mostAnticipated",
            votes = 1,
            rank = 0
        )
    )
    private val mockRankedAnticipated = listOf(
        RankedGameResult(
            id = "bestOldGame",
            title = "bestOldGame",
            votes = 1,
            rank = 0
        )
    )
    private val mockGamesOfTheYear = listOf(
        ScoredGameResult(
            id = "a game",
            title = "a game",
            points = 0,
            votes = 1,
            rank = 0
        )
    )

    @Test
    fun `Should convert a list of Submission into a ResultResponse`() {
        val submissions = SubmissionDataHelper.everything()
        val expectedParticipants = submissions.map { it.name }
        val expectedGiveawayParticipants = submissions.filter { it.enteredGiveaway }.map { it.name }
        whenever(gameScoringService.score(any())).thenReturn(mockGamesOfTheYear)
        whenever(gameRankingService.rank(submissions.mapNotNull { it.mostAnticipated }))
            .thenReturn(mockRankedAnticipated)
        whenever(gameRankingService.rank(submissions.mapNotNull { it.bestOldGame })).thenReturn(mockRankedOldGame)
        val expected = ResultResponse(
            gamesOfTheYear = mockGamesOfTheYear,
            mostAnticipated = mockRankedAnticipated,
            bestOldGame = mockRankedOldGame,
            participants = expectedParticipants,
            giveawayParticipants = expectedGiveawayParticipants
        )
        val actual = resultsService.calculate(submissions)
        assertEquals(expected, actual)
        Mockito.verify(gameScoringService, Mockito.times(1)).score(any())
        Mockito.verify(gameRankingService, Mockito.times(2)).rank(any())
    }

    @Test
    fun `Should convert a list of a single Submission into a ResultResponse`() {
        val submissions = listOf(SubmissionDataHelper.maximal())
        val mostAnticipated = listOf(submissions[0].mostAnticipated ?: throw AssertionError())
        val bestOldGame = listOf(submissions[0].bestOldGame ?: throw AssertionError())
        whenever(gameScoringService.score(any())).thenReturn(mockGamesOfTheYear)
        whenever(gameRankingService.rank(mostAnticipated)).thenReturn(mockRankedAnticipated)
        whenever(gameRankingService.rank(bestOldGame)).thenReturn(mockRankedOldGame)
        val expected =
            ResultResponse(
                gamesOfTheYear = mockGamesOfTheYear,
                mostAnticipated = mockRankedAnticipated,
                bestOldGame = mockRankedOldGame,
                participants = listOf(submissions[0].name),
                giveawayParticipants = listOf(submissions[0].name)
            )
        val actual = resultsService.calculate(submissions)
        assertEquals(expected, actual)
        Mockito.verify(gameScoringService, Mockito.times(1)).score(any())
        Mockito.verify(gameRankingService, Mockito.times(1)).rank(mostAnticipated)
        Mockito.verify(gameRankingService, Mockito.times(1)).rank(bestOldGame)
    }

    @Test
    fun `Should convert an emptyList into a ResultResponse`() {
        val submissions = emptyList<Submission>()
        val expected = ResultResponse(
            gamesOfTheYear = emptyList(),
            mostAnticipated = emptyList(),
            bestOldGame = emptyList(),
            participants = emptyList(),
            giveawayParticipants = emptyList()
        )
        val actual = resultsService.calculate(submissions)
        assertEquals(expected, actual)
    }
}