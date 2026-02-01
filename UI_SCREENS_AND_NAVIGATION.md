# PathSoft Mobile - UI Screens & Navigation Guide

## 📱 Complete Screen Structure

This document provides a comprehensive overview of all UI screens, their layouts, components, and navigation structure.

---

## 🗺️ Navigation Structure

```
                    ┌─────────────────┐
                    │   Login Screen  │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   Dashboard     │
                    │   (Home Hub)    │
                    └────────┬────────┘
                             │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│ Patient List │    │  Register    │    │  Pending     │
│   Screen     │    │  Patient    │    │  Approvals   │
└──────┬───────┘    └──────┬──────┘    └──────────────┘
       │                    │
       │                    │
       ▼                    ▼
┌──────────────┐    ┌──────────────┐
│   Visit      │    │   Visit      │
│  Workspace   │    │  Workspace   │
│ (from list) │    │ (new visit)  │
└──────┬───────┘    └──────┬───────┘
       │                    │
       │                    │
       └─────────┬──────────┘
                 │
                 ▼
        ┌─────────────────┐
        │  Fill Results   │
        │     Screen      │
        └─────────────────┘
```

---

## 🎨 Screen Details

### 1. Login Screen
**Route**: `login`  
**File**: `app/src/main/java/com/pathsoft/mobile/ui/screens/login/LoginScreen.kt`

#### Layout Structure:
```
┌─────────────────────────────────────┐
│                                     │
│   [Gradient Background]            │
│   (Primary Blue to Purple)         │
│                                     │
│         ┌─────────────────┐        │
│         │   White Card     │        │
│         │                  │        │
│         │  PathSoft Logo   │        │
│         │                  │        │
│         │  Username Input  │        │
│         │                  │        │
│         │  Password Input  │        │
│         │                  │        │
│         │  Error Message   │        │
│         │  (if any)        │        │
│         │                  │        │
│         │  [Login Button]  │        │
│         │                  │        │
│         └─────────────────┘        │
│                                     │
└─────────────────────────────────────┘
```

#### Components:
- **Background**: Full-screen gradient (PRIMARY_BLUE → PRIMARY_PURPLE)
- **Card**: White card with rounded corners, centered
- **Input Fields**: 
  - Username (OutlinedTextField)
  - Password (OutlinedTextField with password visibility toggle)
- **Button**: Primary blue button with loading indicator
- **Error Display**: Red text below inputs

#### Navigation:
- **On Success**: Navigate to Dashboard (clears back stack)
- **On Failure**: Stay on screen, show error

---

### 2. Dashboard Screen
**Route**: `dashboard`  
**File**: `app/src/main/java/com/pathsoft/mobile/ui/screens/dashboard/DashboardScreen.kt`

#### Layout Structure:
```
┌─────────────────────────────────────┐
│  Top App Bar                        │
│  ┌───────────────────────────────┐  │
│  │ Dashboard                     │  │
│  │ Monday, January 29, 2024      │  │
│  │ 10:30 AM                      │  │
│  └───────────────────────────────┘  │
├─────────────────────────────────────┤
│                                     │
│  ┌──────────┐  ┌──────────┐        │
│  │   KPI    │  │   KPI    │        │
│  │  Card 1  │  │  Card 2  │        │
│  │          │  │          │        │
│  │ Pending  │  │Completed │        │
│  │Approvals │  │          │        │
│  └──────────┘  └──────────┘        │
│                                     │
│  ┌──────────┐  ┌──────────┐        │
│  │   KPI    │  │   KPI    │        │
│  │  Card 3  │  │  Card 4  │        │
│  │          │  │          │        │
│  │ Patients │  │ Register │        │
│  │          │  │ Patient  │        │
│  └──────────┘  └──────────┘        │
│                                     │
│  [Gradient Background]             │
│  (Primary Blue to Purple)           │
│                                     │
└─────────────────────────────────────┘
```

#### Components:
- **Top App Bar**: 
  - Title: "Dashboard"
  - Subtitle: Current date and time
  - Transparent background
- **KPI Cards Grid**: 2 columns
  - **Card 1**: Pending Approvals (with icon)
  - **Card 2**: Completed (with icon)
  - **Card 3**: Patients (navigates to Patient List)
  - **Card 4**: Register Patient (navigates to Registration)
- **Background**: Gradient (PRIMARY_BLUE → PRIMARY_PURPLE)

#### KPI Card Structure:
```
┌─────────────────────┐
│  Title              │  Icon
│  Value              │
└─────────────────────┘
```

#### Navigation:
- **KPI Card 3**: → Patient List Screen
- **KPI Card 4**: → Patient Registration Screen
- **KPI Card 1/2**: → (Future: Approval screens)

---

### 3. Patient List Screen
**Route**: `patient_list`  
**File**: `app/src/main/java/com/pathsoft/mobile/ui/screens/patient/PatientListScreen.kt`

#### Layout Structure:
```
┌─────────────────────────────────────┐
│  Top App Bar                        │
│  [← Back]  Patients                 │
├─────────────────────────────────────┤
│                                     │
│  ┌───────────────────────────────┐ │
│  │ 🔍 Search by name or mobile...│ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Patient Card 1                │ │
│  │ ┌───────────────────────────┐ │ │
│  │ │ John Doe        [Pending] │ │ │
│  │ │ 📞 9876543210             │ │ │
│  │ │ 📅 2024-01-29              │ │ │
│  │ │ Total: ₹500  Balance: ₹0  │ │ │
│  │ └───────────────────────────┘ │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Patient Card 2                │ │
│  │ ...                            │ │
│  └───────────────────────────────┘ │
│                                     │
│  [Load More Button]                │
│                                     │
└─────────────────────────────────────┘
```

#### Components:
- **Top App Bar**: 
  - Back button (←)
  - Title: "Patients"
  - Primary blue background
- **Search Bar**: 
  - OutlinedTextField with search icon
  - Real-time search filtering
- **Patient Cards**: 
  - Scrollable list (LazyColumn)
  - Each card shows:
    - Patient name
    - Status badge (Pending/Completed/Closed)
    - Mobile number
    - Visit date
    - Total amount
    - Balance amount
- **Load More Button**: 
  - Appears at bottom if more pages available
  - Shows loading indicator when fetching

#### Patient Card Structure:
```
┌─────────────────────────────────┐
│ Patient Name      [Status Badge]│
│ 📞 Mobile Number                 │
│ 📅 Visit Date                    │
│ Total: ₹XXX  Balance: ₹XXX      │
└─────────────────────────────────┘
```

#### Status Badge Colors:
- **Pending**: Yellow/Orange (STATUS_WARNING)
- **Completed**: Green (STATUS_GOOD)
- **Closed**: Gray (TEXT_MUTED)

#### Navigation:
- **Back Button**: → Previous screen (Dashboard)
- **Patient Card Click**: → Visit Workspace Screen

---

### 4. Patient Registration Screen
**Route**: `patient_registration`  
**File**: `app/src/main/java/com/pathsoft/mobile/ui/screens/patient/PatientRegistrationScreen.kt`

#### Layout Structure:
```
┌─────────────────────────────────────┐
│  Top App Bar                        │
│  [← Back]  Register Patient         │
├─────────────────────────────────────┤
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Patient Information           │ │
│  │ ──────────────────────────── │ │
│  │                               │ │
│  │ Patient Name *                │ │
│  │ [________________]             │ │
│  │                               │ │
│  │ Mobile Number                 │ │
│  │ [________________]             │ │
│  │                               │ │
│  │ Email                         │ │
│  │ [________________]             │ │
│  │                               │ │
│  │ Address                       │ │
│  │ [________________]             │ │
│  │ [________________]             │ │
│  │                               │ │
│  │ Gender                        │ │
│  │ [Dropdown ▼]                  │ │
│  │                               │ │
│  │ Age: [Years] [Months] [Days]  │ │
│  │                               │ │
│  └───────────────────────────────┘ │
│                                     │
│  [Register Patient Button]         │
│                                     │
└─────────────────────────────────────┘
```

#### Components:
- **Top App Bar**: 
  - Back button
  - Title: "Register Patient"
- **Form Section**: 
  - White card with border
  - Section title with underline (PRIMARY_BLUE)
  - Form fields:
    - Patient Name* (required)
    - Mobile Number
    - Email
    - Address (multi-line)
    - Gender (dropdown: Male/Female/Other)
    - Age (3 fields: Years/Months/Days)
- **Action Button**: 
  - Primary blue button
  - "Register Patient" text
  - Full width, bottom of screen

#### Form Section Structure:
```
┌─────────────────────────────┐
│ Section Title               │
│ ─────────────────────────── │
│                             │
│ Form Fields...             │
│                             │
└─────────────────────────────┘
```

#### Navigation:
- **Back Button**: → Previous screen
- **On Success**: → Visit Workspace (new visit)
- **On Failure**: Stay on screen, show error

---

### 5. Visit Workspace Screen
**Route**: `visit_workspace/{visitId}/{labCode}`  
**File**: `app/src/main/java/com/pathsoft/mobile/ui/screens/visitworkspace/VisitWorkspaceScreen.kt`

#### Layout Structure:
```
┌─────────────────────────────────────┐
│  Top App Bar                        │
│  [← Back]  Visit #123  [+ Add Test] │
├─────────────────────────────────────┤
│  Tabs: [Patient] [Tests] [Billing] │
├─────────────────────────────────────┤
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Tab Content                    │ │
│  │ (Changes based on selected tab)│ │
│  │                                │ │
│  │                                │ │
│  └───────────────────────────────┘ │
│                                     │
│  [Sky Blue Gradient Background]     │
│                                     │
└─────────────────────────────────────┘
```

#### Tab 1: Patient Info Panel
```
┌─────────────────────────────────────┐
│  Patient Information                 │
│  ────────────────────────────────── │
│                                     │
│  Name:        John Doe              │
│  Mobile:      9876543210            │
│  Email:       john@example.com      │
│  Address:     123 Main St           │
│  Age:         35 years              │
│  Gender:      Male                  │
│                                     │
└─────────────────────────────────────┘
```

#### Tab 2: Test Details Panel
```
┌─────────────────────────────────────┐
│  Test Details                       │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Complete Blood Count          │ │
│  │ Rate: ₹500                    │ │
│  │ Discount: ₹50                 │ │
│  │ Net: ₹450              [🗑️]  │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Lipid Profile                  │ │
│  │ Rate: ₹800                     │ │
│  │ Net: ₹800               [🗑️]  │ │
│  └───────────────────────────────┘ │
│                                     │
└─────────────────────────────────────┘
```

#### Tab 3: Billing Panel
```
┌─────────────────────────────────────┐
│  Billing Summary    [Apply Discount]│
│  ────────────────────────────────── │
│                                     │
│  Gross Amount:        ₹1,300.00   │
│  Discount:            ₹50.00      │
│  ──────────────────────────────── │
│  Net Amount:          ₹1,250.00    │
│  Paid Amount:         ₹500.00      │
│  Balance Amount:      ₹750.00      │
│                                     │
└─────────────────────────────────────┘
```

#### Components:
- **Top App Bar**: 
  - Back button
  - Title: "Visit #{visitId}"
  - Add Test button (hidden if visit status is "Closed")
  - Semi-transparent white background
- **Tab Row**: 
  - Three tabs: Patient, Tests, Billing
  - Material Design 3 TabRow
- **Background**: Sky blue gradient (VISIT_WORKSPACE_START → VISIT_WORKSPACE_END)
- **Content Cards**: 
  - White cards with rounded corners
  - Elevation for depth

#### Navigation:
- **Back Button**: → Previous screen
- **Add Test Button**: Opens Add Test Modal
- **Apply Discount Button**: Opens Discount Modal
- **Remove Test**: Deletes test, recalculates billing

---

### 6. Add Test Modal
**Route**: Modal (Bottom Sheet)  
**File**: `app/src/main/java/com/pathsoft/mobile/ui/components/AddTestModal.kt`

#### Layout Structure:
```
┌─────────────────────────────────────┐
│  Add Tests/Packages          [✕]     │
├─────────────────────────────────────┤
│                                     │
│  [Tests] [Packages]  (Toggle)      │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ 🔍 Search...                  │ │
│  └───────────────────────────────┘ │
│                                     │
│  Selected: 2                        │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ ☑ Complete Blood Count        │ │
│  │   ₹500                         │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ ☐ Lipid Profile               │ │
│  │   ₹800                         │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ ☑ Blood Sugar (Already Added) │ │
│  │   ₹300                         │ │
│  └───────────────────────────────┘ │
│                                     │
│  [Clear]  [Add]                     │
│                                     │
└─────────────────────────────────────┘
```

#### Components:
- **Header**: 
  - Title: "Add Tests/Packages"
  - Close button (X)
- **Toggle Buttons**: 
  - FilterChips: "Tests" / "Packages"
  - Switches between test and package lists
- **Search Bar**: 
  - Real-time search filtering
  - Search icon
- **Selected Count**: 
  - Shows number of selected items
  - Blue text
- **List Items**: 
  - Checkbox for selection
  - Test/Package name
  - Rate/Amount
  - "Already Added" badge (if test already in visit)
- **Action Buttons**: 
  - Clear button (outlined)
  - Add button (primary blue)

#### Test Selection Item:
```
┌─────────────────────────────────┐
│ ☑ Test Name                     │
│   ₹500                           │
│              [Already Added]    │
└─────────────────────────────────┘
```

#### Navigation:
- **Close Button**: Closes modal, returns to Visit Workspace
- **Add Button**: Adds selected tests → Closes modal → Refreshes Visit Workspace

---

### 7. Discount Modal
**Route**: Modal (Bottom Sheet)  
**File**: `app/src/main/java/com/pathsoft/mobile/ui/components/DiscountModal.kt`

#### Layout Structure:
```
┌─────────────────────────────────────┐
│  Apply Discount             [✕]     │
├─────────────────────────────────────┤
│                                     │
│  Discount Category                 │
│  [Doctor] [Lab] [Write Off]        │
│                                     │
│  Discount Type                     │
│  [Fixed Amount] [Percentage]       │
│                                     │
│  Discount Value *                   │
│  [________] ₹ or %                  │
│                                     │
│  Doctor Discount (₹)                │
│  [________] (if Doctor selected)    │
│                                     │
│  Remarks                            │
│  [________________]                 │
│  [________________]                 │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Billing Summary              │ │
│  │ ─────────────────────────── │ │
│  │ Gross Amount:    ₹1,300.00  │ │
│  │ Discount:        -₹50.00     │ │
│  │ ─────────────────────────── │ │
│  │ Net Amount:      ₹1,250.00  │ │
│  │ Balance Amount:  ₹750.00    │ │
│  └───────────────────────────────┘ │
│                                     │
│  [Cancel]  [Apply Discount]         │
│                                     │
└─────────────────────────────────────┘
```

#### Components:
- **Header**: 
  - Title: "Apply Discount"
  - Close button
- **Discount Category**: 
  - Three FilterChips: Doctor, Lab, Write Off
  - Only one can be selected
- **Discount Type**: 
  - Two FilterChips: Fixed Amount (default), Percentage
- **Discount Value**: 
  - Required field
  - Shows suffix (₹ or %)
- **Category-Specific Fields**: 
  - Doctor Discount (if Doctor selected)
  - Lab Discount (if Lab selected)
  - Write Off Amount (if Write Off selected)
- **Remarks**: 
  - Multi-line text field
  - Optional
- **Billing Summary Card**: 
  - Light gray background
  - Shows current billing breakdown
- **Action Buttons**: 
  - Cancel (outlined)
  - Apply Discount (primary blue, disabled if value empty)

#### Billing Summary Structure:
```
┌─────────────────────────────┐
│ Billing Summary             │
│ ─────────────────────────── │
│ Gross Amount:    ₹1,300.00 │
│ Discount:        -₹50.00    │
│ ─────────────────────────── │
│ Net Amount:      ₹1,250.00 │
│ Balance Amount:  ₹750.00    │
└─────────────────────────────┘
```

#### Navigation:
- **Cancel Button**: Closes modal, returns to Visit Workspace
- **Apply Discount Button**: Applies discount → Closes modal → Refreshes Visit Workspace
- **Outside Click**: Does NOT close modal (as per requirements)

---

### 8. Fill Results Screen
**Route**: `fill_results/{testIds}/{visitId}/{patientId}/{labCode}`  
**File**: `app/src/main/java/com/pathsoft/mobile/ui/screens/testresults/FillResultsScreen.kt`

#### Layout Structure:
```
┌─────────────────────────────────────┐
│  Top App Bar                        │
│  [← Back]  Fill Test Results  [💾] │
├─────────────────────────────────────┤
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Complete Blood Count          │ │
│  │ ──────────────────────────── │ │
│  │                               │ │
│  │ Hematology Section            │ │
│  │                               │ │
│  │ Hemoglobin                    │ │
│  │ Range: 12.0 - 16.0 g/dL       │ │
│  │ [________] g/dL               │ │
│  │                               │ │
│  │ White Blood Count             │ │
│  │ Range: 4000 - 11000 /μL       │ │
│  │ [________] /μL                 │ │
│  │                               │ │
│  │ Red Blood Count               │ │
│  │ Range: 4.0 - 5.5 million/μL  │ │
│  │ [________] million/μL         │ │
│  │                               │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Lipid Profile                  │ │
│  │ ──────────────────────────── │ │
│  │                               │ │
│  │ Total Cholesterol             │ │
│  │ Range: < 200 mg/dL            │ │
│  │ [________] mg/dL              │ │
│  │                               │ │
│  └───────────────────────────────┘ │
│                                     │
│  [💾 Save Results] (FAB)           │
│                                     │
└─────────────────────────────────────┘
```

#### Components:
- **Top App Bar**: 
  - Back button
  - Title: "Fill Test Results"
  - Save button (icon)
- **Test Sections**: 
  - Each test in a white card
  - Test name as header
  - Divider line
- **Parameter Groups**: 
  - Grouped by sections (if available)
  - Section name in blue
- **Parameter Inputs**: 
  - Parameter name
  - Normal range display
  - Input field with unit suffix
  - Abnormal value highlighting (red background)
  - Warning message for out-of-range values
- **Floating Action Button**: 
  - Save icon
  - Sticky at bottom right
  - Shows loading indicator when saving

#### Parameter Input Structure:
```
┌─────────────────────────────────┐
│ Parameter Name    Range: X-Y    │
│ [Input Field] Unit               │
│ ⚠ Value outside normal range    │
│ (if abnormal)                    │
└─────────────────────────────────┘
```

#### Abnormal Value Highlighting:
- **Normal**: White/transparent background
- **Abnormal**: Red background (ERROR_RED with 10% opacity)
- **Warning Text**: Red text below input

#### Navigation:
- **Back Button**: → Previous screen
- **Save Button**: Saves results → Navigates back
- **On Success**: Returns to previous screen
- **On Failure**: Shows error, stays on screen

---

## 🎨 Design System Usage

### Colors
- **Primary**: PRIMARY_BLUE (#667EEA)
- **Gradient**: PRIMARY_BLUE → PRIMARY_PURPLE
- **Success**: SUCCESS_GREEN (#28A745)
- **Error**: ERROR_RED (#DC3545)
- **Warning**: WARNING_ORANGE (#FF9800)
- **Background**: BACKGROUND_WHITE (#FFFFFF)
- **Text Dark**: TEXT_DARK (#333333)
- **Text Medium**: TEXT_MEDIUM (#666666)

### Typography
- **Headlines**: 18-28sp, Bold/SemiBold
- **Body**: 13-14sp, Regular/Medium
- **Labels**: 11-12sp, Medium
- **Captions**: 10-11sp, Regular

### Spacing
- **XS**: 4dp
- **Small**: 8dp
- **Medium**: 12dp
- **Large**: 16dp
- **XLarge**: 20dp
- **XXLarge**: 24dp

### Components
- **Cards**: White background, rounded corners (8dp), elevation (4dp)
- **Buttons**: Primary blue, rounded corners (5dp), 56dp height
- **Input Fields**: OutlinedTextField, blue focus border
- **Modals**: Bottom sheets, 85-90% height

---

## 🔄 Navigation Patterns

### 1. Stack Navigation
- **Forward**: Push new screen onto stack
- **Back**: Pop current screen from stack
- **Replace**: Clear stack and set new root

### 2. Modal Navigation
- **Bottom Sheets**: Slide up from bottom
- **Overlay**: Semi-transparent background
- **Dismiss**: Swipe down or close button

### 3. Tab Navigation
- **Visit Workspace**: Three tabs, horizontal
- **State Preserved**: Tab selection maintained
- **Swipe**: Can swipe between tabs (optional)

---

## 📐 Screen Dimensions & Layouts

### Phone Layout (< 600dp width)
- **Single Column**: All forms and lists
- **Full Width**: Cards and inputs
- **Stacked**: Tabs become scrollable

### Tablet Layout (≥ 600dp width)
- **Multi-Column**: Can show side-by-side panels
- **Wider Cards**: More horizontal space
- **Grid**: 2-3 columns for lists

---

## 🎯 Key UI Patterns

### 1. Loading States
- **CircularProgressIndicator**: Center of screen
- **Skeleton Screens**: (Can be added)
- **Button Loading**: Spinner in button

### 2. Error States
- **Error Messages**: Red text below inputs
- **Snackbar**: Bottom of screen
- **Error Cards**: Red-tinted cards

### 3. Empty States
- **No Data**: "No items found" message
- **Centered Text**: Middle of screen
- **Action Hints**: "Try adding..." suggestions

### 4. Success States
- **Navigation**: Auto-navigate on success
- **Refresh**: Data refreshes automatically
- **Feedback**: (Can add success snackbar)

---

## 📱 Screen Relationships

```
Login
  └─> Dashboard
        ├─> Patient List
        │     └─> Visit Workspace
        │           ├─> Add Test Modal
        │           ├─> Discount Modal
        │           └─> Fill Results
        │
        └─> Patient Registration
              └─> Visit Workspace (new)
                    └─> (same as above)
```

---

## 🎨 Visual Hierarchy

### 1. Primary Actions
- **Buttons**: Primary blue, prominent
- **FABs**: Floating, always visible
- **Top Bar Actions**: Icon buttons

### 2. Secondary Actions
- **Outlined Buttons**: Less prominent
- **Text Buttons**: Subtle
- **Icon Buttons**: Minimal

### 3. Information Display
- **Cards**: Grouped information
- **Lists**: Scrollable content
- **Badges**: Status indicators

---

## 🔍 Search & Filter Patterns

### Patient List
- **Search Bar**: Top of screen
- **Real-time**: Filters as you type
- **Scope**: Name and mobile number

### Add Test Modal
- **Search Bar**: Below toggle buttons
- **Filter**: Tests or packages
- **Scope**: Test/package names

---

## 📊 Data Display Patterns

### Lists
- **LazyColumn**: Efficient scrolling
- **Cards**: Each item in a card
- **Pagination**: Load more button

### Forms
- **Sections**: Grouped fields
- **Validation**: Real-time feedback
- **Scrollable**: Vertical scrolling

### Details
- **Info Rows**: Label-value pairs
- **Cards**: Grouped information
- **Hierarchy**: Sections and subsections

---

## 🎭 Modal Patterns

### Bottom Sheets
- **Height**: 85-90% of screen
- **Rounded Top**: Large radius
- **Dismiss**: Swipe down or button
- **Background**: Semi-transparent overlay

### Content
- **Scrollable**: If content exceeds height
- **Actions**: Bottom of modal
- **Header**: Title and close button

---

## 📝 Summary

### Screen Count: 8 Main Screens
1. Login Screen
2. Dashboard Screen
3. Patient List Screen
4. Patient Registration Screen
5. Visit Workspace Screen (3 tabs)
6. Add Test Modal
7. Discount Modal
8. Fill Results Screen

### Navigation Type: Stack-based
- **Forward Navigation**: Push screens
- **Back Navigation**: Pop screens
- **Modal Navigation**: Overlay screens

### Design Consistency
- **Colors**: Consistent theme throughout
- **Typography**: Standardized sizes
- **Spacing**: Uniform padding/margins
- **Components**: Reusable UI elements

### User Experience
- **Intuitive**: Clear navigation paths
- **Responsive**: Loading and error states
- **Accessible**: Proper labels and feedback
- **Mobile-First**: Touch-friendly interactions

---

**Last Updated**: 2026-01-29


