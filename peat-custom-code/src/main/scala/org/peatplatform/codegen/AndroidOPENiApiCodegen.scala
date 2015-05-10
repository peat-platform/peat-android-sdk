package org.peatplatform.codegen

import com.wordnik.swagger.codegen.BasicAndroidJavaGenerator

object AndroidOPENiApiCodegen extends BasicAndroidJavaGenerator {
  def main(args: Array[String]) = generateClient(args)

  // location of templates
  override def templateDir = "src/main/resources/android-java"

  def destinationRoot = "generated-code/android-java"

  // where to write generated code
  override def destinationDir = destinationRoot + "/src/main/java"

  override def importMapping = super.importMapping ++ Map(
    "Set" -> "java.util.Set",
    "Map" -> "java.util.Map",
    "JSON" -> "java.util.Map")

  // package for api invoker, error files
  override def invokerPackage = Some("org.peatplatform.client.common")

  // package for models
  override def modelPackage = Some("org.peatplatform.client.model")

  // package for api classes
  override def apiPackage = Some("org.peatplatform.client.api")

  additionalParams ++= Map(
    "artifactId"      -> "peat-android-client-lib",
    "artifactVersion" -> "1.0.0",
    "groupId"         -> "org.peatplatform")

  // supporting classes
  override def supportingFiles =
    List(
      ("apiInvoker.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiInvoker.java"),
      ("jsonUtil.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "JsonUtil.java"),
      ("apiException.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiException.java"),
      ("httpPatch.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "HttpPatch.java"),
      ("pom.mustache", destinationRoot, "pom.xml"))
}
