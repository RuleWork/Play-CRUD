package models

import play.api.libs.json._ // JSON library
import play.api.libs.json.Reads._ // Custom validation helpers
import play.api.libs.json.Writes._ // Custom validation helpers
import play.api.libs.functional.syntax._ // Combinator syntax
/**
 * @author 统行
 * @version
 */
case class Employee(empId: String, empName: String, gender: String, email: String) {
//    var empName: String = _
//    var gender: String = _
//    var email: String = _
//
//    /**
//      * 不含主键的构造方法
//      */
//     def this(empName: String, gender: String, email: String) {
//        this()
//        this.empName = empName
//        this.gender = gender
//        this.email = email
//    }
//
//    /**
//      * 含主键的构造方法
//      */
//    def this(empId: Int, empName: String, gender: String, email: String) {
//        this()
//        this.empId = empId
//        this.empName = empName
//        this.gender = gender
//        this.email = email
//    }
}



object Employee {
//    def apply(empId: Int, empName: String, gender: String, email: String) : Employee = new Employee(empId, empName, gender, email)
//
//    def unapply(employee: Employee): Option[(Int,
//      String, String, String)] = {
//        if(employee == null) {
//            None
//        } else {
//            Some(employee.empId, employee.empName, employee.gender, employee.email)
//        }
//    }

      implicit val employeeFormat = Json.format[Employee]

//    implicit val employeeReads: Reads[Employee] = (
//        (__ \ "empId").read[Int] and
//        (__ \ "empName").read[String] and
//        (__ \ "gender").read[String] and
//        (__ \ "email").read[String]
//  )(Employee.apply _)
//
//    implicit val employeeWrites: Writes[Employee] = (
//        (__ \ "empId").write[Int] and
//        (__ \ "empName").write[String] and
//        (__ \ "gender").write[String] and
//        (__ \ "email").write[String]
//      )(unlift(Employee.unapply))
//
//    implicit val employeeFormat: Format[Employee] = Format(employeeReads,employeeWrites)
}