import PlayingCard.Suit.Companion.SUIT_STRINGS
import PlayingCard.Value.Companion.CARD_VALUES
import PlayingCard.Value.Companion.of
import java.util.*

data class PlayingCard(val value: Value, val suit: Suit) {
    constructor(cardString: String) :
            this(
                Value((if (cardString.length == 2) 1 else 2).let { cardString.substring(0, it) }),
                Suit(cardString[cardString.length - 1])
            ) {
        require(cardString.length in 2..3) { "Playing Card string value must be exactly 2 or 3 characters – value and suit. Example: KS, 4♥, 7D, A♣, etc." }
    }

    val lowValue get() = this.value.lowValue
    val highValue get() = this.value.highValue
    val isBlack get() = suit.isBlack
    val isRed get() = suit.isRed

    override fun toString() = "$value$suit"

    companion object {
        fun String.asPlayingCard() = PlayingCard(this)
    }

    data class Value(val value: UInt) {
        init {
            require(value in CARD_VALUES) { "Value of playing card must be between 1 and 13 inclusively." }
        }

        val lowValue get() = value
        val highValue get() = if (value == 1u) 14u else value

        override fun toString() = when (value) {
            1u -> "A"
            11u -> "J"
            12u -> "Q"
            13u -> "K"
            else -> "$value"
        }

        companion object {
            val CARD_VALUES = 1u..13u

            operator fun invoke(letterValue: String) = when (letterValue.uppercase()) {
                "A" -> 1u
                "J" -> 11u
                "Q" -> 12u
                "K" -> 13u
                else -> letterValue.toUInt()
            }.let(::Value)

            infix fun String.of(letterSuit: String) = PlayingCard(Value(this), Suit(letterSuit))
            infix fun Int.of(letterSuit: String) = this.toUInt().of(letterSuit)
            infix fun UInt.of(letterSuit: String) = PlayingCard(Value(this), Suit(letterSuit))
        }
    }

    class Suit {
        private val value: String

        val isBlack get() = value in listOf("♠", "♣")
        val isRed get() = value in listOf("♦", "♥")

        constructor(value: String) {
            this.value = when (value.uppercase()) {
                "S" -> "♠"
                "D" -> "♦"
                "C" -> "♣"
                "H" -> "♥"
                else -> value
            }
            require(
                this.value in SUIT_STRINGS
            ) { "Suit of Playing card must be Spade(♠,S), Diamond(♦,D), Club(♣,C), or Heart(♥,H)" }
        }

        constructor(letterSuit: Char) : this(letterSuit.toString())

        override fun toString() = value

        override fun equals(other: Any?) =
            (this === other) || ((other is Suit) && this.value == other.value)

        override fun hashCode() = Objects.hash(this.value)

        companion object {
            val SUIT_STRINGS = listOf(
                "♠",
                "♦",
                "♣",
                "♥"
            )
        }
    }

    /* Deck of Playing Cards */

    data class Deck(private val value: List<PlayingCard>) : List<PlayingCard> by value {
        fun shuffled() = value.shuffled().let(::Deck)

        fun draw(): Pair<PlayingCard, Deck> {
            return Pair(value[0], Deck(value.subList(1, size)))
        }

        override fun toString() = value.toString()

        companion object {
            fun standard() =
                SUIT_STRINGS.flatMap { suit ->
                    CARD_VALUES.map { it of suit }
                }.let(::Deck)
        }
    }
}
