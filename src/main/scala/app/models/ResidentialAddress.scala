package app.models

import app.models.ResidentialAddress.ResidentialAddressType

case class ResidentialAddress(
  lockeId: String,
  street: String,
  city: String,
  state: String,
  country: String,
  postcode: String
) {
  def toDBType: ResidentialAddressType =
    (lockeId, street, city, state, country, postcode)
}

object ResidentialAddress {
  type ResidentialAddressType = (String, String, String, String, String, String)
}
