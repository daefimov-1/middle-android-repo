package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?
) {
    // Блок создания и инициализации переменных
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toFloat()

    val animatedHeight1 = remember { Animatable(0f) }
    val animatedHeight2 = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    // Блок активации анимации при первом запуске
    LaunchedEffect(Unit) {
        launch {
            animatedHeight1.animateTo(
                targetValue = -1 * screenHeight / 4,
                animationSpec = tween(durationMillis = 5000)
            )
        }
        launch {
            animatedHeight2.animateTo(
                targetValue = screenHeight / 4,
                animationSpec = tween(durationMillis = 5000)
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2000)
            )
        }
    }

    Box(
        modifier = Modifier
            .offset(y = animatedHeight1.value.dp)
            .height((screenHeight / 2).dp)
            .alpha(alpha.value)
    ) {
        firstChild?.invoke()
    }

    Box(
        modifier = Modifier
            .offset(y = animatedHeight2.value.dp)
            .height((screenHeight / 2).dp)
            .alpha(alpha.value)
    ) {
        secondChild?.invoke()
    }
}