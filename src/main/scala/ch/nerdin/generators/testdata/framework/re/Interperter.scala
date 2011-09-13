package nl.erikjan.generators.testdata.framework.re

import scala.util.Random;
import scala.collection.immutable.NumericRange;

class Interperter(expression: Or) {
  var rnd: Random = new Random

  def run() {
    println("starting expresion " + expression)
    iterateExpressions(List(expression))
  }

  private def iterateExpressions(list: List[Expression]) {
    list match {
      case Length(expression, lenght) :: rest => {
        //for (i <- 0 to 3)
        //println(expression)

        println("rest " + rest)

        iterateExpressions(List(expression))
      }
      case Or(list) :: rest => {
        println("or desion")
        val e: Expression = list(0)
        println(e)
        iterateExpressions(List[Expression](e))
      }

      case Literal(l) :: rest => {
        println("literal " + l)
        iterateExpressions(rest)
      }

      case Range(from, to) :: rest => {
        val charRange: NumericRange[Char] = from.charAt(0).until(to.charAt(0))
        println(charRange(rnd.nextInt()))
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
