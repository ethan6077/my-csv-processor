package app

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits.toTraverseOps
import doobie.util.transactor.Transactor

import scala.io.Source

object Main extends IOApp {
  // line 0 is the titles, so we're gonna skip it
  private val START_POSITION: Int = 900000
  private val END_POSITION: Int = 1000000
  private val BATCH_SIZE: Int = 5000

  override def run(args: List[String]): IO[ExitCode] = {
    val xa: Transactor[IO] = Database.buildTransactor

    for {
      _ <- IO(println("IOApp started ..."))
      filePath <- Utils.parseArgs(args)
      fileSource <- IO.blocking(Source.fromFile(filePath))
      lines <- IO.blocking(fileSource.getLines)
      lockeIds <- parseFile(lines)
      res <- saveLockeIds(xa, lockeIds)
      _ <- IO(println(s"created $res records!"))
      _ <- IO.blocking(fileSource.close)
      _ <- IO(println(s"Processed CSV file successfully!"))
    } yield ExitCode.Success
  }

  private def parseFile(lines: Iterator[String]): IO[List[String]] =
    IO.pure(lines.slice(START_POSITION, END_POSITION).toList.map(_.split(",").toList.head))

  private def saveLockeIds(xa: Transactor[IO], lockeIds: List[String]): IO[Int] =
    lockeIds
      .sliding(BATCH_SIZE, BATCH_SIZE)
      .toList
      .map(Database.create(xa, _))
      .sequence
      .map(_.sum)

}
