package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.BrainWaveVisualizer
import com.example.ui.components.MascotNoa
import com.example.ui.components.NeuronalStreakTree
import com.example.ui.theme.*

@Composable
fun HomeScreen(
  userProfile: UserProfile,
  onOpenLesson: (Lesson, CognitiveDomain) -> Unit,
  onPlayGame: (CognitiveGame) -> Unit,
  onOpenTest: (CognitiveTest) -> Unit,
  onOpenNeurotech: () -> Unit
) {
  val dailyLesson = SynapseRepository.courseModules[0].lessons[0]
  val dailyGame = SynapseRepository.cognitiveGames[0]
  val dailyTest = SynapseRepository.cognitiveTests[0]

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("home_screen_column"),
    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    // Top Greeting & Header with spacious balance
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Welcome back,",
            style = MaterialTheme.typography.labelMedium,
            color = SynapseTextMuted
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = userProfile.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = SynapseTextPrimary
          )
        }

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = SynapseIrisContainer,
          border = BorderStroke(1.dp, SynapseIris.copy(alpha = 0.2f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Bolt,
              contentDescription = null,
              tint = SynapseIris,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "${userProfile.synapseXp} XP",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = SynapseIris
            )
          }
        }
      }
    }

    // Mascot Noa Guidance Card
    item {
      MascotNoa(
        message = SynapseRepository.mascotTips[1]
      )
    }

    // Growing Neuronal Streak Tree
    item {
      NeuronalStreakTree(
        streakDays = userProfile.dailyStreakDays
      )
    }

    // Daily Brain Plan Section Header
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Today's Brain Plan",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = SynapseTextPrimary
          )
          Text(
            text = "Structured 3-step synaptic daily routine",
            style = MaterialTheme.typography.bodySmall,
            color = SynapseTextMuted
          )
        }
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SynapseCanvasBg,
          border = BorderStroke(1.dp, SynapseCardBorder)
        ) {
          Text(
            text = "Est. 10 min",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = SynapseTextSecondary,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
          )
        }
      }
    }

    // Plan Item 1: Neuroscience Micro-Lesson (spacious pill card with circular indicator)
    item {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(26.dp))
          .clickable { onOpenLesson(dailyLesson, CognitiveDomain.ATTENTION) }
          .testTag("home_lesson_card"),
        shape = RoundedCornerShape(26.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Circular status ring indicator
          Box(
            modifier = Modifier
              .size(26.dp)
              .border(1.5.dp, CognitiveDomain.ATTENTION.primaryColor, CircleShape)
              .background(CognitiveDomain.ATTENTION.containerColor, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(CognitiveDomain.ATTENTION.primaryColor)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "LEARN • 4 MIN",
              style = MaterialTheme.typography.labelSmall,
              color = CognitiveDomain.ATTENTION.primaryColor,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = dailyLesson.title,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold,
              color = SynapseTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Neocortical layers & sensory gating",
              style = MaterialTheme.typography.bodySmall,
              color = SynapseTextMuted
            )
          }

          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(CognitiveDomain.ATTENTION.containerColor),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.MenuBook,
              contentDescription = null,
              tint = CognitiveDomain.ATTENTION.primaryColor,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }

    // Plan Item 2: Cognitive Adaptive Game (Stroop)
    item {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(26.dp))
          .clickable { onPlayGame(dailyGame) }
          .testTag("home_game_card"),
        shape = RoundedCornerShape(26.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Circular status ring indicator
          Box(
            modifier = Modifier
              .size(26.dp)
              .border(1.5.dp, dailyGame.domain.primaryColor, CircleShape)
              .background(dailyGame.domain.containerColor, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(dailyGame.domain.primaryColor)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "TRAIN • ${dailyGame.durationEstimate.uppercase()}",
              style = MaterialTheme.typography.labelSmall,
              color = dailyGame.domain.primaryColor,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = dailyGame.title,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold,
              color = SynapseTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Inhibit automatic reading impulses",
              style = MaterialTheme.typography.bodySmall,
              color = SynapseTextMuted
            )
          }

          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(dailyGame.domain.containerColor),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = null,
              tint = dailyGame.domain.primaryColor,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }

    // Plan Item 3: Cognitive Baseline Test
    item {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(26.dp))
          .clickable { onOpenTest(dailyTest) }
          .testTag("home_test_card"),
        shape = RoundedCornerShape(26.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Circular status ring indicator
          Box(
            modifier = Modifier
              .size(26.dp)
              .border(1.5.dp, dailyTest.domain.primaryColor, CircleShape)
              .background(dailyTest.domain.containerColor, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(dailyTest.domain.primaryColor)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "TEST • ${dailyTest.durationMin} MIN",
              style = MaterialTheme.typography.labelSmall,
              color = dailyTest.domain.primaryColor,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = dailyTest.title,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold,
              color = SynapseTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "WAIS-IV Working Memory Battery",
              style = MaterialTheme.typography.bodySmall,
              color = SynapseTextMuted
            )
          }

          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(dailyTest.domain.containerColor),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Assessment,
              contentDescription = null,
              tint = dailyTest.domain.primaryColor,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }

    // Neurotech Device Integration Preview
    item {
      Column {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Neurotech Wearables",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = SynapseTextPrimary
            )
            Text(
              text = "Live telemetry & brainwave sync",
              style = MaterialTheme.typography.bodySmall,
              color = SynapseTextMuted
            )
          }
          TextButton(onClick = onOpenNeurotech) {
            Text("Manage Devices", color = SynapseIris, fontWeight = FontWeight.SemiBold)
          }
        }
        Spacer(modifier = Modifier.height(10.dp))
        BrainWaveVisualizer()
      }
    }
  }
}
