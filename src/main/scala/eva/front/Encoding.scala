package eva.front

import eva.ast.{Apply, Expr, Lambda, Var}

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
