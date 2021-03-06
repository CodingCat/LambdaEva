package eva.front

import scala.util.parsing.combinator.PackratParsers
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator.syntactical.StdTokenParsers

import eva.ast._
import eva.front.Encoding.CurryNumber

class LambdaParser extends StdTokenParsers with PackratParsers {

  override type Tokens = StdLexical

  override val lexical: Tokens = new LambdaLexer
  lexical.delimiters ++= Seq("\\", "L", ".", "(", ")", "=", ";")

  type P[+T] = PackratParser[T]

  lazy val expr: P[Expr]         = application | notApp
  lazy val notApp                = variable | number | parens | lambda
  lazy val lambda: P[Lambda]     = positioned(("L" | "\\") ~> variable ~ "." ~ expr ^^
    { case v ~ "." ~ e  => Lambda(v, e) })
  lazy val application: P[Apply] = positioned(expr ~ notApp ^^
    { case left ~ right => Apply(left, right) })
  lazy val variable: P[Var]      = positioned(ident ^^ Var.apply)
  lazy val parens: P[Expr]       = "(" ~> expr <~ ")"
  lazy val number: P[Lambda]     = numericLit ^^ { case n => CurryNumber(n.toInt) }

  def parse(str: String): ParseResult[Expr] = {
    val tokens = new lexical.Scanner(str)
    phrase(expr)(tokens)
  }
}

/**
 * we replace "λ" with L
 */
class LambdaLexer extends StdLexical {
  override def letter = elem("letter", c => c.isLetter && c != 'L')
}
