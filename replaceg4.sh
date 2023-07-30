#!/usr/bin/zsh

package='package io.github.seujorgenochurras.minecraftjsh.antlr.generated;'

cd minecraft-jsh || exit
./gradlew generateGrammarSource

files=(build/generated-src/antlr/main/*)

rm -f src/main/java/io/github/seujorgenochurras/minecraftjsh/antlr/generated/*

for file in "${files[@]}"; do
  sed -i "1i $package" "$file"
done

mv build/generated-src/antlr/main/* src/main/java/io/github/seujorgenochurras/minecraftjsh/antlr/generated
