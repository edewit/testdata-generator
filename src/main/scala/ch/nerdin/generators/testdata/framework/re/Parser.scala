package nl.erikjan.generators.testdata.framework.re

import scala.util.parsing.combinator.syntactical._

object Parser extends StandardTokenParsers {


   lexical.delimiters ++= List("(", ")", "{", "}", "[", "]", "-", "|")
   //lexical.reserved += ("-")

   def regex: Parser[Or] = repsep(expression,"|") ^^ { case list => Or(list)}

   def expression: Parser[Expression] = ( rangeExpression | group | literal) ~ ((lengthExpression)?) ^^
   {
      case expr ~ length => Length(expr, length)
   }

   def group = "(" ~> regex <~ ")" ^^ { case group => Group(group)}

   def rangeExpression: Parser[Expression] = "[" ~ range ~ "]" ^^ {case _ ~ range ~ _=> Range(range._1, range._2) }

   def lengthExpression = "{" ~> length <~ "}" ^^ { case l => (l)}

   def length = numericLit ^^ { l => l.toInt }

   def range = value ~ "-" ~ value ^^ { case left ~ _ ~ right => (left, right) }

   def value = (ident|numericLit) ^^ { s => s.toString }

   def literal = (ident|numericLit) ^^ { s => Literal(s.toString) }


   /**
    * @param args the command line arguments
    */
   def main(args: Array[String]) :Unit = {
      //val dsl = "([a-z]|[0-9])|[a-b]"
      //val dsl = "[a-z]{3}"
      val dsl = "[A-Z]"

      regex(new lexical.Scanner(dsl)) match {
         case Success(ord, _) => println(ord)
      }

     parse(dsl)
   }


   def parse(expression : String) {
      regex(new lexical.Scanner(expression)) match {
         case Success(parsed, _) => new Interperter(parsed).run()
      }
   }
}
