package com.aleinin.goty.submit

data class SubmissionRequest(
    val name: String,
    val gamesOfTheYear: List<RankedGameSubmission>,
    val mostAnticipated: GameSubmission?,
    val bestOldGame: GameSubmission?,
    val enteredGiveaway: Boolean = false,
)
