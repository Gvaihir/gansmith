package models

import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext
import models.Tables._
import org.mindrot.jbcrypt.BCrypt
import scala.concurrent.Future

class DatabaseModel(db: Database) (implicit executionContext: ExecutionContext) {

  private def hashAndSalt(rawData: String): String = {
    BCrypt.hashpw(rawData, BCrypt.gensalt())
  }

  def validateUser(username: String, password: String): Future[Option[Int]] = {
    val usernameMatch = db.run(Users.filter(userRow => userRow.username === username).result)
    usernameMatch.map(userRows => userRows.headOption.flatMap {
      userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.id) else None
    })
  }

  def createUser(username: String, password: String, email: String, ethId: String): Future[Option[Int]] = {
    val usernameMatch = db.run(Users.filter(userRow => userRow.username === username).result)
    usernameMatch.flatMap { userRows =>
      if (userRows.isEmpty) {
        db.run(Users += UsersRow(-1, username, hashAndSalt(password), email, hashAndSalt(ethId)))
          .flatMap { addCount =>
            if (addCount > 0) db.run(Users.filter(userRow => userRow.username === username).result)
              .map(_.headOption.map(_.id))
            else Future.successful(None)
          }
      }
      else Future.successful(None)

    }
  }

  def getAsset(assetID: String) = ???

  def createAsset(assetURL: String, mint: String) = ???

  def likeAsset(username: String) = ???

  def deleteAsset(assetID: String) = ???
}
