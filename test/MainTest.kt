import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MainTest {
    @Test
    fun hiTest() {
        val wave = "Hi"
        assertEquals("Hi there!", "$wave there!")
    }

    @Test
    fun shouldFail() {
        assertTrue(false)
    }
}