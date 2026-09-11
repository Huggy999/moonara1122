package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlin.math.sin

@Composable
fun BrainWaveVisualizer(
  modifier: Modifier = Modifier,
  activeBand: String = "Alpha",
  channelName: String = "Channel AF7/AF8 (Frontal Cortex)",
  isLive: Boolean = true
) {
  val infiniteTransition = rememberInfiniteTransition(label = "eeg_wave")
  val phase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = (2 * Math.PI).toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(1800, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "wave_phase"
  )

  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(26.dp),
    color = SynapseCardWhite,
    border = BorderStroke(1.dp, SynapseCardBorder),
    shadowElevation = 1.dp
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Circular status ring
          Box(
            modifier = Modifier
              .size(24.dp)
              .border(1.5.dp, NeurotechGreen, CircleShape)
              .background(NeurotechGreenContainer, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(if (isLive) NeurotechGreen else Color.Gray)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Live Neurofeedback Stream",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold,
              color = SynapseTextPrimary
            )
            Text(
              text = channelName,
              style = MaterialTheme.typography.labelSmall,
              color = SynapseTextMuted
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SynapseCyanContainer
        ) {
          Text(
            text = "10.4 Hz • $activeBand",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = SynapseCyanOnContainer,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // EEG Real-Time Canvas with clean subtle background
      Canvas(
        modifier = Modifier
          .fillMaxWidth()
          .height(68.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(Color(0xFFF1F5F9))
          .border(1.dp, SynapseCardBorderSubtle, RoundedCornerShape(16.dp))
      ) {
        val w = size.width
        val h = size.height
        val midY = h / 2f

        // Center zero line
        drawLine(
          color = Color(0xFFCBD5E1),
          start = Offset(0f, midY),
          end = Offset(w, midY),
          strokeWidth = 1f
        )

        // Wave path 1: Primary Alpha Rhythm
        val alphaPath = Path()
        val points = 100
        for (i in 0..points) {
          val x = (w * i) / points
          val normalizedX = (i.toFloat() / points) * 6 * Math.PI.toFloat()
          val yOffset = (sin(normalizedX + phase) * 0.5f +
                         sin((normalizedX * 2.2f) - phase * 1.5f) * 0.3f +
                         sin((normalizedX * 0.4f) + phase * 0.5f) * 0.2f) * (h * 0.35f)
          val y = midY + yOffset
          if (i == 0) alphaPath.moveTo(x, y) else alphaPath.lineTo(x, y)
        }

        drawPath(
          path = alphaPath,
          color = SynapseIris,
          style = Stroke(width = 2.8f)
        )

        // Wave path 2: Secondary Harmonic (Beta ripple)
        val betaPath = Path()
        for (i in 0..points) {
          val x = (w * i) / points
          val normalizedX = (i.toFloat() / points) * 12 * Math.PI.toFloat()
          val yOffset = (sin(normalizedX - (phase * 2f)) * 0.2f) * (h * 0.2f)
          val y = midY + yOffset
          if (i == 0) betaPath.moveTo(x, y) else betaPath.lineTo(x, y)
        }

        drawPath(
          path = betaPath,
          color = SynapseCyan.copy(alpha = 0.6f),
          style = Stroke(width = 1.6f)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Sensor Impedance: 4.2 kΩ (Optimal)",
          style = MaterialTheme.typography.labelSmall,
          color = SynapseTextMuted
        )
        Text(
          text = "SNR: 98% • Flow State 78%",
          style = MaterialTheme.typography.labelSmall,
          color = NeurotechGreen,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}
