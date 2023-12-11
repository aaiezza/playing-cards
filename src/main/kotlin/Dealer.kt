class Dealer {
    @OptIn(ExperimentalStdlibApi::class)
    fun dealCards(deck: PlayingCard.Deck, numberOfHands: UInt, cardsPerHand: UInt):
            Pair<Array<List<PlayingCard>>, PlayingCard.Deck> {
        require(deck.size >= numberOfHands * cardsPerHand) { "Not enough cards in the deck to deal ${numberOfHands * cardsPerHand}" }
        require(numberOfHands > 0u) { "Number of hands must be greater than 0" }
        require(cardsPerHand > 0u) { "Number of cards per hand must be greater than 0" }

        var hands: Array<List<PlayingCard>> = Array(numberOfHands.toInt()) { listOf() }
        var dealing = deck

        (0..< cardsPerHand.toInt()).forEach { _ ->
            (0..<numberOfHands.toInt()).forEach { h ->
                dealing.dealTo(hands, h).let {
                    hands = it.first
                    dealing = it.second
                }
            }
        }

        return Pair(hands.map { it.toList() }.toTypedArray(), dealing)
    }

    companion object {
        private fun PlayingCard.Deck.dealTo(
            hands: Array<List<PlayingCard>>,
            h: Int
        ): Pair<Array<List<PlayingCard>>, PlayingCard.Deck> {
            val (card, remainingDeck) = this.draw()

            val newHands: Array<List<PlayingCard>> = Array(hands.size) {
                if (it == h)
                    hands[it].plus(card)
                else hands[it]
            }

            return Pair(newHands, remainingDeck)
        }
    }
}