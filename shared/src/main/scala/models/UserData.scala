package models

import play.api.libs.json.Json

abstract case class UserData(userName: String, password: String,
                             email: String, ethID: String)

object ReadsAndWrites {
  implicit val userDataReads = Json.reads[UserData]
  implicit val userDataWrites = Json.writes[UserData]
}