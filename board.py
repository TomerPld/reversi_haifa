from enum import IntEnum

class PlayerOptions(IntEnum):
    Player = 0
    MinMax = 1
    MinMaxAlphaBeta = 2

    def get_next(current):
        return PlayerOptions((current.value + 1) % len(PlayerOptions.__members__.items()))

class State:
    def __init__(self):
        pass


class Board:
    def __init__(self, init_state=None):
        self.state = init_state
        self.init_state = init_state

    def reset(self):
        self.state = self.init_state

    def get_neighbors(self):
        pass

    def set_state(self, state):
        self.state = state

		
