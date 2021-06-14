package models

import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext
import models.Tables._
import org.mindrot.jbcrypt.BCrypt
import scala.concurrent.Future

class DatabaseModel(db: Database) (implicit executionContext: ExecutionContext) {
  def validateUser(username: String, password: String): Future[Option[Int]] = {
    val usernameMatch = db.run(Users.filter(userRow => userRow.username === username).result)
    usernameMatch.map(userRows => userRows.headOption.flatMap {
      userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.id) else None
    })
  }

  def createUser(username: String, password: String, email: String, ethID: String): Future[Boolean] = ???

  def getAsset(assetID: String) = ???

  def createAsset(assetURL: String, mint: String) = ???

  def likeAsset(username: String) = ???

  def deleteAsset(assetID: String) = ???
}
