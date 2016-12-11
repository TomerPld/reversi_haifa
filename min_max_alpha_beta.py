from enums import *


def min_max_alpha_beta(state, depth, heuristic_funcion, max_depth, board, alpha, beta):
    static_value = heuristic_funcion(state, depth)
    if static_value in [float('inf'), -float('inf')] or depth >= max_depth:
        return static_value, []
    successors = board.get_neighbors_states()
    
    if depth % 2 != 0:
        best = -float('inf')
        best_path = []
        while len(successors) > 0 or alpha > beta:
            m = successors.pop()
            value, path = min_max(m, deph + 1, heuristic_funcion, max_depth, board, alpha, beta)                
            if value > best:
                best = value
                best_path = path
                best_path.append(m)
            beta = min(beta, value)
        return best, best_path

    elif depth % 2 == 0:
        best = float('inf')
        best_path = []
        while len(successors) > 0 or alpha > beta:
            m = successors.pop()
            value, path = min_max(m, deph + 1, heuristic_funcion, max_depth, board, alpha, beta)                
            if value < best:
                best = value
                best_path = path
                best_path.append(m)
            alpha = max(alpha, value)
        return best, best_path
    
                