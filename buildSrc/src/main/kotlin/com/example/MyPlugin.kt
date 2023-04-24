package com.example

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.Action

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task(mapOf(
            Task.TASK_TYPE to RobGenerator::class.java
        ), "myTask")
    }
}
