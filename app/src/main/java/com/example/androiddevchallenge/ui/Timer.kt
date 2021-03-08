/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Timer(seconds: Int, onCancel: () -> Unit) {
    var trigger by remember { mutableStateOf(seconds) }
    val elapsed by animateIntAsState(
        targetValue = trigger * 1000,
        animationSpec = tween(seconds * 1000, easing = LinearEasing)
    )
    DisposableEffect(Unit) {
        trigger = 0
        onDispose { }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box {
            TimerText(elapsed)
            CircleLoop(elapsed)
        }
        Spacer(modifier = Modifier.height(16.dp))

        FloatingActionButton(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
            onClick = { onCancel() }
        ) {
            Icon(imageVector = Icons.Default.Stop, contentDescription = "Stop")
        }
    }
}

@Composable
private fun BoxScope.TimerText(elapsed: Int) {
    val (h, m, s) = remember(elapsed / 1000) {
        val elapsedInSec = elapsed / 1000
        val h = elapsedInSec / 3600
        val m = elapsedInSec / 60 - h * 60
        val s = elapsedInSec % 60
        Triple(h, m, s)
    }
    val transition = rememberInfiniteTransition()
    val animatedFont by transition.animateFloat(
        initialValue = 1.5f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )
    Text(
        text = String.format("%02d:%02d:%02d", h, m, s),
        modifier = Modifier.align(Alignment.Center),
        fontSize = 16.sp * animatedFont
    )
}

@Composable
private fun BoxScope.CircleLoop(elapsed: Int) {
    val transition = rememberInfiniteTransition()
    var trigger by remember { mutableStateOf(0f) }
    var finished by remember { mutableStateOf(false) }
    val animateTween by animateFloatAsState(
        targetValue = trigger,
        animationSpec = tween(durationMillis = elapsed, easing = LinearEasing),
        finishedListener = { finished = true }
    )
    val animateRound by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Restart)
    )
    DisposableEffect(Unit) {
        trigger = 360f
        onDispose { }
    }
    val stroke = Stroke(width = 10f)
    val color = MaterialTheme.colors.secondary
    Canvas(
        modifier = Modifier
            .size(200.dp)
            .align(Alignment.Center)
    ) {
        val radius = size.minDimension / 2f
        val size = Size(radius * 2, radius * 2)
        drawCircle(
            color = color,
            style = stroke,
            radius = radius
        )
        drawArc(
            startAngle = 270f,
            sweepAngle = animateTween,
            brush = Brush.radialGradient(
                radius = radius,
                colors = listOf(
                    color.copy(0.1f),
                    color
                )
            ),
            useCenter = true,
            style = Fill
        )
    }
}
