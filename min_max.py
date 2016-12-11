from enums import *


def min_max(board, depth, heuristic_funcion, max_depth):
    static_value = heuristic_funcion(board, depth)
    print('  ' * depth, 'static_value', static_value)
    if static_value in [float('inf'), -float('inf')] or depth >= max_depth:
        return static_value, []
    successors = board.get_neighbors_states()
    print('  ' * depth, 'successors', len(successors))
    
    if depth % 2 != 0:
        best = -float('inf')
        best_path = []
        while len(successors) > 0:
            m = successors.pop()
            value, path = min_max(m, depth + 1, heuristic_funcion, max_depth)                
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
            value, path = min_max(m, depth + 1, heuristic_funcion, max_depth)                
            if value < best:
                best = value
                best_path = path
                best_path.append(m)
        return best, best_path
    
        