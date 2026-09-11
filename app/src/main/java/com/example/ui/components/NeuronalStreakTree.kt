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
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun NeuronalStreakTree(
  modifier: Modifier = Modifier,
  streakDays: Int = 6,
  targetDays: Int = 7
) {
  val infiniteTransition = rememberInfiniteTransition(label = "synaptic_sparkle")
  val pulse by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1600, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "streak_pulse"
  )

  Surface(
    modifier = modifier.fillMaxWidth(),
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
      // Dendritic arbor canvas in soft subtle container
      Box(
        modifier = Modifier
          .size(76.dp)
          .clip(RoundedCornerShape(18.dp))
          .background(SynapseCanvasBg)
          .border(1.dp, SynapseCardBorderSubtle, RoundedCornerShape(18.dp)),
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(8.dp)) {
          val w = size.width
          val h = size.height

          // Main axon trunk
          val trunk = Path().apply {
            moveTo(w * 0.5f, h * 0.95f)
            cubicTo(w * 0.48f, h * 0.70f, w * 0.52f, h * 0.55f, w * 0.50f, h * 0.40f)
          }
          drawPath(
            path = trunk,
            color = SynapseIris,
            style = Stroke(width = 3.5f)
          )

          // Dendritic branch 1 (left)
          val branchLeft = Path().apply {
            moveTo(w * 0.50f, h * 0.55f)
            cubicTo(w * 0.35f, h * 0.50f, w * 0.25f, h * 0.35f, w * 0.20f, h * 0.25f)
          }
          drawPath(
            path = branchLeft,
            color = SynapseIris.copy(alpha = if (streakDays >= 2) 1f else 0.25f),
            style = Stroke(width = 2f)
          )

          // Dendritic branch 2 (right)
          val branchRight = Path().apply {
            moveTo(w * 0.50f, h * 0.50f)
            cubicTo(w * 0.65f, h * 0.45f, w * 0.75f, h * 0.30f, w * 0.80f, h * 0.22f)
          }
          drawPath(
            path = branchRight,
            color = SynapseIris.copy(alpha = if (streakDays >= 3) 1f else 0.25f),
            style = Stroke(width = 2f)
          )

          // Dendritic apical tip (center)
          val branchCenter = Path().apply {
            moveTo(w * 0.50f, h * 0.40f)
            lineTo(w * 0.50f, h * 0.15f)
          }
          drawPath(
            path = branchCenter,
            color = SynapseIris.copy(alpha = if (streakDays >= 4) 1f else 0.25f),
            style = Stroke(width = 2f)
          )

          // Synaptic spines / glowing buttons
          val spineNodes = listOf(
            Offset(w * 0.20f, h * 0.25f),
            Offset(w * 0.35f, h * 0.18f),
            Offset(w * 0.50f, h * 0.15f),
            Offset(w * 0.65f, h * 0.16f),
            Offset(w * 0.80f, h * 0.22f),
            Offset(w * 0.50f, h * 0.95f)
          )

          spineNodes.forEachIndexed { i, pos ->
            val isActive = i < streakDays
            val nodeColor = if (isActive) StreakAmber else Color(0xFFCBD5E1)
            val glowRadius = if (isActive) (4.5f * pulse) else 2.5f

            if (isActive) {
              drawCircle(
                color = StreakAmber.copy(alpha = 0.25f * pulse),
                radius = glowRadius * 2.2f,
                center = pos
              )
            }
            drawCircle(color = nodeColor, radius = glowRadius, center = pos)
          }
        }
      }

      Spacer(modifier = Modifier.width(16.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(24.dp)
              .clip(CircleShape)
              .background(StreakAmberContainer),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.LocalFireDepartment,
              contentDescription = null,
              tint = StreakAmber,
              modifier = Modifier.size(16.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "$streakDays-Day Neural Streak",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = SynapseTextPrimary
          )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Synaptic habit wiring: $streakDays of $targetDays days completed.",
          style = MaterialTheme.typography.bodySmall,
          color = SynapseTextMuted,
          lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Segmented pill progress bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
          for (i in 0 until 7) {
            val isDone = i < streakDays
            Box(
              modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(if (isDone) StreakAmber else Color(0xFFE2E8F0))
            )
          }
        }
      }
    }
  }
}
