package org.example.stubs

import org.example.MathFunction
import org.example.trig.CosFunction
import org.example.trig.CscFunction
import org.example.trig.SecFunction
import org.example.trig.SinFunction
import org.example.trig.TanFunction
import kotlin.math.PI
import kotlin.math.abs

class SinStub : SinFunction {
    private val table = mapOf(
        0.0     to  0.0,
        PI / 6  to  0.5,
        PI / 4  to  0.7071067811865476,
        PI / 3  to  0.8660254037844387,
        PI / 2  to  1.0,
        0.5     to  0.4794255386042030,
        1.0     to  0.8414709848078965,
        -PI / 6 to -0.5,
        -PI / 4 to -0.7071067811865476,
        -PI / 3 to -0.8660254037844387,
        -PI / 2 to -1.0,
        -0.1    to -0.09983341664682815,
        -0.3    to -0.29552020666133957,
        -0.5    to -0.47942553860420300,
        -1.0    to -0.84147098480789650,
    )

    override fun compute(x: Double): Double =
        table[x] ?: throw UnsupportedOperationException("SinStub не имеет значения для x=$x")
}

class CosStub : CosFunction {
    private val table = mapOf(
        0.0     to  1.0,
        PI / 6  to  0.8660254037844387,
        PI / 4  to  0.7071067811865476,
        PI / 3  to  0.5,
        PI / 2  to  0.0,
        PI      to -1.0,
        0.5     to  0.8775825618903728,
        1.0     to  0.5403023058681398,
        -PI / 6 to  0.8660254037844387,
        -PI / 4 to  0.7071067811865476,
        -PI / 3 to  0.5,
        -PI / 2 to  0.0,
        -PI     to -1.0,
        -0.5    to  0.8775825618903728,
    )

    override fun compute(x: Double): Double =
        table[x] ?: throw UnsupportedOperationException("CosStub не имеет значения для x=$x")
}

class TanStub : TanFunction {
    private val table = mapOf(
        0.0     to  0.0,
        PI / 6  to  0.5773502691896257,   // sin(π/6)/cos(π/6) = 0.5/0.8660254037844387
        PI / 4  to  1.0,                  // sin(π/4)/cos(π/4) = 1
        PI / 3  to  1.7320508075688772,   // sin(π/3)/cos(π/3) = 0.8660254037844387/0.5
        0.5     to  0.5463024898437905,   // sin(0.5)/cos(0.5)
        1.0     to  1.5574077246549023,   // sin(1.0)/cos(1.0)
        -PI / 6 to -0.5773502691896257,
        -PI / 4 to -1.0,
        -PI / 3 to -1.7320508075688772,
        -0.1    to -0.1003346720854505,   // sin(-0.1)/cos(-0.1)
        -0.3    to -0.3093362496096232,   // sin(-0.3)/cos(-0.3)
        -0.5    to -0.5463024898437905,   // sin(-0.5)/cos(-0.5)
        -1.0    to -1.5574077246549023,   // sin(-1.0)/cos(-1.0)
    )

    override fun compute(x: Double): Double =
        table[x] ?: throw UnsupportedOperationException("TanStub не имеет значения для x=$x")
}

class SecStub : SecFunction {
    private val table = mapOf(
        0.0     to  1.0,                  // 1/cos(0)
        PI / 6  to  1.1547005383792515,   // 1/cos(π/6) = 2/√3
        PI / 4  to  1.4142135623730951,   // 1/cos(π/4) = √2
        PI / 3  to  2.0,                  // 1/cos(π/3)
        0.5     to  1.1394939273245491,   // 1/cos(0.5)
        1.0     to  1.8508157176809257,   // 1/cos(1.0)
        -PI / 6 to  1.1547005383792515,
        -PI / 4 to  1.4142135623730951,
        -PI / 3 to  2.0,
        -0.1    to  1.0050209184004553,   // 1/cos(-0.1)
        -0.3    to  1.0467516015380856
        ,   // 1/cos(-0.3)
        -0.5    to  1.1394939273245491,   // 1/cos(-0.5)
        -1.0    to  1.8508157176809257,   // 1/cos(-1.0)
    )

    override fun compute(x: Double): Double =
        table[x] ?: throw UnsupportedOperationException("SecStub не имеет значения для x=$x")
}

class CscStub : CscFunction {
    private val table = mapOf(
        PI / 6  to  2.0,                  // 1/sin(π/6)
        PI / 4  to  1.4142135623730951,   // 1/sin(π/4) = √2
        PI / 3  to  1.1547005383792515,   // 1/sin(π/3) = 2/√3
        PI / 2  to  1.0,                  // 1/sin(π/2)
        0.5     to  2.0858296429334881,   // 1/sin(0.5)
        1.0     to  1.1883951057781212,   // 1/sin(1.0)
        -PI / 6 to -2.0,
        -PI / 4 to -1.4142135623730951,
        -PI / 3 to -1.1547005383792515,
        -PI / 2 to -1.0,
        -0.1    to -10.016686131634355,   // 1/sin(-0.1)
        -0.3    to  -3.3838633618241225,  // 1/sin(-0.3)
        -0.5    to  -2.0858296429334881,  // 1/sin(-0.5)
        -1.0    to  -1.1883951057781212,  // 1/sin(-1.0)
    )

    override fun compute(x: Double): Double =
        table[x] ?: throw UnsupportedOperationException("CscStub не имеет значения для x=$x")
}


class TrigSystemStub : MathFunction {
    private val undefinedPoints = setOf(0.0, -PI, -2 * PI, PI / 2, -PI / 2, -3 * PI / 2)

    override fun compute(x: Double): Double {
        require(x <= 0) { "TrigSystemStub определена только для x <= 0" }
        for (p in undefinedPoints) {
            if (abs(x - p) < 1e-14)
                throw IllegalArgumentException("TrigSystemStub: недопустимая точка x=$x")
        }
        return 1.0
    }
}
