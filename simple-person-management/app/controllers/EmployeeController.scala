package controllers

import dao.EmployeeDAO
import javax.inject.Inject
import play.api.libs.json.{JsError, JsObject, JsSuccess, Json}
import models.Employee

import scala.concurrent.ExecutionContext
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.EmployeeService
/**
 * Restful CRUD EmployeeController
 * Service过于简单，直接调用DAO层
 * @author 统行
 * @version
 */
class EmployeeController @Inject()(employeeDAO: EmployeeDAO, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

    def index = Action {
        //包名/格式/模板名
        Ok(views.html.index())
    }

    /**
      * 根据路径参数查询一个单一的用户
      */
    def findEmp(id: String) = Action.async {
        employeeDAO.findById(id).map{
            case employee => Ok(Json.toJson(employee))
        }
    }

    /**
      * 查询所有用户
      */
    def findAll = Action.async {
        employeeDAO.findAll().map {
            case employee => Ok(Json.toJson(employee))
        }
    }

    /**
      * 插入,不能重复插入
      */
    def insertEmp = Action(parse.json) { implicit request =>
//        Json.fromJson[Employee](request.body) match {
//            //转换成功
//            case employee: JsSuccess[Employee] => employeeDAO.insert(employee.get)
//            //转换失败
//            case error: JsError => println(error.errors)
//        }
        val employee = request.body.validate[Employee]
        employee.fold(
            errors => {
                BadRequest(Json.obj("status" -> "ERROR", "message" -> JsError.toJson(errors)))
            },
            employeeData => {
                employeeDAO.insert(employeeData)
                Ok(Json.obj("status" -> "OK", "count" -> 1))
            }
        )
    }

    /**
      * 根据id删除，返回删除成功标记 1
      */
    def deleteById(id: String) = Action.async {
        employeeDAO.deleteEmpById(id).map {
            case count => Ok(Json.obj("count" -> count))
        }
    }

    /**
      * 根据id更新，返回更新成功标记 1
      */
    def updateEmp = Action(parse.json).async { implicit request =>
        val employee = request.body.validate[Employee]
        employeeDAO.updateEmp(employee.get).map {
            case count => Ok(Json.obj("count" -> count, "employee" -> request.body))
        }
    }

}
