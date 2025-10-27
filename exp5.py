import heapq

# ------------------------------
# Distance Vector Routing
# ------------------------------
def distance_vector_routing(graph, nodes):
    # Initialize distances
    dist = {node: {n: float('inf') for n in nodes} for node in nodes}
    for node in nodes:
        dist[node][node] = 0
        for neighbor, weight in graph.get(node, {}).items():
            dist[node][neighbor] = weight

    # Iterative updates (relaxation)
    for _ in range(len(nodes) - 1):
        for u in nodes:
            for v in nodes:
                for w in nodes:
                    if dist[u][v] > dist[u][w] + dist[w][v]:
                        dist[u][v] = dist[u][w] + dist[w][v]
    
    # Print Distance Vector Routing Table
    print("--- Distance Vector Routing Table ---")
    for node in nodes:
        distances = ' '.join(str(dist[node][dest]) for dest in nodes)
        print(f"Router {node} distances: {distances}")
    print()

# ------------------------------
# Link State Routing
# ------------------------------
def link_state_routing(graph, nodes):
    for start in nodes:
        dist = {node: float('inf') for node in nodes}
        dist[start] = 0
        visited = set()
        heap = [(0, start)]

        while heap:
            d, node = heapq.heappop(heap)
            if node in visited:
                continue
            visited.add(node)
            for neighbor, weight in graph[node].items():
                if dist[neighbor] > d + weight:
                    dist[neighbor] = d + weight
                    heapq.heappush(heap, (dist[neighbor], neighbor))
        
        # Print Link State Routing Table for this router
        print(f"--- Link State Routing Table (from Router {start}) ---")
        for node in nodes:
            print(f"Router {start} -> Router {node} = {dist[node]}")
        print()

# ------------------------------
# Sample Graph
# ------------------------------
graph = {
    'A': {'B': 1, 'C': 4},
    'B': {'A': 1, 'C': 2, 'D': 5},
    'C': {'A': 4, 'B': 2, 'D': 1},
    'D': {'B': 5, 'C': 1}
}

nodes = ['A', 'B', 'C', 'D']

# Run Distance Vector Routing
distance_vector_routing(graph, nodes)

# Run Link State Routing
link_state_routing(graph, nodes)
