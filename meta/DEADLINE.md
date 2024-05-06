# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

    My App integrates the Wall Street Bets Reddit API and the Polygon
    Historical Stock Data API. In the API Queries, when a valid date
    is selected that the Wall Street Bets API can query, the HTTP
    Response will be parsed into an object called WallStreetBetsResult[]
    which is an array of objects containing the most popular Reddit posts
    of a specified date. The UI is updated to display the Ticker (indentifier),
    the Sentiment (Bullish = go up, Bearish = go down), and the sentiment score
    (confidence).

    The stock ticker of the most popular Reddit post is then used to query
    the Polygon Historical Stock Data API. The Stock API returns a single object that
    stores the open price, close price, and ticker. The UI is updated to show the day
    change and prices of the given ticker. This allows users to observe whether
    the Wall Street Bets data is following the actual trend of the stock. This
    app could serve as a short-term investing aid, though many of the stocks don't
    follow the sentiment.

    With the Wall Street Bets API and the Polygon API, there is a query limit. To
    counterract this, I put in a 6 second delay between searches and Stock Price Queries.
    The Wall Street Bets API was very frustrating to work with, as traffic drastically
    affects the behavior of the API. When there is little traffic, a 400 status code is
    much more common. The poor structure of the API makes it so that the app works best
    at hours with little traffic and often throws 429 Too Many Request errors during the day, though the
    same request works at less trafficked hours.

    **NOTE**
    If an alert pops up related to HTTP errors, it is often an issue with the Wall Street
    Bets API, as they dont include an option to limit the queries.
    To fix this, sometimes waiting for less traffic will solve the issue. If this doesn't work,
    there may not be any data for the given date, so changing the date may resolve the issue.
    *******

> **Also, include the GitHub `https` URL to your repository.**

TODO WRITE / REPLACE

## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### API 1

```
https://tradestie.com/api/v1/apps/reddit?date=2024-05-02
https://tradestie.com/api/v1/apps/reddit?date=2024-05-01
```

> API1 is very inconsistent, and will throw "429 Too Many Request" errors though the documentation shows no "&limit=#"

### API 2

```
https://api.polygon.io/v1/open-close/CVNA/2024-05-01?adjusted=true&apiKey=XtQnwq6TjekrGAMmwe8kjeAouVR5iVLA
https://api.polygon.io/v1/open-close/TSLA/2024-05-01?adjusted=true&apiKey=XtQnwq6TjekrGAMmwe8kjeAouVR5iVLA
```

> API 2 utilizes a key, and a limit of 10 requests per minute.

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

I learned that finding good APIs is very difficult. I had built my project with one of my APIs working
just fine, and the afternoon of the next day came, and the same code that was working prior was giving
me errors. I did some research into why this was the case and event went as far as contatcing the owner
of the API.
Something exciting that I learned from working on this project is how fascinating it is to connect
data from multiple sources. Additionally, scrolling through the hundreds of APIs, I realized how
versitile Computer Science is, and how limitless projects can be.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

If I could start from scratch, I would choose APIs that are much more reliable. The Wall
Street Bets API is hosted by an individual likely with servers that can't handle traffic
as well as a larger company could. Additionally, it would be interesting to create an API of my
own using data scraping. I really enjoyed the project concept though, as this summer I hope to
create some kind of indicator to analyze stock trends.
