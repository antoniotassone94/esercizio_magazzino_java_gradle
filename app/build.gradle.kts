plugins{
    id("buildlogic.java-application-conventions")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

dependencies{
    implementation("org.json:json:20240303")
    implementation("org.openjfx:javafx:23-ea+20")
    implementation("org.openjfx:javafx-controls:23-ea+20")
    implementation("org.openjfx:javafx-graphics:23-ea+20")
    implementation("org.openjfx:javafx-base:23-ea+20")
    implementation("org.openjfx:javafx-fxml:23-ea+20")
    implementation("org.openjfx:javafx-swing:23-ea+20")
    implementation("org.openjfx:javafx-web:23-ea+20")
    implementation("org.openjfx:javafx-media:23-ea+20")
}

application{
    mainClass = "com.antoniotassone.warehouse.Main"
}

javafx{
    version = "23-ea+20"
}