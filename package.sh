#!/usr/bin/env bash

./gradlew -b ./core/build.gradle clean build bintrayUpload -PbintrayUser=darklycoder -PbintrayKey=9ffad93d566f38acd546f69daf2c34fa8d6b75bd -PdryRun=false