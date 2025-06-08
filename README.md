# FlexiFit
## Description
FlexiFit is a comprehensive fitness and wellness app that helps users build custom diet plans based on user provided ingridients, follow guided gym/yoga routines, and track posture and exercise reps using AI. It also defines the users goal to lose, maintain, or gain weight based on the Body Mass Index (BMI), FlexiFit app helps users stay on track with their health and fitness goals through smart suggestions.

## Screenshots
<table>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/6b47c825-c8e2-45b9-bc86-5a07c794f6c3" alt="DietScreen" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/cea5a78e-bc61-4e51-b47d-8d4a54cbaa48" alt="BottomSheetDiet" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/3ee38e08-c36a-49a1-b9f7-cf841fe2a88f" alt="MealPlanScreen" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/886d7515-415d-40f8-a656-4cb79150fd59" alt="GymScreen" width="250"/>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/2336ddb1-3150-4bd8-8ef8-971174befed6" alt="GymPlanScreen" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/a8e611d7-355a-4ff4-8cd9-74bc909c672a" alt="ExerciseDetail" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/e67825ca-1968-4772-a507-ec833faec255" alt="CameraScreen" width="250"/>
    </td>
    <td align="center">
       <img src="https://github.com/user-attachments/assets/07ca219b-ea27-40a2-aec7-baed8f3c5e24" alt="YogaPlanScreen" width="250"/>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/5239d371-92ce-4ce0-b140-1bfdc4043d64" alt="YogaDetailScreen" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/9176e111-3fe5-4733-be44-2b9dd7536366" alt="ProfileScreen" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/6dc7129a-2be1-42fa-913b-0414a60ce40b" alt="MealPlanScreen" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/977a3b61-98f7-42ed-8768-f59c21d8e807" alt="ExerciseDetail" width="250"/>
    </td>
  </tr>
</table>

## Features
- Built with Jetpack Compose, offering a smooth and modern UI experience.
- Firebase Authentication providing secure and seamless user authentication through email/password and google sign in authentication.
- Personalized Meal Planning with user provided ingridients and filters like Vegan, Gluten-Free, No Sugar, Vegetarian, Oil Free and more.
- Smart suggestions based on BMI and goals (gain/lose/maintain weight).
- Interactive Body Map UI lets users select muscle groups for focused workouts.
- Detailed exercise screens with how-to instructions and video demonstrations.
- Integrated TensorFlow Lite models for Posture detection and Real-time rep counting.

# Cloning and Using the FlexiFit App

You can clone the Flexi Fit app repository from GitHub and set it up on your local machine for testing and development. Follow these steps to get started:

## Clone the Repository
Open your terminal or command prompt and run the following command to clone the repository:

```bash
git clone https://github.com/YourUsername/Flexi-Fit.git
```

Replace YourUsername with your GitHub username.

## Navigate to the Project Directory
Change your current directory to the project folder:
```bash
cd Flexi-Fit
```

## Install Dependencies
Run the following command to install all the project dependencies:
```bash
./gradlew build
```

## Set Up Firebase
To use Firebase features like Authentication and Realtime updates:
<br>

Create a new Firebase project and Register your app with the correct android package name(Your package name is generally the applicationId in your app-level build.gradle file). <br>

Download the google-services.json file and move it to your module (app-level) root directory. </br>
Enable authentication methods:
   - Email/Password
   - Google Sign-In
<br>

Add your SHA-1 key in **Project Settings > Your Apps > Add Fingerprint**:
   - For debug key:  
     `./gradlew signingReport` → use SHA-1 from `debug` variant
   - For release key (for production):  
     Use the SHA-1 from your release keystore
     
<br>

Enable Firestore Database
- Navigate to **Firestore Database** in Firebase Console.
- Click **Create Database**, choose "Start in test mode" (for development), or set rules as needed.
- Create a collection named: `users`


## Running the App
Use Android Studio to open the project, or you can run the app using the command:
```bash
./gradlew run
```
