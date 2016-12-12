from enums import *


def min_max_alpha_beta(board, depth, heuristic_funcion, max_depth, alpha, beta):
    static_value = heuristic_funcion(board, depth)
    print('  ' * depth, 'static_value', static_value)
    successors = board.get_neighbors_states()
    if len(successors) == 0 or depth >= max_depth:
        return static_value, []
    print('  ' * depth, 'successors', len(successors))
    
    if depth % 2 != 0:
        best = -float('inf')
        best_path = []
        while len(successors) > 0 and alpha <= beta:
            m = successors.pop()
            value, path = min_max_alpha_beta(m, depth + 1, heuristic_funcion, max_depth, alpha, beta)
            if value > best:
                best = value
                best_path = path
                best_path.append(m)
            beta = min(beta, value)
        print('  ' * depth, 'best', best)
        return best, best_path

    elif depth % 2 == 0:
        best = float('inf')
        best_path = []
        while len(successors) > 0 and alpha <= beta:
            m = successors.pop()
            value, path = min_max_alpha_beta(m, depth + 1, heuristic_funcion, max_depth, alpha, beta)           
            if value < best:
                best = value
                best_path = path
                best_path.append(m)
            alpha = max(alpha, value)
        print('  ' * depth, 'best', best)
        return best, best_path
    
                