FROM openjdk:8

# Copy files to new folder and create folder for SDK
COPY . /application
RUN mkdir /application/Sdk

ENV SDK_URL="https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip" \
    ANDROID_HOME="/application/Sdk/android-sdk" \
    ANDROID_VERSION=28 \
    ANDROID_BUILD_TOOLS_VERSION=28.0.3

# Download Android SDK
RUN mkdir "$ANDROID_HOME" .android \
    && cd "$ANDROID_HOME" \
    && curl -o sdk.zip $SDK_URL \
    && unzip sdk.zip \
    && rm sdk.zip \
    && yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses

# Install Android Build Tool and Libraries
RUN $ANDROID_HOME/tools/bin/sdkmanager --update
RUN $ANDROID_HOME/tools/bin/sdkmanager "build-tools;${ANDROID_BUILD_TOOLS_VERSION}" \
    "platforms;android-${ANDROID_VERSION}" \
    "platform-tools"

WORKDIR /application

# Clean and build app for release
RUN rm local.properties
RUN ./gradlew clean
RUN ./gradlew assembleDebug

# Move the apk to the shared volume
CMD cp ./app/build/outputs/apk/debug/app-debug.apk /shared/client.apk