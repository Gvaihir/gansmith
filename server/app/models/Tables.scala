package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Assets.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Assets
   *  @param assetId Database column asset_id SqlType(serial), AutoInc, PrimaryKey
   *  @param usersLiked Database column users_liked SqlType(int4), Default(None)
   *  @param assetName Database column asset_name SqlType(varchar), Length(100,true)
   *  @param url Database column url SqlType(varchar), Length(1000,true)
   *  @param price Database column price SqlType(float4), Default(None) */
  case class AssetsRow(assetId: Int, usersLiked: Option[Int] = None, assetName: String, url: String, price: Option[Float] = None)
  /** GetResult implicit for fetching AssetsRow objects using plain SQL queries */
  implicit def GetResultAssetsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[String], e3: GR[Option[Float]]): GR[AssetsRow] = GR{
    prs => import prs._
    AssetsRow.tupled((<<[Int], <<?[Int], <<[String], <<[String], <<?[Float]))
  }
  /** Table description of table assets. Objects of this class serve as prototypes for rows in queries. */
  class Assets(_tableTag: Tag) extends profile.api.Table[AssetsRow](_tableTag, "assets") {
    def * = (assetId, usersLiked, assetName, url, price) <> (AssetsRow.tupled, AssetsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(assetId), usersLiked, Rep.Some(assetName), Rep.Some(url), price)).shaped.<>({r=>import r._; _1.map(_=> AssetsRow.tupled((_1.get, _2, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column asset_id SqlType(serial), AutoInc, PrimaryKey */
    val assetId: Rep[Int] = column[Int]("asset_id", O.AutoInc, O.PrimaryKey)
    /** Database column users_liked SqlType(int4), Default(None) */
    val usersLiked: Rep[Option[Int]] = column[Option[Int]]("users_liked", O.Default(None))
    /** Database column asset_name SqlType(varchar), Length(100,true) */
    val assetName: Rep[String] = column[String]("asset_name", O.Length(100,varying=true))
    /** Database column url SqlType(varchar), Length(1000,true) */
    val url: Rep[String] = column[String]("url", O.Length(1000,varying=true))
    /** Database column price SqlType(float4), Default(None) */
    val price: Rep[Option[Float]] = column[Option[Float]]("price", O.Default(None))

    /** Foreign key referencing Users (database name assets_users_liked_fkey) */
    lazy val usersFk = foreignKey("assets_users_liked_fkey", usersLiked, Users)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Assets */
  lazy val Assets = new TableQuery(tag => new Assets(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param username Database column username SqlType(varchar), Length(20,true)
   *  @param password Database column password SqlType(varchar), Length(200,true)
   *  @param email Database column email SqlType(varchar), Length(100,true), Default(None)
   *  @param ethId Database column eth_id SqlType(varchar), Length(100,true), Default(None) */
  case class UsersRow(id: Int, username: String, password: String, email: Option[String] = None, ethId: Option[String] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String], <<?[String], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, username, password, email, ethId) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(username), Rep.Some(password), email, ethId)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(varchar), Length(20,true) */
    val username: Rep[String] = column[String]("username", O.Length(20,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
    /** Database column email SqlType(varchar), Length(100,true), Default(None) */
    val email: Rep[Option[String]] = column[Option[String]]("email", O.Length(100,varying=true), O.Default(None))
    /** Database column eth_id SqlType(varchar), Length(100,true), Default(None) */
    val ethId: Rep[Option[String]] = column[Option[String]]("eth_id", O.Length(100,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
