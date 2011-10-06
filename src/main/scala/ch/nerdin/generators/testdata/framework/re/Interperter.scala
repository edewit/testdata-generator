package ch.nerdin.generators.testdata.framework.re

import scala.util.Random

class Interperter(expression: Or) {
  val result:StringBuilder = new StringBuilder()

  def run() : String = {
    println("starting expresion " + expression)
    iterateExpressions(List(expression))
    println("== result " + result.toString)
    return result.toString
  }

  private def iterateExpressions(list: List[Expression]) {
    val rand = new Random(System.currentTimeMillis());
    list match {
      case Length(expression, lenght) :: rest => {

          println("expression " + expression)
          println("length " + lenght)
          println("rest " + rest)

          lenght match {
            case Some(x:Int) => for (i <- 1 to x) { iterateExpressions(List(expression)) }
            case _ => iterateExpressions(List(expression))
          }
        }
      case Or(list) :: rest => {
          println("or desion")
          val e : Expression = list(rand.nextInt(math.max(list.length - 0, 1)))
          println("chosen: " + e)
          iterateExpressions(List[Expression](e))
        }

      case Literal(l) :: rest => {
          println("literal " + l)
          result.append(l);
          iterateExpressions(rest)
        }

      case Range(from, to) :: rest => {
          println("from " + from)
          println("to " + to)
          val value: Int = from.charAt(0) + rand.nextInt(math.abs(to.charAt(0) - from.charAt(0)))
          result.append(Character.toChars(value)(0))
          iterateExpressions(rest)
        }

      case Group(group) :: rest => {
          println("group")
          iterateExpressions(List(group))
        }

      case Nil => {
          println("no match")
        }
    }
  }
}
