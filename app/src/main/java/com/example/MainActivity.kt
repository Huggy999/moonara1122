package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        SynapseApp()
      }
    }
  }
}

enum class NavTab(val title: String) {
  HOME("Home"),
  LEARN("Learn"),
  TRAIN("Train"),
  TEST("Test"),
  PROFILE("You")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SynapseApp() {
  var currentTab by remember { mutableStateOf(NavTab.HOME) }
  var userProfile by remember { mutableStateOf(UserProfile()) }

  // Dialog & Modal states
  var activeLessonData by remember { mutableStateOf<Pair<Lesson, CognitiveDomain>?>(null) }
  var activeGame by remember { mutableStateOf<CognitiveGame?>(null) }
  var showSubscription by remember { mutableStateOf(false) }
  var showOnboarding by remember { mutableStateOf(false) }
  var showWidgetsSheet by remember { mutableStateOf(false) }

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = SynapseCanvasBg,
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      TopAppBar(
        title = {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(SynapseIrisContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                tint = SynapseIris,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "SYNAPSE",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.4.sp,
                color = SynapseTextPrimary
              )
              Text(
                text = "Brain Optimization Lab",
                style = MaterialTheme.typography.labelSmall,
                color = SynapseIris,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        },
        actions = {
          // Widgets & Design System Explorer button (inspired by user's screenshot)
          FilledTonalIconButton(
            onClick = { showWidgetsSheet = true },
            modifier = Modifier
              .padding(end = 4.dp)
              .testTag("topbar_widgets_btn"),
            colors = IconButtonDefaults.filledTonalIconButtonColors(
              containerColor = SynapseIrisContainer,
              contentColor = SynapseIris
            )
          ) {
            Icon(
              imageVector = Icons.Default.Widgets,
              contentDescription = "Widgets Design System",
              modifier = Modifier.size(20.dp)
            )
          }

          IconButton(
            onClick = { showSubscription = true },
            modifier = Modifier
              .padding(end = 6.dp)
              .testTag("topbar_pro_btn")
          ) {
            Icon(
              imageVector = Icons.Default.WorkspacePremium,
              contentDescription = "Subscription",
              tint = StreakAmber,
              modifier = Modifier.size(26.dp)
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = SynapseCanvasBg,
          titleContentColor = SynapseTextPrimary
        )
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = SynapseCardWhite,
        contentColor = SynapseTextPrimary,
        tonalElevation = 2.dp,
        modifier = Modifier.testTag("bottom_nav_bar")
      ) {
        NavigationBarItem(
          icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
          label = { Text("Home", fontWeight = FontWeight.Medium) },
          selected = currentTab == NavTab.HOME,
          onClick = { currentTab = NavTab.HOME },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SynapseIris,
            selectedTextColor = SynapseIris,
            indicatorColor = SynapseIrisContainer,
            unselectedIconColor = SynapseTextMuted,
            unselectedTextColor = SynapseTextMuted
          ),
          modifier = Modifier.testTag("nav_tab_home")
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.MenuBook, contentDescription = "Learn") },
          label = { Text("Learn", fontWeight = FontWeight.Medium) },
          selected = currentTab == NavTab.LEARN,
          onClick = { currentTab = NavTab.LEARN },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = DomainAttentionTeal,
            selectedTextColor = DomainAttentionTeal,
            indicatorColor = DomainAttentionContainer,
            unselectedIconColor = SynapseTextMuted,
            unselectedTextColor = SynapseTextMuted
          ),
          modifier = Modifier.testTag("nav_tab_learn")
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.SportsEsports, contentDescription = "Train") },
          label = { Text("Train", fontWeight = FontWeight.Medium) },
          selected = currentTab == NavTab.TRAIN,
          onClick = { currentTab = NavTab.TRAIN },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = DomainSpeedOrange,
            selectedTextColor = DomainSpeedOrange,
            indicatorColor = DomainSpeedContainer,
            unselectedIconColor = SynapseTextMuted,
            unselectedTextColor = SynapseTextMuted
          ),
          modifier = Modifier.testTag("nav_tab_train")
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.Analytics, contentDescription = "Test") },
          label = { Text("Test", fontWeight = FontWeight.Medium) },
          selected = currentTab == NavTab.TEST,
          onClick = { currentTab = NavTab.TEST },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = DomainFlexibilityPurple,
            selectedTextColor = DomainFlexibilityPurple,
            indicatorColor = DomainFlexibilityContainer,
            unselectedIconColor = SynapseTextMuted,
            unselectedTextColor = SynapseTextMuted
          ),
          modifier = Modifier.testTag("nav_tab_test")
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.Person, contentDescription = "You") },
          label = { Text("You", fontWeight = FontWeight.Medium) },
          selected = currentTab == NavTab.PROFILE,
          onClick = { currentTab = NavTab.PROFILE },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SynapseIris,
            selectedTextColor = SynapseIris,
            indicatorColor = SynapseIrisContainer,
            unselectedIconColor = SynapseTextMuted,
            unselectedTextColor = SynapseTextMuted
          ),
          modifier = Modifier.testTag("nav_tab_profile")
        )
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(SynapseCanvasBg)
    ) {
      when (currentTab) {
        NavTab.HOME -> HomeScreen(
          userProfile = userProfile,
          onOpenLesson = { lesson, domain -> activeLessonData = Pair(lesson, domain) },
          onPlayGame = { game -> activeGame = game },
          onOpenTest = { currentTab = NavTab.TEST },
          onOpenNeurotech = { currentTab = NavTab.PROFILE }
        )
        NavTab.LEARN -> LearnScreen(
          onSelectLesson = { lesson, domain -> activeLessonData = Pair(lesson, domain) },
          onShowSubscription = { showSubscription = true }
        )
        NavTab.TRAIN -> TrainScreen(
          onPlayGame = { game -> activeGame = game },
          onShowSubscription = { showSubscription = true }
        )
        NavTab.TEST -> TestScreen(
          onSelectTest = { test ->
            scope.launch {
              snackbarHostState.showSnackbar("Baseline re-calibrated for ${test.title}!")
            }
          },
          onShowSubscription = { showSubscription = true }
        )
        NavTab.PROFILE -> ProfileScreen(
          userProfile = userProfile,
          onShowSubscription = { showSubscription = true },
          onRestartOnboarding = { showOnboarding = true }
        )
      }
    }
  }

  // Active Lesson Reader Dialog
  activeLessonData?.let { (lesson, domain) ->
    LessonDetailDialog(
      lesson = lesson,
      domain = domain,
      onDismiss = { activeLessonData = null },
      onCompleted = {
        userProfile = userProfile.copy(
          synapseXp = userProfile.synapseXp + 100
        )
        scope.launch {
          snackbarHostState.showSnackbar("Lesson Completed! +100 Synapse XP")
        }
      }
    )
  }

  // Active Playable Games
  activeGame?.let { game ->
    when (game.gameType) {
      GameType.STROOP -> {
        StroopGameDialog(
          onDismiss = { activeGame = null },
          onComplete = { score, avgRt ->
            userProfile = userProfile.copy(
              synapseXp = userProfile.synapseXp + (score * 2)
            )
            scope.launch {
              snackbarHostState.showSnackbar("Stroop Complete! Accuracy: $score%, Latency: ${avgRt}ms (+${score * 2} XP)")
            }
          }
        )
      }
      GameType.REACTION -> {
        ReactionSpeedGameDialog(
          onDismiss = { activeGame = null },
          onComplete = { bestMs ->
            userProfile = userProfile.copy(
              synapseXp = userProfile.synapseXp + 150
            )
            scope.launch {
              snackbarHostState.showSnackbar("PVT Complete! Best Reaction: ${bestMs}ms (+150 XP)")
            }
          }
        )
      }
      else -> {
        StroopGameDialog(
          onDismiss = { activeGame = null },
          onComplete = { score, avgRt ->
            userProfile = userProfile.copy(
              synapseXp = userProfile.synapseXp + 100
            )
          }
        )
      }
    }
  }

  // Subscription Sheet
  if (showSubscription) {
    SubscriptionSheet(
      onDismiss = { showSubscription = false },
      onSubscribe = {
        showSubscription = false
        userProfile = userProfile.copy(isPremiumSubscriber = true)
        scope.launch {
          snackbarHostState.showSnackbar("Welcome to Synapse Pro! All 25+ modules unlocked.")
        }
      }
    )
  }

  // Onboarding Intake Dialog
  if (showOnboarding) {
    OnboardingDialog(
      onDismiss = { showOnboarding = false },
      onFinish = { goal, dailyMin ->
        showOnboarding = false
        userProfile = userProfile.copy(
          primaryGoal = goal,
          dailyTargetMin = dailyMin
        )
        scope.launch {
          snackbarHostState.showSnackbar("Neural Roadmap updated: $dailyMin min/day for $goal")
        }
      }
    )
  }

  // Widgets & Design System Explorer Sheet
  if (showWidgetsSheet) {
    WidgetsSheet(
      onDismiss = { showWidgetsSheet = false },
      onOpenOnboarding = {
        showWidgetsSheet = false
        showOnboarding = true
      },
      onOpenMascotTip = {
        showWidgetsSheet = false
        scope.launch {
          snackbarHostState.showSnackbar("Noa says: Synaptic plasticity is strongest with 10 min daily active recall!")
        }
      }
    )
  }
}
