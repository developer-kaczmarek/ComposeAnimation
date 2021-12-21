package io.github.kaczmarek.disneyanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kaczmarek.disneyanimation.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisneyAnimationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DisneyLogo()
                }
            }
        }
    }
}

@Composable
fun DisneyLogo() {
    var arcAnimationPlayed by remember { mutableStateOf(false) }
    var textLogoAnimationPlayed by remember { mutableStateOf(false) }
    var plusAnimationPlayed by remember { mutableStateOf(false) }

    val alphaPercent = animateFloatAsState(
        targetValue = if (textLogoAnimationPlayed) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 0
        ),
        finishedListener = { arcAnimationPlayed = true }
    )

    val currentPercent = animateFloatAsState(
        targetValue = if (arcAnimationPlayed) 135f else 0f,
        animationSpec = tween(
            durationMillis = 1500,
            delayMillis = 200,
            easing = FastOutLinearInEasing
        ),
        finishedListener = { plusAnimationPlayed = true }
    )

    val scalePercent = animateFloatAsState(
        targetValue = if (plusAnimationPlayed) 1f else 0f,
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = 0
        )
    )

    LaunchedEffect(key1 = true) {
        textLogoAnimationPlayed = true
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .drawBehind {
                    drawArc(
                        brush = Brush.linearGradient(
                            0f to GradientColor1,
                            0.2f to GradientColor2,
                            0.35f to GradientColor3,
                            0.45f to GradientColor4,
                            0.75f to GradientColor5,
                        ),
                        startAngle = -152f,
                        sweepAngle = currentPercent.value,
                        useCenter = false,
                        style = Stroke(width = 10f, cap = StrokeCap.Round)
                    )
                }
        )
        Row {
            Image(
                painter = painterResource(id = R.drawable.img_disney_logo_text),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(200.dp),
                alpha = alphaPercent.value
            )
            Image(
                painter = painterResource(id = R.drawable.img_plus),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
                    .scale(scalePercent.value)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DisneyAnimationTheme {
        DisneyLogo()
    }
}