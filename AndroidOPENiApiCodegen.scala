package eu.openict.codegen

import com.wordnik.swagger.codegen.BasicAndroidJavaGenerator

object AndroidOPENiApiCodegen extends BasicAndroidJavaGenerator {
  def main(args: Array[String]) = generateClient(args)

  // location of templates
  override def templateDir = "src/main/resources/android-java"

  def destinationRoot = "generated-code/android-java"

  // where to write generated code
  override def destinationDir = destinationRoot + "/src/main/java"

  // package for api invoker, error files
  override def invokerPackage = Some("eu.openiict.client.common")

  // package for models
  override def modelPackage = Some("eu.openiict.client.model")

  // package for api classes
  override def apiPackage = Some("eu.openiict.client.api")

  additionalParams ++= Map(
    "artifactId"      -> "openi-android-client", 
    "artifactVersion" -> "1.0.0",
    "groupId"         -> "eu.openiict")

  // supporting classes
  override def supportingFiles =
    List(
      ("apiInvoker.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiInvoker.java"),
      ("jsonUtil.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "JsonUtil.java"),
      ("apiException.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiException.java"),
      ("pom.mustache", destinationRoot, "pom.xml"))
}
