package com.aleinin.goty.game

import org.springframework.stereotype.Service

@Service
class GameSearchService(private val gameSearchClient: GameSearchClient) {

    fun search(request: GameSearchRequest) =
        gameSearchClient.findGames(request.title, request.year, request.mainGame, request.limit)
}
