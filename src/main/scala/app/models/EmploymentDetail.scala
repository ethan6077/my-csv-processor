package app.models

import app.models.EmploymentDetail.EmploymentDetailType

case class EmploymentDetail(lockeId: String, employmentType: String, occupation: String) {
  def toDBType: EmploymentDetailType =
    (lockeId, employmentType, occupation)
}

object EmploymentDetail {
  type EmploymentDetailType = (String, String, String)
}
