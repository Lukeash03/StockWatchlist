# Stock Watchlist App 📈
<p>A simple mobile app for Android to track and manage your favorite stocks. Built using Kotlin, this app allows users to search for stocks, add them to a watchlist, view detailed company information, and manage their watchlist.</p>

## **Features**  
🔍 Search for Stocks: Search and view details of stocks using Alphavantage API.  
➕ Add to Watchlist: Add stocks to your personalized watchlist for quick access.  
➖ Remove from Watchlist: Easily remove stocks from your watchlist.  
🔄 Swipe to Refresh: Refresh the stock list by swiping down on the screen.  
🛠️ Local Storage: Uses Room Database for persisting watchlist data.  
📡 Network Integration: Retrofit used for fetching stock data from the API.  
🔄 Real-time Updates: UI updates to reflect changes in the watchlist dynamically.

## **Tech Stack**
- Language: Kotlin
- Architecture: MVVM (Model-View-ViewModel)
- Dependency Injection: Dagger-Hilt for cleaner architecture and easier testing
- Network: Retrofit
- Local Storage: Room Database
- UI Components: RecyclerView, Material Design Components, Bottom Navigation Bar
- Coroutine Support: Used for asynchronous tasks
