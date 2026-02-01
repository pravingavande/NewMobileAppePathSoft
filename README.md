# PathSoft Mobile Application

A Kotlin Android mobile application that integrates with the PathSoft Web Angular Frontend and Backend API. The mobile app replicates the web application's functionality while providing an optimized mobile user experience.

## 🏗️ Project Structure

```
app/
├── data/
│   ├── api/
│   │   ├── ApiService.kt          # Retrofit API interface
│   │   ├── ApiModule.kt           # API dependency injection
│   │   └── models/                 # API request/response models
│   ├── local/
│   │   └── AuthTokenManager.kt    # Token and user data storage
│   └── repository/                # Data repositories
├── di/                             # Dependency injection modules
├── presentation/
│   └── viewmodel/                  # ViewModels for screens
├── ui/
│   ├── theme/                      # Design system (colors, typography, etc.)
│   ├── navigation/                 # Navigation setup
│   └── screens/                    # UI screens
│       ├── login/
│       ├── dashboard/
│       ├── patient/
│       └── visitworkspace/
└── MainActivity.kt
```

## 🎨 Design System

The app uses a comprehensive design system matching the web application:

- **Colors**: Primary blue/purple gradients, success/error/warning colors
- **Typography**: Consistent font sizes and weights
- **Spacing**: Standardized spacing values
- **Components**: Reusable UI components

See `app/src/main/java/com/pathsoft/mobile/ui/theme/` for full design system implementation.

## 🔧 Setup Instructions

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK (API 24 minimum, API 34 target)

### Configuration

1. **Update Base URL**: 
   - Open `app/src/main/java/com/pathsoft/mobile/data/api/ApiModule.kt`
   - Update `BASE_URL` constant with your production API URL

2. **Build the Project**:
   ```bash
   ./gradlew build
   ```

3. **Run the App**:
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use `./gradlew installDebug`

## 📱 Features

### ✅ Implemented

- **Authentication**: Login screen with token-based authentication
- **Dashboard**: KPI cards and quick navigation
- **Patient List**: Searchable patient list with pagination
- **Patient Registration**: Form for registering new patients
- **Visit Workspace**: Tabbed interface for patient info, tests, and billing

### 🚧 In Progress / TODO

- Add Test modal with test/package selection
- Discount modal with category support
- Fill Results screen for test parameter entry
- Test Results approval workflow
- PDF report generation and viewing
- Offline support (optional)
- Unit and UI tests

## 🔌 API Integration

The app uses Retrofit for API calls with:
- Bearer token authentication (automatic via interceptor)
- Gson for JSON serialization
- OkHttp logging for debugging

All API endpoints are defined in `ApiService.kt` and match the backend API specifications.

## 🏛️ Architecture

- **MVVM Pattern**: ViewModels handle business logic, UI is reactive
- **Jetpack Compose**: Modern declarative UI framework
- **Hilt**: Dependency injection for clean architecture
- **Coroutines**: Asynchronous operations
- **Repository Pattern**: Data layer abstraction

## 📦 Dependencies

Key dependencies:
- Jetpack Compose (UI)
- Hilt (Dependency Injection)
- Retrofit (API calls)
- Coroutines (Async operations)
- Material Design 3 (UI components)

See `app/build.gradle.kts` for complete dependency list.

## 🐛 Troubleshooting

### Build Errors

1. **Gradle Sync Issues**: 
   - Clean project: `Build > Clean Project`
   - Invalidate caches: `File > Invalidate Caches`

2. **Hilt Errors**:
   - Ensure `kapt` plugin is applied
   - Check that all `@HiltViewModel` classes are in correct packages

3. **API Connection Issues**:
   - Verify `BASE_URL` is correct
   - Check network permissions in `AndroidManifest.xml`
   - For localhost testing, use `10.0.2.2` instead of `localhost` on emulator

### Runtime Issues

1. **Login Fails**:
   - Check API base URL
   - Verify credentials
   - Check network connectivity

2. **Token Expired**:
   - App should handle token refresh (to be implemented)
   - Currently requires re-login

## 📝 Development Notes

- All API calls require Bearer token authentication
- Lab code is stored in SharedPreferences after login
- Date formatting uses ISO 8601 format
- Sample Registration DateTime preserves local timezone

## 🔐 Security

- Tokens stored in SharedPreferences (consider migrating to EncryptedSharedPreferences for production)
- HTTPS required for API calls (configured in `ApiModule`)
- Network security config may be needed for self-signed certificates in development

## 📄 License

[Add your license information here]

## 👥 Contributors

[Add contributor information here]

---

**Version**: 1.0  
**Last Updated**: 2026-01-29

