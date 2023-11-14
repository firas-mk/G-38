plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Other dependencies

    implementation ("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
    implementation("junit:junit:4.13.2")
    implementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.8.1")

}

tasks.named<Test>("test") {
    useJUnitPlatform()
    include ("*Test.java")
    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}
sourceSets {
    test {
        java {
            srcDir ("src/test/java/Classes")
        }
    }
}