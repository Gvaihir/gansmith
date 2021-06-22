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
class IndexController @Inject() (cc: ControllerComponents ) (implicit ec: ExecutionContext)
  extends AbstractController(cc){

  def main = Action {
    implicit request => Ok(views.html.index())
  }

}
