package com.example.ui.components

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WorkspacePremium
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
fun SubscriptionSheet(
  onDismiss: () -> Unit,
  onSubscribe: () -> Unit
) {
  var isAnnual by remember { mutableStateOf(true) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .fillMaxHeight(0.90f)
        .padding(6.dp)
        .testTag("subscription_sheet"),
      shape = RoundedCornerShape(30.dp),
      color = SynapseCardWhite,
      border = BorderStroke(1.dp, SynapseCardBorder),
      shadowElevation = 8.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(24.dp)
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = SynapseTextPrimary)
          }
        }

        Box(
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(SynapseIrisContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.WorkspacePremium,
            contentDescription = null,
            tint = SynapseIris,
            modifier = Modifier.size(36.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Synapse Pro",
          style = MaterialTheme.typography.headlineLarge,
          fontWeight = FontWeight.Bold,
          color = SynapseTextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "Full Cognitive Science Suite & Neurotech Sync",
          style = MaterialTheme.typography.bodyMedium,
          color = SynapseTextMuted,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Billing pill toggle
        Row(
          modifier = Modifier
            .background(SynapseCanvasBg, RoundedCornerShape(16.dp))
            .border(1.dp, SynapseCardBorder, RoundedCornerShape(16.dp))
            .padding(4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isAnnual) SynapseIris else Color.Transparent,
            modifier = Modifier.clickable { isAnnual = true }
          ) {
            Text(
              text = "Annual (Save 40%)",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = if (isAnnual) Color.White else SynapseTextSecondary,
              modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (!isAnnual) SynapseIris else Color.Transparent,
            modifier = Modifier.clickable { isAnnual = false }
          ) {
            Text(
              text = "Monthly",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = if (!isAnnual) Color.White else SynapseTextSecondary,
              modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = if (isAnnual) "$4.99 / month (billed $59.99/year)" else "$8.99 / month (cancel anytime)",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = SynapseTextPrimary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Feature Comparison Card
        Surface(
          shape = RoundedCornerShape(22.dp),
          color = SynapseCanvasBg,
          border = BorderStroke(1.dp, SynapseCardBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Text(
              text = "PRO MEMBERSHIP INCLUDES:",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = SynapseIris
            )
            Spacer(modifier = Modifier.height(12.dp))

            val proFeatures = listOf(
              "Full 25+ Neuroscience modules (Sleep, LTP, Executive networks)",
              "All 8 Adaptive Cognitive Training games with lab analytics",
              "Unlimited Standardized Cognitive Battery (WAIS, PVT, Flanker)",
              "Live EEG Wearables Sync (Muse, Neurable, Oura Ring)",
              "Zero ads, zero dark patterns, lifetime science guarantee"
            )

            proFeatures.forEach { feat ->
              Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.Top
              ) {
                // Circular indicator ring
                Box(
                  modifier = Modifier
                    .size(20.dp)
                    .border(1.5.dp, SynapseIris, CircleShape)
                    .background(SynapseIrisContainer, CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = SynapseIris,
                    modifier = Modifier.size(12.dp)
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                  text = feat,
                  style = MaterialTheme.typography.bodyMedium,
                  color = SynapseTextPrimary,
                  lineHeight = 20.sp
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
          onClick = onSubscribe,
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("subscribe_action_btn"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = SynapseIris)
        ) {
          Text(
            text = "Start 7-Day Free Trial",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
          )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "No commitment. Free tier remains fully functional forever.",
          style = MaterialTheme.typography.labelSmall,
          color = SynapseTextMuted
        )
      }
    }
  }
}
