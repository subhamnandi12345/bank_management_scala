package models

case class SavingsAccount(accountId: Long, balance: Double) extends BankAccount {
  override def deposit(amount: Double): BankAccount = copy(balance = balance + amount)
  override def withdraw(amount: Double): BankAccount = copy(balance = balance - amount)
}
case class CurrentAccount(accountId: Long, balance: Double, overdraftLimit: Double) extends BankAccount {
  override def deposit(amount: Double): BankAccount = copy(balance = balance + amount)
  override def withdraw(amount: Double): BankAccount =
    if (balance - amount >= -overdraftLimit) copy(balance = balance - amount)
    else this
}

