from enums import *


def min_max(board, depth, heuristic_funcion, max_depth):
    static_value = heuristic_funcion(board, depth)
    successors = board.get_neighbors_states()
    stuck = False
    if len(successors) == 0:
        board.next_turn()
        opponent_successors = board.get_neighbors_states()
        board.next_turn()
        if len(opponent_successors) == 0:
            stuck = True
    if stuck or depth >= max_depth:
        return static_value, []
		
    if len(successors) == 0:
        board.next_turn()
        best, best_path = min_max(board, depth + 1, heuristic_funcion, max_depth)
        return best, best_path
    
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
    
        