package app

import app.models.EmploymentDetail.EmploymentDetailType
import app.models.IncomeDetail.IncomeDetailType
import app.models.PersonalDetail.PersonalDetailType
import app.models.ResidentialAddress.ResidentialAddressType
import app.models.{EmploymentDetail, IncomeDetail, PersonalDetail, ResidentialAddress}
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxTuple4Semigroupal
import com.github.javafaker.Faker
import doobie.ConnectionIO
import doobie.implicits._
import doobie.util.transactor.Transactor
import doobie.util.update.Update

object Database {
  def buildTransactor: Transactor[IO] =
    Transactor.fromDriverManager[IO](
      driver = "org.postgresql.Driver", // JDBC driver classname
      url = "jdbc:postgresql://localhost/me_profile?ApplicationName=renter-profile-seeding-tool", // Connect URL
      user = "mepa_rw", // Database user name
      password = Utils.getDbPassword.unsafeRunSync, // Database password
      logHandler = None // Don't setup logging for now. See Logging page for how to log events in detail
    )

  def create(xa: Transactor[IO], lockeIds: List[String]): IO[Int] = {
    val faker = new Faker()
    (
      createPersonalDetails(lockeIds, faker),
      createEmploymentDetails(lockeIds, faker),
      createIncomeDetails(lockeIds, faker),
      createResidentialHistory(lockeIds, faker)
    ).mapN(_ + _ + _ + _).transact(xa)
  }

  private def createPersonalDetails(lockeIds: List[String], faker: Faker): ConnectionIO[Int] = {
    val personalDetails: List[PersonalDetailType] = lockeIds.map { lockeId =>
      PersonalDetail(
        lockeId,
        faker.name.firstName,
        faker.name.lastName,
        faker.number.digits(7),
        faker.name.title
      ).toDBType
    }

    val sql =
      """
        |INSERT INTO personal_details (locke_id, first_name, last_name, phone_number, title)
        |VALUES (?, ?, ?, ?, ?);
        |""".stripMargin

    Update[PersonalDetailType](sql).updateMany(personalDetails)
  }

  private def createEmploymentDetails(lockeIds: List[String], faker: Faker): ConnectionIO[Int] = {
    val employmentDetails: List[EmploymentDetailType] = lockeIds.map { lockeId =>
      EmploymentDetail(lockeId, "EMPLOYED", faker.job.title).toDBType
    }

    val sql =
      """
        |INSERT INTO employment_details (locke_id, employment_type, occupation)
        |VALUES (?, ?::EMPLOYMENT_TYPE, ?);
        |""".stripMargin

    Update[EmploymentDetailType](sql).updateMany(employmentDetails)
  }

  private def createIncomeDetails(lockeIds: List[String], faker: Faker): ConnectionIO[Int] = {
    val incomeDetails: List[IncomeDetailType] = lockeIds.map { lockeId =>
      IncomeDetail(lockeId, "SALARY", faker.number.numberBetween(60000, 300000)).toDBType
    }

    val sql =
      """
        |INSERT INTO income_details (locke_id, income_type, income_amount)
        |VALUES (?, ?::INCOME_TYPE, ?);
        |""".stripMargin

    Update[IncomeDetailType](sql).updateMany(incomeDetails)
  }

  private def createResidentialHistory(lockeIds: List[String], faker: Faker): ConnectionIO[Int] = {
    val residentialHistory: List[ResidentialAddressType] = lockeIds.map { lockeId =>
      ResidentialAddress(
        lockeId,
        faker.address.streetAddress,
        faker.address.cityName,
        faker.address.state,
        faker.address.countryCode,
        faker.address.zipCode
      ).toDBType
    }

    val sql =
      """
        |INSERT INTO residential_history (locke_id, street, city, state, country_code, postcode)
        |VALUES (?, ?, ?, ?, ?, ?)
        |""".stripMargin

    Update[ResidentialAddressType](sql).updateMany(residentialHistory)
  }
}
