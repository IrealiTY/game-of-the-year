package com.aleinin.goty.submit

data class SubmissionRequest(
    val name: String,
    val gamesOfTheYear: List<RankedGameSubmission>,
    val mostAnticipated: GameSubmission?,
    val bestOldGame: GameSubmission?,
    val enteredGiveaway: Boolean = false
) {
    init {
        require(name.isNotBlank()) { "name cannot be blank"}
        require(gamesOfTheYear.isNotEmpty()) { "gamesOfTheYear cannot be empty"}
    }
}
