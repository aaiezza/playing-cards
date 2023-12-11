import java.util.*

data class PlayingCard(val value: Value, val suit: Suit) {
    constructor(letterValue: String) :
            this(Value(letterValue[0]), Suit(letterValue[1])) {
        require(letterValue.length == 2) { "Playing Card string value must be exactly 2 characters – value and suit. Example: KS, 4♥, 7D, A♣, etc." }
    }

    override fun toString() = "$value$suit"

    companion object {
        fun String.asPlayingCard() = PlayingCard(this)
    }

    data class Value(val value: UInt) {
        init {
            require(value in 0u..12u) { "Value of playing card must be between 0 and 12 inclusively." }
        }

        override fun toString() = when (value) {
            0u -> "A"
            in 1u..9u -> "$value"
            10u -> "J"
            11u -> "Q"
            12u -> "K"
            else -> throw IllegalStateException()
        }

        companion object {
            operator fun invoke(letterValue: Char) = Value(letterValue.toString())

            operator fun invoke(letterValue: String) = when (letterValue.uppercase()) {
                "A" -> 0u
                in "1".."9" -> letterValue.toUInt()
                "J" -> 10u
                "Q" -> 11u
                "K" -> 12u
                else -> 100u
            }.let(::Value)

            infix fun String.of(letterSuit: String) = PlayingCard(Value(this), Suit(letterSuit))
            infix fun Int.of(letterSuit: String) = this.toUInt().of(letterSuit)
            infix fun UInt.of(letterSuit: String) = PlayingCard(Value(this), Suit(letterSuit))
        }
    }

    class Suit {
        private val value: String

        constructor(value: String) {
            this.value = when (value.uppercase()) {
                "S" -> "♠"
                "D" -> "♦"
                "C" -> "♣"
                "H" -> "♥"
                in SUIT_STRINGS -> value
                else -> ""
            }
            require(
                this.value in SUIT_STRINGS
            ) { "Suit of Playing card must be Spade(♠), Diamond(♦), Club(♣), or Heart(♥)" }
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
}