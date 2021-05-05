import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
}

group = "com.github.shionit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val ktlint by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:0.41.0")
    implementation("org.apache.poi:poi-ooxml:5.0.0")
    implementation("com.github.spullara.mustache.java:compiler:0.9.7")
    implementation("com.google.guava:guava:30.1.1-jre")
    // for check generated code
    compileOnly("org.projectlombok:lombok:1.18.20")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa:2.3.0.RELEASE")
    compileOnly("org.springframework.boot:spring-boot-starter-validation:2.3.0.RELEASE")
    // for test
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClassName = "MainKt"
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}
