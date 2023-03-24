package example

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- if (args.length < 1)
        IO.raiseError(new IllegalArgumentException("Need csv file"))
      else IO.unit
      _ <- IO(println("IOApp started ..."))
      _ <- IO(println(s"Processed CSV file successfully!"))
    } yield ExitCode.Success

}
