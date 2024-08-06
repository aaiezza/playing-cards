class Dealer {
    fun dealCards(deck: PlayingCard.Deck, numberOfHands: UInt, cardsPerHand: UInt):
            Pair<Array<Hand>, PlayingCard.Deck> {
        require(deck.size >= numberOfHands.toInt() * cardsPerHand.toInt()) { "Not enough cards in the deck to deal ${numberOfHands * cardsPerHand}" }
        require(numberOfHands > 0u) { "Number of hands must be greater than 0" }
        return dealCards(deck, Array(numberOfHands.toInt()) { Hand() }, cardsPerHand)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun dealCards(deck: PlayingCard.Deck, hands: Array<Hand>, cardsPerHand: UInt):
            Pair<Array<Hand>, PlayingCard.Deck> {
        require(deck.size >= hands.size * cardsPerHand.toInt()) { "Not enough cards in the deck to deal ${hands.size.toUInt() * cardsPerHand}" }
        require(hands.isNotEmpty()) { "Number of hands must be greater than 0" }
        require(cardsPerHand > 0u) { "Number of cards per hand must be greater than 0" }

        var newHands: Array<Hand> = hands
        var dealing = deck

        (0..<cardsPerHand.toInt()).forEach { _ ->
            newHands.indices.forEach { h ->
                dealing.dealTo(newHands, h).let {
                    newHands = it.first
                    dealing = it.second
                }
            }
        }

        return Pair(newHands, dealing)
    }

    companion object {
        private fun PlayingCard.Deck.dealTo(
            hands: Array<Hand>,
            h: Int
        ): Pair<Array<Hand>, PlayingCard.Deck> {
            val (card, remainingDeck) = this.draw()

            val newHands: Array<Hand> = Array(hands.size) {
                if (it == h)
                    hands[it] + card
                else hands[it]
            }

            return Pair(newHands, remainingDeck)
        }
    }
}
