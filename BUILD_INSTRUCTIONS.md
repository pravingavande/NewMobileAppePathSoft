# Build Instructions for PathSoft Mobile App

## 📋 Prerequisites

Before building, ensure you have:

1. **Android Studio** (Hedgehog 2023.1.1 or later)
   - Download from: https://developer.android.com/studio
   
2. **JDK 17 or later**
   - Android Studio usually includes JDK, but verify:
     - File → Project Structure → SDK Location → JDK Location
   
3. **Android SDK**
   - Minimum SDK: API 24 (Android 7.0)
   - Target SDK: API 34 (Android 14)
   - Install via Android Studio: Tools → SDK Manager

4. **Internet Connection** (for downloading dependencies)

---

## 🚀 Building the Project

### Method 1: Using Android Studio (Recommended)

1. **Open the Project**
   ```
   File → Open → Select the project folder (E:\GitRepo\MobileAppNew)
   ```

2. **Sync Gradle**
   - Android Studio will automatically sync when you open the project
   - If not, click: `File → Sync Project with Gradle Files`
   - Wait for dependencies to download (first time may take 5-10 minutes)

3. **Build the Project**
   - Click: `Build → Make Project` (or press `Ctrl+F9` / `Cmd+F9`)
   - Or: `Build → Rebuild Project` (clean build)

4. **Check for Errors**
   - View errors in: `Build` tab at the bottom
   - Fix any compilation errors before proceeding

### Method 2: Using Command Line (Terminal/PowerShell)

#### On Windows (PowerShell):
```powershell
# Navigate to project directory
cd E:\GitRepo\MobileAppNew

# Make gradlew executable (if needed)
# Windows usually handles this automatically

# Build the project
.\gradlew.bat build

# Or build debug APK
.\gradlew.bat assembleDebug

# Or build release APK
.\gradlew.bat assembleRelease
```

#### On Linux/Mac:
```bash
# Navigate to project directory
cd /path/to/MobileAppNew

# Make gradlew executable
chmod +x gradlew

# Build the project
./gradlew build

# Or build debug APK
./gradlew assembleDebug

# Or build release APK
./gradlew assembleRelease
```

**Note**: If `gradlew` doesn't exist, Android Studio will generate it on first sync.

---

## 📱 Running the App

### Option 1: Run on Emulator

1. **Create/Start an Emulator**
   - Tools → Device Manager
   - Click "Create Device" or start an existing one
   - Recommended: Pixel 5 or newer, API 30+

2. **Run the App**
   - Click the green "Run" button (▶️) in toolbar
   - Or: `Run → Run 'app'` (or press `Shift+F10` / `Ctrl+R`)
   - Select your emulator/device when prompted

### Option 2: Run on Physical Device

1. **Enable Developer Options** on your Android device:
   - Settings → About Phone → Tap "Build Number" 7 times

2. **Enable USB Debugging**:
   - Settings → Developer Options → USB Debugging (ON)

3. **Connect Device**:
   - Connect via USB cable
   - Accept "Allow USB Debugging" prompt on device

4. **Run the App**:
   - Click "Run" button in Android Studio
   - Select your device from the list

### Option 3: Install APK Directly

After building, find the APK:
- **Debug APK**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK**: `app/build/outputs/apk/release/app-release.apk`

Transfer to device and install.

---

## ⚙️ Configuration Before First Run

### 1. Update API Base URL

**Important**: Update the API base URL before running!

1. Open: `app/src/main/java/com/pathsoft/mobile/data/api/ApiModule.kt`
2. Find line 34: `private const val BASE_URL = "https://localhost:7069/api/"`
3. Replace with your actual API URL:
   ```kotlin
   private const val BASE_URL = "https://your-api-domain.com/api/"
   ```

**For Emulator Testing**:
- If testing against localhost API, use: `https://10.0.2.2:7069/api/`
- `10.0.2.2` is the emulator's alias for your host machine's localhost

### 2. Network Security Config (if using self-signed certificates)

If your API uses self-signed certificates, create:
`app/src/main/res/xml/network_security_config.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="system" />
            <certificates src="user" />
        </trust-anchors>
    </base-config>
</network-security-config>
```

Then add to `AndroidManifest.xml` in `<application>` tag:
```xml
android:networkSecurityConfig="@xml/network_security_config"
```

---

## 🔧 Troubleshooting

### Issue: Gradle Sync Failed

**Solutions**:
1. Check internet connection
2. File → Invalidate Caches → Invalidate and Restart
3. Delete `.gradle` folder in project root, then sync again
4. Check `gradle.properties` for proxy settings if behind corporate firewall

### Issue: "SDK location not found"

**Solution**:
1. Create `local.properties` in project root:
   ```properties
   sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
   ```
   (Adjust path to your Android SDK location)

### Issue: "Kotlin version mismatch"

**Solution**:
- Ensure Kotlin version matches in:
  - `build.gradle.kts` (root): `1.9.20`
  - `app/build.gradle.kts`: Should use same version

### Issue: Hilt/Kapt errors

**Solutions**:
1. Ensure `kapt` plugin is applied in `app/build.gradle.kts`
2. Build → Clean Project
3. File → Invalidate Caches → Invalidate and Restart
4. Delete `app/build` folder, then rebuild

### Issue: "Cannot resolve symbol" errors

**Solutions**:
1. File → Sync Project with Gradle Files
2. Build → Clean Project → Rebuild
3. Check that all dependencies are downloaded (check Gradle tab)

### Issue: App crashes on startup

**Check**:
1. Check Logcat for error messages
2. Verify API base URL is correct
3. Check network permissions in AndroidManifest.xml
4. Verify Hilt is properly set up (check `@HiltAndroidApp` annotation)

### Issue: Build takes too long

**Solutions**:
1. Enable Gradle daemon (usually enabled by default)
2. Increase Gradle memory in `gradle.properties`:
   ```properties
   org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
   ```
3. Use Gradle build cache

---

## 📦 Build Variants

The project has two build types:

1. **Debug** (default)
   - Includes debugging symbols
   - Not optimized
   - APK location: `app/build/outputs/apk/debug/`

2. **Release**
   - Optimized
   - ProGuard disabled (for now)
   - APK location: `app/build/outputs/apk/release/`

To switch: `Build → Select Build Variant → Choose variant`

---

## ✅ Verification Checklist

Before running, verify:

- [ ] Android Studio is up to date
- [ ] JDK 17+ is configured
- [ ] Android SDK API 24-34 is installed
- [ ] Gradle sync completed without errors
- [ ] API base URL is updated
- [ ] No compilation errors in Build tab
- [ ] Device/Emulator is connected and ready

---

## 🎯 Quick Start Commands

```powershell
# Windows PowerShell - Quick Build & Install
cd E:\GitRepo\MobileAppNew
.\gradlew.bat clean build
.\gradlew.bat installDebug

# Or just run from Android Studio (easiest!)
```

---

## 📞 Need Help?

If you encounter issues:
1. Check the error message in Build tab
2. Check Logcat for runtime errors
3. Verify all prerequisites are met
4. Try Clean → Rebuild Project

---

**Happy Building! 🚀**

