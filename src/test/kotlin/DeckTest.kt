import PlayingCard.Value.Companion.of
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class DeckTest {
    @Test
    fun `should create and shuffle Deck`() {
        val deck = PlayingCard.Deck.standard()

        println(deck)
        assertThat(deck.size).isEqualTo(52)

        val shuffledDeck = deck.shuffled()

        assertThat(shuffledDeck.size).isEqualTo(52)
    }

    @Test
    fun `should draw top card of Deck`() {
        val (topCard, deck) = PlayingCard.Deck.standard().draw()

        assertThat(topCard).isEqualTo("A" of "S")
        assertThat(deck.size).isEqualTo(51)
    }
}