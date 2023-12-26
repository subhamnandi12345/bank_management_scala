package services
import models.{BankAccount, CurrentAccount, Customer, SavingsAccount}
// app/services/CustomerServiceImpl.scala
object CustomerServiceImpl extends CustomerService {
  var customers: List[Customer] = List()

  private def generateAccountId: Long = customers.length + 1

  private def createBankAccount(initialBalance: Double): BankAccount =
    if (initialBalance >= 0) SavingsAccount(generateAccountId, initialBalance)
    else CurrentAccount(generateAccountId, initialBalance, overdraftLimit = 1000)

  override def createNewCustomer(name: String, email: String, initialBalance: Double): Customer = {
    val newCustomer = Customer(generateAccountId, name, email, createBankAccount(initialBalance))
    customers = customers :+ newCustomer
    newCustomer
  }

  override def deleteCustomer(customerId: Long): Boolean = {
    val initialSize = customers.length
    customers = customers.filterNot(_.id == customerId)
    customers.length < initialSize
  }

  override def showCustomers: List[Customer] = customers

  override def showBalance(customerId: Long): Option[Double] = {
    customers.find(_.id == customerId).map(_.account.balance)
  }
}
