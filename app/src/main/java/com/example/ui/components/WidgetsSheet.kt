package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.*

data class DesignWidgetItem(
  val title: String,
  val subtitle: String,
  val icon: ImageVector,
  val accentColor: Color,
  val detailDescription: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetsSheet(
  onDismiss: () -> Unit,
  onOpenOnboarding: () -> Unit,
  onOpenMascotTip: () -> Unit
) {
  val widgetItems = remember {
    listOf(
      DesignWidgetItem(
        title = "Typography",
        subtitle = "Clean scale, generous line-heights & crisp weights",
        icon = Icons.Default.TextFields,
        accentColor = SynapseIris,
        detailDescription = "Strict vertical rhythm using 26sp Display, 22sp Headings, and 15sp/24sp Body text with balanced kerning for effortless cognitive readability."
      ),
      DesignWidgetItem(
        title = "Iconography",
        subtitle = "Geometric precision with consistent stroke weight",
        icon = Icons.Default.Category,
        accentColor = SynapseCyan,
        detailDescription = "Material 3 unified symbol set using dual-tone cognitive domain containers and 48dp minimum touch target boundaries."
      ),
      DesignWidgetItem(
        title = "Onboarding",
        subtitle = "Zero-friction 4-step cognitive intake journey",
        icon = Icons.Default.Explore,
        accentColor = DomainAttentionTeal,
        detailDescription = "Guided habit alignment: goal selection, time commitment calibration, and week-1 synaptic roadmap projection."
      ),
      DesignWidgetItem(
        title = "Mascot, animal, character",
        subtitle = "Noa the Brain Guide with real-time breathing physics",
        icon = Icons.Default.SmartToy,
        accentColor = DomainFlexibilityPurple,
        detailDescription = "Interactive vector character that floats, blinks, and delivers contextual evidence-based neuroplasticity advice."
      ),
      DesignWidgetItem(
        title = "amazing unforgetable customer experience",
        subtitle = "Transparent science, zero pseudoscience, delightful feedback",
        icon = Icons.Default.AutoAwesome,
        accentColor = StreakAmber,
        detailDescription = "Fast haptic-inspired micro-interactions, responsive progress reflections, XP celebrations, and respectful non-predatory freemium terms."
      ),
      DesignWidgetItem(
        title = "Animations and Interactions & Details and Motion",
        subtitle = "Harmonic EEG waveforms, pulse transitions & spring physics",
        icon = Icons.Default.MotionPhotosOn,
        accentColor = DomainSpeedOrange,
        detailDescription = "60fps continuous sinusoidal traveling wave synthesis, dendritic arborization growth, and active recall card flip dynamics."
      ),
      DesignWidgetItem(
        title = "Streaks feature, gamification",
        subtitle = "Neuronal Arbor habit growth & Synapse XP",
        icon = Icons.Default.LocalFireDepartment,
        accentColor = StreakAmber,
        detailDescription = "Visual habit persistence represented as an organic dendritic tree sprouting glowing synaptic spines rather than dry checkmarks."
      ),
      DesignWidgetItem(
        title = "Illustrations",
        subtitle = "Custom vector neural anatomy & brainwave visuals",
        icon = Icons.Default.Palette,
        accentColor = DomainEmotionRose,
        detailDescription = "High-fidelity procedural vector graphics including the 5-domain cognitive radar spider chart and multi-band EEG monitor."
      ),
      DesignWidgetItem(
        title = "Animations & Interactions",
        subtitle = "Interactive Stroop Conflict & PVT Millisecond Reflex",
        icon = Icons.Default.Psychology,
        accentColor = DomainMemoryBlue,
        detailDescription = "Fully playable cognitive experimental paradigms with real-time millisecond latency measurement and active recall quizzing."
      )
    )
  }

  var selectedItemIndex by remember { mutableStateOf(0) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .testTag("widgets_screen_surface"),
      color = SynapseCanvasBg
    ) {
      Scaffold(
        containerColor = SynapseCanvasBg,
        topBar = {
          TopAppBar(
            title = {
              Text(
                text = "Widgets",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = SynapseTextPrimary
              )
            },
            navigationIcon = {
              IconButton(onClick = onDismiss) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                  contentDescription = "Back",
                  tint = SynapseTextPrimary
                )
              }
            },
            actions = {
              IconButton(onClick = { /* search action */ }) {
                Icon(
                  imageVector = Icons.Default.Search,
                  contentDescription = "Search",
                  tint = SynapseTextPrimary
                )
              }
              IconButton(onClick = { /* more action */ }) {
                Icon(
                  imageVector = Icons.Default.MoreVert,
                  contentDescription = "More",
                  tint = SynapseTextPrimary
                )
              }
            },
            colors = TopAppBarDefaults.topAppBarColors(
              containerColor = SynapseCanvasBg
            )
          )
        }
      ) { innerPadding ->
        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
          contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          items(widgetItems.indices.toList()) { index ->
            val item = widgetItems[index]
            val isSelected = selectedItemIndex == index

            Surface(
              shape = RoundedCornerShape(26.dp),
              color = SynapseCardWhite,
              border = androidx.compose.foundation.BorderStroke(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) item.accentColor else SynapseCardBorder
              ),
              shadowElevation = if (isSelected) 2.dp else 0.5.dp,
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
                .clickable {
                  selectedItemIndex = index
                  if (item.title == "Onboarding") onOpenOnboarding()
                  if (item.title.contains("Mascot")) onOpenMascotTip()
                }
                .testTag("widget_item_$index")
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 18.dp, vertical = 16.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  // Elegant circular indicator ring (inspired by screenshot)
                  Box(
                    modifier = Modifier
                      .size(26.dp)
                      .border(
                        width = if (isSelected) 2.dp else 1.5.dp,
                        color = if (isSelected) item.accentColor else PillIndicatorBorder,
                        shape = CircleShape
                      )
                      .background(
                        color = if (isSelected) item.accentColor.copy(alpha = 0.12f) else Color.Transparent,
                        shape = CircleShape
                      ),
                    contentAlignment = Alignment.Center
                  ) {
                    if (isSelected) {
                      Box(
                        modifier = Modifier
                          .size(10.dp)
                          .clip(CircleShape)
                          .background(item.accentColor)
                      )
                    }
                  }

                  Spacer(modifier = Modifier.width(16.dp))

                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = item.title,
                      style = MaterialTheme.typography.titleMedium,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                      color = SynapseTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                      text = item.subtitle,
                      style = MaterialTheme.typography.bodySmall,
                      color = SynapseTextMuted
                    )
                  }

                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .clip(CircleShape)
                      .background(item.accentColor.copy(alpha = 0.10f)),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = item.icon,
                      contentDescription = null,
                      tint = item.accentColor,
                      modifier = Modifier.size(20.dp)
                    )
                  }
                }

                AnimatedVisibility(visible = isSelected) {
                  Column(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(top = 12.dp, start = 42.dp)
                  ) {
                    HorizontalDivider(
                      color = SynapseCardBorder.copy(alpha = 0.5f),
                      modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                      text = item.detailDescription,
                      style = MaterialTheme.typography.bodyMedium,
                      color = SynapseTextSecondary,
                      lineHeight = 22.sp
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
