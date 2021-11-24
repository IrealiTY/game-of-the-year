package com.aleinin.goty.game

import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.utils.Endpoints
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableList.toImmutableList
import org.springframework.stereotype.Component
import java.time.Year
import java.util.stream.StreamSupport


@Component
class GameSearchService(
    private val igdbWrapper: IGDBWrapper,
    private val objectMapper: ObjectMapper,
) {

    fun search(gameSearchRequest: GameSearchRequest): ImmutableList<GameSearchResponse> {
        val request = APICalypse()
                .fields("id, name")
                .search(gameSearchRequest.title)
                .where(buildWhere(gameSearchRequest.year, gameSearchRequest.mainGame))
                .limit(gameSearchRequest.limit)
        val response = igdbWrapper.apiJsonRequest(Endpoints.GAMES, request.buildQuery())
        val responseTree = objectMapper.readTree(response)
        return StreamSupport.stream(responseTree.spliterator(), false)
                .map { game: JsonNode? ->
                    GameSearchResponse(id = game?.get("id")!!.asText(), title = game.get("name")!!.asText())
                }
                .collect(toImmutableList())
    }

    fun buildWhere(year: Int? = Year.now().value, mainGame: Boolean) =
            if(mainGame) {
                "release_dates.y = $year & category = 0"
            } else {
                "release_dates.y = $year"
            }
}
