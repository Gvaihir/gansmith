package mock

import shared.SharedMessages
import org.scalajs.dom
import org.scalajs.dom.html

object Hello {
  def main(args: Array[String]): Unit = {
    dom.document.getElementById("scalaJSshoutout").textContent = SharedMessages.itWorks
    println("Hello world!")
  }
}
