package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

data class RadarDomainValue(
  val label: String,
  val scorePct: Float,
  val color: Color,
  val containerColor: Color
)

@Composable
fun CognitiveRadarChart(
  modifier: Modifier = Modifier,
  values: List<RadarDomainValue> = listOf(
    RadarDomainValue("Memory", 0.86f, DomainMemoryBlue, DomainMemoryContainer),
    RadarDomainValue("Attention", 0.91f, DomainAttentionTeal, DomainAttentionContainer),
    RadarDomainValue("Speed", 0.78f, DomainSpeedOrange, DomainSpeedContainer),
    RadarDomainValue("Flexibility", 0.82f, DomainFlexibilityPurple, DomainFlexibilityContainer),
    RadarDomainValue("Regulation", 0.84f, DomainEmotionRose, DomainEmotionContainer)
  )
) {
  val n = values.size

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(
      modifier = Modifier
        .size(240.dp)
        .padding(12.dp),
      contentAlignment = Alignment.Center
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxRadius = (size.minDimension / 2f) * 0.85f

        // Concentric reference rings
        val rings = listOf(0.25f, 0.5f, 0.75f, 1.0f)
        rings.forEach { ratio ->
          val ringPath = Path()
          for (i in 0 until n) {
            val angle = -Math.PI / 2 + (2 * Math.PI * i / n)
            val x = center.x + (maxRadius * ratio * cos(angle)).toFloat()
            val y = center.y + (maxRadius * ratio * sin(angle)).toFloat()
            if (i == 0) ringPath.moveTo(x, y) else ringPath.lineTo(x, y)
          }
          ringPath.close()
          drawPath(
            path = ringPath,
            color = Color(0xFFCBD5E1).copy(alpha = if (ratio == 1f) 0.8f else 0.4f),
            style = Stroke(width = if (ratio == 1f) 1.5f else 1f)
          )
        }

        // Radial spokes
        for (i in 0 until n) {
          val angle = -Math.PI / 2 + (2 * Math.PI * i / n)
          val x = center.x + (maxRadius * cos(angle)).toFloat()
          val y = center.y + (maxRadius * sin(angle)).toFloat()
          drawLine(
            color = Color(0xFFE2E8F0),
            start = center,
            end = Offset(x, y),
            strokeWidth = 1.2f
          )
        }

        // Polygon points for scores
        val scorePath = Path()
        val scorePoints = mutableListOf<Offset>()
        for (i in 0 until n) {
          val angle = -Math.PI / 2 + (2 * Math.PI * i / n)
          val score = values[i].scorePct.coerceIn(0.15f, 1.0f)
          val r = maxRadius * score
          val x = center.x + (r * cos(angle)).toFloat()
          val y = center.y + (r * sin(angle)).toFloat()
          scorePoints.add(Offset(x, y))
          if (i == 0) scorePath.moveTo(x, y) else scorePath.lineTo(x, y)
        }
        scorePath.close()

        // Soft gradient fill
        drawPath(
          path = scorePath,
          brush = Brush.radialGradient(
            colors = listOf(
              SynapseIris.copy(alpha = 0.35f),
              SynapseCyan.copy(alpha = 0.20f),
              DomainAttentionTeal.copy(alpha = 0.10f)
            ),
            center = center,
            radius = maxRadius
          )
        )

        // Outline
        drawPath(
          path = scorePath,
          color = SynapseIris,
          style = Stroke(width = 2.8f)
        )

        // Vertex points with outer rings
        scorePoints.forEachIndexed { index, point ->
          drawCircle(
            color = values[index].color.copy(alpha = 0.25f),
            radius = 7.dp.toPx(),
            center = point
          )
          drawCircle(
            color = values[index].color,
            radius = 4.5.dp.toPx(),
            center = point
          )
          drawCircle(
            color = Color.White,
            radius = 2.dp.toPx(),
            center = point
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Legend pills with circular indicators
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      values.forEach { item ->
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = item.containerColor,
          modifier = Modifier.padding(horizontal = 2.dp)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(item.color)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
              Text(
                text = "${(item.scorePct * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = item.color
              )
              Text(
                text = item.label,
                style = MaterialTheme.typography.labelSmall,
                color = SynapseTextMuted,
                fontSize = 10.sp
              )
            }
          }
        }
      }
    }
  }
}
