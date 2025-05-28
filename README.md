---

# 🤖 AI Assistant – Kotlin Android App (Jetpack Compose + Gemini API)

An AI-powered assistant app built with **Kotlin** and **Jetpack Compose**, enabling users to chat with a smart AI assistant that understands and responds in real time.

Powered by **Gemini API** (`gemini-1.5-flash` model), this app lets you explore natural language conversations on any topic — right from your Android device.

---

## ✨ Features

* 💬 Chat with an AI assistant in real time
* 🧠 Powered by Gemini's `gemini-1.5-flash` model
* ⚡️ Fast and responsive UI built with **Jetpack Compose**
* ☁️ Secure API access with Gemini API key

---

---

## 🚀 Getting Started

### 1. Clone the repository


### 2. Get a Gemini API Key

* Go to [Google AI Studio](https://aistudio.google.com/app/apikey)
* Sign in with your Google account
* Generate an **API key**
* Copy the key for later use

### 3. Add the API Key to your project

In your project, create a `local.properties` file (if not already there) and add:

```
GEMINI_API_KEY=your_api_key_here
```

Then, in your `build.gradle.kts` (or `.gradle` if using Groovy), access it like this:

```kotlin
val geminiApiKey: String = project.findProperty("GEMINI_API_KEY") as String
```

Or directly pass it to your network layer.

---

## 🛠️ Tech Stack

* **Kotlin** – Modern programming language for Android
* **Jetpack Compose** – Declarative UI framework
* **Retrofit / Ktor / OkHttp** – For making network requests (depending on your setup)
* **Gemini API** – From Google AI Studio (`gemini-1.5-flash` model)


---

## 🔐 API Usage

You're interacting with Gemini using the `gemini-1.5-flash` model.

---

## ✅ To Do

* [ ] Add conversation history
* [ ] Support voice input
* [ ] Dark theme toggle
* [ ] Caching recent chats

---

## 🤝 Contributing

Pull requests are welcome! If you’d like to contribute, please fork the repo and submit a PR.

---

## 📬 Contact

Have questions or suggestions?

* Email: `youremail@example.com`
* GitHub: [@shyam-barange](https://github.com/shyam-barange)

---
