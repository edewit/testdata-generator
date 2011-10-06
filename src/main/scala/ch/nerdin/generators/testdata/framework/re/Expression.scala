package ch.nerdin.generators.testdata.framework.re


abstract class Expression
case class Or(l: List[Expression]) extends Expression
class Range(r: String, t: String) extends Expression {
  def this(l: Expression)=
    {

    }
}
case class Range(l: Expression) extends Expression
case class Group(g:  Expression) extends Expression
case class Length(e:Expression, l: Object) extends Expression
case class Literal(c: String) extends Expression