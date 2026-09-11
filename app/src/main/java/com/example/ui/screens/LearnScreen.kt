package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.ui.theme.*

@Composable
fun LearnScreen(
  onSelectLesson: (Lesson, CognitiveDomain) -> Unit,
  onShowSubscription: () -> Unit
) {
  var selectedDomainFilter by remember { mutableStateOf<CognitiveDomain?>(null) }
  val modules = remember(selectedDomainFilter) {
    if (selectedDomainFilter == null) {
      SynapseRepository.courseModules
    } else {
      SynapseRepository.courseModules.filter { it.domain == selectedDomainFilter }
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("learn_screen_column"),
    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    // Header
    item {
      Column(modifier = Modifier.padding(top = 4.dp)) {
        Text(
          text = "Neuroscience Curriculum",
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold,
          color = SynapseTextPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "Rigorous, peer-reviewed learning modules designed with spaced repetition and active recall.",
          style = MaterialTheme.typography.bodyMedium,
          color = SynapseTextMuted,
          lineHeight = 22.sp
        )
      }
    }

    // Cognitive Domain Pill Filter Chips
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
      ) {
        item {
          val isAll = selectedDomainFilter == null
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isAll) SynapseIris else SynapseCardWhite,
            border = BorderStroke(1.dp, if (isAll) SynapseIris else SynapseCardBorder),
            modifier = Modifier.clickable { selectedDomainFilter = null }
          ) {
            Text(
              text = "All Domains",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = if (isAll) FontWeight.Bold else FontWeight.Medium,
              color = if (isAll) Color.White else SynapseTextSecondary,
              modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
          }
        }

        items(CognitiveDomain.entries.toTypedArray()) { domain ->
          val isSel = selectedDomainFilter == domain
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isSel) domain.primaryColor else SynapseCardWhite,
            border = BorderStroke(1.dp, if (isSel) domain.primaryColor else SynapseCardBorder),
            modifier = Modifier.clickable { selectedDomainFilter = domain }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(if (isSel) Color.White else domain.primaryColor)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = domain.displayName,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                color = if (isSel) Color.White else SynapseTextSecondary
              )
            }
          }
        }
      }
    }

    // Course Modules List
    items(modules) { module ->
      var isExpanded by remember { mutableStateOf(false) }

      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(26.dp))
          .testTag("course_module_${module.id}"),
        shape = RoundedCornerShape(26.dp),
        color = SynapseCardWhite,
        border = BorderStroke(1.dp, SynapseCardBorder),
        shadowElevation = 1.dp
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          // Module Header Row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Circular indicator ring
            Box(
              modifier = Modifier
                .size(28.dp)
                .border(1.5.dp, module.domain.primaryColor, CircleShape)
                .background(module.domain.containerColor, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .clip(CircleShape)
                  .background(module.domain.primaryColor)
              )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = module.domain.containerColor
                ) {
                  Text(
                    text = module.domain.displayName.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = module.domain.primaryColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  )
                }
                if (module.isPremium) {
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = StreakAmberContainer
                  ) {
                    Text(
                      text = "PRO",
                      style = MaterialTheme.typography.labelSmall,
                      color = StreakAmberOn,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = module.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SynapseTextPrimary
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = module.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = SynapseTextMuted
              )
            }

            IconButton(onClick = { isExpanded = !isExpanded }) {
              Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = SynapseTextMuted
              )
            }
          }

          // Expandable Lessons
          AnimatedVisibility(visible = isExpanded) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
            ) {
              HorizontalDivider(color = SynapseCardBorderSubtle)
              Spacer(modifier = Modifier.height(10.dp))

              module.lessons.forEachIndexed { idx, lesson ->
                Surface(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .clickable {
                      if (module.isPremium) {
                        onShowSubscription()
                      } else {
                        onSelectLesson(lesson, module.domain)
                      }
                    },
                  shape = RoundedCornerShape(18.dp),
                  color = SynapseCanvasBg,
                  border = BorderStroke(1.dp, SynapseCardBorderSubtle)
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Box(
                      modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, SynapseCardBorder, CircleShape),
                      contentAlignment = Alignment.Center
                    ) {
                      Text(
                        text = "${idx + 1}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SynapseIris
                      )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                      Text(
                        text = lesson.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = SynapseTextPrimary
                      )
                      Text(
                        text = "${lesson.durationMin} min • Active Recall Quiz included",
                        style = MaterialTheme.typography.labelSmall,
                        color = SynapseTextMuted
                      )
                    }

                    Icon(
                      imageVector = if (module.isPremium) Icons.Default.Lock else Icons.Default.ChevronRight,
                      contentDescription = null,
                      tint = if (module.isPremium) StreakAmber else SynapseTextMuted,
                      modifier = Modifier.size(18.dp)
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
