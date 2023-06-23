package example

import cats.effect.{ExitCode, IO, IOApp}

import java.io.File
import java.util.Scanner

object Main extends IOApp {

  private val SAMPLE_SIZE_MAX: Int = 500000

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- if (args.length < 1)
        IO.raiseError(new IllegalArgumentException("Need csv file"))
      else IO.unit
      myFile = new File(args(0))
      _ <- IO(println("IOApp started ..."))
      _ <- IO(parseFile(myFile))
      _ <- IO(println(s"Processed CSV file successfully!"))
    } yield ExitCode.Success

  private def parseFile(file: File): Unit = {

    var uniqueConsumers: Set[String] = Set()

    val sc = new Scanner(file)
    var readerCount = 0
    while (sc.hasNext() && readerCount < SAMPLE_SIZE_MAX) {
      val row = sc.next().split(",").toList
      val consumerId = row(1)
//      we'll skip the first header row
      if (readerCount > 0) {
//        println("first consumer id:", consumerId)
        uniqueConsumers += consumerId
      }
      readerCount = readerCount + 1
    }

    sc.close()

    println("=========================================")
    println(s"Unique Consumers Count: ${uniqueConsumers.size}")
    println("=========================================")
  }

}
