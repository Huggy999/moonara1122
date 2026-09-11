package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CognitiveTest
import com.example.data.SynapseRepository
import com.example.ui.components.CognitiveRadarChart
import com.example.ui.theme.*

@Composable
fun TestScreen(
  onSelectTest: (CognitiveTest) -> Unit,
  onShowSubscription: () -> Unit
) {
  val tests = SynapseRepository.cognitiveTests

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("test_screen_column"),
    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    // Header
    item {
      Column(modifier = Modifier.padding(top = 4.dp)) {
        Text(
          text = "Cognitive Function Tests",
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold,
          color = SynapseTextPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "Standardized psychometric batteries with percentile benchmarking against age-matched cohorts.",
          style = MaterialTheme.typography.bodyMedium,
          color = SynapseTextMuted,
          lineHeight = 22.sp
        )
      }
    }

    // Cognitive Radar Map Card
    item {
      Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Current Neural Profile",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SynapseTextPrimary
              )
              Text(
                text = "5-Domain Composite Score: 124 (High)",
                style = MaterialTheme.typography.labelSmall,
                color = SynapseIris,
                fontWeight = FontWeight.SemiBold
              )
            }

            Surface(
              shape = RoundedCornerShape(10.dp),
              color = SynapseIrisContainer
            ) {
              Text(
                text = "88th Percentile",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = SynapseIrisOnContainer,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          CognitiveRadarChart()
        }
      }
    }

    item {
      Text(
        text = "Standardized Assessment Protocols",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = SynapseTextPrimary,
        modifier = Modifier.padding(top = 6.dp)
      )
    }

    items(tests) { test ->
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(26.dp))
          .clickable { onSelectTest(test) }
          .testTag("test_card_${test.id}"),
        shape = RoundedCornerShape(26.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Circular indicator ring
            Box(
              modifier = Modifier
                .size(28.dp)
                .border(1.5.dp, test.domain.primaryColor, CircleShape)
                .background(test.domain.containerColor, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .clip(CircleShape)
                  .background(test.domain.primaryColor)
              )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = test.domain.containerColor
                ) {
                  Text(
                    text = test.domain.displayName.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = test.domain.primaryColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "${test.durationMin} min test",
                  style = MaterialTheme.typography.labelSmall,
                  color = SynapseTextMuted
                )
              }

              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = test.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SynapseTextPrimary
              )
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = SynapseCanvasBg,
              border = BorderStroke(1.dp, SynapseCardBorder)
            ) {
              Text(
                text = "${test.benchmarkPercentile}th %ile",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = test.domain.primaryColor,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = test.summaryResult,
            style = MaterialTheme.typography.bodyMedium,
            color = SynapseTextSecondary,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = test.scientificProtocol,
              style = MaterialTheme.typography.labelSmall,
              color = SynapseTextMuted,
              fontSize = 11.sp
            )

            TextButton(
              onClick = { onSelectTest(test) },
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text("Re-test", color = SynapseIris, fontWeight = FontWeight.SemiBold)
            }
          }
        }
      }
    }
  }
}
