package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

@Composable
fun OnboardingDialog(
  onDismiss: () -> Unit,
  onFinish: (selectedGoal: String, dailyCommitMin: Int) -> Unit
) {
  var currentStep by remember { mutableIntStateOf(0) }
  var selectedGoal by remember { mutableStateOf("Deep Focus & Concentration") }
  var selectedMinutes by remember { mutableIntStateOf(10) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .padding(12.dp)
        .testTag("onboarding_dialog"),
      shape = RoundedCornerShape(30.dp),
      color = SynapseCardWhite,
      border = BorderStroke(1.dp, SynapseCardBorder),
      shadowElevation = 8.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Step progress pill dots
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
        ) {
          for (i in 0..3) {
            Box(
              modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(width = if (i == currentStep) 28.dp else 8.dp, height = 8.dp)
                .background(
                  color = if (i == currentStep) SynapseIris else Color(0xFFE2E8F0),
                  shape = RoundedCornerShape(4.dp)
                )
            )
          }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Step Content Switcher
        when (currentStep) {
          0 -> {
            MascotNoa(
              message = "Welcome to Synapse! I'm Noa, your personal cognitive guide. Let's explore the real architecture of your mind."
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
              text = "Science-Backed Brain Optimization",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center,
              color = SynapseTextPrimary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No pseudoscience claims. Active recall neuroscience lessons, standardized psychometric tasks, and objective biometric tracking.",
              style = MaterialTheme.typography.bodyLarge,
              textAlign = TextAlign.Center,
              color = SynapseTextSecondary,
              lineHeight = 24.sp
            )
          }
          1 -> {
            Text(
              text = "What is your primary cognitive goal?",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center,
              color = SynapseTextPrimary
            )
            Spacer(modifier = Modifier.height(20.dp))
            val goals = listOf(
              "Deep Focus & Concentration",
              "Working Memory & Recall",
              "Processing Speed & Agility",
              "Stress & Emotional Resilience"
            )
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              goals.forEach { goal ->
                val isSel = selectedGoal == goal
                Surface(
                  modifier = Modifier
                    .fillMaxWidth()
                    .border(
                      width = if (isSel) 2.dp else 1.dp,
                      color = if (isSel) SynapseIris else SynapseCardBorder,
                      shape = RoundedCornerShape(22.dp)
                    )
                    .clickable { selectedGoal = goal },
                  shape = RoundedCornerShape(22.dp),
                  color = if (isSel) SynapseIrisContainer else SynapseCardWhite
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    // Circular indicator ring
                    Box(
                      modifier = Modifier
                        .size(24.dp)
                        .border(
                          width = if (isSel) 2.dp else 1.5.dp,
                          color = if (isSel) SynapseIris else PillIndicatorBorder,
                          shape = CircleShape
                        )
                        .background(
                          color = if (isSel) SynapseIris.copy(alpha = 0.12f) else Color.Transparent,
                          shape = CircleShape
                        ),
                      contentAlignment = Alignment.Center
                    ) {
                      if (isSel) {
                        Box(
                          modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(SynapseIris)
                        )
                      }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                      text = goal,
                      style = MaterialTheme.typography.bodyLarge,
                      color = SynapseTextPrimary,
                      fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                    )
                  }
                }
              }
            }
          }
          2 -> {
            Text(
              text = "Daily Neural Commitment",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center,
              color = SynapseTextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Consistency trumps intensity. Even 5 to 10 minutes initiates long-term synaptic neurogenesis.",
              style = MaterialTheme.typography.bodyMedium,
              textAlign = TextAlign.Center,
              color = SynapseTextMuted
            )
            Spacer(modifier = Modifier.height(24.dp))
            val times = listOf(5, 10, 20)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              times.forEach { min ->
                val isSel = selectedMinutes == min
                Surface(
                  modifier = Modifier
                    .weight(1f)
                    .border(
                      width = if (isSel) 2.dp else 1.dp,
                      color = if (isSel) SynapseIris else SynapseCardBorder,
                      shape = RoundedCornerShape(22.dp)
                    )
                    .clickable { selectedMinutes = min },
                  shape = RoundedCornerShape(22.dp),
                  color = if (isSel) SynapseIrisContainer else SynapseCardWhite
                ) {
                  Column(
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                    Text(
                      text = "$min",
                      style = MaterialTheme.typography.displayMedium,
                      fontWeight = FontWeight.Bold,
                      color = if (isSel) SynapseIris else SynapseTextPrimary
                    )
                    Text(
                      text = "min/day",
                      style = MaterialTheme.typography.labelSmall,
                      color = SynapseTextMuted
                    )
                  }
                }
              }
            }
          }
          3 -> {
            Text(
              text = "Your Personalized Synaptic Plan",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center,
              color = SynapseTextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
              shape = RoundedCornerShape(24.dp),
              color = SynapseCanvasBg,
              border = BorderStroke(1.dp, SynapseCardBorder),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(32.dp)
                      .clip(CircleShape)
                      .background(SynapseIrisContainer),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(Icons.Default.Bolt, contentDescription = null, tint = SynapseIris, modifier = Modifier.size(18.dp))
                  }
                  Spacer(modifier = Modifier.width(10.dp))
                  Text(
                    text = "Week 1: Neuroplastic Foundation",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = SynapseTextPrimary
                  )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                  text = "• 1 Daily Neuroscience Micro-Lesson (4 min)\n• 1 Cognitive Adaptive Exercise ($selectedMinutes min target)\n• Weekly Baseline Cognitive Radar Mapping\n• 100% Free daily routine included",
                  style = MaterialTheme.typography.bodyMedium,
                  color = SynapseTextSecondary,
                  lineHeight = 22.sp
                )
              }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
              text = "Start free. No trial traps or forced subscriptions.",
              style = MaterialTheme.typography.labelMedium,
              color = SynapseIris,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Action buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          if (currentStep > 0) {
            OutlinedButton(
              onClick = { currentStep-- },
              modifier = Modifier
                .weight(1f)
                .height(52.dp),
              shape = RoundedCornerShape(16.dp),
              border = BorderStroke(1.dp, SynapseCardBorder)
            ) {
              Text("Back", color = SynapseTextPrimary, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.width(12.dp))
          }

          Button(
            onClick = {
              if (currentStep < 3) {
                currentStep++
              } else {
                onFinish(selectedGoal, selectedMinutes)
              }
            },
            modifier = Modifier
              .weight(1f)
              .height(52.dp)
              .testTag("onboarding_next_btn"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SynapseIris)
          ) {
            Text(
              text = if (currentStep < 3) "Continue" else "Get Started",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
          }
        }
      }
    }
  }
}
