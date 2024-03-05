plugins {
//  `kotlin-project`
}

group = "visual-model-serialization"

version = "unspecified"

dependencies {
  implementation(libs.jacksonCore)
  implementation(libs.jacksonDatabind)
  implementation(libs.jacksonAnnotations)
  implementation(projects.serialize.visualSerializeApi)
  implementation(libs.autoService)
  annotationProcessor(libs.autoService)
}