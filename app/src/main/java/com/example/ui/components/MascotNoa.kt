package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

/**
 * Noa the Brain Guide:
 * Friendly, minimalist, scientifically inspired character with expressive eyes,
 * glowing neural aura, and animated subtle float and blink.
 * Redesigned with generous space, soft border, and pill geometry.
 */
@Composable
fun MascotNoa(
  modifier: Modifier = Modifier,
  message: String,
  title: String = "Noa • Your Synapse Guide",
  isCompact: Boolean = false,
  onTipClick: (() -> Unit)? = null
) {
  val infiniteTransition = rememberInfiniteTransition(label = "noa_anim")
  val floatOffset by infiniteTransition.animateFloat(
    initialValue = -2.5f,
    targetValue = 2.5f,
    animationSpec = infiniteRepeatable(
      animation = tween(2400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "float"
  )

  val pulseGlow by infiniteTransition.animateFloat(
    initialValue = 0.5f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(2000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .testTag("mascot_noa_card"),
    shape = RoundedCornerShape(26.dp),
    color = SynapseCardWhite,
    border = BorderStroke(1.dp, SynapseCardBorder),
    shadowElevation = 1.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp, vertical = 18.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Vector Mascot Character Avatar with soft circular halo
      Box(
        modifier = Modifier
          .size(if (isCompact) 58.dp else 68.dp)
          .offset(y = floatOffset.dp),
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val w = size.width
          val h = size.height
          val center = Offset(w / 2f, h / 2f)

          // Synaptic Glow Aura
          drawCircle(
            brush = Brush.radialGradient(
              colors = listOf(
                SynapseIris.copy(alpha = 0.25f * pulseGlow),
                SynapseCyan.copy(alpha = 0.15f * pulseGlow),
                Color.Transparent
              ),
              center = center,
              radius = w * 0.54f
            ),
            radius = w * 0.54f,
            center = center
          )

          // Soft Friendly Brain Body
          val lobeColor = Color(0xFF60A5FA)
          drawCircle(color = lobeColor, radius = w * 0.32f, center = Offset(w * 0.38f, h * 0.46f))
          drawCircle(color = SynapseIris, radius = w * 0.32f, center = Offset(w * 0.62f, h * 0.46f))
          drawCircle(color = Color(0xFF38BDF8), radius = w * 0.28f, center = Offset(w * 0.50f, h * 0.62f))

          // Friendly Expressive Eyes
          val eyeWhite = Color.White
          val pupilColor = Color(0xFF0F172A)

          // Left eye
          drawCircle(color = eyeWhite, radius = w * 0.08f, center = Offset(w * 0.40f, h * 0.45f))
          drawCircle(color = pupilColor, radius = w * 0.045f, center = Offset(w * 0.42f, h * 0.45f))
          drawCircle(color = eyeWhite, radius = w * 0.015f, center = Offset(w * 0.43f, h * 0.43f))

          // Right eye
          drawCircle(color = eyeWhite, radius = w * 0.08f, center = Offset(w * 0.60f, h * 0.45f))
          drawCircle(color = pupilColor, radius = w * 0.045f, center = Offset(w * 0.62f, h * 0.45f))
          drawCircle(color = eyeWhite, radius = w * 0.015f, center = Offset(w * 0.63f, h * 0.43f))

          // Friendly Smile Curve
          val smilePath = Path().apply {
            moveTo(w * 0.44f, h * 0.58f)
            quadraticTo(w * 0.50f, h * 0.65f, w * 0.56f, h * 0.58f)
          }
          drawPath(
            path = smilePath,
            color = pupilColor,
            style = androidx.compose.ui.graphics.drawscope.Stroke(
              width = w * 0.04f,
              cap = androidx.compose.ui.graphics.StrokeCap.Round
            )
          )

          // Glowing Synaptic Spark on forehead
          drawCircle(
            color = StreakAmber,
            radius = w * 0.05f * pulseGlow,
            center = Offset(w * 0.50f, h * 0.25f)
          )
        }
      }

      Spacer(modifier = Modifier.width(16.dp))

      // Speech bubble text
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Circular status ring
          Box(
            modifier = Modifier
              .size(10.dp)
              .border(1.5.dp, SynapseIris, CircleShape)
              .background(SynapseIrisContainer, CircleShape)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = SynapseIris,
            fontWeight = FontWeight.Bold
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = message,
          style = MaterialTheme.typography.bodyMedium,
          color = SynapseTextPrimary,
          lineHeight = 22.sp
        )
      }
    }
  }
}
