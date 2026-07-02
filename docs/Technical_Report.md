# TECHNICAL REPORT


## 1. Title Page

**UNIVERSITY OF CAPE COAST**
**COLLEGE OF DISTANCE EDUCATION (CoDE)**
**DEPARTMENT OF SCIENCE AND MATHEMATICS**

**Course:** INF811D – Object Oriented Programming
**Programme:** MSc Information Technology
**Academic Year:** 2025/2026

**Project Title:** Online Examination and Grading System (Java Swing Desktop Application)

**Student Name:** Kassim Abdul-Ganiu
**Index / Student Number:** MS/ITE/25/0034
**Date:** 02/07/2026_

---

## 2. Introduction

Examinations are a core part of teaching and learning, but marking them by hand is slow and error-prone, especially for large classes. This project presents an **Online Examination and Grading System**: a desktop application, built in Java using the Swing GUI toolkit, that lets students sit exams on a computer and receive their marks instantly, while giving the teacher an at-a-glance view of every attempt.

The application was designed and implemented individually as the semester project for INF811D (Object Oriented Programming). It demonstrates the four pillars of object-oriented programming — encapsulation, inheritance, polymorphism and abstraction — together with event-driven GUI programming, exception handling and input validation.

I chose this topic because my personal interest and previous projects have centred on the educational domain, so an examination and grading system was the natural fit among the listed options and one whose requirements I understood well. Beyond satisfying the course requirements, I set out to strengthen my grasp of applying OOP principles in a real graphical application and to gain first-hand experience building a Java desktop interface with Swing.

---

## 3. Problem Statement

Traditional paper-based examinations and manual marking present several problems:

- Marking is **time-consuming** and delays feedback to students.
- Manual grading is prone to **human calculation errors** and inconsistency.
- Paper results are **hard to aggregate**; producing class statistics (average, highest score) is tedious.
- There is no immediate, structured **feedback** to the student on which questions were missed.

The system addresses these problems by automating the delivery, timing, marking and reporting of objective and short-answer examinations.

---

## 4. Objectives of the System

The objectives of the system are to:

1. Allow a student to sign in and sit a timed examination through a graphical interface.
2. Support multiple question types — multiple choice, true/false and short theory questions.
3. **Automatically mark** each submission and compute a score, percentage, letter grade and pass/fail outcome.
4. Give the student an **itemised breakdown** of marks awarded per question.
5. Provide a **teacher dashboard** showing all attempts and class statistics.
6. Validate all user input and handle errors gracefully without crashing.
7. Apply and clearly demonstrate the four core OOP principles.

---

## 5. Scope of the System

**In scope:**

- Student sign-in with input validation and a teacher/administrator login secured by a PIN.
- Two pre-loaded examinations (Introduction to Computing, Computer Networks) with a mixture of question types.
- A countdown timer that auto-submits when time expires.
- Automatic grading, result breakdown, and session-level teacher statistics.
- A single-machine desktop application using in-memory data (no external database required).

**Out of scope:**

- Networked/multi-user access over the internet.
- Persistent storage in an external database (results are held for the running session).
- Teacher authoring of new exams through the GUI (exam content is seeded in code).

These boundaries were chosen to keep the project focused on demonstrating OOP and GUI concepts rather than infrastructure.

---

## 6. Methodology

The project followed an **incremental, layered development approach**:

1. **Requirements** were derived from the project brief and the chosen topic (Online Examination and Grading System).
2. **Design** – the system was separated into four layers: `model` (domain objects), `service` (application logic), `exception` (error types) and `gui` (Swing interface). This separation of concerns keeps each class small and focused.
3. **Implementation** – the model layer was built first and tested conceptually, then the service layer, and finally the Swing GUI was layered on top. Development used **JDK 17** and was compiled with `javac`.
4. **Version control** – the code was developed on Git with meaningful, incremental commits and pushed to GitHub, giving a clear development history.
5. **Testing** – the application was run repeatedly, exercising each screen (login, exam, result, teacher) and edge cases such as empty inputs, wrong PIN and unanswered questions.

---

## 7. System Design

### 7.1 Architecture

The application uses a simple **layered architecture**:

```
             ┌─────────────────────────┐
             │          gui            │  Swing screens + navigation
             │  (AppWindow, *Panel)    │
             └────────────┬────────────┘
                          │ calls
             ┌────────────▼────────────┐
             │        service          │  grading, auth, storage
             │ (GradingService, etc.)  │
             └────────────┬────────────┘
                          │ uses
             ┌────────────▼────────────┐
             │         model           │  domain objects (OOP core)
             │ (Question, Exam, ...)   │
             └─────────────────────────┘
   exception/  supports all layers with typed errors
```

### 7.2 Navigation flow

```
Login ──(student)──► Dashboard ──► Exam ──► Result ──► Dashboard / Logout
      └─(teacher PIN)─► Teacher Dashboard
```

`AppWindow` holds a `CardLayout` and swaps one full-screen panel for another, acting as a lightweight navigation controller.

### 7.3 Class diagram (inheritance core)

```
            «interface»
             Gradable
                ▲
                │ implements
            Question (abstract)
             ▲         ▲
             │         │
 MultipleChoiceQuestion │
             ▲         TheoryQuestion
             │
      TrueFalseQuestion
```

---

## 8. Description of Classes and Methods

### Model layer (`com.logictc.exam.model`)

| Class | Responsibility | Key methods |
|---|---|---|
| `Gradable` (interface) | Contract for anything that can be marked | `grade(String)`, `getMaxPoints()` |
| `Question` (abstract) | Base type for all questions; holds id, text, points | `getPoints()`, abstract `getType()`, `isAutoMarked()` |
| `MultipleChoiceQuestion` | Question with options and one correct index | overrides `grade()`, `getType()` |
| `TrueFalseQuestion` | Specialised MCQ with True/False options | extends `MultipleChoiceQuestion` |
| `TheoryQuestion` | Free-text question marked by keyword coverage | overrides `grade()`, `isAutoMarked()` |
| `Student` | Immutable candidate (id, name, programme) | getters, `toString()` |
| `Exam` | Titled, timed list of questions | `getTotalPoints()`, `getQuestions()` |
| `GradedAnswer` | Outcome of marking one question | `getAwarded()`, `isCorrect()` |
| `Result` | Full outcome of an attempt | `getPercentage()`, `getGrade()`, `isPass()` |

### Service layer (`com.logictc.exam.service`)

| Class | Responsibility | Key methods |
|---|---|---|
| `GradingService` | Marks a whole exam via polymorphic dispatch | `grade(student, exam, responses)` |
| `ExamRepository` | Supplies the seeded exams | `getExams()`, `findByCode()` |
| `AuthService` | Validates sign-in and teacher PIN | `loginStudent(...)`, `verifyTeacher(...)` |
| `ResultStore` | Holds results and computes statistics | `add()`, `averagePercentage()`, `highestPercentage()` |

### Exception layer (`com.logictc.exam.exception`)

| Class | Responsibility |
|---|---|
| `InvalidInputException` | Checked exception thrown on validation failure |

### GUI layer (`com.logictc.exam.gui`)

| Class | Responsibility |
|---|---|
| `AppWindow` | Main `JFrame`; owns services; navigates between panels |
| `LoginPanel` | Student form + teacher PIN, with validation |
| `DashboardPanel` | Lists available exams as cards |
| `ExamPanel` | Renders questions, runs the countdown timer, submits |
| `ResultPanel` | Shows score, grade and per-question breakdown |
| `TeacherPanel` | Results table plus class statistics |
| `Theme`, `Ui`, `FlatButton` | Shared styling and reusable components |
| `Main` | Entry point; sets look-and-feel and launches the GUI |

---

## 9. GUI Design Explanation

The interface was built with **Java Swing** and follows a consistent visual language defined once in the `Theme` class (colour palette and fonts) and reused everywhere through the `Ui` helper factory:

- **Login screen** – a centred card with clearly labelled fields, a primary "Start as Student" action and a separate teacher PIN area. Invalid input raises a friendly dialog rather than crashing.
- **Dashboard** – a coloured header bar greeting the student, followed by a scrollable list of exam "cards" each showing the subject, question count, total marks and duration.
- **Exam screen** – each question is shown in its own card with the correct input control (radio buttons for choices, a text area for theory) and a live **countdown timer** in the header.
- **Result screen** – a green/red band communicates pass/fail at a glance, followed by the headline score and an itemised breakdown of marks per question.
- **Teacher dashboard** – summary "stat cards" (attempts, class average, highest score) above a full results table.

Custom `FlatButton` components paint their own rounded, coloured background so the look is identical regardless of the operating system's default button style. Navigation between screens is handled by a `CardLayout` in `AppWindow`.

---

## 10. OOP Concepts Implemented

| Concept | Where it is implemented | Explanation |
|---|---|---|
| **Encapsulation** | `Question`, `Student`, `Exam`, `Result` | All fields are `private` and accessed only through public getters; objects validate their own state in the constructor. |
| **Inheritance** | `Question → MultipleChoiceQuestion → TrueFalseQuestion`; `TheoryQuestion → Question` | Subclasses reuse the base class's fields and behaviour. `TrueFalseQuestion` reuses the entire grading logic of `MultipleChoiceQuestion`. |
| **Polymorphism** | `GradingService.grade()` | The service calls `question.grade(response)` on the abstract `Question` type; at runtime the correct subclass method executes. Adding a new question type needs no change to the grading engine. |
| **Abstraction** | `Gradable` interface, abstract `Question` class | Define *what* a question must do (grade itself, expose max points) without dictating *how*, hiding each type's marking details. |

**Supporting requirements also demonstrated:**

- *Operators & expressions* – percentage and grade-threshold calculations in `Result`.
- *Control structures* – loops and conditionals across grading and statistics.
- *Methods & modular programming* – small, single-purpose methods and helper classes (`Ui`, `Theme`).
- *Exception handling* – `InvalidInputException` thrown by `AuthService` and caught in `LoginPanel`.
- *Arrays/collections* – `List` and `Map` used for questions, options and responses.
- *Event-driven programming* – Swing action listeners and a `javax.swing.Timer`.
- *Input validation* – ID/name checks and PIN verification before proceeding.

---

## 11. Screenshots and Outputs

Screenshots are stored in the `screenshots/` folder of the repository:

| Screen | File |
|---|---|
| Login | `screenshots/login.png` |
| Student dashboard | `screenshots/dashboard.png` |
| Taking an exam (with timer) | `screenshots/exam.png` |
| Result breakdown | `screenshots/result.png` |
| Teacher dashboard | `screenshots/teacher.png` |



## 12. GitHub Repository Link

**https://github.com/KASSIMABDULGANIU/OnlineExamGradingSystem-**

The repository contains the complete source code, a proper package/folder structure, `README.md`, build/run scripts, screenshots and a meaningful commit history.

---

## 13. Challenges Encountered

Several challenges were encountered during the development of this project:

- **Choosing a single topic.** Because I have built a number of projects in the educational domain, narrowing the fifty options down to just one was genuinely difficult. I eventually settled on the Online Examination and Grading System because it aligned most closely with work I already understood and was passionate about.

- **Setting up the development environment.** Preparing the Java tooling and IDE and creating a well-structured project from scratch took some effort, particularly getting the package/folder layout right so the classes were organised sensibly across the model, service and GUI layers.

- **Working with Swing for the first time.** I deliberately chose Java Swing over JavaFX, which meant learning a new GUI toolkit from the ground up — laying out components, handling events, and getting a consistent, professional appearance across screens.

- **Setting up Git and GitHub.** Initialising the repository, making meaningful commits and pushing the project to GitHub was a new workflow for me, and getting the local repository correctly connected to the remote took a little troubleshooting.

- **A character-encoding bug.** Separator characters initially displayed as garbled text (`â€¢`) because the source files were UTF-8 but were being compiled with the platform's default encoding. I resolved it by switching to plain ASCII separators and compiling with the `-encoding UTF-8` option.

---

## 14. Conclusion  

The Online Examination and Grading System successfully meets its objectives: a student can sign in, sit a timed exam, and receive an automatically calculated result with a detailed breakdown, while the teacher can review all attempts and class statistics. The project provided practical experience in applying the four OOP principles and in building an event-driven graphical application in Java Swing.



## 15. Recommendations  

Future improvements could include:

- Persisting results to a database or file so they survive between sessions.
- Allowing teachers to create and edit exams through the interface.
- Adding user accounts with secure password hashing instead of a shared PIN.
- Supporting more question types (e.g. matching, fill-in-the-blank) and randomised question order.
- Exporting results and statistics to PDF or Excel.

---

## 16. References

> Use your institution's citation style. Suggested starting references:

- Oracle. *Java Platform, Standard Edition 17 API Specification.* Oracle Corporation.
- Oracle. *The Java Tutorials – Creating a GUI With Swing.*
- Deitel, P. & Deitel, H. *Java How to Program.* Pearson.
- Bloch, J. *Effective Java* (3rd ed.). Addison-Wesley.
