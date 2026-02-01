package com.pathsoft.mobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val FONT_FAMILY = FontFamily.SansSerif

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FONT_FAMILY,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )
)

// Font Size Constants
object FontSizes {
    val H1 = 28.sp
    val H2 = 20.sp
    val H3 = 18.sp
    val H4 = 16.sp
    val BODY_LARGE = 14.sp
    val BODY = 13.sp
    val BODY_SMALL = 12.sp
    val CAPTION = 11.sp
    val TINY = 10.sp
}

// Font Weight Constants
object FontWeights {
    val REGULAR = FontWeight.Normal    // 400
    val MEDIUM = FontWeight.Medium     // 500
    val SEMIBOLD = FontWeight.SemiBold // 600
    val BOLD = FontWeight.Bold         // 700
}

