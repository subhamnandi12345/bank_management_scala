package models

import play.api.libs.json.{JsValue, Json, Writes}

trait BankAccount {
  def accountId: Long
  def balance: Double
  def deposit(amount: Double): BankAccount
  def withdraw(amount: Double): BankAccount
}
object BankAccount {
  implicit val bankAccountWrites: Writes[BankAccount] = new Writes[BankAccount] {
    def writes(account: BankAccount): JsValue = Json.obj(
      "accountId" -> account.accountId,
      "balance" -> account.balance
    )
  }
}