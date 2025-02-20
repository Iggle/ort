/*
 * Copyright (C) 2017 The ORT Project Authors (see <https://github.com/oss-review-toolkit/ort/blob/main/NOTICE>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

import de.undercouch.gradle.tasks.download.Download

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // See https://youtrack.jetbrains.com/issue/KTIJ-19369.
plugins {
    // Apply core plugins.
    `java-library`

    // Apply third-party plugins.
    alias(libs.plugins.download)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {
    api(project(":model"))
    api(project(":utils:scripting-utils"))

    implementation(project(":downloader"))
    implementation(project(":utils:ort-utils"))
    implementation(project(":utils:spdx-utils"))

    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")
    implementation(libs.kotlinxSerialization)

    testImplementation(libs.mockk)
}

tasks.withType<KotlinCompile>().configureEach {
    val customCompilerArgs = listOf(
        "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    )

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + customCompilerArgs
    }
}

tasks.register("updateOsadlMatrix", Download::class).configure {
    src("https://www.osadl.org/fileadmin/checklists/matrixseqexpl.json")
    dest("src/main/resources/rules/matrixseqexpl.json")
}
