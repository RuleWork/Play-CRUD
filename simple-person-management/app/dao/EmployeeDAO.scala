package dao

import javax.inject.Inject
import models.Employee
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.{GetResult, JdbcProfile}
import java.sql._

import scala.concurrent.{ExecutionContext, Future}

/**
  */
class EmployeeDAO @Inject()(@NamedDatabase("postgresqldb") protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._


  private class EmpsTable(tag: Tag) extends Table[Employee](tag, "employee") {

    def empId = column[String]("id", O.PrimaryKey)
    def empName = column[String]("emp_name")
    def gender = column[String]("gender")
    def email = column[String]("email")

    def * = (empId, empName, gender, email) <> ((Employee.apply _).tupled, Employee.unapply) //使用case class 不需要定义type

  }

  //查询所有员工
  private lazy val Emps = TableQuery[EmpsTable]

  //插入
  def
  insert(employee: Employee): Future[Unit] = db.run(Emps += employee).map(_ => ())

  //根据id删除
  def deleteEmpById(empId: String): Future[Int] = db.run(Emps.filter(_.empId === empId).delete)

  //根据传入的user对象，查询id，并修改值 (修改多列)
  def updateEmp(employee: Employee) = db.run(
    Emps.filter(_.empId === employee.empId).map(employee => (employee.gender, employee.email)).update(employee.gender, employee.email)
  )

  //根据员工id查找某个员工
  def findById(empId: String): Future[Seq[Employee]] = db.run(Emps.filter(_.empId === empId).result)

  //查询所有，根据id排序
  def findAll(): Future[Seq[Employee]] = db.run(Emps.sortBy(_.empId).result)

}
