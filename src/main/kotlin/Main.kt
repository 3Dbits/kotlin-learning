package com.HT

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.BufferedReader
import java.io.FileReader
import java.util.concurrent.locks.Lock

//TIP 1. Extension property, needs to be top level
val String.lastChar: Char
    get() = this[length - 1]

var StringBuilder.lastChar: Char
    get() = this[length - 1]
    set(value) {
        this.setCharAt(length - 1, value)
    }

//TIP 51. Removing the overhead of lambdas with inline functions. When you declare a function as inline,
// its body is inlined—in other words, it’s substituted directly into places where the function is called
// instead of being invoked normally.
inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action()
    }
    finally {
        lock.unlock()
    }
}
// Because you’ve declared the synchronized function as inline, the code generated for every
// call to it is the same as for a synchronized statement in Java.
// https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH10_F05_Isakova.png

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {

    //TIP 2. declares a read-only reference
    val a = "Test"
    // Error:
    //a = "Test2"

    //TIP 3. Declares a reassignable reference
    var b = "Test"
    b = "Test2"

    //TIP 4. Explicit conversions
    val i = 1
    val l: Long = i.toLong()

    //TIP 5. Declares a function, No semicolon required
    fun test1() {
        println("Hello form Test1")
    }

    fun test2(a: Int, b: String = "default param") : Int {
        println("Integer is $a, and String is $b")
        return a * 2
    }

    //TIP 6. instead of void we have Unit that is type
    fun test3() : Unit {}

    //TIP 7. Nothing type: “This function never returns”
    fun fail(message: String): Nothing {
        throw IllegalStateException(message)
    }

    //TIP 8. In Kotlin, if is an expression, not a statement.
    val myBoolean = true
    val x = if (myBoolean) 3 else 5

    //TIP 9. function with expression
    fun max(a: Int, b: Int) =
        if (a > b) a else b

    //TIP 10. try/catch as expression
    val inputString = "u"
    val number = try {
        inputString.toInt()
    } catch (nfe: NumberFormatException) {
        -1
    }

    //TIP 11. Switch
    val direction = when (inputString) {
        "u" -> "UP"
        "d" -> "DOWN"
        else -> "UNKNOWN"
    }

    //TIP 12. Class,
    // public is the default visibility
    class Person(
        val name: String,
        var isStudent: Boolean
    )

    val person = Person("Bob", true)
    println(person.name)
    person.isStudent = false
    println(person.isStudent)

    //TIP 13. Enum
    val favoriteColor = Color.BLUE
    println("My favorite color is ${favoriteColor.displayName}")

    println("All available colors:")
    for (color in Color.entries) {
        color.printInfo()
    }

    //TIP 14. Using when expression with enums
    fun getColorTemperature(color: Color) = when (color) {
        Color.RED, Color.ORANGE, Color.YELLOW -> "Warm"
        Color.GREEN, Color.BLUE, Color.INDIGO -> "Cool"
        Color.VIOLET, Color.PURPLE -> "Cool to Warm"
    }

    println("${Color.RED.name} is a ${getColorTemperature(Color.RED)} color")
    println("${Color.RED.displayName} is a ${getColorTemperature(Color.RED)} color")

    fun mix(c1: Color, c2: Color) =
        when (setOf(c1, c2)) {
            setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
            setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
            setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
            else -> throw Exception("Dirty color")
        }

    //TIP  15. Using when without an argument
    fun mixOptimized(c1: Color, c2: Color) =
        when {
            (c1 == Color.RED && c2 == Color.YELLOW) ||
                    (c1 == Color.YELLOW && c2 == Color.RED) ->
            Color.ORANGE

            (c1 == Color.YELLOW && c2 == Color.BLUE) ||
                    (c1 == Color.BLUE && c2 == Color.YELLOW) ->
                Color.GREEN

            (c1 == Color.BLUE && c2 == Color.VIOLET) ||
                    (c1 == Color.VIOLET && c2 == Color.BLUE) ->
                Color.INDIGO

            else -> throw Exception("Dirty color")
        }

    //TIP 15. Smart cast
    fun eval(e: Expr): Int {
        if (e is Num) {
            val n = e as Num  // This explicit cast to Num is redundant
            return n.value
        }
        if (e is Sum) {
            return eval(e.right) + eval(e.left)  // The variable e is smart cast
        }
        throw IllegalArgumentException("Unknown expression")
    }

    //TIP Same function as before but with if expression
    fun eval2(e: Expr): Int =
        if (e is Num) {
            e.value
        } else if (e is Sum) {
            eval(e.right) + eval(e.left)
        } else {
            throw IllegalArgumentException("Unknown expression")
        }

    //TIP Again same but with switch
    fun eval3(e: Expr): Int =
        when (e) {
            is Num -> {
                println("num: ${e.value}")
                e.value
            }
            is Sum -> eval(e.right) + eval(e.left)
            else -> throw IllegalArgumentException("Unknown expr")
        }

    //TIP 16. Nested loops, alias@ is called label
    val outerCondition = true
    val innerCondition = true
    val shouldExitInner = true
    val shouldSkipInner = true
    val shouldExit = true
    val shouldSkip = true

    outer@ while (outerCondition) {
        while (innerCondition) {
            if (shouldExitInner) break
            if (shouldSkipInner) continue
            if (shouldExit) break@outer
            if (shouldSkip) continue@outer
            // ...
        }
        // ...
    }

    //TIP 17. Ranges and progressions
    val oneToTen = 1..10

    fun main() {
        for (i in 100 downTo 1 step 2) {
            print(println(i))
        }
        // 100, 98, 96,...
    }

    val binaryReps = mutableMapOf<Char, String>()
    for (char in 'A'..'F') {
        val binary = char.code.toString(radix = 2)
        binaryReps[char] = binary
    }

    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }

    val list = listOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {
        println("$index: $element")
    }

    //TIP 18. in operator
    fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
    fun isNotDigit(c: Char) = c !in '0'..'9'

    fun recognize(c: Char) = when (c) {
        in '0'..'9' -> "It's a digit!"
        in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
        else -> "I don't know..."
    }

    //TIP 19. throw construct is an expression. Kotlin doesn’t differentiate between checked and unchecked exceptions.
    // You don’t specify the exceptions thrown by a function, and you may or may not handle any exceptions.
    val percentage =
        if (number in 0..100)
            number
        else
            throw IllegalArgumentException(
                "A percentage value must be between 0 and 100: $number"
            )

    //TIP 20. Creating collections in Kotlin
    val set = setOf(1, 7, 53)
    val list2 = listOf(1, 7, 53)
    val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

    val result = mutableListOf<Int?>()

    fun whatList(someList: List<Int?>?) {}
    // https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH08_F02_Isakova.png

    //TIP 21. Extension function
    fun String.lastChar(): Char = this[this.length - 1]
    println("Kotlin".lastChar())

    //TIP 22. vararg keyword
    val list3 = listOf("args: ", *args)
    //fun <T> listOf(vararg values: T) { /* implementation */ }

    //TIP 23.Infix calls, Pair
    val infix1 = 1.to("one")
    val infix2 = 1 to "one"
    // infix fun Any.to(other: Any) = Pair(this, other)

    //TIP 24. destructuring-declaration feature
    for ((index, element) in set.withIndex()) {
        println("$index: $element")
    }
    // example of mapOf implementation:
    // fun <K, V> mapOf(vararg values: Pair<K, V>): Map<K, V>

    val p = Point(10, 20)
    val (x1, y1) = p
    val (_, y2) = p

    //TIP 25. Object equality: equals(),
    // In Kotlin, the == operator is the default way to compare two objects: it compares their values by calling equals under the hood.
    // Thus, if equals is overridden in your class, you can safely compare its instances using ==.
    // For reference comparison, you can use the === operator (checks whether two references point to the same object in memory)
    class Customer(val name: String, val postalCode: Int) {
        override fun equals(other: Any?): Boolean {
            if (other == null || other !is Customer)
                return false
            return name == other.name &&
                postalCode == other.postalCode
        }
        override fun toString() = "Customer(name=$name, postalCode=$postalCode)"
    }
    // a == b => a?.equals(b) ?: (b == null)

    //TIP 26. Identity equals operator (===).
    // Is used to check whether two references point to the exact same object in memory
    println("person1" === "person2")  // false (different objects)
    println("person1" === "person3")  // true (same object reference)

    //TIP 27. comparison operators
    val u = "aaa" <= "bbb"
    // a >= b => a.compareTo(b) >= 0

    //TIP 28. Data class. Data classes provide compiler-generated equals, hashCode, toString, copy, and other methods.
    data class Customer2(val name: String, val postalCode: Int)

    //TIP 29. Class delegation
    class DelegatingCollection<T> : Collection<T> {
        private val innerList = arrayListOf<T>()

        override val size: Int get() = innerList.size
        override fun isEmpty(): Boolean = innerList.isEmpty()
        override fun contains(element: T): Boolean = innerList.contains(element)
        override fun iterator(): Iterator<T> = innerList.iterator()
        override fun containsAll(elements: Collection<T>): Boolean =
            innerList.containsAll(elements)
    }
    // with class delegation this can be:
    class DelegatingCollection2<T>(
        innerList: Collection<T> = mutableListOf<T>()
    ) : Collection<T> by innerList
    /*
    * All the method implementations in the class are gone.
    * The compiler will generate them, and the implementation is
    * similar to that in the DelegatingCollection example.
     */

    //TIP 30. Companion objects: A place for factory methods and static members
    MyClass.callMe() // Rest in Interfaces.kt

    //TIP 31. lambda
    data class Person3(val name: String, val age: Int)
    val people = listOf(Person3("Alice", 29), Person3("Bob", 31))
    val oldestPerson = people.maxByOrNull { it.age }
    // The code in curly braces { it.age } is a lambda implementing this selector logic
    // https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH05_F01_Isakova.png

    val sum = { x: Int, y: Int -> x + y }
    println(sum(1, 2))
    // or
    val ttt = 2 // added to remove IDE error
    { println(42) }()

    // just for fun ways to write one line
    people.maxByOrNull({p: Person3 -> p.age })
    people.maxByOrNull() {p: Person3 -> p.age }
    people.maxByOrNull {p: Person3 -> p.age }
    people.maxByOrNull {p -> p.age }
    people.maxByOrNull { it.age }
    people.maxByOrNull(Person3::age) // Member references
    // If a lambda only takes a single parameter, you can refer to it with its implicit name it
    // keyword it is an autogenerated parameter name

    //TIP 32. Performing multiple operations on the same object: with
    fun alphabet() = with(StringBuilder()) {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
        toString()
    }

    //TIP 33. Initializing and configuring objects: The apply function
    fun alphabet2() = StringBuilder().apply {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
    }.toString()

    //buildString standard library function
    fun alphabet3() = buildString {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
    }

    //buildSet standard library function, also we have buildList and buildMap
    val shouldAdd = false
    val fruits = buildSet {
        add("Apple")
        if (shouldAdd) {
            addAll(listOf("Apple", "Banana", "Cherry"))
        }
    }

    //TIP 34. Performing additional actions with an object: also
    val uppercaseFruits = mutableListOf<String>()
    val reversedLongFruits = fruits
        .map { it.uppercase() }
        .also { uppercaseFruits.addAll(it) }
        .filter { it.length > 5 }
        .also { println(it) }
        .reversed()
    // [BANANA, CHERRY]
    println(uppercaseFruits)
    // [APPLE, BANANA, CHERRY]
    println(reversedLongFruits)
    // [CHERRY, BANANA]

    //TIP 35. Member references & Bound callable references
    fun salute() = println("Salute!")
    run(::salute)

    val seb = Person3("Sebastian", 26)
    val personsAgeFunction = Person3::age
    println(personsAgeFunction(seb))
    // 26
    val sebsAgeFunction = seb::age
    println(sebsAgeFunction())
    // 26

    //TIP 36. Defining SAM interfaces in Kotlin: fun interfaces.
    val isOdd = IntCondition { it % 2 != 0 }
    println(isOdd.check(1))
    // true
    println(isOdd.checkString("2"))
    // false
    println(isOdd.checkChar('3'))
    // true

    //TIP 37. Splitting a list into a pair of lists: partition
    val canBeInClub27 = { p: Person3 -> p.age <= 27 }

    val (comeIn, stayOut) = people.partition(canBeInClub27)
    println(comeIn)
    // [Person(name=Alice, age=26)]
    println(stayOut)
    // [Person(name=Bob, age=29), Person(name=Carol, age=31)]
    // https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH06_F05_Isakova.png

    //TIP 38. Transforming collections into maps: associate, associateWith, and associateBy
    val people3 = listOf(
        Person3("Joe", 22),
        Person3("Mary", 31),
        Person3("Jamie", 22)
    )
    val nameToAge = people3.associate { it.name to it.age }
    println(nameToAge)
    // {Joe=22, Mary=31, Jamie=22}
    println(nameToAge["Joe"])
    // 22

    val personToAge = people.associateWith { it.age }
    println(personToAge)
    // {Person(name=Joe, age=22)=22, Person(name=Mary, age=31)=31,
    //  Person(name=Jamie, age=22)=22}

    val ageToPerson = people.associateBy { it.age }
    println(ageToPerson)
    // {22=Person(name=Jamie, age=22), 31=Person(name=Mary, age=31)}

    // Keep in mind that keys for maps have to be unique, and the ones generated by associate,
    // associateBy, and associateWith are no exception. If your transformation function would
    // result in the same key being added multiple times, the last result overwrites any previous assignments.

    //TIP 39. Handling special cases for collections: ifEmpty
    val empty = emptyList<String>()
    val full = listOf("apple", "orange", "banana")
    println(empty.ifEmpty { listOf("no", "values", "here") })
    // [no, values, here]
    println(full.ifEmpty { listOf("no", "values", "here") })
    // [apple, orange, banana]

    // ifBlank: ifEmpty’s sibling function for strings
    val blankName = " "
    val name = "J. Doe"
    println(blankName.ifEmpty { "(unnamed)" })
    //
    println(blankName.ifBlank { "(unnamed)" })
    // (unnamed)
    println(name.ifBlank { "(unnamed)" })
    // J. Doe

    //TIP 40. Lazy collection operations: Sequences.
    // Sequences give you an alternative way to perform such computations that avoids
    // the creation of intermediate temporary objects, similar to how Java 8’s streams do
    people.map(Person3::name).filter { it.startsWith("A") }
    /*
    * The Kotlin standard library reference says that both map and filter return a list.
    * That means this chain of calls will create two lists: one to hold the results of
    * the map function and another for the results of filter. This isn’t a problem when
    * the source list contains two elements, but it becomes much less efficient if you
    * have a million.
     */

    people
        .asSequence()  // Converts the initial collection to Sequence
        .map(Person3::name)  // Sequences support the same API as collections.
        .filter { it.startsWith("A") }
        .toList()  // Converts the resulting Sequence back into a list

    // https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH06_F12_Isakova.png
    listOf(1, 2, 3, 4)
        .asSequence()
        .map { it * it }
        .find { it > 3 }

    // The order of the operations you perform on a collection can affect performance as well.

    //TIP 41. Working with nullable values
    fun strLenSafe(s: String?) = {/* */}
    // Type? = Type or null

    fun strLenSafe2(s: String?): Int =
        if (s != null) s.length else 0

    fun strLenSafe3(s: String?): Int =
        s?.length ?: 0 // ?: Elvis expression

    //TIP 42. Safely casting values without throwing exceptions: as?
    val other = 1
    val otherPerson = other as? Person ?: false
    // https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH07_F04_Isakova.png

    //TIP 43. Dealing with nullable expressions: The let function
    fun sendEmailTo(email: String) {
        println("Sending email to $email")
    }

    var email: String? = "yole@example.com"
    email?.let { sendEmailTo(it) }
    // Sending email to yole@example.com
    email = null
    email?.let { sendEmailTo(it) }
    // https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH07_F06_Isakova.png
    // https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH07_SB_Table.png

    //TIP 44. Non-null types without immediate initialization: Late-initialized properties
    class MyService {
        fun performAction(): String = "Action Done!"
    }

    // Note that a late-initialized property is always a var because you need to be able to change its value outside of the
    // constructor, and val properties are compiled into final fields that must be initialized in the constructor.
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class MyTest {
        private lateinit var myService: MyService

        @BeforeAll
        fun setUp() {
            myService = MyService()
        }

        @Test
        fun testAction() {
            assertEquals("Action Done!", myService.performAction())
        }
    }

    //TIP 45. Extending types without the safe-call operator: Extensions for nullable types
    fun String?.isNullOrBlank(): Boolean =
        this == null || this.isBlank()

    //TIP 46. multiline input string
    val input = """
        1
        abc
        42
    """.trimIndent()

    //TIP 47. Overloading arithmetic operators
    val p1 = Point(10, 20)
    val p2 = Point(30, 40)
    println(p1 + p2)
    // + sign under the hood refer to plus function
    println(p1.plus(p2))

    //extension function
    println(p1.minus(p2))

    //TIP 48. Checking whether an object belongs to a collection: The in convention
    val rect = Rectangle(Point(10, 20), Point(50, 50))
    println(Point(20, 30) in rect)
    // true
    println(Point(5, 5) in rect)
    // false
    // a in c => c.contain(a)

    //TIP 49. Delegated properties
    // var p: Type by Delegate()
    // Using delegated properties: Lazy initialization and by lazy()
    class Person4(val name: String) {
        /*
        private var _emails: List<Email>? = null

        val emails: List<Email>
            get() {
                if (_emails == null) {
                    _emails = loadEmails(this)
                }
                return _emails!!
            }
         */
        // turn into:
        val emails by lazy { loadEmails(this) }

        private fun loadEmails(person: Person4): List<Email> {
            println("Load emails for ${person.name}")
            return listOf(/*...*/)
        }
    }

    val p3 = Person4("Alice")
    p3.emails // Emails are loaded on first access.
    // Load emails for Alice
    p3.emails

    //TIP 50. Higher-order functions
    val sum2: (Int, Int) -> Int = { x3, y3 -> x3 + y3 }
    // https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781617299605/files/OEBPS/Images/CH10_F03_Isakova.png

    fun String.filter(predicate: (Char) -> Boolean): String {
        return buildString {
            for (char in this@filter) {
                if (predicate(char)) append(char)
            }
        }
    }
    println("ab1c".filter { it in 'a'..'z' })
    // abc

    // how to safely call a function that is passed in as parameter
    fun <T> Collection<T>.joinToString(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = "",
        transform: ((T) -> String)? = null
    ): String {
        val result = StringBuilder(prefix)
        for ((index, element) in this.withIndex()) {
            if (index > 0) result.append(separator)
            val str = transform?.invoke(element) // safe-call syntax to call the function
                ?: element.toString() // Elvis operator to handle the case in which a callback wasn’t specified
            result.append(str)
        }

        result.append(postfix)
        return result.toString()
    }

    //TIP 52. use function from the Kotlin standard library. The use function is an extension function called
    // on a closable resource (an object implementing the Closable interface); it receives a lambda as an argument.
    fun readFirstLineFromFile(fileName: String): String {
        BufferedReader(FileReader(fileName)).use { br ->
            return br.readLine()
        }
    }
    // Of course, the use function is inlined, so its use doesn’t incur any performance overhead.

    //TIP 53. Return statements in lambdas: returning from an enclosing function

    //TODO add Generic/Annotations/Reflection/Coroutines/etc..
}
