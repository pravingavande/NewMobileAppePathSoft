# Quick Start Guide

## 🚀 Fastest Way to Build & Run

### Step 1: Open in Android Studio
1. Launch **Android Studio**
2. **File → Open** → Select `E:\GitRepo\MobileAppNew`
3. Wait for Gradle sync to complete (first time: 5-10 minutes)

### Step 2: Update API URL (IMPORTANT!)
1. Open: `app/src/main/java/com/pathsoft/mobile/data/api/ApiModule.kt`
2. Change line 34:
   ```kotlin
   private const val BASE_URL = "https://your-actual-api-url.com/api/"
   ```
   - For emulator + localhost: `https://10.0.2.2:7069/api/`
   - For production: Your actual API domain

### Step 3: Build
- **Build → Make Project** (or `Ctrl+F9`)

### Step 4: Run
- Click **▶️ Run** button (or `Shift+F10`)
- Select device/emulator
- App will install and launch!

---

## 📝 Command Line (Alternative)

```powershell
# Navigate to project
cd E:\GitRepo\MobileAppNew

# Build
.\gradlew.bat assembleDebug

# Install on connected device
.\gradlew.bat installDebug
```

---

## ⚠️ Common First-Time Issues

1. **Gradle sync fails?**
   - Check internet connection
   - File → Invalidate Caches → Restart

2. **"SDK not found"?**
   - Create `local.properties` in project root:
     ```
     sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
     ```

3. **Build errors?**
   - Build → Clean Project
   - Then Build → Rebuild Project

---

## ✅ That's It!

For detailed instructions, see `BUILD_INSTRUCTIONS.md`

