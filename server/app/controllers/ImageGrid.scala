package controllers
import javax.inject._
import play.api.mvc._

@Singleton
class ImageGrid @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def displayImages() = Action {
    Ok("Here is the image grid")
  }
}


