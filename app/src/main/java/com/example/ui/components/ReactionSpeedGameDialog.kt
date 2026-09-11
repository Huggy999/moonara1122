package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class ReactionState {
  INSTRUCTIONS,
  WAITING,
  TRIGGERED,
  TOO_EARLY,
  ROUND_RESULT,
  COMPLETED
}

@Composable
fun ReactionSpeedGameDialog(
  onDismiss: () -> Unit,
  onComplete: (bestMs: Long) -> Unit
) {
  var gameState by remember { mutableStateOf(ReactionState.INSTRUCTIONS) }
  var round by remember { mutableIntStateOf(1) }
  val totalRounds = 5
  val reactionTimes = remember { mutableStateListOf<Long>() }
  var triggerTime by remember { mutableLongStateOf(0L) }
  var lastRoundMs by remember { mutableLongStateOf(0L) }

  LaunchedEffect(gameState) {
    if (gameState == ReactionState.WAITING) {
      val randomDelayMs = Random.nextLong(1500, 3500)
      delay(randomDelayMs)
      if (gameState == ReactionState.WAITING) {
        triggerTime = System.currentTimeMillis()
        gameState = ReactionState.TRIGGERED
      }
    }
  }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .padding(12.dp)
        .testTag("reaction_speed_dialog"),
      shape = RoundedCornerShape(28.dp),
      color = SynapseCardWhite,
      border = BorderStroke(1.dp, SynapseCardBorder),
      shadowElevation = 6.dp
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Synaptic Reflex (PVT)",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              color = SynapseTextPrimary
            )
            Text(
              text = "Processing Speed & White Matter Latency",
              style = MaterialTheme.typography.labelMedium,
              color = DomainSpeedOrange
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = SynapseTextPrimary)
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (gameState != ReactionState.COMPLETED) {
          Text(
            text = "Round $round of $totalRounds",
            style = MaterialTheme.typography.labelMedium,
            color = SynapseTextMuted
          )
          Spacer(modifier = Modifier.height(16.dp))

          val areaColor = when (gameState) {
            ReactionState.INSTRUCTIONS -> SynapseCanvasBg
            ReactionState.WAITING -> Color(0xFFFEF3C7) // warm amber
            ReactionState.TRIGGERED -> Color(0xFF10B981) // emerald
            ReactionState.TOO_EARLY -> Color(0xFFFEE2E2) // rose error
            ReactionState.ROUND_RESULT -> SynapseIrisContainer
            ReactionState.COMPLETED -> SynapseCanvasBg
          }

          val textColor = when (gameState) {
            ReactionState.INSTRUCTIONS -> SynapseTextPrimary
            ReactionState.WAITING -> Color(0xFF92400E)
            ReactionState.TRIGGERED -> Color.White
            ReactionState.TOO_EARLY -> Color(0xFFB91C1C)
            ReactionState.ROUND_RESULT -> SynapseIrisOnContainer
            ReactionState.COMPLETED -> SynapseTextPrimary
          }

          val statusText = when (gameState) {
            ReactionState.INSTRUCTIONS -> "Tap anywhere in this card to start"
            ReactionState.WAITING -> "Wait for GREEN spark...\nDo not tap yet!"
            ReactionState.TRIGGERED -> "TAP NOW!"
            ReactionState.TOO_EARLY -> "False Start!\nTap to try again"
            ReactionState.ROUND_RESULT -> "${lastRoundMs}ms\nTap for next round"
            ReactionState.COMPLETED -> ""
          }

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(210.dp)
              .clip(RoundedCornerShape(24.dp))
              .background(areaColor)
              .border(1.5.dp, SynapseCardBorder, RoundedCornerShape(24.dp))
              .clickable {
                when (gameState) {
                  ReactionState.INSTRUCTIONS -> gameState = ReactionState.WAITING
                  ReactionState.WAITING -> gameState = ReactionState.TOO_EARLY
                  ReactionState.TRIGGERED -> {
                    val rt = System.currentTimeMillis() - triggerTime
                    lastRoundMs = rt
                    reactionTimes.add(rt)
                    if (round >= totalRounds) {
                      gameState = ReactionState.COMPLETED
                      val best = reactionTimes.minOrNull() ?: rt
                      onComplete(best)
                    } else {
                      gameState = ReactionState.ROUND_RESULT
                    }
                  }
                  ReactionState.TOO_EARLY -> gameState = ReactionState.WAITING
                  ReactionState.ROUND_RESULT -> {
                    round++
                    gameState = ReactionState.WAITING
                  }
                  ReactionState.COMPLETED -> {}
                }
              }
              .testTag("reaction_tap_surface"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = statusText,
              style = MaterialTheme.typography.headlineSmall,
              fontWeight = FontWeight.Bold,
              color = textColor,
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(18.dp)
            )
          }

          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "Human neural benchmark: 190ms - 260ms for healthy adults.",
            style = MaterialTheme.typography.bodySmall,
            color = SynapseTextMuted,
            textAlign = TextAlign.Center
          )
        } else {
          val avgMs = if (reactionTimes.isNotEmpty()) reactionTimes.average().toLong() else 0L
          val bestMs = reactionTimes.minOrNull() ?: 0L

          Text(
            text = "PVT Reflex Complete!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = DomainSpeedOrange
          )
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Best Reaction: ${bestMs}ms",
            style = MaterialTheme.typography.titleLarge,
            color = SynapseTextPrimary,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Average Latency: ${avgMs}ms across $totalRounds trials",
            style = MaterialTheme.typography.bodyMedium,
            color = SynapseTextMuted
          )

          Spacer(modifier = Modifier.height(18.dp))
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = DomainSpeedContainer,
            border = BorderStroke(1.dp, DomainSpeedOrange.copy(alpha = 0.25f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(
                text = "Neuro-Myelination Metric",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = DomainSpeedOn
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Signal transduction velocity along the corticospinal tract and optic radiation was recorded. Fast responses indicate optimal resting potentials.",
                style = MaterialTheme.typography.bodySmall,
                color = SynapseTextPrimary,
                lineHeight = 18.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(24.dp))
          Button(
            onClick = onDismiss,
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DomainSpeedOrange)
          ) {
            Text("Complete", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
          }
        }
      }
    }
  }
}
