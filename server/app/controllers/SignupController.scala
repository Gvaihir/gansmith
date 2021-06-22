package controllers

import models.{DatabaseModel, UserData}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc._
import play.api.i18n._
import play.api.libs.json._

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

@Singleton
class SignupController @Inject() (
                                  protected val dbConfigProvider: DatabaseConfigProvider,
                                  cc: ControllerComponents
                                ) (implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  private val model = new DatabaseModel(db)

  def main = Action {
    implicit request => Ok(views.html.login())
  }

  implicit val userDataReads = Json.reads[UserData]

  def withJsonBody[A](f: A => Future[Result])(
    implicit request: Request[AnyContent],
    reads: Reads[A]
  ): Future[Result] = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Future.successful(Redirect(routes.IndexController.main))
      }
    }.getOrElse(Future.successful(Redirect(routes.IndexController.main)))
  }

  def createUser = Action.async { implicit request =>
    withJsonBody[UserData] {ud =>
      model.createUser(ud.userName, ud.password, ud.email, ud.ethID).map {idCheck =>
        idCheck match {
          case Some(userid) => Ok(Json.toJson(true))
            .withSession(
              "username" -> ud.userName,
              "userid" -> userid.toString,
              "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse("")
            )
          case None => Ok(Json.toJson(false))
        }
      }
    }
  }

}
