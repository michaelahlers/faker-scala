package ahlers.refined

import eu.timepit.refined.api.Result
import eu.timepit.refined.api.Validate
import shapeless.Witness

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 05, 2020
 */
object extras {

  final case class Drop[L <: Int, R <: Int, P](a: L, b: R, p: P)
  object Drop {

    implicit def dropValidate[A <: Int, B <: Int, P, R](
      implicit
      A: Witness.Aux[A],
      B: Witness.Aux[B],
      P: Validate.Aux[String, P, R]
    ): Validate.Aux[String, Drop[A, B, P], Drop[A, B, P.Res]] =
      new Validate[String, Drop[A, B, P]] {

        override type R = Drop[A, B, P.Res]

        override def validate(t: String) = {
          val r = P.validate(t.drop(A.value).dropRight(B.value))
          Result.fromBoolean(r.isPassed, Drop(A.value, B.value, r))
        }

        override def showExpr(t: String) =
          P.showExpr(t.drop(A.value).dropRight(B.value))

      }

  }

}
