import org.junit.Assert.assertEquals
import org.example.Practice

class PracticeTest {
  @org.junit.Test
  def test1():Unit = {
    var expected : Int =3
    var actual : Int = Practice.divide(30,10)
    assertEquals(expected, actual)
}
}
