plugins{
    `java-library`
}
group = "org.visual.model.git"

version = "unspecified"

dependencies {
  implementation(libs.jgit)
  testImplementation(libs.jgitJunit)
  implementation(libs.jgitLfs)
  implementation(libs.jgitSSH)
  implementation(libs.jgitHttp)
}
