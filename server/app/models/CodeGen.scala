package models

import java._
import java.net.URI

object CodeGen extends App{
  val dbURL: String = sys.env.getOrElse("DATABASE_URL", "None")
  val curDir: String = new io.File(".").getAbsolutePath
  var parsedURL: Array[String] = parseHerokuURL(dbURL)
  var jdbcFormattedURL: String = {
    f"jdbc:postgresql://${parsedURL(0)}%s:${parsedURL(1)}%s${parsedURL(2)}"
  }
  slick.codegen.SourceCodeGenerator.run(
    profile = "slick.jdbc.PostgresProfile",
    jdbcDriver = "org.postgresql.Driver",
    url = jdbcFormattedURL,
    outputDir = f"$curDir%s/server/app",
    pkg = "models",
    user = Option(parsedURL(3)),
    password = Option(parsedURL(4)),
    ignoreInvalidDefaults = true,
    outputToMultipleFiles = false
  )

  def parseHerokuURL(herokuURL: String): Array[String] = {
    val uri: URI = new URI(herokuURL)
    val username: String = uri.getUserInfo.split(":")(0)
    val password: String = uri.getUserInfo.split(":")(1)
    val host: String = uri.getHost()
    val port: String = f"${uri.getPort()}%d"
    val db: String = uri.getPath().trim()
    Array(host, port, db, username, password)
  }
}


