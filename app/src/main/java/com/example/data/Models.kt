package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class CognitiveDomain(
  val displayName: String,
  val brainRegion: String,
  val primaryColor: Color,
  val containerColor: Color,
  val description: String
) {
  MEMORY(
    displayName = "Memory",
    brainRegion = "Hippocampus & Medial Temporal",
    primaryColor = DomainMemoryBlue,
    containerColor = DomainMemoryContainer,
    description = "Working memory capacity, episodic recall, and long-term consolidation."
  ),
  ATTENTION(
    displayName = "Attention",
    brainRegion = "Dorsolateral Prefrontal & Parietal",
    primaryColor = DomainAttentionTeal,
    containerColor = DomainAttentionContainer,
    description = "Selective focus, distractibility filtering, and sustained mental stamina."
  ),
  SPEED(
    displayName = "Processing Speed",
    brainRegion = "White Matter Tracts & Myelin",
    primaryColor = DomainSpeedOrange,
    containerColor = DomainSpeedContainer,
    description = "Rapid neural signal transmission, motor reaction, and visual scan rate."
  ),
  FLEXIBILITY(
    displayName = "Flexibility",
    brainRegion = "Frontostriatal & Anterior Cingulate",
    primaryColor = DomainFlexibilityPurple,
    containerColor = DomainFlexibilityContainer,
    description = "Cognitive shifting, task switching, and dynamic problem recalibration."
  ),
  EMOTION(
    displayName = "Regulation",
    brainRegion = "Ventromedial PFC & Amygdala",
    primaryColor = DomainEmotionRose,
    containerColor = DomainEmotionContainer,
    description = "Stress habituation, autonomic balance, and prefrontal emotional gating."
  )
}

data class QuizQuestion(
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val neuroExplanation: String
)

data class Lesson(
  val id: String,
  val title: String,
  val durationMin: Int,
  val takeaway: String,
  val body: String,
  val quiz: QuizQuestion,
  val isCompleted: Boolean = false
)

data class CourseModule(
  val id: String,
  val title: String,
  val subtitle: String,
  val domain: CognitiveDomain,
  val estimatedMinutes: Int,
  val isPremium: Boolean,
  val lessons: List<Lesson>
)

enum class GameType {
  STROOP,
  N_BACK,
  REACTION,
  TASK_SWITCH
}

data class CognitiveGame(
  val id: String,
  val title: String,
  val domain: CognitiveDomain,
  val gameType: GameType,
  val labInspiration: String,
  val durationEstimate: String,
  val description: String,
  val isLocked: Boolean = false,
  val bestScore: Int = 0
)

data class CognitiveTest(
  val id: String,
  val title: String,
  val domain: CognitiveDomain,
  val durationMin: Int,
  val benchmarkPercentile: Int, // e.g. 84th percentile
  val scientificProtocol: String,
  val summaryResult: String
)

enum class DeviceType {
  EEG_HEADSET,
  NEUROFEEDBACK,
  WEARABLE_BAND
}

data class NeurotechDevice(
  val id: String,
  val name: String,
  val manufacturer: String,
  val deviceType: DeviceType,
  val isConnected: Boolean,
  val batteryLevel: Int,
  val primaryMetric: String
)

data class UserProfile(
  val name: String = "Dr. Sarah Lin",
  val dailyStreakDays: Int = 6,
  val longestStreakDays: Int = 14,
  val synapseXp: Int = 4250,
  val levelTitle: String = "Level 4 • Neural Architect",
  val overallIndex: Int = 124, // baseline 100
  val isPremiumSubscriber: Boolean = false,
  val dailyTargetMin: Int = 10,
  val primaryGoal: String = "Deep Focus & Memory Retention"
)
