#!/bin/bash

java --module-path "3rd-party-libs/javafx-sdk-11.0.1/lib" --add-modules javafx.controls --add-exports=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED --add-exports=javafx.base/com.sun.javafx.reflect=ALL-UNNAMED --add-exports=javafx.base/com.sun.javafx.beans=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.glass.utils=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED -jar Scouting-App-Server.jar true
