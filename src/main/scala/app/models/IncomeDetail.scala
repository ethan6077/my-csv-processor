package app.models

import app.models.IncomeDetail.IncomeDetailType

case class IncomeDetail(lockeId: String, incomeType: String, incomeAmount: Int) {
  def toDBType: IncomeDetailType =
    (lockeId, incomeType, incomeAmount)
}

object IncomeDetail {
  type IncomeDetailType = (String, String, Int)
}
