from enum import IntEnum

class PlayerOptions(IntEnum):
    Player = 0
    MinMax = 1
    MinMaxAlphaBeta = 2

    def get_next(current):
        return PlayerOptions((current.value + 1) % len(PlayerOptions.__members__.items()))

class State:
    def __init__(self):
        self.matrix = []
        self.turn = 0

    def init_state(self):
        for i in range(12):
            for j in range(12):
                if (i, j) in [(5,6), (6, 5)]:
                    self.matrix.append(1)
                elif (i, j) in [(5, 5), (6, 6)]:
                    self.matrix.append(2)
                else:
                    self.matrix.append(0)
        self.turn = 0
        return self

class Board:
    def __init__(self):
        self.init_state = State().init_state()
        self.state = self.init_state
        
    def reset(self):
        self.state = self.init_state

    def get_neighbors(self):
        pass

    def set_state(self, state):
        self.state = state

    def next_turn(self):
        self.state.turn = 1 - self.state.turn
		
