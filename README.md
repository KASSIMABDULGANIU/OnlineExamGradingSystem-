# Online Examination & Grading System

A desktop **Online Examination and Grading System** built with **Java Swing**. Students sign in, sit timed multiple-choice / true-false / theory exams, and receive an instant auto-graded result with a full breakdown. A teacher dashboard shows every attempt with class statistics.

> INF811D – Object Oriented Programming · MSc Information Technology · University of Cape Coast (CoDE)

---

## Features

- **Two roles** – student sign-in (with input validation) and teacher login via PIN.
- **Timed exams** – a live countdown auto-submits when time runs out.
- **Three question types** – multiple choice, true/false, and keyword-marked theory.
- **Automatic grading** – instant score, percentage, letter grade and pass/fail.
- **Answer breakdown** – marks awarded per question after submission.
- **Teacher dashboard** – table of all attempts plus attempts count, class average and highest score.
- **Consistent, modern UI** – custom flat buttons, cards and colour theme.

---

## How to Run

You need **JDK 17 or later** installed (`java -version` to check).

### Option A – use the helper scripts (Windows)
```bat
compile.bat   ::  compiles all sources into the out\ folder
run.bat       ::  runs the application
```

### Option B – manual commands
From the project root:
```bash
# compile every source file into out/
javac -d out (all .java files under src)

# on Windows PowerShell:
javac -d out (Get-ChildItem -Recurse -Filter *.java src).FullName

# run
java -cp out com.logictc.exam.Main
```

### Demo credentials
- **Student:** enter any Student ID (letters/numbers) and your full name.
- **Teacher PIN:** `1234`

---

## Project Structure

```
src/com/logictc/exam/
├── Main.java                     # entry point, launches the GUI
├── model/                        # domain classes (OOP core)
│   ├── Gradable.java             # interface  (abstraction)
│   ├── Question.java             # abstract base (encapsulation/abstraction)
│   ├── MultipleChoiceQuestion.java
│   ├── TrueFalseQuestion.java    # extends MultipleChoiceQuestion (inheritance)
│   ├── TheoryQuestion.java
│   ├── Student.java
│   ├── Exam.java
│   ├── GradedAnswer.java
│   └── Result.java
├── service/                      # application logic
│   ├── GradingService.java       # polymorphic marking engine
│   ├── ExamRepository.java       # seeded exam content
│   ├── AuthService.java          # validation + sign-in
│   └── ResultStore.java          # results + class statistics
├── exception/
│   └── InvalidInputException.java
└── gui/                          # Java Swing user interface
    ├── AppWindow.java            # CardLayout navigation controller
    ├── LoginPanel.java
    ├── DashboardPanel.java
    ├── ExamPanel.java
    ├── ResultPanel.java
    ├── TeacherPanel.java
    ├── Theme.java · Ui.java · FlatButton.java
```

---

## OOP Concepts Demonstrated

| Concept | Where |
|---|---|
| **Encapsulation** | All model fields are `private` and exposed only through getters (e.g. `Question`, `Student`, `Result`). |
| **Inheritance** | `Question` → `MultipleChoiceQuestion` → `TrueFalseQuestion`; `TheoryQuestion` also extends `Question`. |
| **Polymorphism** | `GradingService` calls `question.grade(response)` without knowing the concrete type; each subclass grades differently. |
| **Abstraction** | `Gradable` interface and the abstract `Question` class define behaviour without implementation. |

Also demonstrated: operators & expressions (grade thresholds, percentages), control structures (loops/conditionals in grading), methods & modular design, exception handling (`InvalidInputException`), collections (`List`, `Map`), event-driven programming (Swing action listeners, timer) and input validation.

---

## Screenshots

See the [`screenshots/`](screenshots) folder.

| Screen | File |
|---|---|
| Login | `screenshots/login.png` |
| Student dashboard | `screenshots/dashboard.png` |
| Taking an exam | `screenshots/exam.png` |
| Result breakdown | `screenshots/result.png` |
| Teacher dashboard | `screenshots/teacher.png` |

---

## Author

**Kassim Abdul-Ganiu** — MSc Information Technology, University of Cape Coast (CoDE).
