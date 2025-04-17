# 🚗 Car Data CLI

A **Java 17** command-line application that parses and processes car data from **XML** and **CSV** files.  
It supports flexible filtering, sorting, and output formatting — and runs smoothly via **Docker**, with execution logs saved to `logs/app.log`.

---

## ✨ Features

- 🔍 Filter by:
  - `Brand` + `Price`
  - `Brand` + `ReleaseDate`
- 📊 Sort by:
  - `year-asc`, `year-desc`
  - `price-asc`, `price-desc`
- 📤 Output Formats:
  - Table (default)
  - JSON
  - XML
- 🐳 Dockerized for seamless cross-platform use
- 🪵 Logs written to `logs/app.log`

---

## 🐳 Run with Docker (Linux)

> ✅ Please install Docker, if not already installed!

### 🔧 Step 1: Make Shell Scripts Executable

```bash
 chmod +x build.sh
 chmod +x run.sh
```

### 🏗️ Step 2: Build the Docker Image

```bash
 ./build.sh
```

---

## 🚀 Usage
-  ./run.sh [filter] [sort] [output]


### 🔘 Parameters (All Optional)
| Argument   | Description                                                       | Example                                  |
|------------|-------------------------------------------------------------------|------------------------------------------|
| `filter`   | Filter By (Brand+Price): `Brand`, `Price`                         | `"Brand=Toyota,Price<30000"`             |
|            | Filter By (Brand+ReleaseDate): `Brand`, `ReleaseDate`             | `"Brand=Toyota,ReleaseDate=2023-01-15"`  |
| `sort`     | Sorting order: `year-desc`, `year-asc`, `price-desc`, `price-asc` | `"price-desc"`                           |
| `output`   | Output format: `table`, `json`, `xml`                             | `"json"`                                 |

---

## 💡 Examples

### 🔹 Default run (no filter, sort by year, output table)
```bash
 ./run.sh
```

### 🔹 Only filter examples
```bash
 ./run.sh "Brand=Toyota,Price<30000"
 ./run.sh "Brand=Toyota,Price>20000"
 ./run.sh "Brand=Toyota,Price=25000"
 ./run.sh "Brand=Toyota,ReleaseDate=2023-01-15"
 ./run.sh "Brand=Toyota,ReleaseDate>2022-01-01"
 ./run.sh "Brand=Toyota,ReleaseDate<2024-01-01"
```

### 🔹 Only sort examples
```bash
 ./run.sh "" "year-desc"
 ./run.sh "" "year-asc"
 ./run.sh "" "price-desc"
 ./run.sh "" "price-asc"
```

### 🔹 Only output format examples
```bash
 ./run.sh "" "" "table"
 ./run.sh "" "" "json"
 ./run.sh "" "" "xml"
```

### 🔹 Full combinations
```bash
 ./run.sh "Brand=Toyota,Price<30000" "price-desc" "table"
 ./run.sh "Brand=Toyota,Price>20000" "price-asc" "json"
 ./run.sh "Brand=Toyota,ReleaseDate=2023-01-15" "year-desc" "xml"
 ./run.sh "Brand=Toyota,ReleaseDate<2024-01-01" "year-asc" "table"
 ./run.sh "Brand=Toyota,Price=25000" "price-desc" "json"
```

---

## 📁 Logs

- Logs are saved in: `logs/app.log`

---


## 🔧 Tech Stack

- Java 17
- Maven
- SLF4J + Logback (logging)
- OpenCSV, Jackson, JAXB
- Docker

---
