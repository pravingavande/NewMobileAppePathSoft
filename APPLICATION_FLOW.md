# PathSoft Mobile Application - Flow Documentation

## 📱 Application Flow Overview

This document describes the complete flow of the PathSoft mobile application, including user journeys, navigation, authentication, and data flow.

---

## 🔐 1. Authentication Flow

```
┌─────────────┐
│ App Launch  │
└──────┬──────┘
       │
       ▼
┌─────────────────────┐
│ Check Auth Token    │
│ (AuthTokenManager)  │
└──────┬──────────────┘
       │
   ┌───┴───┐
   │       │
   ▼       ▼
Token   No Token
Exists  Found
   │       │
   │       ▼
   │  ┌─────────────┐
   │  │ Login Screen│
   │  └──────┬──────┘
   │         │
   │         ▼
   │  ┌──────────────────┐
   │  │ Enter Credentials │
   │  │ (username/password)│
   │  └──────┬───────────┘
   │         │
   │         ▼
   │  ┌──────────────────┐
   │  │ API: POST /auth/  │
   │  │      login        │
   │  └──────┬───────────┘
   │         │
   │         ▼
   │  ┌──────────────────┐
   │  │ Save Token &      │
   │  │ User Data         │
   │  │ (SharedPreferences)│
   │  └──────┬───────────┘
   │         │
   └─────────┘
         │
         ▼
┌─────────────────┐
│  Dashboard      │
│  (Main Screen)  │
└─────────────────┘
```

**Steps:**
1. App launches → `MainActivity` → `PathSoftNavigation`
2. Check if auth token exists in `AuthTokenManager`
3. **If token exists**: Navigate directly to Dashboard
4. **If no token**: Show Login Screen
5. User enters credentials → API call to `/api/auth/login`
6. On success: Save token and user data → Navigate to Dashboard
7. On failure: Show error message, stay on Login screen

---

## 🏠 2. Main Navigation Flow

```
                    ┌──────────────┐
                    │   Dashboard  │
                    │  (Home)      │
                    └──────┬───────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│ Patient List │  │  Register    │  │  Pending     │
│              │  │  Patient     │  │  Approvals   │
└──────┬───────┘  └──────┬───────┘  └──────────────┘
       │                 │
       │                 │
       ▼                 ▼
┌──────────────┐  ┌──────────────┐
│ Visit        │  │ Visit        │
│ Workspace    │  │ Workspace    │
│ (from list)  │  │ (new visit)  │
└──────────────┘  └──────────────┘
```

**Navigation Routes:**
- `login` → Login Screen
- `dashboard` → Dashboard (Home)
- `patient_list` → Patient List Screen
- `patient_registration` → Patient Registration
- `visit_workspace/{visitId}/{labCode}` → Visit Workspace
- `fill_results/{testIds}/{visitId}/{patientId}/{labCode}` → Fill Results

---

## 👥 3. Patient Management Flow

### 3.1 Patient List Flow

```
┌──────────────┐
│  Dashboard   │
└──────┬───────┘
       │
       │ Click "Patients"
       ▼
┌──────────────┐
│ Patient List │
│   Screen     │
└──────┬───────┘
       │
       │ Load Patients
       ▼
┌──────────────────┐
│ API: GET         │
│ /patients/list   │
│ (with pagination)│
└──────┬───────────┘
       │
       │ Display List
       ▼
┌──────────────┐
│ Patient Cards│
│ (Scrollable) │
└──────┬───────┘
       │
       │ User clicks patient
       ▼
┌──────────────┐
│ Visit        │
│ Workspace    │
└──────────────┘
```

**Features:**
- Search by name or mobile number
- Pagination (load more)
- Filter by status/date
- Click patient → Navigate to Visit Workspace

### 3.2 Patient Registration Flow

```
┌──────────────┐
│  Dashboard   │
└──────┬───────┘
       │
       │ Click "Register Patient"
       ▼
┌──────────────────┐
│ Patient          │
│ Registration     │
│ Screen           │
└──────┬───────────┘
       │
       │ Fill Form
       │ - Name, Mobile, Email
       │ - Address, Gender, Age
       │ - Select Tests/Packages
       │
       ▼
┌──────────────────┐
│ API: POST        │
│ /patient-        │
│ registration/    │
│ save             │
└──────┬───────────┘
       │
       │ Success
       ▼
┌──────────────┐
│ Visit        │
│ Workspace    │
│ (New Visit)  │
└──────────────┘
```

**Steps:**
1. User fills patient information form
2. Optionally searches for existing patient
3. Selects tests/packages
4. Submits registration
5. API creates visit and patient
6. Navigate to Visit Workspace for new visit

---

## 🏥 4. Visit Workspace Flow

```
┌──────────────┐
│ Visit        │
│ Workspace    │
│ Screen       │
└──────┬───────┘
       │
       │ Load Visit Data
       ▼
┌──────────────────┐
│ API: GET         │
│ /visit-workspace/│
│ {visitId}/{lab}  │
└──────┬───────────┘
       │
       │ Display in Tabs
       ▼
┌─────────────────────────────┐
│  Tab 1: Patient Info        │
│  Tab 2: Test Details        │
│  Tab 3: Billing Summary     │
└─────────────────────────────┘
```

### 4.1 Add Test Flow

```
┌──────────────┐
│ Visit        │
│ Workspace    │
└──────┬───────┘
       │
       │ Click "Add Test" button
       ▼
┌──────────────────┐
│ Add Test Modal   │
│ (Bottom Sheet)   │
└──────┬───────────┘
       │
       │ Load Tests/Packages
       ▼
┌──────────────────┐
│ API: GET         │
│ /subdoctorm/     │
│ active           │
│ /Package/active  │
└──────┬───────────┘
       │
       │ User selects tests/packages
       ▼
┌──────────────────┐
│ Click "Add"      │
└──────┬───────────┘
       │
       ▼
┌──────────────────┐
│ API: POST        │
│ /visit-workspace/│
│ add-tests        │
└──────┬───────────┘
       │
       │ Success
       ▼
┌──────────────────┐
│ API: POST        │
│ /visit-workspace/│
│ recalculate-     │
│ billing          │
└──────┬───────────┘
       │
       │ Refresh Visit Workspace
       ▼
┌──────────────┐
│ Updated      │
│ Visit        │
│ Workspace    │
└──────────────┘
```

### 4.2 Apply Discount Flow

```
┌──────────────┐
│ Billing Tab  │
│ (Visit WS)   │
└──────┬───────┘
       │
       │ Click "Apply Discount"
       ▼
┌──────────────────┐
│ Discount Modal   │
│ (Bottom Sheet)   │
└──────┬───────────┘
       │
       │ User fills:
       │ - Category (Doctor/Lab/WriteOff)
       │ - Type (Fixed/Percentage)
       │ - Value
       │ - Remarks
       ▼
┌──────────────────┐
│ Click "Apply"    │
└──────┬───────────┘
       │
       ▼
┌──────────────────┐
│ API: POST        │
│ /visit-workspace/│
│ apply-discount   │
└──────┬───────────┘
       │
       │ Success
       ▼
┌──────────────────┐
│ Refresh Visit    │
│ Workspace        │
│ (Updated Billing)│
└──────────────────┘
```

### 4.3 Remove Test Flow

```
┌──────────────┐
│ Tests Tab    │
│ (Visit WS)   │
└──────┬───────┘
       │
       │ Click delete icon on test
       ▼
┌──────────────────┐
│ API: DELETE      │
│ /visit-workspace/│
│ remove-test/     │
│ {pvtID}          │
└──────┬───────────┘
       │
       │ Success
       ▼
┌──────────────────┐
│ Recalculate      │
│ Billing          │
└──────┬───────────┘
       │
       │ Refresh Visit Workspace
       ▼
┌──────────────┐
│ Updated      │
│ View         │
└──────────────┘
```

---

## 🧪 5. Test Results Flow

```
┌──────────────┐
│ Visit        │
│ Workspace    │
│ (Tests Tab)  │
└──────┬───────┘
       │
       │ Click "Fill Results" (or navigate from elsewhere)
       ▼
┌──────────────────┐
│ Fill Results     │
│ Screen           │
└──────┬───────────┘
       │
       │ Load Test Parameters
       ▼
┌──────────────────┐
│ API: GET         │
│ /test/           │
│ GetTestParameters│
│ (with test IDs)  │
└──────┬───────────┘
       │
       │ Display Parameters
       ▼
┌──────────────────┐
│ Parameter Inputs │
│ (Grouped by      │
│  Test/Section)   │
└──────┬───────────┘
       │
       │ User enters values
       │ (Abnormal values highlighted)
       │
       ▼
┌──────────────────┐
│ Click "Save"     │
│ (FAB or Top Bar) │
└──────┬───────────┘
       │
       ▼
┌──────────────────┐
│ API: POST        │
│ /TestResults/    │
│ save             │
└──────┬───────────┘
       │
       │ Success
       ▼
┌──────────────┐
│ Navigate     │
│ Back         │
└──────────────┘
```

**Features:**
- Parameters grouped by test sections
- Normal range displayed for each parameter
- Abnormal values highlighted in red
- Warning indicators for out-of-range values
- Save button (Floating Action Button)

---

## 📊 6. Data Flow Architecture

### 6.1 MVVM Pattern

```
┌─────────────┐
│   UI Layer  │
│  (Compose)  │
└──────┬──────┘
       │
       │ Observes State
       ▼
┌─────────────┐
│  ViewModel  │
│  (State)    │
└──────┬──────┘
       │
       │ Calls Repository
       ▼
┌─────────────┐
│ Repository │
│  (Data)     │
└──────┬──────┘
       │
       │ API Calls
       ▼
┌─────────────┐
│  API Service│
│  (Retrofit) │
└──────┬──────┘
       │
       │ HTTP Requests
       ▼
┌─────────────┐
│  Backend    │
│   API       │
└─────────────┘
```

### 6.2 State Management Flow

```
User Action
    │
    ▼
UI Component (Composable)
    │
    ▼
ViewModel Function
    │
    ▼
Update UIState (StateFlow)
    │
    ▼
Repository Call
    │
    ▼
API Service
    │
    ▼
Response/Error
    │
    ▼
Update UIState
    │
    ▼
UI Re-composes (Reactive)
```

---

## 🔄 7. Complete User Journey Example

### Scenario: Register New Patient and Fill Results

```
1. App Launch
   └─> Check Auth → Login Screen

2. Login
   └─> Enter Credentials → API Call → Save Token → Dashboard

3. Dashboard
   └─> Click "Register Patient"

4. Patient Registration
   └─> Fill Form
       ├─> Search Existing Patient (optional)
       ├─> Enter Patient Details
       ├─> Select Tests/Packages
       └─> Submit → API Call → Visit Created

5. Visit Workspace (Auto-navigate)
   ├─> Patient Tab: View Patient Info
   ├─> Tests Tab: View Selected Tests
   └─> Billing Tab: View Billing Summary

6. Add More Tests (Optional)
   └─> Click "Add Test" → Modal Opens
       ├─> Select Tests/Packages
       └─> Add → API Call → Billing Recalculated

7. Apply Discount (Optional)
   └─> Billing Tab → "Apply Discount"
       ├─> Select Category/Type
       ├─> Enter Value
       └─> Apply → API Call → Billing Updated

8. Fill Test Results
   └─> Navigate to Fill Results Screen
       ├─> Load Test Parameters
       ├─> Enter Parameter Values
       ├─> Check Abnormal Values (highlighted)
       └─> Save → API Call → Results Saved

9. Back to Visit Workspace
   └─> View Updated Information
```

---

## 🗺️ 8. Screen Transition Map

```
Login Screen
    │
    └─> Dashboard
            │
            ├─> Patient List
            │       │
            │       └─> Visit Workspace
            │
            ├─> Patient Registration
            │       │
            │       └─> Visit Workspace (new visit)
            │
            └─> Pending Approvals
                    │
                    └─> Fill Results
                            │
                            └─> Back to Visit Workspace

Visit Workspace
    │
    ├─> Add Test Modal
    │       │
    │       └─> (Close) → Back to Visit Workspace
    │
    ├─> Discount Modal
    │       │
    │       └─> (Close) → Back to Visit Workspace
    │
    └─> Fill Results Screen
            │
            └─> (Save) → Back to Visit Workspace
```

---

## 🔑 9. Key Flow Points

### Authentication
- **Token Storage**: SharedPreferences via `AuthTokenManager`
- **Auto-login**: Checks token on app launch
- **Token in API**: Automatically added via `AuthInterceptor`

### Navigation
- **Back Stack**: Managed by Navigation Component
- **Deep Linking**: Supported via route parameters
- **State Preservation**: ViewModels retain state during navigation

### Data Loading
- **Lazy Loading**: Lists load data on screen appearance
- **Pagination**: Patient list supports "Load More"
- **Refresh**: Pull-to-refresh (can be added)
- **Caching**: ViewModels cache loaded data

### Error Handling
- **Network Errors**: Displayed in UI state
- **API Errors**: Shown as error messages
- **Validation**: Form validation before submission

---

## 📱 10. Mobile-Specific Flow Considerations

### Bottom Sheets (Modals)
- **Add Test Modal**: Bottom sheet (90% height)
- **Discount Modal**: Bottom sheet (85% height)
- **No Outside Dismiss**: Discount modal only closes via Cancel

### Tabs (Visit Workspace)
- **Three Tabs**: Patient, Tests, Billing
- **State Preserved**: Tab selection maintained
- **Swipe Navigation**: Can be added

### Forms
- **Scrollable**: All forms support vertical scrolling
- **Validation**: Real-time validation feedback
- **Keyboard Handling**: Auto-dismiss on submit

---

## 🎯 11. Feature-Specific Flows

### Search Flow
```
User Types in Search Box
    │
    ▼
Debounce (optional)
    │
    ▼
Update Search Query in ViewModel
    │
    ▼
Filter Local Data OR
    │
    ▼
API Call with Search Parameter
    │
    ▼
Update UI with Results
```

### Billing Recalculation Flow
```
Action (Add/Remove Test)
    │
    ▼
API: Add/Remove Test
    │
    ▼
On Success → API: Recalculate Billing
    │
    ▼
Refresh Visit Workspace Data
    │
    ▼
Update UI with New Billing
```

---

## 🔄 12. State Flow Example

### Visit Workspace State Flow

```
Initial State
    │
    ├─> isLoading = true
    │
    ▼
Load Visit Workspace
    │
    ├─> API Call
    │
    ▼
Success
    │
    ├─> isLoading = false
    ├─> visitWorkspace = data
    └─> errorMessage = null
    │
    ▼
User Clicks "Add Test"
    │
    ├─> showAddTestModal = true
    │
    ▼
User Selects Tests
    │
    ├─> selectedTestIds = [1, 2, 3]
    │
    ▼
User Clicks "Add"
    │
    ├─> API: Add Tests
    ├─> API: Recalculate Billing
    └─> Refresh Visit Workspace
    │
    ▼
Updated State
    │
    ├─> visitWorkspace = updated data
    └─> showAddTestModal = false
```

---

## 📝 Summary

The application follows a **clean, linear flow** with:

1. **Authentication First**: Login → Token Storage → Auto-login
2. **Dashboard Hub**: Central navigation point
3. **Feature Screens**: Patient management, Visit workspace, Results
4. **Modal Interactions**: Add Test, Discount (non-blocking)
5. **Data Refresh**: Auto-refresh after operations
6. **Error Handling**: User-friendly error messages
7. **State Management**: MVVM with StateFlow
8. **API Integration**: All features connected to backend

The flow is designed to be **intuitive** and **mobile-friendly**, with proper state management and error handling throughout.

---

**Last Updated**: 2026-01-29


