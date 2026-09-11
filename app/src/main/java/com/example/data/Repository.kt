package com.example.data

object SynapseRepository {

  val courseModules: List<CourseModule> = listOf(
    CourseModule(
      id = "mod_anatomy",
      title = "Brain Anatomy & Networks",
      subtitle = "From Single Synapses to Connectomes",
      domain = CognitiveDomain.ATTENTION,
      estimatedMinutes = 18,
      isPremium = false,
      lessons = listOf(
        Lesson(
          id = "les_cortex",
          title = "The Architecture of the Cerebral Cortex",
          durationMin = 4,
          takeaway = "Six horizontal layers of neocortex govern high-order sensory integration and abstract cognition.",
          body = "Your neocortex contains roughly 16 billion neurons organized into 6 distinct micro-laminar layers. Layer IV receives sensory inputs from the thalamus, while Layer V houses large pyramidal cells sending motor commands. Understanding this column-based modularity reveals why focused attention requires gating thalamic filtering.",
          quiz = QuizQuestion(
            question = "Which cortical layer contains large pyramidal neurons that transmit outputs down to the motor systems and striatum?",
            options = listOf("Layer I (Molecular)", "Layer IV (Internal Granular)", "Layer V (Internal Pyramidal)", "Layer VI (Multiform)"),
            correctIndex = 2,
            neuroExplanation = "Layer V contains massive pyramidal tract projection neurons responsible for subcortical motor command signaling."
          )
        ),
        Lesson(
          id = "les_synapse",
          title = "Synaptic Transmission & Plasticity",
          durationMin = 5,
          takeaway = "Synapses strengthen through Long-Term Potentiation (LTP) governed by NMDA receptor activation.",
          body = "When presynaptic glutamate triggers sufficient depolarization, magnesium ions unblock postsynaptic NMDA receptors. Calcium ion influx triggers retro-signals that insert more AMPA receptors into the dendritic spine, physically reinforcing that neural connection — the molecular basis of learning.",
          quiz = QuizQuestion(
            question = "What ion block must be expelled via membrane depolarization for NMDA receptors to conduct calcium?",
            options = listOf("Sodium (Na+)", "Potassium (K+)", "Magnesium (Mg2+)", "Chloride (Cl-)"),
            correctIndex = 2,
            neuroExplanation = "The voltage-dependent magnesium (Mg2+) plug blocks the pore at resting potentials and is repelled during strong depolarization."
          )
        )
      )
    ),
    CourseModule(
      id = "mod_attention",
      title = "Attention & Deep Focus",
      subtitle = "Dopamine Circuits & Executive Control",
      domain = CognitiveDomain.ATTENTION,
      estimatedMinutes = 22,
      isPremium = false,
      lessons = listOf(
        Lesson(
          id = "les_dorsal_ventral",
          title = "Top-Down vs. Bottom-Up Attention",
          durationMin = 5,
          takeaway = "The Dorsal Attention Network drives deliberate goal focus; the Ventral Network flags surprise alerts.",
          body = "Goal-directed concentration relies on the Dorsal Attention Network (DAN), connecting the frontal eye fields with the intraparietal sulcus. Interruptions, notifications, and flashes activate the bottom-up Ventral Attention Network (VAN), causing cognitive switching costs of up to 15 minutes.",
          quiz = QuizQuestion(
            question = "Which network maintains deliberate, top-down sustained concentration on an intentional task?",
            options = listOf("Default Mode Network (DMN)", "Dorsal Attention Network (DAN)", "Ventral Attention Network (VAN)", "Salience Network (SN)"),
            correctIndex = 1,
            neuroExplanation = "The Dorsal Attention Network (DAN) mediates voluntary, top-down endogenous allocation of cognitive focus."
          )
        )
      )
    ),
    CourseModule(
      id = "mod_memory",
      title = "Memory & Consolidation",
      subtitle = "Hippocampal Indexing & Spaced Recall",
      domain = CognitiveDomain.MEMORY,
      estimatedMinutes = 25,
      isPremium = false,
      lessons = listOf(
        Lesson(
          id = "les_hippocampus",
          title = "The Hippocampal Replay Mechanism",
          durationMin = 5,
          takeaway = "Hippocampal sharp-wave ripples transfer fresh daytime memories into cortical long-term storage.",
          body = "The hippocampus acts as a fast-learning episodic index. During quiet rest and slow-wave sleep, high-frequency sharp-wave ripples (150-250 Hz) replay daytime neuronal firing sequences at 10-20x speed, training neocortical networks for permanent consolidation.",
          quiz = QuizQuestion(
            question = "During which physiological state do hippocampal sharp-wave ripples predominantly replay daytime traces?",
            options = listOf("High-stress fight-or-flight", "Slow-Wave Non-REM Sleep & Quiet Wakefulness", "Intense aerobic sprinting", "REM dreaming only"),
            correctIndex = 1,
            neuroExplanation = "Sharp-wave ripples occur primarily during non-REM slow-wave sleep and quiescent wakeful rest periods."
          )
        )
      )
    ),
    CourseModule(
      id = "mod_sleep",
      title = "Sleep & Circadian Rhythmics",
      subtitle = "Glymphatic Cleansing & REM Reorganization",
      domain = CognitiveDomain.EMOTION,
      estimatedMinutes = 20,
      isPremium = true,
      lessons = listOf(
        Lesson(
          id = "les_glymphatic",
          title = "The Glymphatic Waste Clearance System",
          durationMin = 4,
          takeaway = "During deep sleep, interstitial space expands by 60% as CSF flushes toxic metabolic byproducts.",
          body = "Discovered by Maiken Nedergaard, the glymphatic system pumps cerebrospinal fluid through astrocytic aquaporin-4 channels along perivascular spaces during delta sleep, removing beta-amyloid, tau, and daytime metabolic waste.",
          quiz = QuizQuestion(
            question = "Which cellular channels facilitate the influx of cerebrospinal fluid along perivascular spaces?",
            options = listOf("Voltage-gated potassium channels", "Aquaporin-4 (AQP4) water channels", "Sodium-potassium pumps", "GABA-A ionophores"),
            correctIndex = 1,
            neuroExplanation = "Astrocytic endfeet express dense Aquaporin-4 (AQP4) water channels that facilitate bulk interstitial fluid exchange."
          )
        )
      )
    ),
    CourseModule(
      id = "mod_flexibility",
      title = "Decision-Making & Emotions",
      subtitle = "Ventromedial Prefrontal Cortex & Somatic Markers",
      domain = CognitiveDomain.FLEXIBILITY,
      estimatedMinutes = 24,
      isPremium = true,
      lessons = listOf(
        Lesson(
          id = "les_somatic",
          title = "The Somatic Marker Hypothesis",
          durationMin = 5,
          takeaway = "Emotions are subconscious physiological bodily signals that accelerate rational decision trees.",
          body = "Antonio Damasio demonstrated that without gut-level affective signals processed through the ventromedial prefrontal cortex and insula, human beings suffer from decision paralysis over trivial choices despite normal IQ.",
          quiz = QuizQuestion(
            question = "Which brain structure integrates autonomic bodily sensations (gut feelings) into conscious emotional awareness?",
            options = listOf("Insular Cortex", "Occipital Pole", "Primary Motor Cortex", "Cerebellar Vermis"),
            correctIndex = 0,
            neuroExplanation = "The insula maps visceral, somatic states and translates interoceptive bodily signals into conscious feeling states."
          )
        )
      )
    )
  )

  val cognitiveGames: List<CognitiveGame> = listOf(
    CognitiveGame(
      id = "game_stroop",
      title = "Stroop Color Clash",
      domain = CognitiveDomain.ATTENTION,
      gameType = GameType.STROOP,
      labInspiration = "J. Ridley Stroop (1935) Selective Attention Protocol",
      durationEstimate = "2 min",
      description = "Inhibit automatic reading impulses and identify conflicting font ink color under strict time pressure.",
      isLocked = false,
      bestScore = 92
    ),
    CognitiveGame(
      id = "game_reaction",
      title = "Synaptic Reflex (PVT)",
      domain = CognitiveDomain.SPEED,
      gameType = GameType.REACTION,
      labInspiration = "Psychomotor Vigilance Task (PVT - Dinges & Powell)",
      durationEstimate = "1.5 min",
      description = "Detect millisecond visual neural signals and test sustained alertness without false anticipations.",
      isLocked = false,
      bestScore = 214 // ms
    ),
    CognitiveGame(
      id = "game_nback",
      title = "Spatial Dual N-Back",
      domain = CognitiveDomain.MEMORY,
      gameType = GameType.N_BACK,
      labInspiration = "Susanne Jaeggi Working Memory Dual-Paradigm",
      durationEstimate = "3 min",
      description = "Track moving positions on a 3x3 neural grid, recalling if the current position matches N steps back.",
      isLocked = false,
      bestScore = 84
    ),
    CognitiveGame(
      id = "game_switch",
      title = "Task-Switch Shuttle",
      domain = CognitiveDomain.FLEXIBILITY,
      gameType = GameType.TASK_SWITCH,
      labInspiration = "Rogers & Monsell Alternating Runs Paradigm",
      durationEstimate = "2.5 min",
      description = "Rapidly alternate between parity (Odd/Even) and magnitude (High/Low) rules to minimize switch latency.",
      isLocked = true,
      bestScore = 0
    )
  )

  val cognitiveTests: List<CognitiveTest> = listOf(
    CognitiveTest(
      id = "test_memory_span",
      title = "Forward & Backward Digit Span",
      domain = CognitiveDomain.MEMORY,
      durationMin = 4,
      benchmarkPercentile = 86,
      scientificProtocol = "WAIS-IV Working Memory Index (WMI)",
      summaryResult = "High working memory capacity (Span: 8 digits forward, 6 digits backward)."
    ),
    CognitiveTest(
      id = "test_flanker",
      title = "Eriksen Flanker Test",
      domain = CognitiveDomain.ATTENTION,
      durationMin = 3,
      benchmarkPercentile = 91,
      scientificProtocol = "Eriksen & Eriksen (1974) Inhibitory Interference",
      summaryResult = "Superb distractibility resistance. Congruency cost: +28ms (Cohort avg: +58ms)."
    ),
    CognitiveTest(
      id = "test_speed_scan",
      title = "Symbol Search Speed Index",
      domain = CognitiveDomain.SPEED,
      durationMin = 3,
      benchmarkPercentile = 78,
      scientificProtocol = "WAIS-IV Processing Speed Index (PSI)",
      summaryResult = "Rapid visual discrimination and motor execution under time bounds."
    ),
    CognitiveTest(
      id = "test_wisconsin",
      title = "Card Sorting Plasticity Assessment",
      domain = CognitiveDomain.FLEXIBILITY,
      durationMin = 5,
      benchmarkPercentile = 82,
      scientificProtocol = "Wisconsin Card Sorting Test (WCST)",
      summaryResult = "Strong rule-shifting agility with low perseverative error quotient."
    )
  )

  val neurotechDevices: List<NeurotechDevice> = listOf(
    NeurotechDevice(
      id = "dev_muse2",
      name = "Muse 2 Headband",
      manufacturer = "InteraXon",
      deviceType = DeviceType.EEG_HEADSET,
      isConnected = true,
      batteryLevel = 84,
      primaryMetric = "Alpha Rhythm: 10.4 Hz (Calm Focus)"
    ),
    NeurotechDevice(
      id = "dev_neurable",
      name = "Neurable MW75 Neuro",
      manufacturer = "Neurable Audio",
      deviceType = DeviceType.EEG_HEADSET,
      isConnected = false,
      batteryLevel = 0,
      primaryMetric = "Focus Score: Standby"
    ),
    NeurotechDevice(
      id = "dev_oura",
      name = "Oura Horizon Ring Gen 3",
      manufacturer = "Oura Health",
      deviceType = DeviceType.WEARABLE_BAND,
      isConnected = true,
      batteryLevel = 92,
      primaryMetric = "HRV: 64 ms • Readiness: 88"
    )
  )

  val mascotTips: List<String> = listOf(
    "Hi, I'm Noa! Did you know? Synapses that fire together really do wire together through LTP (Long-Term Potentiation).",
    "Struggling on a challenging task isn't failure — it releases norepinephrine and acetylcholine to signal your brain to rewire!",
    "Your brain uses 20% of your body's glucose despite weighing only 2% of your mass. Feed your focus with hydration!",
    "A 10-minute non-sleep deep rest (NSDR) session restores striatal dopamine after intense cognitive work."
  )
}
