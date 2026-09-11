package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.*
import kotlin.random.Random

data class StroopTrial(
  val wordText: String,
  val inkColor: Color,
  val inkName: String
)

@Composable
fun StroopGameDialog(
  onDismiss: () -> Unit,
  onComplete: (score: Int, avgReactionMs: Long) -> Unit
) {
  val colors = listOf(
    Pair("RED", Color(0xFFE11D48)),
    Pair("BLUE", Color(0xFF2563EB)),
    Pair("GREEN", Color(0xFF059669)),
    Pair("YELLOW", Color(0xFFD97706))
  )

  var trialIndex by remember { mutableStateOf(0) }
  var correctCount by remember { mutableStateOf(0) }
  var currentTrial by remember { mutableStateOf(generateTrial(colors)) }
  var startTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
  val reactionTimes = remember { mutableStateListOf<Long>() }
  var isGameOver by remember { mutableStateOf(false) }
  var feedbackText by remember { mutableStateOf<String?>(null) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .padding(12.dp)
        .testTag("stroop_game_dialog"),
      shape = RoundedCornerShape(28.dp),
      color = SynapseCardWhite,
      border = BorderStroke(1.dp, SynapseCardBorder),
      shadowElevation = 6.dp
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Stroop Color Clash",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              color = SynapseTextPrimary
            )
            Text(
              text = "Selective Attention & Inhibitory Control",
              style = MaterialTheme.typography.labelMedium,
              color = DomainAttentionTeal
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = SynapseTextPrimary)
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (!isGameOver) {
          // Progress & Score
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Trial ${trialIndex + 1} of 10",
              style = MaterialTheme.typography.labelMedium,
              color = SynapseTextMuted
            )
            Text(
              text = "Accuracy: ${if (trialIndex == 0) 100 else (correctCount * 100 / trialIndex)}%",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = SynapseIris
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
          LinearProgressIndicator(
            progress = { (trialIndex) / 10f },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = SynapseIris,
            trackColor = SynapseCanvasBg
          )

          Spacer(modifier = Modifier.height(28.dp))

          // Rule banner with circular dot
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .background(SynapseCanvasBg, RoundedCornerShape(12.dp))
              .padding(horizontal = 14.dp, vertical = 8.dp)
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(SynapseIris)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Select the INK COLOR, ignore the text!",
              style = MaterialTheme.typography.bodyMedium,
              fontWeight = FontWeight.SemiBold,
              color = SynapseTextSecondary
            )
          }

          Spacer(modifier = Modifier.height(24.dp))

          // Word stimulus card
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(110.dp)
              .background(SynapseCanvasBg, RoundedCornerShape(22.dp))
              .border(1.dp, SynapseCardBorder, RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = currentTrial.wordText,
              color = currentTrial.inkColor,
              fontSize = 42.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 2.sp
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          feedbackText?.let { fb ->
            Text(
              text = fb,
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = if (fb.contains("Correct")) NeurotechGreen else DomainEmotionRose
            )
          } ?: Spacer(modifier = Modifier.height(20.dp))

          Spacer(modifier = Modifier.height(20.dp))

          // 4 Color Response Buttons
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              colors.take(2).forEach { (colorName, colorVal) ->
                Button(
                  onClick = {
                    val rt = System.currentTimeMillis() - startTime
                    reactionTimes.add(rt)
                    if (colorName == currentTrial.inkName) {
                      correctCount++
                      feedbackText = "Correct! (${rt}ms)"
                    } else {
                      feedbackText = "Inhibition error! Ink was ${currentTrial.inkName}"
                    }

                    if (trialIndex + 1 >= 10) {
                      isGameOver = true
                      val avgRt = if (reactionTimes.isNotEmpty()) reactionTimes.average().toLong() else 0L
                      val finalScore = (correctCount * 10)
                      onComplete(finalScore, avgRt)
                    } else {
                      trialIndex++
                      currentTrial = generateTrial(colors)
                      startTime = System.currentTimeMillis()
                    }
                  },
                  modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .testTag("stroop_btn_$colorName"),
                  shape = RoundedCornerShape(16.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = colorVal)
                ) {
                  Text(
                    text = colorName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                  )
                }
              }
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              colors.takeLast(2).forEach { (colorName, colorVal) ->
                Button(
                  onClick = {
                    val rt = System.currentTimeMillis() - startTime
                    reactionTimes.add(rt)
                    if (colorName == currentTrial.inkName) {
                      correctCount++
                      feedbackText = "Correct! (${rt}ms)"
                    } else {
                      feedbackText = "Inhibition error! Ink was ${currentTrial.inkName}"
                    }

                    if (trialIndex + 1 >= 10) {
                      isGameOver = true
                      val avgRt = if (reactionTimes.isNotEmpty()) reactionTimes.average().toLong() else 0L
                      val finalScore = (correctCount * 10)
                      onComplete(finalScore, avgRt)
                    } else {
                      trialIndex++
                      currentTrial = generateTrial(colors)
                      startTime = System.currentTimeMillis()
                    }
                  },
                  modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .testTag("stroop_btn_$colorName"),
                  shape = RoundedCornerShape(16.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = colorVal)
                ) {
                  Text(
                    text = colorName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                  )
                }
              }
            }
          }
        } else {
          // Game Over summary
          val avgRt = if (reactionTimes.isNotEmpty()) reactionTimes.average().toLong() else 0L
          val finalScore = (correctCount * 10)

          Text(
            text = "Exercise Complete!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = SynapseIris
          )
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Accuracy: $correctCount / 10 correct ($finalScore%)",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = SynapseTextPrimary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Mean Reaction Latency: ${avgRt}ms",
            style = MaterialTheme.typography.bodyMedium,
            color = SynapseTextMuted
          )

          Spacer(modifier = Modifier.height(18.dp))
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = SynapseIrisContainer,
            border = BorderStroke(1.dp, SynapseIris.copy(alpha = 0.2f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(
                text = "Neuro-Cognitive Insight",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = SynapseIrisOnContainer
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Your anterior cingulate cortex (ACC) mediated conflict detection while the dorsolateral prefrontal cortex (DLPFC) suppressed the habitual reading response.",
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
            colors = ButtonDefaults.buttonColors(containerColor = SynapseIris)
          ) {
            Text("Done", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
          }
        }
      }
    }
  }
}

private fun generateTrial(colors: List<Pair<String, Color>>): StroopTrial {
  val wordPair = colors.random()
  val inkPair = if (Random.nextFloat() < 0.75f) {
    colors.filter { it.first != wordPair.first }.random()
  } else {
    wordPair
  }
  return StroopTrial(
    wordText = wordPair.first,
    inkColor = inkPair.second,
    inkName = inkPair.first
  )
}
