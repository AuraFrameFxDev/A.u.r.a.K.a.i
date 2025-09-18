package dev.aurakai.auraframefx.model

import kotlinx.serialization.Serializable

@Serializable
data class AgentMessage(
    val content: String,
    val sender: AgentType,
    val timestamp: Long,
    val confidence: Float
)

@Serializable
data class AgentHierarchy(
    val masterAgents: List<HierarchyAgentConfig>,
    val auxiliaryAgents: MutableList<HierarchyAgentConfig> = mutableListOf()
) {
    companion object {
        /**
         * List of master agents with their default configurations.
         * These are the core agents that are always available in the system.
         */
        @JvmStatic
        val MASTER_AGENTS: List<HierarchyAgentConfig> = listOf(
            HierarchyAgentConfig("GENESIS", setOf("coordination", "synthesis"), 1),
            HierarchyAgentConfig("AURA", setOf("creativity", "design"), 2),
            HierarchyAgentConfig("KAI", setOf("security", "analysis"), 2),
            HierarchyAgentConfig("CASCADE", setOf("vision", "processing"), 3)
        )

        private val auxiliaryAgents = mutableListOf<HierarchyAgentConfig>()

        /**
         * Register a new auxiliary agent configuration with a fixed priority of 4.
         *
         * Appends the created HierarchyAgentConfig to the companion object's internal auxiliaryAgents list.
         *
         * @param name Human-readable identifier for the auxiliary agent.
         * @param capabilities Set of capability names describing the agent's abilities.
         * @return The created HierarchyAgentConfig (priority == 4).
         */
        fun registerAuxiliaryAgent(name: String, capabilities: Set<String>): HierarchyAgentConfig {
            val config = HierarchyAgentConfig(name, capabilities, 4,)
            auxiliaryAgents.add(config)
            return config
        }

        fun getAgentConfig(name: String): HierarchyAgentConfig? {
            return (MASTER_AGENTS + auxiliaryAgents).find { it.name == name }
        }

        fun getAgentsByPriority(): List<HierarchyAgentConfig> {
            return (MASTER_AGENTS + auxiliaryAgents).sortedBy { it.priority }
        }
    }
}

@Serializable
data class HierarchyAgentConfig(
    val name: String,
    val capabilities: Set<String>,
    val priority: Int
)

@Serializable
enum class ConversationMode {
    TURN_ORDER,
    FREE_FORM
}

interface ContextAwareAgent {
    fun setContext(context: Map<String, Any>)
    fun getContext(): Map<String, Any>
}