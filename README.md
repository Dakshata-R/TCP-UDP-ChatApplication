# TCP-UDP-ChatApplication
A real-time chat application in Java demonstrating TCP for reliable messaging and UDP for fast, connectionless messaging.

## Overview
This project is a **real-time chat application** implemented in Java that demonstrates the difference between **Transmission Control Protocol (TCP)** and **User Datagram Protocol (UDP)**.  
- **TCP** is used for **reliable, ordered communication**, suitable for sending important messages.  
- **UDP** is used for **fast, connectionless messaging**, suitable for notifications or low-latency updates.

The application consists of:
- **Server.java** – Handles both TCP and UDP clients simultaneously.
- **TCPClient.java** – Connects to the server over TCP for reliable messaging.
- **UDPClient.java** – Connects to the server over UDP for fast messaging.
- 
## Features
- Real-time chat between multiple TCP clients.
- Fast notifications or messages via UDP.
- Demonstrates **connection-oriented vs connectionless communication**.
- Uses **multithreading** to handle multiple clients concurrently.
- Works on localhost and can be extended to LAN.

## How to Run

### 1. Compile the code
javac Server.java
javac TCPClient.java
javac UDPClient.java
### 2. Start the server
java Server
### 3. Start a TCP client (in a new terminal)
java TCPClient
### 4. Start a UDP client (in another terminal)
java UDPClient
### 5. Test communication
Type messages in TCP client → reliable, ordered messages are sent to server and other TCP clients.
Type messages in UDP client → messages are sent to server and echoed back immediately.

### Sample Output
Server:
Server running TCP:5000 UDP:5001
TCP: Hello via TCP
UDP: Quick message via UDP

TCP Client:
Connected to TCP server.
Hello via TCP
TCP Msg: Hi, I got your TCP message!

UDP Client:
Quick message via UDP
UDP Msg: Quick message via UDP
