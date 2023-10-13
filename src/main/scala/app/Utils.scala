package app

import cats.effect.IO
import cats.effect.std.Env

object Utils {
  // This function will parse the args and return a file path
  def parseArgs(args: List[String]): IO[String] =
    if (args.length < 1) {
      IO.raiseError(new IllegalArgumentException("Pleas specify the csv file path"))
    } else if (args.length > 1) {
      IO.raiseError(new IllegalArgumentException("The app only takes one argument"))
    } else IO.pure(args.head)

  def getDbPassword: IO[String] =
    Env[IO].get("DB_PASSWORD").flatMap {
      case Some(password) => IO.pure(password)
      case None           => IO.raiseError(new IllegalArgumentException("Pleas specify DB_PASSWORD in ENV"))
    }
}
