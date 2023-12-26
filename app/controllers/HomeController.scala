// app/controllers/HomeController.scala
package controllers

import play.api.mvc._
import play.api.libs.json._
import services.CustomerServiceImpl

import javax.inject.Inject
import scala.collection.mutable.ListBuffer

class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())

  }

  def createNewCustomer: Action[JsValue] = Action(parse.json) { request =>
    val name = (request.body \ "name").as[String]
    val email = (request.body \ "email").as[String]
    val initialBalance = (request.body \ "initialBalance").as[Double]

    val newCustomer = CustomerServiceImpl.createNewCustomer(name, email, initialBalance)

    Ok(Json.obj("message" -> "Customer created successfully", "customer" -> Json.toJson(newCustomer)))
  }

  def deleteCustomer(customerId: Long): Action[AnyContent] = Action {
    if (CustomerServiceImpl.deleteCustomer(customerId)) {
      Ok(Json.obj("message" -> "Customer deleted successfully"))
    } else {
      NotFound(Json.obj("message" -> s"Customer with ID $customerId not found"))
    }
  }

  def showCustomers: Action[AnyContent] = Action {
    val customers = CustomerServiceImpl.showCustomers
    Ok(Json.toJson(customers))
  }

  def showBalance(customerId: Long): Action[AnyContent] = Action {
    CustomerServiceImpl.showBalance(customerId) match {
      case Some(balance) => Ok(Json.obj("balance" -> balance))
      case None => NotFound(Json.obj("message" -> s"Customer with ID $customerId not found"))
    }
  }
}
