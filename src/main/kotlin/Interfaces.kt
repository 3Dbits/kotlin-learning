package com.HT

//for main
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

// Classic examples
interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable!")
}

interface Focusable {
    fun setFocus(b: Boolean) =
        println("I ${if (b) "got" else "lost"} focus.")

    fun showOff() = println("I'm focusable!")
}

class Button : Clickable, Focusable {
    // override modifier is mandatory
    override fun click() = println("I was clicked")

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

// Open, final, and abstract modifiers: Final by default
// Visibility modifiers: Public by default (we have public, protected, private, internal)
open class RichButton : Clickable {
    fun disable() { /* ... */ }  // This function is final: you can’t override it in a subclass.
    open fun animate() { /* ... */ }  // This function is open: you may override it in a subclass.
    override fun click() { /* ... */ }  // This function overrides an open function and is open as well.
}

open class RichButton2 : Clickable {
    final override fun click() { /* ... */ }  // final isn’t redundant here because override without final implies being open.
}

abstract class Animated {
    abstract val animationSpeed: Double  // This property is abstract: it doesn’t have a value, and subclasses need to override its value or accessor.
    val keyframes: Int = 20  // Properties in abstract classes aren’t open by default but can be explicitly marked as open.
    open val frames: Int = 60  // same

    abstract fun animate()  // This function is abstract: it doesn’t have an implementation and must be overridden in subclasses.
    open fun stopAnimating() { /* ... */ }  // Non-abstract functions in abstract classes aren’t open by default but can be marked as such.
    fun animateTwice() { /* ... */ }  // same
}

//TIP Sealed class
sealed class Expr2
class Num2(val value: Int) : Expr2()
class Sum2(val left: Expr2, val right: Expr2) : Expr2()
// The when expression covers all possible cases, so no else branch is needed.
fun eval(e: Expr2): Int =
    when (e) {
        is Num2 -> e.value
        is Sum2 -> eval(e.right) + eval(e.left)
    }

//TIP Object declarations: Singletons made easy
object Payroll {
    val allExpr = mutableListOf<Expr>()

    fun calculateSalary() {
        for (expr in allExpr) {
            /* ... */
        }
    }
}

//TIP Companion objects: A place for factory methods and static members
class MyClass {
    companion object {
        fun callMe() {
            println("Companion object called")
        }
    }
}

private fun getNameFromSocialNetwork(accountId: Int): String {
    return "Test"
}

class User private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) =
            User(email.substringBefore('@'))

        fun newSocialUser(accountId: Int) =
            User(getNameFromSocialNetwork(accountId))
    }
}

class Person(val name: String) {
    companion object Loader {
        fun fromJSON(jsonText: String): Person = Person(jsonText)
    }
}

interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Person2(val name: String) {
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person = Person(jsonText)
    }
}

//TIP Inline classes. To qualify as “inline,” your class must have exactly one property,
// which needs to be initialized in the primary constructor.
@JvmInline
value class UsdCent(val amount: Int)

//TIP Defining SAM interfaces in Kotlin: fun interfaces.
// Functional interfaces in Kotlin contain exactly one abstract method but can also contain
// several additional non-abstract methods. This can help you express more complex constructs you
// couldn’t fit into a function type’s signature.
fun interface IntCondition {
    fun check(i: Int): Boolean
    fun checkString(s: String) = check(s.toInt())
    fun checkChar(c: Char) = check(c.digitToInt())
}

// Overloading arithmetic operators
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }
}
// Defining an operator as an extension function
operator fun Point.minus(other: Point): Point {
    return Point(x - other.x, y - other.y)
}

// Checking whether an object belongs to a collection: The in convention
data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x..<lowerRight.x &&
    p.y in upperLeft.y..<lowerRight.y
}

// Delegated properties
/*
class Foo {
    private val delegate = Delegate()

    var p: Type
    set(value: Type) = delegate.setValue(/* ... */, value)
    get() = delegate.getValue(/* ... */)
}
 */

// for main.kt
class Email { /*...*/ }

// Delegated properties
/*
object Users : IdTable() {
    val name: Column<String> = varchar("name", length = 50).index()
    val age: Column<Int> = integer("age")
}

class User(id: EntityID) : Entity(id) {
    var name: String by Users.name
    var age: Int by Users.age
}

operator fun <T> Column<T>.getValue(o: Entity, desc: KProperty<*>): T
{
    // retrieve the value from the database
}
operator fun <T> Column<T>.setValue(o: Entity, desc: KProperty<*>, value: T)
{
    // update the value in the database
}
 */
