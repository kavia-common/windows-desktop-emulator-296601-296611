#!/bin/bash
cd /home/kavia/workspace/code-generation/windows-desktop-emulator-296601-296611/kavia_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

