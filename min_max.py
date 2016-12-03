

def min_max(state, depth, heuristic_funcion, max_depth, board):
    static_value = heuristic_funcion(state, depth)
    if static_value in [float('inf'), -float('inf')] or depth >= max_depth:
        return static_value, []
    successors = board.get_neighbors_states()
    
    if depth % 2 != 0:
        best = -float('inf')
        best_path = []
        while len(successors) > 0:
            m = successors.pop()
            value, path = min_max(m, deph + 1, heuristic_funcion, max_depth, board)                
            if value > best:
                best = value
                best_path = path
                best_path.append(m)
        return best, best_path

    elif depth % 2 == 0:
        best = float('inf')
        best_path = []
        while len(successors) > 0:
            m = successors.pop()
            value, path = min_max(m, deph + 1, heuristic_funcion, max_depth, board)                
            if value < best:
                best = value
                best_path = path
                best_path.append(m)
        return best, best_path
    
        