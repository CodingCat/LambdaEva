package eva.ast

import scala.util.parsing.input.Positional

sealed trait Expr extends Positional

case class Lambda(arg: Var, body: Expr) extends Expr

case class Var(name: String) extends Expr

case class Apply(fun: Expr, arg: Expr) extends Expr

case class Constant(name: String) extends Expr