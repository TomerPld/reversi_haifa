
class MinMaxAlphaBeta:
    def __init__(self, board, heuristic_funcion, max_depth):
        self.board = board
        self.heuristic_funcion = heuristic_funcion
        self.max_depth = max_depth

    def get_next(self):
        return _mini_max(self.state, 1)

    def set_state(self, state):
        self.state = state

    def deep_enough(self, state, depth):
        return depth >= self.max_depth

    def _mini_max_alpha_beta(state, depth, alpha, beta):
        if self.deep_enough(state, depth):
            return self.heuristic_funcion(state, depth), ''
        successors = self.board.get_neighbors()
        
        if depth % 2 != 0:
            while len(successors) > 0 or alpha > beta:
                best = -float('inf')
                m = successors.pop()
                value, path = self._mini_max_alpha_beta(m, deph + 1, alpha, beta)
                if value > best:
                    best = value
                    best_path = path + m
                beta = min(beta, value)
            return best, best_path

        elif depth % 2 == 0:
            while len(successors) > 0  or alpha > beta:
                best = float('inf')
                m = successors.pop()
                value, path = self._mini_max_alpha_beta(m, deph + 1, alpha, beta)
                if value < best:
                    best = value
                    best_path = path + m
                alpha = max(alpha, value)
            return best, best_path
        
        