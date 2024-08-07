package com.HT

enum class Color(val rgb: Int, val displayName: String) {
    RED(0xFF0000, "Red"),
    ORANGE(0xFFA500, "Orange"),
    YELLOW(0xFFFF00, "Yellow"),
    GREEN(0x00FF00, "Green"),
    BLUE(0x0000FF, "Blue"),
    INDIGO(0x4B0082, "Indigo"),
    VIOLET(0x8F00FF, "Violet"),
    PURPLE(0x800080, "Purple");

    fun printInfo() {
        println("Color: $displayName, RGB: ${rgb.toString(16)}")
        // Or
        println("Color $this is RGB ${rgb.toString(16)}")
    }
}

