package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
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
import com.example.data.CognitiveDomain
import com.example.data.Lesson
import com.example.ui.theme.*

@Composable
fun LessonDetailDialog(
  lesson: Lesson,
  domain: CognitiveDomain,
  onDismiss: () -> Unit,
  onCompleted: (lessonId: String) -> Unit
) {
  var selectedOption by remember { mutableStateOf<Int?>(null) }
  var hasAnswered by remember { mutableStateOf(false) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .fillMaxHeight(0.90f)
        .padding(6.dp)
        .testTag("lesson_dialog"),
      shape = RoundedCornerShape(28.dp),
      color = SynapseCardWhite,
      border = BorderStroke(1.dp, SynapseCardBorder),
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(22.dp)
      ) {
        // Top action bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = domain.containerColor
            ) {
              Text(
                text = domain.displayName.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = domain.primaryColor,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                fontWeight = FontWeight.Bold
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "${lesson.durationMin} min read",
              style = MaterialTheme.typography.labelSmall,
              color = SynapseTextMuted
            )
          }

          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = SynapseTextPrimary)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Scrollable lesson content
        Column(
          modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
        ) {
          Text(
            text = lesson.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = SynapseTextPrimary
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Key Takeaway Card with pill design
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = domain.containerColor,
            border = BorderStroke(1.dp, domain.primaryColor.copy(alpha = 0.2f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(16.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(Color.White),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Lightbulb,
                  contentDescription = null,
                  tint = domain.primaryColor,
                  modifier = Modifier.size(18.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "Core Neuro Principle",
                  style = MaterialTheme.typography.labelMedium,
                  color = domain.primaryColor,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = lesson.takeaway,
                  style = MaterialTheme.typography.bodyMedium,
                  color = SynapseTextPrimary,
                  lineHeight = 22.sp
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // Body text with airy line height
          Text(
            text = lesson.body,
            style = MaterialTheme.typography.bodyLarge,
            color = SynapseTextSecondary,
            lineHeight = 26.sp
          )

          Spacer(modifier = Modifier.height(26.dp))

          // Active Recall Section
          HorizontalDivider(color = SynapseCardBorder)
          Spacer(modifier = Modifier.height(20.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(domain.containerColor),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                tint = domain.primaryColor,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Active Recall Challenge",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = SynapseTextPrimary
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = lesson.quiz.question,
            style = MaterialTheme.typography.bodyLarge,
            color = SynapseTextPrimary,
            fontWeight = FontWeight.Medium,
            lineHeight = 24.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Quiz options with circular indicator dots
          lesson.quiz.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOption == index
            val isCorrect = index == lesson.quiz.correctIndex
            val borderColor = when {
              !hasAnswered && isSelected -> domain.primaryColor
              hasAnswered && isCorrect -> NeurotechGreen
              hasAnswered && isSelected && !isCorrect -> DomainEmotionRose
              else -> SynapseCardBorder
            }

            val bgColor = when {
              hasAnswered && isCorrect -> NeurotechGreenContainer
              hasAnswered && isSelected && !isCorrect -> DomainEmotionContainer
              isSelected -> domain.containerColor
              else -> SynapseCardWhite
            }

            Surface(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .border(1.dp, borderColor, RoundedCornerShape(20.dp))
                .clickable(enabled = !hasAnswered) {
                  selectedOption = index
                  hasAnswered = true
                  onCompleted(lesson.id)
                },
              shape = RoundedCornerShape(20.dp),
              color = bgColor
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                // Circular indicator ring
                Box(
                  modifier = Modifier
                    .size(24.dp)
                    .border(
                      width = if (isSelected) 2.dp else 1.5.dp,
                      color = borderColor,
                      shape = CircleShape
                    )
                    .background(
                      color = if (isSelected) domain.primaryColor.copy(alpha = 0.15f) else Color.Transparent,
                      shape = CircleShape
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  if (isSelected) {
                    Box(
                      modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(if (hasAnswered && !isCorrect) DomainEmotionRose else domain.primaryColor)
                    )
                  }
                }

                Spacer(modifier = Modifier.width(14.dp))
                Text(
                  text = optionText,
                  style = MaterialTheme.typography.bodyMedium,
                  color = SynapseTextPrimary,
                  lineHeight = 20.sp
                )
              }
            }
          }

          // Neuro explanation reveal
          AnimatedVisibility(visible = hasAnswered) {
            Column(modifier = Modifier.padding(top = 16.dp)) {
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (selectedOption == lesson.quiz.correctIndex) {
                  NeurotechGreenContainer
                } else {
                  DomainEmotionContainer
                },
                border = BorderStroke(
                  1.dp,
                  if (selectedOption == lesson.quiz.correctIndex) NeurotechGreen else DomainEmotionRose
                ),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Text(
                    text = if (selectedOption == lesson.quiz.correctIndex) "Correct! +100 Synapse XP" else "Neuroplastic Opportunity",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedOption == lesson.quiz.correctIndex) NeurotechGreenOn else DomainEmotionOn
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = lesson.quiz.neuroExplanation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SynapseTextPrimary,
                    lineHeight = 22.sp
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))
        }

        // Bottom Done Button
        Button(
          onClick = onDismiss,
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("lesson_finish_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = domain.primaryColor)
        ) {
          Text("Finish & Return", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
      }
    }
  }
}
