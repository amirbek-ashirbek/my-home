package com.example.myhome.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myhome.R

// Set of Material typography styles to start with
val CirceFont = FontFamily(
	Font(R.font.circe_thin, FontWeight.Thin),
	Font(R.font.circe_extra_light, FontWeight.ExtraLight),
	Font(R.font.circe_light, FontWeight.Light),
	Font(R.font.circe_regular, FontWeight.Normal),
	Font(R.font.circe_bold, FontWeight.Bold),
	Font(R.font.circe_extra_bold, FontWeight.ExtraBold)
)

val Typography = Typography(
	defaultFontFamily = CirceFont,
	body1 = TextStyle(
		fontWeight = FontWeight.Normal,
		fontSize = 17.sp,
	),
	h1 = TextStyle(
		fontSize = 21.sp,
		fontWeight = FontWeight.Normal
	)
	/* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)