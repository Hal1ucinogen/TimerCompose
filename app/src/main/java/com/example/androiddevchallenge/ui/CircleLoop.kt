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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.CircleLoop(mills: Int) {
    var trigger by remember { mutableStateOf(0f) }
    val animateTween by animateFloatAsState(
        targetValue = trigger,
        animationSpec = tween(durationMillis = mills, easing = LinearEasing),
        finishedListener = {}
    )
    DisposableEffect(Unit) {
        trigger = 360f
        onDispose { }
    }
    val stroke = Stroke(10f)
    val color = MaterialTheme.colors.secondary
    Canvas(
        modifier = Modifier
            .size(240.dp)
            .align(Alignment.CenterHorizontally)
    ) {
        val radius = size.minDimension / 2f
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
