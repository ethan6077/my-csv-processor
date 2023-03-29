package example

import cats.effect.{ExitCode, IO, IOApp}

import java.io.File
import java.util.Scanner

object Main extends IOApp {

  private val SAMPLE_SIZE_MAX: Int = 37000
  private val LAST_CHAR_SAMPLE_VALUE: Char = '7'
  private val SECOND_LAST_CHAR_SAMPLE_VALUE: Char = '1'

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- if (args.length < 1)
        IO.raiseError(new IllegalArgumentException("Need csv file"))
      else IO.unit
      myFile = new File(args(0))
      _ <- IO(println("IOApp started ..."))
      _ <- IO(printLastCharValues(myFile))
      _ <- IO(println(s"Processed CSV file successfully!"))
    } yield ExitCode.Success

  private def printLastCharValues(file: File): Unit = {

    var lastCharValues: List[Char] = List()
    var secondLastCharValues: List[Char] = List()

    var lastCharSampleCount = 0
    var lastTwoCharsSampleCount = 0

    val sc = new Scanner(file)
    var readerCount = 0
    while (sc.hasNext() && readerCount < SAMPLE_SIZE_MAX) {
      val row = sc.next().split(",").toList
      val consumerId = row(1)
      if (readerCount > 0) {
//        println(consumerId)
        val stringLength = consumerId.length

        val lastChar = consumerId.charAt(stringLength - 1)
        if (!lastCharValues.exists(v => v == lastChar)) {
          lastCharValues = lastCharValues :+ lastChar
        }
        if (lastChar == LAST_CHAR_SAMPLE_VALUE) {
          lastCharSampleCount = lastCharSampleCount + 1
        }

        val secondLastChar = consumerId.charAt(stringLength - 2)
        if (!secondLastCharValues.exists(v => v == secondLastChar)) {
          secondLastCharValues = secondLastCharValues :+ secondLastChar
        }
        if (lastChar == LAST_CHAR_SAMPLE_VALUE && secondLastChar == SECOND_LAST_CHAR_SAMPLE_VALUE) {
          lastTwoCharsSampleCount = lastTwoCharsSampleCount + 1
        }

      }
      readerCount = readerCount + 1
    }

    sc.close()

    println("=========================================")
    println("Last char possible values:")
    println(lastCharValues.sorted.mkString(","))
    println("Second last char possible values:")
    println(secondLastCharValues.sorted.mkString(","))
    println("=========================================")
    println(s"Total Sample Size: $SAMPLE_SIZE_MAX")
    println("Last char sample:")
    val lastCharSamplePercentage: Float = lastCharSampleCount.toFloat / SAMPLE_SIZE_MAX.toFloat * 100
    val lastCharSamplePercentageString: String = f"$lastCharSamplePercentage%.2f"
    println(s"value: $LAST_CHAR_SAMPLE_VALUE, count: $lastCharSampleCount, percentage: $lastCharSamplePercentageString%")
    println("Last two chars sample:")
    val lastTwoCharsSamplePercentage: Float = lastTwoCharsSampleCount.toFloat / SAMPLE_SIZE_MAX.toFloat * 100
    val lastTwoCharsSamplePercentageString: String = f"$lastTwoCharsSamplePercentage%.2f"
    println(s"value: $SECOND_LAST_CHAR_SAMPLE_VALUE$LAST_CHAR_SAMPLE_VALUE, count: $lastTwoCharsSampleCount, percentage: $lastTwoCharsSamplePercentageString%")
    println("=========================================")
  }

}
