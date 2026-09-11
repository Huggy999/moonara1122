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
import com.example.data.NeurotechDevice
import com.example.data.SynapseRepository
import com.example.data.UserProfile
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
  userProfile: UserProfile,
  onShowSubscription: () -> Unit,
  onRestartOnboarding: () -> Unit
) {
  var devices by remember { mutableStateOf(SynapseRepository.neurotechDevices) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("profile_screen_column"),
    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    // User Persona Card
    item {
      Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Row(
          modifier = Modifier.padding(20.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(64.dp)
              .clip(CircleShape)
              .background(SynapseIrisContainer),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Psychology,
              contentDescription = null,
              tint = SynapseIris,
              modifier = Modifier.size(36.dp)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = userProfile.name,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = SynapseTextPrimary
            )
            Text(
              text = userProfile.levelTitle,
              style = MaterialTheme.typography.labelMedium,
              color = SynapseIris,
              fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Focus: ${userProfile.primaryGoal}",
              style = MaterialTheme.typography.bodySmall,
              color = SynapseTextMuted
            )
          }
        }
      }
    }

    // Stats Grid (Streak, Total XP, Composite Index)
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Streak Card
        Surface(
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(22.dp),
          color = SynapseCardWhite,
          border = BorderStroke(1.dp, SynapseCardBorder),
          shadowElevation = 1.dp
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(StreakAmberContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = StreakAmber, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "${userProfile.dailyStreakDays} Days",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = SynapseTextPrimary
            )
            Text(
              text = "Active Streak",
              style = MaterialTheme.typography.labelSmall,
              color = SynapseTextMuted
            )
          }
        }

        // Synapse XP Card
        Surface(
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(22.dp),
          color = SynapseCardWhite,
          border = BorderStroke(1.dp, SynapseCardBorder),
          shadowElevation = 1.dp
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(SynapseIrisContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Bolt, contentDescription = null, tint = SynapseIris, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "${userProfile.synapseXp}",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = SynapseTextPrimary
            )
            Text(
              text = "Synapse XP",
              style = MaterialTheme.typography.labelSmall,
              color = SynapseTextMuted
            )
          }
        }

        // Cognitive Index Card
        Surface(
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(22.dp),
          color = SynapseCardWhite,
          border = BorderStroke(1.dp, SynapseCardBorder),
          shadowElevation = 1.dp
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(DomainAttentionContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Analytics, contentDescription = null, tint = DomainAttentionTeal, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "${userProfile.overallIndex}",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = SynapseTextPrimary
            )
            Text(
              text = "Neural Index",
              style = MaterialTheme.typography.labelSmall,
              color = SynapseTextMuted
            )
          }
        }
      }
    }

    // Synapse Pro Status Banner
    item {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(24.dp))
          .clickable { onShowSubscription() },
        shape = RoundedCornerShape(24.dp),
        color = if (userProfile.isPremiumSubscriber) NeurotechGreenContainer else SynapseIrisContainer,
        border = BorderStroke(
          1.dp,
          if (userProfile.isPremiumSubscriber) NeurotechGreen else SynapseIris.copy(alpha = 0.3f)
        )
      ) {
        Row(
          modifier = Modifier.padding(18.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(if (userProfile.isPremiumSubscriber) NeurotechGreen else SynapseIris),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.WorkspacePremium,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (userProfile.isPremiumSubscriber) "Synapse Pro Active" else "Upgrade to Synapse Pro",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = if (userProfile.isPremiumSubscriber) NeurotechGreenOn else SynapseIrisOnContainer
            )
            Text(
              text = if (userProfile.isPremiumSubscriber) "Full curriculum and neurotech telemetry unlocked" else "Unlock 25+ modules, 8 games, and EEG sync",
              style = MaterialTheme.typography.bodySmall,
              color = SynapseTextSecondary
            )
          }

          Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = if (userProfile.isPremiumSubscriber) NeurotechGreen else SynapseIris
          )
        }
      }
    }

    // Neurotech Device Hub Header
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Neurotech Hardware Hub",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = SynapseTextPrimary
          )
          Text(
            text = "Direct Bluetooth connectivity to consumer EEG & biometrics",
            style = MaterialTheme.typography.bodySmall,
            color = SynapseTextMuted
          )
        }
      }
    }

    // List of Neurotech Devices (Muse 2, Neurable, Oura)
    items(devices.size) { index ->
      val dev = devices[index]

      Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Circular connection indicator
          Box(
            modifier = Modifier
              .size(26.dp)
              .border(
                1.5.dp,
                if (dev.isConnected) NeurotechGreen else PillIndicatorBorder,
                CircleShape
              )
              .background(
                if (dev.isConnected) NeurotechGreenContainer else Color.Transparent,
                CircleShape
              ),
            contentAlignment = Alignment.Center
          ) {
            if (dev.isConnected) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .clip(CircleShape)
                  .background(NeurotechGreen)
              )
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = dev.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SynapseTextPrimary
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "• ${dev.manufacturer}",
                style = MaterialTheme.typography.labelSmall,
                color = SynapseTextMuted
              )
            }

            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "${dev.primaryMetric} • ${if (dev.isConnected) "Battery: ${dev.batteryLevel}%" else "Disconnected"}",
              style = MaterialTheme.typography.bodySmall,
              color = if (dev.isConnected) NeurotechGreen else SynapseTextMuted
            )
          }

          Button(
            onClick = {
              devices = devices.toMutableList().also { list ->
                list[index] = dev.copy(isConnected = !dev.isConnected)
              }
            },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (dev.isConnected) SynapseCanvasBg else SynapseIris,
              contentColor = if (dev.isConnected) SynapseTextPrimary else Color.White
            ),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
          ) {
            Text(
              text = if (dev.isConnected) "Disconnect" else "Connect",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    // App Preferences / Intake Re-run
    item {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(24.dp))
          .clickable { onRestartOnboarding() },
        shape = RoundedCornerShape(24.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Row(
          modifier = Modifier.padding(18.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(SynapseCanvasBg),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.Tune, contentDescription = null, tint = SynapseIris, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(14.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Adjust Cognitive Target & Intake",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold,
              color = SynapseTextPrimary
            )
            Text(
              text = "Current commitment: ${userProfile.dailyTargetMin} min/day for ${userProfile.primaryGoal}",
              style = MaterialTheme.typography.bodySmall,
              color = SynapseTextMuted
            )
          }
          Icon(Icons.Default.ChevronRight, contentDescription = null, tint = SynapseTextMuted)
        }
      }
    }
  }
}
