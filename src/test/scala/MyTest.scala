import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MySpec extends Specification {

  "MyClass" should {

    "Do something" in {
      true must_== true
    }

  }

}
