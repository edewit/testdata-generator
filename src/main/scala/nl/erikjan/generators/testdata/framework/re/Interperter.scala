package nl.erikjan.generators.testdata.framework.re

import nl.erikjan.generators.testdata.framework.RandomUtil;

/*
class Interperter(expression: Or) {

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
               val e : Expression = list(RandomUtil.randomBetween(0, list.length))
               println(e)
               iterateExpressions(List[Expression](e))
            }

         case Literal(l) :: rest => {
               println("literal " + l)
               iterateExpressions(rest)
            }

         case Range(from, to) :: rest => {
               println(RandomUtil.randomBetween(from.charAt(0), to.charAt(0)))
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
*/