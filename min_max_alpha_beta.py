from enums import *


def min_max_alpha_beta(board, depth, heuristic_funcion, max_depth, alpha, beta):
    static_value = heuristic_funcion(board, depth)
    print('  ' * depth, 'static_value', static_value)
    if static_value in [float('inf'), -float('inf')] or depth >= max_depth:
        return static_value, []
    successors = board.get_neighbors_states()
    print('  ' * depth, 'successors', len(successors))
    
    if depth % 2 != 0:
        best_maximum = -float('inf')
        best_path = []
        while len(successors) > 0 and alpha <= beta:
            m = successors.pop()
            value, path = min_max_alpha_beta(m, depth + 1, heuristic_funcion, max_depth, alpha, beta)
            if value > best_maximum:
                best_maximum = value
                best_path = path
                best_path.append(m)
            beta = min(beta, value)
        return best_maximum, best_path

    elif depth % 2 == 0:
        best_minimum = float('inf')
        best_path = []
        while len(successors) > 0 and alpha <= beta:
            m = successors.pop()
            value, path = min_max_alpha_beta(m, depth + 1, heuristic_funcion, max_depth, alpha, beta)           
            if value < best_minimum:
                best_minimum = value
                best_path = path
                best_path.append(m)
            alpha = max(alpha, value)
        return best_minimum, best_path
    
                