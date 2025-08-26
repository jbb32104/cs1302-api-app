# CSCI 1302 – API App v2024.sp  
### Stock Sentiment Analyzer  

![Approved for](https://img.shields.io/badge/Approved%20for-Spring%202024-blue)  
![Team Size](https://img.shields.io/badge/Team%20Size-1-informational)  

![Banner](https://github.com/cs1302uga/cs1302-api-app/raw/main/resources/readme-banner.png)  

## 📌 Project Overview  
This project uses the **WallStreetBets (WSB) API** to analyze stock sentiment from the r/wallstreetbets subreddit. The application ranks stocks by their **weighted sentiment score** (positive vs. negative mentions) and allows the user to:  

- View the **top trending stocks** on WallStreetBets.  
- Select a **date** to view the **historical stock price**.  
- Compare sentiment-driven stocks against their market performance.  

This project demonstrates API integration, JavaFX GUI development, and the combination of **social sentiment data** with **financial data** to provide insight into retail trading activity.  

---

## 🚀 Features  
- ✅ Fetches stock ticker sentiment data from the WallStreetBets API.  
- ✅ Ranks stocks by **highest weighted sentiment**.  
- ✅ Allows user to select a **historical date** to fetch stock prices.  
- ✅ Displays other **top sentiment stocks** for that date.  
- ✅ Interactive and user-friendly JavaFX interface.  

---

## 🔧 Technologies Used  
- **Java 17**  
- **JavaFX 17**  
- **Gson** (JSON parsing)  
- **WallStreetBets API** – sentiment data  
- **Financial/Stock Price API** – historical price data  

---

## 📊 Example Workflow  
1. User runs the app.  
2. App fetches sentiment scores from **WSB API**.  
3. User selects a stock → App fetches its **historical price**.  
4. App displays the stock’s chart & related top sentiment tickers.  

---

## 🛠️ How to Run  
1. Clone this repository:  
   ```bash
   git clone <your-fork-url> cs1302-api-app
   cd cs1302-api-app
