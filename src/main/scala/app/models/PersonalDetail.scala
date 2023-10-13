package app.models

import app.models.PersonalDetail.PersonalDetailType

case class PersonalDetail(lockeId: String, firstName: String, lastName: String, phoneNumber: String, title: String) {
  def toDBType: PersonalDetailType =
    (lockeId, firstName, lastName, phoneNumber, title)
}

object PersonalDetail {
  type PersonalDetailType = (String, String, String, String, String)
}
