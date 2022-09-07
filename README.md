# Orderbook
Displays an orderbook upto 10 levels using the Coinbase API
[![Tests](https://github.com/elrasp/orderbook/actions/workflows/maven.yml/badge.svg)](https://github.com/elrasp/orderbook/actions/workflows/maven.yml)

## Coinbase API
The WebSocket Feed of the Coinbase API is used. More information about the API can be obtained from its [documentation](https://docs.cloud.coinbase.com/exchange/docs/websocket-overview)

## Java Version
The project has been built using Java JDK 17.0.4

## How to run
`java -jar target/orderbook-1.0-SNAPSHOT-jar-with-dependencies.jar BTC-USD`
<ul>
<li>Takes a single command line argument that indicates the product id for which the market data feed is required. If the product id is not provided by default BTC-USD will be considered.</li>
<li>Use CRTL + C to exit</li>
</ul>
