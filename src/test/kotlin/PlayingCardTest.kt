import PlayingCard.Companion.asPlayingCard
import PlayingCard.Value.Companion.of
import assertk.assertThat
import assertk.assertions.hasToString
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class PlayingCardTest {
    @Test
    fun `should fail to make impossible card suit`() {
        assertFailsWith(IllegalArgumentException::class) { PlayingCard.Suit("G") }
    }

    @Test
    fun `should fail to make impossible card value`() {
        assertFailsWith(IllegalArgumentException::class) { PlayingCard.Value(14u) }
    }

    @Test
    fun `should fail to make impossible card`() {
        assertFailsWith(IllegalArgumentException::class) { PlayingCard("ADS") }
        assertFailsWith(IllegalArgumentException::class) { PlayingCard("6T") }
    }

    @Test
    fun `should make card A♦`() {
        val expectedCardValue = PlayingCard.Value("A")
        val expectedCardSuit = PlayingCard.Suit("D")

        listOf(
            PlayingCard("AD"),
            "AD".asPlayingCard(),
            "A" of "D",
            "A".of("d"),
        ).forEach { card ->
            assertThat(card.value).isEqualTo(expectedCardValue)
            assertThat(card.suit).isEqualTo(expectedCardSuit)
            assertThat(card).hasToString("A♦")
        }
    }

    @Test
    fun `should make card 7♥`() {
        val expectedCardValue = PlayingCard.Value(7u)
        val expectedCardSuit = PlayingCard.Suit("♥")

        listOf(
            PlayingCard(PlayingCard.Value(7u), PlayingCard.Suit("H")),
            PlayingCard("7H"),
            7 of "♥",
            7u of "♥",
            7 of "H",
            "7".of("h"),
        ).forEach { card ->
            assertThat(card.value).isEqualTo(expectedCardValue)
            assertThat(card.suit).isEqualTo(expectedCardSuit)
            assertThat(card).hasToString("7♥")
        }
    }
}