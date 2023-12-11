data class Hand(val value: List<PlayingCard>) {
    constructor() : this(emptyList())
    constructor(vararg cards: PlayingCard) : this(cards.toList())
    operator fun plus(card: PlayingCard) = Hand(value + card)
    override fun toString() = value.toString()
}