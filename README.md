# ChatNote

ChatNote is a modular, chat-style note-taking application designed for efficient note organization.  
The app follows best practices in Android development, implementing Clean Architecture, Dependency  
Injection (DI), event-driven state management, and a well-structured modular approach to ensure  
scalability and maintainability.

## Features

- **Chat-style note-taking**: Each note is entered and saved as if sending a message.
- **Local storage**: Notes are stored securely on the device.
- **Folder-based organization**: Notes are grouped into folders similar to chat conversations.
- **Customizable folder icons**: Default icons assigned with options for user customization.
- **Optimized state management**: Uses event-driven architecture for handling UI state and updates.

## Screenshots

### Home List
![Home List](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/home.png)  
![Home List](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/home_folder_swipe.png)  
![Home List](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/home_delete_folder.png)

### Direct Notes
![Direct Notes](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/notes.png)  
![Direct Notes](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/image_draft.png)  
![Direct Notes](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/single_note.png)  
![Direct Notes](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/note_actions.png)

### Folder Editor
![Note Editor](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/folder_editor.png)

### Image Picker
![Image Picker](https://raw.githubusercontent.com/8codehub/ChatNote/refs/heads/main/assets/image_picker.png)

## Architecture

ChatNote follows Clean Architecture principles with a modular approach:

### Core Modules
- `core-data`: Contains shared data models, database entities, and Room configurations.
- `core-domain`: Houses use cases and domain-specific business logic.
- `core-ui`: Includes shared UI components, themes, and utility classes.

### Common Modules
- `common`: Contains shared components and utilities used across the app.
- `content`: Centralized module for storing and managing all application strings.
- `ui-kit`: Provides shared UI building blocks such as the **image picker** and **image viewer** components.

### Navigation Module
- `navigation`: Manages type-safe navigation throughout the app.

### Feature Modules

The `feature` module contains feature-specific submodules, each divided into three layers:

#### Direct Notes Module (`direct-notes`)
- `directnotes-data`: Data layer for managing note-specific database operations.
- `directnotes-domain`: Domain layer for note-related use cases.
- `directnotes-ui`: UI layer for displaying and interacting with individual notes.

#### Home List Module (`home-list`)
- `homelist-data`: Data layer for managing folder-related database operations.
- `homelist-domain`: Domain layer for folder-related use cases.
- `homelist-ui`: UI layer for displaying and interacting with the folder list.

## Firebase Configuration

This project does not include the `google-services.json` file required for Firebase integration. If  
you want to build the app, you need to provide your own `google-services.json` file.

## Dependency Injection

ChatNote uses **Hilt** for DI to ensure proper separation of concerns and efficient dependency  
management. ViewModels only inject use cases to keep UI logic clean.

## State Management

The app utilizes **event-driven architecture** with **Flow**, **StateFlow**, and **Channel** for  
handling state updates and one-time events. This approach avoids unnecessary recompositions and  
ensures optimal performance.

## Best Practices Implemented

- **Modularization**: Clearly defined boundaries between core, common, and feature modules.
- **DI with Hilt**: Ensures testability and maintainability.
- **Event-driven design**: Efficient handling of UI events without unnecessary state persistence.
- **StateFlow for UI updates**: Ensures real-time updates with proper lifecycle awareness.
- **Efficient data handling**: Uses Room with optimized queries and caching mechanisms.

## Purpose & Vision

The goal of ChatNote is to provide an intuitive, lightweight note-taking experience inspired by  
messaging apps. By structuring notes in a familiar chat-like format, users can quickly create, edit,  
and organize their thoughts. The app is designed to be **fast, modular, and scalable**, allowing  
future enhancements such as cloud backup and collaboration features.

## Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **Hilt (DI)**
- **Room (Database)**
- **Flow, StateFlow & Channel (Reactive State Management)**
- **Coroutines (Asynchronous Processing)**
- **Type-Safe Navigation**

## Installation

To set up the project:

```sh
# Clone the repository
$ git clone https://github.com/8codehub/ChatNote.git
$ cd ChatNote

# Build the project
$ ./gradlew assembleDebug
