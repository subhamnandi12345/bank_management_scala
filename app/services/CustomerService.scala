package services

import models.Customer

trait CustomerService {
  def createNewCustomer(name: String, email: String, initialBalance: Double): Customer
  def deleteCustomer(customerId: Long): Boolean
  def showCustomers: List[Customer]
  def showBalance(customerId: Long): Option[Double]
}
