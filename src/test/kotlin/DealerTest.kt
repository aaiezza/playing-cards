import PlayingCard.Value.Companion.of
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class DealerTest {
    lateinit var subject: Dealer

    @BeforeEach
    fun init() {
        subject = Dealer()
    }

    @Test
    fun `should fail deal to two hands`() {
        val deck = PlayingCard.Deck.standard()
        assertFailsWith<IllegalArgumentException> { subject.dealCards(deck, 5u, 12u) }
    }

    @Test
    fun `should fail deal to 0 hands`() {
        val deck = PlayingCard.Deck.standard()
        assertFailsWith<IllegalArgumentException> { subject.dealCards(deck, 0u, 12u) }
    }

    @Test
    fun `should fail deal to 0 cards`() {
        val deck = PlayingCard.Deck.standard()
        assertFailsWith<IllegalArgumentException> { subject.dealCards(deck, 3u, 0u) }
    }

    @Test
    fun `should deal to two hands`() {
        val deck = PlayingCard.Deck.standard()

        val (hands, newDeck) = subject.dealCards(deck, 2u, 7u)

        assertThat(newDeck.size).isEqualTo(38)
        assertThat(hands[0]).isEqualTo(
            Hand(
                "A" of "S", 3 of "S", 5 of "S", 7 of "S", 9 of "S", "J" of "S", "K" of "S"
            )
        )
        assertThat(hands[1]).isEqualTo(
            Hand(
                2 of "S", 4 of "S", 6 of "S", 8 of "S", 10 of "S", "Q" of "S", "A" of "D"
            )
        )
    }
}