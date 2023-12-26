package models

import play.api.libs.json.{JsValue, Json, Writes}

case class Customer(id: Long, name: String, email: String, account: BankAccount)
object Customer {
  import BankAccount._
  implicit val customerWrites: Writes[Customer] = new Writes[Customer] {
    def writes(customer: Customer): JsValue = Json.obj(
      "id" -> customer.id,
      "name" -> customer.name,
      "email" -> customer.email,
      "account" -> Json.toJson(customer.account)
    )
  }
}