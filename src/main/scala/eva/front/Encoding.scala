package eva.front

import eva.ast.{Lambda, Apply, Var, Expr}

object Encoding {

  object CurryNumber {
    def apply(n: Int) = {
      var cn: Expr = Var("z")
      for (i <- 1 to n)
        cn = Apply(Var("s"), cn)
      Lambda(Var("s"), Lambda(Var("z"), cn))
    }
  }

}
